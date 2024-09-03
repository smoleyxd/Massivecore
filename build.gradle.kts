import groovy.namespace.QName
import groovy.util.Node
import io.papermc.paperweight.util.Git
import io.papermc.paperweight.util.path

// Plugins
plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
    `java-library`
    id("io.spring.dependency-management") version "1.1.5"
    id("net.linguica.maven-settings") version "0.5"
    id("io.papermc.paperweight.userdev") version "1.7.1"
    `maven-publish`
}

// Basics
group = "com.massivecraft.${name.lowercase()}"
version = "MC-1.21-SNAPSHOT"
description = name

// Java Version
java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

// Repositories
repositories {
    mavenLocal()
    maven {
        name = "massivecraft"
        url = uri(
            System.getenv("MAVEN_DEPENDENCIES_URL")
                ?: "https://maven.massivecraft.team/repository/massivecraft-dependencies/"
        )
        credentials {
            username = System.getenv("MAVEN_DEPENDENCIES_USER")
            password = System.getenv("MAVEN_DEPENDENCIES_PASS")
        }
    }

    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

// Dependency Management
dependencyManagement {
    imports {
        mavenBom("com.massivecraft.massivesuper:MassiveSuper:$version")
    }
}

// Mojang-mapped Reobf
paperweight.reobfArtifactConfiguration = io.papermc.paperweight.userdev.ReobfArtifactConfiguration.MOJANG_PRODUCTION

// Dependencies
dependencies {
    paperweight.paperDevBundle(project.dependencyManagement.importedProperties["massiveSpigotVersion"])

    api("com.massivecraft.massivecore", "MassiveCoreXlib")

    compileOnly("net.milkbowl.vault", "VaultAPI") {
        exclude("org.bukkit", "bukkit")
    }

    compileOnly("com.googlecode.json-simple", "json-simple", "1.1.1") {
        exclude("junit", "junit")
    }

    compileOnly("org.jetbrains", "annotations", "24.1.0")
    compileOnly("io.lumine", "Mythic-Dist")
}

// Tasks
tasks {
    build {
        dependsOn(shadowJar)
    }

    jar {
        archiveClassifier.set("original")
    }

    shadowJar {
        archiveClassifier.set("")
    }

    withType<GenerateModuleMetadata> {
        enabled = false
    }

    compileJava {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything

        // Set the release flag. This configures what version bytecode the compiler will emit, as well as what JDK APIs are usable.
        // See https://openjdk.java.net/jeps/247 for more information.
        options.release.set(21)
    }

    javadoc {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything
    }

    processResources {
        outputs.upToDateWhen { false }

        filteringCharset = Charsets.UTF_8.name() // We want UTF-8 for everything

        // Get Build version
        val git = Git(project.layout.projectDirectory.path)
        val gitHash = git("rev-parse", "--short=8", "HEAD").getText().trim()
        var implementationVersion = System.getenv("BUILD_NUMBER") ?: gitHash
        if (git("status", "-s").getText().isNotBlank()) implementationVersion = "$implementationVersion-LOCAL"

        val props = project.dependencyManagement.importedProperties

        expand(
            "group" to project.group,
            "name" to project.name,
            "version" to "${project.version} ($implementationVersion)",
            "url" to "${props["massiveBaseUrl"]}/${project.name.lowercase()}",
            "description" to "${props["massiveColorInfo"]}${project.name} is a plugin that contains libraries and features that other plugins make use of. ${props["massiveDescriptionSuffix"]}",
        )
    }
}

// Jars
java {
    withSourcesJar()
}

// Publishing
publishing {
    publications.create<MavenPublication>("maven") {
        pom.withXml {
            asNode().appendNode("packaging", "jar")

            val dependenciesNode: Node = asNode().children().find {
                if (it !is Node) return@find false
                val name = it.name()
                return@find (name == "dependencies" || (name is QName && name.localPart == "dependencies"))
            } as Node? ?: asNode().appendNode("dependencies")

            configurations["compileOnly"].allDependencies.forEach {
                if (it.group != null) {
                    val dependencyNode = dependenciesNode.appendNode("dependency")
                    dependencyNode.appendNode("groupId", it.group)
                    dependencyNode.appendNode("artifactId", it.name)
                    if (it.version != null) {
                        dependencyNode.appendNode("version", it.version)
                        dependencyNode.appendNode("scope", "provided")
                    }
                }
            }
        }

        from(components["java"])
    }

    repositories {
        val repo = if (project.version.toString().endsWith("-SNAPSHOT"))
            "MAVEN_REPO_URL_SNAPSHOTS" else "MAVEN_REPO_URL_RELEASES"

        if (System.getenv(repo) != null) maven {
            name = "massivecraft"
            url = uri(System.getenv(repo))
            credentials {
                username = System.getenv("MAVEN_DEPENDENCIES_USER")
                password = System.getenv("MAVEN_DEPENDENCIES_PASS")
            }
        }
    }
}
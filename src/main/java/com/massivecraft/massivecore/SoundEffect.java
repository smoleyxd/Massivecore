package com.massivecraft.massivecore;

import com.massivecraft.massivecore.command.editor.annotation.EditorName;
import com.massivecraft.massivecore.command.editor.annotation.EditorNullable;
import com.massivecraft.massivecore.command.editor.annotation.EditorType;
import com.massivecraft.massivecore.command.type.enumeration.TypeSound;
import com.massivecraft.massivecore.command.type.enumeration.TypeSoundId;
import com.massivecraft.massivecore.util.MUtil;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

public final class SoundEffect implements Serializable
{
	@Serial
	private static final transient long serialVersionUID = 1L;
	
	// -------------------------------------------- //
	// FIELDS: RAW
	// -------------------------------------------- //
	
	@EditorNullable(false)
	@EditorName("sound")
	@EditorType(TypeSoundId.class)
	private final String soundId;
	public String getSoundId() { return this.soundId; }
	public Sound getSound() { return TypeSound.valueOf(this.getSoundId()); }
	
	private final float volume;
	public float getVolume() { return this.volume; }
	
	private final float pitch;
	public float getPitch() { return this.pitch; }
	
	// -------------------------------------------- //
	// FIELDS: WITH
	// -------------------------------------------- //
	
	@Contract(value = "_ -> new", pure = true)
	public @NotNull SoundEffect withSoundId(String soundId) { return new SoundEffect(soundId, volume, pitch); }
	@Contract("_ -> new")
	public @NotNull SoundEffect withSound(Sound sound) { return new SoundEffect(TypeSound.get().getId(sound), volume, pitch); }
	@Contract(value = "_ -> new", pure = true)
	public @NotNull SoundEffect withVolume(float volume) { return new SoundEffect(soundId, volume, pitch); }
	@Contract(value = "_ -> new", pure = true)
	public @NotNull SoundEffect withPitch(float pitch) { return new SoundEffect(soundId, volume, pitch); }
	
	// -------------------------------------------- //
	// CONSTUCT
	// -------------------------------------------- //
	
	private SoundEffect(String soundId, float volume, float pitch)
	{
		this.soundId = soundId;
		this.volume = volume;
		this.pitch = pitch;
	}
	
	private SoundEffect()
	{
		// No Arg Constructor for GSON
		this(null, 1.0f, 1.0f);
	}
	
	// -------------------------------------------- //
	// VALUE OF
	// -------------------------------------------- //
	
	@Contract(value = "_, _, _ -> new", pure = true)
	public static @NotNull SoundEffect valueOf(String soundId, float volume, float pitch)
	{
		return new SoundEffect(soundId, volume, pitch);
	}
	
	@Contract("_, _, _ -> new")
	public static @NotNull SoundEffect valueOf(Sound sound, float volume, float pitch)
	{
		return valueOf(TypeSound.get().getId(sound), volume, pitch);
	}
	
	// -------------------------------------------- //
	// RUN
	// -------------------------------------------- //
	
	@Contract("null -> fail")
	public void run(Location location)
	{
		if (location == null) throw new NullPointerException("location");
		location.getWorld().playSound(location, this.getSound(), this.getVolume(), this.getPitch());
	}
	
	@Contract("null, _ -> fail")
	public void run(HumanEntity human, Location location)
	{
		if (human == null) throw new NullPointerException("human");
		if (MUtil.isntPlayer(human)) return;
		Player player = (Player)human;
		player.playSound(location, this.getSound(), this.getVolume(), this.getPitch());
	}
	
	@Contract("null -> fail")
	public void run(HumanEntity human)
	{
		if (human == null) throw new NullPointerException("human");
		if (MUtil.isntPlayer(human)) return;
		this.run(human, human.getEyeLocation());
	}
	
	// -------------------------------------------- //
	// RUN ALL
	// -------------------------------------------- //
	
	public static void runAll(@NotNull Collection<SoundEffect> soundEffects, @NotNull Location location)
	{
		for (SoundEffect soundEffect : soundEffects)
		{
			soundEffect.run(location);
		}
	}
	
	public static void runAll(@NotNull Collection<SoundEffect> soundEffects, @NotNull HumanEntity human, Location location)
	{
		for (SoundEffect soundEffect : soundEffects)
		{
			soundEffect.run(human, location);
		}
	}
	
	public static void runAll(@NotNull Collection<SoundEffect> soundEffects, @NotNull HumanEntity human)
	{
		for (SoundEffect soundEffect : soundEffects)
		{
			soundEffect.run(human);
		}
	}
	
	// -------------------------------------------- //
	// EQUALS & HASHCODE
	// -------------------------------------------- //
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(pitch);
		result = prime * result + ((soundId == null) ? 0 : soundId.hashCode());
		result = prime * result + Float.floatToIntBits(volume);
		return result;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof SoundEffect other)) return false;
		if (Float.floatToIntBits(pitch) != Float.floatToIntBits(other.pitch)) return false;
		if (soundId == null)
		{
			if (other.soundId != null) return false;
		}
		else if (!soundId.equals(other.soundId)) return false;
		return Float.floatToIntBits(volume) == Float.floatToIntBits(other.volume);
	}
	
}

package com.massivecraft.massivecore.store.subscribers;

import org.bukkit.util.Consumer;

public class ConsumerSubscriber<T> extends OperationSubscriber<T>
{
	
	private final Consumer<T> consumer;
	
	/**
	 * Construct a new instance
	 * @param consumer the consumer
	 */
	public ConsumerSubscriber(final Consumer<T> consumer) {
		this.consumer = consumer;
	}
	
	
	@Override
	public void onNext(final T document) {
		super.onNext(document);
		consumer.accept(document);
	}
	
}

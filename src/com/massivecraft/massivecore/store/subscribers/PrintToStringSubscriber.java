package com.massivecraft.massivecore.store.subscribers;

public class PrintToStringSubscriber<T> extends ConsumerSubscriber<T>
{
	
	/**
	 * Construct a new instance
	 */
	public PrintToStringSubscriber() {
		super(System.out::println);
	}

}

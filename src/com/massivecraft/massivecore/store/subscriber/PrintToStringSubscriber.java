package com.massivecraft.massivecore.store.subscriber;

public class PrintToStringSubscriber<T> extends ConsumerSubscriber<T>
{
	
	/**
	 * Construct a new instance
	 */
	public PrintToStringSubscriber() {
		super(System.out::println);
	}

}

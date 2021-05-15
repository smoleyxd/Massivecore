package com.massivecraft.massivecore.store.subscribers;

public class PrintSubscriber<T> extends OperationSubscriber<T>
{
	
	private final String message;
	
	/**
	 * A Subscriber that outputs a message onComplete.
	 *
	 * @param message the message to output onComplete
	 */
	public PrintSubscriber(final String message) {
		this.message = message;
	}
	
	@Override
	public void onComplete() {
		System.out.printf((message) + "%n", getReceived());
		super.onComplete();
	}
}

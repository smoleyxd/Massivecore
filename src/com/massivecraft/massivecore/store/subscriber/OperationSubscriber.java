package com.massivecraft.massivecore.store.subscriber;

import com.massivecraft.massivecore.xlib.reactivestreams.Subscription;

public class OperationSubscriber<T> extends ObservableSubscriber<T>
{
	
	@Override
	public void onSubscribe(final Subscription s) {
		super.onSubscribe(s);
		s.request(Integer.MAX_VALUE);
	}

}

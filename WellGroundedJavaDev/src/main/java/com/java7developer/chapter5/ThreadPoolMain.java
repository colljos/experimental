package com.java7developer.chapter5;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledFuture;

import com.java7developer.chapter4.QueueReaderTask;
import com.java7developer.chapter4.WorkUnit;
import com.java7developer.chapter5.ThreadPoolManager.CancelProxy;
import java.lang.invoke.MethodHandle;

public class ThreadPoolMain {

	private ScheduledFuture<?> hndl;
	private ThreadPoolManager manager;
	  
	private void run() {
		BlockingQueue<WorkUnit<String>> lbq = new LinkedBlockingQueue<>();
		manager = new ThreadPoolManager(lbq);
		  
		final QueueReaderTask msgReader = new QueueReaderTask(100) {
			@Override
			public void doAction(String msg_) {
				if (msg_ != null) System.out.println("Msg recvd: "+ msg_);
			}
		};
		hndl = manager.run(msgReader); 
	}
	
	///////////////////////////////////
	  
	private void cancelUsingReflection(ScheduledFuture<?> hndl) {
		Method meth = manager.makeMethod();
		               
		try {
			System.out.println("With Reflection");
			meth.invoke(hndl);	
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	private void cancelUsingProxy(ScheduledFuture<?> hndl) {
		CancelProxy proxy = manager.makeProxy();
		               
		System.out.println("With Proxy");
		// This is the only one with compile-time static typing
		proxy.invoke(manager, hndl);
	}
		       
	private void cancelUsingMH(ScheduledFuture<?> hndl) {
		MethodHandle mh = manager.makeMh();
			
		try {
			System.out.println("With Method Handle");
			mh.invokeExact(hndl);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}  
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ThreadPoolMain tpm = new ThreadPoolMain();
		tpm.run();
	}

}

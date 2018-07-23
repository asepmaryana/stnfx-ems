package com.stu.dmt;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class PausableThreadPoolExecutor extends ThreadPoolExecutor {
	
	public PausableThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}

	@Override
	protected void beforeExecute(Thread t, Runnable r)
	{
		super.beforeExecute(t, r);
		pauseLock.lock();
		try {
			while (isPaused) unpaused.await();
		}
		catch(InterruptedException ie) {
			t.interrupt();
		}
		finally {
			pauseLock.unlock();
		}
	}

	    public void pause() {
	        pauseLock.lock();
	        try {
	            isPaused = true;
	        } finally {
	            pauseLock.unlock();
	        }
	    }

	    public void resume() {
	        pauseLock.lock();
	        try {
	            isPaused = false;
	            unpaused.signalAll();
	        } finally {
	            pauseLock.unlock();
	        }
	    }

	    public boolean getIsPaused() {
	        pauseLock.lock();
	        try {
	            return isPaused;
	        }
	        finally {
	            pauseLock.unlock();
	        }
	    }

	    /**
	    * <code>true</code> if execution is paused, <code>false</code> otherwise.
	    */
	    private boolean isPaused;

	   /**
	    * lock on paused state.
	    */
	    private ReentrantLock pauseLock = new ReentrantLock();

	   /**
	    * unpaused condition to await when paused.
	    */
	    private Condition unpaused = pauseLock.newCondition();
}

package com.stu.dmt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.stu.dmt.config.AppConfig;
import com.stu.dmt.config.PollerConfig;

public class Main {
	
	int duration = 10;	
    int second;
    int pollCycleNumber = 0;
    int respError = 0;
    PollerConfig cfg;
    PausableThreadPoolExecutor executorService;
    AnnotationConfigApplicationContext ctx = null;
    
    class Shutdown extends Thread
    {
        @Override
        public void run()
        {
        	LoggerFactory.getLogger(Shutdown.class).info("Poller shutting down, please wait....");
            try {
                if(executorService != null) executorService.shutdown();
            }
            catch (Exception e) {
            	LoggerFactory.getLogger(Shutdown.class).error("Poller shutting error: ", e);
            }
            LoggerFactory.getLogger(Shutdown.class).info("Poller stopped.");
            if(ctx != null) ctx.close();
        }
    }
    
    public Main()
    {
    	Runtime.getRuntime().addShutdownHook(new Shutdown());
    	ctx = new AnnotationConfigApplicationContext();
        ctx.register(AppConfig.class);
		ctx.refresh();
		
		cfg = ctx.getBean(PollerConfig.class);
		LoggerFactory.getLogger(Main.class).debug(cfg.toString());
		
        // Create a thread pool to poll devices
        executorService = new PausableThreadPoolExecutor(
            cfg.getCorePoolSize(),
            cfg.getMaximumPoolSize(),
            Long.MAX_VALUE,
            TimeUnit.MINUTES,
            new LinkedBlockingQueue<Runnable>()
        );
    }
    
    public AnnotationConfigApplicationContext getAppContext() {
    	return ctx;
    }
    
    void go()
    {
        Calendar cal = Calendar.getInstance();
        second = cal.get(Calendar.SECOND);
        duration = cfg.getPollCycle();
        int wait = second % duration;
        if(wait == 0) wait = duration;
        else wait = duration - wait;
        
        try { LoggerFactory.getLogger(Main.class).info("Wait for " + wait + " sec."); Thread.sleep(wait * 1000); }
        catch (Exception e) {}
        
        while(true)
        {
        	Calendar dtime = Calendar.getInstance();
            second = dtime.get(Calendar.SECOND);
            
        	pollCycleNumber++;
        	LoggerFactory.getLogger(Main.class).info("Starting poll cycle #" + pollCycleNumber);
            long startTime = System.currentTimeMillis();
            ArrayList<Future<PollResult>> futures = new ArrayList<Future<PollResult>>();
            
            futures.add(executorService.submit(new MatePoller(getAppContext())));

            // wait for all the results
        	List<PollResult> fails	= new ArrayList<PollResult>();
            int successes = 0;
            int partial = 0;
            int failed = 0;
            PollStatus result;
            for (Future<PollResult> future : futures)
            {
                try {
                   result = future.get().getStatus();
                   if (result == PollStatus.SUCCESS) {
                	   successes++;
                   }
                   else if (result == PollStatus.PARTIAL) {
                	   partial++;
                   }
                   else if (result == PollStatus.FAILED) {
                	   fails.add(future.get());
                	   failed++;
                   }
                }
                catch (Exception e) {
                	LoggerFactory.getLogger(Main.class).error("Polling Error:", e);
                	failed++;
                }
            }

            if ((failed == 0) && (partial == 0)) result = PollStatus.SUCCESS;
            else if ((successes > 0) || (partial > 0)) result = PollStatus.PARTIAL;
            else result = PollStatus.FAILED;
            
            // log poll cycle statistics
            long stopTime = System.currentTimeMillis();
            long pollCycleTime = stopTime - startTime;

            LoggerFactory.getLogger(Main.class).info("Poll cycle #" + pollCycleNumber + " complete, took: " + pollCycleTime +" ms. Stats by device: " + successes + " success / " + partial + " partial / " + failed + " failed");
            
            long addition = 0;
            if (pollCycleTime > cfg.getPollCycle() * 1000) {
            	LoggerFactory.getLogger(Main.class).warn("Poll cycle #" + pollCycleNumber + " took too long.");
            	addition = pollCycleTime - (cfg.getPollCycle() * 1000);
            }
            
            // Sleep until time for the next poll cycle
            long now = System.currentTimeMillis();
            while (now < (startTime+(cfg.getPollCycle() * 1000) - addition))
            {
                try {
                   long toSleep = Math.min(5000, (startTime+(cfg.getPollCycle() * 1000) - addition) - now);                       
                   Thread.sleep(toSleep);
                }
                catch (InterruptedException ie) {}
                now = System.currentTimeMillis();
            }
        }
    }
    
	public static void main(String[] args) throws Exception {
		Main app = new Main();
		app.go();		
	}

}

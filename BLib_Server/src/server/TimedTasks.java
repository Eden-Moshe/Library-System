package server;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;



/**
 * A utility class for scheduling and managing daily tasks that run at a specific time.
 * This class provides functionality to schedule tasks to run daily at 13:00 (1:00 PM)
 * and handles the proper scheduling of initial execution and subsequent daily runs.
 */
public class TimedTasks {
    /** The Timer instance used to schedule and manage tasks */
    private Timer timer;

    /**
     * Constructs a new TimedTasks instance.
     * Initializes the internal Timer object used for task scheduling.
     */
    public TimedTasks() {
        timer = new Timer();
    }

    
    /**
     * Schedules a task to run daily at 13:00 (1:00 PM).
     * If the scheduled time has already passed for the current day,
     * the task will be scheduled to start the next day at 13:00.
     * The task will continue to run daily at the same time until stopped.
     *
     * @param task The Runnable task to be executed on schedule
     */
    public void scheduleTask(Runnable task) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 13); 
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        
      
        // for debugging
//        Date now =  new Date();
//        calendar.setTime(now);
//        calendar.add(Calendar.SECOND, 1);
        
        
        // If the time has already passed today, schedule for tomorrow
        if (calendar.getTime().before(new Date())) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                task.run();
            }
        }, calendar.getTime(), 24 * 60 * 60 * 1000);
    }

    /**
     * Stops all scheduled tasks and terminates the timer.
     * After calling this method, the TimedTasks instance cannot be reused.
     */
    public void stop() {
        timer.cancel();
    }
}
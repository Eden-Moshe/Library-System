package server;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class TimedTasks {
    private Timer timer;

    public TimedTasks() {
        timer = new Timer();
    }

    
    public void scheduleTask(Runnable task) {
        Calendar calendar = Calendar.getInstance();
        //daily tasks will run at 13:00 each day
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

    public void stop() {
        timer.cancel();
    }
}
package at.team2.server.tasks;

import at.team2.application.facade.ReservationApplicationFacade;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

@Singleton
public class RemoveInvalidReservationsTask implements Runnable {
    private ReservationApplicationFacade facade;

    public RemoveInvalidReservationsTask() {
        facade = new ReservationApplicationFacade();
    }

    @Override
    public void run() {
        Calendar calendar = Calendar.getInstance();
        Date now = new Date();
        calendar.setTime(now);
        int hour, minute;
        Date timeToRun;

        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        if(hour >= 1) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            calendar.add(Calendar.HOUR_OF_DAY, -(hour -1));
        } else if(hour < 1) {
            calendar.add(Calendar.HOUR_OF_DAY, 1); // calculate the difference to run the task at 01:00
        }

        calendar.add(Calendar.MINUTE, -minute); // calculate the difference and therefore sum up to 0

        timeToRun = calendar.getTime();
        int everyTime = 1000 * 60 * 60 * 24; // one day

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runTask();
            }
        }, timeToRun, everyTime);
    }

    @Schedule(dayOfMonth = "*", hour = "1", minute = "0", second = "0")
    private void runTask() {
        facade.removeOldReservations();
    }
}
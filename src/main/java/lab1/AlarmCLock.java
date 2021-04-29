package lab1;

import java.util.*;

class Time{
    public Integer seconds;
    public Integer minutes;
    public Integer hours;

    public Time(Integer seconds, Integer minutes, Integer hours) {
        this.seconds = seconds;
        this.minutes = minutes;
        this.hours = hours;
    }

    public Time(Integer minutes, Integer hours) {
        this.seconds = 0;
        this.minutes = minutes;
        this.hours = hours;
    }

    public boolean equals(Time t) {
        return seconds.equals(t.seconds) && minutes.equals(t.minutes) && hours.equals(t.hours);
    }

}
public class  AlarmCLock implements AlarmClockInterface {
    public boolean flag = false;
    ArrayList<Time> alarms = new ArrayList<Time>();

    @Override
    public void printAlarmMessage() {
        System.out.println("Alarm!");
    }

    @Override
    public void insertAlarm(Time time) {
        alarms.add(time);
    }

    @Override
    public ArrayList<Time> getAlarms() {
        return  alarms;
    }

    @Override
    public void reset(){
        flag = false;
    }
    @Override
    public Time checkAllAlarms(Time time) {
        for (Time a : alarms) {
            if (a.seconds == time.seconds && a.minutes == time.minutes && time.hours == a.hours) {
                alarms.remove(a);
                printAlarmMessage();
                flag = true;
                return  a;
            }
        }
        return null;
    }

    @Override
    public void deleteAlarms(Time time) {
        for (Time a : alarms) {
            if (a.seconds == time.seconds && a.minutes == time.minutes && time.hours == a.hours) {
                alarms.remove(a);
                break;
            }
        }
    }
}

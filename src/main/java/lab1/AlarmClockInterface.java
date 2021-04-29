package lab1;

import java.util.ArrayList;

public interface AlarmClockInterface {
    void printAlarmMessage();

    void insertAlarm(Time time);
    ArrayList<Time> getAlarms();
    void deleteAlarms(Time time);
    Time checkAllAlarms(Time time);
    void reset();
}

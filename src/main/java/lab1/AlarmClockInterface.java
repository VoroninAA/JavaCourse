package lab1;

public interface AlarmClockInterface {
    void printAlarmMessage();

    void insertAlarm(Time time);

    void checkAllAlarms(Time time);
    void reset();
}

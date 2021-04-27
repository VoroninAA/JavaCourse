package lab1;

public class ClocksExtended extends ClocksBase implements ClocksExtendedInterface {
    private Integer seconds;

    public ClocksExtended(String model, Double cost, Integer hours, Integer minutes, Integer seconds) {
        super(model, cost, hours, minutes);
        this.seconds = seconds;
    }
    @Override
    public Integer getSeconds() {
        return seconds;
    }
    @Override
    public void setSeconds(Integer seconds) throws Exception {
        if (seconds > 59)
            throw new Exception("Time format is not right! Please, check input");
        this.seconds = seconds;
        checkAllAlarms(new Time(hours,minutes,seconds));
    }
    @Override
    public void setStartTime(Integer hours, Integer minutes, Integer seconds) throws Exception {
        if (hours > 23 || minutes > 59 || seconds > 60)
            throw new Exception("Time format is not right! Please, check input");
        this.seconds = seconds;
        this.minutes = minutes;
        this.hours = hours;
        checkAllAlarms(new Time(hours,minutes,seconds));
    }
    @Override
    public void moveTime(Integer hours, Integer minutes, Integer seconds) throws Exception {
        if (this.hours + hours > 23 || (minutes / 60 + this.hours +hours > 23 || seconds / 60 + minutes + this.minutes > 59 && hours == 23))
            throw new Exception("Time format is not right! Please, check input");
        this.minutes += seconds / 60;
        this.hours += minutes / 60;
        this.seconds += seconds % 60;
        this.seconds =this.seconds % 60;
        this.minutes = this.minutes % 60;
        this.hours += hours;
        this.hours = this.hours % 24;
        checkAllAlarms(new Time(this.hours,this.minutes,this.seconds));
    }
    @Override
    public void startTime(Integer seconds) throws Exception {
        this.seconds += seconds;
        this.minutes += this.seconds / 60;
        this.hours += this.minutes / 60;

        this.seconds =this.seconds % 60;
        this.minutes = this.minutes % 60;
        this.hours = this.hours % 24;
    }
}

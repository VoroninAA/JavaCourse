package lab1;

public class ClocksExtended extends ClocksBase {
    private Integer seconds;

    public ClocksExtended(String model, Double cost, Integer hours, Integer minutes, Integer seconds) {
        super(model, cost, hours, minutes);
    }

    public Integer getSeconds() {
        return seconds;
    }

    public void setSeconds(Integer seconds) throws Exception {
        if (seconds > 59)
            throw new Exception("Time format is not right! Please, check input");
        this.seconds = seconds;
    }

    public void setStartTime(Integer hours, Integer minutes, Integer seconds) throws Exception {
        if (hours > 23 || minutes > 59 || seconds > 60)
            throw new Exception("Time format is not right! Please, check input");
        this.seconds = seconds;
        this.minutes = minutes;
        this.hours = hours;
    }

    public void moveTime(Integer hours, Integer minutes, Integer seconds) throws Exception {
        if (this.hours + hours > 23 || (minutes / 60 + this.hours +hours > 23 || seconds / 60 + minutes + this.minutes > 59 && hours == 23))
            throw new Exception("Time format is not right! Please, check input");
        this.seconds += seconds % 60;
        this.minutes += seconds / 60;
        this.minutes += minutes % 60;
        this.hours += hours;
        this.hours += minutes / 60;
    }
}

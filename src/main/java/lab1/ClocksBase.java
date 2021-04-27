package lab1;

public class ClocksBase extends AlarmCLock implements  ClocksInterface{
    protected String model;
    protected Double cost;
    protected Integer hours;
    protected Integer minutes;

    public ClocksBase(String model, Double cost, Integer hours, Integer minutes) {
        this.model = model;
        this.cost = cost;
        this.hours = hours;
        this.minutes = minutes;
    }
    @Override
    public String getModel() {
        return model;
    }
    @Override
    public void setModel(String model) {
        this.model = model;
    }
    @Override
    public Double getCost() {
        return cost;
    }
    @Override
    public void setCost(Double cost) {
        this.cost = cost;
    }
    @Override
    public Integer getHours() {
        return hours;
    }
    @Override
    public void setHours(Integer hours) throws Exception {
        if (hours > 23)
            throw new Exception("Time format is not right! Please, check input");
        this.hours = hours;
        checkAllAlarms(new Time(hours,minutes));
    }
    @Override
    public Integer getMinutes() {
        return minutes;
    }
    @Override
    public void setMinutes(Integer minutes) throws Exception {
        if (minutes > 59)
            throw new Exception("Time format is not right! Please, check input");
        this.minutes = minutes;
        checkAllAlarms(new Time(hours,minutes));
    }
    @Override
    public void setStartTime(Integer hours, Integer minutes) throws Exception {
        if (hours > 23 || minutes > 59)
            throw new Exception("Time format is not right! Please, check input");
        this.hours = hours;
        this.minutes = minutes;
        checkAllAlarms(new Time(hours,minutes));
    }
    @Override
    public void moveTime(Integer hours, Integer minutes) throws Exception {
        if (this.hours + hours > 23 || minutes / 60 + this.hours + hours > 23)
            throw new Exception("Time format is not right! Please, check input");
        this.hours += hours;
        this.hours += minutes / 60;
        this.minutes += minutes % 60;
        checkAllAlarms(new Time(this.hours,this.minutes));
    }

}

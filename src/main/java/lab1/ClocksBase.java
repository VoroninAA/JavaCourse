package lab1;

public class ClocksBase {
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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) throws Exception {
        if (hours > 23)
            throw new Exception("Time format is not right! Please, check input");
        this.hours = hours;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) throws Exception {
        if (minutes > 59)
            throw new Exception("Time format is not right! Please, check input");
        this.minutes = minutes;
    }

    public void setStartTime(Integer hours, Integer minutes) throws Exception {
        if (hours > 23 || minutes > 59)
            throw new Exception("Time format is not right! Please, check input");
        this.hours = hours;
        this.minutes = minutes;
    }

    public void moveTime(Integer hours, Integer minutes) throws Exception {
        if (this.hours + hours > 23 || minutes / 60 + this.hours + hours > 23)
            throw new Exception("Time format is not right! Please, check input");
        this.hours += hours;
        this.hours += minutes / 60;
        this.minutes += minutes % 60;
    }

}

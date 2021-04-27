package lab1;

public interface ClocksExtendedInterface extends ClocksInterface{

    public Integer getSeconds();

    public void setSeconds(Integer seconds) throws Exception ;

    public void setStartTime(Integer hours, Integer minutes, Integer seconds) throws Exception ;

    public void moveTime(Integer hours, Integer minutes, Integer seconds) throws Exception;
    public void startTime(Integer seconds) throws Exception;
}

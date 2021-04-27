package lab1;

public interface ClocksInterface {

        public String getModel();

        public void setModel(String model);

        public Double getCost();

        public void setCost(Double cost);

        public Integer getHours();

        public void setHours(Integer hours)throws Exception  ;

        public Integer getMinutes()throws Exception ;

        public void setMinutes(Integer minutes)throws Exception ;

        public void setStartTime(Integer hours, Integer minutes)throws Exception ;

        public void moveTime(Integer hours, Integer minutes) throws Exception;

}

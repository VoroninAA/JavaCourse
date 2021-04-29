package lab1;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlarmsDB {

    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;
    private static String driver = "com.mysql.cj.jdbc.Driver";
    private static String url = "jdbc:mysql://localhost:3306/";
    private static  String dbName = "alarms";
    private static String userName = "root";
    private static String password = "root";
    public AlarmsDB() {
    }

    public static List<String> startDB() {
        String query = "select * from alarms";


        try {
            Class.forName(driver).newInstance();
            con = DriverManager.getConnection(url + dbName, userName, password);
            if (con != null)
                System.out.println("Приложение подключилось к БД !");
            else
                System.out.println("Приложение НЕ подключилось к БД ?");
            stmt = con.createStatement();

            // executing SELECT query
            rs = stmt.executeQuery(query);

            ArrayList<String> DBList = new ArrayList<>();
            while (rs.next()) {
                System.out.println("Total number of alarms in the table : " + rs.getString(1));
                DBList.add(rs.getString(1));
            }
            return DBList;
        } catch (SQLException | ClassNotFoundException sqlEx) {
            sqlEx.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }
        public static void closeDB(){
        //close connection ,stmt and resultset here
        try {
            con.close();
        } catch (SQLException se) { /*can't do anything */ }
        try {
            stmt.close();
        } catch (SQLException se) { /*can't do anything */ }
        try {
            rs.close();
        } catch (SQLException se) { /*can't do anything */ }
    }
    public static void insertDB(String alarm){
        String query = " insert into alarms (alarm)"
                + " values (?)";
        try {
            con = DriverManager.getConnection(url + dbName, userName, password);
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString (1, alarm);
            stmt.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public static void removeDB(String alarm){
        System.out.println("db " + alarm);
        String query = "delete from alarms where alarm = ?";
        try {
            con = DriverManager.getConnection(url + dbName, userName, password);
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString (1, alarm);
            stmt.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
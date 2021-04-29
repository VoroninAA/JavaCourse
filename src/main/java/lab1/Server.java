package lab1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{
    static ClocksExtended clock = new ClocksExtended("Apple", 10.0, 10, 10, 10);
    public static void main(String[] args) throws Exception
    {
        AlarmsDB db = new AlarmsDB();
        ClocksExtended clock = new ClocksExtended("Apple", 10.0, 10, 10, 10);
        ServerSocket ss = new ServerSocket(5056);
        Thread time_thread = new Thread(new Server().new timeGoing(clock));
        time_thread.start();
        for (String al : db.startDB()){
            clock.insertAlarm(new Time(Integer.valueOf(al.substring(6)), Integer.valueOf(al.substring(3, 5)), Integer.valueOf(al.substring(0, 2))));
        };
        while (true) {
            Socket s = null;

            try {
                // socket object to receive incoming client requests
                s = ss.accept();

                System.out.println("A new client is connected : " + s);

                // obtaining input and out streams
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                System.out.println("Assigning new thread for this client");

                // create a new thread object
                Thread t = new ClientHandler(s, dis, dos, clock, time_thread, db);

                // Invoking the start() method
                t.start();

            } catch (Exception e) {
                assert s != null;
                s.close();
                e.printStackTrace();
            } finally {
                db.closeDB();
            }
        }

    }

    class timeGoing implements Runnable {
        ClocksExtended clock;
        public timeGoing(ClocksExtended clock) {
            this.clock = clock;
        }
        @Override
        public void run() {
            try {
                while (true) {
                    clock.startTime(1);
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}


class ClientHandler extends Thread
{
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;
    ClocksExtended clock;
    Thread time_thread;
    AlarmsDB db;
    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos, ClocksExtended clock, Thread time_thread, AlarmsDB db)
    {   this.clock = clock;
        this.db = db;
        this.time_thread=time_thread;
        this.s = s;
        this.dis = dis;
        this.dos = dos;
    }

    @Override
    public void run() {
        BufferedReader in = null;
        BufferedWriter out = null;
        try {
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));

            while (true) {
                Time tmp = clock.checkAllAlarms(new Time(clock.getSeconds(), clock.getMinutes(), clock.getHours()));
                if (clock.flag ) {
                    if (tmp!=null) {
                        db.removeDB(tmp.hours + ":" + tmp.minutes + ":" + tmp.seconds);
                    }
                    out.write("Alarm " + "\n");
                    out.flush();
                }
                out.write("Time " + clock.getHours().toString() + ":" + clock.getMinutes().toString() + ":" + clock.getSeconds().toString() + "\n");
                out.flush();

                clock.reset();
                String word = in.readLine();
                System.out.println(word);
                if (!word.contains("get_time")) {
                    if (word.contains("rm_alarm"))
                    {
                        word = word.substring(word.indexOf("rm_")-8,word.indexOf("rm_"));
                        System.out.println(word);
                        clock.deleteAlarms(new Time(Integer.valueOf(word.substring(6, 8)), Integer.valueOf(word.substring(3, 5)), Integer.valueOf(word.substring(0, 2))));
                        db.removeDB(word.substring(0,8));
                    }
                    else {
                        word = word.substring(word.indexOf("add_")-8,word.indexOf("add_"));
                    clock.insertAlarm(new Time(Integer.valueOf(word.substring(6, 8)), Integer.valueOf(word.substring(3, 5)), Integer.valueOf(word.substring(0, 2))));
                    db.insertDB(word);
                    }
                }
                if (word.contains("get_alarms")){
                    StringBuilder res = new StringBuilder("alrms ");
                    for (Time cl : clock.getAlarms())
                    {
                        res.append(cl.hours).append(":").append(cl.minutes).append(":").append(cl.seconds).append(" ");
                    }
                    out.write(res.toString());
                    out.flush();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert in != null;
                in.close();
                assert out != null;
                out.close();
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
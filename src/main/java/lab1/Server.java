package lab1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static Socket clientSocket;
    private static ServerSocket server;
    private static BufferedReader in;
    private static BufferedWriter out;

    public static void main(String[] args) {
        try {
            try {
                server = new ServerSocket(8080);
                System.out.println("Server up");
                clientSocket = server.accept();
                try {
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                    ClocksExtended clock = new ClocksExtended("Apple", 10.0, 10, 10, 10);
                    clock.insertAlarm(new Time(10, 10, 20));
                    while (true) {
                        clock.checkAllAlarms(new Time(clock.getHours(), clock.getMinutes(), clock.getSeconds()));
                        if (clock.flag) {
                            out.write("Alarm " + "\n");
                            out.flush();
                        }
                        out.write("Time " + clock.getHours().toString() + ":" + clock.getMinutes().toString() + ":" + clock.getSeconds().toString() + "\n");
                        out.flush();
                        Thread.sleep(1000);
                        clock.startTime(1);
                        clock.reset();
                        String word = in.readLine();
                        System.out.println(word);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    clientSocket.close();
                    in.close();
                    out.close();
                }
            } finally {
                System.out.println("Server down");
                server.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
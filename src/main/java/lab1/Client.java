package lab1;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;

import static java.lang.Thread.sleep;

public class Client
{
    private static boolean alarm = false;
    private static String time = "10";
    private static String alarmLabelText = "";
    private static Thread t;
    static JFrame jFrame;
    static JLabel jLabel;
    static JPanel jPanel;
    static JTextField jtextField;
    static JLabel jTextFieldLabel;
    static JTextField jtextFieldRemove;
    static JLabel alarmLabel;
    static boolean time_flag = false;
    static BufferedWriter out = null;
    static BufferedReader in = null;
    static Socket s = null;
    public static void main(String[] args) throws IOException {
        try {
            InetAddress ip = InetAddress.getByName("localhost");

            s = new Socket(ip, 5056);
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));

            t = new Thread(Client::init);
            t.start();

        int counter = 0;
            while (true) {

                counter++;
                out.write("get_time" + "\n");
                if (counter % 5 == 1) {
                    out.write("get_alarms");
                }
                out.flush();

                String serverWord = in.readLine();
                if (serverWord.contains("Alarm"))
                    alarm = true;
                else {
                    if (serverWord.contains("alrms")) {
                        alarmLabelText = "Current alarms " + serverWord.substring(6, serverWord.indexOf("Time"));
                    } else {
                        time = serverWord;
                    }
                }
                System.out.println(serverWord);
                Thread.sleep(100);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            s.close();
            in.close();
            out.close();
            t.stop();
        }

    }


    static void init(){

        jFrame = new JFrame("Clock");
        jPanel = new JPanel();
        jLabel = new JLabel("11");
        alarmLabel = new JLabel("alarms:");
        jLabel.setBounds(50,100,200,30);
        jPanel.add(jLabel, BorderLayout.NORTH);
        jtextField = new JTextField(20);
        jTextFieldLabel = new JLabel("Add alarm here");
        jtextFieldRemove= new JTextField(20);;
        jPanel.add(jTextFieldLabel, BorderLayout.WEST);
        jPanel.add(jtextField, BorderLayout.CENTER);
        jPanel.add(alarmLabel);

        JButton button = new JButton("Stop");
        button.setBounds(50,100,200,30);
        button.addActionListener(e -> {
            try {
                stop();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        jPanel.add(button);

        JButton button1 = new JButton("Start");
        button1.setBounds(50,100,200,30);
        button1.addActionListener(e -> start());
        jPanel.add(button1);

        JButton button2 = new JButton("Set alarm");
        button2.setBounds(50,100,200,30);
        button2.addActionListener(e -> {
            try {
                addAlarm();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        jPanel.add(button2);

        JButton button3 = new JButton("Rm alarm");
        button3.setBounds(50,100,200,30);
        button3.addActionListener(e -> {
            try {
                removeAlarm();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        jPanel.add(button3);
        jPanel.add(jtextFieldRemove);
        jFrame.add(jPanel);
        jFrame.setSize(300,300);

        jFrame.setVisible(true);
        start();
    }

    public static void start(){

        if (time_flag) {
            return;
        }

        time_flag = true;
        t = new Thread(() -> {
            while(true){
                jLabel.setText(time);
                alarmLabel.setText(alarmLabelText);
                if (alarm) {
                    JOptionPane.showMessageDialog(jPanel,
                            "Alarm");
                    alarm = false;
                }
            }
        });
        t.start();
    }

    public static void stop() throws IOException {
        time_flag = false;
        out.write("stop");
        t.stop();
    }

    public static void addAlarm() throws IOException {
        out.write(jtextField.getText() +"add_alarms" + "\n");
        out.flush();
    }

    public static void removeAlarm() throws IOException {
        out.write(  jtextFieldRemove.getText() + "rm_alarms " +"\n");
        out.flush();
    }
}




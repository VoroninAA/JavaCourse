package lab1;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

    public class Client {
        private static boolean alarm = false;
        private static String time = "10";
        private static Socket clientSocket;
        private static BufferedReader reader;
        private static BufferedReader in;
        private static BufferedWriter out;
        private static Thread t;
        static JFrame jFrame;
        static JLabel jLabel;
        static JPanel jPanel;
        static boolean time_flag = false;
        public static void main(String[] args) {
            try {
                try {
                    clientSocket = new Socket("localhost", 8080);

                    reader = new BufferedReader(new InputStreamReader(System.in));

                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                    out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                    t = new Thread(() -> {
                        init();
                    });
                    t.start();


                    while (true) {


                        out.write("get_time" + "\n");
                        out.flush();

                        String serverWord = in.readLine();
                        if (serverWord.contains("Alarm"))
                            alarm = true;
                        else
                            time = serverWord;
                        System.out.println(serverWord);
                        Thread.sleep(1000);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    clientSocket.close();
                    in.close();
                    out.close();
                    t.stop();
                }
            } catch (IOException e) {
                System.err.println(e);
            }

        }


        static void init(){

            jFrame = new JFrame("Clock");
            jPanel = new JPanel();
            jLabel = new JLabel("11");
            jLabel.setBounds(50,100,200,30);
            jPanel.add(jLabel);

            JButton button = new JButton("Stop");
            button.setBounds(50,100,200,30);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    stop();
                }
            });
            jPanel.add(button);

            JButton button1 = new JButton("Start");
            button1.setBounds(50,100,200,30);
            button1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    start();
                }
            });
            jPanel.add(button1);

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
            t = new Thread(){
                @Override
                public void run(){
                    while(true){
                        jLabel.setText(time);
                        if (alarm) {
                            JOptionPane.showMessageDialog(jPanel,
                                    "<html><h2>Alarm</h2><i>Rings</i>");
                            alarm = false;
                        }
                        try {
                            t.sleep(1000);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            t.start();
        }

        public static void stop(){
            time_flag = false;
            t.stop();
        }
    }




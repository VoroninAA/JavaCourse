package lab1;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GraphicalWindow {
    public static void main(String[] args){
        ClocksExtended clock = new ClocksExtended("Apple", 10.0, 10, 10, 10);

        clock.insertAlarm(new Time(23,0,0));
        new GraphicalWindow(clock);
    }

    Thread t;
    JFrame jFrame;
    JLabel jLabel;
    JPanel jPanel;
    ClocksExtended myClock;
    boolean time_flag = false;

    GraphicalWindow(ClocksExtended clock){
        myClock = clock;
        jFrame = new JFrame("Clock");
        jPanel = new JPanel();
        String time = myClock.getHours().toString() + ":" + myClock.getMinutes().toString() + ":" + myClock.getSeconds().toString();
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

    public void start(){
        if (time_flag) {
            return;
        }
        time_flag = true;
        t = new Thread(){
            @Override
            public void run(){
                try {
                    myClock.setStartTime(22, 59, 55);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                while(true){
                    String time = myClock.getHours().toString() + ":" + myClock.getMinutes().toString() + ":" + myClock.getSeconds().toString();
                    jLabel.setText(time);
                    myClock.checkAllAlarms(new Time(myClock.getHours(),myClock.getMinutes(),myClock.getSeconds()));
                    if (myClock.flag) {
                        JOptionPane.showMessageDialog(jPanel,
                                "<html><h2>Alarm</h2><i>Rings</i>");
                    }
                    try {
                        t.sleep(1000);
                        myClock.startTime(1);
                        myClock.reset();
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

    public void stop(){
        time_flag = false;
        t.stop();
    }
}



package minesweeper;

import javax.swing.*;

public class RunTime extends Thread{
    JLabel time;


    public RunTime(JLabel label){
        time = label;
    }

    public void countDown(){
        long start;
        start = System.currentTimeMillis();
        while (true){
            long time =  System.currentTimeMillis() - start;
            long second = 20 - time/1000;
            this.time.setText(String.valueOf(second));
            if (this.time.getText().equals("0")){
                MainFrame.controller.nextTurn();
            }
        }
    }
}

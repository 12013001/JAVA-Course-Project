package minesweeper;

import components.GridComponent;
import controller.GameController;
import entity.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * 此类的对象是一个计分板容器，通过传入玩家对象，
 * 可以用update()方法实时更新玩家的分数以及失误数。
 */
public class ScoreBoard extends JPanel {

    public void setMineCounter(JLabel mineCounter) {
        this.mineCounter = mineCounter;
    }

    private JLabel mineCounter;
    private JLabel Player;
    private JLabel TurnShow;
    private JLabel Turn;

    ArrayList<Player> playerArrayList = new ArrayList<Player>();
    ArrayList<JLabel> scores = new ArrayList<JLabel>();
//    public int mineLeave = 0;//有问题

    /**
     * 通过进行游戏的玩家来初始化计分板。这里只考虑了两个玩家的情况。
     * 如果想要2-4人游戏甚至更多，请自行修改(建议把所有玩家存在ArrayList)~
     *
     */
    public ScoreBoard(){ }
    public ScoreBoard(ArrayList<Player> players, int xCount, int yCount, int mineCount) {

        this.setLayout(null);
        mineCounter = new JLabel("剩余雷数");
        mineCounter.setSize(100,100);
        mineCounter.setVisible(true);
        this.add(new JLabel("Score Board - "));
        this.setSize(400, 300);
        this.setLocation(1500, 650);
        this.setBackground(Color.WHITE);
        this.add(mineCounter);
        mineCounter.setLocation(1500, 800);
        this.playerArrayList = players;
        Turn = new JLabel();
        Turn.setVisible(true);
        this.add(Turn);
        for (int i = 0; i < playerArrayList.size(); i++){
            JLabel score = new JLabel();
            score.setVisible(true);
            scores.add(score);
            this.add(score);
        }

        TurnShow = new JLabel();
        TurnShow.setVisible(true);
        this.add(TurnShow);



        for (int i = 0; i < playerArrayList.size(); i++){
            scores.get(i).setText(playerArrayList.get(i).getUserName() + String.valueOf(playerArrayList.get(i).getScore()));
            this.add(scores.get(i));
        }

        this.setLayout(new BoxLayout(this, 1));
        update();
        Font myFont = new Font("宋体",Font.BOLD+Font.ITALIC,70);
        this.setFont(myFont);

    }

    public ScoreBoard(ArrayList<Player> players, int xCount, int yCount) {
        mineCounter = new JLabel("剩余雷数");
        mineCounter.setSize(100,100);
        mineCounter.setVisible(true);
        mineCounter.setLocation(1500, 800);
        this.add(new JLabel("Score Board - "));
        this.setSize(yCount * GridComponent.gridSize, 80);
        this.setLocation(0, xCount * GridComponent.gridSize);
        this.add(mineCounter);
        TurnShow = new JLabel();
        TurnShow.setVisible(true);
        this.add(TurnShow);
        this.setBackground(Color.WHITE);

        Turn = new JLabel();
        Turn.setVisible(true);
        this.add(Turn);

        this.playerArrayList = players;
        for (int i = 0; i < playerArrayList.size(); i++){
            JLabel score = new JLabel();
            score.setVisible(true);
            scores.add(score);
            this.add(score);
        }


        for (int i = 0; i < playerArrayList.size(); i++){
            scores.get(i).setText(playerArrayList.get(i).getUserName() + String.valueOf(playerArrayList.get(i).getScore()));
            this.add(scores.get(i));
        }

        this.setLayout(new BoxLayout(this, 1));
        update();

    }


    /**
     * 刷新计分板的数据。
     * 计分板会自动重新获取玩家的分数，并更新显示。
     */
    public void update() {
        TurnShow.setText("现在是"+MainFrame.controller.getOnTurnPlayer().getUserName()+"的回合");
        TurnShow.repaint();
        mineCounter.setText("剩余雷数"+(String.valueOf(MainFrame.mineCount - GridComponent.recoverMine)));
        mineCounter.repaint();
        Turn.setText("回合数："+String.valueOf(GameController.getTurnsMun()));
        Turn.repaint();
        for (int i = 0; i < playerArrayList.size(); i++){
            scores.get(i).setText(String.format("%s : %d score (+ %d mistake)\n", playerArrayList.get(i).getUserName(), playerArrayList.get(i).getScore(), playerArrayList.get(i).getMistake()));
        }
    }
}

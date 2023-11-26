package components;

import entity.GridStatus;
import entity.Player;
import minesweeper.GamePanel;
import minesweeper.MainFrame;
import minesweeper.Music;
import minesweeper.ScoreBoard;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class GridComponent extends BasicComponent {

    public static int gridSize = 50;

    private Music musicFuck = new Music();
    private int row;
    private int col;
    private GridStatus status = GridStatus.Covered;

    public GridStatus getStatus() {
        return status;
    }

    public int getContent() {
        return content;
    }

    public void setStatus(GridStatus status) {
        this.status = status;
    }

    private int content = 0;
    public static int countClick = 0;
    public static int recoverMine = 0;
    public static int openGrid = 0;

    public GridComponent(int x, int y) {
        this.setSize(gridSize, gridSize);
        this.row = x;
        this.col = y;
    }


    @Override
    public void onMouseLeftClicked() {
        if (countClick == 0 && MainFrame.controller.getGamePanel().getChessboard()[row][col] == -1){
            musicFuck.playMusic1("挖石头.WAV");
            while (MainFrame.controller.getGamePanel().getChessboard()[row][col] == -1) {
                MainFrame.controller.getGamePanel().generateChessBoard(MainFrame.controller.getGamePanel().getChessboard().length, MainFrame.controller.getGamePanel().getChessboard()[0].length, MainFrame.controller.getMineMun(MainFrame.controller.getGamePanel().getChessboard()));
                MainFrame.controller.getGamePanel().resetContent(MainFrame.controller.getGamePanel().getChessboard().length, MainFrame.controller.getGamePanel().getChessboard()[0].length, MainFrame.controller.getMineMun(MainFrame.controller.getGamePanel().getChessboard()));
            }
        }
        if (this.status == GridStatus.Covered){
            if (MainFrame.controller.getGamePanel().getChessboard()[row][col] != -1 && MainFrame.controller.getGamePanel().getChessboard()[row][col] != 0) {
                musicFuck.playMusic1("挖石头.WAV");
                this.status = GridStatus.Clicked;
                repaint();
                openGrid++;
            }
            else if (MainFrame.controller.getGamePanel().getChessboard()[row][col] == -1){
                musicFuck.playMusic1("哦.WAV");
                this.status = GridStatus.ClickedOff;
                repaint();
                openGrid++;
                recoverMine++;
                MainFrame.controller.getOnTurnPlayer().costScore();
            }
            else if (MainFrame.controller.getGamePanel().getChessboard()[row][col] == 0){
                musicFuck.playMusic1("挖石头.WAV");
                this.dfs();
            }
            MainFrame.controller.updateScoreboard();
            countClick++;
            if (countClick % MainFrame.controller.getClickTime() == 0){
                MainFrame.controller.nextTurn();
            }
        }
        if (!judgeOver()){
            MainFrame.controller.ifOver = true;

            endTheGame();
        }
        //TODO: 在左键点击一个格子的时候，还需要做什么？
    }

    public void onMouseMiddleClicked() {
        if (this.status == GridStatus.Covered){
            openGrid1(row,col);
        }
    }

    @Override
    public void onMouseRightClicked() {
        if (this.status == GridStatus.Covered){
            if (MainFrame.controller.getGamePanel().getChessboard()[row][col] != -1) {
                musicFuck.playMusic1("史莱姆.WAV");
                this.status = GridStatus.FlagOff;
                repaint();
                openGrid++;
                MainFrame.controller.getOnTurnPlayer().addMistake();
            }
            else if (MainFrame.controller.getGamePanel().getChessboard()[row][col] == -1){
                musicFuck.playMusic1("叮.WAV");
                this.status = GridStatus.Flag;
                repaint();
                openGrid++;
                recoverMine++;
                MainFrame.controller.getOnTurnPlayer().addScore();
            }
            MainFrame.controller.updateScoreboard();
            countClick++;
            if (countClick % MainFrame.controller.getClickTime() == 0){
                MainFrame.controller.nextTurn();
            }
        }
        if (!judgeOver()){
            MainFrame.controller.ifOver = true;
            endTheGame();
        }
        //TODO: 在右键点击一个格子的时候，还需要做什么？
    }

    public void openGrid(int x, int y){//偷偷开格子方法
        GridComponent grid = MainFrame.controller.getGamePanel().getGrid(x, y );
        if (grid.status == GridStatus.Covered) {
            if (MainFrame.controller.getGamePanel().getChessboard()[row][col] == -1){
                ImageIcon picture = new ImageIcon("岩浆c.JPG");
                grid.getGraphics().drawImage(picture.getImage(),0, 0, getWidth() - 1, getHeight() - 1,picture.getImageObserver());
            }
        }
    }

    public void openGrid1(int x, int y){//偷偷开格子方法1
        GridComponent grid = MainFrame.controller.getGamePanel().getGrid(x, y );
        if (grid.status == GridStatus.Covered) {
            if (MainFrame.controller.getGamePanel().getChessboard()[row][col] == 0){
                ImageIcon picture = new ImageIcon("0c.JPG");
                grid.getGraphics().drawImage(picture.getImage(),0, 0, getWidth() - 1, getHeight() - 1,picture.getImageObserver());
            }
            if (MainFrame.controller.getGamePanel().getChessboard()[row][col] == 1){
                ImageIcon picture = new ImageIcon("1c.JPG");
                grid.getGraphics().drawImage(picture.getImage(),0, 0, getWidth() - 1, getHeight() - 1,picture.getImageObserver());
            }
            if (MainFrame.controller.getGamePanel().getChessboard()[row][col] == 2){
                ImageIcon picture = new ImageIcon("2c.JPG");
                grid.getGraphics().drawImage(picture.getImage(),0, 0, getWidth() - 1, getHeight() - 1,picture.getImageObserver());
            }
            if (MainFrame.controller.getGamePanel().getChessboard()[row][col] == 3){
                ImageIcon picture = new ImageIcon("3c.JPG");
                grid.getGraphics().drawImage(picture.getImage(),0, 0, getWidth() - 1, getHeight() - 1,picture.getImageObserver());
            }
            if (MainFrame.controller.getGamePanel().getChessboard()[row][col] == 4){
                ImageIcon picture = new ImageIcon("4c.JPG");
                grid.getGraphics().drawImage(picture.getImage(),0, 0, getWidth() - 1, getHeight() - 1,picture.getImageObserver());
            }
            if (MainFrame.controller.getGamePanel().getChessboard()[row][col] == 5){
                ImageIcon picture = new ImageIcon("5c.JPG");
                grid.getGraphics().drawImage(picture.getImage(),0, 0, getWidth() - 1, getHeight() - 1,picture.getImageObserver());
            }
            if (MainFrame.controller.getGamePanel().getChessboard()[row][col] == 6){
                ImageIcon picture = new ImageIcon("6c.JPG");
                grid.getGraphics().drawImage(picture.getImage(),0, 0, getWidth() - 1, getHeight() - 1,picture.getImageObserver());
            }
            if (MainFrame.controller.getGamePanel().getChessboard()[row][col] == 7){
                ImageIcon picture = new ImageIcon("7c.JPG");
                grid.getGraphics().drawImage(picture.getImage(),0, 0, getWidth() - 1, getHeight() - 1,picture.getImageObserver());
            }
            if (MainFrame.controller.getGamePanel().getChessboard()[row][col] == 8){
                ImageIcon picture = new ImageIcon("8c.JPG");
                grid.getGraphics().drawImage(picture.getImage(),0, 0, getWidth() - 1, getHeight() - 1,picture.getImageObserver());
            }
            if (MainFrame.controller.getGamePanel().getChessboard()[row][col] == -1){
                ImageIcon picture = new ImageIcon("岩浆c.JPG");
                grid.getGraphics().drawImage(picture.getImage(),0, 0, getWidth() - 1, getHeight() - 1,picture.getImageObserver());
            }
        }
    }

    public void cover(int i, int j){
        GridComponent grid = MainFrame.controller.getGamePanel().getGrid(i, j);
        if (true){
            grid.status = GridStatus.Covered;
            repaint();
        }
    }

    public void cover1(int i, int j){
        GridComponent grid = MainFrame.controller.getGamePanel().getGrid(i, j);
        if (grid.status == GridStatus.Covered){
            grid.status = GridStatus.Covered;
            repaint();
        }
    }

    public void draw(Graphics g) {

        if (this.status == GridStatus.Covered) {
            ImageIcon picture = new ImageIcon("草方块.JPG");
            g.drawImage(picture.getImage(),0, 0, getWidth() - 1, getHeight() - 1,picture.getImageObserver());
        }
        if (this.status == GridStatus.Clicked) {
            if (MainFrame.controller.getGamePanel().getChessboard()[row][col] == 0){
                ImageIcon picture = new ImageIcon("0.JPG");
                g.drawImage(picture.getImage(),0, 0, getWidth() - 1, getHeight() - 1,picture.getImageObserver());
            }
            if (MainFrame.controller.getGamePanel().getChessboard()[row][col] == 1){
                ImageIcon picture = new ImageIcon("1.JPG");
                g.drawImage(picture.getImage(),0, 0, getWidth() - 1, getHeight() - 1,picture.getImageObserver());
            }
            if (MainFrame.controller.getGamePanel().getChessboard()[row][col] == 2){
                ImageIcon picture = new ImageIcon("2.JPG");
                g.drawImage(picture.getImage(),0, 0, getWidth() - 1, getHeight() - 1,picture.getImageObserver());
            }
            if (MainFrame.controller.getGamePanel().getChessboard()[row][col] == 3){
                ImageIcon picture = new ImageIcon("3.JPG");
                g.drawImage(picture.getImage(),0, 0, getWidth() - 1, getHeight() - 1,picture.getImageObserver());
            }
            if (MainFrame.controller.getGamePanel().getChessboard()[row][col] == 4){
                ImageIcon picture = new ImageIcon("4.JPG");
                g.drawImage(picture.getImage(),0, 0, getWidth() - 1, getHeight() - 1,picture.getImageObserver());
            }
            if (MainFrame.controller.getGamePanel().getChessboard()[row][col] == 5){
                ImageIcon picture = new ImageIcon("5.JPG");
                g.drawImage(picture.getImage(),0, 0, getWidth() - 1, getHeight() - 1,picture.getImageObserver());
            }
            if (MainFrame.controller.getGamePanel().getChessboard()[row][col] == 6){
                ImageIcon picture = new ImageIcon("6.JPG");
                g.drawImage(picture.getImage(),0, 0, getWidth() - 1, getHeight() - 1,picture.getImageObserver());
            }
            if (MainFrame.controller.getGamePanel().getChessboard()[row][col] == 7){
                ImageIcon picture = new ImageIcon("7.JPG");
                g.drawImage(picture.getImage(),0, 0, getWidth() - 1, getHeight() - 1,picture.getImageObserver());
            }
            if (MainFrame.controller.getGamePanel().getChessboard()[row][col] == 8){
                ImageIcon picture = new ImageIcon("8.JPG");
                g.drawImage(picture.getImage(),0, 0, getWidth() - 1, getHeight() - 1,picture.getImageObserver());
            }
        }
        if (this.status == GridStatus.ClickedOff) {
            ImageIcon picture = new ImageIcon("岩浆.JPG");
            g.drawImage(picture.getImage(),0, 0, getWidth() - 1, getHeight() - 1,picture.getImageObserver());
        }
        if (this.status == GridStatus.Flag) {
            ImageIcon picture = new ImageIcon("黑曜石.JPG");
            g.drawImage(picture.getImage(),0, 0, getWidth() - 1, getHeight() - 1,picture.getImageObserver());
        }
        if (this.status == GridStatus.FlagOff) {
            if (MainFrame.controller.getGamePanel().getChessboard()[row][col] == 0){
                ImageIcon picture = new ImageIcon("0.JPG");
                g.drawImage(picture.getImage(),0, 0, getWidth() - 1, getHeight() - 1,picture.getImageObserver());
            }
            if (MainFrame.controller.getGamePanel().getChessboard()[row][col] == 1){
                ImageIcon picture = new ImageIcon("1.JPG");
                g.drawImage(picture.getImage(),0, 0, getWidth() - 1, getHeight() - 1,picture.getImageObserver());
            }
            if (MainFrame.controller.getGamePanel().getChessboard()[row][col] == 2){
                ImageIcon picture = new ImageIcon("2.JPG");
                g.drawImage(picture.getImage(),0, 0, getWidth() - 1, getHeight() - 1,picture.getImageObserver());
            }
            if (MainFrame.controller.getGamePanel().getChessboard()[row][col] == 3){
                ImageIcon picture = new ImageIcon("3.JPG");
                g.drawImage(picture.getImage(),0, 0, getWidth() - 1, getHeight() - 1,picture.getImageObserver());
            }
            if (MainFrame.controller.getGamePanel().getChessboard()[row][col] == 4){
                ImageIcon picture = new ImageIcon("4.JPG");
                g.drawImage(picture.getImage(),0, 0, getWidth() - 1, getHeight() - 1,picture.getImageObserver());
            }
            if (MainFrame.controller.getGamePanel().getChessboard()[row][col] == 5){
                ImageIcon picture = new ImageIcon("5.JPG");
                g.drawImage(picture.getImage(),0, 0, getWidth() - 1, getHeight() - 1,picture.getImageObserver());
            }
            if (MainFrame.controller.getGamePanel().getChessboard()[row][col] == 6){
                ImageIcon picture = new ImageIcon("6.JPG");
                g.drawImage(picture.getImage(),0, 0, getWidth() - 1, getHeight() - 1,picture.getImageObserver());
            }
            if (MainFrame.controller.getGamePanel().getChessboard()[row][col] == 7){
                ImageIcon picture = new ImageIcon("7.JPG");
                g.drawImage(picture.getImage(),0, 0, getWidth() - 1, getHeight() - 1,picture.getImageObserver());
            }
            if (MainFrame.controller.getGamePanel().getChessboard()[row][col] == 8){
                ImageIcon picture = new ImageIcon("8.JPG");
                g.drawImage(picture.getImage(),0, 0, getWidth() - 1, getHeight() - 1,picture.getImageObserver());
            }
//            ImageIcon picture = new ImageIcon("地狱岩.JPG");
//            g.drawImage(picture.getImage(),0, 0, getWidth() - 1, getHeight() - 1,picture.getImageObserver());
        }
    }

    public void setContent(int content) {
        this.content = content;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.printComponents(g);
        draw(g);
    }

    public void dfs(){
        this.status = GridStatus.Clicked;
        this.repaint();
        openGrid++;
        int drow[] = { -1, -1, -1, 0, 0, 1, 1, 1 };
        int dcol[] = { -1, 0, 1, -1, 1, -1, 0, 1 };
        for (int i = 0; i < 8; i++ ){
            int r = this.row + drow[i];
            int c = this.col + dcol[i];
            if (r >= 0 && r < MainFrame.controller.getGamePanel().getChessboard().length
                    && c >= 0 && c < MainFrame.controller.getGamePanel().getChessboard()[0].length
                    && MainFrame.controller.getGamePanel().getChessboard()[r][c] != -1 && MainFrame.controller.getGamePanel().getGrid( r, c).status == GridStatus.Covered){
                if (MainFrame.controller.getGamePanel().getChessboard()[r][c] == 0){
                    MainFrame.controller.getGamePanel().getGrid(r ,c).dfs();
                }
                else {
                    MainFrame.controller.getGamePanel().getGrid(r ,c).status = GridStatus.Clicked;
                    MainFrame.controller.getGamePanel().getGrid(r ,c).repaint();
                    openGrid++;
                }
            }
        }
    }
    public boolean judgeOver(){
        boolean judge = true;
        if (MainFrame.mineCount - recoverMine == 0 || openGrid == MainFrame.xCount*MainFrame.yCount ||
                MainFrame.controller.getHighestScore() - MainFrame.controller.getLowestScore() > MainFrame.mineCount - recoverMine){//有改动
            judge = false;
        }
        return judge;
    }
    public void endTheGame() {
        //MainFrame.music.over();


        JFrame overFrame = new JFrame("游戏结束");
        Dimension size=Toolkit.getDefaultToolkit().getScreenSize();
        int sreenW=size.width;
        int sreenH=size.height;
        overFrame.setSize(sreenW, sreenH);
        overFrame.setLocationRelativeTo(null);
        ImageIcon imageIcon2 = new ImageIcon("背景3.JPG");
        JLabel label = new JLabel(imageIcon2);

        Container container1 = overFrame.getContentPane();
        label.setBounds(0,0,imageIcon2.getIconWidth(),imageIcon2.getIconHeight());
        overFrame.getRootPane().add(label);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setVisible(true);
        container1.add(panel);
        JButton button = new JButton("确定");
        button.setSize(150, 80);
        button.setLocation(850,500);

        JLabel winner = new JLabel();
        if (MainFrame.controller.getWinner() != null) {
            winner.setText(String.format("Game over! Winner is %s!", MainFrame.controller.getWinner().getUserName()));
            winner.repaint();
        } else {
            winner.setText("Dogfall!");
            winner.repaint();
        }
        Font font = new Font("宋体",Font.BOLD+Font.ITALIC,45);
        winner.setFont(font);
        winner.setSize(1000, 400);
        winner.setLocation(500,100);
        panel.add(winner);
        panel.add(button);
        panel.setOpaque(false);
        ((JPanel)container1).setOpaque(false);
        overFrame.setVisible(true);
        winner.setVisible(true);
        overFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        button.addActionListener(e -> {
            System.exit(0);

        });
    }
}

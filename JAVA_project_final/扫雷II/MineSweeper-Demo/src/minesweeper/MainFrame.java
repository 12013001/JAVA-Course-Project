package minesweeper;


import components.GridComponent;
import controller.GameController;
import entity.GridStatus;
import entity.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TimerTask;

public class MainFrame extends JFrame implements ActionListener {
    private JPanel BigPanel;
    private JButton winCondition;
    private JButton gameIntroduction;
    private JButton photo;

    private JFrame input;
    private TextField inputXCount;
    private TextField inputYCount;
    private TextField inputMineCount;
    private JPanel inputPanel;
    private JButton inputConfirm;


    private JButton FirstButton;
    private JTextField InputPlayerNumber;
    private JFrame InputPlayerNumberFrame;
    private JTextField InputMaxClickNum;

    public static GameController controller;
    private JFrame startFrame;
    private JPanel startPanel;
    private JButton newgameButton;
    private JButton readingArchive;
    private JButton exit;


    private JButton Restart1;
    private JButton Restart2;

    public static int xCount;
    public static int yCount;
    public static int mineCount;
    private int playerNumber;
    private int clickMun;
    private JFrame gamestartFrame;
    private JButton choiceButton1;
    private JButton choiceButton2;
    private JButton choiceButton3;
    private JButton choiceButton4;
    private JPanel gamestartPanel;
    private JLabel gamestartLabel;
    private JFrame overFrame;
    private JLabel winner;
    private JLabel mineCounter;
    private Music music = new Music();


    public MainFrame() {
        String source = "卡农.WAV";
        music.playMusic(source);
        startFrame=new JFrame("初始界面");
        startPanel=new JPanel();
        newgameButton=new JButton("新游戏");
        readingArchive=new JButton("读取存档");
        exit=new JButton("退出游戏");
        Container container=startFrame.getContentPane();
        startPanel.setLayout(null);
        startPanel.add(exit);
        startPanel.add(newgameButton);
        startPanel.add(readingArchive);
        newgameButton.setSize(400,120);
        readingArchive.setSize(400,120);
        exit.setSize(400,120);
        newgameButton.setLocation(750,400);
        readingArchive.setLocation(750,600);
        exit.setLocation(750,800);
        Font myfont = new Font("宋体",Font.BOLD+Font.ITALIC,70);
        newgameButton.setFont(myfont);
        readingArchive.setFont(myfont);
        exit.setFont(myfont);
        newgameButton.setContentAreaFilled(false);
        readingArchive.setContentAreaFilled(false);
        exit.setContentAreaFilled(false);
        newgameButton.setBorder(null);
        readingArchive.setBorder(null);
        exit.setBorder(null);


        ImageIcon imageIcon1 = new ImageIcon("背景4.PNG");
        JLabel label1 = new JLabel(imageIcon1);
        startFrame.getLayeredPane().add(label1,new Integer(Integer.MIN_VALUE));
        label1.setBounds(0,0,imageIcon1.getIconWidth(),imageIcon1.getIconHeight());
        Container container1 = startFrame.getContentPane();
        container1.add(startPanel);
        ((JPanel)container1).setOpaque(false);
        Dimension size=Toolkit.getDefaultToolkit().getScreenSize();
        //Toolkit kit = Toolkit.getDefaultToolkit();
        int sreenW=size.width;
        int sreenH=size.height;
        startFrame.setSize(sreenW, sreenH);
        startFrame.setLocationRelativeTo(null);
        startPanel.setOpaque(false);
        startFrame.setVisible(true);
        newgameButton.addActionListener(this::actionPerformed);
        readingArchive.addActionListener(this::actionPerformed);
        exit.addActionListener(this::actionPerformed);



        gamestartFrame = new JFrame("模式选择");
        gamestartFrame.setSize(sreenW, sreenH);
        Container patternChoose = gamestartFrame.getContentPane();
        ImageIcon imageIcon2 =new ImageIcon("");
        JLabel labelIma2 = new JLabel(imageIcon2);
        gamestartFrame.getRootPane().add(labelIma2,new Integer(Integer.MIN_VALUE));
        labelIma2.setBounds(0,0,imageIcon2.getIconWidth(),imageIcon2.getIconHeight());
        gamestartFrame.setLocationRelativeTo(null);
        gamestartFrame.getLayeredPane().add(labelIma2);

        gamestartPanel = new JPanel();
        gamestartPanel.setLayout(null);
        gamestartLabel = new JLabel("请选择难度");
        choiceButton1 = new JButton("简单模式");
        choiceButton2 = new JButton("普通模式");
        choiceButton3 = new JButton("困难模式");
        choiceButton4 = new JButton("自定义模式");

        choiceButton1.setSize(400,120);
        choiceButton2.setSize(400,120);
        choiceButton3.setSize(400,120);
        choiceButton4.setSize(400,120);
        choiceButton1.setLocation(750,200);
        choiceButton2.setLocation(750,400);
        choiceButton3.setLocation(750,600);
        choiceButton4.setLocation(750,800);
        choiceButton1.setFont(myfont);
        choiceButton2.setFont(myfont);
        choiceButton3.setFont(myfont);
        choiceButton4.setFont(myfont);


        patternChoose.add(gamestartPanel);
        ButtonGroup group = new ButtonGroup();
        group.add(choiceButton1);
        group.add(choiceButton2);
        group.add(choiceButton3);
        group.add(choiceButton4);
        gamestartPanel.add(gamestartLabel);
        gamestartPanel.add(choiceButton1);
        gamestartPanel.add(choiceButton2);
        gamestartPanel.add(choiceButton3);
        gamestartPanel.add(choiceButton4);
        patternChoose.add(gamestartPanel);
        ((JPanel)patternChoose).setOpaque(false);
        gamestartPanel.setOpaque(false);
        gamestartFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        choiceButton1.addActionListener(this::actionPerformed);
        choiceButton2.addActionListener(this::actionPerformed);
        choiceButton3.addActionListener(this::actionPerformed);
        choiceButton4.addActionListener(this::actionPerformed);


        //todo: change the count of xCount, yCount and mineCount by passing parameters from constructor
        playerNumber = 2;
        clickMun = 2;
        this.setTitle("MineSweeper");
        this.setLayout(null);
        this.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newgameButton) {
            FirstInput();
        }
        if (e.getSource() == exit) {
            Music music1 = new Music();
            music1.playMusic1("点击左.WAV");
            System.exit(0);
        }
        if (e.getSource() == readingArchive) {
            startFrame.dispose();
            String fileName = JOptionPane.showInputDialog(this, "请输入文件名");
            Music music1 = new Music();
            music1.playMusic1("点击左.WAV");
            Container container = this.getContentPane();
            ImageIcon imageIcon = new ImageIcon("背景2.JPG");
            JLabel label = new JLabel(imageIcon);
            this.getRootPane().add(label);
            label.setBounds(0,0,imageIcon.getIconWidth(),imageIcon.getIconHeight());
            ((JPanel)container).setOpaque(false);
            this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            try {
                readFileData("Test");
            } catch (IOException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
            this.setSize(yCount * GridComponent.gridSize + 20, xCount * GridComponent.gridSize + 200);
            this.setVisible(true);
            cheatButton();
            setClick();
            setRead();
            setRestartButton();
            StopCheatButton();
            WinConditionButton();
            PhotoIntroductionButton();
            IntroductionButton();
            FullScreen();
        }


        if (e.getSource() == choiceButton1) {
            Container container = this.getContentPane();
            ImageIcon imageIcon = new ImageIcon("背景2.JPG");
            JLabel label = new JLabel(imageIcon);
            this.getRootPane().add(label);
            label.setBounds(0,0,imageIcon.getIconWidth(),imageIcon.getIconHeight());
          ((JPanel)container).setOpaque(false);


            Music music1 = new Music();
            music1.playMusic1("点击左.WAV");
            this.setEasy();
            MainFrame.xCount =xCount;
            MainFrame.yCount =yCount;
            MainFrame.mineCount =mineCount;
            gamestartFrame.dispose();
            this.setVisible(true);
            ArrayList<Player> playersF = new ArrayList<Player>();
            for (int i = 0; i < playerNumber; i++) {
                playersF.add(new Player());
            }
            controller = new GameController(playersF);
            controller.setPlayerMun(playerNumber);
            controller.setClickTime(clickMun);
            ScoreBoard scoreBoard = new ScoreBoard(playersF, xCount, yCount, mineCount);
            controller.setScoreBoard(scoreBoard);
            GamePanel GP = new GamePanel(xCount, yCount, mineCount);
            controller.setGamePanel(GP);
            container.add(GP);
            container.add(scoreBoard);
           // ((JPanel)container).setOpaque(false);
          //  GP.setOpaque(false);
           // scoreBoard.setOpaque(false);

            cheatButton();
            setClick();
            setRead();
            setRestartButton();
            StopCheatButton();
            WinConditionButton();
            PhotoIntroductionButton();
            IntroductionButton();
            FullScreen();
        }


        if (e.getSource() == Restart1) {
            Music music1 = new Music();
            music1.playMusic1("点击左.WAV");
            controller.restart1();
        }


        if (e.getSource() == Restart2) {
            Music music1 = new Music();
            music1.playMusic1("点击左.WAV");
            controller.restart2();
        }


        if (e.getSource() == choiceButton2) {
            Container container = this.getContentPane();
            ImageIcon imageIcon = new ImageIcon("背景2.JPG");
            JLabel label = new JLabel(imageIcon);
            this.getRootPane().add(label);
            label.setBounds(0,0,imageIcon.getIconWidth(),imageIcon.getIconHeight());
            ((JPanel)container).setOpaque(false);


            Music music1 = new Music();
            music1.playMusic1("点击左.WAV");
            this.setMiddle();
            MainFrame.xCount =xCount;
            MainFrame.yCount =yCount;
            MainFrame.mineCount =mineCount;
            gamestartFrame.dispose();
            this.setVisible(true);
            ArrayList<Player> playersF = new ArrayList<Player>();
            for (int i = 0; i < playerNumber; i++) {
                playersF.add(new Player());
            }
            controller = new GameController(playersF);
            controller.setPlayerMun(playerNumber);
            controller.setClickTime(clickMun);
            ScoreBoard scoreBoard = new ScoreBoard(playersF, xCount, yCount, mineCount);
            controller.setScoreBoard(scoreBoard);
            GamePanel GP = new GamePanel(xCount, yCount, mineCount);
            controller.setGamePanel(GP);
            container.add(GP);
            container.add(scoreBoard);

            SetImportant(xCount,yCount,mineCount);
            cheatButton();
            setClick();
            setRead();
            setRestartButton();
            FullScreen();
            WinConditionButton();
            PhotoIntroductionButton();
            IntroductionButton();
            StopCheatButton();
        }


        if (e.getSource() == choiceButton3) {
            Container container = this.getContentPane();
            ImageIcon imageIcon = new ImageIcon("背景2.JPG");
            JLabel label = new JLabel(imageIcon);
            this.getRootPane().add(label);
            label.setBounds(0,0,imageIcon.getIconWidth(),imageIcon.getIconHeight());
            ((JPanel)container).setOpaque(false);


            Music music1 = new Music();
            music1.playMusic1("点击左.WAV");
            this.setHard();
            MainFrame.xCount =xCount;
            MainFrame.yCount =yCount;
            MainFrame.mineCount =mineCount;
            gamestartFrame.dispose();
            this.setVisible(true);
            ArrayList<Player> playersF = new ArrayList<Player>();
            for (int i = 0; i < playerNumber; i++) {
                playersF.add(new Player());
            }
            controller = new GameController(playersF);
            controller.setPlayerMun(playerNumber);
            controller.setClickTime(clickMun);
            ScoreBoard scoreBoard = new ScoreBoard(playersF, xCount, yCount, mineCount);
            controller.setScoreBoard(scoreBoard);
            GamePanel GP = new GamePanel(xCount, yCount, mineCount);
            controller.setGamePanel(GP);
            container.add(GP);
            container.add(scoreBoard);
            SetImportant(xCount,yCount,mineCount);
            cheatButton();
            setClick();
            setRead();
            setRestartButton();
            WinConditionButton();
            PhotoIntroductionButton();
            IntroductionButton();
            FullScreen();
            StopCheatButton();
        }


        if (e.getSource() == choiceButton4) {
            Music music1 = new Music();
            music1.playMusic1("点击左.WAV");
            Container container = this.getContentPane();
            ImageIcon imageIcon = new ImageIcon("背景2.JPG");
            JLabel label = new JLabel(imageIcon);
            this.getRootPane().add(label);
            label.setBounds(0,0,imageIcon.getIconWidth(),imageIcon.getIconHeight());
            ((JPanel)container).setOpaque(false);
            gamestartFrame.dispose();
            UserDefined();
            FullScreen();
            StopCheatButton();
            WinConditionButton();
            PhotoIntroductionButton();
            IntroductionButton();
        }

        if (e.getSource() == winCondition){
            JFrame frame = new JFrame("游戏说明");
            Container container = frame.getContentPane();
            ImageIcon imageIcon = new ImageIcon("介绍2.PNG");
            JLabel label = new JLabel(imageIcon);
            label.setVisible(true);
            frame.getRootPane().add(label);
            label.setBounds(0,0,imageIcon.getIconWidth(),imageIcon.getIconHeight());
            ((JPanel)container).setOpaque(false);
            frame.setVisible(true);
            frame.setSize(780,380);
            frame.setLocationRelativeTo(null);

        }
        if (e.getSource() ==gameIntroduction){
            JFrame frame = new JFrame("游戏简介");
            JPanel panel = new JPanel();
            ImageIcon imageIcon = new ImageIcon("介绍1.PNG");
            JLabel label = new JLabel(imageIcon);
            label.setVisible(true);
            panel.add(label);
            panel.setVisible(true);
            label.setBounds(0,0,imageIcon.getIconWidth(),imageIcon.getIconHeight());
            frame.add(panel);
            frame.setVisible(true);

            frame.setSize(780,610);
            frame.setLocationRelativeTo(null);
            }
        if (e.getSource()==photo){
            JFrame frame = new JFrame("图标介绍");
            Container container = frame.getContentPane();
            ImageIcon imageIcon = new ImageIcon("介绍3.PNG");
            JLabel label = new JLabel(imageIcon);
            label.setVisible(true);
            frame.getRootPane().add(label);
            label.setBounds(0,0,imageIcon.getIconWidth(),imageIcon.getIconHeight());
            ((JPanel)container).setOpaque(false);
            frame.setVisible(true);
            frame.setSize(500,700);
            frame.setLocationRelativeTo(null);
        }

        if (e.getSource() == FirstButton) {
            boolean bool1 = false;
            boolean bool2 = false;
            Music music1 = new Music();
            music1.playMusic1("点击左.WAV");
            String text0 = InputMaxClickNum.getText();
            if (text0.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "请输入最大点击次数！", "错误输入", JOptionPane.WARNING_MESSAGE);
            } else {
                String text00 = text0.replaceAll("[0-9]", "");
                if (!(text00.length() == 0)) {
                    JOptionPane.showMessageDialog(null, "请输入正确形式的最大点击次数！（最大点击次数须为1-5）", "错误输入", JOptionPane.WARNING_MESSAGE);
                } else {
                    int number = Integer.parseInt(text0);
                    if (number < 1 || number > 5) {
                        JOptionPane.showMessageDialog(null, "请输入正确形式的最大点击次数！（最大点击次数须为1-5）", "错误输入", JOptionPane.WARNING_MESSAGE);
                    } else {
                        bool1 = true;
                        this.clickMun = Integer.parseInt(text0);
                    }
                }
            }
            String text = InputPlayerNumber.getText();
            if (text.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "请输入正确形式的玩家个数！(玩家个数须为2-4)！", "错误输入", JOptionPane.WARNING_MESSAGE);
            } else {
                String text1 = text.replaceAll("[1-9]", "");
                if (!(text1.length() == 0)) {
                    JOptionPane.showMessageDialog(null, "请输入正确形式的玩家个数！(玩家个数须为2-4)", "错误输入", JOptionPane.WARNING_MESSAGE);
                } else {
                    this.playerNumber = Integer.parseInt(text.trim());
                    if (playerNumber > 4 || playerNumber == 1) {
                        JOptionPane.showMessageDialog(null, "请输入正确形式的玩家个数！(玩家个数须为2-4)", "错误输入", JOptionPane.WARNING_MESSAGE);
                    } else {
                        bool2 = true;
                    }
                }
            }
            if (bool1&&bool2){
                InputPlayerNumberFrame.dispose();
                gamestartFrame.setVisible(true);
            }
        }


        if (e.getSource() == inputConfirm) {
            boolean temp1 = false;
            boolean temp2 = false;
            boolean temp3 = false;
            String X = inputXCount.getText();
            if (X.trim().length() == 0) {
                JOptionPane.showMessageDialog(null, "请输入正确形式的Xcount！", "错误输入", JOptionPane.WARNING_MESSAGE);
            } else {
                String XReplace = X.replaceAll("[0-9]", "");
                if (!(XReplace.length() == 0)) {
                    JOptionPane.showMessageDialog(null, "请输入正确形式的Xcount！", "错误输入", JOptionPane.WARNING_MESSAGE);
                } else {
                    int XCountInt = Integer.parseInt(X);
                    if (XCountInt < 30 && XCountInt > 0) {
                        xCount = XCountInt;
                        temp1 = true;
                    } else {
                        JOptionPane.showMessageDialog(null, "请输入正确新式的Xcount！", "错误输入", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
            String Y = inputYCount.getText();
            if (Y.trim().length() == 0) {
                JOptionPane.showMessageDialog(null, "请输入正确形式的Ycount！", "错误输入", JOptionPane.WARNING_MESSAGE);
            } else {
                String YReplace = Y.replaceAll("[0-9]", "");
                if (!(YReplace.length() == 0)) {
                    JOptionPane.showMessageDialog(null, "请输入正确形式的Ycount！", "错误输入", JOptionPane.WARNING_MESSAGE);
                } else {
                    int YCountInt = Integer.parseInt(Y);
                    if (YCountInt < 24 && YCountInt > 0) {
                        yCount = YCountInt;
                        temp2 = true;
                    } else {
                        JOptionPane.showMessageDialog(null, "请输入正确形式的Ycount！", "错误输入", JOptionPane.WARNING_MESSAGE);
                    }
                }

            }
            int dreamMineNumber = (xCount*yCount)/2;
            String MineCount = inputMineCount.getText();
            if (MineCount.trim().length() == 0) {
                JOptionPane.showMessageDialog(null, "请输入正确形式的Minecount！", "错误输入", JOptionPane.WARNING_MESSAGE);
            } else {
                String MineCountReplace = MineCount.replaceAll("[0-9]", "");
                if (!(MineCountReplace.length() == 0)) {
                    JOptionPane.showMessageDialog(null, "请输入正确形式的Minecount！（不能超过xCount与yCount乘积的一半）", "错误输入", JOptionPane.WARNING_MESSAGE);
                } else {
                    int MinecountCountInt = Integer.parseInt(MineCount);
                    if (MinecountCountInt < dreamMineNumber && MinecountCountInt > 0) {
                        mineCount = MinecountCountInt;
                        temp3 = true;
                    } else {
                        JOptionPane.showMessageDialog(null, "请输入正确形式的Minecount！（不能超过xCount与yCount乘积的一半）", "错误输入", JOptionPane.WARNING_MESSAGE);
                    }
                }

            }
            if (temp1 && temp2 && temp3) {
                input.dispose();
                SetImportant(xCount,yCount,mineCount);
                cheatButton();
                setClick();
                setRead();
                setRestartButton();
                FullScreen();
                this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            }

        }
    }

    private void setEasy(){
        int temp1 =9;
        int temp2=10;
        this.xCount = temp1;
        this.yCount = temp1;
        this.mineCount = temp2;
    }
    private void setMiddle(){
        int temp1 =16;
        int temp2 =40;
        this.xCount = temp1;
        this.yCount = temp1;
        this.mineCount = temp2;
    }
    private void setHard(){
        int temp1 =16;
        int temp2 =30;
        int temp3 =99;
        this.xCount = temp1;
        this.yCount = temp2;
        this.mineCount = temp3;
    }


    public void readFileData(String fileName) throws IOException {
        BufferedReader in=new BufferedReader(new FileReader("Test.txt"));
        ArrayList<String> s = new ArrayList<String>();
        String part = null;
        while ((part = in.readLine()) != null){
            s.add(part);
        }
        in.close();

        gamestartFrame.dispose();
        this.setVisible(true);
        int line = 0;
        this.xCount = Integer.parseInt(s.get(line));
        line++;
        this.yCount = Integer.parseInt(s.get(line));
        line++;
        this.mineCount = Integer.parseInt(s.get(line));
        line++;
        this.playerNumber = Integer.parseInt(s.get(line));
        line++;
        this.clickMun = Integer.parseInt(s.get(line));
        line++;
        controller = new GameController();
        controller.setPlayerMun(playerNumber);
        int[][] board = new int[xCount][yCount];
        for (int i = 0; i < xCount; i++){
            for (int j = 0; j < yCount; j++){
                board[i][j] = Integer.parseInt(s.get(line));
                line++;
            }
        }
        GridComponent[][] grids = new GridComponent[xCount][yCount];
        for (int i = 0; i < xCount; i++){
            for (int j = 0; j < yCount; j++){
                GridStatus status = null;
                if (Integer.parseInt(s.get(line+1)) == 0){
                    status = GridStatus.Covered;
                }
                if (Integer.parseInt(s.get(line+1)) == 1){
                    status = GridStatus.Flag;
                }
                if (Integer.parseInt(s.get(line+1)) == 2){
                    status = GridStatus.Clicked;
                }
                if (Integer.parseInt(s.get(line+1)) == 3){
                    status = GridStatus.FlagOff;
                }
                if (Integer.parseInt(s.get(line+1)) == 4){
                    status = GridStatus.ClickedOff;
                }
                grids[i][j] = new GridComponent(i,j);
                grids[i][j].setContent(Integer.parseInt(s.get(line)));
                grids[i][j].setStatus(status);
                line = line + 2;
            }
        }
        GamePanel GP = new GamePanel(xCount, yCount, mineCount);
        GP.generateChessBoard(board);
        ArrayList<Player> players = new ArrayList<Player>();
        for (int i = 0; i < playerNumber; i++){
            Player player = new Player(s.get(line), Integer.parseInt(s.get(line + 1)));
//            player.setMistake(Integer.parseInt(s.get(line + 2)));////////////////////////////////////////////////
            players.add(player);
            line = line + 2;
        }
        controller.setPlayers(players);
        controller.setClickTime(clickMun);



        Player player1 = new Player(s.get(line), Integer.parseInt(s.get(line + 1)));
        controller.setOnTurn(player1);
        line = line +2;

        controller.setHighestScore(Integer.parseInt(s.get(line)));
        line++;


        GameController.setChangeTurnMun(Integer.parseInt(s.get(line)));
        line++;

        GameController.setTurnsMun(Integer.parseInt(s.get(line)));
        line++;

        GridComponent.countClick = Integer.parseInt(s.get(line));
        line++;

        GridComponent.recoverMine = Integer.parseInt(s.get(line));
        line++;

        GridComponent.openGrid = Integer.parseInt(s.get(line));
        ScoreBoard scoreBoard=new ScoreBoard(controller.getPlayers(),xCount,yCount,mineCount);//jifenbbanmeiwt
        controller.setScoreBoard(scoreBoard);
        controller.setGamePanel(GP);
        MainFrame.controller.getGamePanel().generateChessBoard(board);
        MainFrame.controller.getGamePanel().resetContent(xCount,yCount,mineCount);
        for (int i = 0; i < MainFrame.controller.getGamePanel().getMineField().length; i++){
            for (int j = 0; j < MainFrame.controller.getGamePanel().getMineField()[0].length; j++){
                MainFrame.controller.getGamePanel().getMineField()[i][j].setStatus(grids[i][j].getStatus());
                MainFrame.controller.getGamePanel().getMineField()[i][j].repaint();
            }
        }
        MainFrame.controller.updateScoreboard();

//        MainFrame.controller.setOnTurn(controller.getOnTurnPlayer());
//        MainFrame.controller.setHighestScore(controller.getHighestScore());



        this.add(scoreBoard);
        this.add(GP);
        this.setSize(yCount * GridComponent.gridSize + 20, xCount * GridComponent.gridSize + 200);


        //todo: read date from file
    }

    public void cheatButton(){
        JButton button= new JButton("作弊");
        button.setVisible(true);
        button.setSize(150,80);
        button.setLocation(1600,50 );
        this.add(button);
        button.addActionListener(e -> {
            controller.getGamePanel().setMineField(controller.cheat(controller.getGamePanel().getMineField()));
        });
    }
    public void StopCheatButton(){
        JButton button = new JButton("取消作弊");
        button.setVisible(true);
        button.setSize(150,80);
        button.setLocation(1600,150 );
        this.add(button);
        button.addActionListener(e -> {
            controller.closeCheat();
        });
    }



    public void setClick(){
        JButton clickBtn = new JButton("存档");
        clickBtn.setSize(150, 80);
//        clickBtn.setLocation(5, xCount * 59);
        clickBtn.setLocation(1600, 250);
        add(clickBtn);
        clickBtn.addActionListener(e -> {
            String fileName = JOptionPane.showInputDialog(this, "保存文件名");
            try {
                controller.writeDataToFile(fileName);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void setRead(){
        JButton clickBtn = new JButton("读取");
        clickBtn.setSize(150, 80);
        clickBtn.setLocation(1600, 350);
        add(clickBtn);
        clickBtn.addActionListener(e -> {
            String fileName = JOptionPane.showInputDialog(this, "读取文件名");
            try {
                this.dispose();
                readFileData(fileName);
                FullScreen();
            } catch (IOException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        });
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    public void FullScreen (){
        Dimension size=Toolkit.getDefaultToolkit().getScreenSize();
        Toolkit kit = Toolkit.getDefaultToolkit();
        int sreenW=size.width;
        int sreenH=size.height;
        this.setSize(sreenW, sreenH);
        this.setLocationRelativeTo(null);
    }
    public void UserDefined(){
        input = new JFrame("参数输入");
        inputPanel = new JPanel();

        JLabel labelX = new JLabel("请在这里输入Xcount");
        JLabel labelY = new JLabel("请在这里输入Ycount");
        JLabel labelMine = new JLabel("请在这里输入雷数");


        ImageIcon imageIcon = new ImageIcon("背景3.JPG");
        JLabel label11 = new JLabel(imageIcon);
        input.getRootPane().add(label11,new Integer(Integer.MIN_VALUE));
        label11.setBounds(0,0,imageIcon.getIconWidth(),imageIcon.getIconHeight());
        Container container1 = input.getContentPane();

//        ((JPanel)container1).setOpaque(false);

        ;
        inputXCount = new TextField();
        inputXCount.setVisible(true);
        inputYCount = new TextField();
        inputYCount.setVisible(true);
        inputMineCount = new TextField();
        inputMineCount.setVisible(true);

        inputPanel.setLayout(null);
        inputPanel.setVisible(true);
        inputConfirm = new JButton("确定");
        inputConfirm.setVisible(true);


        labelX.setSize(400,80);
        labelY.setSize(400,80);
        labelMine.setSize(400,80);
        labelX.setLocation(300,100);
        labelY.setLocation(300,250);
        labelMine.setLocation(300,400);

        Dimension size=Toolkit.getDefaultToolkit().getScreenSize();
        int sreenW=size.width;
        int sreenH=size.height;
        input.setSize(sreenW, sreenH);
        input.setLocationRelativeTo(null);
        input.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Font myfont = new Font("宋体",Font.BOLD+Font.ITALIC,40);
        labelX.setFont(myfont);
        labelY.setFont(myfont);
        labelMine.setFont(myfont);


        input.setVisible(true);
        inputXCount.setSize(200,40);
        inputXCount.setLocation(800,100);
        inputXCount.setBackground(Color.WHITE);
        inputYCount.setBackground(Color.WHITE);
        inputMineCount.setBackground(Color.WHITE);
        inputYCount.setSize(200,40);
        inputYCount.setLocation(800,250);
        inputMineCount.setSize(200,40);
        inputMineCount.setLocation(800,400);
        inputConfirm.setSize(200, 60);
        inputConfirm.setLocation(820,600);
        inputConfirm.addActionListener(this);
        inputPanel.add(labelX);
        inputPanel.add(inputXCount);
        inputPanel.add(labelY);
        inputPanel.add(inputYCount);
        inputPanel.add(labelMine);
        inputPanel.add(inputMineCount);
        inputPanel.add(inputConfirm);
        container1.add(inputPanel);
        ((JPanel)container1).setOpaque(false);
        inputPanel.setOpaque(false);


    }
    public void FirstInput(){
        startFrame.dispose();
        Music music1 = new Music();
        music1.playMusic1("点击左.WAV");
        JPanel panel = new JPanel();
       // startFrame.setLayout(null);
        panel.setLayout(null);
        InputPlayerNumberFrame = new JFrame("输入玩家个数和每回合最大点击次数");
        JLabel label1 = new JLabel("输入玩家个数");
        label1.setSize(400,100);
        label1.setVisible(true);
        label1.setLocation(700,300);
        Font myfont = new Font("宋体",Font.BOLD+Font.ITALIC,40);
        label1.setFont(myfont);
        JLabel label2 = new JLabel("输入最大点击个数");
        label2.setVisible(true);
        label2.setSize(400,100);
        label2.setLocation(700,450);
        label2.setFont(myfont);
        InputMaxClickNum = new JTextField();
        InputMaxClickNum.setVisible(true);
        InputMaxClickNum.setSize(200,50);
        InputMaxClickNum.setLocation(1300,450);
        InputMaxClickNum.setBackground(Color.WHITE);

        FirstButton = new JButton("确定");
        FirstButton.setSize(150,80);
        FirstButton.setLocation(890,650);
        FirstButton.setVisible(true);
        Dimension size=Toolkit.getDefaultToolkit().getScreenSize();
        //Toolkit kit = Toolkit.getDefaultToolkit();
        int sreenW=size.width;
        int sreenH=size.height;
        InputPlayerNumberFrame.setSize(sreenW, sreenH);
        InputPlayerNumberFrame.setVisible(true);
        InputPlayerNumber = new JTextField();
        InputPlayerNumber.setVisible(true);
        InputPlayerNumber.setSize(200,50);
        InputPlayerNumber.setLocation(1300,300);
        InputPlayerNumber.setBackground(Color.WHITE);




        Container container = InputPlayerNumberFrame.getContentPane();
        ImageIcon imageIcon = new ImageIcon("背景3.JPG");
        JLabel label = new JLabel(imageIcon);
        label.setBounds(0,0,imageIcon.getIconWidth(),imageIcon.getIconHeight());
        InputPlayerNumberFrame.getRootPane().add(label);





        panel.setSize(sreenW, sreenH);
        panel.setVisible(true);
        panel.add(label1);
        panel.add(InputPlayerNumber);
        panel.add(label2);
        panel.add(InputMaxClickNum);
        panel.add(FirstButton);
        container.add(panel);
        ((JPanel)container).setOpaque(false);
        panel.setOpaque(false);

        FirstButton.addActionListener(this::actionPerformed);
        InputPlayerNumberFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
    public void setRestartButton(){
        Restart1 = new JButton("重启1");
        Restart2 = new JButton("重启2");
        Restart1.setSize(150, 80);
        Restart2.setLocation(1600, 450);
        Restart2.setSize(150, 80);
        Restart1.setLocation(1600, 550);
        Restart1.setVisible(true);
        Restart2.setVisible(true);
        this.add(Restart1);
        this.add(Restart2);
        Restart1.addActionListener(this);
        Restart2.addActionListener(this);
    }
    public void SetImportant(int xCount,int yCount,int mineCount){
        MainFrame.xCount =xCount;
        MainFrame.yCount =yCount;
        MainFrame.mineCount =mineCount;
        gamestartFrame.dispose();
        this.setVisible(true);
        ArrayList<Player> playersF = new ArrayList<Player>();
        for (int i = 0; i < playerNumber; i++) {
            playersF.add(new Player());
        }
        controller = new GameController(playersF);
        controller.setPlayerMun(playerNumber);
        controller.setClickTime(clickMun);
        ScoreBoard scoreBoard = new ScoreBoard(playersF, xCount, yCount, mineCount);
        controller.setScoreBoard(scoreBoard);
        GamePanel GP = new GamePanel(xCount, yCount, mineCount);
        controller.setGamePanel(GP);
        this.add(scoreBoard);
        this.add(GP);
//        this.setSize(yCount * GridComponent.gridSize + 20, xCount * GridComponent.gridSize + 200);
    }
    public void IntroductionButton(){
        gameIntroduction = new JButton("游戏简介");
        gameIntroduction.setVisible(true);
        gameIntroduction.setSize(150,80);
        gameIntroduction.setLocation(1760,250);
        this.add(gameIntroduction);
        gameIntroduction.addActionListener(this);


    }
    public void WinConditionButton(){
        winCondition = new JButton("获胜条件");
        winCondition.setVisible(true);
        winCondition.setSize(150,80);
        winCondition.setLocation(1760,150);
        this.add(winCondition);
        winCondition.addActionListener(this);
    }
    public void PhotoIntroductionButton(){
        photo = new JButton("图标介绍");
        photo.setVisible(true);
        photo.setSize(150,80);
        photo.setLocation(1760,350);
        this.add(photo);
        photo.addActionListener(this);

    }

}
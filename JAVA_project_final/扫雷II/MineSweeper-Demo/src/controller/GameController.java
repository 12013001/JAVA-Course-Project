package controller;

import components.GridComponent;
import entity.GridStatus;
import minesweeper.GamePanel;
import entity.Player;
import minesweeper.MainFrame;
import minesweeper.ScoreBoard;
import sun.applet.Main;

import java.io.BufferedReader;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;


public class GameController {

    public ArrayList<Player> players = new ArrayList<Player>();
    public void addPlayer(Player player){
        players.add(player);
    }
    public ArrayList<Player> getPlayers(){
        return players;
    }

    public Player getWinner() {
        return winner;
    }

    private Player winner;
    private Player loser;
    private ArrayList<Player> Dogfalls = new ArrayList<Player>();
    private int highestScore = 0;

    public int getHighestScore() {
        return highestScore;
    }

    public int getLowestScore() {
        return lowestScore;
    }

    private int lowestScore = 0;
    private Player onTurn;


    public boolean isIfOver() {
        return ifOver;
    }

    public void setIfOver(boolean ifOver) {
        this.ifOver = ifOver;
    }

    public boolean ifOver = false;

    public void setHighestScore(int highestScore) {
        this.highestScore = highestScore;
    }

    public void setOnTurn(Player onTurn) {
        this.onTurn = onTurn;
    }

    public static void setChangeTurnMun(int changeTurnMun) {
        GameController.changeTurnMun = changeTurnMun;
    }

    public static void setTurnsMun(int turnsMun) {
        GameController.turnsMun = turnsMun;
    }

    private GamePanel gamePanel;

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    private ScoreBoard scoreBoard;

    public int getClickTime() {
        return clickTime;
    }

    public void setClickTime(int clickTime) {
        this.clickTime = clickTime;
    }

    private int clickTime;

    public int getPlayerMun() {
        return playerMun;
    }

    public void setPlayerMun(int playerMun) {
        this.playerMun = playerMun;
    }

    private int playerMun;
    private static int changeTurnMun = 0;

    public static int getTurnsMun() {
        return turnsMun;
    }

    private static int turnsMun = 1;


    public GameController(){}
    public GameController(ArrayList<Player> players) {
        this.init(players);
        this.onTurn = players.get(0);
    }

    /**
     * 初始化游戏。在开始游戏前，应先调用此方法，给予游戏必要的参数。
     *
     */
    public void init(ArrayList<Player> players) {
        
        this.players = players;
        this.onTurn = players.get(0);
        //TODO: 在初始化游戏的时候，还需要做什么？
    }

    /**
     * 进行下一个回合时应调用本方法。
     * 在这里执行每个回合结束时需要进行的操作。
     * <p>
     * (目前这里没有每个玩家进行n回合的计数机制的，请自行修改完成哦~）
     */

    public void nextTurn() {
        changeTurnMun++;
        int turn = 0;
        for (int i = 0; i < players.size(); i++){
            if (players.get(i) == onTurn){
                turn = i;
            }
        }
        if (turn != players.size()-1){
            onTurn = players.get(turn+1);
        }
        else {
            onTurn = players.get(0);
        }

        scoreBoard.update();
        ////////////////////////////////////////////////////////////////////////////
        this.winner = searchWinner();
        this.loser = searchLoser(players);
        ///////////////////////////////////////////////////////////////////////////////////////
        if ((highestScore - loser.getScore()) > MainFrame.mineCount - GridComponent.recoverMine){
            this.ifOver = true;
        }
        if (changeTurnMun % playerMun == 0){
            turnsMun++;
            this.winner = searchWinner();
            this.loser = searchLoser(players);
            if ((highestScore - loser.getScore()) > getMineMun(MainFrame.controller.getGamePanel().getChessboard()) - GridComponent.recoverMine){
                this.ifOver = true;
//                System.out.println("结束");
//                if (winner != null){
//                    System.out.printf("%s is the winner!\n", winner);
//                }
//                else {
//                    StringBuilder T = new StringBuilder();
//                    T.append("Dogfall! ");
//                    for (int i = 0; i < Dogfalls.size(); i++){
//                        T.append(" ").append(Dogfalls.get(i).getUserName()).append(" ");
//                        System.out.println(T.toString());
//                    }
//                    System.out.println(T.toString());
//                }
            }
        }
        //TODO: 在每个回合结束的时候，还需要做什么 (例如...检查游戏是否结束？)
    }

    public Player searchWinner(){
        Player winner = players.get(0);
        for (int i = 0; i < players.size(); i++){
            if (players.get(i).getScore() > winner.getScore()){
                winner = players.get(i);
            }
        }
        highestScore = winner.getScore();
        int count = 1;
        Dogfalls = new ArrayList<Player>();
        Dogfalls.add(winner);
        for (int i = 0; i < players.size(); i++){
            if (!players.get(i).getUserName().equals(winner.getUserName())){
                if (players.get(i).getScore() == winner.getScore()){
                    Dogfalls.add(players.get(i));
                    count++;
                }
            }
        }
        if (Dogfalls.size() > 1){
            for (int i = 0; i < Dogfalls.size(); i++){
                if (Dogfalls.get(i).getMistake() < winner.getMistake()){
                    ArrayList<Player> temp = Dogfalls;
                    winner = temp.get(i);
                    Dogfalls.remove(winner);
                    count--;
                }
            }
        }
        if (count > 1){
            return null;
        }
        return winner;
    }

    public Player searchLoser(ArrayList<Player> players){
        Player loser = players.get(0);
        for (int i = 0; i < players.size(); i++){
            if (players.get(i).getScore() < loser.getScore()){
                loser = players.get(i);
            }
        }
        lowestScore = loser.getScore();
        return loser;
    }

    /**
     * 获取正在进行当前回合的玩家。
     *
     * @return 正在进行当前回合的玩家
     */
    public Player getOnTurnPlayer() {
        return onTurn;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public ScoreBoard getScoreBoard() {
        return scoreBoard;
    }

    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setScoreBoard(ScoreBoard scoreBoard) {
        this.scoreBoard = scoreBoard;
    }

    public void writeDataToFile(String fileName) throws IOException {
        BufferedWriter writer=new BufferedWriter(new FileWriter("Test.txt"));
        StringBuilder T = new StringBuilder();
        T.append(gamePanel.getChessboard().length).append("\n");//棋盘行数
        T.append(gamePanel.getChessboard()[0].length).append("\n");//棋盘列
        T.append(getMineMun(MainFrame.controller.getGamePanel().getChessboard())).append("\n");//雷数
        T.append(playerMun).append("\n");//玩家数
        T.append(clickTime).append("\n");//点击次数
        for (int i = 0; i < gamePanel.getChessboard().length; i++){
            for (int j = 0; j < gamePanel.getChessboard()[0].length; j++){
                T.append(gamePanel.getChessboard()[i][j]).append("\n");
            }
        }//棋盘内部
        for (int i = 0; i < gamePanel.getChessboard().length; i++){
            for (int j = 0; j < gamePanel.getChessboard()[0].length; j++){
                T.append(gamePanel.getMineField()[i][j].getContent()).append("\n");
                if (gamePanel.getMineField()[i][j].getStatus() == GridStatus.Covered){
                    T.append(0).append("\n");
                }
                if (gamePanel.getMineField()[i][j].getStatus() == GridStatus.Flag){
                    T.append(1).append("\n");
                }
                if (gamePanel.getMineField()[i][j].getStatus() == GridStatus.Clicked){
                    T.append(2).append("\n");
                }
                if (gamePanel.getMineField()[i][j].getStatus() == GridStatus.FlagOff){
                    T.append(3).append("\n");
                }
                if (gamePanel.getMineField()[i][j].getStatus() == GridStatus.ClickedOff){
                    T.append(4).append("\n");
                }
            }
        }
        for (int i = 0; i < playerMun; i++){
            T.append(players.get(i).getUserName()).append("\n");
            T.append(players.get(i).getScore()).append("\n");
//            T.append(players.get(i).getMistake()).append("\n");/////////////////////////////////////////////////////////////
        }
        T.append(onTurn.getUserName()).append("\n");
        T.append(onTurn.getScore()).append("\n");
        T.append(highestScore).append("\n");

        T.append(changeTurnMun).append("\n");
        T.append(turnsMun).append("\n");
        T.append(GridComponent.countClick).append("\n");
        T.append(GridComponent.recoverMine).append("\n");
        T.append(GridComponent.openGrid).append("\n");
        writer.write(T.toString());
        writer.close();
        //todo: write data into file
    }

    public static int[][] setChessBoard(int xCount, int yCount, int mineCount){
        int[][] board = new int[xCount][yCount];
        Random r = new Random();
        int haveMine = 0;
        while (haveMine < mineCount){
            int numX = r.nextInt(xCount);
            int numY = r.nextInt(yCount);
            if (board[numX][numY] != -1){
                board[numX][numY] = -1;
                haveMine++;
            }
        }
        int[][] board1 = new int[xCount+2][yCount+2];
        for(int i = 1; i < board1.length-1; i++) {
            for (int j = 1; j < board1[i].length-1; j++) {
                board1[i][j] = board[i-1][j-1];
            }
        }
        for(int i = 1; i < board1.length-1; i++) {
            for (int j = 1; j < board1[i].length-1; j++) {
                if (board1[i][j] != -1){
                    if (board1[i-1][j-1] == -1) {
                        board1[i][j]++;
                    }
                    if (board1[i-1][j] == -1) {
                        board1[i][j]++;
                    }
                    if (board1[i-1][j+1] == -1) {
                        board1[i][j]++;
                    }
                    if (board1[i][j+1] == -1) {
                        board1[i][j]++;
                    }
                    if (board1[i+1][j+1] == -1) {
                        board1[i][j]++;
                    }
                    if (board1[i+1][j] == -1) {
                        board1[i][j]++;
                    }
                    if (board1[i+1][j-1] == -1) {
                        board1[i][j]++;
                    }
                    if (board1[i][j-1] == -1) {
                        board1[i][j]++;
                    }
                }
            }
        }
        for(int i = 1; i < board1.length-1; i++) {
            for (int j = 1; j < board1[i].length-1; j++) {
                board[i-1][j-1] = board1[i][j];
            }
        }
        return board;
    }

    public static int[][] setChessBoard( int[][] board){
        return board;
    }

    public static boolean checkBoard(int[][] board){
        boolean judge = true;
        for(int i = 1; i < board.length-1; i++) {
            for (int j = 1; j < board[i].length-1; j++) {
                if (board[i][j] == -1 && board[i-1][j-1] == -1 && board[i-1][j] == -1 && board[i-1][j+1] == -1 && board[i][j+1] == -1 && board[i+1][j+1] == -1 && board[i+1][j] == -1 && board[i+1][j-1] == -1 && board[i][j-1] == -1){
                    judge = false;
                    break;
                }
            }
            if (!judge){
                break;
            }
        }
        for (int i = 1; i < board.length-1; i++){
            if (board[i][0] == -1 && board[i-1][0] ==-1 && board[i-1][1] ==-1 && board[i][1] == -1 && board[i+1][1] == -1 && board[i+1][0] == -1){
                judge = false;
                break;
            }
            if (board[i][board[0].length-1] == -1 && board[i-1][board[0].length-1] ==-1 && board[i-1][board[0].length-2] ==-1 && board[i][board[0].length-2] == -1 && board[i+1][board[0].length-2] == -1 && board[i+1][board[0].length-1] == -1){
                judge = false;
                break;
            }
        }
        for (int i = 1; i < board[0].length-1; i++){
            if (board[0][i] == -1 && board[0][i-1] ==-1 && board[1][i-1] ==-1 && board[1][i] == -1 && board[1][i+1] == -1 && board[0][i+1] == -1){
                judge = false;
                break;
            }
            if (board[board.length-1][i] == -1 && board[board.length-1][i-1] ==-1 && board[board.length-2][i-1] ==-1 && board[board.length-2][i] == -1 && board[board.length-2][i+1] == -1 && board[board.length-1][i+1] == -1){
                judge = false;
                break;
            }
        }
        if (board[0][0] == -1 && board[0][1] == -1 && board[1][0] == -1 && board[1][1] == -1){
            judge = false;
        }
        if (board[0][board[0].length-1] == -1 && board[0][board[0].length-2] == -1 && board[1][board[0].length-1] == -1 && board[1][board[0].length-2] == -1){
            judge = false;
        }
        if (board[board.length-1][0] == -1 && board[board.length-1][1] == -1 && board[board.length-2][0] == -1 && board[board.length-2][1] == -1){
            judge = false;
        }
        if (board[board.length-1][board[0].length-1] == -1 && board[board.length-1][board[0].length-2] == -1 && board[board.length-2][board[0].length-1] == -1 && board[board.length-2][board[0].length-2] == -1){
            judge = false;
        }
        return judge;
    }

    public void updateScoreboard(){
        scoreBoard.update();
    }

    public GridComponent[][] cheat(GridComponent[][] gridComponents){
        for (int i = 0; i < gridComponents.length; i++){
            for (int j = 0; j < gridComponents[0].length; j++){
                gridComponents[i][j].openGrid( i, j);
            }
        }
        return gridComponents;
    }

    public int getMineMun(int[][] chessboard){
        int num = 0;
        for (int i = 0; i < chessboard.length; i++) {
            for (int j = 0; j < chessboard[0].length; j++) {
                if (chessboard[i][j] == -1){
                    num++;
                }
            }
        }
        return num;
    }

    public void restart1(){
        for (int i = 0; i < gamePanel.getMineField().length; i++){
            for (int j = 0; j < gamePanel.getMineField()[0].length; j++){
                gamePanel.getMineField()[i][j].cover(i,j);
            }
        }
        onTurn=players.get(0);
        for (int i = 0; i < players.size(); i++){
            players.get(i).setScore(0);
        }
        highestScore = 0;
        lowestScore = 0;
        winner = null;
        loser = null;
        Dogfalls = new ArrayList<Player>();
        turnsMun = 0;
        changeTurnMun = 0;
        GridComponent.countClick = 0;
        GridComponent.recoverMine = 0;
        GridComponent.openGrid = 0;
        updateScoreboard();
    }

    public void restart2(){
        gamePanel.generateChessBoard(MainFrame.xCount, MainFrame.yCount, MainFrame.mineCount);
        for (int i = 0; i < gamePanel.getMineField().length; i++){
            for (int j = 0; j < gamePanel.getMineField()[0].length; j++){
                gamePanel.getMineField()[i][j].cover(i,j);
                gamePanel.getMineField()[i][j].setContent(gamePanel.getChessboard()[i][j]);
            }
        }
        onTurn=players.get(0);
        for (int i = 0; i < players.size(); i++){
            players.get(i).setScore(0);
        }
        highestScore = 0;
        lowestScore = 0;
        winner = null;
        loser = null;
        Dogfalls = new ArrayList<Player>();
        turnsMun = 0;
        changeTurnMun = 0;
        GridComponent.countClick = 0;
        GridComponent.recoverMine = 0;
        GridComponent.openGrid = 0;
        updateScoreboard();
    }

    public void closeCheat(){//-------------------------------------------------------关闭作弊
        for (int i = 0; i < gamePanel.getMineField().length; i++){
            for (int j = 0; j < gamePanel.getMineField()[0].length; j++){
                gamePanel.getMineField()[i][j].cover1(i,j);
            }
        }
    }
}

package minesweeper;

import components.GridComponent;
import controller.GameController;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    public void setMineField(GridComponent[][] mineField) {
        this.mineField = mineField;
    }

    public GridComponent[][] getMineField() {
        return mineField;
    }

    private GridComponent[][] mineField;

    public int[][] getChessboard() {
        return chessboard;
    }


    public void setChessboard(int[][] chessboard) {
        this.chessboard = chessboard;
        System.out.println("diaonima");
    }

    private int[][] chessboard;

    /**
     * 初始化一个具有指定行列数格子、并埋放了指定雷数的雷区。
     *
//     * @param xCount    count of grid in column
//     * @param yCount    count of grid in row
//     * @param mineCount mine count
//     */
    public GamePanel(){}
    public GamePanel(int xCount, int yCount, int[][] chessboard, GridComponent[][] mineField){
//        this.setVisible(true);
        this.setFocusable(true);
        this.setLayout(null);
        this.setBackground(Color.WHITE);
        this.setSize(GridComponent.gridSize * yCount, GridComponent.gridSize * xCount);
        this.chessboard = chessboard;
        this.mineField = mineField;
    }

    public GamePanel(int xCount, int yCount, int mineCount) {
//        this.setVisible(true);
        this.setFocusable(true);
        this.setLayout(null);
        this.setBackground(Color.WHITE);
        this.setSize(GridComponent.gridSize * yCount, GridComponent.gridSize * xCount);
        initialGame(xCount, yCount, mineCount);
        repaint();
    }

    public void initialGame(int xCount, int yCount, int mineCount) {//初始化防重雷
        generateChessBoard(xCount, yCount, mineCount);
        mineField = new GridComponent[xCount][yCount];
        for (int i = 0; i < xCount; i++) {
            for (int j = 0; j < yCount; j++) {
                GridComponent gridComponent = new GridComponent(i, j);
                gridComponent.setContent(chessboard[i][j]);
                gridComponent.setLocation(j * GridComponent.gridSize, i * GridComponent.gridSize);
                mineField[i][j] = gridComponent;
                this.add(mineField[i][j]);
            }
        }
        repaint();
    }

    public void resetContent(int xCount, int yCount, int mineCount){
        for (int i = 0; i < xCount; i++) {
            for (int j = 0; j < yCount; j++) {
                GridComponent gridComponent = new GridComponent(i, j);
                gridComponent.setContent(chessboard[i][j]);
            }
        }
    }

    public void generateChessBoard(int xCount, int yCount, int mineCount) {
        chessboard = GameController.setChessBoard(xCount, yCount, mineCount);
        while (!GameController.checkBoard(chessboard)){
            chessboard = GameController.setChessBoard(xCount, yCount, mineCount);
        }
        //todo: generate chessboard by your own algorithm
    }

    public void generateChessBoard(int[][] chessboard) {
        this.chessboard = GameController.setChessBoard(chessboard);
        while (!GameController.checkBoard(this.chessboard)){
            chessboard = GameController.setChessBoard(chessboard);
        }
        //todo: generate chessboard by your own algorithm
    }

    /**
     * 获取一个指定坐标的格子。
     * 注意请不要给一个棋盘之外的坐标哦~
     *
     * @param x 第x列
     * @param y 第y行
     * @return 该坐标的格子
     */
    public GridComponent getGrid(int x, int y) {//要坐标方法
        try {
            return mineField[x][y];
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}

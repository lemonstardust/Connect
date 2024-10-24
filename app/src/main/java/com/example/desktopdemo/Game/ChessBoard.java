package com.example.desktopdemo.Game;


import static com.example.desktopdemo.Game.GameConfig.BLACKCHESS;
import static com.example.desktopdemo.Game.GameConfig.NOCHESS;
import static com.example.desktopdemo.Game.GameConfig.WHITECHESS;

import java.util.Stack;


public class ChessBoard {

    public static char[][] chessBoard = new char[19][19];

    public char currentRole = BLACKCHESS;

    private static Stack<Integer> play_stack = new Stack<>();

    /** 脱离战场范围的范围 */
    private static int xMin = 20;

    private static int xMax = 0;

    private static int yMin = 20;

    private static int yMax = 0;

    public static char[][] getChessBoard() {
        return chessBoard;
    }

    public static void setChessBoard(char[][] chessBoard) {
        ChessBoard.chessBoard = chessBoard;
    }

    public ChessBoard() {
        initboard();
    }

    public static void initboard() {
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 19; j++) {
                chessBoard[i][j] = NOCHESS;
                play_stack.clear();
            }
        }
    }

    public char getCurrentRole() {
        return currentRole;
    }

    public char [][] getChessBoardDate(){
        return chessBoard;
    }

    public static int getAllChessNumber() {
        int count = play_stack.size();
        return count;
    }

    public static char getNextStepChessColor() {
        char chessColor = NOCHESS;
        int chessNum = getAllChessNumber();
        switch (chessNum % 4) {
            case 0:
            case 3:
                chessColor = BLACKCHESS;
                break;
            case 1:
            case 2:
                chessColor = WHITECHESS;
                break;
        }
        return chessColor;
    }

    public static void makeChess(int coord) {
        int x=coord/100;
        int y=coord%100;
        // 向棋局局势数组中添加棋子
        chessBoard[y-1][x-1] = getNextStepChessColor();
        // 向下棋的顺序栈中添加棋子
        play_stack.push(coord);
    }

    /**
     * 移除一个棋子
     */
    public static void unMakeChess() {
        // 从下棋顺序栈中弹出添加的棋子
        int coord = play_stack.pop();
        int x=coord/100;
        int y=coord%100;
        // 从已下棋子hash列表中移除添加的棋子
        chessBoard[y-1][x-1] = NOCHESS;
    }

    /**
     * 执行下一回合走法
     *
     * @param move 一回合走法
     */
    public static void makeNextMove(Move move) {
        for (int coord : move.getCoordArray()) {
            makeChess(coord);
        }
    }

    /**
     * 撤消本回合走法所下棋子
     */
    public static void unMakeMove() {
        for (int i = 0; i < 2; i++) {
            unMakeChess();
        }
    }

    public void addChess(int coord) {
        int x = coord/100;
        int y = coord%100;
        if (x < xMin) {
            xMin = x;
        }
        if (x > xMax) {
            xMax = x;
        }
        if (y < yMin) {
            yMin = y;
        }
        if (y > yMax) {
            yMax = y;
        }
        // 向棋局局势数组中添加棋子
        chessBoard[y -1][x -1] = getNextStepChessColor();
        // 向下棋的顺序栈中添加棋子
        play_stack.push(coord);
        //notifyChessBoardModelEvent(new ChessBoardModelEvent(this, coord));

        char role=getNextStepChessColor();
//		if(currentRole!=role){
//			notifyRoleChangeEvent(new RoleChangeEvent(this));
//		}
        currentRole=role;
    }

    public static int getxMin() {
        return xMin;
    }

    public static int getxMax() {
        return xMax;
    }

    public static int getyMin() {
        return yMin;
    }

    public static int getyMax() {
        return yMax;
    }
}

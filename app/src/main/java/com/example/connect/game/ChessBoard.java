package com.example.connect.game;


import com.example.connect.observe.IChessChangeListener;

import java.util.Stack;


public class ChessBoard {

    private static char[][] chessBoard = new char[19][19];

    private char currentRole = GameConfig.BLACKCHESS;

    private static Stack<Integer> play_stack = new Stack<>();

    private IChessChangeListener countChangeListener;
    /**
     * 脱离战场范围的范围
     */
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
                chessBoard[i][j] = GameConfig.NOCHESS;
                play_stack.clear();
            }
        }
    }

    public char getCurrentRole() {
        return currentRole;
    }

    public char[][] getChessBoardDate() {
        return chessBoard;
    }

    public static int getAllChessNumber() {
        int count = play_stack.size();
        return count;
    }

    public static char getNextStepChessColor() {
        char chessColor = GameConfig.NOCHESS;
        int chessNum = getAllChessNumber();
        switch (chessNum % 4) {
            case 0:
            case 3:
                chessColor = GameConfig.BLACKCHESS;
                break;
            case 1:
            case 2:
                chessColor = GameConfig.WHITECHESS;
                break;
        }
        return chessColor;
    }

    public static void makeChess(int coord) {
        int x = coord / 100;
        int y = coord % 100;
        // 向棋局局势数组中添加棋子
        chessBoard[y - 1][x - 1] = getNextStepChessColor();
        // 向下棋的顺序栈中添加棋子
        play_stack.push(coord);
    }

    /**
     * 移除一个棋子
     */
    public static void unMakeChess() {
        // 从下棋顺序栈中弹出添加的棋子
        int coord = play_stack.pop();
        int x = coord / 100;
        int y = coord % 100;
        // 从已下棋子hash列表中移除添加的棋子
        chessBoard[y - 1][x - 1] = GameConfig.NOCHESS;
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
        int x = coord / 100;
        int y = coord % 100;
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
        chessBoard[y - 1][x - 1] = getNextStepChessColor();
        // 向下棋的顺序栈中添加棋子
        play_stack.push(coord);

        if (countChangeListener != null) {
            countChangeListener.onChessCountChange(play_stack.size());
        }
        char role = getNextStepChessColor();

        if (currentRole != role && countChangeListener != null) {
            countChangeListener.onRoleChange(currentRole);
        }

        currentRole = role;
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

    public void setCountChangeListener(IChessChangeListener countChangeListener) {
        this.countChangeListener = countChangeListener;
    }
}

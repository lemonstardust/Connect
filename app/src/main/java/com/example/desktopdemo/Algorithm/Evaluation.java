package com.example.desktopdemo.Algorithm;


import com.example.desktopdemo.Game.GameConfig;
import com.example.desktopdemo.Game.Road;

import java.util.Vector;

public class Evaluation {

    /** 对路的评分准则，该结果通过遗传算法离线优化得到 */
    public final static int[] SCOREOFROAD = {0, 17, 78, 141, 788, 1030, 10000};

    /** 所有的棋路 */
    public static Vector<Road> allRoads = new Vector<>(924);

    /** 黑子的有效棋路数 */
    public static int[] numberOfBlackRoad = new int[7];

    /** 白子的有效棋路数 */
    public static int[] numberOfWhiteRoad = new int[7];


    public static void checkChessBoardStatus(char[][] chessboard) {
        allRoads = new Vector<>(924);
        numberOfBlackRoad = new int[7];
        numberOfWhiteRoad = new int[7];
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 14; j++) {
                Vector<Character> horizontal = new Vector<>(6);
                horizontal.add(chessboard[i][j]);
                horizontal.add(chessboard[i][j+1]);
                horizontal.add(chessboard[i][j+2]);
                horizontal.add(chessboard[i][j+3]);
                horizontal.add(chessboard[i][j+4]);
                horizontal.add(chessboard[i][j+5]);
                Road hR = new Road(horizontal);
                allRoads.add(hR);
                Vector<Character> vertical = new Vector<>(6);
                vertical.add(chessboard[j][i]);
                vertical.add(chessboard[j+1][i]);
                vertical.add(chessboard[j+2][i]);
                vertical.add(chessboard[j+3][i]);
                vertical.add(chessboard[j+4][i]);
                vertical.add(chessboard[j+5][i]);
                Road vR = new Road(vertical);
                allRoads.add(vR);
            }
        }

        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 14; j++) {
                Vector<Character> leftOblique = new Vector<>(6);
                leftOblique.add(chessboard[i][j]);
                leftOblique.add(chessboard[i+1][j+1]);
                leftOblique.add(chessboard[i+2][j+2]);
                leftOblique.add(chessboard[i+3][j+3]);
                leftOblique.add(chessboard[i+4][j+4]);
                leftOblique.add(chessboard[i+5][j+5]);
                Road lR = new Road(leftOblique);
                allRoads.add(lR);
                Vector<Character> rightOblique = new Vector<>(6);
                rightOblique.add(chessboard[i][j+5]);
                rightOblique.add(chessboard[i+1][j+4]);
                rightOblique.add(chessboard[i+2][j+3]);
                rightOblique.add(chessboard[i+3][j+2]);
                rightOblique.add(chessboard[i+4][j+1]);
                rightOblique.add(chessboard[i+5][j]);
                Road rR = new Road(rightOblique);
                allRoads.add(rR);
            }
        }

        for(int i=0;i<allRoads.size();i++){
            Road r=allRoads.get(i);
            if (r.getChessColor() == GameConfig.BLACKCHESS) {
                numberOfBlackRoad[r.getValidChessCount()]++;
            }
            if (r.getChessColor() == GameConfig.WHITECHESS) {
                numberOfWhiteRoad[r.getValidChessCount()]++;
            }
        }
    }

    public static int evaluateChessStatus(char chessColor, char[][] chessBoard) {
        int currentScore = 0;
        int blackRoadScore = 0;
        int whiteRoadScore = 0;
        checkChessBoardStatus(chessBoard);

        for (int i = 1; i < 7; i++) {
            blackRoadScore += numberOfBlackRoad[i] * SCOREOFROAD[i];
            whiteRoadScore += numberOfWhiteRoad[i] * SCOREOFROAD[i];
        }
        if (chessColor == GameConfig.BLACKCHESS) {
            currentScore = blackRoadScore - whiteRoadScore;
        }
        if (chessColor == GameConfig.WHITECHESS) {
            currentScore = whiteRoadScore - blackRoadScore;
        }
        return currentScore;
    }


    public static boolean isGameOver(char[][] c){
        boolean result=false;
        checkChessBoardStatus(c);
        if(numberOfBlackRoad[6]>0||numberOfWhiteRoad[6]>0)
            result=true;
        return result;
    }

}

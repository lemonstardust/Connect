package com.example.connect.algorithm;


import com.example.connect.game.ChessBoard;
import com.example.connect.game.GameConfig;
import com.example.connect.game.Move;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;


public class GenerateMoves {


    public static ArrayList<Move> generateMoves(char[][] chessBoard) {
        //DefaultChessBoardModel dcbm = (DefaultChessBoardModel) chessBoardModel;

        ArrayList<Move> moves = new ArrayList<>();
        ArrayList<Integer> step1 = getTopPoint(chessBoard);
        for (Integer coord1 : step1) {
            ChessBoard.makeChess(coord1);
            ArrayList<Integer> step2 = getTopPoint(chessBoard);
            for (Integer coord2 : step2) {
                moves.add(new Move(coord1, coord2));
            }
            ChessBoard.unMakeChess();
        }
        return moves;
    }


    //参数可以设置成ChessBoard chessBoard ，在调用getchesBoard方法获得棋盘数组
    //
    public static ArrayList<Integer> getTopPoint(char[][] chessBoard) {
        ArrayList<Integer> coords = new ArrayList<>(GameConfig.GENERATEMOVESWIDTH);

        char color = ChessBoard.getNextStepChessColor();
        HashMap<Integer, Integer> coordScore = new HashMap<>();
        HashSet<Integer> ReserveCoords = getReserveCoords(chessBoard);
        for (Integer coord : ReserveCoords) {
            ChessBoard.makeChess(coord);
            int score = Evaluation.evaluateChessStatus(color, chessBoard);
            coordScore.put(coord, score);
            ChessBoard.unMakeChess();
        }
        List<Map.Entry<Integer, Integer>> coordScoreList = new ArrayList<Map.Entry<Integer, Integer>>(
                coordScore.entrySet());
        Collections.sort(coordScoreList,
                new Comparator<Map.Entry<Integer, Integer>>() {
                    public int compare(Map.Entry<Integer, Integer> o1,
                                       Map.Entry<Integer, Integer> o2) {
                        return (o2.getValue() - o1.getValue());
                    }
                });
        for (int i = 0; i < GameConfig.GENERATEMOVESWIDTH; i++) {
            coords.add(coordScoreList.get(i).getKey());
        }
        return coords;
    }


    private static HashSet<Integer> getReserveCoords(char[][] chessBoard) {
        HashSet<Integer> ReserveCoords = new HashSet<>();
        char[][] compsitionData = ChessBoard.getChessBoard();

        int xMin = ChessBoard.getxMin() - 2;
        int xMax = ChessBoard.getxMax() + 2;
        int yMin = ChessBoard.getyMin() - 2;
        int yMax = ChessBoard.getyMax() + 2;

        xMin = xMin < 1 ? 1 : xMin;
        xMax = xMax > 19 ? 19 : xMax;
        yMin = yMin < 1 ? 1 : yMin;
        yMax = yMax > 19 ? 19 : yMax;
        for (int i = xMin; i <= xMax; i++) {
            for (int j = yMin; j <= yMax; j++) {
                if (chessBoard[j - 1][i - 1] == GameConfig.NOCHESS) {
                    ReserveCoords.add(i * 100 + j);
                }
            }
        }
        return ReserveCoords;
    }
}

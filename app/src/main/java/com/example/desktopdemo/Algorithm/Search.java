package com.example.desktopdemo.Algorithm;


import com.example.desktopdemo.Game.ChessBoard;
import com.example.desktopdemo.Game.GameConfig;
import com.example.desktopdemo.Game.Move;

import java.util.ArrayList;

public class Search {

    public static Move bestMove=null;

    public static int alphaBeta(char[][] chessBoard,int alpha,int beta,int depth){
        int value,best=Integer.MIN_VALUE;
        //DefaultChessBoardModel dcbm=(DefaultChessBoardModel)chessBoardModel;

        char chessColor= ChessBoard.getNextStepChessColor();
        //如果棋局结束或当前节点为叶子节点则返回评估值
        if(Evaluation.isGameOver(chessBoard)||depth<=0){
            int evaluateScore=Evaluation.evaluateChessStatus(chessColor, chessBoard);
            return evaluateScore;
        }
        ArrayList<Move> moves= GenerateMoves.generateMoves(chessBoard);
        for (Move move : moves) {
            ChessBoard.makeNextMove(move);
            value=-alphaBeta(chessBoard, -beta, -alpha,depth-1);
            ChessBoard.unMakeMove();
            if(value>best){
                best=value;
                if(best>alpha){
                    alpha=best;
                }
                if(value>=beta){
                    break;
                }
            }
            if(depth== GameConfig.AILevel && value>=best){
                bestMove=move;
            }
        }
        return best;
    }

    public static Move getNextMoves(char[][] chessBoard){
        Move move=null;
        alphaBeta(chessBoard, Integer.MIN_VALUE, Integer.MAX_VALUE, GameConfig.AILevel);
        if(bestMove!=null)
            move=bestMove;
        return move;
    }
}

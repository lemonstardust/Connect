package com.example.desktopdemo.Game;

import static com.example.desktopdemo.Game.GameConfig.BLACKCHESS;
import static com.example.desktopdemo.Game.GameConfig.NOCHESS;
import static com.example.desktopdemo.Game.GameConfig.WHITECHESS;

import java.util.Vector;

public class Road {
    private char chessColor=NOCHESS;

    /** 当前有效路中棋子的个数 */
    private int validChessCount=0;

    /** 当前棋路中棋子的信息 */
    private Vector<Character> chesses=new Vector<>(6);

    /**
     *
     */
    public Road(Vector<Character> chesses) {
        this.chesses=chesses;
        int bChessNum=0;
        int wChessNum=0;
        for(Character c:chesses){
            //如果棋子数据模型不存在，不做任何判断
            if(c==NOCHESS){
                continue;
            }
            if(c==BLACKCHESS){
                bChessNum++;
            }
            if(c==WHITECHESS){
                wChessNum++;
            }
        }
        if(bChessNum>0 && wChessNum==0){
            this.chessColor=BLACKCHESS;
            this.validChessCount=bChessNum;
        }
        if(wChessNum>0 && bChessNum==0){
            this.chessColor=WHITECHESS;
            this.validChessCount=wChessNum;
        }
    }


    /**
     * getChessColor获取当前路的颜色
     * @return chessColor ChessPoint.BLACKCHESS或'b'表明当前棋路为黑子棋路、
     *  	ChessPoint.WHITECHESS或'w'表明当前棋路为白子棋路、ChessPoint.NOCHESS
     *  	或'n'表明当前棋路无效
     */
    public char getChessColor() {
        return chessColor;
    }
    /**
     * getValidChessCount获取当前有效路的有效棋子个数
     * @return validChessCount 有效路中有效棋子的个数
     */
    public int getValidChessCount() {
        return validChessCount;
    }
    /**
     * getChess获取当前路中棋子的信息
     * @return chesses 当前路中的棋子信息
     */
    public Vector<Character> getChesses() {
        return chesses;
    }
}

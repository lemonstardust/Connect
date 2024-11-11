package com.example.connect.game;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.connect.dialog.WinDialog;
import com.example.connect.algorithm.Evaluation;
import com.example.connect.algorithm.Search;
import com.example.desktopdemo.R;


public class GameBoardView extends View {
    private Context mContext;

    private static int view_width ;          //手机屏幕的宽度
    private static int view_height ;         //手机屏幕的高度
    private static int boardToTop;
    static  int grid ;       //每个方格的大小
    private int left,right,top,bottom;
    Paint paintLine;
    Paint drawBlack,drawWhite,background;

    char currentRole ;      //目前角色
    ChessBoard chessBoard = new ChessBoard();
    char[][] board;

    public GameBoardView(Context context) {
        super(context);
        mContext = context;

        paintLine = new Paint();
        drawBlack = new Paint();
        drawWhite = new Paint();
        background = new Paint();
        getBasicConstant();
        ChessBoard.initboard();
        board =  chessBoard.getChessBoardDate();
    }

    public GameBoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mContext = context;

        paintLine = new Paint();
        drawBlack = new Paint();
        drawWhite = new Paint();
        background = new Paint();
        getBasicConstant();
        ChessBoard.initboard();
        board =  chessBoard.getChessBoardDate();
    }

    public GameBoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        paintLine = new Paint();
        drawBlack = new Paint();
        drawWhite = new Paint();
        background = new Paint();
        getBasicConstant();
        ChessBoard.initboard();
        board =  chessBoard.getChessBoardDate();
    }

//    public Game(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //设置棋盘线的样式，宽度，颜色
        paintLine.setStyle(Paint.Style.FILL);
        paintLine.setStrokeWidth(4);
        paintLine.setColor(getResources().getColor(R.color.black));
        //画棋盘背景————矩形
        background.setColor(getResources().getColor(R.color.board_background));
        background.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawRect(0,boardToTop,view_width,boardToTop+view_width,background);

        //画棋盘线19*19
        for(int i=0;i<=18;i++){
            canvas.drawLine(left,boardToTop+top+i*grid,view_width-right,boardToTop+top+i*grid,paintLine);
            canvas.drawLine(left+i*grid,boardToTop+top,left+i*grid,boardToTop+view_width-bottom,paintLine);
        }

        //设置棋子画笔
        drawWhite.setStyle(Paint.Style.FILL_AND_STROKE);
        drawBlack.setStyle(Paint.Style.FILL_AND_STROKE);
        drawWhite.setColor(getResources().getColor(R.color.white));
        drawBlack.setColor(getResources().getColor(R.color.black));

        /*
         * 画棋子
         * 黑棋是-1
         * 白棋是1*/
        for (int i = 0;i<=18;i++)
        {
            for (int j = 0;j<=18;j++)
            {
                //黑棋
                if (board[i][j] == GameConfig.BLACKCHESS)
                    canvas.drawCircle((j+1)*view_width/20,boardToTop+(i+1)*view_width/20,view_height/80,drawBlack);

                //白棋
                if (board[i][j] == GameConfig.WHITECHESS)
                    canvas.drawCircle((j+1)*view_width/20,boardToTop+(i+1)*view_width/20,view_height/80,drawWhite);
            }
        }
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        int x = (int) event.getX();
        int y = (int) event.getY();
        if (x<view_width/40||x>view_width-view_width/40||y<=(boardToTop)||y>(view_height-boardToTop))
        {
            return false;
        }
        x = (x-view_width/20+view_width/40)/(view_width/20);
        y = (y-boardToTop-view_width/20+view_width/40)/(view_width/20);
        if(x>18)
            x = 18;
        if (y>18)
            y = 18;

        Move move = null;
        currentRole = chessBoard.getCurrentRole();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //人人模式
                if (GameConfig.VSWay == GameConfig.PLAYERVSPLAYER){
                    if (board[y][x] == GameConfig.NOCHESS) {
                        if (currentRole == GameConfig.BLACKCHESS){
                            chessBoard.addChess((x+1)*100 + y+1);
                        }
                        if (currentRole == GameConfig.WHITECHESS){
                            chessBoard.addChess((x+1)*100 + y+1);
                        }
                    }
                }

                //人机模式
                if (GameConfig.VSWay == GameConfig.PLAYERVSAI){
                    //AI执白，玩家执黑
                    if (GameConfig.BlackStatus == GameConfig.PLAYER && GameConfig.WhiteStatus == GameConfig.AI){
                        if (currentRole == GameConfig.WHITECHESS){
                            move = Search.getNextMoves(board);
                            if(move != null) {
                                for(int coord:move.getCoordArray()){
                                    chessBoard.addChess(coord);
                                }
                            }

                        }
                        if (currentRole== GameConfig.BLACKCHESS && board[y][x] == GameConfig.NOCHESS){
                            chessBoard.addChess((x+1)*100 + y+1);
                        }
                    }
                    //AI执黑，玩家执白
                    if(GameConfig.BlackStatus==GameConfig.AI && GameConfig.WhiteStatus==GameConfig.PLAYER){
                        if(currentRole== GameConfig.BLACKCHESS){
                            if(ChessBoard.getAllChessNumber()==0){
                                move=new Move(1010);
                            }else{
                                move = Search.getNextMoves(board);
                            }
                            if(move != null) {
                                for(int coord:move.getCoordArray()){
                                    chessBoard.addChess(coord);
                                    //System.out.println("black = "+coord);
                                }
                            }
                        }
                        if (currentRole == GameConfig.WHITECHESS && board[y][x] == GameConfig.NOCHESS){
                            chessBoard.addChess((x+1)*100 + y+1);
                        }
                    }
                }

                //机机模式

                boolean result = Evaluation.isGameOver(board);
                if (result){
                    WinDialog win = new WinDialog(mContext);

                    if (currentRole == GameConfig.BLACKCHESS){
                        win.setWinTitle("Black   Win !");
                    }else
                        win.setWinTitle("White   Win !");
                    win.show();
                }
                break;
        }
        return true;
    }

    /*
     * 获取主要常量
     * 屏幕的宽度，高度
     * left，right，top，bottom，grid*/
    private void getBasicConstant(){
        view_width = getContext().getResources().getDisplayMetrics().widthPixels;       //获取屏幕的宽度
        view_height = getContext().getResources().getDisplayMetrics().heightPixels;     //获取屏幕的高度
        boardToTop = (view_height - view_width)/2;
        bottom=right=left=top=grid = view_width/20;

    }
}

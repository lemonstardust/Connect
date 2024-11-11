package com.example.connect.game

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.connect.algorithm.Evaluation
import com.example.connect.algorithm.Search
import com.example.connect.dialog.WinDialog
import com.example.desktopdemo.R


class GameBoardView @JvmOverloads constructor(
    private val context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var screenWidth = 0 //手机屏幕的宽度
    private var screenHeight = 0 //手机屏幕的高度
    private var boardToTop = 0
    private var left = 0
    private var right = 0
    private var top = 0
    private var bottom = 0
    private var grid: Int = 0 //每个方格的大小

    private lateinit var paintLine: Paint
    private lateinit var drawBlack: Paint
    private lateinit var drawWhite: Paint
    private lateinit var background: Paint

    private var currentRole: Char = 0.toChar() //目前角色
    private var chessBoard: ChessBoard = ChessBoard()
    private lateinit var board: Array<CharArray>


    init {
        initView()
    }

    private fun initView() {
        paintLine = Paint()
        //设置棋盘线的样式，宽度，颜色
        paintLine.style = Paint.Style.FILL
        paintLine.strokeWidth = 4f
        paintLine.color = resources.getColor(R.color.black)

        drawBlack = Paint()
        drawWhite = Paint()
        background = Paint()
        //设置棋子画笔
        drawWhite.style = Paint.Style.FILL_AND_STROKE
        drawBlack.style = Paint.Style.FILL_AND_STROKE
        drawWhite.color = resources.getColor(R.color.white)
        drawBlack.color = resources.getColor(R.color.black)

        getBasicConstant()
        ChessBoard.initboard()
        board = chessBoard.chessBoardDate
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //画棋盘背景————矩形
        background.color = resources.getColor(R.color.board_background)
        background.style = Paint.Style.FILL_AND_STROKE
        canvas.drawRect(
            0f,
            boardToTop.toFloat(),
            screenWidth.toFloat(),
            (boardToTop + screenWidth).toFloat(),
            background
        )

        //画棋盘线19*19
        for (i in 0..18) {
            canvas.drawLine(
                left.toFloat(),
                (boardToTop + top + i * grid).toFloat(),
                (screenWidth - right).toFloat(),
                (boardToTop + top + i * grid).toFloat(),
                paintLine
            )
            canvas.drawLine(
                (left + i * grid).toFloat(),
                (boardToTop + top).toFloat(),
                (left + i * grid).toFloat(),
                (boardToTop + screenWidth - bottom).toFloat(),
                paintLine
            )
        }

        /*
         * 画棋子
         * 黑棋是-1
         * 白棋是1*/
        for (i in 0..18) {
            for (j in 0..18) {
                //黑棋
                if (board[i][j] == GameConfig.BLACKCHESS) canvas.drawCircle(
                    ((j + 1) * screenWidth / 20).toFloat(),
                    (boardToTop + (i + 1) * screenWidth / 20).toFloat(),
                    (screenHeight / 80).toFloat(),
                    drawBlack
                )

                //白棋
                if (board[i][j] == GameConfig.WHITECHESS) canvas.drawCircle(
                    ((j + 1) * screenWidth / 20).toFloat(),
                    (boardToTop + (i + 1) * screenWidth / 20).toFloat(),
                    (screenHeight / 80).toFloat(),
                    drawWhite
                )
            }
        }
        invalidate()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        var x = event.x.toInt()
        var y = event.y.toInt()
        if ((x < screenWidth / 40 || x > screenWidth - screenWidth / 40 || y <= boardToTop) || y > (screenHeight - boardToTop)) {
            return false
        }
        x = (x - screenWidth / 20 + screenWidth / 40) / (screenWidth / 20)
        y = (y - boardToTop - screenWidth / 20 + screenWidth / 40) / (screenWidth / 20)
        if (x > 18) x = 18
        if (y > 18) y = 18

        var move: Move? = null
        currentRole = chessBoard.currentRole
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                when (GameConfig.VSWay) {
                    GameConfig.PLAYERVSPLAYER -> {
                        if (board[y][x] == GameConfig.NOCHESS) {
                            if (currentRole == GameConfig.BLACKCHESS) {
                                chessBoard.addChess((x + 1) * 100 + y + 1)
                            }
                            if (currentRole == GameConfig.WHITECHESS) {
                                chessBoard.addChess((x + 1) * 100 + y + 1)
                            }
                        }
                    }

                    GameConfig.PLAYERVSAI -> {
                        //AI执白，玩家执黑
                        if (GameConfig.BlackStatus == GameConfig.PLAYER && GameConfig.WhiteStatus == GameConfig.AI) {
                            if (currentRole == GameConfig.WHITECHESS) {
                                move = Search.getNextMoves(board)
                                if (move != null) {
                                    for (coord in move.coordArray) {
                                        chessBoard.addChess(coord)
                                    }
                                }
                            }
                            if (currentRole == GameConfig.BLACKCHESS && board[y][x] == GameConfig.NOCHESS) {
                                chessBoard.addChess((x + 1) * 100 + y + 1)
                            }
                        }
                        //AI执黑，玩家执白
                        if (GameConfig.BlackStatus == GameConfig.AI && GameConfig.WhiteStatus == GameConfig.PLAYER) {
                            if (currentRole == GameConfig.BLACKCHESS) {
                                move = if (ChessBoard.getAllChessNumber() == 0) {
                                    Move(1010)
                                } else {
                                    Search.getNextMoves(board)
                                }
                                if (move != null) {
                                    for (coord in move.coordArray) {
                                        chessBoard.addChess(coord)
                                        //System.out.println("black = "+coord);
                                    }
                                }
                            }
                            if (currentRole == GameConfig.WHITECHESS && board[y][x] == GameConfig.NOCHESS) {
                                chessBoard.addChess((x + 1) * 100 + y + 1)
                            }
                        }
                    }

                    else -> {
                        //机机模式
                    }
                }

                evaluationResult()
            }
        }
        return true
    }


    private fun evaluationResult() {
        val result = Evaluation.isGameOver(board)
        if (result) {
            WinDialog(context).apply {
                setWinTitle(
                    if (currentRole == GameConfig.BLACKCHESS) {
                        "Black   Win !"
                    } else "White   Win !"
                )

                show()
            }

        }
    }

    private fun getBasicConstant() {
        screenWidth = context.resources.displayMetrics.widthPixels //获取屏幕的宽度
        screenHeight = context.resources.displayMetrics.heightPixels //获取屏幕的高度
        boardToTop = (screenHeight - screenWidth) / 2
        grid = screenWidth / 20
        top = grid
        left = grid
        right = grid
        bottom = grid
    }

}

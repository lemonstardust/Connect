package com.example.connect.game

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.example.connect.algorithm.Evaluation
import com.example.connect.algorithm.Search
import com.example.connect.dialog.WinDialog
import com.example.connect.observe.IChessChangeListener
import com.example.desktopdemo.R
import kotlin.math.min


class GameBoardView : View {

    companion object {
        private const val TAG = "GameBoardView"

        private const val WIDTH_INTERVAL = 40
        private const val HALF_WIDTH_INTERVAL = WIDTH_INTERVAL / 2
    }

    private var screenWidth = 0 //手机屏幕的宽度
    private var screenHeight = 0 //手机屏幕的高度
    private var mTopMargin = 0
    private var left = 0
    private var right = 0
    private var top = 0
    private var bottom = 0
    private var grid: Int = 0 //每个方格的大小

    private var mChessRadius: Float = WIDTH_INTERVAL * 2f

    private lateinit var paintLine: Paint
    private lateinit var drawBlack: Paint
    private lateinit var drawWhite: Paint
    private lateinit var background: Paint

    private var currentRole: Char = 0.toChar() //目前角色
    private var chessBoard: ChessBoard = ChessBoard()
    private lateinit var board: Array<CharArray>

    private var eventX = 0

    private var eventY = 0

    private var boardX = 0

    private var boardY = 0

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyle: Int) : super(
        context,
        attributeSet,
        defStyle
    ) {
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
        //画棋盘背景————矩形
        background.color = resources.getColor(R.color.board_background)
        background.style = Paint.Style.FILL_AND_STROKE

        //设置棋子画笔
        drawWhite.style = Paint.Style.FILL_AND_STROKE
        drawBlack.style = Paint.Style.FILL_AND_STROKE
        drawWhite.color = resources.getColor(R.color.white)
        drawBlack.color = resources.getColor(R.color.black)

        getBasicConstant()
        ChessBoard.initboard()
        board = chessBoard.chessBoardDate
        chessBoard.setCountChangeListener(mIChessListener)
        currentRole = GameConfig.BLACKCHESS
    }

    private val mIChessListener = object : IChessChangeListener {
        override fun onChessCountChange(curSize: Int) {
            evaluationResult()
            invalidate()
            Log.i(TAG, "onChessCountChange curSize:$curSize")

        }

        override fun onRoleChange(c: Char) {
            currentRole = c
            Log.i(TAG, "current Role:$currentRole")
        }

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawRect(
            0f,
            mTopMargin.toFloat(),
            screenWidth.toFloat(),
            (mTopMargin + screenWidth).toFloat(),
            background
        )

        //画棋盘线19*19
        for (i in 0..18) {
            canvas.drawLine(
                left.toFloat(),
                (mTopMargin + top + i * grid).toFloat(),
                (screenWidth - right).toFloat(),
                (mTopMargin + top + i * grid).toFloat(),
                paintLine
            )
            canvas.drawLine(
                (left + i * grid).toFloat(),
                (mTopMargin + top).toFloat(),
                (left + i * grid).toFloat(),
                (mTopMargin + screenWidth - bottom).toFloat(),
                paintLine
            )
        }

        /*
         * 画棋子
         * 黑棋是-1
         * 白棋是1*/
        for (i in 0..18) {
            for (j in 0..18) {
                when (board[i][j]) {
                    GameConfig.BLACKCHESS -> {
                        canvas.drawCircle(
                            ((j + 1) * screenWidth / HALF_WIDTH_INTERVAL).toFloat(),
                            (mTopMargin + (i + 1) * screenWidth / HALF_WIDTH_INTERVAL).toFloat(),
                            mChessRadius,
                            drawBlack
                        )
                    }

                    GameConfig.WHITECHESS -> {
                        canvas.drawCircle(
                            ((j + 1) * screenWidth / HALF_WIDTH_INTERVAL).toFloat(),
                            (mTopMargin + (i + 1) * screenWidth / HALF_WIDTH_INTERVAL).toFloat(),
                            mChessRadius,
                            drawWhite
                        )
                    }
                }

            }
        }
//        invalidate()
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        eventX = event.x.toInt()
        eventY = event.y.toInt()


        if ((eventX < screenWidth / WIDTH_INTERVAL || eventX > screenWidth - screenWidth / WIDTH_INTERVAL || eventY <= mTopMargin) || eventY > (screenHeight - mTopMargin)) {
            return false
        }
        boardX = (eventX - screenWidth / HALF_WIDTH_INTERVAL + screenWidth / WIDTH_INTERVAL) /
                (screenWidth / HALF_WIDTH_INTERVAL)
        boardY =
            (eventY - mTopMargin - screenWidth / HALF_WIDTH_INTERVAL + screenWidth / WIDTH_INTERVAL) /
                    (screenWidth / HALF_WIDTH_INTERVAL)

        boardX = min(boardX, 18)
        boardY = min(boardY, 18)

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                touchDown(event)
            }

            MotionEvent.ACTION_POINTER_DOWN -> {
                val count = event.pointerCount
            }

            MotionEvent.ACTION_POINTER_UP -> {

            }

            MotionEvent.ACTION_UP -> {

            }
        }
        return true
    }

    private fun touchDown(event: MotionEvent) {
        var move: Move?
        when (GameConfig.VSWay) {
            GameConfig.PLAYERVSPLAYER -> {
                if (board[boardY][boardX] == GameConfig.NOCHESS) {
                    if (currentRole == GameConfig.BLACKCHESS) {
                        chessBoard.addChess((boardX + 1) * 100 + boardY + 1)
                    } else if (currentRole == GameConfig.WHITECHESS) {
                        chessBoard.addChess((boardX + 1) * 100 + boardY + 1)
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
                    if (currentRole == GameConfig.BLACKCHESS && board[boardY][boardX] == GameConfig.NOCHESS) {
                        chessBoard.addChess((boardX + 1) * 100 + boardY + 1)
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
                    if (currentRole == GameConfig.WHITECHESS && board[boardY][boardX] == GameConfig.NOCHESS) {
                        chessBoard.addChess((boardX + 1) * 100 + boardY + 1)
                    }
                }
            }

            else -> {
                //机机模式
            }
        }


    }


    private fun evaluationResult() {
        val result = Evaluation.isGameOver(board)
        if (result) {
            WinDialog(context).apply {
                setTitle("游戏结束")
                setContent(
                    if (Evaluation.numberOfBlackRoad[6] > 0) {
                        "黑棋胜利！"
                    } else "白棋胜利！"
                )
                show()
            }

        }
    }

    private fun getBasicConstant() {
        screenWidth = context.resources.displayMetrics.widthPixels //获取屏幕的宽度
        screenHeight = context.resources.displayMetrics.heightPixels //获取屏幕的高度
        mTopMargin = (screenHeight - screenWidth) / 2
        grid = screenWidth / HALF_WIDTH_INTERVAL
        top = grid
        left = grid
        right = grid
        bottom = grid
        mChessRadius = screenHeight / (WIDTH_INTERVAL * 2f)
    }

}

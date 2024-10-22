package com.example.desktopdemo.Game;


public class GameConfig {

    /**
     * BLACKCHESS表示当前棋点是黑子
     */
    public final static char BLACKCHESS = 'b';
    /**
     * WHITECHESS表示当前棋点是白子
     */
    public final static char WHITECHESS = 'w';
    /**
     * NOCHESS表示当前棋点没有棋子
     */
    public final static char NOCHESS = 'n';

    /**
     * 对战模式选择中的人机对战
     */
    public final static int PLAYERVSAI = 0;
    /**
     * 对战模式选择中的人人对战
     */
    public final static int PLAYERVSPLAYER = 1;
    /**
     * 博弈双方的身份-玩家
     */
    public final static int PLAYER = 0;
    /**
     * 博弈双方的身份-AI
     */
    public final static int AI = 1;
    /**
     * 难度为新手时的博弈树搜索深度
     */
    public final static int NOVICEDEEP = 1;
    /**
     * 难度为普通时的博弈树搜索深度
     */
    public final static int NORMALDEEP = 2;
    /**
     * 难度为精英时的博弈树搜索深度
     */
    public final static int HARDDEEP = 3;
//GenerateMoves
    /**
     * 走法生成时获取棋点的宽度
     */
    public final static int GENERATEMOVESWIDTH = 5;
//MainFrame
    /**
     * 当前棋局的对战模式,默认是人机对战
     */
    public static int VSWay = GameConfig.PLAYERVSAI;
    /**
     * 当前AI的等级，默认是新手等级
     */
    public static int AILevel = GameConfig.NOVICEDEEP;
    /**
     * 当前黑方身份，默认是玩家
     */
    public static int BlackStatus = GameConfig.PLAYER;
    /**
     * 当前白方身份，默认是AI
     */
    public static int WhiteStatus = GameConfig.AI;
}

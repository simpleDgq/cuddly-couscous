package class34;

// 348. 设计井字棋

public class Problem_0348_DesignTicTacToe {
    /**
     * 请在 n × n 的棋盘上，实现一个判定井字棋（Tic-Tac-Toe）胜负的神器，判断每一次玩家落子后，是否有胜出的玩家。
     * 
     * 在这个井字棋游戏中，会有 2 名玩家，他们将轮流在棋盘上放置自己的棋子。
     * 
     * 在实现这个判定器的过程中，你可以假设以下这些规则一定成立：
     * 
     *  1. 每一步棋都是在棋盘内的，并且只能被放置在一个空的格子里；
     *  
     *  2. 一旦游戏中有一名玩家胜出的话，游戏将不能再继续；
     *  
     *  3. 一个玩家如果在同一行、同一列或者同一斜对角线上都放置了自己的棋子，那么他便获得胜利。
     */
    
    /**
     * 思路:
     * 
     * 搞一些变量和数组分别记录:
     * 第1行1号小人下了几个棋子，第2行1号小人下了几个棋子...第n-1行1号小人下了几个棋子；
     * 
     * 第1行2号小人下了几个棋子，第2行2号小人下了几个棋子...第n-1行2号小人下了几个棋子
     * 
     * 第1列1号小人下了几个棋子，第2列1号小人下了几个棋子...第n-1列1号小人下了几个棋子
     * 
     * 第1列2号小人下了几个棋子，第2列2号小人下了几个棋子...第n-1列2号小人下了几个棋子
     * 
     * 左对角线1号小人下了几个棋子
     * 
     * 右对角线2号小人下了几个棋子
     * 
     * 
     * move函数: player在row行，col列下了棋子
     * 返回0表示谁都没赢;
     * 返回1表示1号玩家赢了;
     * 返回2表示2号玩家赢了
     * 
     * 判断谁赢的时候，直接根据上面准备好的变量就可以判断了
     * 
     */
    class TicTacToe {
        
        private int rows[][];
        private int cols[][];
        private int leftUp[]; // 左多角线
        private int rightUp[]; // 右多角线
        // 记录i,j位置是否有棋子
        private boolean[][] matrix;
        int N; // 记录行数，用于比较棋子数是否等于N
        
        public TicTacToe(int n) {
            // rows[i][1]表示1号小人在第i行下了几个棋子
            // rows[i][2]表示2号小人在第i行下了几个棋子
            rows = new int[n][3]; // 0号不用
            
            // cols[j][1]表示1号小人在第j列下了几个棋子
            // cols[j][2]表示2号小人在第j列下了几个棋子
            cols = new int[n][3]; // 0号不用
            
            // leftUp[1]表示1号小人在左对角线下了几个棋子
            // leftUp[2]表示2号小人在左对角线下了几个棋子
            leftUp = new int[3];
            
            // rightUp[1]表示1号小人在右对角线下了几个棋子
            // rightUp[2]表示2号小人在右对角线下了几个棋子
            rightUp = new int[3];
            
            matrix = new boolean[n][n];
            
            N = n;
        }
        
        public int move(int row, int col, int player) {
            // 如果row,col位置以及有棋子了，直接返回0，表示谁也不赢
            if(matrix[row][col]) {
                return 0;
            }
            // 记录已经下过了
            matrix[row][col] = true;
            // 该行上，player的棋子加1
            rows[row][player]++;
            // 该列上，player的棋子加1
            cols[col][player]++;
            // 如果是左对角线，左对角线上，player的棋子加1
            if(row == col) {
                leftUp[player]++;
            }
            // 如果是右对角线，右对角线上，player的棋子加1
            if(row + col == N - 1) { // 例如: 0行N - 1列 -> 0 + N - 1 == N - 1 .... 1行N-2列  -> 推出这个条件
                rightUp[player]++;
            }
            // 如果该行上的棋子数是N，或者该列上的棋子数是N，或者对角线上是N
            // 说明该player赢了，返回player
            if (rows[row][player] == N || cols[col][player] == N || leftUp[player] == N || rightUp[player] == N) {
                return player;
            }
            //谁也没赢，返回0
            return 0;
        }
    }
}

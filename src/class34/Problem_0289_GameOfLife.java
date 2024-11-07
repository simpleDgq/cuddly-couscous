package class34;

// 289. 生命游戏
// https://leetcode.cn/problems/game-of-life/
public class Problem_0289_GameOfLife {
    /**
     * 根据 百度百科 ， 生命游戏 ，简称为 生命 ，是英国数学家约翰·何顿·康威在 1970 年发明的细胞自动机。
     * 给定一个包含 m × n 个格子的面板，每一个格子都可以看成是一个细胞。每个细胞都具有一个初始状态： 
     * 1 即为 活细胞 （live），或 0 即为 死细胞 （dead）。每个细胞与其八个相邻位置（水平，垂直，对角线）的细胞都遵循以下四条生存定律：
     *  1)如果活细胞周围八个位置的活细胞数少于两个，则该位置活细胞死亡；
     *  2)如果活细胞周围八个位置有两个或三个活细胞，则该位置活细胞仍然存活；
     *  3)如果活细胞周围八个位置有超过三个活细胞，则该位置活细胞死亡；
     *  4)如果死细胞周围正好有三个活细胞，则该位置死细胞复活；
     *  下一个状态是通过将上述规则同时应用于当前状态下的每个细胞所形成的，其中细胞的出生和死亡是同时发生的。
     *  
     *  给你 m x n 网格面板 board 的当前状态，返回下一个状态。
     */
    
    /**
     * 思路：
     * 1.简单做法，搞一个辅助矩阵，遍历原始矩阵的每一个位置，计算每一个位置的状态填到辅助矩阵里面，最后返回。 --> 空间复杂度高
     * 2.可以发现，矩阵中的数是int类型，要么是0，要么是1
     * 可以用每个位置的int数的倒数第一位记录原始的状态
     * 用倒数第二位记录下一轮的状态。0表示死，1表示活。
     * 
     * 每一个位置下一轮的状态都计算好，记录到倒数第二位上。
     * 最后每一个位置只需要往右移动一位就得到了下一轮的状态。
     * 
     * 节约了空间
     */
    public void gameOfLife(int[][] board) {
        if(board == null || board.length == 0 || board[0] == null || board[0].length == 0) {
            return;
        }
        int N = board.length;
        int M = board[0].length;

        // 遍历矩阵的每一个位置，计算好下一轮的状态，存储在每一个int数的倒数第二位
        for(int i = 0; i <= N - 1; i++) {
            for(int j = 0; j <= M - 1; j++) {
                // 计算每个节点周围存活的细胞数有多少个
                int neighbors = neighbors(i, j, board);
                // 如果周围有3个活细胞(条件2,4)，那么下一轮这个位置还是存活的；
                // 如果该位置是存活的，然后该位置周围有2个活细胞，那么下一轮这个位置还是存活的
                if(neighbors == 3 || (board[i][j] == 1 && neighbors == 2)) {
                    board[i][j] |= 2; // // 或上2，就将第二位变成了1表示下一轮存活, 注意这里，最后一位还是不变的，原始的状态还在，不会影响后面的判断
                }
            }
        }

        // 全体向右移动一位，就是下一轮的状态
         for(int i = 0; i <= N - 1; i++) {
            for(int j = 0; j <= M - 1; j++) {
               board[i][j] >>= 1;
            }
        }
    }
    // 计算i,j 位置周围有多少个存活的细胞
    public int neighbors(int i, int j, int board[][]) {
        // 8个方向都去求一遍
        return isLive(i - 1, j, board) +
               isLive(i + 1, j, board) +
               isLive(i, j - 1, board) +
               isLive(i, j + 1, board) +
               isLive(i - 1, j - 1, board) +
               isLive(i - 1, j + 1, board) +
               isLive(i + 1, j - 1, board) +
               isLive(i + 1, j + 1, board);
    }

    // 判断i,j位置的细胞是否存活
    // 存活就返回1，不存活返回0
    public int isLive(int i, int j, int[][] board) {
        if(i < 0 || i >= board.length || j < 0 || j >= board[0].length) { // 越界了
            return 0;
        }
        // 没有越界，看原始的状态是不是1，是1的话，说明活细胞
        return (board[i][j] & 1) == 1 ? 1 : 0;
    }
    
}

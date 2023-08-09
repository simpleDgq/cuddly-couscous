package class31;

// 130. 被围绕的区域
public class Problem_0130_SurroundedRegions {
    /**
     * 给你一个 m x n 的矩阵 board ，由若干字符 'X' 和 'O' ，
     * 找到所有被 'X' 围绕的区域，并将这些区域里所有的 'O' 用 'X' 填充。 
     */
    
    /**
     * 思路:
     * 和岛问题类似--> 感染
     * 
     * 先看四周哪些是0先感染进去, 都改成Y字符,
     * 再次遍历原数组没有从四周感染到的0, 它就一定会 X 包裹
     * 改成X, 改完之后再把Y改回。
     * 时间复杂度O(N*M)
     * 
     * 上下边界一起搞，
     * 左右边界一起搞
     */
    public void solve(char[][] board) {
        if(board == null || board.length == 0 || board[0] == null || board[0].length == 0) {
            return; 
        }
        int M = board.length;
        int N = board[0].length;
        
        // 上下边界，遍历，如果是O，感染进去
        for (int j = 0; j <= N - 1; j++) {
            if(board[0][j] == 'O') {
                infect(0, j, board);
            }
            if(board[M - 1][j] == 'O') {
                infect(M - 1, j, board);
            }
        }
        // 左右边界，遍历，如果是O，感染进去
        for (int i = 0; i <= M - 1; i++) {
            if(board[i][0] == 'O') {
                infect(i, 0, board);
            }
            if(board[i][N- 1] == 'O') {
                infect(i, N- 1, board);
            }
        }
        // 遍历所有的格子，如果是O，改成X，同时如果是Y，给改回成O
        for(int i = 0; i <= M - 1; i++) {
            for(int j = 0; j <= N - 1; j++) {
                if(board[i][j] == 'O') {
                    board[i][j] = 'X';
                } else if(board[i][j] == 'Y') {
                    board[i][j] = 'O';
                }
            } 
        }
    }
    
    // 感染函数，从i,j位置出发，如果是O，感染成Y
    // 然后感染上下左右位置
    public void infect(int i, int j, char board[][]) {
        // 如果越界了，或者不是O，不用感染，直接返回
        if(i < 0 || i >= board.length || j < 0 || j >= board[0].length || board[i][j] != 'O') {
            return;
        }
        // 是O，感染成Y
        board[i][j] = 'Y';
        // 搞上下左右
        infect(i - 1, j, board);
        infect(i + 1, j, board);
        infect(i, j - 1, board);
        infect(i, j + 1, board);
    }
}

package class28;

// 37. 解数独
public class Problem_0037_SudokuSolver {
    /**
     * 编写一个程序，通过填充空格来解决数独问题。
     *  数独的解法需 遵循如下规则：
     *      数字 1-9 在每一行只能出现一次。
     *      数字 1-9 在每一列只能出现一次。
     *      数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。（请参考示例图）
     *      
     * 题目给出一个数组，需要将空着的地方填好
     */
    
    /**
     * 思路: 递归
     * 一个个格子去尝试(从上往下，从左往右)，如果当前格子没有数字在，当前格子从1-9去尝试，
     * 选择的数字，在该行、该列以及存在的桶里面都要没有出现过，所以需要和上一题一样搞3个数组，
     * 分别记录某行、某列、某个桶里面有没有出现过某个num，方便进行判断有没有存在过
     * 
     * 搞定了当前的格子，然后递归去填下一个格子
     */
    public void solveSudoku(char[][] board) {
        if(board == null || board.length == 0 || board[0] == null || board[0].length == 0) {
            return;
        }
        boolean[][] row = new boolean[9][10];
        boolean[][] col = new boolean[9][10];
        boolean[][] bucket = new boolean[9][10];
        // 初始row，col，bucket
        initMap(board, row, col, bucket);
        // 递归去吧
        process(board, 0, 0, row, col, bucket);
    }
    
    // 生成row，col，bucket三个数组
    // 遍历每一个格子，记录某行、某列、某个桶，有没有出现过某个num
    public void initMap(char[][] board, boolean row[][], boolean col[][], boolean bucket[][]) {
        int N = board.length;
        int M = board[0].length;
       
        for(int i = 0; i <= N - 1; i++) {
            for(int j = 0; j <= M - 1; j++) {
                if(board[i][j] != '.') {
                    int bid = 3 * (i / 3) + (j / 3);
                    int num = board[i][j] - '0';
                    row[i][num] = true;
                    col[j][num] = true;
                    bucket[bid][num] = true;
                }
            } 
        }
    }
    
    // 当前来到i,j位置进行尝试
    // row[][], col[][], bucket[][] 分别记录某行，某列，某个桶有没有出现过某个数字num
    public boolean process(char[][] board, int i, int j, boolean row[][], boolean col[][], boolean bucket[][]) {
        if(i == 9) { // 行的范围是0~8，如果已经到达了第9行，说明已经全部填完了，返回true
            return true;
        }
        // 计算下一个应该填的位置
        int nexti = j != 8 ? i : i + 1; // j到了8，说明到了一行的最后，应该去下一行搞了
        int nextj = j != 8 ? j + 1 : 0; // j到了8，说明到了一行的最后，应该去下一行搞了，列号变成0，否则j+1
        
        // 如果当前格子已经有数字存在，直接去搞下一个格子
        if(board[i][j] != '.') {
            return process(board, nexti, nextj, row, col, bucket);
        } else {
            // 计算桶的id
            int bid = 3 * (i / 3) + (j / 3);
            // 用1 ~ 9 尝试填这个格子
            for(int num = 1; num <= 9; num++) {
                if(!row[i][num] && !col[j][num] && !bucket[bid][num]) { // 如果行、列、桶都没有出现过
                    // 填好格子，并且标记
                    row[i][num] = true;
                    col[j][num] = true;
                    bucket[bid][num] = true;
                    board[i][j] = (char)(num + '0');
                    // 用当前的num填好i,j位置的前提下。去搞下一个位置, 如果后续的递归返回true，说明搞定了
                    // 这里直接返回，因为题目说只有唯一的答案，找到一个就可以返回了，后续不用继续再去尝试
                    if(process(board, nexti, nextj, row, col, bucket)) {
                        return true;
                    }
                    // 下一步要用新的num去填i,j位置了，这里要回复现场
                    row[i][num] = false;
                    col[j][num] = false;
                    bucket[bid][num] = false;
                    board[i][j] = '.';
                }
            }
            // 如果尝试玩，还没有返回true，说明搞不定，返回false
            return false;
        }
    }
}

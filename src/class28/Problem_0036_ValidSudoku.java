package class28;

// 36. 有效的数独
public class Problem_0036_ValidSudoku {
    /**
     * 请你判断一个 9 x 9 的数独是否有效。只需要 根据以下规则 ，验证已经填入的数字是否有效即可。
     *  数字 1-9 在每一行只能出现一次。
     *  数字 1-9 在每一列只能出现一次。
     *  数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。（请参考示例图）
     */
    
    /**
     * 
     * 思路:
     * 搞3个boolean数组，记录每一行，每一列，每一个桶有没有出现过该数子num
     * 
     * row[i][j]: 第i行有没有出现过j这个数字
     * col[i][j]: 第i列有没有出现过j这个数字
     * bucket[i][j]: 第i号桶有没有出现j这个数字
     * 
     * 
     * 遍历矩阵的每一个字符，如果以前在某一行或某一列或某一个桶已经出现过，就表示不合法  ==> 桶表示的是9个一组的小举证
     * 如果没有出现过，就记录在数组中，
     * 如果遍历完整个矩阵都没有返回false，说明是有效的，返回true
     */    
    public boolean isValidSudoku(char[][] board) {
        if(board == null || board.length == 0 || board[0] == null || board[0].length == 0) {
            return false;
        }
        boolean row[][] = new boolean[9][10];
        boolean col[][] = new boolean[9][10];
        boolean bucket[][] = new boolean[9][10];
        int N = board.length;
        int M = board[0].length;
        for(int i = 0; i <= N - 1; i++) {
            for(int j = 0; j <= M - 1; j++) {
                if(board[i][j] != '.') {
                   // 计算出桶的id 观察法得到
                   int bid = 3 * (i / 3) + (j / 3);
                   // 当前遍历到的数
                   int num = board[i][j] - '0';
                   // 如果以前在某一行或某一列或某一个桶已经出现过，就表示不合法
                   if(row[i][num] || col[j][num] || bucket[bid][num]) {
                       return false;
                   }
                   // 以前没有出现过，现在出现了
                   row[i][num] = true;
                   col[j][num] = true;
                   bucket[bid][num] = true;
                }
            } 
        }
        // 遍历完都没有返回false，表示是有效的
        return true;
    }
}

package class30;

//79. 单词搜索
public class Problem_0079_WordSearch {
    /**
     * 给定一个 m x n 二维字符网格 board 和一个字符串单词 word 。如果 word 存在于网格中，返回 true ；否则，返回 false 。
     * 单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。
     * 同一个单元格内的字母不允许被重复使用。
     */
    /**
     * 思路:
     * 递归
     * 
     * 定义一个递归函数f(i,j,k)表示的是当前来到了board的[i][j]位置，你给我返回能不能搞定从word的k位置出发的
     * 剩下的所有字符
     */
    public boolean exist(char[][] board, String word) {
        if(board == null || board.length == 0 || board[0] == null || board[0].length == 0 || word == null || word.length() == 0) {
            return false;
        }
        char words[] = word.toCharArray();
        // 从board的每一个位置出发，去尝试，有一个能搞定，就返回true
        for(int i = 0; i <= board.length - 1; i++) {
            for(int j = 0; j <= board[0].length - 1; j++) {
                if(process(board, words, i, j, 0)) {
                   return true; 
                }
            }
        }
        return false;
    }
    /**
     * 当前来到了board的[i][j]位置，你给我返回能不能搞定从word的k位置出发的
     * 剩下的所有字符
     */
    public boolean process(char[][] board, char word[], int i, int j, int k) {
        if(k == word.length) { // 如果k已经到了word的结尾了，表示已经全部搞定了
            return true;
        }
        // 如果i, j位置越界了，说明搞不定
        if(i < 0 || i >= board.length || j < 0 || j >= board[0].length) {
            return false;
        }
        // 如果当前来到的位置的字符，不等于当前要搞定的k位置的字符
        // 第一个字符都不能搞定，直接返回false
        if(board[i][j] != word[k]) {
            return false; 
         }
        
        // 说明k位置能够搞定，去搞k之后的位置
        // 不能走回头路，将当前位置标记为0，记录现场，后面要恢复
        char temp = board[i][j];
        board[i][j] = 0; // 用数字0，不能是字符'0'，因为word里面可能含有字符'0'
        // 从i, j位置出发，上下左右去搞k + 1开始剩下的字符串
        boolean ans = process(board, word, i - 1, j, k + 1) || process(board, word, i + 1, j, k + 1)
                || process(board, word, i, j - 1, k + 1) || process(board, word, i, j + 1, k + 1);
        // 恢复现场，去从下一个字符开始搞之前，需要恢复现场
        board[i][j] = temp;
        return ans;
    }
}

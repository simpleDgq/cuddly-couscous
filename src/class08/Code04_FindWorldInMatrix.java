package class08;

public class Code04_FindWorldInMatrix {
    /*
     * 
     * 给定一个char[][] matrix，也就是char类型的二维数组，再给定一个字符串word，
        可以从任何一个某个位置出发，可以走上下左右，能不能找到word？
        char[][] m = { { 'a', 'b', 'z' },
        { 'c', 'd', 'o' },
        { 'f', 'e', 'o' },
        设定1：可以走重复路的情况下，返回能不能找到
        比如，word = "zoooz"，是可以找到的，z -> o -> o -> o -> z，因为允许走一条路径中已经走过的字符
        设定2：不可以走重复路的情况下，返回能不能找到
        比如，word = "zoooz"，是不可以找到的，因为允许走一条路径中已经走过的字符不能重复走
        
        Plus:
        
        给定一个字符类型的二维数组board，和一个字符串组成的列表words。
        可以从board任何位置出发，每一步可以走向上、下、左、右，四个方向，
        但是一条路径已经走过的位置，不能重复走。
        返回words哪些单词可以被走出来。
        例子
        board = [
        ['o','a','a','n'],
        ['e','t','a','e'],
        ['i','h','k','r'],
        ['i','f','l','v']
        ]
        words = ["oath","pea","eat","rain"]
        输出：["eat","oath"]
     */
    
    /**
     *
    // 能走重复路
    // f(i,j,k)
    // 从i，j位置出发，你能不能走出str从k开头往后的所有来.
    */
    private static boolean canLoop(int i, int j, int k, char[][] m, char[] str) { // 可以改记忆化搜索
        if(k == str.length) { // k来到了str.length，要搞定的是空串
           return true; 
        }
        // 如果越界或者str[k] 和m[i][j] 不相等(出发的位置的字符不等于要搞定的str的开始字符)
        if(i < 0 || i == m.length || j < 0 || j == m[0].length || m[i][j] != str[k]) {
            return false;
        }
        // 不越界而且str[k] 等于m[i][j]。 上下左右去走吧!
        if(canLoop(i - 1, j, k + 1, m, str) || canLoop(i + 1, j, k + 1, m, str) 
                || canLoop(i, j - 1, k + 1, m, str) || canLoop(i, j + 1, k + 1, m, str)) {
            return true;
        }
        return false;
    }
    // 你整张表从任何一个位置出发，都可能走出整个str来。
    // 每一个位置都调用这个(i,j)从零出发，看哪个返回true, 有任何一个返回true, 答案就是true。
    //每个格子全调一遍，中间有任何一个格子能返回true能走出来
    public static boolean findWord1(char[][] matrix, String str) {
        if (str == null || str.equals("")) { // 空串，能搞定
            return true;
        }
        if(matrix == null || matrix.length == 0 ||
                matrix[0] == null || matrix[0].length == 0) {
            return false;
        }
        int N = matrix.length;
        int M = matrix[0].length;
        for(int i = 0; i <= N - 1; i++) {
            for(int j = 0; j <= M - 1; j++) { // 每一个i,j 去看能不能搞定str从0位置开始的字符串
                if(canLoop(i, j, 0, matrix, str.toCharArray())) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * 能走重复路的动态规划:
     * 可变参数: i, j, k
     * 范围: i: 0 ~ N - 1  j: 0 ~ M - 1 k: 0 ~ str.length - 1
     * dp[i][j][k]表示：必须以m[i][j]这个字符结尾的情况下，能不能找到str[0...k]这个前缀串  ==> 和上面的递归没关系，上面的递归直接加傻缓存就行
     * (需要记住这个dp含义，就能写出来)
     * 
     * 每一层，依赖它下面的一层。先填好第0层，在填其它层
     */
    public static boolean findWord1Dp(char[][] matrix, String str) {
        if (str == null || str.equals("")) { // 空串，能搞定
            return true;
        }
        if(matrix == null || matrix.length == 0 ||
                matrix[0] == null || matrix[0].length == 0) {
            return false;
        }
        
        int N = matrix.length;
        int M = matrix[0].length;
        int K = str.length();
        char strs[] = str.toCharArray();
        
        // dp[i][j][k]表示：必须以m[i][j]这个字符结尾的情况下，能不能找到str[0...k]这个前缀串
        boolean dp[][][] = new boolean[N][M][K];
        // k=0的时候，填好第0层
        for(int i = 0; i <= N -1; i++) {
            for(int j = 0; j <= M -1; j++) {
                // 必须以m[i][j]这个字符结尾的情况下，能不能找到str[0]这个前缀串
                dp[i][j][0] = (matrix[i][j] == strs[0]); // 如果相等，就能搞定
            }
        }
        // 一层层往上填
        for(int k = 1; k <= K - 1; k++) {
            for(int i = 0; i <= N -1; i++) {
                for(int j = 0; j <= M -1; j++) {
                    // 开头字符串相等。看上下左右，能不能搞定str[0...k - 1]， 有一个能搞定，就表示当前i,j能搞定str[0...k],就返回true
                    dp[i][j][k] = (matrix[i][j] == strs[k] && checkPrevious(dp, i, j, k));
                }
            }
        }
        // 遍历所有的格子，找答案
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (dp[i][j][K - 1]) {
                    return true;
                }
            }
        }
        // 如果没有找到
        return false; 
    }
    // 上下左右有一个满足，就满足，返回true
    // 越界就是false
    // k - 1 : 每一层依赖它下面的一层
    public static boolean checkPrevious(boolean[][][] dp, int i, int j, int k) {
        boolean up = i > 0 ? (dp[i - 1][j][k - 1]) : false;
        boolean down = i < dp.length - 1 ? (dp[i + 1][j][k - 1]) : false;
        boolean left = j > 0 ? (dp[i][j - 1][k - 1]) : false;
        boolean right = j < dp[0].length - 1 ? (dp[i][j + 1][k - 1]) : false;
        return up || down || left || right;
    }
    
    
    /**
     * 不能走重复路
     *  f(i,j,k)
    *   从i，j位置出发，你能不能走出str从k开头往后的所有来.
    *   走过的位置标0，下次走的时候，如果是0，就不走，返回fasle
     */
    public static boolean findWord2(char[][] matrix, String str) {
        if (str == null || str.equals("")) { // 空串，能搞定
            return true;
        }
        if(matrix == null || matrix.length == 0 ||
                matrix[0] == null || matrix[0].length == 0) {
            return false;
        }
        int N = matrix.length;
        int M = matrix[0].length;
        for(int i = 0; i <= N - 1; i++) {
            for(int j = 0; j <= M - 1; j++) { // 每一个i,j 去看能不能搞定str从0位置开始的字符串
                if(noLoop(i, j, 0, matrix, str.toCharArray())) {
                    return true;
                }
            }
        }
        return false;
    }
    private static boolean noLoop(int i, int j, int k, char[][] m, char[] str) { // 没办法改动态规划，因为m也是可变参数，去他妈的，就这样不改了
        if(k == str.length) { // k来到了str.length，要搞定的是空串
           return true; 
        }
        // 如果越界或者str[k] 和m[i][j] 不相等(出发的位置的字符不等于要搞定的str的开始字符)
        // m[i][j] == '0' 等于0，说明这个位置走过，返回fasle，因为不能走重复路
        if(i < 0 || i == m.length || j < 0 || j == m[0].length || m[i][j] != str[k] || m[i][j] == '0') {
            return false;
        }
        // 不越界而且str[k] 等于m[i][j]。 上下左右去走吧!
        m[i][j] = '0'; // 走过的位置标0
        if(noLoop(i - 1, j, k + 1, m, str) || noLoop(i + 1, j, k + 1, m, str) 
                || noLoop(i, j - 1, k + 1, m, str) || noLoop(i, j + 1, k + 1, m, str)) {
            return true;
        }
        m[i][j] = str[k]; // str[k] 是等于m[i][j]的，可以用str[k]来恢复
        // 要恢复现场，否则从下一个全新的i,j出发的时候，matrix里面的数据就不对了，含有0
        return false;
    }
    
    public static void main(String[] args) {
        char[][] m = { { 'a', 'b', 'z' }, { 'c', 'd', 'o' }, { 'f', 'e', 'o' }, };
        String word1 = "zoooz";
        String word2 = "zoo";
        // 可以走重复路的设定
        System.out.println(findWord1(m, word1));
        System.out.println(findWord1Dp(m, word2));
        
        // 不可以走重复路的设定
        System.out.println(findWord2(m, word1));
        System.out.println(findWord2(m, word2));
    }
}

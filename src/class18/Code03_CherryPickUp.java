package class18;

// 测试链接 : https://leetcode.cn/problems/cherry-pickup/
// https://www.nowcoder.com/questionTerminal/8ecfe02124674e908b2aae65aad4efdf
public class Code03_CherryPickUp {
    /**
     * 一个N x N的网格(grid) 代表了一块樱桃地，每个格子由以下三种数字的一种来表示：
        0 表示这个格子是空的，所以你可以穿过它。
        1 表示这个格子里装着一个樱桃，你可以摘到樱桃然后穿过它。
        -1 表示这个格子里有荆棘，挡着你的路。
        你的任务是在遵守下列规则的情况下，尽可能的摘到最多樱桃：
        
        从位置 (0, 0) 出发，最后到达 (N-1, N-1) ，只能向下或向右走，并且只能穿越有效的格子（即只可以穿过值为0或者1的格子）；
        当到达 (N-1, N-1) 后，你要继续走，直到返回到 (0, 0) ，只能向上或向左走，并且只能穿越有效的格子；
        当你经过一个格子且这个格子包含一个樱桃时，你将摘到樱桃并且这个格子会变成空的（值变为0）；
        如果在 (0, 0) 和 (N-1, N-1) 之间不存在一条可经过的路径，则没有任何一个樱桃能被摘到。
     */
    
    /**
     * 思路:
     * 尝试: 两个人A, B都从左下角走到右下角， 都只能向下或者向右走， 但是A跟B能做出不同的选择
     *       如果， 某一时刻， AB进入相同的一个格子， A和B只获得一份
     *       A走到之后， 就认为B就是回来的路径.
     * A来到了a行b列, B来到了c行d列，如果它们跳进不同的格子里，
     *   只获得一个的情况下，问你a跟b获得整体的最大。
     *   如果某一个位置A也来过，B也来过，AB一定是同时来的，而不会分先后
     *   因为AB同步走
     */
    public int cherryPickup(int[][] grid) {
        if(grid == null || grid.length == 0 || grid[0].length == 0) {
            return -1;
        }
        int ans = process(grid, 0, 0, 0, 0);
        return ans < 0 ? 0 : ans;
    }
    // A来到了a,b位置，B来到了c,d位置
    // 共同走下右下角，如果两个人同时来到了同一个位置，只获得一份樱桃
    // 返回最大能够获得的樱桃数
    public int process(int grid[][], int a, int b, int c, int d) {
        int N = grid.length;
        int M = grid[0].length;
        if(a == N - 1 && b == M - 1) { // 都来到了右下角的格子，获得该格子的樱桃，直接返回
            return grid[N - 1][M - 1];
        }
        // 来到了一个格子，能够往下或者往右走，A和B有4种组合
        /**
         * A下 B下
         * A下 B右
         * A右 B下
         * A右 B右
         * 每种情况PK最大值，累加
         */
        // 注意: 初始值得是Integer.MIN_VALUE，因为，如果是0的话，
        // 后序如果求得的是-1，和0 PK就变成了0，这是错的，因为-1是到不了的答案；要返回-1，不能返回0
        int best = Integer.MIN_VALUE;// 最好的后续
        // 如果A能往下
        if(a + 1 <= N - 1) {
            // B能往下
            if(c + 1 <= N - 1) {
                best = Math.max(best, process(grid, a + 1, b, c + 1, d));
            }
            // B能往右
            if(d + 1 <= M - 1) {
                best = Math.max(best, process(grid, a + 1, b, c, d + 1));
            }
        }
        // 如果A能往右
        if(b + 1 <= M - 1) {
           // B能往下
            if(c + 1 <= N - 1) {
                best = Math.max(best, process(grid, a, b + 1, c + 1, d));
            }
            // B能往右
            if(d + 1 <= M - 1) {
                best = Math.max(best, process(grid, a, b + 1, c, d + 1));
            }
        }
        // 如果到达的是-1，或者后序返回的是-1，表示不能到达，直接返回-1
        if(grid[a][b] == -1 || grid[c][d] == - 1 || best == -1) {
            return -1;
        }
        // 上面只求了后续的最好，当前来到的a,b c,d位置，要收集
        int cur = 0;
        if(a == c && b == d) { // 来到了同一个位置，只收集一份
           cur = grid[a][b];
        } else {
           cur =  grid[a][b] + grid[c][d];
        }
        return cur + best; // 当前的加后续的最好就是答案
    }
    
    
    /**
     * 可以直接挂傻缓存。
     * 4个可变参数，因为是同步走的，有a+b = c+d
     * d = a+b-c, 可以省掉一维参数
     * 
     * 可变参数: a,b,c
     * 范围: a: 0 ~ N - 1
     *      b: 0 ~ M - 1
     *      c: 0 ~ N - 1
     * int dp[][][] = new int[N][M][N]
     */
    public static int cherryPickup2(int[][] grid) {
        if(grid == null || grid.length == 0 || grid[0].length == 0) {
            return -1;
        }
        int N = grid.length;
        int M = grid[0].length;
        int dp[][][] = new int[N][M][N];
        // -1表示到不了
        // 用MIN_VALUE 表示没有求过
        // 因为0也是答案的一种
        // 都初始化为Integer.MIN_VALUE
        for(int i = 0; i <= N - 1; i++) {
            for(int j = 0; j <= M - 1; j++) {
                for(int k = 0; k <= N - 1; k++) {
                    dp[i][j][k] = Integer.MIN_VALUE;
                }
            }
        }
        int ans = process2(grid, 0, 0, 0, dp);
        return ans < 0 ? 0 : ans; // ans小于0，说明到不了，返回樱桃数0
    }
    // 挂傻缓存
    public static int process2(int[][] grid, int a, int b, int c, int[][][] dp) {
        int N = grid.length;
        int M = grid[0].length;
        if (a == N || b == M || c == N || a + b - c == M) { // 越界了
            return -1; // 表示到不了，直接返回-1
        }
        // 如果已经求过了，直接返回
        if(dp[a][b][c] != Integer.MIN_VALUE) {
            return dp[a][b][c];
        }
        // base case
        if(a == N - 1 && b == M - 1) { // 都来到了右下角的格子，获得该格子的樱桃，直接返回
            dp[a][b][c] = grid[N - 1][M - 1];
            return dp[a][b][c];
        }
        // 求后序能获得的最好best
        int best = Integer.MIN_VALUE;// 初始值得是Integer.MIN_VALUE，因为，如果是0的话，
        // 后序如果求得的是-1，和0 PK就变成了0，这是错的，因为-1是到不了的答案；要返回-1，不能返回0
        // 因为第一个if已经判断了越界的情况，这里不需要再判断
        best = Math.max(best, process2(grid, a + 1, b, c + 1, dp));
        best = Math.max(best, process2(grid, a + 1, b, c, dp));
        best = Math.max(best, process2(grid, a, b + 1, c + 1, dp));    
        best = Math.max(best, process2(grid, a, b + 1, c, dp));
        // 如果当前到达的是-1，或者后序best是-1，说明到达不了
        // 直接返回-1
        if(grid[a][b] == -1 || grid[c][a + b - c] == -1 || best == -1) {
            dp[a][b][c]  = -1;
            return dp[a][b][c];
        }
        // 上面只求了后续的最好，当前来到的a,b c,d位置，要收集
        int cur = 0;
        if(a == c && b == a + b - c) { // 来到了同一个位置，只收集一份
           cur = grid[a][b];
        } else {
           cur =  grid[a][b] + grid[c][a + b - c];
        }
        dp[a][b][c] = cur + best; // 记录答案
        return dp[a][b][c]; // 当前的加后续的最好就是答案。返回
    }
}

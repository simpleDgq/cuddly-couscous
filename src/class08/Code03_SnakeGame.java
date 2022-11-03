package class08;

import java.util.Arrays;

public class Code03_SnakeGame {
    /*
     * 给定一个矩阵matrix，值有正、负、0
        蛇可以空降到最左列的任何一个位置，初始增长值是0
        蛇每一步可以选择右上、右、右下三个方向的任何一个前进
        沿途的数字累加起来，作为增长值；但是蛇一旦增长值为负数，就会死去
        蛇有一种能力，可以使用一次：把某个格子里的数变成相反数
        蛇可以走到任何格子的时候停止
        返回蛇能获得的最大增长值
     =====   
        给定一个二维数组matrix，每个单元都是一个整数，有正有负。最开始的时候小Q操纵 一条长度为0的蛇蛇从矩阵最左侧任选一个单元格进入地图，蛇每次只能够到达当前位 置的右上相邻，右侧相邻和右下相邻的单元格。蛇蛇到达一个单元格后，自身的长度会 瞬间加上该单元格的数值，任何情况下长度为负则游戏结束。小Q是个天才，他拥有一 个超能力，可以在游戏开始的时候把地图中的某一个节点的值变为其相反数(注:最多 只能改变一个节点)。问在小Q游戏过程中，他的蛇蛇最长长度可以到多少?
        比如:
        1 -4 10
        3 -2 -1
        2 -1 0
        0 5 -2
        最优路径为从最左侧的3开始，3 -> -4(利用能力变成4) -> 10。所以返回17。
     */
    
    /**
     * 递归 + 动态规划
     * ==
     *  1. f(i,j): 蛇从一个最优的最左列的格子开始走到 i,j位置停, 返回两个值: 中途一次能力也不用获得的最好结果跟
     *  使用一次能力也不用获得的最好结果。
     *  2. 每一个格子都求一个f(i,j), 蛇有可能在任何格子停, 所有的 f(i,j) 中求 max 就是答案
     */
    public static class Info{
        public int no; // 一次能力也不用能够获得的最大值
        public int yes; // 用一次能力能够获得的最大值
        
        public Info(int no, int yes) {
           this.no = no;
           this.yes = yes;
        }
    }
    // 蛇从某一个最左列，且最优的空降点降落
    // 沿途走到(i,j)必须停！
    // 返回，一次能力也不用，获得的最大成长值
    // 返回，用了一次能力，获得的最大成长值
    // 如果蛇从某一个最左列，且最优的空降点降落，不用能力，怎么都到不了(i,j)，那么no = -1
    // 如果蛇从某一个最左列，且最优的空降点降落，用了一次能力，怎么都到不了(i,j)，那么yes = -1
    public static Info process(int matrix[][], int i, int j) {
        if(j == 0) { // 最左列
            // 不用能力
            int no = Math.max(-1, matrix[i][0]); // 如果matrix[i][0]是负数，蛇直接就死了，说明怎么也到不了，直接返回-1
            // 用能力能获得的最大值
            int yes = Math.max(-1, -matrix[i][0]); // 如果-matrix[i][0]是负数，蛇直接就死了，说明怎么也到不了，直接返回-1
            return new Info(no, yes);
        }
        // 不是最左列
        // 一定有左侧
        int preNo = -1;
        int preYes = -1;
        Info pre = process(matrix, i, j - 1); // 如果是从左侧到i,j的，搜集左侧答案
        preNo = Math.max(preNo, pre.no);
        preYes = Math.max(preYes, pre.yes);
        // 一定有左上。 从左上过来
        if(i > 0) {
            pre = process(matrix, i - 1, j - 1);
            preNo = Math.max(preNo, pre.no);
            preYes = Math.max(preYes, pre.yes);
        }
        // 一定有左下。 从左下过来
        if(i < matrix.length - 1) {
            pre = process(matrix, i + 1, j - 1);
            preNo = Math.max(preNo, pre.no);
            preYes = Math.max(preYes, pre.yes);
        }
        // 左，左上，左下，三种情况PK完，选出了最大的preYes，preNo
        // 来到自己，搞出i，j的Info，返回
        int no = preNo == -1 ? -1 : Math.max(-1, preNo + matrix[i][j]);// 一次能力也不用，能获得的最大值. 如果preNo等于-1，说明不用能力没有办法到达i,j, 直接返回-1
        
        // 用能力，分两种情况
        // 1. 前面用能力
        int p1 = preYes == -1 ? -1 : Math.max(-1, preYes + matrix[i][j]);
        // 2. 前面不用，i,j位置用
        int p2 = preNo == -1 ? -1 : Math.max(-1, preNo - matrix[i][j]);
        int yes = Math.max(p1, p2); // 两种情况取最大值
        // 返回
        return new Info(no, yes);
    }
    
    // 每一个位置都计算一个i,j。取里面的max
    public static int walk1(int matrix[][]) {
        if(matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return -1;
        }
        int ans = -1;
        for(int i = 0; i <= matrix.length - 1; i++) {
            for(int j = 0; j <= matrix[0].length - 1; j++) {
                Info res = process(matrix, i, j);
                ans = Math.max(ans, Math.max(res.yes, res.no));
            }
        }
        return ans;
    }
    
    /*
     * 动态规划
     * 可变参数i和j
     * 范围: i: 0 - matrix.length - 1 
     *       j: 0 - matrix[0].length - 1
     * int dp[N][M][2] 
     * 还需要挂两个int答案 , 所以是int dp[N][M][2] 
     * 你甚至可以搞个Info dp[N][M]
     * 
     * 分析依赖: 每一个位置只依赖它的左、左上、左下位置
     * 从左往右，一列一列的填
     * 
     */
    public static int walk2(int matrix[][]) {
        if(matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return -1;
        }
        int N = matrix.length;
        int M = matrix[0].length;
        
        int dp[][][] = new int[N][M][2];
        int max = -1;
        // base case, 第0列
        for(int i = 0; i <= N - 1; i++) {
            dp[i][0][0] = Math.max(-1, matrix[i][0]); // 不用能力
            dp[i][0][1] = Math.max(-1, -matrix[i][0]); // 用能力
            max = Math.max(max, Math.max(dp[i][0][0], dp[i][0][1]));
        }
        
        // 从左往右，一列一列的填
        for(int j = 1; j <= M -1; j++) {
            for(int i = 0; i <= N - 1; i++) {
                int preNo = -1;
                int preYes = -1;
                int pre[] = dp[i][j - 1];
                preNo = Math.max(preNo, pre[0]);
                preYes = Math.max(preYes, pre[1]);
                // 一定有左上。 从左上过来
                if(i > 0) {
                    pre = dp[i - 1][j - 1];
                    preNo = Math.max(preNo, pre[0]);
                    preYes = Math.max(preYes, pre[1]);
                }
                // 一定有左下。 从左下过来
                if(i < matrix.length - 1) {
                    pre = dp[i + 1][j - 1];
                    preNo = Math.max(preNo, pre[0]);
                    preYes = Math.max(preYes, pre[1]);
                }
                // 左，左上，左下，三种情况PK完，选出了最大的preYes，preNo
                // 来到自己，搞出i，j的Info，返回
                int no = preNo == -1 ? -1 : Math.max(-1, preNo + matrix[i][j]);
                
                // 用能力，分两种情况
                // 1. 前面用能力
                int p1 = preYes == -1 ? -1 : Math.max(-1, preYes + matrix[i][j]);
                // 2. 前面不用，i,j位置用
                int p2 = preNo == -1 ? -1 : Math.max(-1, preNo - matrix[i][j]);
                int yes = Math.max(p1, p2); // 两种情况取最大值
                // 设置dp[i][j]
                dp[i][j][0] = no;
                dp[i][j][1] = yes;
                // 决策出最大值
                max = Math.max(max, Math.max(dp[i][j][0],dp[i][j][1]));
            } 
        }
        return max; 
    }
    
    
    public static int[][] generateRandomArray(int row, int col, int value) {
        int[][] arr = new int[row][col];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                arr[i][j] = (int) (Math.random() * value) * (Math.random() > 0.5 ? -1 : 1);
            }
        }
        return arr;
    }

    public static void main(String[] args) {
        int N = 7;
        int M = 7;
        int V = 10;
        int times = 1000000;
        for (int i = 0; i < times; i++) {
            int r = (int) (Math.random() * (N + 1));
            int c = (int) (Math.random() * (M + 1));
            int[][] matrix = generateRandomArray(r, c, V);
            int ans1 = walk1(matrix);
            int ans2 = walk2(matrix);
            if (ans1 != ans2) {
                for (int j = 0; j < matrix.length; j++) {
                    System.out.println(Arrays.toString(matrix[j]));
                }
                System.out.println("Oops   ans1: " + ans1 + "   ans2:" + ans2);
                break;
            }
        }
        System.out.println("finish");
    }
}

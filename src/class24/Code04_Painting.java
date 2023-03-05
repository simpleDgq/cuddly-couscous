package class24;

import java.util.ArrayList;
import java.util.List;

public class Code04_Painting {
    /**
     * 棋盘染色问题
     * 
     * N * M的棋盘
        每种颜色的格子数必须相同的
        上下左右的格子算相邻
        相邻格子染的颜色必须不同
        所有格子必须染色
        返回至少多少种颜色可以完成任务
     */
    
    /**
     * 思路:
     * 递归-暴力方法观察规律
     * 
     * 假设颜色数有i种，每种颜色的格子数有必须相同，那么必定有(N * M) % i == 0
     * 
     * 怎么去尝试?
     * 1. 1种颜色能不能完成任务
     * 2. 2种颜色能不能完成任务
     * ...
     */
    
    public static int minColors(int N, int M) {
        // 从用2种颜色到N * M - 1种颜色尝试，如果都不能搞定，最后返回N* M种颜色
        for (int i = 2; i < N * M; i++) {
            int matrix[][] = new int[N][M];
            if((N * M) % i == 0 && can(N, M, i, matrix)) { //假设颜色数有i种，每种颜色的格子数有必须相同，那么必定有(N * M) % i == 0
                return i; // 如果能搞定, 返回i
            }
        }
        // 如果都不能搞定,最后返回N* M种颜色
        return N * M;
    }
    // pNum 当前尝试的颜色数量
    public static boolean can(int N, int M, int pNum, int matrix[][]) {
        int all = N * M;
        int every = all / pNum; // 每种颜色能够染的方块数量
        List<Integer> rest = new ArrayList<Integer>();
        rest.add(0); // 需要预先填一个0，因为颜色是从第1种开始的，没有第0种颜色。list的index表示的是第几种颜色，值
                    // 是这种颜色可用的格子数。如果不填0的话，会导致下面add的时候，会导致0位置表示的是第一种颜色，不好处理
        for(int i = 1; i <= pNum; i++) {
            rest.add(every); // 每种颜色需要染的格子数量, 第一种颜色能染every个格子,第二种也能染every格子....第pNum种能染every个
        }
        // pNum中颜色，从0行0列开始，开始尝试
        return process(matrix, N, M, pNum, 0, 0, rest);
    }
    
    // N * M 的棋盘，可用的颜色数量是pNum, 当前染到了row,col位置，
    // 剩下的需要用某种颜色染色的格子数量都在rest种, 给我返回，是否能够全部染色成功，并且
    // 相邻的格子不能是相同颜色
    public static boolean process(int[][] matrix, int N, int M, int pNum, int row, int col, List<Integer> rest) {
        if(row == N) { // 如果row等于N了，最后一行都干完了，说明全部染色成功了
            return true;
        }
        if(col == M) { // 如果到达了最后一列，开始下一行
            return process(matrix, N, M, pNum, row + 1, 0, rest); // 直接返回后序的结果
        }
        // 尝试用1到pNum种颜色去染当前row,col这个格子，并且要保证相邻的格子颜色不同
        for(int currentColor = 1; currentColor <= pNum; currentColor++) {
            int left = col == 0 ? 0 : matrix[row][col - 1]; // 当前格子的左边格子的颜色. 如果当前格子是第0列，就没有左边格子，颜色赋值为0
            int up = row == 0 ? 0 : matrix[row - 1][col]; // 同理当前格子上边格子的颜色
            // 如果当前格子和相邻格子颜色不一样，而且可以被当前这种颜色染色的格子的数量大于0，如果等于0的话，说明已经没有格子可以用于当前的颜色染了
            if(left != currentColor && up != currentColor && rest.get(currentColor) > 0) {
                // 当前这种颜色能够染的格子数减1
                int count = rest.get(currentColor);
                rest.set(currentColor, count - 1); // list里面的index就是第i种颜色，值就是这种颜色剩下的能够染的格子数
                // 设置row,col号格子用的颜色
                matrix[row][col] = currentColor;
                // 第row,col已经被搞定，用pNum种颜色去尝试染下一个格子
                if(process(matrix, N, M, pNum, row, col + 1, rest)) { // 如果后续都能搞定，返回true
                    return true;
                }
                // 如果第i中颜色，染row,col号格子，不能搞定；下一步要去尝试用第i+1种颜色，染row,col号格子。需要先恢复现场
                rest.set(currentColor, count); // list里面的index就是第i种颜色
                matrix[row][col] = 0;
            }
        }
        // 如果没有搞定
        return false;
    }

    public static void main(String[] args) {
        // 根据代码16行的提示，打印出答案，看看是答案是哪个因子
        for (int N = 2; N < 10; N++) {
            for (int M = 2; M < 10; M++) {
                System.out.println("N   = " + N);
                System.out.println("M   = " + M);
                System.out.println("ans = " + minColors(N, M));
                System.out.println("===========");
            }
        }
        // 打印答案，分析可知，是N*M最小的质数因子，原因不明，也不重要
        // 反正打表法猜出来了
    }
}

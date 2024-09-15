package class38;

import java.util.ArrayList;
import java.util.List;

// 994. 腐烂的橘子
// https://leetcode.cn/problems/rotting-oranges
public class Problem_0994_orangesRotting {
    public int orangesRotting(int[][] grid) {
        /**
         * BFS
         * 初始的时候，统计所有的腐烂橘子的位置，全部加入到队列中；
         * 队列不为空，每过一分钟，ans++，遍历当前队列中所有的橘子，然后将这些橘子的上下左右的橘子腐烂
         * 将队列更新为这些新腐烂的橘子的位置
         * 直到队列为空。
         * 
         * 为了判断是否有永远不会腐烂的橘子（如示例 2），我们可以统计初始新鲜橘子的个数 fresh。
         * 在 BFS 中，每有一个新鲜橘子被腐烂，就把 fresh 减一，这样最后如果发现 fresh>0，
         * 就意味着有橘子永远不会腐烂，返回 −1。
         * 
         * 注意在 grid 全为 0 的情况下要返回 0，但这种情况下 ans 仍为其初始值 −1，所以最后返回的是 max(ans,0)。
         * 
         * 代码实现时，在 BFS 中要将 grid[i][j]=1 的橘子修改成 2（或者其它不等于 1 的数），这可以保证每个橘子加入 q 中
         * 至多一次。 如果不修改，我们就无法知道哪些橘子被腐烂过了，比如示例 1 中 (0,1) 去腐烂 (1,1)，而 (1,1) 在此之后
         * 又重新腐烂(0,1)，如此反复，程序就会陷入死循环。读者可以注释掉下面代码中的 grid[i][j] = 2 这行代码试试。
         * 
         */
        if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) {
            return -1;
        }
        // 4个方向。这里记一下就行，主要就是一个编程技巧
        int directions[][] = { { -1, 0 }, { 1, 0 }, { 0, 1 }, { 0, -1 } };
        int N = grid.length;
        int M = grid[0].length;
        // 用ArrayList代替数组
        List<Integer[]> queue = new ArrayList<Integer[]>();
        int fresh = 0; // 新鲜橘子的数量
        int ans = -1;

        // 将所有初始腐烂的橘子加到队列中
        for (int i = 0; i <= N - 1; i++) {
            for (int j = 0; j <= M - 1; j++) {
                if (grid[i][j] == 2) { // 如果是腐烂的
                    queue.add(new Integer[] { i, j });
                } else if (grid[i][j] == 1) { // 统计一下新鲜橘子的数量
                    fresh++;
                }
            }
        }
        // 每个腐烂队列，都遍历一遍
        while (!queue.isEmpty()) {
            // 每一轮，ans加一次
            ans++;
            List<Integer[]> tmp = queue;
            queue = new ArrayList<Integer[]>(); // 存放下一轮腐烂的橘子的位置
            // 遍历这一轮腐烂的橘子
            for (Integer[] cur : tmp) {
                // 上下左右4个方向，如果是好的橘子，则腐烂，改成2
                for (int[] direction : directions) {
                    int n = cur[0] + direction[0]; // 腐烂的橘子的4周的某一个位置的行号
                    int m = cur[1] + direction[1];// 腐烂的橘子的4周的某一个位置的列号
                    if (n >= 0 && n <= N - 1 && m >= 0 && m <= M - 1 && grid[n][m] == 1) { // 不越界 而且是好的橘子，就腐烂
                        grid[n][m] = 2;
                        fresh--;
                        // 新腐烂的位置，加入到下一轮的队列中
                        queue.add(new Integer[] { n, m });
                    }
                }
            }
        }
        // 如果新鲜的橘子的个数不是0，说明不能完全腐烂
        if (fresh != 0) {
            return -1;
        }
        // 如果刚开始，矩阵全为0，没有橘子，那么就应该返回0，所以这里要和0 pk一下
        return Math.max(ans, 0);
    }
}

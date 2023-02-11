package class22;

import java.util.PriorityQueue;

// https://leetcode-cn.com/problems/trapping-rain-water-ii/
public class Code03_TrappingRainWaterII {
    /**
     * 给你一个 m x n 的矩阵，其中的值均为非负整数，代表二维高度图每个单元的高度，请计算图中形状最多能接多少体积的雨水。

    示例：
    
    给出如下 3x6 的高度图:
    [
    [1,4,3,1,3,2],
    [3,2,1,3,2,4],
    [2,3,3,2,3,1]
    ]
    返回 4 。
    
    如上图所示，这是下雨前的高度图[[1,4,3,1,3,2],[3,2,1,3,2,4],[2,3,3,2,3,1]] 的状态。
    
    下雨后，雨水将会被存储在这些方块中。总的接雨水量是4。
    
    提示：
    
    1 <= m, n <= 110
    0 <= heightMap[i][j] <= 20000。
    */
    
    /**
     * 思路: 小根堆
     * 想象成一个锅，用来接雨水。
     * 
     * 4条边界入堆，搞一个max记录瓶颈，刚开始是0，
     * 出堆的时候，看能不能推高max，并且将出堆的元素
     * 的上下左右位置，放入堆，已经放入过的不在放入，
     * 搞一个boolean数组记录是不是已经放入过堆，放入的时候结算水量。
     * 
     * 时间复杂度:
     * 时间复杂度：O(M*N*log(M*N))，其中 M 是矩阵的行数，N 是矩阵的列数。我们需要将矩阵中的每个元素都进行遍历，
     * 同时将每个元素都需要插入到优先队列中，总共需要向队列中插入 M*N 个元素，因此队列中最多有 M*N 个元素，
     * 每次堆进行调整的时间复杂度为O(log(M*N))，因此总的时间复杂度为 O(M*N*log(M*N))。
     */
    // 节点的值以及位置
    public class Node {
        public int value;
        public int i;
        public int j;
        
        public Node(int value, int i, int j) {
            this.value = value;
            this.i = i;
            this.j = j;
        }
    }
    
    public int trapRainWater(int[][] heightMap) {
        if(heightMap == null || heightMap.length == 0 || heightMap[0] == null || heightMap[0].length == 0) {
            return 0;
        }
        int N = heightMap.length;
        int M = heightMap[0].length;
        
        // boolean数据，记录i,j位置是否进入过堆
        boolean isEntered[][] = new boolean[N][M];
        // 小根堆
        PriorityQueue<Node> heap = new PriorityQueue<Node>((a, b)-> a.value - b.value);
        // 二维数组的4条边界入堆
        for(int j = 0; j <= M - 1; j++) {
            isEntered[0][j] = true;
            heap.add(new Node(heightMap[0][j], 0, j));
        }
        for(int i = 1; i <= N - 1; i++) {
            isEntered[i][M - 1] = true;
            heap.add(new Node(heightMap[i][M - 1], i, M - 1));
        }
        for(int j = 0; j <= M - 1; j++) {
            isEntered[N - 1][j] = true;
            heap.add(new Node(heightMap[N - 1][j], N - 1, j));
        }
        for(int i = 1; i <= N - 2; i++) {
            isEntered[i][0] = true;
            heap.add(new Node(heightMap[i][0], i, 0));
        }
        // max 记录瓶颈，water记录水量
        int max = 0;
        int water = 0;
        while(!heap.isEmpty()) {
            Node cur = heap.poll();
            int i = cur.i;
            int j = cur.j;
            max = Math.max(cur.value, max); // 每弹出一个元素，看能不能推高瓶颈
            // 弹出元素的上下左右，没有进过的都进对，同时结算水量
            if(i > 0 && !isEntered[i - 1][j]) { // 有上且没有进过堆
                water += Math.max(0, max - heightMap[i - 1][j]); // 如果是负数，说明上边的元素比max瓶颈大，存不住水，返回0。 
                                                                //如果小，则能够存max-heightMap[i - 1][j]的水量
                isEntered[i - 1][j] = true;
                heap.add(new Node(heightMap[i - 1][j], i - 1, j));
            }
            if(i < N - 1 && !isEntered[i + 1][j]) { // 有下且没有进过堆
                water += Math.max(0, max - heightMap[i + 1][j]);
                isEntered[i + 1][j] = true;
                heap.add(new Node(heightMap[i + 1][j], i + 1, j));
            }
            if(j > 0 && !isEntered[i][j - 1]) { // 有左且没有进过堆
                water += Math.max(0, max - heightMap[i][j - 1]);
                isEntered[i][j - 1] = true;
                heap.add(new Node(heightMap[i][j - 1], i, j - 1));
            }
            if(j < M - 1 && !isEntered[i][j + 1]) { // 有右且没有进过堆
                water += Math.max(0, max - heightMap[i][j + 1]);
                isEntered[i][j + 1] = true;
                heap.add(new Node(heightMap[i][j + 1], i, j + 1));
            }
        }
        return water;
    }
}

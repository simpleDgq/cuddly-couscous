package class26;

import java.util.Comparator;
import java.util.TreeSet;

public class Code01_MinRange {
    /**
     * 三个有序数组中，找三个数互减绝对值累加和最小的值。
     * 
     * 给你三个有序数组, 在每个数组中分别拿出一个值, X, Y, Z, 问怎么选择这三个值让|X-Y| + |Y-Z| + |Z-X|的绝对值尽量小, 求这个最小值
     */
    
    /**
     * 思路:
     * 假设三个数组x, y, z各拿出一个数, 假设最小值a, 最大值b, c位于中间
        随意假设, 指标就是最大值减最小值，b到a距离的两倍。
       例子:   假设x y z分别是最小值a, 最大值b，中间的值c
       x z y  
       a c b  
      那么|X-Y| + |Y-Z| + |Z-X| = 2*(b - a)
      
        转换成最窄区间问题:  (大厂刷题班19 632.最小区间)
        我三个数组中，选择一个最小区间，让这个区间的从最小值到最大时的这个长度最短。
        而且每一个数组中，至少有一个数在区间里.  求出最小区间之后，2倍就是答案
        所以这个最最窄区间的最小值跟最大值，它必然是在三个数字中出现过的数.
        
        
        *有序表:
        *刚开始，数组的第一个元素进入有序表，，得到一个区间，然后最小值弹出，最小值来自哪一个数组就把哪一个数组的下一个元素放入有序表，
        *得到一个新的区间，pk出最小区间，记录下来；继续，当一个数组耗尽的时候，就结束
        *
        *例子： [1,5,6]     [2,7,9]   [4,6,8]
        *将1, 2, 4放入有序表，得到一个区间[1,4], 弹出最小值，然后5进入有序表，有序表中[2,4,5]，又得到一个区间[2,5], pk
        *出最小区间，记录答案
        *
        *实质是如果我找到的区间必须以这个1开头的情况下最窄是多少, 然后最小值弹出, 最小值来自第一个数组
        把第一个数组中的下一个数放入有序表, 得到区间2,4,5, 是必须以这个2为开头的情况下的最窄区间
        周而复始，你其实尝试了每个数作为区间开始时候的最窄。答案就是你找到的最窄的那个。
     */
    // 放入有序表中的Node节点
    public static class Node {
        public int value; //值
        public int arrId;// 所属的数组的id
        public int arrIndex;// 在arr中的index号
        public Node(int value, int arrId, int arrIndex) {
            this.value = value;
            this.arrId = arrId;
            this.arrIndex = arrIndex;
        }
    }
    // 比较器
    public static class NodeComparator implements Comparator<Node> {

        @Override
        public int compare(Node o1, Node o2) {
            // value不一样，按value从小到大排序
            // value一样，按arrId排序，因为有序表不允许加入重复值, 比如: Java里的 TreeMap, 加入多条重复的只会留下最初的一条, 不会覆盖
            // 有序表如果想加入重复值,这里相等时，就把arrId比较小下一个元素的放入有序表
            return o1.value != o2.value ? o1.value - o2.value : o1.arrId - o2.arrId;
        }
    }
    
    public static int minRange(int matrix[][]) {
        if(matrix == null || matrix.length == 0) {
            return 0;
        }
        int N = matrix.length;
        // 所有数组的第一个数进入有序表
        TreeSet<Node> treeSet = new TreeSet<Node>(new NodeComparator());
        for(int i = 0; i <= N - 1; i++) {
            treeSet.add(new Node(matrix[i][0], i, 0));
        }
        // 收集答案，弹出最小值，继续
        int min = Integer.MAX_VALUE; // 记录区间长度最小值
        while(treeSet.size() == N) { // 如果treeSet不能够凑出N个了，说明有数组耗尽，直接退出
            // 取出区间的最大和最小值，PK出最小值min
            min = Math.min(min, treeSet.last().value - treeSet.first().value);
            // 弹出最小值
            Node minNode = treeSet.pollFirst();
            // 将最小值所在数组的下一个节点放入有序表，如果还有下一个元素的话
            int arrId = minNode.arrId; // 最小值节点所在数组的id
            int nextIndex = minNode.arrIndex + 1; // 下一个要放入的节点的index
            if(nextIndex < matrix[arrId].length) { // 如果还有下一个元素
                // 放入有序表
                treeSet.add(new Node(matrix[arrId][nextIndex], arrId, nextIndex));
            }
        }
        return min << 1; // 2倍
    }
}

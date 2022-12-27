package class19;

import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

//本题测试链接 : https://leetcode.com/problems/smallest-range-covering-elements-from-k-lists/
//google二面原题
public class Code04_SmallestRangeCoveringElementsfromKLists {
    /**
     * 你有 k 个升序排列的整数数组。找到一个最小区间，使得 k 个列表中的每个列表至少有一个数包含在其中。

        我们定义如果 b-a < d-c 或者在 b-a == d-c 时 a < c，则区间 [a,b] 比 [c,d] 小。
        
        示例 1:
        
        输入:[[4,10,15,24,26], [0,9,12,20], [5,18,22,30]]
        输出: [20,24]
        解释: 
        列表 1：[4, 10, 15, 24, 26]，24 在区间 [20,24] 中。
        列表 2：[0, 9, 12, 20]，20 在区间 [20,24] 中。
        列表 3：[5, 18, 22, 30]，22 在区间 [20,24] 中。
        注意:
        
        给定的列表可能包含重复元素，所以在这里升序表示 >= 。
        1<= k <= 3500
        -10^5 <= 元素的值 <= 10^5
        对于使用Java的用户，请注意传入类型已修改为List>。重置代码模板后可以看到这项改动。
        提示：
        nums.length == k
        1 <= k <= 3500
        1 <= nums[i].length <= 50
        -10^5 <= nums[i][j] <= 10^5
        nums[i] 按非递减顺序排列
     */
    
    /**
     * 思路:
     * 有序表TreeSet
     * 非常方便的查到所有数字最小值，也可以非常方便的查到所有数字的最大直
     *   每个数组中的第一个数加入有序表, 取出最大值跟最小值, 可以找到一个区间
     *   这个区间一定每个数组都有一个数落在这个区间上
     *   然后删除最小值, 把这个最小值来自数组的下一个值加入有序表, 排序后重新取出最小值跟最大值
     *   构成一个新的区间, 跟之前的区间比较是否更优
     *   当你有一个数组耗尽了，不用再继续了, 你找到的最窄区间出来了
     * 
     * 为什么这样求解就行？
     * [2,3,7]
     * [4,10,12]
     * [1,6,14]
     * 数组的第一个数都进去TreeSet. 1,2,4  -> 区间是1~4
     * 求解的是必须以这个1作为区间的开头，我向右怎么样最经济
     * 然后1出来，6进去，2,4,6 -> 区间是2~6
     * 必须以这个2作为区间的开头，我向右怎么样最经济
     * 所以你每一个数产生答案的时候都是某一个数字如果作为连续区间的开头往右怎么样最经济，
     * 你把所有答案其实都穷举了
     */
    public class Node {
        public int value; // 进入TreeSet的值
        public int arrid;// 进入TreeSet的值所在的数组的id
        public int index;// 进入TreeSet的值在数组中的下标
        
        public Node(int value, int arrid, int index) {
            this.value = value;
            this.arrid = arrid;
            this.index = index;
        }
    }
    
    public class NodeComparator implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            // 根据value从小到大排序，如果value相等，根据arrid从小到大排序
            return o1.value != o2.value ? o1.value - o2.value : o1.arrid - o2.arrid;
        }
    }
    
    public int[] smallestRange(List<List<Integer>> nums) {
        // 有序表
        TreeSet<Node> treeSet = new TreeSet<Node>(new NodeComparator());
        int N = nums.size(); // 总共多少个数组
        // 所有的数组的第0个元素，入有序表
        for(int i= 0; i <= N - 1; i++) {
            treeSet.add(new Node(nums.get(i).get(0), i, 0));
        }
        // 记录答案:区间[a,b]
        int a = 0;
        int b = 0;
        boolean set = false;// 记录是否是第一次形成区间
        // treeset中有N个元素
        while(treeSet.size() == N) {
            // 取出最大和最小值
            int min = treeSet.first().value;
            int max = treeSet.last().value;
            // 是第一次形成区间，或者有更小的区间形成，则更新区间
            if(!set || max - min < b - a) {
                set = true; // 记录第一次形成区间
                // 设置区间
                a = min;
                b = max;
            }
            // 弹出最小值，同时如果最小值所在的数组还有元素，将最小值所在的数组的下一个数放入treeset
            Node node = treeSet.pollFirst();
            int arrid = node.arrid;
            int index = node.index + 1;
            // 如果还有元素
            if(index != nums.get(arrid).size()) {
                treeSet.add(new Node(nums.get(arrid).get(index), arrid, index));
            }
            // 如果上一步没有加入元素，说明一个数组已经耗尽了，会直接退出while
        }
        return new int[]{a, b};
    }
}

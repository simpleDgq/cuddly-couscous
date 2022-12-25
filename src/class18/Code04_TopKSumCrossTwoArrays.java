package class18;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

public class Code04_TopKSumCrossTwoArrays {
    /**
     * 两个有序数组间相加和的Topk问题
     * 
     * 描述
     *   给定两个有序数组 arr1 和 arr2，再给定一个整数 k，返回来自 arr1 和 arr2 的两个数相加和最大的前 k 个
     *   ，两个数必须分别来自两个数组, 按照降序输出
        
        [要求]
        时间复杂度为O(klogk)
        
        输入描述：
        第一行三个整数 N, K 分别表示数组 arr1, arr2 的大小，以及需要询问的数
        接下来一行 N 个整数，表示 arr1 内的元素
        再接下来一行 N 个整数，表示 arr2 内的元素
        
        输出描述：
        输出 K 个整数表示答案
        
        示例 1
        输入：
        
        5 4
        1 2 3 4 5
        3 5 7 9 11
        输出：
        
        16 15 14 14
        备注:
        1<=N<=10^5
        0<<arr1i, arr2i<=10^9
        保证 1<=K<=2N
     */
    
    /**
     * 思路:
     * 大根堆 ：arr1做行对应，arr2做列对应
     *   最大值是右下角,进堆
     *   出堆，收集答案, 出来一个之后, 出来的元素的左边和上面进堆。
     *   这样一直收集满K个为止
     *   注意: 防止同一个位置进入堆
     *        要保证进大根堆不要重复进 --> 用set记录
     *    
     * 时间复杂度:
     *   每次出来一个元素，进去两个元素，大根堆最多K个元素，
     *   每次堆调整的代价是logK，总共K轮，所以时间复杂度是K*logK
     */
    
    // 放入大根堆中的结构
    public static class Node{
        public int index1; // arr1中的位置
        public int index2; // arr2中的位置
        public int sum; // arr1[index1] + arr2[index2]的值
        
        public Node(int index1, int index2, int sum) {
           this.index1 = index1;
           this.index2 = index2;
           this.sum = sum;
        }
    }
    // 大根堆比较器
    public static class NodeComparator implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            return o2.sum - o1.sum; // 如果返回正数，认为第二个参数应该排在前面
        }
    }
    
    public static int[] topK(int arr1[], int arr2[], int K) {
        int res[] = new int[K];
        int resIndex = 0;
        PriorityQueue<Node> heap = new PriorityQueue<Node>(new NodeComparator());
        HashSet<Long> set = new HashSet<Long>();
        int N = arr1.length;
        int M = arr2.length;
        // 最右下角的位置就是最大的，进入堆
        heap.add(new Node(N - 1, M - 1, arr1[N - 1] + arr2[M - 1]));
        set.add(index(N - 1, M - 1, M));
        while(resIndex != K) {
            Node ansNode = heap.poll();
            res[resIndex++] = ansNode.sum; // 收集答案
            int index1 = ansNode.index1;
            int index2 = ansNode.index2;
            // 出来的节点从set中去掉，因为不可能在被放入堆
            set.remove(index(index1, index2, M));
            // 左边入堆, 左还有元素，而且没有入过堆
            if(index2 - 1 >= 0 && !set.contains(index(index1, index2 - 1, M))) {
                heap.add(new Node(index1, index2 - 1, arr1[index1] + arr2[index2 - 1]));
                set.add(index(index1, index2 - 1, M));
            }
            // 上边入堆, 上还有元素，而且没有入过堆
            if(index1 - 1 >= 0 && !set.contains(index(index1 - 1, index2, M))) {
                heap.add(new Node(index1 - 1, index2, arr1[index1 - 1] + arr2[index2]));
                set.add(index(index1 - 1, index2, M));
            }
        }
        return res;
    }
    // 用一维坐标代替二维
    public static long index(int index1, int index2, int M) { 
        return index1 * M + index2;
    }
}

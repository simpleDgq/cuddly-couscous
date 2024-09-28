package class35;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

// 347. 前 K 个高频元素
// https://leetcode.cn/problems/top-k-frequent-elements/description/
public class Problem_0347_TopKFrequentElements {
    /**
     * 思路：小根堆+词频表
     * 1. 遍历一遍数组，建立一个词频表，记录在map中，key是数，value是每个数出现的次数
     * 2. 搞一个小根堆，按照词频进行排序，词频小的放在上面。
     * 
     * 放入小根堆的过程：
     * 遍历词频表，如果堆中的元素个数小于k个则直接加入小根堆
     * 如果等于k个而且当前元素的词频比堆顶元素大，则直接加入堆
     * 如果堆中的元素个数超过k个，弹出堆顶元素，保持堆中元素个数只有k个
     * 最后收集堆中的元素就是词频最高的k个元素
     */
    class Node {
        public int num;
        public int count;

        public Node(int num, int count) {
            this.num = num;
            this.count = count;
        }
    }

    // 按照词频降序排序
    class NodeComparator implements Comparator<Node> { // 记住怎么实现比较器的
        public int compare(Node o1, Node o2) {
            return o1.count - o2.count;
        }
    }
    
    public int[] topKFrequent(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k > nums.length) {
            return null;
        }
        // 建立词频表
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int num : nums) {
            if (map.containsKey(num)) {
                map.put(num, map.get(num) + 1);
            } else {
                map.put(num, 1);
            }
        }
        // 建好小根堆，放入k个元素
        PriorityQueue<Node> heap = new PriorityQueue<Node>(new NodeComparator());
        // 遍历map中的每一个元素
        for(int num : map.keySet()) { // 记住map怎么取key的
            int count = map.get(num);
            Node node = new Node(num, count);
            // 不足k个或者到了k个但是当前元素的词频比堆顶元素大，直接加入
            if(heap.size() < k || (heap.size() == k && count > heap.peek().count)) {
                heap.add(node);
            }
            // 如果堆的大小超过k个，移除最小的堆顶元素，保持堆中只有k个元素
            if(heap.size() > k) {
                heap.poll(); // 记住怎么弹出堆顶的
            }
        }
        // 堆中剩下的k个元素就是答案
        int ans[] = new int[k];
        int index = 0;
        while(!heap.isEmpty()) {
            ans[index++] = heap.poll().num;
        }
        return ans;
    }
}

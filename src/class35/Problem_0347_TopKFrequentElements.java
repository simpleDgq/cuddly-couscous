package class35;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

// 347. 前 K 个高频元素
// https://leetcode.cn/problems/top-k-frequent-elements/description/
public class Problem_0347_TopKFrequentElements {
    /**
     * 给你一个整数数组 nums 和一个整数 k ，请你返回其中出现频率前 k 高的元素。你可以按 任意顺序 返回答案。
     */
    
    
    /**
     * 思路: 词频表 + 小根堆
     * 1. 遍历一遍数组，建立一个词频表，记录数组中，每个数出现的次数
     * 2. 搞一个小根堆，按照词频进行排序，词频小的放在上面
     * 遍历词频表，如果小根堆中的元素小于k，直接放入词频表中的元素;
     * 如果等于k，则将当前遍历到的元素的词频和堆顶元素的词频进行比较;
     * 如果大于，则将遍历到的元素加入到小根堆
     */
    public static int[] topKFrequent(int[] nums, int k) {
        if(nums == null || nums.length == 0 || k <= 0) {
            return null;
        }
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        for(int i = 0; i <= nums.length - 1; i++) {
            if(map.containsKey(nums[i])) {
                map.put(nums[i], map.get(nums[i]) + 1);
            } else {
                map.put(nums[i], 1);
            }
        }
        
        // 小跟堆，词频排序
        PriorityQueue<Node> heap = new PriorityQueue<Node>(new NodeComparatpr());
        for(int key : map.keySet()) {
            int count = map.get(key);
            Node node = new Node(key, count);
            // 如果堆中的元素个数小于k，则继续往里面添加Node
            if(heap.size() < k) {
                heap.add(node); 
            } else if(heap.size() == k && count > heap.peek().count) {
                // 这里需要是else if，否则会报错。因为上面的if添加了元素之后，heap的size可能就是k了，而且堆可能经过
                // 调整之后，堆顶的元素小于当前已经被加入堆中的元素。这种情况是不能继续加当前节点的，因为已经加了，而且可能被
                // 调整到了堆的下面
                // 如果等于k了，判断要加入的元素的词频，是不是比堆顶的大，是就加入
                heap.add(node);
                // 移除掉堆顶，保留K个元素
                heap.poll();
            }
        }
        int ans[] = new int[k];
        // 堆中剩余的元素就是答案
        int i = 0;
        while(!heap.isEmpty()) {
            ans[i++] = heap.poll().num;
        }
        return ans;
    }
    
    public static class NodeComparatpr implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            // TODO Auto-generated method stub
            return o1.count - o2.count;
        }
    }
    
    public static class Node {
        int num;
        int count;
        public Node(int num, int count) {
            this.num = num;
            this.count = count;
        }
    }
}

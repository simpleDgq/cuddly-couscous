package class06;

// 本题测试链接 : https://leetcode.com/problems/maximum-xor-of-two-numbers-in-an-array/
public class Code02_MaximumXorOfTwoNumbersInAnArray {
    /*
     * 数组中两个数的最大异或值 [M]
     * 
     * 前面一题是把某一个前缀异或和放到结构里去。
     *   这题就是把裸的数字放到结构里去每一个数字去选一个自己的最好的。
     *   你来到i的时候，把之前所有的数到 number Trie里面去, i来了直接选让前缀树告诉你你跟谁结合最大,
     *   然后把你自己再放到Trie面去，再处理下一个。
     */
    
    public static class Node {
        Node nexts[] = new Node[2];
    }
    public static class NumTrie {
       
        Node head = new Node();
        public void add(int num) {
            Node cur = head;
            for(int i = 31; i >= 0; i--) {
                int path = (num >> i) & 1;
                cur.nexts[path] = cur.nexts[path] == null ? new Node() : cur.nexts[path];
                cur = cur.nexts[path];
            }
        }
        public int maxXor(int num) {
            Node cur = head;
            int ans = 0;
            for(int i = 31; i >= 0; i--) {
                int path = (num >> i) & 1;
                int best = i == 31 ? path : (path ^ 1);
                best = cur.nexts[best] == null ? best ^ 1 : best;
                ans |= ((path ^ best) << i);
                cur = cur.nexts[best];
            }
            return ans;
        }
    }
    
    public int findMaximumXOR(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if(nums.length <= 1) { // 必须有两个数
            return 0;
        }
        int max = 0;
        NumTrie numTrie = new NumTrie();
        numTrie.add(nums[0]); // 必须有两个数，所以先添加一个数进去
        
        for(int i = 0; i <= nums.length - 1; i++) {
            max = Math.max(max, numTrie.maxXor(nums[i]));
            numTrie.add(nums[i]);
        }
        return max;
    }
}

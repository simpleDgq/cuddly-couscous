package class06;

//https://leetcode.com/problems/maximum-xor-with-an-element-from-array/
public class Code03_MaximumXorWithAnElementFromArray {
    
    /* 1707. 与数组中元素的最大异或值
     * 给你一个由非负整数组成的数组 nums 。另有一个查询数组 queries ，其中 queries[i] = [xi, mi] 。
     * 
     * 求xi于nums里面的哪个数异或的值最大，但是与xi异或的值不能超过mi
     */
    /*
     * 前缀树结构可以往里添加数字
        需要提供方法maxXor: 告诉我这里面的数字哪一个跟 X 结合能出最大值，但是与 X 结合的数字不能大于M。
        就是所有小于等于 M 的数字是 X 是可以考虑的，就相当于你加了限制
     */
    
    public static class Node{
        Node nexts[] = new Node[2];
        int minValue = Integer.MAX_VALUE; // 每个节点上，记录一下，当前节点出发，所有可能的数的最小值
    }
    public static class NumTrie { 
        Node head = new Node();
        
        public void add(int num) {
            Node cur = head;
            // 每来一个数字，要设置头的最小值
            cur.minValue = Math.min(num, cur.minValue);
            for(int i = 30; i >= 0; i--) { // 都是正数，从30位开始
                int path = (num >> i) & 1;
                cur.nexts[path] = cur.nexts[path] == null ?  new Node() : cur.nexts[path];
                cur = cur.nexts[path];
                // 设置下一个节点的最小值
                cur.minValue = Math.min(num, cur.minValue);
            }
        }
        // 前缀树上返回与X异或最大的结果，但是与X异或的数不能超过M
        public int maxXorWhitXBehindM(int X, int M) {
            if(head.minValue > M) { // 假设树的最小值是300，但是M是200。表示的是不能超过200，但是最小已经都300，不能玩，直接返回-1
                return -1;
            }
            Node cur = head;
            int ans = 0;
            for(int i = 30; i >= 0; i--) {
                int path = (X >> i) & 1;
                int best = path ^ 1;
                // 如果存在走向最优的路并且最优的路的最小值小于等于M，直接走向最优的路，否则都走向相反的路
                best = (cur.nexts[best] != null && cur.nexts[best].minValue <= M) ? best : best ^ 1;
                
                // 搜集答案
                ans |= ((path ^ best) << i);
                // 下一跳
                cur = cur.nexts[best];
            }
            return ans;
        }
    } 
   
    public int[] maximizeXor(int[] nums, int[][] queries) {
        // nums所有的数建好前缀树
        NumTrie numTrie = new NumTrie();
        for(int i = 0; i <= nums.length - 1; i++) {
            numTrie.add(nums[i]);
        }
        
        // 对queries的每一个数去查询树上满足条件的最大答案
        int N = queries.length;
        int ans[] = new int[N];
        for(int j = 0; j <= N - 1; j++) {
            int query[] = queries[j];
            ans[j] = numTrie.maxXorWhitXBehindM(query[0], query[1]);
        }
        return ans;
    }
}

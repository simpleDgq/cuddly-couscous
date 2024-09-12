package class37;

import java.util.HashMap;

// 437. 路径总和 III
// https://leetcode.cn/problems/path-sum-iii/description/
public class Problem_0437_PathSumIII {
    /**
     * 给定一个二叉树的根节点 root ，和一个整数 targetSum ，求该二叉树里节点值之和等于 targetSum 的 路径 的数目。
     * 
     * 路径 不需要从根节点开始，也不需要在叶子节点结束，但是路径方向必须是向下的（只能从父节点到子节点）。
     */
    
    /**
     * 思路:
     * 
     * 和求数组里面有多少个子数组的和是targetSum
     * 
     * 利用前缀和数组 + HashMap
     * 
     * 比如数组0~100的和是1000，假设targetSum是100，1000 - 100 = 900， 
     * 以10位置结尾的的子数组和是900，那么从11 到100的子数组的和就是100，就找到了1个
     * 如果找出所有i位置结尾的数组的和是900的，就找到了所有的答案
     * 
     * 
     * 这一题也是一样，在二叉树上，遍历经过的节点的和就是前缀和，当到达一个节点之后，加上当前这个节点
     * 得到以当前这个节点为尾部的累加和sum，sum减去targetSum，如果之前的位置出现过sum-targetSum， 
     * 出现的次数就是答案
     * 当前节点搞完之后，再去左右节点递归搜集答案，累加
     * 当某一个节点的左右节点搞完之后，需要返回上一层节点，返回之前，由于当前节点造成的累加和，得在map里面去掉
     * 
     * 
     * 搞一个map记录，路径上累加和出现的次数，初始状态必须放一个(0,1)进去
     * 例如如果只有一个节点8，要求的targetSum也是8，8-8=0，需要判断map里面0出现了几次
     * 如果不放(0,1)到map里面，这种case就搜集不到答案
     * 
     * 
     * 
     */
    public class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;
    }

    public int pathSum(TreeNode root, int targetSum) {
        if(root == null) {
            return 0;
        }
        // key: 累加和 value: 累加和出现的次数
        HashMap<Long, Integer> map = new HashMap<Long, Integer>(); // 代码里面的long，完全是为了过leetcode，因为最后一个case，超过long的范围了
        map.put(0L, 1);
        return (int)process(root, 0, targetSum, map);
     }
    // root: 当前所在的节点
    // sum: 当前节点之前进过的路径生成的前缀和
    // targetSum: 要求的目标和
    // map: 累加和记录
    // 从root节点出发，root节点之前经过的路径的和是preSum，同时我告诉你，经过的路径上，出现过的累加和
    // 有哪些，次数是多少，你给我返回root节点出发，有多少条路径的累加和是targetSum
    public long process(TreeNode root, long preSum, int targetSum, HashMap<Long, Integer> map) {
        if(root == null) { // 如果是null, 搞不出来
            return 0;
        }
        long ans = 0;
        // 当前节点加上前面的累加和
        long sum = root.val + preSum;
        // 如果map中有sum - targetSum，说明以当前节点结尾的路径，有答案，搜集答案
        if(map.containsKey(sum - targetSum)) {
            ans = map.get(sum - targetSum);
        } 
        // 将以当前节点作尾节点的累加和加入到map中
        if(map.containsKey(sum)) { // 如果map中已经有了，次数加1
            map.put(sum, map.get(sum) + 1);
        } else { // 没有，直接放入
            map.put(sum, 1);
        }
        
        // 搞完当前节点，然后去遍历当前节点的左右子树，搜集到的答案累加
        ans += process(root.left, sum, targetSum, map);
        ans += process(root.right, sum, targetSum, map);
        // 搞完当前节点的左右子树，当前节点该返回上一层节点了，
        // 如果map中由当前节点造出来的累加和次数是1，减减之后是0，应该从map中删去
        // 如果不是1，次数应该--
        if(map.get(sum) == 1) {
           map.remove(sum);
        } else {
            map.put(sum, map.get(sum) - 1);
        }
        return ans;
    }
}

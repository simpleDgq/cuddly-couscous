package class30;
// 124. 二叉树中的最大路径和
public class Problem_0124_BinaryTreeMaximumPathSum {
    /**
     * 二叉树中的 路径 被定义为一条节点序列，序列中每对相邻节点之间都存在一条边。
     * 同一个节点在一条路径序列中 至多出现一次 。该路径 至少包含一个 节点，且不一定经过根节点。
     * 路径和 是路径中各节点值的总和。
     * 给你一个二叉树的根节点 root ，返回其 最大路径和 。
     */
    /**
     * 思路:
     * 二叉树递归套路
     * 
     * 可能性:
     * 1. 最大路径和和X无关
     *    1)最大路径和出现在左子树中，也就是左子树最大的路径和maxPathSum
     *    2)最大路径和出现在右子树中，也就是右子树最大的路径和maxPathSum
     * 2. 最大路径和和X有关
     *    1) 最大路径和是X本身
     *    2) 最大路径和是X出发，往左子树上扎，也就是左子树的maxPathSumFromHead
     *    3) 最大路径和是X出发，往右子树上扎，也就是右子树的maxPathSumFromHead
     *    4) 最大路径和是经过X，从左子树扎到右子树，或者从右子树扎到左子树，也就是左子树的maxPathSumFromHead + 右子树的maxPathSumFromHead + X.val
     *    
     * 要搜集的信息:
     * 1)整棵树的maxPathSum
     * 2)以某个节点为头出发的最大路径和，maxPathSumFromHead  
     */
    // 题目提供
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int v) {
            val = v;
        }

    }
    // 要搜集的信息
    public class Info {
        public int maxPathSum;
        public int maxPathSumFromHead;
        
        public Info(int maxPathSum, int maxPathSumFromHead) {
            this.maxPathSum = maxPathSum;
            this.maxPathSumFromHead = maxPathSumFromHead;
        }
    }
    
    public int maxPathSum(TreeNode root) {
        if(root == null) {
            return 0;
        }
        return process(root).maxPathSum;
    }
    
    public Info process(TreeNode X) {
        if(X == null) {
            return null; // 空树没有这些信息，直接返回null
        }
        // 以X为头节点，整颗树构造Info
        Info leftInfo = process(X.left);
        Info rightInfo = process(X.right);
        // 求maxPathSumFromHead。也就是以X为头的整棵树，必须从X出发，最大路径和是多少
        int maxPathSumFromHead = X.val; // 只有X节点自己
        // 如果有左子树，和左子树PK
        if(leftInfo != null) {
            // leftInfo.maxPathSumFromHead是X的左子树，以左子树的头节点出发，最大路径和，现在求的是以X为头的整棵树，往左扎
            // 最大路径和，所以要加X.val
            maxPathSumFromHead = Math.max(maxPathSumFromHead, leftInfo.maxPathSumFromHead + X.val); 
        }
        // 同理如果有右子树
        if(rightInfo != null) {
            maxPathSumFromHead = Math.max(maxPathSumFromHead, rightInfo.maxPathSumFromHead + X.val); 
        }
        
        // 求maxPathSum。也就是以X为头的整棵树，最大路径和是多少
        int maxPathSum = X.val; // 只有X节点自己
        // 答案只来自于左子树
        if(leftInfo != null) {
            maxPathSum = Math.max(maxPathSum, leftInfo.maxPathSum);
        }
        // 答案只来自于右子树
        if(rightInfo != null) {
            maxPathSum = Math.max(maxPathSum, rightInfo.maxPathSum);
        }
        // 答案来自于必须以X为头，往左子树扎，或者必须以X为头，往右子树扎，取最大值
        // maxPathSumFromHead 就是前面已经计算出来的，必须以X为头，往左或者往右，能扎出来的最大值
        maxPathSum =  Math.max(maxPathSum, maxPathSumFromHead);
        // 经过X节点，从左往右，或者从右往左
        // 左信息和右信息必须存在，而且左子树的maxPathSum 和 右子树的maxPathSum 都必须大于0，这样才有可能将答案推高
        if(leftInfo != null && rightInfo != null && leftInfo.maxPathSumFromHead > 0 && rightInfo.maxPathSumFromHead > 0) {
            maxPathSum = Math.max(maxPathSum, leftInfo.maxPathSumFromHead + rightInfo.maxPathSumFromHead + X.val);
        }
        return new Info(maxPathSum, maxPathSumFromHead);
    }
    
    
    /**
     * 扩展:
     * 如果要返回路径的做法 -- 略
     */
}

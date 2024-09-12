package class33;

import java.util.Stack;

// https://leetcode.cn/problems/kth-smallest-element-in-a-bst
public class Problem_0230_kthSmallest {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
    }

    /**230. 二叉搜索树中第 K 小的元素
     * 给定一个二叉搜索树的根节点 root ，和一个整数 k ，请你设计一个算法查找其中第 k 小的元素（从 1 开始计数）。
     * 
     * 1. 中序遍历 的过程中k--，k为0的时候，弹出的节点就是答案
     * 从头结点开始，当前节点的左边界节点一直入栈，直到当前节点为null
     * 当前节点为null，则从栈中弹出元素进行打印，同时cur变成当前节点的右节点，
     * 然后从右节点的左边界又一直入栈。
     * 
     * 时间复杂度：O(H+k)，其中 H 是树的高度。在开始遍历之前，我们需要 O(H) 到达叶结点。
     * 当树是平衡树时，时间复杂度取得最小值 O(logN+k)；当树是线性树
     * （树中每个结点都只有一个子结点或没有子结点）时，时间复杂度取得最大值 O(N+k)。
     * 
     * 空间复杂度：O(H)，栈中最多需要存储 H 个元素。当树是平衡树时，
     * 空间复杂度取得最小值 O(logN)；当树是线性树时，空间复杂度取得最大值 O(N)。
     * 
     * 2. Morris遍历
     */
    public int kthSmallest(TreeNode root, int k) {

        Stack<TreeNode> stack = new Stack<TreeNode>();
        TreeNode cur = root;
        while (!stack.isEmpty() || cur != null) {
            // 头节点开始，左边界一直入队
            if (cur != null) {
                stack.push(cur);
                cur = cur.left;
            } else {
                // 出栈
                cur = stack.pop();
                k--;
                if (k == 0) {
                    break;
                }
                // 当前节点指向右节点
                cur = cur.right;
            }
        }
        return cur.val;
    }
}

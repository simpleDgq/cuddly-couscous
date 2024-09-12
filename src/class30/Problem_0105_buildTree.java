package class30;

import java.util.HashMap;

// https://leetcode.cn/problems/construct-binary-tree-from-preorder-and-inorder-traversal
public class Problem_0105_buildTree {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
        TreeNode(int val) {
            this.val = val;
        }
    }
    /* 105. 从前序与中序遍历序列构造二叉树
     * 给定两个整数数组 preorder 和 inorder ，其中 preorder 是二叉树的先序遍历， 
     * inorder 是同一棵树的中序遍历，请构造二叉树并返回其根节点。
     * 
     * 思路：先序的第一个节点是头结点。找出其在中序中的位置，作为划分，将中序遍历划分为左右两部分
     * 然后，递归建立左右子树。
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder == null || inorder == null || preorder.length != inorder.length) {
            return null;
        }
        // key: 中序遍历的值
        // value: 该值在中序遍历数组中的下标
        HashMap<Integer, Integer> valueIndex = new HashMap<Integer, Integer>();
        for (int i = 0; i <= inorder.length - 1; i++) {
            valueIndex.put(inorder[i], i);
        }
        return build2(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1, valueIndex);
    }

    public TreeNode build2(int[] preorder, int L1, int R1, int[] inorder, int L2, int R2, HashMap<Integer, Integer> valueIndex) {
        if (L1 > R1) { // 越界了。
            return null;
        }
        TreeNode head = new TreeNode(preorder[L1]);
        if (L1 == R1) { // 只有一个节点，直接返回作为头结点
            return head;
        }
        // 下面的解法每次遍历都需要查找，可优化，将中序遍历中的数据存在一张表hashmap中，每次直接取出来
        // 直接取出来，不用遍历（空间换时间）
        int find = (int) valueIndex.get(preorder[L1]);
        head.left = build2(preorder, L1 + 1, L1 + (find - L2), inorder, L2, find - 1, valueIndex);
        head.right = build2(preorder, L1 + (find - L2) + 1, R1, inorder, find + 1, R2, valueIndex);
        return head;
    }
    

    // 给定先序、中序数组，以及分别开始的下标，递归建立左右子树。返回头结点
    public TreeNode build(int[] preorder, int L1, int R1, int[] inorder, int L2, int R2) {
        if (L1 > R1) { // 越界了。 左子树为空，或者右子树为空，会出现这种越界情况
            return null;
        }
        TreeNode head = new TreeNode(preorder[L1]);
        if (L1 == R1) { // 只有一个节点，直接返回作为头结点
            return head;
        }
        int find = L2; // 从中序最开始位置，往后找，找到头结点的位置
        while (preorder[L1] != inorder[find]) { // 每次遍历都需要查找，可优化，将中序遍历中的数据存在一张表hashmap中，每次直接取出来 ==> 得到build2
            find++;
        }
        // 找到了中序遍历中头结点的位置
        // 递归建立左子树. L1 + (find - L2) ==> L1开始的位置 + 左子树元素个数
        head.left = build(preorder, L1 + 1, L1 + (find - L2), inorder, L2, find - 1);
        // 递归建立右子树，并且连接到head
        head.right = build(preorder, L1 + (find - L2) + 1, R1, inorder, find + 1, R2);
        return head;
    }
}

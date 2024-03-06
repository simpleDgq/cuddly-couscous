package class37;

// 226. 翻转二叉树
// https://leetcode.cn/problems/invert-binary-tree/description/
public class Problem_0226_InvertBinaryTree {
    /**
     * 给你一棵二叉树的根节点 root ，翻转这棵二叉树，并返回其根节点。
     */
    /**
     * 思路:
     * 递归 二叉树的遍历
     * 
     * 头结点下面的两个节点先交换，
     * 然后分别递归交换左右子树的节点
     */
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    // 先序遍历
    public TreeNode invertTree(TreeNode root) {
        if(root == null) {
           return null; 
        }
        // 交换头节点的左右节点
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
        // 然后左子树的两个节点进行交换
        invertTree(root.left);
        // 然后右子树的两个节点进行交换
        invertTree(root.right);
        
        return root;
    }
}

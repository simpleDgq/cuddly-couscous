package class30;

// 101. 对称二叉树
public class Problem_0101_SymmetricTree {
    /**
     * 给你一个二叉树的根节点 root ， 检查它是否轴对称。
     * 
     * 思路：传入两个root，相当于两颗树进行比较
     */
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
    }
    
    public boolean isSymmetric(TreeNode root) {
        if(root == null) {
            return false;
        }
        // 传入同一个头结点
        return compareLeftWithRight(root, root);
    }

    public boolean compareLeftWithRight(TreeNode root1, TreeNode root2) {
        // 一个节点是null，一个不是null，必不是相同的树
        if((root1 == null && root2 != null) || (root1 != null && root2 == null)) {
            return false;
        }
        // 两个节点都是null，必是相同的树
        if(root1 == null && root2 == null) {
            return true;
        }

        // 将root1的左子树与root2的右子树进行比较; 同时将root1的右子树和root2的左子树进行比较
        // 值相等
        return root1.val == root2.val && compareLeftWithRight(root1.left, root2.right) 
        && compareLeftWithRight(root1.right, root2.left);
    }
}

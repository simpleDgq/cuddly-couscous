package class38;

// 617. 合并二叉树
// https://leetcode.cn/problems/merge-two-binary-trees/description/
public class Problem_0617_MergeTwoBinaryTrees {
    /**
     * 二叉树的递归套路
     */
    public class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int val) {
            this.val = val;
        }
    }
    // 当前，一棵树的头是t1，另一颗树的头是t2
    // 请返回，整体merge之后的头
    public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
        if(root1 == null) {
            return root2;
        }
        if(root2 == null) {
            return root1;
        }
        // 构造好头
        TreeNode root = new TreeNode(root1.val + root2.val);
        // 以X为头结点的二叉树，分别构造好左子树和右子树
        root.left = mergeTrees(root1.left, root2.left);
        root.right = mergeTrees(root1.right, root2.right);
        return root;
    }
}

package class32;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// https://leetcode.cn/problems/binary-tree-right-side-view
public class Problem_0199_rightSideView {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
    }
    /**199. 二叉树的右视图
     * 给定一个二叉树的 根节点 root，想象自己站在它的右侧，按照从顶部到底部的顺序，返回从右侧所能看到的节点值。
     * 
     * 思路：
     * 1.先层次遍历，再取每一层的最后一个元素就好啦
     * 
     * 2. 深度优先遍历也能做，先遍历有子树，在遍历左子树
     * 当某个深度首次到达时，记录答案
     * 
     */
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> ans = new ArrayList<Integer>();
        if (root == null) {
            return ans;
        }
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        // 头节点入队列
        queue.add(root);
        while (!queue.isEmpty()) {
            // 队列的大小就是每一层的节点数
            int size = queue.size();
            // 一层层打印
            for (int i = 0; i <= size - 1; i++) {
                TreeNode cur = queue.poll();
                // 每一层的最后一个节点，加入ans中
                if (i == size - 1) {
                    ans.add(cur.val);
                }
                if (cur.left != null) {
                    queue.add(cur.left);
                }
                if (cur.right != null) {
                    queue.add(cur.right);
                }
            }
        }
        return ans;
    }

// 深度优先遍历解法
    // public List<Integer> rightSideView(TreeNode root) {
    // List<Integer> ans = new ArrayList<>();
    // dfs(root, 0, ans);
    // return ans;
    // }

    // private void dfs(TreeNode root, int depth, List<Integer> ans) {
    // if (root == null) {
    // return;
    // }
    // if (depth == ans.size()) { // 这个深度首次遇到
    // ans.add(root.val);
    // }
    // dfs(root.right, depth + 1, ans); // 先递归右子树，保证首次遇到的一定是最右边的节点
    // dfs(root.left, depth + 1, ans);
    // }
}

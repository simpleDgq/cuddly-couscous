package class30;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// 103. 二叉树的锯齿形层序遍历
public class Problem_0103_BinaryTreeZigzagLevelOrderTraversal {
    /**
     * 给你二叉树的根节点 root ，返回其节点值的 锯齿形层序遍历 。
     * （即先从左往右，再从右往左进行下一层遍历，以此类推，层与层之间交替进行）。
     */
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
    
    /**
     * 思路:
     * 二叉树的层次遍历，搞一个size，记录每层要弹出多少个
     * 有左加左，有右加右
     */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<>();
        if(root == null) {
            return ans;
        }
        // 队列
        LinkedList<TreeNode> deque = new LinkedList<>();
        // 头结点入队列
        deque.add(root);
        int size = 0; // 记录每一层有多少个节点，方便一次性全部弹出
        // 因为每一层要交替的从左往右和从右往左，搞一个变量记录，如果是true，就是从左往右，否则就是从右往左
        boolean direction = true;
        while(!deque.isEmpty()) {
            size = deque.size();
            // 记录当前层的节点
            List<Integer> curLevel = new ArrayList<Integer>();
            for(int i = 1; i <= size; i++) { // size个节点全部一次性出队列
                // 从左往右的话，先入队的先出；从右往左的话，先入队的后出
                TreeNode curNode = direction ? deque.pollFirst() : deque.pollLast();
                // 当前节点加入
                curLevel.add(curNode.val);
                // 有左加左，有右加右
                if(direction) { // 如果是要从左往右打印
                    // 先进先出，先加左节点，再加右节点到队列的最后
                    if (curNode.left != null) {
                        deque.addLast(curNode.left);
                    }
                    if (curNode.right != null) {
                        deque.addLast(curNode.right);
                    }
                } else { // 如果是要从右往左打印
                    // 先加右节点
                    if (curNode.right != null) {
                        deque.addFirst(curNode.right);
                    }
                    if (curNode.left != null) {
                        deque.addFirst(curNode.left);
                    }
                }
            }
            // 一层搞完，搞下一层，记录答案，同时方向要变为相反
            ans.add(curLevel);
            direction = !direction;
        }
        return ans;
    }   
}

package class14;

import java.util.Stack;

//本题测试链接 : https://leetcode.com/problems/recover-binary-search-tree/
public class Code05_RecoverBinarySearchTree {
    // 恢复搜索二叉树
    /**
     * 给你二叉搜索树的根节点 root ，该树中的两个节点被错误地交换。请在不改变其结构的情况下，恢复这棵树。

        进阶：使用 O(n) 空间复杂度的解法很容易实现。你能想出一个只使用常数空间的解决方案吗？
        
        示例 1：
        
        输入：root = [1,3,null,null,2]
        输出：[3,1,null,null,2]
        解释：3 不能是 1 左孩子，因为 3 > 1 。交换 1 和 3 使二叉搜索树有效。
        示例 2：
        
        输入：root = [3,1,4,null,null,2]
        输出：[2,1,4,null,null,3]
        解释：2 不能在 3 的右子树中，因为 2 < 3 。交换 2 和 3 使二叉搜索树有效。
        提示：
        
        树上节点的数目在范围 [2, 1000] 内
        -2^31 <= Node.val <= 2^31 - 1
     */
    
    /**
     * 
     * 思路:
     * 可能有两对或一对降序
        第一个错误节点是第一回降序的第一个节点，第二个错误节点是最后一回降序的第二个节点。
        可以把错误节点交换值(leetcode只要求交换值)，也可以从结构上彻底调对(这个很难，略过)。
     *
     */
    // 不要提交这个类
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int v) {
            val = v;
        }
    }

    // 如果能过leetcode，只需要提交这个方法即可
    // 但其实recoverTree2才是正路，只不过leetcode没有那么考
    public static void recoverTree(TreeNode root) {
        TreeNode[] errors = twoErrors(root);
        if (errors[0] != null && errors[1] != null) {
            int tmp = errors[0].val;
            errors[0].val = errors[1].val;
            errors[1].val = tmp;
        }
    }

    public static TreeNode[] twoErrors(TreeNode head) {
        TreeNode[] ans = new TreeNode[2];
        if (head == null) {
            return ans;
        }
        TreeNode cur = head;
        TreeNode mostRight = null;
        TreeNode pre = null;
        TreeNode e1 = null;
        TreeNode e2 = null;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) {
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    mostRight.right = null;
                }
            }
            if (pre != null && pre.val >= cur.val) {
                e1 = e1 == null ? pre : e1;
                e2 = cur;
            }
            pre = cur;
            cur = cur.right;
        }
        ans[0] = e1;
        ans[1] = e2;
        return ans;
    }
    
    // 使用中序遍历实现，时间复杂度O(N) 空间0(H) H表示树的高度
    public static void in(TreeNode head) {
        if(head == null) {
            return;
        }
        Stack<TreeNode> stack = new Stack<TreeNode>();
        TreeNode cur = head;
        TreeNode preNode = null; // 记录中序遍历的前一个节点
        TreeNode e1 = null; // 第一次降序的第一个节点
        TreeNode e2 = null; // 最后一次降序的第二个节点
        while(!stack.isEmpty() || cur != null) { // cur不为空，左边界节点一直入栈
            if(cur != null) {
                stack.push(cur);
                cur = cur.left;
            } else {
                cur = stack.pop();
//                System.out.println(cur.value);
                if(preNode != null && preNode.val >= cur.val) { // 降序了
                    e1 = e1 == null ? preNode : e1; // 记录第一次降序的第一个节点
                    e2 = cur; // 记录最后一次降序的第二个节点
                }
                preNode = cur; // 记录中序遍历的前一个节点
                cur = cur.right; // cur变成当前弹出节点的右节点，后面cur的left又会入栈
            }
        }
        // 交换e1和e2的值
        int temp = e1.val;
        e1.val = e2.val;
        e2.val = temp;
    }
    
}

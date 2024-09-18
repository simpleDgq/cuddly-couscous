package class30;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

// 注意，我们课上讲了一个别的题，并不是leetcode 114
// 我们课上讲的是，把一棵搜索二叉树变成有序链表，怎么做
// 而leetcode 114是，把一棵树先序遍历的结果串成链表
// 所以我更新了代码，这个代码是leetcode 114的实现
// 利用morris遍历
// 114. 二叉树展开为链表
// https://leetcode.cn/problems/flatten-binary-tree-to-linked-list
public class Problem_0114_FlattenBinaryTreeToLinkedList {

    // 这个类不用提交
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int value) {
            val = value;
        }
    }

    public class Info {
        TreeNode head;
        TreeNode tail;

        public Info(TreeNode head, TreeNode tail) {
            this.head = head;
            this.tail = tail;
        }
    }

    public void flatten(TreeNode root) {
        /**
         * 思路:
         * 1.先序遍历 时间复杂度: O(N) 空间复杂度：O(N)
         * 搞一遍先序遍历，将遍历的结果放在list中
         * 再遍历list，改变list中的节点的指针指向
         * 时间复杂度：O(N) 二叉数每个节点都要走一遍
         * 空间复杂度：O(N) list要存储N个二叉树节点
         * 
         * 2. 二叉树的递归套路 时间复杂度: O(N) 空间复杂度：O(N)
         * 搜集的Info
         * 左子树搞成链表之后的head和tail
         * 右子树搞成链表之后的head和tail
         * 
         * head 和 tail
         * 
         * 3. mirrors遍历 时间复杂度: O(N) 空间复杂度：O(1)
         * 
         * 假设来到当前节点cur，开始时cur来到头节点位置
         * 1）如果cur没有左孩子，cur向右移动(cur = cur.right)
         * 2）如果cur有左孩子，找到左子树上最右的节点mostRight：
         * a. 如果mostRight的右指针指向空，让其指向cur，
         * 然后cur向左移动(cur = cur.left)
         * b. 如果mostRight的右指针指向cur，让其指向null，
         * 然后cur向右移动(cur = cur.right)
         * 3）cur为空时遍历停止
         */
        if (root == null) {
            return;
        }
        // process(root);
        pre(root);

    }

    // 先序遍历做法
    public void pre(TreeNode root) {
        List<TreeNode> nodes = new ArrayList<TreeNode>();
        Stack<TreeNode> stack = new Stack<TreeNode>();
        // 头节点入栈，有右入右，有左入左
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode cur = stack.pop();
            nodes.add(cur);
            if (cur.right != null) {
                stack.push(cur.right);
            }
            if (cur.left != null) {
                stack.push(cur.left);
            }
        }
        // 遍历nodes，改写指针
        nodes.get(0).left = null;
        for (int i = nodes.size() - 1; i >= 1; i--) {
            TreeNode cur = nodes.get(i);
            cur.left = null;
            if (i == nodes.size() - 1) {
                cur.right = null;
            }
            nodes.get(i - 1).right = cur;
        }
    }

    // 二叉树递归套路做法
    public Info process(TreeNode root) {
        if (root == null) {
            // 空树，直接返回null
            return null;
        }
        // 搜集左右子树信息
        Info leftInfo = process(root.left);
        Info rightInfo = process(root.right);
        // 以X为头的整棵树，构造Info
        TreeNode head = root;
        head.left = null;
        head.right = leftInfo == null ? null : leftInfo.head;
        // 有左子树，尾节点变成左子树的尾
        TreeNode tail = leftInfo == null ? head : leftInfo.tail;
        // 尾结点的右指针应该指向右子树的头
        tail.right = rightInfo == null ? null : rightInfo.head;
        // 有右子树，尾节点应该变成右子树的尾
        tail = rightInfo == null ? tail : rightInfo.tail;
        return new Info(head, tail);
    }

    // Morris遍历的解
    public static void flatten2(TreeNode root) {
        if (root == null) {
            return;
        }
        TreeNode pre = null;
        TreeNode cur = root;
        TreeNode mostRight = null;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) {
                    mostRight.right = cur;
                    if (pre != null) {
                        pre.left = cur;
                    }
                    pre = cur;
                    cur = cur.left;
                    continue;
                } else {
                    mostRight.right = null;
                }
            } else {
                if (pre != null) {
                    pre.left = cur;
                }
                pre = cur;
            }
            cur = cur.right;
        }
        cur = root;
        TreeNode next = null;
        while (cur != null) {
            next = cur.left;
            cur.left = null;
            cur.right = next;
            cur = next;
        }
    }

}

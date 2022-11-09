package class10;

//本题测试链接 : https://leetcode.com/problems/convert-binary-search-tree-to-sorted-doubly-linked-list/
// https://www.lintcode.com/problem/1534/  可以来这里测试，这里不收费
public class Code04_BSTtoDoubleLinkedList {
    /**
     * 搜索二叉树转换成双向链表
     */
    
    /**
    二叉树的递归套路
    X 为头的整棵二叉树请变成有序双向链表返回 info 信息
    info信息两个: 转完之后的链表的头指针，和转完之后链表的尾指针而且只返回两个变量,
    但是我认为中间全部串好了。
    */
    // 提交时不要提交这个类
    public static class TreeNode {
        public int value;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int data) {
            this.value = data;
        }
    }
    
    public class Info {
        TreeNode start; // 转换完之后链表的头
        TreeNode end; // 转换完之后链表的尾
        public Info(TreeNode start, TreeNode end) {
           this.start = start;
           this.end = end;
        }
    }
    
    public Info process(TreeNode X) {
        if(X == null) { // 空树，转换完之后，头尾都是空
            return new Info(null, null);
        }
        // 左树搜集
        Info lInfo = process(X.left);
        // 右树搜集
        Info rInfo = process(X.right);
        // 生成X为头的整棵树的信息
        if(lInfo.end != null) { // 如果左边转成双向链表后的尾不是null，将它的右指针指向X
            lInfo.end.right = X;
        }
        X.left = lInfo.end; // X的left指向前面的end
        if(rInfo.start != null) {
            rInfo.start.left = X;
        }
        X.right = rInfo.start;
        // 返回
        return new Info(lInfo.start != null ? lInfo.start : X, rInfo.end != null ? rInfo.end : X);
    }
    
    public TreeNode treeToDoublyList(TreeNode head) { 
        if(head == null) {
            return null;
        }
        Info info = process(head);
        // 连接头尾
        info.end.right = info.start;
        info.start.left = info.end;
        return info.start;
    }

}

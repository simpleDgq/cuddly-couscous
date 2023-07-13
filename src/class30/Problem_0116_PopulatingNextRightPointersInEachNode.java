package class30;

// 116. 填充每个节点的下一个右侧节点指针
public class Problem_0116_PopulatingNextRightPointersInEachNode {
    /**
     * 
     * 给定一个 完美二叉树 ，其所有叶子节点都在同一层，每个父节点都有两个子节点。二叉树定义如下：
     * struct Node {
     *  int val;
     *  Node *left;
     *  Node *right;
     *  Node *next;
     * }
     * 填充它的每个 next 指针，让这个指针指向其下一个右侧节点。如果找不到下一个右侧节点，则将 next 指针设置为 NULL。
     * 初始状态下，所有 next 指针都被设置为 NULL。
     * 
     * 你只能使用常量级额外空间。
     */
    /**
     * 思路:
     * 层次遍历，用size记录每一层的节点数
     * 
     * 要求空间复杂度是常数时间，所以不能用系统的队列，得自己实现。
     */
    // 不要提交这个类
    public static class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;
    }

    public Node connect(Node root) {
        if(root == null) {
            return null;
        }
        MyQueue queue = new MyQueue();
        // 头节点入队列
        queue.offer(root);
        int size = 0;
        while(!queue.isEmpty()) {
            size = queue.size;
            // 一层上，节点的前一个节点
            Node pre = null;
            // 遍历每一层的节点，有左加左，有右加右，
            // 并且连接好每一层的节点
            for(int i = 1; i <= size; i++) {
                Node cur = queue.poll();
                if(cur.left != null) {
                    queue.offer(cur.left);
                }
                if(cur.right != null) {
                    queue.offer(cur.right);
                }
                // 前一个节点的next连接cur
                if(pre != null) {
                    pre.next = cur;
                }
                // pre 往下跳
                pre = cur;
            }
        }
        return root;
    }
    
    public class MyQueue {
        public Node head;
        public Node tail;
        public int size;
        
        public MyQueue() {
            head = null;
            tail = null;
            size = 0;
        }
        
        public boolean isEmpty() {
            return size == 0;
        }
        // 队尾添加节点
        public void offer(Node cur) {
            size++;
            if(head == null) {
                head = tail = cur;
            } else {
                tail.next = cur;
                tail = cur;
            }
        }
        
        // 从队列头取出节点
        public Node poll() {
            size--;
            // 保留头
            Node ans = head;
            // 头指针向下移动
            head = head.next;
            ans.next = null;
            return ans;
        }
    }
}

package class28;

// https://leetcode.cn/problems/swap-nodes-in-pairs
//24. 两两交换链表中的节点
public class Problem_0024_SwapPairs {
    /**思路：
     * 搞一个temp节点，每次都要交换它 后面的两个节点。
     * temp是一个dummy节点，刚开始next指向头节点。
     * 如果 temp 的后面没有节点或者只有一个节点，则没有更多的节点需要交换
     */
    
    public class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
    
    public ListNode swapPairs(ListNode head) {
        if(head == null || head.next == null) {
            return head;
        }
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode temp = dummy;
        
        while(temp.next != null && temp.next.next != null) {
            ListNode node1 = temp.next;
            ListNode node2 = temp.next.next;
            // temp -> node1 -> node2 -> ... 变成temp -> node2 -> node1 ->...
            node1.next = node2.next;
            node2.next = node1;
            temp.next = node2;
            // 从新的起始temp开始搞
            temp = node1;
        }
        return dummy.next;
    }
    
}

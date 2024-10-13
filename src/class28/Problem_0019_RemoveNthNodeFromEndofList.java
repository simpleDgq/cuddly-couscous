package class28;

// 19. 删除链表的倒数第 N 个结点
public class Problem_0019_RemoveNthNodeFromEndofList {
    /**
     * 给你一个链表，删除链表的倒数第 n 个结点，并且返回链表的头结点。
     */
    
    /**
     * 思路：
     * 找要删除的节点的上一个节点
     * 
     * tips: 由于可能会删除链表头部，用哨兵节点简化代码
     * 1.cur 先走n步。然后pre指向head，一起往下走
     * 2.当cur.next等于null的时候，pre指向的就是要删除的节点的上一个节点
     */
    
    public class ListNode {
         int val;
         ListNode next;
         ListNode() {}
         ListNode(int val) { this.val = val; }
         ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
    

    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null || n <= 0) {
            return head;
        }
        // 由于可能会删除链表头部，用哨兵节点简化代码
        ListNode dummy = new ListNode(0, head);
        ListNode cur = dummy;
        ListNode pre = dummy;
        // cur 先走n步
        while (n-- != 0) {
            cur = cur.next;
        }
        // pre指向head，一起往下走
        while (cur.next != null) {
            pre = pre.next;
            cur = cur.next;
        }
        // pre指向了要删除节点的上一个节点
        pre.next = pre.next.next;
        return dummy.next;
    }
    

}

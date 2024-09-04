package class28;

// 19. 删除链表的倒数第 N 个结点
public class Problem_0019_RemoveNthNodeFromEndofList {
    /**
     * 给你一个链表，删除链表的倒数第 n 个结点，并且返回链表的头结点。
     */
    
    /**
     * 思路:
     * 有17个节点, 删掉倒数第8个
     * 先数够8个, 然后要删的节点指针指向第一个节点
     * 接下来两个指针共同往下走
     * 当第一个指针到最后一个的时候, 待删除指针指向的就是要删除的节点
     * 
     * 其实真正要找到时倒数第9个, 也就是倒数第8个节点的前一个节点，
     * 因为要从前一个节点连接到8的下个节点
     * 
     * 题目要求用一次遍历做到。
     * 
     * 这题还有点不太好写。
     * 
     * 
     * 双指针
     * 
     * cur从头节点开始，走n + 1步
     * 然后pre从头开始，和cur一起走
     * 
     * 当cur为null的时候，将pre.next指向pre.next.next
     */
    
    public class ListNode {
         int val;
         ListNode next;
         ListNode() {}
         ListNode(int val) { this.val = val; }
         ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
    
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode cur = head;
        ListNode pre = null;
        
        while(cur != null) {
            n--;
            // cur走到了要删除节点的前一个位置，将pre指向head
            // 后面pre和cur一起往后走
            if(n == -1) {
                pre = head;
            }
            // pre和cur指针一起往后走，当cur走向空的时候，pre就指向了要删除的节点的前一个节点
            if(n < -1) {
                pre = pre.next;
            }
            cur = cur.next;
        }
        // 如果n > 0, 说明链表的长度小于n。 比如链表长度是2，要删除倒数第3个节点，显然没有
        // 这种情况直接返回头节点就行
        if(n > 0) {
            return head;
        }
        // 如果n == 0, 说明要删除的是头节点
        // 例如 3个节点，要删除倒数第三个节点，当cur等于null的时候，n = 0
        // 经过上面的while循环之后，pre是null
        if(n == 0) {
            return head.next; 
        }
        // 删除要删除的节点
        pre.next = pre.next.next;
        
        return head;
    }
}

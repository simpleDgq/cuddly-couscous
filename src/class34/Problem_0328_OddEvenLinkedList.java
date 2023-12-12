package class34;

// 328. 奇偶链表

public class Problem_0328_OddEvenLinkedList {
    /**
     * 给定单链表的头节点 head ，将所有索引为奇数的节点和索引为偶数的节点分别组合在一起，然后返回重新排序的列表。
     * 第一个节点的索引被认为是 奇数 ， 第二个节点的索引为 偶数 ，以此类推。
     * 请注意，偶数组和奇数组内部的相对顺序应该与输入时保持一致。
     * 
     * 你必须在 O(1) 的额外空间复杂度和 O(n) 的时间复杂度下解决这个问题。
     */
    
    /**
     * 思路: 分离节点后合并
     * 
     * 如果链表为空，则直接返回链表。
     * 对于原始链表，每个节点都是奇数节点或偶数节点。头节点是奇数节点，头节点的后一个节点是偶数节点，
     * 相邻节点的奇偶性不同。因此可以将奇数节点和偶数节点分离成奇数链表和偶数链表，然后将偶数链表连接在奇数链表之后，
     * 合并后的链表即为结果链表。
     * 
     * 原始链表的头节点 head 也是奇数链表的头节点以及结果链表的头节点，head 的后一个节点是偶数链表的头节点。
     * 令 evenHead = head.next，则 evenHead 是偶数链表的头节点。
     * 
     * 维护两个指针 odd 和 even 分别指向奇数节点和偶数节点，初始时 odd = head，even = evenHead。
     * 通过迭代的方式将奇数节点和偶数节点分离成两个链表，每一步首先更新奇数节点，然后更新偶数节点。
     * 
     * 更新奇数节点时，奇数节点的后一个节点需要指向偶数节点的后一个节点，因此令 odd.next = even.next，
     * 然后令 odd = odd.next，此时 odd 变成 even 的后一个节点。
     * 
     * 更新偶数节点时，偶数节点的后一个节点需要指向奇数节点的后一个节点，因此令 even.next = odd.next，
     * 然后令 even = even.next，此时 even 变成 odd 的后一个节点。
     * 
     * 在上述操作之后，即完成了对一个奇数节点和一个偶数节点的分离。重复上述操作，直到全部节点分离完毕。
     * 全部节点分离完毕的条件是 even 为空节点或者 even.next 为空节点，此时 odd 指向最后一个奇数节点（即奇数链表的最后一个节点）。
     * 
     * 最后令 odd.next = evenHead，将偶数链表连接在奇数链表之后，即完成了奇数链表和偶数链表的合并，结果链表的头节点仍然是 head。
     */
    
    public static class ListNode {
        int val;
        ListNode next;
    }
    
    public ListNode oddEvenList(ListNode head) {
        if(head == null) {
            return null;
        }
        ListNode odd = head; // 奇数链表头节点
        ListNode evenHead = head.next; // 偶数链表头节点
        
        ListNode even = evenHead;
        // 这个条件有点难理解
        // 奇数链表去链接偶数链表，偶数链表的最后一个节点的next必须指向null，或者最后一个节点本身就是null
        // 举例子: 3个节点的链表的时候，分拆完节点之后，最后一个节点是null
        // 4个节点的链表分拆完节点之后，最后一个节点的next是null
        while(even != null && even.next != null) {
            // 奇数节点的next需要指向偶数节点的next, 然后odd指向新的奇数节点
            odd.next = even.next;
            odd = odd.next;
            // 偶数节点的next需要指向奇数节点的next, 然后even指向新的偶数节点
            even.next = odd.next;
            even = even.next;
        }
        // 将两条奇偶链表连接起来
        odd.next = evenHead;
        return head;
    }
}

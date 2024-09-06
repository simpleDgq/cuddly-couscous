package class38;

import java.util.Comparator;
import java.util.PriorityQueue;

//  https://leetcode.cn/problems/merge-k-sorted-lists/?envType=study-plan-v2&envId=top-100-liked
public class MergeKLists {
    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
    public ListNode mergeKLists(ListNode[] lists) {
        // 利用PriorityQueue，先将每条链表的头结点都装进去。自动排序之后，取出来的第一个元素一定是最终合并之后的链表的头结点（因为一定是所有链表最小的）
        if(lists == null || lists.length == 0) {
            return null;
        }

        PriorityQueue<ListNode> queue = new PriorityQueue(new ListNodecomparator());
        ListNode head = null;
        for(int i = 0; i < lists.length; i++) { // 将所有链表的头结点放入队列
            if(lists[i] != null) {
                queue.add(lists[i]);
            }
        }
        head = queue.poll(); // 第一个出来的一定是整个链表的头结点
        if(head == null) {
            return null;
        }
        if(head.next != null) {
            queue.add(head.next); // 下一个结点进入队列
        }
        ListNode pre = head; // pre 执向head，方便链接下一个从Queue里面出来的结点

        while(!queue.isEmpty()) { // 队列中一直有元素，继续拿出结点
            pre.next = queue.poll(); // 链接从queue里面出来的节点
            pre = pre.next; // 方便链接下一个结点
            if(pre.next != null) { 
               queue.add(pre.next); // 下一个结点进入队列
            }
        }
        return head; 
    }
    class ListNodecomparator implements Comparator<ListNode> {

        // 如果返回负数，认为第一个参数应该排在前面
        // 如果返回正数，认为第二个参数应该排在前面
        // 如果返回0，认为谁放前面无所谓
        public int compare(ListNode node1, ListNode node2) {
            return node1.val - node2.val;
        }
    } 
}

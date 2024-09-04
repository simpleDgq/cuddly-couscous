package class38;

public class KGroupList {

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

    public static ListNode reverseKGroup(ListNode head, int k) {
        ListNode start = head;
        ListNode end = getKGroupEnd(start, k);
        if (end == null) {
            return head;
        }

        head = end; // 记录下翻转之后的新链表的头结点，以后都不会发生变化
        reverse(start, end); // 翻转start 到end的链表，翻转完成之后，start.next会指向下一组要翻转的链表的第一个节点

        while (start.next != null) { // 如果start.next 不为null，说明还有节点
            ListNode lastEnd = start; // 记录下每次翻转完成之后的链表最后的一个节点的位置，等会要纠正lastEnd.next的指向
            start = start.next; // 继续下一组的翻转
            end = getKGroupEnd(start, k);
            if (end == null) {
                return head;
            }
            reverse(start, end); // 翻转第二组
            lastEnd.next = end; // 纠正上一组翻转完成之后的最后一个节点的指向，指向end
        }
        return head;
    }

    public static ListNode getKGroupEnd(ListNode start, int k) {
        while (--k != 0 && start != null) {
            start = start.next;
        }
        return start;
    }

    public static void reverse(ListNode start, ListNode end) {
        end = end.next; // 记录下一组要被翻转的链表的第一个元素
        ListNode pre = null;
        ListNode cur = start;
        ListNode next = null;
        while (cur != end) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        start.next = end; // 翻转完成之后，start.next都会指向下一组需要翻转的链表的第一个元素
    }

    public static void main(String args[]) {
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(4);
        ListNode node5 = new ListNode(5);

        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = null;

        reverseKGroup(node1, 2);

    }
}

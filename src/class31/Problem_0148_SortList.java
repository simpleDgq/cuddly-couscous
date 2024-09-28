package class31;

//148. 排序链表
public class Problem_0148_SortList {
    /**
     * 给你链表的头结点 head ，请将其按 升序 排列并返回 排序后的链表 。
     * 
     * 时间复杂度: O(n*logN) 空间复杂度: O(1)
     * 
     */
    
    /**
     * 思路:
     * 
     * 看到这种要求，好像只有堆排序可以完成
     * 
     * 其实可以用归并排序。归并排序时间复杂度O(n*logN)，归并排序空间复杂度是O(N)，因为有
     * 辅助数组
     * 但是链表排序，可以不用辅助数组，直接连接指针，这样就可以做到O(1)的空间复杂度
     *
     *步骤：
     * 1.利用快慢指针找出链表的中间节点，并切分成两个子链表
     * 2.分别递归排序左子链表和右子链表
     * 3.归并排序左右链表，返回排序后的头节点
     */
    public class ListNode {
        int val;
        ListNode next;

        public ListNode(int v) {
            val = v;
        }
    }

    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        return process(head);
    }

    // 归并排序，返回头节点
    public ListNode process(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        // 找链表中点
        ListNode mid = getMid(head);
        ListNode midNext = mid.next;
        mid.next = null;
        // 左半部分链表排序
        ListNode lNode = process(head); // L到mid
        // 右半部分链表排序
        ListNode rNode = process(midNext); // mid+1到R
        // 合并左右两个有序链表
        return merge(lNode, rNode);
    }

    // 快慢指针找链表中点
    public ListNode getMid(ListNode node) {
        if (node == null || node.next == null) {
            return node;
        }
        ListNode fast = node.next;
        ListNode slow = node;
        // 快指针一次走两步，慢指针一次走一步，快指针为null的时候，slow指向的节点就是中点
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    // 合并两个有序链表，并返回头结点
    public ListNode merge(ListNode lNode, ListNode rNode) {
        if (lNode == null) {
            return rNode;
        }
        if (rNode == null) {
            return lNode;
        }
        // 比较两个链表的头，谁是新链表的头
        ListNode head = lNode.val < rNode.val ? lNode : rNode;
        ListNode cur1 = head == lNode ? head.next : rNode.next;
        // ListNode cur2 = cur1 == lNode.next ? rNode : lNode; // 这种写法不对，都只有一个节点的情况下，会出问题
        ListNode cur2 = head == lNode ? rNode : lNode;
        ListNode pre = head; // 前一个元素
        while (cur1 != null && cur2 != null) { // 两个链表都还有元素
            if (cur1.val < cur2.val) {
                pre.next = cur1;
                pre = cur1;
                cur1 = cur1.next;
            } else {
                pre.next = cur2;
                pre = cur2;
                cur2 = cur2.next;
            }
        }
        // 长链表剩下的元素，链接在pre.next后面
        pre.next = cur1 != null ? cur1 : cur2;
        return head;
    }
}

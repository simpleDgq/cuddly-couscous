package class34;

// 287. 寻找重复数
// https://leetcode.cn/problems/find-the-duplicate-number/
public class Problem_0287_FindTheDuplicateNumber {
    /**
     * 给定一个包含 n + 1 个整数的数组 nums ，其数字都在 [1, n] 范围内（包括 1 和 n），可知至少存在一个重复的整数。
     * 假设 nums 只有 一个重复的整数 ，返回 这个重复的数 。
     * 你设计的解决方案必须 不修改 数组 nums 且只用常量级 O(1) 的额外空间。
     */
    
    /**
     * 思路:
     * 
     * 
     * 例子: 从0位置的数出发，如果当前位置是5，则去5位置上找，如果5位置是3，则去3位置上
     * 如果3位置上是5，就形成了环，如果是3，也形成了环
     * 
     * 当前位置的数是啥，下一步就找下标位当前数位置的数
     * 
     * n = 6，范围是1-6，只有一个重复的数，必然存在一个入环的节点
     * 
     * 问题就变成了怎么通过有限几个变量在单链表上找第一个入环节点的问题
     * 
     * 
     * 步骤:
     * 一个快指针, 一个慢指针。它俩都从开头出发，快指针一次走两步, 慢指针一次走一步，
     * 他俩一定会在环上的某个位置相遇，然后相遇的时候, 快指针重新回到开头，慢指针停在原地，
     * 接下来快指针变成一次走一步，慢指针继续一次走一步，他俩一定会第二次相遇。
     * 第二次相遇的时候就是第一个入环节点
     * 
     */
    public int findDuplicate(int[] nums) {
        if (nums == null || nums.length < 2) {
            return -1;
        }
        // fast 一次走两步，slow一次走一步
        int fast = nums[nums[0]];
        int slow = nums[0];
        // 如果不相等，继续走
        while(fast != slow) {
            fast = nums[nums[fast]];
            slow = nums[slow];
        }
        // 相等之后，fast回到开始. 一次走一步，相遇的地方就是入环节点
        fast = 0;
        while(fast != slow) {
            fast = nums[fast];
            slow = nums[slow];
        }
        return slow;
    }
}

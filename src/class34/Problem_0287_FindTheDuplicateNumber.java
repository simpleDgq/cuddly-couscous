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
     * 1. 使用hashmap存，遍历一遍数组，看map中有没，有就直接返回 --> 空间复杂度O(N) 空间复杂度O(N)
     * 2. 和求缺失的第一个正数一样。i位置应该放i+1，不断的交换，放好各个位置，遍历数组，如果i位置不是i+1，则
     * 直接返回当前的数 时间复杂度O(N) 空间复杂度O(1) 但是改变了原数组
     * 
     * 3.下面的方法，单链表上找第一个入环节点
     * 思路:
     * 同单链表上找第一个入环节点的问题一样
     * 
     * 例子: 从0位置的数出发，如果当前位置是5，则去5位置上找，如果5位置是3，则去3位置上
     * 如果3位置上是5，就形成了环，如果是3，也形成了环
     * 
     * 当前位置的数是啥，下一步就找下标位是当前数位置的数
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
        if(nums == null || nums.length == 0) {
            return 0;
        }
        //fast指针一次走两步. 
        int fast = nums[nums[0]];
        int slow = nums[0]; // slow指针一次走一步
        // 第一次相遇的时候
        while(fast != slow) {
            // fast走的时候，走到fast位置的数，指向的nums[fast]位置 --> nums[nums[fast]]
            fast = nums[nums[fast]];
            slow = nums[slow];
        }
        // 相等的时候，fast回到头
        fast = 0;
        // 快慢指针一起走，相遇的节点就是入环节点
        while(fast != slow) {
            fast = nums[fast];
            slow = nums[slow];
        }
        return slow;
    }
}

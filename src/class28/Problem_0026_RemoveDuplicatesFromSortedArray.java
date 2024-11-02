package class28;

// 26. 删除有序数组中的重复项
public class Problem_0026_RemoveDuplicatesFromSortedArray {
    /**
     * 给你一个 升序排列 的数组 nums ，请你 原地 删除重复出现的元素，使每个元素 只出现一次 ，
     * 返回删除后数组的新长度。元素的 相对顺序 应该保持 一致 。然后返回 nums 中唯一元素的个数。
     */
    
    /**
     * 思路: 双指针
     * fill 和 cur
     * 
     * fill表示，当前填到了哪
     * cur表示当前遍历到了哪个数
     * 
     * 如果当前的数和fill指向的数不相等，则将当前数填到fill的下一个位置
     * fill++，cur++
     * 
     * 否则cur++
     * 
     * 0位置的数肯定是不变的，所以cur从1开始
     */
    public int removeDuplicates2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        // 只有一个元素的情况，直接返回
        int N = nums.length;
        if (N == 1) {
            return 1;
        }
        int fill = 0;
        for (int cur = 1; cur <= N - 1; cur++) {
            // 如果fill位置的元素不等于cur位置的数，则将当前位置的数填到fill的下一个位置
            if (nums[fill] != nums[cur]) {
                nums[++fill] = nums[cur];
            } // 否则cur++
        }
        // fill 指向数组的最后一个元素，+1就是数组的长度
        return fill + 1;
    }

    /**
     * 快慢指针 (nums[fast] != nums[slow - 1] 这个条件比较难想到，这个思路可以解决第80题)
     * slow表示要填的位置，fast表示当前遍历到的元素
     * 遍历检查每一个元素是不是要被保留
     * slow-1就是已经填好的位置
     * 如果nums[fast] != nums[slow - 1]，那么就将fast位置的数填入slow，slow++，fast++
     * 否则fast++
     * 
     * fast 和 slow 都从1开始，0位置的数一定会保留
     */
    public int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        // 只有一个元素的情况，直接返回
        int N = nums.length;
        if (N == 1) {
            return 1;
        }
        int slow = 1;
        int fast = 1;
        while(fast <= N - 1) {
            //  如果nums[fast] != nums[slow - 1]，那么就将fast位置的数填入slow，slow++，fast++
            if (nums[fast] != nums[slow - 1]) {
                nums[slow++] = nums[fast];
            }
            fast++;
        }
        return slow;
    }
}

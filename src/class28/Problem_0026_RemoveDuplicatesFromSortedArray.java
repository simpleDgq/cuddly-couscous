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
     * fill++，cur++[
     * 
     * 否则cur++
     * 
     * 0位置的数肯定是不变的，所以cur从1开始
     */
    public int removeDuplicates(int[] nums) {
        if(nums == null || nums.length == 0) {
            return 0;
        }
        if (nums.length < 2) {
            return nums.length;
        }
        
        int fill = 0;
        for(int cur = 1; cur <= nums.length - 1; cur++) {
            if(nums[fill] != nums[cur]) {
                nums[++fill] = nums[cur]; // cur 填到fill的下一个位置，同时fill++
            }
        }
        // fill 指向数组的最后一个元素，+1就是数组的长度
        return fill + 1;
    }
}

package class28;

// 34. 在排序数组中查找元素的第一个和最后一个位置
public class Problem_0034_FindFirstAndLastPositionOfElementInSortedArray {
    /**
     * 给你一个按照非递减顺序排列的整数数组 nums，和一个目标值 target。请你找出给定目标值在数组中的开始位置和结束位置。
     * 如果数组中不存在目标值 target，返回 [-1, -1]。
     * 你必须设计并实现时间复杂度为 O(log n) 的算法解决此问题。
     */
    
    /**
     * 思路: 二分法
     * 
     * 搞一个函数查找小于某个数的最右的位置
     * 找小于target的最右位置，找到之后+1位置的数和target相等，那么就是target出现的第一个位置
     * 找小于target+1的最右位置，如果该位置是target，那么该位置就是target出现的最后一个位置
     * 
     * 例如: 如果在数组中，要查找的target是7
     * 
     * 1.那么小于7的最右位置找到之后，该位置的下一个位置，如果是7，那么该位置一定是7出现的第一个位置
     * 2.同理小于8的最右位置，如果是7，则该位置就是7出现的最后的位置
     * 组成数组返回即可
     */   
    public int[] searchRange(int[] nums, int target) {
        if(nums == null || nums.length == 0) {
            return new int[] {-1, -1};
        }
        // 找小于target的最右位置，该位置+1如果是target，则就是第一次出现的位置
        int L = lessMoreRight(nums, target) + 1;
        // 如果L越界了，说明没有出现target; 如果L位置的数不等于target，也说明数组中没有出现target
        if(L == nums.length || nums[L] != target) {
            return new int[] {-1, -1};
        }
        // 找小于target+1的最右位置，如果上面没有返回{-1,-1},说明数组中一定出现过target，所以右边界一定是有的
        // 最不济也就是和L位置重合
        return new int[] {L, lessMoreRight(nums, target + 1)};
    }
    
    // 返回小于target的最右位置
    public int lessMoreRight(int nums[], int target) {
       int L = 0;
       int R = nums.length - 1;
       int ans = -1;
       while(L <= R) {
           int mid = L + ((R - L) >> 1);
           // 如果中点位置比target小，记录答案，继续去右边找
           if(nums[mid] < target) {
               ans = mid;
               L = mid + 1;
           } else { // 如果中点位置比target大或者相等，不记录答案，继续去左边找
               R = mid - 1;
           }
       }
       return ans;
    }
}

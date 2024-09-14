package class28;

// 35. 搜索插入位置
// https://leetcode.cn/problems/search-insert-position
public class Problem_0035_searchInsert {
    public int searchInsert(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return Integer.MIN_VALUE;
        }

        int N = nums.length;
        int L = 0;
        int R = N - 1;
        // 纯二分，返回L就行
        // 举例子
        // [1,3,5,7] target = 5. 4, 6, 8
        // 上面列举了所有的情形，最终的L就是答案
        while (L <= R) {
            int mid = L + ((R - L) >> 1);
            if (nums[mid] == target) {
                return mid;
            } else if(nums[mid] < target) {
                L = mid + 1;
            } else {
                R = mid - 1;
            }
        }
        return L;
    }
}

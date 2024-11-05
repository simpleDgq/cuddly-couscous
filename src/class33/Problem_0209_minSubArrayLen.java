package class33;


// 209. 长度最小的子数组
public class Problem_0209_minSubArrayLen {
    /**
     * 滑动窗口
     * 两个指针，分别表示滑动窗口的start开始和end结束位置。sum存储窗口中的元素的和，
     * 如果窗口内的和一直是大于target的， 计算答案，然后左边一直吐出元素，
     * sum减去吐出的元素，继续看sum是不是大于target的, 是的话就收集答案
     * sum<target的时候，end一直往后，sum累加。
     * 
     * 所有的答案求最小值
     * 
     */
    public int minSubArrayLen(int target, int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int start = 0;
        int end = 0;
        int N = nums.length;
        int ans = Integer.MAX_VALUE;
        int sum = 0; // 记录窗口内元素的和
        while (end < N) {
            sum += nums[end]; // 窗口中元素的和累加
            while (sum >= target) { // 如果窗口内的和是大于target的， 左边一直吐出元素，计算新的答案
                ans = Math.min(ans, end - start + 1);
                sum -= nums[start]; // 吐出元素，和要减nums[start]
                start++;
            }
            end++;
        }
        return ans == Integer.MAX_VALUE ? 0 : ans; // 等于Integer.MAX_VALUE说明搞不定
    }
}

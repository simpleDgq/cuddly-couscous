package class30;

// 80. 删除有序数组中的重复项 II
public class Problem_0080_removeDuplicates {
    /**
     * 快慢指针
     * slow表示要填的位置，fast表示当前遍历到的元素
     * 遍历检查每一个元素是不是要被保留
     * slow-1和slow - 2就是已经填好的位置
     * 如果nums[fast] != nums[slow - 2]，那么就将fast位置的数填入slow，slow++，fast++
     * 否则fast++
     * 
     * fast 和 slow 都从2开始，前两个位置的数不管是否相等一定会保留，题目要求的是至多两个重复数
     * 
     * (nums[fast] != nums[slow - 2] 这个条件比较难想到)
     * 如果nums[fast] == nums[slow - 2]，那么如果slow位置填入fast指向的值之后，就会出现连续相同的三个元素
     * 所以打破这个条件，nums[fast] != nums[slow - 2]成立的时候才填。
     * 
     * 通解：每个元素最多出现k次
     * nums[slow - k] != nums[fast] 就能搞定
     * 
     */
    public int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        // 只有一个元素或两个元素的情况，直接返回
        int N = nums.length;
        if (N <= 2) {
            return N;
        }
        int slow = 2;
        int fast = 2;
        while (fast <= N - 1) {
            // 如果nums[fast] != nums[slow - 2]，那么就将fast位置的数填入slow，slow++，fast++
            if (nums[fast] != nums[slow - 2]) {
                nums[slow++] = nums[fast];
            }
            fast++;
        }
        return slow;
    }
}

package class29;

// 55. 跳跃游戏
// https://leetcode.cn/problems/jump-game/description
public class Problem_0055_JumpGame {
    public boolean canJump(int[] nums) {
        /**
         * 依次遍历数组中的每一个位置，并实时维护 最远可以到达的位置。
         * 如果最远能够跳到的位置，小于当前i位置了，说明i位置不可达，更不可能跳到数组最后了，直接返回false
         * 如果过程中最远能够到达的位置max大于等于数组长度N-1了，说明能够跳到，返回true
         */
        if (nums == null || nums.length == 0) {
            return false;
        }
        int N = nums.length;
        int max = 0; // 最远能够跳到的位置
        for (int i = 0; i <= N - 1; i++) {
            if(i > max) { // i位置不可达
                return false;
            }
            // 如果max >= N - 1说明能够跳到最后，提前结束循环
            if(max >= N - 1) {
                return true;
            }
            // 更新最远能够跳到的位置
            max = Math.max(max, i + nums[i]);
        }
        // 上面没返回，说明跳不到
        return false;
    }
}

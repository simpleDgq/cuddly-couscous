package class08;

// https://leetcode.cn/problems/container-with-most-water/
public class Code02_ContainerWithMostWater {
    /*
     * 给定一个长度为 n 的整数数组 height 。有 n 条垂线，第 i 条线的两个端点是 (i, 0) 和 (i, height[i]) 。

        找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。

        返回容器可以储存的最大水量。

        说明：你不能倾斜容器。
     */
    /*
     * 
     * 先假设无重复值, 左右两个指针
        左边跟右边比大小， 谁小结算谁的水量
        两个左右两个指针依次划的过程中算出所有水量的最大值就是答案
        
        这里和数组三连问题的第三连有点类似，只关注答案能不能被推大，但是不精确计算每一个答案。
        // 有点难理解，直接背住
     */
    // O(N)
    public int maxArea(int[] height) {
        if(height == null || height.length == 0) {
            return 0;
        }
        int L = 0;
        int R = height.length - 1;
        int max = 0;
        while(L < R) {
            max = Math.max(max, Math.min(height[L], height[R]) * (R - L));
            if(height[L] > height[R]) {
               R--; 
            } else {
               L++; 
            }
        }
        return max;
    }
}

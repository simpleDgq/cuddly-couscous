package class22;

// https://leetcode.cn/problems/trapping-rain-water/
public class Code02_TrappingRainWater {
    /**
     * 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
     */
    /**
     * 思路:
     * 大思路: 求i位置自己整个位置上方有几格水?
     * 怎么求? i位置左边的最大值，和右边的最大值，取最小值，减去arr[i]就是答案
     * 
     * 假设i位置的数是5，左边最大值17，右边最大值是23，左右最小值是17，则5位置上方能够得到的水量是17-5 = 12格水
     * 边界: 如果i位置的数是17，左边最大值是9，右边最大值是23，左右最小值是9，是小于17的，17位置不可能被水覆盖，水量是0
     * 
     * 所有i位置的答案就是: Max(Min(max左，max右) - arr[i], 0) 当i位置数比左右最大的最小还大的时候，相减之后是负数，和0
     * 比较大小，应该取0
     * 
     * 使用辅助数组求: 0 ~ i位置范围上的最大值max
     * i ~ N-1范围上的最大值max
     * 
     * 不用辅助数组:
     * 
     * 搞两个指针L和R，L和R指向的位置，左边max跟右边max谁小就先结算那一边的水量，相等的时候，可以一起结算(代码中先结算了左边，后面的循环会直接
     * 结算右边，没必要单独处理)
     * 结算的时候，小的max-arr[i]就是答案
     * 
     * 假设整个数组下标是0~20
     * 1)0位置最左, 20位置上最右是不可能留下水的。 假设0位置是17，20位置是13
     * 2)搞两个指针L和R分别指向1和19位置
     * 左边的max是17，右边的max是13，右边小，先结算右边
     * R指向的19位置的最大高度假设6, 13 - 6 = 7， R位置的水量是7。
     *
     * 因为6它的左边这么多最大值还没看过，但它的最大值是17，恐怕它真实的左边最大值是大于17的。
     * 而我右边的最大值是13，这可是个真实最大值, 所以6位置的水量就是13 - 6 = 7格子水
     * 
     * 左边max跟右边max谁小就先结算那一边的水量, L
     * 和R一直往中间靠，一直搞下去。
     * 
     */
    public int trap(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }
        int N = height.length;
        int maxLeft = height[0];
        int maxRight = height[N - 1];

        int L = 1;
        int R = N - 2;
        int sum = 0;
        while (L <= R) {
            if (maxLeft <= maxRight) { // 左边的max小于等于右边的max，结算左边。更新maxLeft
                sum += Math.max(maxLeft - height[L], 0); // 如果是负数，就是0
                maxLeft = Math.max(maxLeft, height[L]);
                L++;
            } else { // 结算右边，更新maxRight
                sum += Math.max(maxRight - height[R], 0); // 如果是负数，就是0
                maxRight = Math.max(maxRight, height[R]);
                R--;
            }
        }
        return sum;
    }
}

package class16;

import java.util.Arrays;

// https://leetcode.cn/problems/patching-array/
public class Code03_MinPatches {
    /**
     * 330.按要求补齐数组
     * 
     * 给定一个已排序的正整数数组 nums，和一个正整数 n 。从 [1, n] 区间内选取任意个数字补充到 nums 中，使得 [1, n] 
     * 区间内的任何数字都可以用 nums 中某几个数字的和来表示。请输出满足上述要求的最少需要补充的数字个数。
        示例 1:
        
        输入: nums = [1,3], n = 6
        输出: 1 
        解释:
        根据 nums 里现有的组合 [1], [3], [1,3]，可以得出 1, 3, 4。
        现在如果我们将 2 添加到 nums 中， 组合变为: [1], [2], [3], [1,3], [2,3], [1,2,3]。
        其和可以表示数字 1, 2, 3, 4, 5, 6，能够覆盖 [1, 6] 区间里所有的数。
        所以我们最少需要添加一个数字。
        示例 2:
        
        输入: nums = [1,5,10], n = 20
        输出: 2
        解释: 我们需要添加 [2, 4]。
        示例 3:
        
        输入: nums = [1,2,2], n = 5
        输出: 0
     */
    
    /**
     * 思路:
     * 1) 如果给定的n是1000，数组是空，那么先要搞定的就是1~1，补一个1,
     * 接下来要搞定的是2, 补1？ 不划算，补1只能搞定1~2，补2能够搞定1~3, 所以补2
     * 接下来要搞定的是4, 已经搞定的是1~3, 补4最划算，能搞定1~7
     * 搞定8，补8, .... 发现就是二进制 1 2 4 8 16.... 这样保证增长的最快。 --> 补的数，每次都是能到达的range+1
     * 
     * 2) 先把数组排序
     * 例子: [4, 5, 17, 39]   n = 83
     * 数组中每个数最经济的使用
        1) 只能是 1~3 范围，你先都搞定之后，你舒舒服服，使用这个4，就能扩到1~7了
           给自己提了小目标，这个小目标是搞定1~3 范围上，你缺几个数--> 上来就缺1，补1，能搞定1~1(range刚开始是0，range+1就是1, range变成了1)；
            缺2，补上一次的range 1加上1 = 2， 搞定1~3； ==>补1,2搞定
        2) 使用这个4，就能扩到1~7了。对于5, 小目标1~4范围上都可以搞定, 已经满足了
        可以直接使用这个5，让它变成12，不用添任何数字
        3) 想使用17, 要求目标1~16必须具备
           目前有1~12, 缺13, 补13之后范围扩到1~25  (每次补的数字都是range+1)
        4) 使用17, 就能扩到1~42了; 对于39, 小目标1~38范围上都可以搞定, 已经满足了，直接使用，不用添加任何数字
            变成1~81
        5) 83还不能搞定，补个82最省，搞定所有
        如果n不是83，是一个很大的数字，就和空数组一样，每次都补range+1
     */
    public int minPatches(int[] nums, int aim) {
        if(nums == null || nums.length == 0) {
            return -1; // 搞不定
        }
        Arrays.sort(nums);
        long range = 0; // 能够搞定的范围1~range  这里要用long，因为leetcode的数据，累加之后会溢出int，导致结果不对
        int patches = 0; // 加的数的个数
        int N = nums.length;
        
        for(int i = 0; i <= N - 1; i++) {
            // 对应每一个nums[i], 为了能够最经济的使用，需要1 ~ nums[i] - 1都先被搞定
            while(nums[i] - 1 > range) { //1 到 nums[i] - 1 没有被搞定，这里搞定
                range += range + 1; // range 每次都是扩充range+1 (范围的最后加1);  range+1就是要补的数
                patches++; // 补一个数，加一次
                // 每次补了看有没有达标
                if(range >= aim) {
                    return patches;
                }
            }
            // 1 ~ nums[i] - 1被搞定了, 使用nums[i]
            range += nums[i];
            // 扩充了range, 看有没有搞定目标
            if(range >= aim) {
                return patches;
            }
        }
        // 数组中的数全部使用完，都还没有达标，继续补数，相当于空数组的情况
        while(aim > range) {
            range += range + 1; // range 每次都是扩充range+1 (范围的最后加1);  range+1就是要补的数
            patches++; // 补一个数，加一次
        }
        return patches;
    }
}
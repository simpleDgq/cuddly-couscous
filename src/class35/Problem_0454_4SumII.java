package class35;

import java.util.HashMap;

// 454. 四数相加 II
// https://leetcode.cn/problems/4sum-ii/description/
public class Problem_0454_4SumII {
    /**
     * 给你四个整数数组 nums1、nums2、nums3 和 nums4 ，数组长度都是 n ，请你计算有多少个元组 (i, j, k, l) 能满足：
     * 0 <= i, j, k, l < n
     * 
     * nums1[i] + nums2[j] + nums3[k] + nums4[l] == 0
     */
    
    /**
     * 思路:
     * A数组和B数组的每一个元素，分别进行组合，计算出sum，记录每个和出现了多少次
     * key是出现的sum，value是sum出现的次数 --> 时间复杂度O(n^2)
     * 
     * 同理，C数组和D数组的每一个元素，分别进行组合，也计算出sum，记录每个和出现了多少次
     * key是出现的sum，value是sum出现的次数  -> 这一步其实可以不用表也行，在遍历C和D的时候
     * 直接计算和，然后去A和B里面已经计算好的表里面直接查就行了
     * 
     * 最后两张表，进行组合，从一张表里面取出一个数sum，看另一张表里面-sum有多少个，
     * 就是有多少个元组能满足
     * 
     * 变形: 如果要求(i, j, k, l) 下标怎么办？
     * 也是一样，只不过map里面记录的是每个sum出现的下标
     * 最后结合两张表的时候，进行结合就行了
     * 
     * O(N^2)
     */
    public int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
        if(nums1 == null || nums2 == null || nums3 == null || nums4 == null) {
            return 0;
        }
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        int sum = 0;
        // 遍历A，B，建好表
        for(int i = 0; i <= nums1.length - 1; i++) {
            for(int j = 0; j <= nums2.length - 1; j++) {
                sum = nums1[i] + nums2[j];
                if(map.containsKey(sum)) {
                   map.put(sum, map.get(sum) + 1); 
                } else {
                    map.put(sum, 1); 
                }
            }
        }
        int ans = 0;
        for(int i = 0; i <= nums3.length - 1; i++) {
            for(int j = 0; j <= nums4.length - 1; j++) {
                sum = nums3[i] + nums4[j];
                // 如果map中有-sum, 出现了多少次，就累加
                if(map.containsKey(-sum)) {
                    ans += map.get(-sum);
                }
            }
        }
        return ans;
    }
}

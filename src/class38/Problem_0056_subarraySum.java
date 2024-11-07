package class38;

import java.util.HashMap;

// 560. 和为 K 的子数组
public class Problem_0056_subarraySum {
    public int subarraySum(int[] nums, int k) {
        /**
         * 看到子数组，把求解流程定为什么？
         * 看到子数组问题, 必须以每个位置开头怎么怎么样；
         * 必须以每个位置结尾必须怎么怎么样去考虑
         * 
         * 求以0位置结尾的数组，等于K的子数组，有多少个
         * 求以1位置结尾的数组，等于K的子数组，有多少个
         * 求以2位置结尾的数组，等于K的子数组，有多少个
         * 求以3位置结尾的数组，等于K的子数组，有多少个
         * 
         * 例子: 如果求以100位置结尾的子数组，累加和是30，有多少个，怎么求？
         * 
         * 如果知道100位置结尾的全部数组的累加和是200，
         * 如果能够找到一个位置的前缀和是170，那么这个位置到100为止的累加和一定是30
         * 
         * 搞一个map记录前缀和和出现的次数，如果170出现了3次，那么肯定也就有3个位置到
         * 100的累加和是30
         * 每一个位置都累加就得到最终的答案了
         */
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int N = nums.length;
        // key: 前缀和
        // value: 前缀和出现的次数
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        // 前缀和0出现的次数为1，刚开始一个元素也不选的时候
        map.put(0, 1);
        int ans = 0;
        int sum = 0;
        for (int i = 0; i <= N - 1; i++) {
            sum += nums[i];
            // 来到i位置，前缀和是sum
            // 要求以i位置结尾的数组，和为k，有多少个，
            // 其实就是在求i位置的前面，前缀和为sum - k的子数组有多少个
            if (map.containsKey(sum - k)) {
                ans += map.get(sum - k);
            }
            // 将新的前缀和加入到map中
            if (!map.containsKey(sum)) {
                map.put(sum, 1);
            } else {
                map.put(sum, map.get(sum) + 1);
            }
        }
        return ans;
    }
}

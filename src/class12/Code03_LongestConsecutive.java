package class12;

import java.util.HashSet;

//本题测试链接 : https://leetcode.com/problems/longest-consecutive-sequence/
public class Code03_LongestConsecutive {
    /**
     * 给定一个未排序的整数数组 nums ，找出数字连续的最长序列（不要求序列元素在原数组中连续）的长度。
     * 
     * 进阶：你可以设计并实现时间复杂度为 O(n) 的解决方案吗？
     * 
     * 示例 1：
     * 
     * 输入：nums = [100,4,200,1,3,2]
     * 输出：4
     * 解释：最长数字连续序列是 [1, 2, 3, 4]。它的长度为 4。
     * 示例 2：
     * 
     * 输入：nums = [0,3,7,2,5,8,4,6,0,1]
     * 输出：9
     * 提示：
     * 
     * 0 <= nums.length <= 10^4
     * -10^9 <= nums[i] <= 10^9
     */

    /**
     * leetcode思路
     * 简单来说就是每个数都判断一次这个数是不是连续序列的开头那个数。
     * 
     * 怎么判断呢，就是用哈希表查找这个数前面一个数是否存在，即num-1在序列中是否存在。存在那这个数肯定不是开头，直接跳过。
     * 因此只需要对每个开头的数进行循环，直到这个序列不再连续，因此复杂度是O(n)。 以题解中的序列举例:
     * [100，4，200，1，3，4，2]
     * 去重后的哈希序列为：
     * [100，4，200，1，3，2]
     * 按照上面逻辑进行判断：
     * 元素100是开头,因为没有99，且以100开头的序列长度为1
     * 元素4不是开头，因为有3存在，过，
     * 元素200是开头，因为没有199，且以200开头的序列长度为1
     * 元素1是开头，因为没有0，且以1开头的序列长度为4，因为依次累加，2，3，4都存在。
     * 元素3不是开头，因为2存在，过，
     * 元素2不是开头，因为1存在，过。
     * 完
     * 
     * 简单说就是，如果num-1不包含在数组里面，num就是开头。
     * 然后一个while循环去判断num开头的序列有多长，更新longestStreak。
     * 最后返回longestStreak即可。
     * 
     * 连续数字，不能有重复数，所以去重。
     */
    public int longestConsecutive(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        HashSet<Integer> set = new HashSet<Integer>();
        for (int num : nums) {
            set.add(num); // 加入set进行去重，连续序列中不能出现重复元素
        }
        int longestStreak = 0;

        // 看每一个元素是否能作为开头的数，如果能，能推多远
        for (int num : set) {
            // 如果set中没有num - 1， 说明num能够作为某个连续序列的开头
            // 那么可以遍历set，看连续序列最长能够推多远
            if (!set.contains(num - 1)) {
                int curLongest = 1;
                while (set.contains(num + 1)) {
                    curLongest++;
                    num++;
                }
                longestStreak = Math.max(longestStreak, curLongest);
            }
        }
        return longestStreak;
    }
}

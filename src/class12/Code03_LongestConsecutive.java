package class12;

import java.util.HashMap;
import java.util.HashSet;

//本题测试链接 : https://leetcode.com/problems/longest-consecutive-sequence/
public class Code03_LongestConsecutive {
    /**
     * 给定一个未排序的整数数组 nums ，找出数字连续的最长序列（不要求序列元素在原数组中连续）的长度。

        进阶：你可以设计并实现时间复杂度为 O(n) 的解决方案吗？
        
        示例 1：
        
        输入：nums = [100,4,200,1,3,2]
        输出：4
        解释：最长数字连续序列是 [1, 2, 3, 4]。它的长度为 4。
        示例 2：
        
        输入：nums = [0,3,7,2,5,8,4,6,0,1]
        输出：9
        提示：
        
        0 <= nums.length <= 10^4
        -10^9 <= nums[i] <= 10^9
     */
    
    /**
     * 思路:
     * 两张表： 连续区间头表 + 连续区间尾表
     * 
     * 每张表分别记录以某个数为头的区间的长度，以某个数为尾的区间的长度。
     * 
     * 一个数来到的时候，如果没有存在过，都自己建出自己的区间，看看跟之前能不能合，看看后面能不能合。
     * 然后删除合并之后无效的区间。
     */
    public int longestConsecutive(int[] nums) {
        if(nums == null || nums.length == 0) {
           return 0; 
        }
        HashMap<Integer, Integer> headMap = new HashMap<Integer, Integer>();
        HashMap<Integer, Integer> tailMap = new HashMap<Integer, Integer>();
        HashSet<Integer> visited = new HashSet<Integer>();
        
        for(int num : nums) {
            if(!visited.contains(num)) { // 没有访问过
                visited.add(num);
                // 建立自己的区间
                headMap.put(num, 1);
                tailMap.put(num, 1);
                // 如果有以num - 1结尾的区间，将它和以num为头的区间合并上
                if(tailMap.containsKey(num - 1)) {
                    int preLen = tailMap.get(num - 1);
                    int preHead = num - preLen;// 前一个区间的头
                    // 合并当前数到前一个区间
                    tailMap.put(num, preLen + 1); // 合并之后，以num结尾的区间的长度+1
                    tailMap.remove(num - 1); // num - 1不在是尾，删掉
                    headMap.put(preHead, preLen + 1); // 以num - 1为头的区间长度加1
                    headMap.remove(num); // num不在是头，删掉
                }
                // 如果有以num + 1开头的区间，将它和以num为尾的区间合并上
                if(headMap.containsKey(num + 1)) {
                    int preLen = tailMap.get(num); // 以num为尾的区间的长度
                    int preHead = num - preLen + 1;// 以num为尾的区间的头
                    int postLen = headMap.get(num + 1); // 以num+1为头的区间的长度
                    int postTail = num + postLen; // 以num+1为头的区间的尾
                    
                    headMap.put(preHead, preLen + postLen); // 合并
                    headMap.remove(num + 1); // 合并之后num + 1不在是头，删掉
                    tailMap.put(postTail, preLen + postLen);
                    tailMap.remove(num); // // 合并之后num不在是尾，删掉 
                }
            }
        }
        // 遍历headMap，取最长的就是答案
        int ans = 0;
        for(int len : headMap.values()) {
            ans = Math.max(ans, len);
        }
        return ans;
    }
}

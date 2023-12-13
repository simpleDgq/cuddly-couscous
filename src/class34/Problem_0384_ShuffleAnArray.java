package class34;

// 384. 打乱数组

public class Problem_0384_ShuffleAnArray {
    /**
     * 给你一个整数数组 nums ，设计算法来打乱一个没有重复元素的数组。打乱后，数组的所有排列应该是 等可能 的。
     * 
     * 实现 Solution class:
     * 
     *  Solution(int[] nums) 使用整数数组 nums 初始化对象
     *  int[] reset() 重设数组到它的初始状态并返回
     *  int[] shuffle() 返回数组随机打乱后的结果
     */
    
    /**
     * 思路:
     * 打乱:每个数在洗完牌之后都等概率地跑到剩下所有的位置上去
     * 
     * 0..N-1做一个随机，然后把它交换到最后一个位置。
     * 0..N-2做一个随机，然后把它交换到最后N-2位置。
     * 0..N-3做一个随机，然后把它交换到最后N-3位置。
     * 全部弄完, 彻底打乱了
     * 
     * 随机一个位置选出来将这个数和最后位置的数交换
     */
    class Solution {
        
        private int[] origin; // 原始数组。reset要返回初始状态，所以原始数组要保持不变，打乱操作在shuffle上操作
        private int[] shuffle; // 打乱之后的数组
        private int N;

        public Solution(int[] nums) {
            origin = nums;
            N = nums.length;
            shuffle = new int[N];
            
            //shuffle = Arrays.copyOf(origin, origin.length); // 可以用这个
            for(int i = 0; i <= N - 1; i++) {// 复制数组
                shuffle[i] = origin[i];
            }
        }
        
        public int[] reset() {
            return origin;
        }
        
        public int[] shuffle() {
            // 0 到N - 1上随机....
            // 0 到N - 2上随机...
            // ....
            for(int i = N - 1; i >= 0; i--) {
                // 随机index，取值，和最后一个数交换
                // Math.random()产生的数是0.0到1.0之间的小数，不包括1.0
                // * (i + 1) --> 产生的是[0.0, i+1)的小数，不包括i+1
                // 转为int，就是0到i的整数
                int randomIndex = (int)(Math.random() * (i + 1));
                int tmp = shuffle[i];
                shuffle[i] = shuffle[randomIndex];
                shuffle[randomIndex] = tmp;
            }
            return shuffle;
        }
    }
}

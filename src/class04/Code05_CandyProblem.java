package class04;

public class Code05_CandyProblem {
 // 测试链接 : https://leetcode.cn/problems/candy/
    
    /*
        n 个孩子站成一排。给你一个整数数组 ratings 表示每个孩子的评分。
        你需要按照以下要求，给这些孩子分发糖果:
            每个孩子至少分配到 1 个糖果。
            相邻两个孩子评分更高的孩子会获得更多的糖果。
        请你给每个孩子分发糖果，计算并返回需要准备的 最少糖果数目 。
    */
    /*
     * 思路:
     * 左规则: 每个孩子，如果评分比左边大，要比他左边的孩子多
     * 右规则: 每个孩子，如果评分比右边大，要比他右边的孩子多
     * 
     * 遍历一遍数组，生成满足左规则的数组，再遍历一遍数组，生成满足右规则的数组，
     * 最后遍历生成的辅助数组，每个位置取最大值，生成的数组就是答案
     */
    // 时间：O(N)  空间: O(N)
    public int candy(int[] ratings) {
        if(ratings == null || ratings.length == 0) {
            return 0;
        }
        if(ratings.length == 1) {
            return 1;
        }
        int N = ratings.length;
        int left[] = new int[N];
        int right[] = new int[N];
        int ans = 0;
        
        left[0] = 1;
        for(int i = 1; i <= N -1 ; i++) {
            if(ratings[i] > ratings[i - 1]) {
                left[i] = left[i - 1] + 1; 
            } else { // 如果变小了，就变回1
                left[i] = 1;
            }
        }
        
        right[N - 1] = 1;
        for(int i = N - 2; i >= 0; i--) {
            if(ratings[i] > ratings[i + 1]) {
                right[i] = right[i + 1] + 1; 
            } else { // 如果变小了，就变回1
                right[i] = 1;
            }
        }
        
        for(int i = 0; i <= N -1 ; i++) {
            ans += Math.max(left[i], right[i]);
        }
        return ans;
    }
    
    // 这题还有，时间复杂度O(N)，额外空间复杂度O(1)  的解法，但是太复杂，去tmd，不管了
 
}

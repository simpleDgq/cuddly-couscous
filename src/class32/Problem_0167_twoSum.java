package class32;

// 167. 两数之和 II - 输入有序数组
public class Problem_0167_twoSum {
    /**
     * 思路1： 因为数组元素是非递增顺序排列，所以可以每一个数都去它的右边找有没有
     * 等于target - nums[i]的数。找的时候可以用二分法。
     * 每一个数搞一遍，时间复杂度是O(nlogN)
     * 每一个数只用去它的右边找，是因为经过它左边的数的时候，已经查找过了，没有找到，才来到现在的位置。
     * 
     * 
     * 下标从1开始，这个条件很迷惑。计算出来最终的答案之后，加1就行了。
       思路2：数组有序，直接双指针法。一个指向头，一个指向尾。看和是不是target，是的话就返回，
       和小于target的话，则继续去右边找；和大于target的话，继续去左边找。
     */
    public int[] twoSum(int[] numbers, int target) {
        if(numbers == null || numbers.length == 0) {
            return new int[] {-1, -1};
        }
        int left = 0;
        int right = numbers.length - 1;

        while(left < right) {
            int sum = numbers[left] + numbers[right];
            if(sum == target) {
                return new int[] {left + 1, right + 1}; // 下标从1开始，这个条件很迷惑。计算出来最终的答案之后，加1就行了。
            } else if(sum < target) {
                left++;
            } else {
                right--;
            }
        }
        return new int[] {-1, -1};
    }
}

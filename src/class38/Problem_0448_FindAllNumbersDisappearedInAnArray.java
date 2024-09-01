package class38;

import java.util.ArrayList;
import java.util.List;

// 448. 找到所有数组中消失的数字
// https://leetcode.cn/problems/find-all-numbers-disappeared-in-an-array/description/
public class Problem_0448_FindAllNumbersDisappearedInAnArray {
    /**
     * 给你一个含 n 个整数的数组 nums ，其中 nums[i] 在区间 [1, n] 内。请你找出所有在 [1, n] 范围内但没有出现在 nums
     * 中的数字，并以数组的形式返回结果。
     */
    /**
     * 思路: i+1应该出现在i位置
     * 
     * 例子: nums = [4,3,2,7,8,2,3,1]
     * 0位置出现的值是4, 4应该出现在3位置，将0位置的数和3位置的数交换 ， 得到 [7,3,2,4,8,2,3,1]
     * 这时候0位置是7，应该出现在6位置，继续交换 ，得到 [3,3,2,4,8,2,7,1]
     * 这时候0位置是3，应该出现在2位置，继续交换 ，得到 [2,3,3,4,8,2,7,1]
     * 这时候0位置是2，应该出现在1位置，继续交换 ，得到 [3,2,3,4,8,2,7,1]
     * 这时候0位置是3，应该出现在2位置，2位置 已经是3了，所以不交换
     * 继续看下一个位置1，1位置已经是2了，继续，2位置已经所以3了，继续下一个位置
     * 3位置已经是4了，继续下一个位置
     * 4位置是8，8应该出现在7位置，和7位置交换得到[3,2,3,4,1,2,7,8]
     * 这时候4位置是1，应该出现在0位置，和0位置交换，得到[1,2,3,4,3,2,7,8]
     * 这时候4位置是3，应该出现在2位置，2位置 已经是3了，所以不交换，继续下一个位置
     * 下一个位置是2，应该出现在1位置，1位置 已经是2了，所以不交换，继续下一个位置
     * 下一个位置是7，应该出现在6位置，6位置 已经是7了，所以不交换，继续下一个位置
     * 同理最后一个位置8也不交换
     * 
     * 然后遍历一遍整个数组，看i位置是不是i+1, 不是的就是缺少的数。
     */

    public List<Integer> findDisappearedNumbers(int[] nums) {
        List<Integer> result = new ArrayList<Integer>();
        if (nums == null || nums.length == 0) {
            return result;
        }
        int N = nums.length;
        // 每一个位置都去玩上面的下标循环怼
        for (int i = 0; i <= N - 1; i++) {
            walk(nums, i);
        }
        // 怼完之后，遍历一遍数组，看i位置的值是不是i+1，不是的话就搜集答案
        for (int i = 0; i <= N - 1; i++) {
            if (nums[i] != i + 1) {
                result.add(i + 1);
            }
        }
        return result;
    }
    
    // 下标循环怼的过程
    public void walk(int nums[], int i) {
        // 如果当前i位置的值不是i+1, 则应该交换，然后继续判断
        // 是的话就不用做什么，直接搞下一个位置
        while(nums[i] != i + 1) {
            // nums[i]应该去的位置
            int nextI = nums[i] - 1;
            // 如果不是，则交换当前位置和nextI
            if(nums[nextI] != nextI + 1) {
                swap(nums, i, nextI);
            } else {
                // 如果应该去的位置的值，已经是nextI + 1了，不用交换，继续搞下一个元素
                // 需要break，退出while，不然就死循环一直搞i位置了
                break;
            }
        }
    }

    public void swap(int nums[], int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}

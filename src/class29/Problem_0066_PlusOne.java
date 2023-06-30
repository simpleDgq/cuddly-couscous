package class29;

// 66. 加一
public class Problem_0066_PlusOne {
    /**
     * 给定一个由 整数 组成的 非空 数组所表示的非负整数，在该数的基础上加一。
     * 最高位数字存放在数组的首位， 数组中每个元素只存储单个数字。
     * 你可以假设除了整数 0 之外，这个整数不会以零开头。
     * 
     * 例如: 输入：digits = [1,2,3]
     *      输出：[1,2,4]
     *      解释：输入数组表示数字 123。
     *      
     *      [9, 9, 9]
     *      则[1, 0 , 0 , 0]
     */
    
    /**
     * 思路:
     * 从后往前遍历数组的每一个数字，如果最后的数小于9，则直接加1，然后返回原数组
     * 否则当前位置的数变0，继续往前
     * 如果for里面一直没有return，说明数组里面全是9，搞一个新数字，长度为原始数组长度+1.
     * 将0位置变成1，返回
     */
    public int[] plusOne(int[] digits) {
        if(digits == null || digits.length == 0) {
            return null;
        }
        int N = digits.length;
        for(int i = N - 1; i >= 0; i--) {
            // 当前数小于9，直接加1，然后返回
            if(digits[i] < 9) {
                digits[i] += 1;
                return digits;
            }
            // 否则当前元素变0
            digits[i] = 0;
        }
        // 一直没有return，创建新数字，然后返回
        int ans[] = new int[N + 1];
        ans[0] = 1;
        return ans;
    }
}

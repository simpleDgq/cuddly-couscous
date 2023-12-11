package class34;

// 326. 3 的幂
public class Problem_0326_PowerOfThree {
    /**
     * 给定一个整数，写一个函数来判断它是否是 3 的幂次方。如果是，返回 true ；否则，返回 false 。
     * 整数 n 是 3 的幂次方需满足：存在整数 x 使得 n == 3x
     */
    
    /**
     * 解法1: O(logN)
     *  我们不断地将 n 除以 3，直到 n=1。如果此过程中 n 无法被 3 整除，就说明 n 不是 333 的幂。
     *  本题中的 n 可以为负数或 0，可以直接提前判断该情况并返回 False，也可以进行试除，
     *  因为负数或 0 也无法通过多次除以 3 得到 1。
     */
    public boolean isPowerOfThree(int n) {
        while (n != 0 && n % 3 == 0) {
            n /= 3;
        }
        return n == 1;
    }
    
    
    /**
     * 我们还可以使用一种较为取巧的做法。
     * 在题目给定的 32位有符号整数的范围内，最大的 3 的幂为 3^19=1162261467。
     * 我们只需要判断 n是否是 3^19的约数即可。
     * 与方法一不同的是，这里需要特殊判断 n 是负数或 0 的情况。
     */
    public boolean isPowerOfThree2(int n) {
        //return n > 0 && (Math.pow(3, 19) % n == 0); // 方便记忆，用幂函数求3的19次方
        return n > 0 && (1162261467 % n == 0);
    }
   
}

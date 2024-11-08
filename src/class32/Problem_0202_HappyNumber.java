package class32;

import java.util.HashSet;

//202. 快乐数
public class Problem_0202_HappyNumber {
    /**
     *  编写一个算法来判断一个数 n 是不是快乐数。
     * 
     * 「快乐数」 定义为：
     *  对于一个正整数，每一次将该数替换为它每个位置上的数字的平方和。
     *  然后重复这个过程直到这个数变为 1，也可能是 无限循环 但始终变不到 1。
     *  如果这个过程 结果为 1，那么这个数就是快乐数。
     *  如果 n 是 快乐数 就返回 true ；不是，则返回 false 。
     */
    
    /**
     * 将n的每一位的平方都累加到sum里面
     * 搞一个set记录出现过的sum，
     * 如果出现重复的sum，说明搞不定，直接返回
     */
    public boolean isHappy(int n) {
        HashSet<Integer> set = new HashSet<Integer>();
        // 如果n变成1，说明是快乐数
        while (n != 1) {
            int sum = 0;
            // 将n的每一位的平方都累加到sum里面
            while (n != 0) {
                sum += (n % 10) * (n % 10);
                n /= 10;
            }
            // 如果出现过重复的sum，说明不是快乐数
            if (set.contains(sum)) {
                return false;
            }
            // 没有出现过，添加。重置sum
            set.add(sum);
            n = sum; 
        }
        return true;
    }
    
    /**
     * 思路: 掌握
     * (背)
     * 这题有一个结论: 
     *      如果是快乐数, 能回到1，如果不是, 它转下去一定会出现4, 然后呈现循环的状态
     * 可以用暴力方法尝试出来，打印第一种解法的set，试几个数字，就能找到规律
     */
    public boolean isHappy2(int n) {
        while(n != 1 && n != 4) {
            int sum = 0;
            // 将n的每一位，平方之后，累加，加入到set中
            while(n != 0) {
                sum += (n % 10) * (n % 10);
                n /= 10;
            }
            n = sum;
        }
        return n == 1;
    }
}

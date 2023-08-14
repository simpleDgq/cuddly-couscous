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
     * 思路:
     * 1. 搞一个set，每次计算的结果都放入到set中
     * 2. 如果计算的结果在set中存在，说明循环了，永远计算不完，一定不是快乐数
     */
    public boolean isHappy(int n) {
        HashSet<Integer> set = new HashSet<>();
        while(n != 1) {
            int sum = 0;
            // 将n的每一位，平方之后，累加，加入到set中
            while(n != 0) {
                sum += (n % 10) * (n % 10);
                n /= 10;
            }
            if(set.contains(sum)) {
                return false;
            }
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

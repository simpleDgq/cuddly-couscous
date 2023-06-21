package class29;

// 50. Pow(x, n)
public class Problem_0050_PowXN {
    /**
     * 实现 pow(x, n) ，即计算 x 的整数 n 次幂函数（即，x^n ）。
     * n可能为负数
     */
    
    /**
     * 思路:
     * 快速求x的n次幂
     * 
     * 将n表示成二进制，每次取一位
     * 如果是1，结果中就乘上t，t从x开始，
     * 每次都乘上自己
     * 
     * 例如:
     * 求10的75次方，75 = 64 + 8 + 2 + 1 = 1001011
     * t = 10
     * 取出n的二进制的最后一位，是1，说明结果里面有10的1次方，乘进去res = res * t，同时 t *= t;  t=10^2
     * 取出n的二进制的倒数第2位，是1，说明结果里面有10的2次方，乘进去res = res * t, 同时 t *= t; t= 10^4
     * 取出n的二进制的倒数第3位，是0，说明结果里面没有10的4次方，res不变, 同时 t *= t; t= 10^8
     * 取出n的二进制的倒数第4位，是1，说明结果里面没有10的8次方，乘进去res = res * t, 同时 t *= t; t= 10^16
     * ...
     * 以此类推
     * 
     * 时间复杂度分析: O(logN)  N 是 次方数
     * 
     * res最多乘了几次？最差的时候，如果次方数二进制全是1，次数就是1的个数，logN
     * t乘了几次？也是logN次
     * 所以时间复杂度logN
     * 
     * 怎么求？
     * 如果n是负数，将n转成正数，按照上面的过程，求x的n次方，求完之后，如果n是负数，用1除res就行
     * 
     * 需要注意的是如果n是Integer.MIN_VALUE的话，转不成正数，求绝对值abs之后，还是Integer.MIN_VALUE
     * 这种情况下，可以先将n + 1，然后求绝对值，然后求x的n+1次方，最后再乘上个X，再用1除得到的结果，就是x的
     * Integer.MIN_VALUE次方
     */
    public double myPow(double x, int n) {
        if(n == 0) {
            return 1D;
        }
        // 如果n是Integer.MIN_VALUE，求n+1的绝对值，否则求n的绝对值
        int pow = Math.abs(n == Integer.MIN_VALUE ? n + 1: n);
        // 快速求x的n次方
        double t = x;
        double res = 1D;
        while(pow != 0) {
            // 如果最后一位是1，乘t到结果中
            if((pow & 1) == 1) {
                res *= t;
            }
            // t 变
            t *= t;
            // pow 往右移动一位
            pow >>= 1;
        }
        // 如果n是系统最小值，结果还得乘上一个x
        if(n == Integer.MIN_VALUE) {
            res *= x;
        }
        // 如果n是负数，用1除res，然后返回
        return n < 0 ? (1D / res) : res;
    }
}

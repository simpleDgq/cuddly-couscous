package class24;

public class Code02_NotContains4 {
    /**
     * 正常的里程表会依次显示自然数表示里程
        吉祥的里程表会忽略含有4的数字而跳到下一个完全不含有4的数
        正常：1 2 3 4 5 6 7 8 9 10 11 12 13 14 15
        吉祥：1 2 3 5 6 7 8 9 10 11 12 13 15 16 17 ... 38 39 50 51 52 53 55
        给定一个吉祥里程表的数字num，返回这个数字代表的真实里程 
     */
    
    /**
     *  课上讲的比较复杂，直接用10进制转9进制的方法解决
     *  
     *  当给的num是9的时候，其实对应的事8，当给的num是10的时候是9，11的时候是10
     *  
     *  15的时候是13。。。
     *
     *
     *   时间复杂度: logNum
     */
    public static long notContain4(long num) {
        if(num <= 0) {
            return 0;
        }
        long ans = 0;
        long cur = 0;
        for(long base = 1; num != 0; base *= 9) {
            // 一位位的搞出来
            cur = num % 10;
            ans += (cur < 4 ? cur : cur - 1) * base;
            num /= 10;
        }
        return ans;
    }
    
    
    // 这种解法暴力，num*logNum的时候复杂度 --> 1到num每个数都搞一变，搞得时候，都是log以10为底num的复杂度
    // num中一定没有4这个数字
    
    public static long notContains4Nums1(long num) {
        long count = 0;
        for (long i = 1; i <= num; i++) {
            if (isNot4(i)) {
                count++;
            }
        }
        return count;
    }

    public static boolean isNot4(long num) {
        while (num != 0) {
            if (num % 10 == 4) {
                return false;
            }
            num /= 10;
        }
        return true;
    }
 
    
    public static void main(String[] args) {
        long max = 1000000;
        System.out.println("功能测试开始，验证 0 ~ " + max + " 以内所有的结果");
        for (long i = 0; i <= max; i++) {
            // 测试的时候，输入的数字i里不能含有4，这是题目的规定！
            
            if (isNot4(i) &&  notContain4(i) != notContains4Nums1(i)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("如果没有打印Oops说明验证通过");

       
    }
}

package class28;

// https://leetcode.cn/problems/string-to-integer-atoi/
public class Problem_0008_StringToInteger {

    /**
     * 8. 字符串转换整数 (atoi)
     * 
     * 请你来实现一个 myAtoi(string s) 函数，使其能将字符串转换成一个 32 位有符号整数（类似 C/C++ 中的 atoi 函数）。
     */
    
    /**
     * 思路:  这题的精髓在，很大的数字可能会溢出。
     * 
     * 如果是一个正常的字符串，应该怎么转换？
     * 
     * 例如"3061", 从第一位开始, 每次处理一位，取出来，累加到当前结果res * 10中。
     * 
     * 
     * res按正数处理:
     * for(i = 0; i <= 3; i++) {
     *      cur = str[i];
     *      res = res * 10 + cur;
     * }
     * 
     * res按负数处理，因为负数的绝对值反问比正数的更大，用一个绝对值更大的东西来装我们装换的结果会更加安全。
     * 有符号整数的范围是: -2147483648 ~ 2147483647 负数可以兼顾正数，因为负数的绝对值范围比正数大一点。
     * 
     * 
     */
    public static int myAtoi(String s) {
        if(s == null || s.length() == 0) {
            return 0;
        }
        // 过滤一些无效的字符
        s = removeHeadZero(s.trim());
        System.out.println(s);
        // 当s和e相等的时候，例如string.substring(1, 1) 会返回空字符串
        // 所以这里返回的字符串可能是空
        if(s == null || s.length() == 0) {
            return 0;
        }
        char str[] = s.toCharArray();
        // 判断截取之后的字符串是否是有效的
        if(!isValid(str)) {
            return 0;
        }

        // 正经整数怎么转换
        int minp = Integer.MIN_VALUE / 10;
        int mino = Integer.MIN_VALUE % 10;
        boolean posi = str[0] == '-' ? false : true;
        int res = 0;
        int cur = 0;
        for(int i = (str[0] == '-' || str[0] == '+') ? 1 : 0; i <= str.length - 1; i++) {
            cur = '0' - str[i]; // res 从负数开始，用0字符减之后就是负数啦
            // 考虑溢出问题, 主要是res会溢出
            /*
             * 1.如果res 已经比整数最小值 / 10 还小了，那么下面乘上10之后，一定溢出了
             * 2.如果res等于整数最小值 / 10, 而且cur 比 Integer.MIN_VALUE % 10还小，那么加上cur之后一定溢出
             * 溢出的情况，如果是正数，返回整数最大值，负数返回最小值
             */
            if(res < minp || (res == minp && cur < mino)) {
               return posi ? Integer.MAX_VALUE : Integer.MIN_VALUE; 
            }
            res = res * 10 + cur;
        }
        // 如果是正数，而且res是正式的最小值，说明结果是整数的最大值，返回
        if(posi && res == Integer.MIN_VALUE) {
          return Integer.MAX_VALUE;  
        }
        return posi ? -res : res; 
    }
    
    /**
     * 删除字符串前面的0，字符串开头可能是+或者-，或者直接就是数字
     * 
     * 从左边开始，找到第一个不是0的位置，记录下来
     * 
     * 从右边开始，找到最左边第一个不是数字的位置，记录下来
     * 
     * 截取这段字符串, 返回
     */
    public static String removeHeadZero(String str) {
        boolean r = (str.startsWith("+") || str.startsWith("-"));
        // 如果字符串是以+ 或者- 开始，那么找第一个不是0位置的时候，应该从1位置开始
        int s = r ? 1 : 0;
        char strs[] = str.toCharArray();
        for(; s <= strs.length - 1; s++) {
            if(strs[s] != '0') {
                break;
            }
        }
        System.out.println(s);
        // s 来到了第一个不是'0'的位置
        int e = strs.length;
        // 从右往左，如果有符号，到第1位置，否则只到0位置
        for(int i = strs.length - 1; i >= (r ? 1 : 0); i--) {
            // 如果不是数字，记录下位置，一直往左，最终找到不是数字的最左位置
            if(strs[i] < '0' || strs[i] > '9') {
                e = i;
            }
        }
        System.out.println(e);
        // 截取s到e的这一段, 如果第一位是符号，也得拼接上
        return (r ? strs[0] : "" ) + str.substring(s, e);
    }
    
    public static boolean isValid(char[] chas) {
        // 当s和e相等的时候，例如string.substring(1, 1) 会返回空字符串
        // 所以这里返回的字符串可能是有符号的
        // 第一位是符号，而且字符串长度是1，说明字符串无效
        if ((chas[0] == '-' || chas[0] == '+') && chas.length == 1) {
            return false;
        }
        // 如果第一位不是符号，而且也不是数字，说明字符串无效
        if (chas[0] != '-' && chas[0] != '+' && (chas[0] < '0' || chas[0] > '9')) {
            return false;
        }
        // 剩下的字符，如果不是数字，就不是有效的
        for (int i = 1; i < chas.length; i++) {
            if (chas[i] < '0' || chas[i] > '9') {
                return false;
            }
        }
        return true;
    }
}

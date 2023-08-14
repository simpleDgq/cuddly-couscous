package class32;

import java.util.HashMap;

//166. 分数到小数
public class Problem_0166_FractionToRecurringDecimal {
    /**
     * 给定两个整数，分别表示分数的分子 numerator 和分母 denominator，以 字符串形式返回小数 。
     * 如果小数部分为循环小数，则将循环的部分括在括号内。
     * 
     * 如果存在多个答案，只需返回 任意一个 。
     * 对于所有给定的输入，保证 答案字符串的长度小于 104 。
     * 
     * 输入：numerator = 1, denominator = 2
     * 输出："0.5"
     * 
     * 输入：numerator = 4, denominator = 333
     * 输出："0.(012)"
     */
    
    /**
     * 这题感觉纯数学问题
     * 思路:
     * 
     * 例子: a 和 b
     * 
     * 1. a / b得到整数部分
     * 2. 然后(a * 10) / b = c，得到c作为第1位小数
     * 3. (a * 10) % b = d
     * 4. 然后d和b作为新的a和b去搞
     * =====================
     * 
     * 例子:
     * a = 1 b = 3
     * 
     * 1. 1 / 3 = 0  整数部分是0
     * 2. 1 * 10 / 3 = 3 得到第一位小数3
     * 3. 1 * 10 % 3 = 1
     * 4. 1 和 3作为新的除数和被除数去搞
     * 
     * 出现循环小数，插入括号，然后结束
     */
    public String fractionToDecimal(int numerator, int denominator) {
        if (numerator == 0) {
            return "0";
        }
        StringBuilder res = new StringBuilder();
        // "+" or "-" 有一个是负数，那么结果就是负数
        res.append(((numerator > 0) ^ (denominator > 0)) ? "-" : "");
        long num = Math.abs((long) numerator);
        long den = Math.abs((long) denominator);
        // 求integral part
        res.append(num / den);
        num %= den;
        // 如果能够被整除，搞定了，直接返回
        if (num == 0) {
            return res.toString();
        }
        // 求fractional part
        res.append(".");
        // key: 循环小数位 value: 在res中的位置
        HashMap<Long, Integer> map = new HashMap<Long, Integer>();
        map.put(num, res.length());
        while (num != 0) {
            // 计算出小数位，放入res中
            num *= 10;
            res.append(num / den);
            num %= den;
            // 如果map中已经出现过，说明出现循环小数了，res中插入括号
            if (map.containsKey(num)) {
                int index = map.get(num);
                res.insert(index, "(");
                res.append(")");
                break;
            } else {
                // 如果map中没有出现过，记录该num出现的位置
                map.put(num, res.length());
            }
        }
        return res.toString();
    }
}

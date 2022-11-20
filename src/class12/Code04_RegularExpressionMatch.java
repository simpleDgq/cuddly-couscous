package class12;

//测试链接 : https://leetcode.com/problems/regular-expression-matching/
public class Code04_RegularExpressionMatch {
    /**
     * 给你一个字符串 s 和一个字符规律 p，请你来实现一个支持 '.' 和 '*' 的正则表达式匹配。
    
        '.' 匹配任意单个字符
        '*' 匹配零个或多个前面的那一个元素
        所谓匹配，是要涵盖 整个 字符串 s的，而不是部分字符串。
        
        示例 1：
        
        输入：s = "aa" p = "a"
        输出：false
        解释："a" 无法匹配 "aa" 整个字符串。
        示例 2:
        
        输入：s = "aa" p = "a*"
        输出：true
        解释：因为 '*' 代表可以匹配零个或多个前面的那一个元素, 在这里前面的元素就是 'a'。因此，字符串 "aa" 可被视为 'a' 重复了一次。
        示例 3：
        
        输入：s = "ab" p = ".* "
        输出：true
        解释：".* " 表示可匹配零个或多个（'*'）任意字符（'.'）。
        示例 4：
        
        输入：s = "aab" p = "c * a * b"
        输出：true
        解释：因为 '*' 表示零个或多个，这里 'c' 为 0 个, 'a' 被重复一次。因此可以匹配字符串 "aab"。
        示例 5：
        
        输入：s = "mississippi" p = "mis*is*p*."
        输出：false
        提示：
        
        0 <= s.length <= 20
        0 <= p.length <= 30
        s 可能为空，且只包含从 a-z 的小写字母。
        p 可能为空，且只包含从 a-z 的小写字母，以及字符 . 和 *。
        保证每次出现字符 * 时，前面都匹配到有效的字符
     */
    
    /**
     * 思路:
     * 两个字符串怎么怎么样？ --> 样本对应模型
     * 
     * dp[i][j]含义: str1 从i位置出发及其往后的字符，能不能被p从j出发及其往后的字符搞定
     * 
     * 
     * 大过滤
     *   str不能有. 和 *
     *   exp 开头不能是*, 两个*不能挨着
        
     *递归含义:
     *   str从si出发及其后面的所有, 能不能被 exp从ei出发及其后面的所有配出来
     *   能配出来返回true, 否则false
     *   
     *   逻辑划分: 按ei+1位置是不是*来进行划分
     *   
     *   1) ei+1位置不是*，那么说明si没有操作空间了，si位置的字符必须等于ei位置的字符，或者ei位置的字符是., 能搞定si。然后从ei+1开始去搞si+1
     *      str1   a
     *             si位置
     *      exp    
     *             ei位置
     *   2) ei+1位置是*
     *          1) si位置和ei位置的字符不相等， 那么si位置必须有ei+1位置的*搞定，*变成0个，然后ei+2开始去搞si开始的字符串
     *          2) si位置和ei位置的字符相等的时候或者ei是.的时候，也可以*变成0个，然后ei+2开始去搞si开始的字符串
     *             *搞定1个，然后ei+2开始去搞si+1开始的字符串
     *             *搞定2个，然后ei+2开始去搞si+2开始的字符串
     *             ... --> si++
     *   
     */
    public boolean isMatch(String s, String p) {
       if(s == null || p == null) {
           return false;
       }
       // 必须是有效的，s不能包含*和.  p第一个字符不能是*，而且不能有两个连续的字符
       if(!isValid(s, p)) {
           return false;
       }
       char str1[] = s.toCharArray();
       char exp[] = p.toCharArray();
       return process(str1, exp, 0, 0);
    }
    // si及其往后的字符，能不能由ei及其往后的表达式搞定
    // 搞定，返回true, 搞不定返回false
    public boolean process(char[] str1, char exp[], int si, int ei) {
        if(ei == exp.length) { // 表达式没了，str1也必须没有了，才行
            return si == str1.length;
        }
        // 如果ei+1不是*，那么si必须等于ei位置，或者ei位置是.
        // ei + 1等于数组长度的时候，肯定也不是*，也要考虑
        if(ei + 1 == exp.length || exp[ei + 1] != '*') {
            // si位置必须还有字符
            return si != str1.length && (str1[si] == exp[ei] || exp[ei] == '.') && process(str1, exp, si + 1, ei + 1);
        }
        // ei+1是*
        // ei位置和si位置相等或者ei位置是.
        while(si != str1.length && (str1[si] == exp[ei] || exp[ei] == '.')) {
            if(process(str1, exp, si, ei + 2)) {
                return true; // 有一个能搞定就返回true
            }
            si++; // * 搞定 0 个，搞定1个... , si往后动
        }
        // ei 和 si位置不等, * 变成 0个，然后ei+2开始去搞si开始的串
        return process(str1, exp, si, ei + 2);
    }
    public boolean isValid(String s, String p) {
        char str1[] = s.toCharArray();
        char exp[] = p.toCharArray();
        for(int i = 0; i <= str1.length - 1; i++) {
            if(str1[i] == '*' || str1[i] == '.') {
                return false;
            }
        }
        for(int i = 0; i <= exp.length - 1; i++) {
            if(exp[i] == '*' && (i == 0 || exp[i - 1] == '*')) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * 加傻缓存
     */
    public boolean isMatch2(String s, String p) {
        if(s == null || p == null) {
            return false;
        }
        // 必须是有效的，s不能包含*和.  p第一个字符不能是*，而且不能有两个连续的字符
        if(!isValid(s, p)) {
            return false;
        }
        char str1[] = s.toCharArray();
        char exp[] = p.toCharArray();
        int N = str1.length;
        int M = exp.length;
        // 0代表没算过，1代表true, -1代表false
        int dp[][] = new int [N + 1][M + 1];
        return process2(str1, exp, 0, 0, dp);
     }
     // si及其往后的字符，能不能由ei及其往后的表达式搞定
     // 搞定，返回true, 搞不定返回false
     public boolean process2(char[] str1, char exp[], int si, int ei, int dp[][]) {
         if(dp[si][ei] != 0) {
             return dp[si][ei] == 1;
         }
         boolean ans = false;
         if(ei == exp.length) { // 表达式没了，str1也必须没有了，才行
             ans = si == str1.length;
         } else {
             // 如果ei+1不是*，那么si必须等于ei位置，或者ei位置是.
             // ei + 1等于数组长度的时候，肯定也不是*，也要考虑
             if(ei + 1 == exp.length || exp[ei + 1] != '*') {
                 // si位置必须还有字符
                 ans = si != str1.length && (str1[si] == exp[ei] || exp[ei] == '.') && process2(str1, exp, si + 1, ei + 1, dp);
             } else {
                 // ei+1是*
                 // ei位置和si位置相等或者ei位置是.
                 while(si != str1.length && (str1[si] == exp[ei] || exp[ei] == '.')) {
                     if(process2(str1, exp, si, ei + 2, dp)) {
                         ans = true; // 有一个能搞定就返回true
                         break;
                     }
                     si++; // * 搞定 0 个，搞定1个... , si往后动
                 }
                 // ei 和 si位置不等, * 变成 0个，然后ei+2开始去搞si开始的串
                 ans = ans | process2(str1, exp, si, ei + 2, dp); // 如果是break出来的，ans已经是true了，后续的process就不用再调用了
            }
         }
         dp[si][ei] = ans == true ? 1 : -1;
         return ans;
    }
     
    /**
     * 可以发现，有个while循环，需要想办法优化掉?
     * 当我一个格子有枚举行为的时候，我就观察他已经算过的格子，能不能把枚举行为替代掉，
     * 从而得到一个使用有限若干个位置的方式来得到这一个格子的值.
     * 
     * str1   a   a   a   a   b
     *        13  14  15  16  17
     * exp    a   *
     *        29  30
     *        
     * f(13, 29) 怎么计算?
     * 1) a * 变0个a，然后从31位置出发去搞str1 从13开始的字符: f(13, 31)
     * 2) 变 1个a :f(14, 31)
     * ..
     * 类推: f(13, 29) = f(13, 31) || f(14, 31) || f(15, 31) || f(16, 31) || f(17, 31)
     * 
     * 如果12位置也是a。
     * str1  a   a   a   a   a   b
     *       12  13  14  15  16  17
     * exp   a   *
     *       29  30
     * f(13, 29) 怎么计算?
     * f(12, 29) = f(12, 31) || f(13, 31) || f(14, 31) || f(15, 31) || f(16, 31) || f(17, 31)
     * 
     * ==> f(12, 29) = f(12, 31) || f(13, 29)
     * 
     * f(si, ei) = f(si, ei + 2) || f(si + 1, ei)
     * 
     * 发现枚举行为可以被优化
     * 在笔试过程中遇到你先别往下写，你先像我一样是吧，写一个最糙的暴力版本，发现它只有超时了。
     *   所有 case 都过了，直接挂缓存，发现直接过了, 就不用特意优化，如果你发现没过，有枚举行为，那肯定得做斜率优化了
     */
     public boolean process3(char[] str1, char exp[], int si, int ei, int dp[][]) {
         if(dp[si][ei] != 0) {
             return dp[si][ei] == 1;
         }
         boolean ans = false;
         if(ei == exp.length) { // 表达式没了，str1也必须没有了，才行
             ans = si == str1.length;
         } else {
             // 如果ei+1不是*，那么si必须等于ei位置，或者ei位置是.
             // ei + 1等于数组长度的时候，肯定也不是*，也要考虑
             if(ei + 1 == exp.length || exp[ei + 1] != '*') {
                 // si位置必须还有字符
                 ans = si != str1.length && (str1[si] == exp[ei] || exp[ei] == '.') && process3(str1, exp, si + 1, ei + 1, dp);
             } else {
                 if(si == str1.length) { // str1没字符了，ei还有字符，ei+1是*, 只能ei+1的*变0个，然后从ei+2去搞
                     ans = process3(str1, exp, si, ei+2, dp);
                 } else { // str1还有字符
                     if((str1[si] != exp[ei] && exp[ei] != '.')) { // si位置的字符不能被ei位置字符搞定.只能ei+1的*变0个，然后从ei+2去搞
                         ans = process3(str1, exp, si, ei+2, dp);
                     } else { // 能搞定，变0个...1个...等。 优化枚举行为
                         ans = process3(str1, exp, si, ei+2, dp) || process3(str1, exp, si + 1, ei, dp);
                     }
                  }
            }
         }
         dp[si][ei] = ans == true ? 1 : -1;
         return ans;
    }
     
    /**
     * 改动态规划
     * 两个可变参数，ei和si
     * 范围: ei: 0 ~ exp.length   si: 0 ~ str1.length
     * 
     * boolean dp[exp.length+1][str1.length + 1]
     * 
     * 去它妈的，不搞了。
     */
}

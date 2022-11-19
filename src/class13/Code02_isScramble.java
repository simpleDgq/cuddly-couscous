package class13;

//本题测试链接 : https://leetcode.com/problems/scramble-string/
public class Code02_isScramble {
    //87. 扰乱字符串
    /**

    给定一个字符串 s1，我们可以把它递归地分割成两个非空子字符串，从而将其表示为二叉树。
    
    下图是字符串 s1 = "great" 的一种可能的表示形式。
    
        great
       /    \
      gr    eat
     / \    /  \
    g   r  e   at
               / \
              a   t
    在扰乱这个字符串的过程中，我们可以挑选任何一个非叶节点，然后交换它的两个子节点。
    
    例如，如果我们挑选非叶节点 "gr" ，交换它的两个子节点，将会产生旋变/扰乱字符串 "rgeat" 。
    
        rgeat
       /    \
      rg    eat
     / \    /  \
    r   g  e   at
               / \
              a   t
    
    我们将 "rgeat” 称作 "great" 的一个旋变/扰乱字符串。
    
    同样地，如果我们继续交换节点 "eat" 和 "at" 的子节点，将会产生另一个新的旋变/扰乱字符串 "rgtae" 。
    
        rgtae
       /    \
      rg    tae
     / \    /  \
    r   g  ta  e
           / \
          t   a
    我们将 "rgtae” 称作 "great" 的一个旋变/扰乱字符串。
    
    给出两个长度相等的字符串 s1 和 s2，判断 s2 是否是 s1 的旋变/扰乱字符串。
    
    示例 1:
    
    输入: s1 = "great", s2 = "rgeat"
    输出: true
    示例 2:
    
    输入: s1 = "abcde", s2 = "caebd"
    输出: false
    
    ======leetcode的描述===
    使用下面描述的算法可以扰乱字符串 s 得到字符串 t ：
    如果字符串的长度为 1 ，算法停止
    如果字符串的长度 > 1 ，执行下述步骤：
    在一个随机下标处将字符串分割成两个非空的子字符串。即，如果已知字符串 s ，则可以将其分成两个子字符串 x 和 y ，且满足 s = x + y 。
    随机 决定是要「交换两个子字符串」还是要「保持这两个子字符串的顺序不变」。即，在执行这一步骤之后，s 可能是 s = x + y 或者 s = y + x 。
    在 x 和 y 这两个子字符串上继续从步骤 1 开始递归执行此算法。
    给你两个 长度相等 的字符串 s1 和 s2，判断 s2 是否是 s1 的扰乱字符串。如果是，返回 true ；否则，返回 false 。
    
    示例 1：
    
    输入：s1 = "great", s2 = "rgeat"
    输出：true
    解释：s1 上可能发生的一种情形是：
    "great" --> "gr/eat" // 在一个随机下标处分割得到两个子字符串
    "gr/eat" --> "gr/eat" // 随机决定：「保持这两个子字符串的顺序不变」
    "gr/eat" --> "g/r / e/at" // 在子字符串上递归执行此算法。两个子字符串分别在随机下标处进行一轮分割
    "g/r / e/at" --> "r/g / e/at" // 随机决定：第一组「交换两个子字符串」，第二组「保持这两个子字符串的顺序不变」
    "r/g / e/at" --> "r/g / e/ a/t" // 继续递归执行此算法，将 "at" 分割得到 "a/t"
    "r/g / e/ a/t" --> "r/g / e/ a/t" // 随机决定：「保持这两个子字符串的顺序不变」
    算法终止，结果字符串和 s2 相同，都是 "rgeat"
    这是一种能够扰乱 s1 得到 s2 的情形，可以认为 s2 是 s1 的扰乱字符串，返回 true
    示例 2：
    
    输入：s1 = "abcde", s2 = "caebd"
    输出：false
    示例 3：
    
    输入：s1 = "a", s2 = "a"
    输出：true
    提示：
    
    s1.length == s2.length
    1 <= s1.length <= 30
    s1 和 s2 由小写英文字母组成
     */
    
    
    /**
     * 是不是可以认为比如说 abcd 这些串随意的一个排列都是它的玄变串?
     * 不是，cabd就不是abcd的旋变串。
     * 
     * 思路:
     * 大过滤: 如果两个字符串的长度不相等，或者字符种类不一样，那一定不是旋变字符串，直接返回false.
     * 
     * 两个字符串，怎么怎么样？ -->样本对应模型
     *   按第一刀的情况来分
     * 
     * 定义函数f(s1, l1, r1, s2, l2, r2)f(s1,l1,r1,s2,l2,r2)﻿: 
     *   str1的L1~R1, 对str2 L2~R2等长, 看str1的L1~R1, 对str2 L2~R2这一段是不是玄变字符串
     * 1) base case 当然你 l1 到 r1 上只有一个字符， l1等r1, l2也等于r2, 如果只有一个字符的情况下,
     *    str1这一个字符跟str2这一个字符, 相等就是, 不想等就不是
     * 2) 普遍情况: 枚举的方式是 str1 第一刀切哪儿了, 0 ~ 0, 0 ~ 1, 0 ~ 2 等位置切
     *    可能性两种:
     *    1) str1切出来的左部分和str2等长的左部分是旋变字符串，而且str1剩下的右部分和str2剩下的右部分是旋变字符串。那整体就是旋变的
     *    2) str1切出来的左部分和str2右边等长的部分是旋变字符串，而且str1剩下的右部分和str2剩下的左部分是旋变字符串。那整体就是选变得。
     *    两种可能性只要有一种是true，那结果就是true
     * 
     */
    public boolean isScramble(String s1, String s2) {
        if ((s1 == null && s2 != null) || (s1 != null && s2 == null)) {
            return false;
        }
        if(s1 == null && s2 == null) { // 都是null
            return true;
        }
        if(s1.equals(s2)) { // 字符串相等，肯定是旋变
            return true;
        }
        char str1[] = s1.toCharArray();
        char str2[] = s2.toCharArray();
        // 如果长度不等，或者字符种类不一样，返回false
        if(!sameTypeSameNumber(str1, str2)) {
            return false;
        } 
        return process(str1, 0, str1.length - 1, str2, 0, str2.length - 1);
    }
    // str1[L1...R1] str2[L2...R2] 是否互为玄变串
    // 一定保证这两段是等长的！
    public boolean process(char str1[], int L1, int R1, char str2[], int L2, int R2) {
        if(L1 == R1) { // 只有一个字符，因为考察的范围是等长的，如果L1 == R1，那么L2一定等于R2，不用特别考虑
            return str1[L1] == str2[L2]; // base case
        }
        // 枚举第一刀的位置, < R1， 不能等于，因为要给右边留字符
        for(int leftEnd = L1; leftEnd < R1; leftEnd++) { // 举例子去推下标
            // str1左边和str2左边对应，str1右和str2右对应
            boolean p1 = process(str1, L1, leftEnd, str2, L2, L2 + (leftEnd - L1)) 
                    && process(str1, leftEnd + 1, R1, str2, L2 + (leftEnd - L1) + 1, R2);                                      
            // str1左边和str2右边的一小部分对应，str1右和str2左边对应
            boolean p2 = process(str1, L1, leftEnd, str2, R2 - (leftEnd - L1) , R2) 
                    && process(str1, leftEnd + 1, R1, str2, L2, R2 - (leftEnd - L1) - 1);  
            // 一种为true，就返回
            if(p1 || p2) {
                return true;
            }
        }
        // 都没有返回，就是false
        return false;
    }
    
    public static boolean sameTypeSameNumber(char[] str1, char[] str2) {
        if (str1.length != str2.length) { // 长度不相等，直接返回false
            return false;
        }
        int[] map = new int[256];
        for (int i = 0; i < str1.length; i++) {
            map[str1[i]]++;
        }
        for (int i = 0; i < str2.length; i++) {
            if (--map[str2[i]] < 0) { // 字符种类不一样，返回false
                return false;
            }
        }
        return true;
    }
    
    // 优化成3个参数
    public boolean isScramble2(String s1, String s2) {
        if ((s1 == null && s2 != null) || (s1 != null && s2 == null)) {
            return false;
        }
        if(s1 == null && s2 == null) { // 都是null
            return true;
        }
        if(s1.equals(s2)) { // 字符串相等，肯定是旋变
            return true;
        }
        char str1[] = s1.toCharArray();
        char str2[] = s2.toCharArray();
        // 如果长度不等，或者字符种类不一样，返回false
        if(!sameTypeSameNumber(str1, str2)) {
            return false;
        }
        return process2(str1, str2, 0, 0, str1.length);
    }
    // str1[L1...R1] str2[L2...R2] 是否互为玄变串
    // 一定保证这两段是等长的！
    // 因为是等长的，如果知道了L1和L2，以及长度size，就能知道右边界，所以可以优化成3个参数
    public boolean process2(char str1[], char str2[], int L1, int L2, int size) {
        if(size == 1) { // 只有一个字符，因为考察的范围是等长的，如果L1 == R1，那么L2一定等于R2，不用特别考虑
            return str1[L1] == str2[L2]; // base case
        }
        // 枚举第一刀的位置
        for (int leftPart = 1; leftPart < size; leftPart++) {
            // str1左边和str2左边对应，str1右和str2右对应
            boolean p1 = process2(str1, str2, L1, L2, leftPart)
                    && process2(str1, str2, L1 + leftPart, L2 + leftPart, size - leftPart);                                      
            // str1左边和str2右边的一小部分对应，str1右和str2左边对应
            boolean p2 = process2(str1, str2, L1, L2 + size - leftPart, leftPart) 
                    && process2(str1, str2, L1 + leftPart, L2, size - leftPart);  
            // 一种为true，就返回
            if(p1 || p2) {
                return true;
            }
        }
        // 都没有返回，就是false
        return false;
    }
    
    /**
     * 加傻缓存
     */
    public boolean isScramble3(String s1, String s2) {
        if ((s1 == null && s2 != null) || (s1 != null && s2 == null)) {
            return false;
        }
        if(s1 == null && s2 == null) { // 都是null
            return true;
        }
        if(s1.equals(s2)) { // 字符串相等，肯定是旋变
            return true;
        }
        char str1[] = s1.toCharArray();
        char str2[] = s2.toCharArray();
        // 如果长度不等，或者字符种类不一样，返回false
        if(!sameTypeSameNumber(str1, str2)) {
            return false;
        }
        int N = str1.length;
        // 0代表没算过，1代表true, -1代表false
        int dp[][][] = new int[N][N][N + 1];
        
        return process3(str1, str2, 0, 0, str1.length, dp);
    }
    // str1[L1...R1] str2[L2...R2] 是否互为玄变串
    // 一定保证这两段是等长的！
    // 因为是等长的，如果知道了L1和L2，以及长度size，就能知道右边界，所以可以优化成3个参数
    public boolean process3(char str1[], char str2[], int L1, int L2, int size, int dp[][][]) {
        if(dp[L1][L2][size] != 0) {
            return dp[L1][L2][size] == 1;
        }
        boolean ans = false;
        if(size == 1) { // 只有一个字符，因为考察的范围是等长的，如果L1 == R1，那么L2一定等于R2，不用特别考虑
            ans = str1[L1] == str2[L2]; // base case
        } else {
            // 枚举第一刀的位置
            for (int leftPart = 1; leftPart < size; leftPart++) {
                // str1左边和str2左边对应，str1右和str2右对应
                boolean p1 = process3(str1, str2, L1, L2, leftPart, dp)
                        && process3(str1, str2, L1 + leftPart, L2 + leftPart, size - leftPart, dp);                                      
                // str1左边和str2右边的一小部分对应，str1右和str2左边对应
                boolean p2 = process3(str1, str2, L1, L2 + size - leftPart, leftPart, dp) 
                        && process3(str1, str2, L1 + leftPart, L2, size - leftPart, dp);  
                // 一种为true，就返回
                if(p1 || p2) {
                    ans = true;
                    break;
                }
            }
        }
        // 都没有返回
        dp[L1][L2][size] = ans? 1 : -1;
        return ans;
    }
    
    /**
     * 加傻缓存就完事了，就是最右解
     * 也可以改成动态规划。
     */
    public static boolean isScramble4(String s1, String s2) {
        if ((s1 == null && s2 != null) || (s1 != null && s2 == null)) {
            return false;
        }
        if (s1 == null && s2 == null) {
            return true;
        }
        if (s1.equals(s2)) {
            return true;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        if (!sameTypeSameNumber(str1, str2)) {
            return false;
        }
        int N = s1.length();
        boolean[][][] dp = new boolean[N][N][N + 1];
        for (int L1 = 0; L1 < N; L1++) {
            for (int L2 = 0; L2 < N; L2++) {
                dp[L1][L2][1] = str1[L1] == str2[L2];
            }
        }
        // 第一层for循环含义是：依次填size=2层、size=3层..size=N层，每一层都是一个二维平面
        // 第二、三层for循环含义是：在具体的一层，整个面都要填写，所以用两个for循环去填一个二维面
        // L1的取值氛围是[0,N-size]，因为从L1出发往右长度为size的子串，L1是不能从N-size+1出发的，这样往右就不够size个字符了
        // L2的取值范围同理
        // 第4层for循环完全是递归函数怎么写，这里就怎么改的
        for (int size = 2; size <= N; size++) {
            for (int L1 = 0; L1 <= N - size; L1++) {
                for (int L2 = 0; L2 <= N - size; L2++) {
                    for (int leftPart = 1; leftPart < size; leftPart++) {
                        if ((dp[L1][L2][leftPart] && dp[L1 + leftPart][L2 + leftPart][size - leftPart])
                                || (dp[L1][L2 + size - leftPart][leftPart] && dp[L1 + leftPart][L2][size - leftPart])) {
                            dp[L1][L2][size] = true;
                            break;
                        }
                    }
                }
            }
        }
        return dp[0][0][N];
    }
    
}

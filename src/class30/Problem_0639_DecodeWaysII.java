package class30;

// 639. 解码方法 II
public class Problem_0639_DecodeWaysII {
    /**
     * 一条包含字母 A-Z 的消息通过以下的方式进行了 编码 ：
     * 'A' -> "1
     * 'B' -> "2"
     * ...
     * 'Z' -> "26"
     * 要 解码 一条已编码的消息，所有的数字都必须分组，然后按原来的编码方案反向映射回字母（可能存在多种方式）。例如，"11106" 可以映射为：
     *  "AAJF" 对应分组 (1 1 10 6)
     *  "KJF" 对应分组 (11 10 6)
     *  注意，像 (1 11 06) 这样的分组是无效的，因为 "06" 不可以映射为 'F' ，因为 "6" 与 "06" 不同。
     *  除了 上面描述的数字字母映射方案，编码消息中可能包含 '*' 字符，可以表示从 '1' 到 '9' 的任一数字（不包括 '0'）。
     *  例如，编码字符串 "1*" 可以表示 "11"、"12"、"13"、"14"、"15"、"16"、"17"、"18" 或 "19" 中的任意一条消息。对 "1*" 进行解码，
     *  相当于解码该字符串可以表示的任何编码消息。
     *  给你一个字符串 s ，由数字和 '*' 字符组成，返回 解码 该字符串的方法 数目 。
     *  由于答案数目可能非常大，返回 109 + 7 的 模 。
     */
    
    /**
     * 思路:
     * 暴力递归到动态规划
     * 
     * process(str, index)
     * 潜台词：str[0...index-1]已经转化完了，不用操心了
     * str从index出发，及其往后的字符，能转出多少有效的，返回方法数
     * 
     * 分str[i]是不是*分别讨论
     */
    public int numDecodings(String s) {
        if(s == null || s.length() == 0) {
            return 0;
        }
        char str[] = s.toCharArray();
        return process(str, 0);
    }
    
    // 当前来到index位置，index之前的字符都已经搞完了，
    // index 及其往后的字做决定，你给我返回有多少种转换方法
    public int process(char str[], int index) {
        if(index == str.length) { // 已经搞完了，找到一种
            return 1;
        }
        // 如果面对的是0，说明前面的决定不对，0中
        if(str[index] == '0') {
            return 0;
        }
        // i位置坐决定
        // 如果i位置不是*号
        if(str[index] != '*') {
            // i位置单转, 去搞i+1位置
            int p1 = process(str, index + 1);
            // 如果index + 1到达了数组末尾
            if(index + 1 == str.length) { // 后面没有字符可以和i位置连接了
                return p1;
            }
            // i和i+1连接
            if(str[index + 1] != '*') { // 如果i+1位置不是*
                int num = (str[index] - '0') * 10 + (str[index + 1] - '0');
                int p2 = 0;
                if(num <= 26) { // i和i + 1能连接
                    p2 = process(str, index + 2);
                }
                return p1 + p2;
            } else { // i + 1位置是*
                // i位置是1  i+1可以是1~9 9种
                // i位置是2  i+1可以是1 2 3 4 5 6 6种
                // i位置是3  超过了26，不符合条件
                int p2 = 0;
                if(str[index] < '3') {
                    p2 = (str[index] == '1' ? 9 : 6) * process(str, index + 2);
                }
                return p1 + p2;
            }
        } else { // i位置是*号
            // i位置单转，9种, 去搞i+1位置
            int p1 = 9 * process(str, index + 1);
            // 如果index + 1到达了数组末尾
            if(index + 1 == str.length) { // 后面没有字符可以和i位置连接了
                return p1;
            }
            // 和 i + 1位置连接
            if(str[index + 1] != '*') { // 如果i+1位置不是*
                // *0  10 20 2种
                // *1  11 21 2种
                // *2  12 22 
                // *3  13 23 
                // *4  14 24
                // *5  15 25
                // *6  16 26
                // *7  17
                // *8  18
                // *9  19
                int p2 = ((str[index + 1] < '7') ? 2 : 1) * process(str, index + 2);
                return p1 + p2;
            } else { // i + 1位置是*
               //**
               // 11  12 13 ~ 19 9种
               // 21  22 23 24 25 26 6种
               int p2 = 15 * process(str, index + 2);
               return p1 + p2;
            }
        }
    }
    
    
    /**
     * 改动态规划
     * 可变参数index 范围: 0~N  int dp[N + 1]
     * 
     * 分析依赖: 每个位置斗志依赖它后面的位置，所以从后往前填表
     */
    public static int numDecodings2(String s) {
        if(s == null || s.length() == 0) {
            return 0;
        }
        char str[] = s.toCharArray();
        int N = str.length;
        long dp[] = new long[N + 1];
        long mod = 1000000007;
        // base case
        dp[N] = 1;
        // "*1*1*0"
        // 从后往前填表
        for(int index = N - 1; index >= 0; index--) {
            if(str[index] == '0') { // 如果遇到了单独的0，说明前面做的决定不对
                dp[index] = 0;
            } else {
                if(str[index] != '*') {
                    // i位置单转, 去搞i+1位置
                    long p1 = dp[index + 1];
                    // 如果index + 1到达了数组末尾
                    if(index + 1 == str.length) { // 后面没有字符可以和i位置连接了
                        dp[index] = p1;
                    } else {
                        // i和i+1连接
                        if(str[index + 1] != '*') { // 如果i+1位置不是*
                            int num = (str[index] - '0') * 10 + (str[index + 1] - '0');
                            long p2 = 0;
                            if(num <= 26) { // i和i + 1能连接
                                p2 = dp[index + 2];
                            }
                            dp[index] = p1 + p2;
                        } else { // i + 1位置是*
                            long p2 = 0;
                            if(str[index] < '3') {
                                p2 = (str[index] == '1' ? 9 : 6) * dp[index + 2];
                            }
                            dp[index] = p1 + p2;
                        }
                    }
                } else { // i位置是*号
                    long p1 = 9 * dp[index + 1];
                    if(index + 1 == str.length) {
                        dp[index] = p1;
                    } else {
                        //i 和 i + 1位置连接
                        if(str[index + 1] != '*') { // 如果i+1位置不是*
                            long p2 = ((str[index + 1] < '7') ? 2 : 1) * dp[index + 2];
                            dp[index] = p1 + p2;
                        } else { // i + 1位置是*
                           long p2 = 15 * dp[index + 2];
                           dp[index] = p1 + p2;
                        }
                    }
                }
            }
            // leetcode要求
            dp[index] %= mod;
        }
        return (int)dp[0];
    }
    
    public static void main(String[] args) {
        String teString = "*1*1*0";
        numDecodings2(teString);
    }

}

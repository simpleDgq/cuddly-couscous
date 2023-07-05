package class30;

// 91. 解码方法
public class Problem_0091_DecodeWays {
    /**
     * 一条包含字母 A-Z 的消息通过以下映射进行了 编码 ：
     * 'A' -> "1"
     * 'B' -> "2"
     * ...
     * 'Z' -> "26"
     * 要 解码 已编码的消息，所有数字必须基于上述映射的方法，反向映射回字母（可能有多种方法）。
     * 例如，"11106" 可以映射为："AAJF" ，将消息分组为 (1 1 10 6)
     *  "KJF" ，将消息分组为 (11 10 6)
     *  注意，消息不能分组为  (1 11 06) ，因为 "06" 不能映射为 "F" ，这是由于 "6" 和 "06" 在映射中并不等价。
     *  给你一个只含数字的 非空 字符串 s ，请计算并返回 解码 方法的 总数 。
     *  题目数据保证答案肯定是一个 32 位 的整数。
     */
    
    /**
     * 思路: 递归 动态规划
     * process(char[] str, int index)
     * 潜台词：str[0...index-1]已经转化完了，不用操心了
     * str从index出发，及其往后的字符，能转出多少有效的，返回方法数
     * 
     */
    public int numDecodings(String s) {
        if(s == null || s.length() == 0) {
            return 0;
        }
        char str[] = s.toCharArray();
        return process(str, 0);
    }
    
    /*
    * 潜台词：str[0...index-1]已经转化完了，不用操心了
    * str从index出发，及其往后的字符，能转出多少有效的，返回方法数
    */
    public static int process(char[] str, int index) {
        if(index == str.length) { // 来到了length位置，说明找到了一种方案，返回1
            return 1;
        }
        // 如果当前的字符是0，说明前面的决定错了，因为0不能单独或者和其它字符连接之后，对应一个英文字母
        if(str[index] == '0') {
            return 0;
        }
        // 两种决定
        // index位置单转, 直接去搞下一个位置
        int ways = process(str, index + 1);
        // index位置和后一个字符连接之后去转换
        if(index + 1 == str.length) { // 越界了，说明不能连接，只能单个转，直接返回单个转的方法数
            return ways;
        }
        int num = (str[index] - '0') * 10 + (str[index + 1] - '0');
        // 连接之后的数字要小于等于26，英文字符只有26个
        if (num <= 26) {
            // 去搞index + 2位置
            ways += process(str, index + 2);
        }
        return ways;
    }
    
    
    /**
     * 改动态规划
     * 分析可变参数: index 返回: 0~length --> int dp[N + 1]
     * 
     * 每个位置，只依赖它后面的元素，从右往左填表
     */
    public int numDecodings2(String s) {
        if(s == null || s.length() == 0) {
            return 0;
        }
        char str[] = s.toCharArray();
        int N = str.length;
        int dp[]= new int[N + 1];
        // base case 
        dp[N] = 1;
        // 从左往右填
        for(int i = N - 1; i >= 0; i--) {
            if(str[i] != '0') {
                // 两种决定
                // index位置单转, 直接去搞下一个位置
                int ways = dp[i + 1];
                // index位置和后一个字符连接之后去转换
                if(i + 1 == str.length) { // 越界了，说明不能连接，只能单个转，直接返回单个转的方法数
                    dp[i] = ways;
                    continue;
                }
                int num = (str[i] - '0') * 10 + (str[i + 1] - '0');
                // 连接之后的数字要小于等于26，英文字符只有26个
                if (num <= 26) {
                    // 去搞index + 2位置
                    ways += dp[i + 2];
                }
                dp[i] = ways;
            }
        }
        
        // 主函数
        return dp[0];
    }
    
}

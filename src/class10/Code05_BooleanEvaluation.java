package class10;


//本题测试链接 : https://leetcode-cn.com/problems/boolean-evaluation-lcci/
public class Code05_BooleanEvaluation {
    /* 布尔运算
     * 给定一个布尔表达式和一个期望的布尔结果 result，布尔表达式由 0 (false)、1 (true)、& (AND)、 | (OR) 和 ^ (XOR) 符号组成。实现一个函数，算出有几种可使该表达式得出 result 值的括号方法。

        示例 1:
        
        输入: s = "1^0|0|1", result = 0
        
        输出: 2
        解释: 两种可能的括号方法是
        1^(0|(0|1))
        1^((0|0)|1)
        示例 2:
        
        输入: s = "0&0&0&1^1|0", result = 1
        
        输出: 10
        提示：
        
        运算符的数量不超过 19 个
     */
    
    /**
     * 范围尝试模型
     * 用符号作为划分，符号左边的一串给我返回弄出true和false的方法数；
     * 符号右边的一串给我返回弄出true和false的方法数，
     * 我再结合左边、右边的情况，加工出来所有可能的true或者fasle的方法数。
     * 
     * 主函数调用的时候，如果要求的是1，就返回true的方法数，如果是0，就返回是false的方法数
     */
    public class Info {
        int f;
        int t;
        public Info(int fa, int tr) {
           this.f = fa;
           this.t = tr;
        }
    }
    // 限制:
    // L...R上，一定有奇数个字符
    // L位置的字符和R位置的字符，非0即1，不能是逻辑符号！
    // L-R这段范围上，给我返回为true和false的方法数
    public Info process(char str[], int L, int R) {
        int fa = 0;
        int tr = 0;
        // base case
        if(L == R) { // 如果只有一个字符，如果这个字符是0，那返回false的总数就是1
            fa = str[L] == '0' ? 1 : 0;
            tr = str[L] == '1' ? 1 : 0;
        } else {
            // 每个符号都去尝试一遍
            for(int spilt = L + 1; spilt <= R - 1; spilt += 2) {
                Info lInfo = process(str, L, spilt - 1);
                Info rInfo = process(str, spilt + 1, R);
                int a = lInfo.f;
                int b = lInfo.t;
                int c = rInfo.f;
                int d = rInfo.t;
                switch (str[spilt]) {
                    case '&':
                        fa += a * c + a * d + b * c; // 注意这里是+=，因为每个符号的结果都要累加起来，才是最终的结果
                        tr += b * d;
                        break;
                    case '|':
                        fa += a * c;
                        tr += a * d + b * c + b * d;
                        break;
                    case '^':
                        fa += a * c + b * d;
                        tr += a * d + b * c;
                        break;
                }
            }
        }
        return new Info(fa, tr);
    }
    
    public int countEval(String s, int result) {
        if(s == null || s.length() == 0 || result > 1 || result < 0) {
            return 0;
        }
        int N = s.length();
        char str[] = s.toCharArray();
        Info info = process(str, 0, N - 1);
        return result == 0 ? info.f : info.t;
    }
    
    
    /*
     * 加傻缓存
     */
    public Info process1(char str[], int L, int R, Info dp[][]) {
        if(dp[L][R] != null) { // 不等于null，表示已经算过，直接返回
            return dp[L][R];
        }
        int fa = 0;
        int tr = 0;
        // base case
        if(L == R) { // 如果只有一个字符，如果这个字符是0，那返回false的总数就是1
            fa = str[L] == '0' ? 1 : 0;
            tr = str[L] == '1' ? 1 : 0;
        } else {
            // 每个符号都去尝试一遍
            for(int spilt = L + 1; spilt <= R - 1; spilt += 2) {
                Info lInfo = process1(str, L, spilt - 1, dp);
                Info rInfo = process1(str, spilt + 1, R, dp);
                int a = lInfo.f;
                int b = lInfo.t;
                int c = rInfo.f;
                int d = rInfo.t;
                switch (str[spilt]) {
                    case '&':
                        fa += a * c + a * d + b * c; // 注意这里是+=，因为每个符号的结果都要累加起来，才是最终的结果
                        tr += b * d;
                        break;
                    case '|':
                        fa += a * c;
                        tr += a * d + b * c + b * d;
                        break;
                    case '^':
                        fa += a * c + b * d;
                        tr += a * d + b * c;
                        break;
                }
            }
        }
        // 保存一下
        dp[L][R] = new Info(fa, tr);
        return dp[L][R];
    }
    
    public int countEval0(String s, int result) {
        if(s == null || s.length() == 0 || result > 1 || result < 0) {
            return 0;
        }
        int N = s.length();
        Info dp[][] = new Info[N][N];
        char str[] = s.toCharArray();
        Info info = process1(str, 0, N - 1, dp);
        return result == 0 ? info.f : info.t;
    }
}

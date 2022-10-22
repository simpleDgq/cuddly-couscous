package class05;

import java.util.ArrayList;
import java.util.Comparator;

public class Code04_DeleteMinCost {
    
    // 题目：
    // 给定两个字符串s1和s2，问s2最少删除多少字符可以成为s1的子串？
    // 比如 s1 = "abcde"，s2 = "axbc"
    // 返回 1

    // 解法一
    // 求出str2所有的子序列，然后按照长度排序，长度大的排在前面。
    // 然后考察哪个子序列字符串和s1的某个子串相等(KMP)，答案就出来了。
    // 分析：
    // 因为题目原本的样本数据中，有特别说明s2的长度很小。所以这么做也没有太大问题，也几乎不会超时。
    // 但是如果某一次考试给定的s2长度远大于s1，这么做就不合适了。
    
    // 时间复杂度
    /*str1 长度N, str2 长度M
        第一步, 生成所有的子序列, 子序列个数2^M, 每个子序列要生成字符串, 所以申城子序列代价O*(2^M* M)
        第二部: 对于每个子序列都要做KMP O(2^M * N)
        如果M不大, 复杂度O(2^M* N) 就是最优解
    */
    
    public static int deleteMinCost(String str1, String str2) {
        if(str1 == null || str2 == null) {
           return 0; 
        }
        char s2[] = str2.toCharArray();
        ArrayList<String> results = new ArrayList<String>();
        process(0, "", s2, results);
        results.sort(new LengthCom());
        
        int ans = str2.length(); // str2 所有的子序列，都不能包含在str1中。只有删除str2所有的字符，搞出空串，才是str1的子串
        for(String s : results) {
           if(str1.indexOf(s) != -1) {
               ans = str2.length() - s.length();
               break;
           }
        }
        return ans;
    }
    
    // 生成str2的所有子序列
    // 当前来到index位置，index之前生成的子序列是path，你给我返回str所有可能的子序列
    public static void process(int index, String path, char[] str, ArrayList<String> results) {
        if(index == str.length) {
            results.add(path);
            return;
        }
        // 要index位置
        process(index + 1, path + str[index], str, results);
        // 不要index位置
        process(index + 1, path, str, results);
    }
    
    public static class LengthCom implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            // TODO Auto-generated method stub
            return o2.length() - o1.length();
        }
    }
    
    
    // 解法二
    // 生成所有s1的子串
    // 然后看s2能不能编辑成s1的某个子串，求最小编辑距离
    // 如果s1的长度较小，s2长度较大，这个方法比较合适
   
    /*复杂度分析:
     * str2长度M, str1 的子串个数 O(N^2) (以0号位置结尾有几个，以1号位置结尾有几个...,所以是O(N^2)), 
     * str2对每一个str1的子串都是一张M*N二维表,都是一个编辑距离问题
     * ==> 所以有N^2张二维表, 每张表大小M*N, 所以总的复杂度是O(N^3 * M)
     *   
     *   而前一个方法的复杂度 O(2^M * N), 当M稍微大一点儿, 这个方法就废了, 因为2^M次方增长很快
     *   所以需要根据数据量挑解法
     *   如果M很小, 选O(2^M * N), 但凡M稍微大一点儿, 选O(N^3 * M)
    */
    public static int deleteMinCost2(String str1, String str2) {
        if(str1 == null || str2 == null) {
           return 0; 
        }
        int N = str1.length();
        int ans = Integer.MAX_VALUE;
        for(int start = 0; start <= N - 1; start++) {
            for(int end = start + 1; end <= N; end++) {
               ans = Math.min(ans, deleteProcess(str2, str1.substring(start, end)));  // str1的子串
            }
        }
        return ans == Integer.MAX_VALUE ? str2.length() : ans;
    }
    
    // str2 通过delete的方式生成str1的最小代价
    public static int deleteProcess(String str2, String str1) {
        // dp[i][j] -> str2的前i个字符搞定str1前j个字符的最小代价
        // str2[0 ~ i - 1] 搞定str1[0 ~j - 1]
        int N = str2.length();
        int M = str1.length();
        
        char s2[] = str2.toCharArray();
        char s1[] = str1.toCharArray();
        
        int dp[][] = new int[N + 1][M + 1];
        
        for(int i = 0; i <= N; i++) {
            for(int j = 0; j <= M; j++) {
               dp[i][j] = Integer.MAX_VALUE; // 最大值，表示str2搞不定str2。 比如str2: ad.  str1: abcd
            }
        }
        // dp的对角线右半部分是不用的
        // 填好第0列，str2 取i个字符，搞定str1 0个字符，代价为 dc * i = i。 dc是删除代价，本题就是1.
        for(int i = 0; i <= N; i++) {
            dp[i][0] = i;  //  dp[0][0] = 0; // str2 取0个字符，搞定str1 0个字符，代价为0
        }
        // 普遍位置。 从左往右，从上往下
        // dp[i][j]的含义：
        // str2[0..i-1]仅通过删除行为变成s1sub[0..j-1]的最小代价
        // 可能性一：
        // str2[0..i]变的过程中，不保留最后一个字符(str2[i])，
        // 那么就是通过str2[0..i-1]变成s1sub[0..j]之后，再最后删掉str2[i]即可 -> dp[i][j] = dp[i-1][j] + 1
        // 可能性二：
        // str2[0..i]变的过程中，想保留最后一个字符(str2[i])，然后变成s1sub[0..j]，
        // 这要求str2[i] == s1sub[j]才有这种可能, 然后str2[0..i-1]变成s1sub[0..j-1]即可
        // 也就是str2[i] == s1sub[j] 的条件下，dp[i][j] = dp[i-1][j-1]
        for(int i = 1; i <= N; i++) {
            for(int j = 1;  j <= M && j <= i; j++) {
                int p1 = Integer.MAX_VALUE;
                if(dp[i - 1][j] != Integer.MAX_VALUE) { // 保证dp[i - 1][j]是有效的，例如abx ， ck 这两个字符串，最后一个字符不一样
                                                        // 删掉x之后，ab是不能搞出ck的，dp[i - 1][j]是无效的，这种case应该被跳过
                    p1 = dp[i - 1][j] + 1;
                }
                int p2 = Integer.MAX_VALUE;
                if(s2[i - 1] == s1[j - 1] && dp[i - 1][j - 1] != Integer.MAX_VALUE) { // 同上面的理
                    p2 = dp[i - 1][j - 1];
                }
                dp[i][j] = Math.min(p1, p2);
            }
        }
        
        /*
         * 简化写法，省去几个变量
         */
//        for(int i = 1; i <= N; i++) {
//            for(int j = 1;  j <= M && j <= i; j++) {
//                if(dp[i - 1][j] != Integer.MAX_VALUE) {
//                    dp[i][j] = dp[i - 1][j] + 1;
//                }
//                if(s2[i - 1] == s1[j - 1] && dp[i - 1][j - 1] != Integer.MAX_VALUE) {
//                    dp[i][j] = Math.min(dp[i - 1][j - 1], dp[i][j]);
//                }
//            }
//        }
        return dp[N][M];
    }
    
    
    // 解法二继续优化: O(N^2 * M)
    // str2 先跟对应str1 空串的时候的表，就是第0列
    // str2 先跟对应str1 1个字符的时候的表，就是第1列， 第1列可以通过第0列得来
    // str2 先跟对应str1 2个空串的时候的表，就是第2列，第2列可以通过第0列和第1列得来
    // ....
    // Str1的子串总共有N^2个，总共就有N^2列，没一列填M行，所以O(N^2 * M)
    // 略...课上没讲，代码懒得搞了
    
    
    public static String generateRandomString(int l, int v) {
        int len = (int) (Math.random() * l);
        char[] str = new char[len];
        for (int i = 0; i < len; i++) {
            str[i] = (char) ('a' + (int) (Math.random() * v));
        }
        return String.valueOf(str);
    }

    public static void main(String[] args) {

        int str1Len = 20;
        int str2Len = 10;
        int v = 5;
        int testTime = 10000;
        boolean pass = true;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            String str1 = generateRandomString(str1Len, v);
            String str2 = generateRandomString(str2Len, v);
            int ans1 = deleteMinCost(str1, str2);
            int ans4 = deleteMinCost2(str1, str2);
            if ( ans1 != ans4) {
                pass = false;
                System.out.println(str1);
                System.out.println(str2);
                System.out.println(ans1);
                break;
            }
        }
        System.out.println("test pass : " + pass);
    }
}

package class07;

import java.util.HashMap;

public class Code06_SplitStringMaxValue {
    /* 字符串拼接最大分数
     * 
        String str, int K, String[] parts, int[] record
        Parts和records的长度一样长，str一定要分割成k个部分，分割出来的每部分在parts里必须得有，
        那一部分的得分在record里，请问str切成k个部分，返回最大得分
        
        和上一题一样，其实就是用parts里面的贴纸拼出str，而且要是K份，只不过返回的是最大分数
    *
     */
    // O(N ^ 3)
    public static int maxValue(String str, int K, String[] parts, int[] record) {
        if(str == null || str.length() == 0 || K == 0 || parts == null 
                || parts.length == 0 || record == null || record.length == 0) {
            return -1;
        }
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        
        for(int i = 0; i <= parts.length - 1; i++) {
            map.put(parts[i], record[i]);
        }
        return process(str, 0, K, map);
    }
    /*
     * 当前来到str的i位置，剩余rest个部分需要用parts里面的字符串搞定，
     * 你给我返回能够获得的最大分数
     */
    public static int process(String str, int i, int rest, HashMap<String, Integer> map) {
        if(rest < 0) { // 剩余负数份需要搞定，怎么也搞不定了，直接返回
            return -1;
        }
        if(i == str.length()) { // 来到了最后一个位置，而且没有需要搞定的份数了。返回0，不需要贴纸，0分。 否则搞不定，返回-1
            return rest == 0 ? 0: -1; 
        }
        int ans = -1;
        for(int end = i; end <= str.length() - 1; end++) {
            String prefix = str.substring(i, end + 1);
            // 当前的前缀存在，那么搞定剩下的字符串，返回搞定剩下的字符串能够得到的最大分数
            // 如果不能搞定，返回-1
            // 如果能够搞定，剩下字符串能够获得的最大分数 + 搞定当前前缀获得的分数map.get(prefix)
            // 和ans PK，看能不能更新答案
            int next = map.containsKey(prefix) ? process(str, end + 1, rest - 1, map) : -1;
            if(next != -1) {
               ans = Math.max(ans, map.get(prefix) + next);
            }
        }
        return ans;
    }
    
    
    /*
     * 动态规划
     * 可变参数i 和 rest
     * 范围：
     * i ： 0 ~ N
     * rest: 0~K
     * ==> dp[N + 1][K + 1]
     * 
     * 分析依赖:
     * dp[i]只依赖它的下面的位置，以及下一行左边的位置
     * 
     * N * K个格子，
     * 每个格子求答案都是O(N^2)
     * 
     * O(N^3 * K)
     */
    public static int maxValue2(String str, int K, String[] parts, int[] record) {
        if(str == null || str.length() == 0 || K == 0 || parts == null 
                || parts.length == 0 || record == null || record.length == 0) {
            return -1;
        }
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        
        for(int i = 0; i <= parts.length - 1; i++) {
            map.put(parts[i], record[i]);
        }
        
        int N = str.length();
        int dp[][] = new int[N + 1][K + 1];
        // base case
//        if(i == str.length()) { // 来到了最后一个位置，而且没有需要搞定的份数了。返回0，不需要贴纸，0分
//            return rest == 0 ? 0: -1; 
//        }
        dp[N][0] = 0;
        for(int rest = 1; rest <= K; rest++) {
            dp[N][rest] = -1;
        }
        // 从下往上，从左往右填表
        for(int i = N - 1; i >= 0; i--) {
            for(int rest = 0; rest <= K; rest++) {
                int ans = -1;
                for(int end = i; end <= str.length() - 1; end++) {
                    String prefix = str.substring(i, end + 1);
                    int next = -1;
                    if(rest - 1 >= 0) {
                       next = map.containsKey(prefix) ? dp[end + 1][rest - 1]: -1;
                    }
                    if(next != -1) {
                       ans = Math.max(ans, map.get(prefix) + next);
                    }
                }
                dp[i][rest] = ans;
            }
        }
        return dp[0][K];
    }
   
    
    /*
     * 动态规划 + 前缀树
     * 
     * K * N个格子，每个格子，O(N)
     * 
     * 前缀树O(M)
     * 
     * O(M) + O(N^2 * K)
     */
    public static class Node {
        public Node nexts[]; // 走向字符的路
        public int value; // 分数
        
        public Node() {
            nexts = new Node[26];
            value = -1;
        }
    }
    
    // 建前缀树
    public static Node constructTrieTree(String arr[], int[] record) {
        if(arr == null || arr.length == 0 || record == null || record.length == 0) {
            return null;
        }
        Node head = new Node(); // 建好头节点
        for(int j = 0; j <= arr.length - 1; j++) { // arr中的每一个字符串，放到前缀树上
            char strs[] = arr[j].toCharArray();
            Node cur = head;
            for(int i = 0; i <= strs.length - 1; i++) {
                int next = strs[i] - 'a';
                if(cur.nexts[next] == null) { // 没有走向next的路，建好
                    cur.nexts[next] = new Node();
                } // 这里不能else，因为，要往下走。例如，abc，走向a没有路，新建了，下一个字符b，不能从头开始，而要从a建出来的节点开始
                cur = cur.nexts[next]; // 走向下一个节点
            }
            cur.value = record[j]; // 一个字符串遍历完，最后执向的节点就是该字符串的结尾字符, 赋予分数
        }
        return head;
    }
    
    public static int maxValue3(String str, int K, String[] parts, int[] record) {
        if(str == null || str.length() == 0 || K == 0 || parts == null 
                || parts.length == 0 || record == null || record.length == 0) {
            return -1;
        }
       // 建前缀树
        Node rootNode = constructTrieTree(parts, record);
        
        int N = str.length();
        int dp[][] = new int[N + 1][K + 1];
        dp[N][0] = 0;
        for(int rest = 1; rest <= K; rest++) {
            dp[N][rest] = -1;
        }
        char strs[] = str.toCharArray();
        // 从下往上，从左往右填表
        for(int i = N - 1; i >= 0; i--) {
            for(int rest = 0; rest <= K; rest++) {
                int ans = -1;
                Node cur = rootNode; //这里需要注意: 每一个新的位置，都必须从头节点出发
                for(int end = i; end <= N - 1; end++) {
                    // 字符串的前缀，在前缀树上去找，看有没有
                    // 每次都只找一个字符
                    int path = strs[end] - 'a';
                    int next = -1;
                    // abcde,  刚开始是从a出发，现在例如来到了c，找不到去d的路了，那么也不可能有从a到e的路，直接break
                    // 如果当前字符出发，找不到路了，说明从当前字符开始，str剩下的其它的前缀也不可能在前缀树上存在，直接返回
                    if(cur.nexts[path] == null) {
                        break;
                    }
                    cur = cur.nexts[path]; // 向下走
                    // 如果cur.value != -1，说明当前前缀字符串在parts中, 搞剩下的前缀去吧
                    if(rest - 1 >= 0 && cur.value != -1) {
                       next = dp[end + 1][rest - 1];
                    }
                    if(next != -1) { // 剩下的搞出最大 + 当前的value 和当前ans PK出最大值
                       ans = Math.max(ans, cur.value + next);
                    }
                }
                dp[i][rest] = ans;
            }
        }
        return dp[0][K];
    }
    
    public static void main(String[] args) {
        String str = "abcdefg";
        int K = 3;
        String[] parts = { "abc", "def", "g", "ab", "cd", "efg", "defg" };
        int[] record = { 1, 1, 1, 3, 3, 3, 2 };
        System.out.println(maxValue(str, K, parts, record));
        System.out.println(maxValue2(str, K, parts, record));
        System.out.println(maxValue3(str, K, parts, record));
    }
}

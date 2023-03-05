package class24;

// https://leetcode.cn/problems/remove-duplicate-letters/
public class Code06_RemoveDuplicateLettersLessLexi {
    /**
     * 
     * 316. 去除重复字母
     * 
     * 给你一个字符串 s ，请你去除字符串中重复的字母，使得每个字母只出现一次。需保证 返回结果的字典序最小（要求不能打乱其他字符的相对位置）。

            注意：该题与 1081.[[不同字符的最小子序列]]相同
            
            示例 1：
            
            输入：s = "bcabc"
            输出："abc"
            示例 2：
            
            输入：s = "cbacdcbc"
            输出："acdb"
            
            提示：
            
            1 <= s.length <= 10^4
            s 由小写英文字母组成
     */
    
    /**
     * 思路: 贪心 这种题目只能背了
     * 
     * 题意: 每种字符只保留一个, 请你返回字典序最小的结果
             相对次序不能乱
     * 
     * 大思路: 先建立词频统计表, 从左往右划线, 划线的词从词频表里删除
     * 
     *  我如果在这线里面，这条线代表啥意思？我如果要选保留的第一个字符的话，我只能在这条线里选，当某个字符的词频为0的时候，
        不能够再把右侧的字符考虑进来了。因为后面没有这个字符了
        
        当我决定保留一个字符的时候，我保留这个字符的同时，左侧字符全不要, 右侧字符可以继续选，
        我在后面的字符里再选一个字符Y，那么 Y 左边的字符就不能再选了，在后面继续选，这是我的主流程。
        
        每次选选ascii码最小的
        
        
        时间复杂度: 遍历一遍总会保留一个字符, 而且以后的字符串中是不含有这个字符的。
                所以你str中如果含有 K 种字符，复杂度就是O(K*N)。这个 K 大写加小写也不就是 52 种字符, 所以O(N)。
     */
    public String removeDuplicateLetters(String s) {
        if(s == null || s.length() < 2) {
            return s; // null或者长度小于2的时候，返回s
        }
        int N = s.length();
        int map[] = new int[256];
        char str[] = s.toCharArray();
        for(int i = 0; i <= N - 1; i++) {
            map[str[i]]++;
        }
        int minIndex = 0; // ascii码最小的字符的位置
        for(int i = 0; i <= N - 1; i++) {
            minIndex = str[minIndex] > str[i] ? i : minIndex; // 记录ascii码最小的字符的位置
            // 划线过程中，如果某个字符的词频是0了，线不能在往右了
            if(--map[str[i]] == 0) {
                break; // break, 需要从画出来的线中选ascii码值最小的字符
            }
        }
        // 需要从画出来的线中选ascii码值最小的字符
        // minIndex左边的都不用，minIndex右边的剩下的字符串，去掉ascii码最小的字符之后，继续选剩下的字符
        return str[minIndex] + removeDuplicateLetters(s.substring(minIndex + 1)
                .replaceAll(String.valueOf(str[minIndex]), ""));
    }
}

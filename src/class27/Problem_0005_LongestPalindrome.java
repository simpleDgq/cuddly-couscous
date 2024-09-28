package class27;

// https://leetcode.cn/problems/longest-palindromic-substring
// 5. 最长回文子串
public class Problem_0005_LongestPalindrome {
    /**
     * 动态规划
     * dp[i][j]: str[i...j]是否是回文串?
     * 如果str[i] != str[j] 一定不是回文串
     * 如果str[i] == str[j] 是否是回文串取决于str[i-1...j-1] 是否是回文 dp[i][j] = str[i] == str[j]
     * && dp[i + 1][j - 1]
     * 依赖左下角的位置
     * 
     * 填好主对角线，一个字符，全是true
     * 填好第二条对角线，两个字符，如果相等就是回文，不相等，就不是
     * 填普遍位置
     * 
     * 如果dp[i][j]是回文，更新回文的最大长度j - i + 1, 回文开始的下标为i
     * 需要注意的是在填第二条对角线的时候也要搜集答案
     * 
     * 搞一个start收集最长回文串的开始位置，已经maxLength收集最长回文串的长度，最终只需要截取就行
     * 
     */
    public String longestPalindrome1(String s) {
        if (s == null || s.length() == 0) {
            return null;
        }
        int N = s.length();
        boolean dp[][] = new boolean[N][N];
        char str[] = s.toCharArray();
        // 填主对角线
        for (int i = 0; i <= N - 1; i++) {
            dp[i][i] = true;
        }
        int maxLength = 1; // 一个字符，至少长度是1
        int start = 0;
        // 第二条对接线
        for (int i = 0; i <= N - 2; i++) {
            dp[i][i + 1] = str[i] == str[i + 1];
            // 填第二条对角线的时候，也要收集答案
            if (dp[i][i + 1] && 2 > maxLength) {
                maxLength = 2;
                start = i;
            }
        }

        // 普遍位置 从下往上，从左往右填
        for (int i = N - 3; i >= 0; i--) {
            for (int j = i + 2; j <= N - 1; j++) {
                dp[i][j] = str[i] == str[j] && dp[i + 1][j - 1];
                if (dp[i][j] && j - i + 1 > maxLength) { // 如果str[i...j]是回文 且 是当前最大的回文
                    maxLength = j - i + 1;
                    start = i;
                }
            }
        }
        // 截取
        return s.substring(start, start + maxLength);
    }

    /**
     * Manacher 算法 - 背
     * 
     * 1. 回文直径与回文半径：整个回文的长度就是回文直径，
     * 如果整个长度是奇数，那么回文半径就是从中间位置开始，算上中间位置，一直到最右边的长度（回文直径 / 2） + 1;
     * 如果是偶数，则是直径/2。在加了特殊字符之后，长度就都是奇数，不存在偶数情况
     * 例如 #1#2#1#  2这个数的回文半径是4  指的就是2开始的这四个字符：2#1#
     * #1#2#:1这个数开始的回文半径就是2指的是1开始的这两个字符：1#
     * 
     * 2. 回文半径数组：
     * str经过特殊处理变成str'，从左往右，每一个位置的字符，求回文半径存在数组里面
     * 
     * 3. 最右回文边界R：记录当前所有找到的回文能够到达的最右边界。
     * 
     * 4. 最右回文边界的中心C，记录的是与R对应的回文串的中心点位置。
     * 
     * 4种情况
     * 1) i在R外，暴力扩
     * 2) i在R内，三种情况: i'是i的镜像位置
     * 1. 如果i'扩出来的回文区域在L~R内，i位置的结果直接获取，取的是i'位置的值（回文半径），O(1) -> pArr[i] = pArr[2 * C - i]
     * 2.如果i'扩出来的回文区域的左边界在L外，i位置的结果也是直接获取，取的是i~R（i位置回文半径就是i~R），O(1) -> pArr[i] = R - i
     * 3. 如果i'刚好在L上，i位置的回文区域至少是i~R为半径的区域，需要去判断R往右扩的位置是不是回文
     * 
     * max-1是最长回文串的长度，如果R在往右扩的过程中，使用一个end = R - 1变量记录下来
     * 每个回文串的结束位置，最终得到最大的回文串的最后位置就是(end - 1) / 2，因为end - 1是
     * 处理之后的最长回文串的最后一个位置，(end - 1) / 2就是原始字符串最后一个最长回文串的位置（观察
     * 得到）
     */
    public String longestPalindrome(String s) {
        if (s == null || s.length() == 0) {
            return null;
        }
        return manacher(s);
    }

    public String manacher(String str) {
        if (str.length() == 1) {
            return str;
        }
        // 加特殊字符#
        char strArr[] = manacherStr(str.toCharArray());
        // 处理每一个字符
        int R = -1; // 最右回文边界的下一个位置
        int C = 0; // 最右回文的中心位置
        int pArr[] = new int[strArr.length]; // 存储每个位置的回文半径的数组
        int max = Integer.MIN_VALUE;
        int end = -1;
        // 每一个i位置都去求回文半径
        for (int i = 0; i <= strArr.length - 1; i++) {
            // 求i位置最小不用验的回文半径
            // R > i说明包住了, 情况2.1和2.2 两者取最小，至少有一个回文半径长度； 如果没被包住，自己至少是一个回文，半径就是1(情况1)
            pArr[i] = R > i ? Math.min(pArr[2 * C - i], R - i) : 1;
            // 当前形成的回文区域左、右都不越界，那么尝试一下当前回文的左、右两个字符是否相等
            // 如果相等，说明能够继续扩，更新i位置的回文半径 --> 这里就是情况2.3
            while (i + pArr[i] <= strArr.length - 1 && i - pArr[i] >= 0) { // 不越界
                if (strArr[i + pArr[i]] == strArr[i - pArr[i]]) { // 回文能够继续扩，回文半径++
                    pArr[i]++;
                } else { // 不能扩
                    break;
                }
            }
            // 当前以i为中心，冲到的回文边界，是否能够推高R，如果能则更新R，以及新的回文中心
            if (i + pArr[i] > R) { // 能够将最右回文边界推大
                R = i + pArr[i];
                C = i;
            }
            if(max < pArr[i]) {
                // 记录最大回文串的结束位置
                end = R - 1;
            }
            // 求最大回文半径
            max = Math.max(max, pArr[i]);
        }
        // max - 1是最大回文长度，(end - 1) / 2是回文串结束位置
        char ss[] = str.toCharArray();
        int end2= (end - 1) / 2;
        char[] res = new char[max - 1];
        for (int i = max - 2; i >= 0; i--) {
            res[i] = ss[end2];
            end2--;
        }
        return String.valueOf(res);
    }

    public static char[] manacherStr(char str[]) {
        char res[] = new char[str.length * 2 + 1];
        int index = 0;
        for (int i = 0; i <= res.length - 1; i++) {
            res[i] = (i % 2) == 0 ? '#' : str[index++];
        }
        return res;
    }
}

package class28;

// 14. 最长公共前缀
public class Problem_0014_LongestCommonPrefix {
    /**
     * 编写一个函数来查找字符串数组中的最长公共前缀。如果不存在公共前缀，返回空字符串 ""。
     */
    
    /**
     * 思路:
     * 直接将第一个字符串当成最长的公共前缀，
     * 然后和后面剩下的每一个字符串，比较，找出公共前缀的index，
     * 最后从第一个字符串截取出来就行
     */
    public String longestCommonPrefix(String[] strs) {
        if(strs == null || strs.length == 0) {
            return "";
        }
        // 第一个字符串作为结果
        String result = strs[0];
        int min = result.length();
        char chars[] = result.toCharArray();
        for(int i = 1; i <= strs.length - 1; i++) {
            char tmp[] = strs[i].toCharArray();
            int index = 0;
            // 遍历比较每一个字符, 如果对应的不相等，说明当前比较的两个字符串找到了最长公共前缀
            while(index < chars.length && index < tmp.length) { // 还有字符没有遍历到
                if (chars[index] != tmp[index]) { 
                    break;
                }
                index++;
            }
            // index 到达了最长公共前缀的后一个位置
            if(index == 0) { // 如果是0，表示没有公共前缀
                return "";
            }
            min = Math.min(min, index);
        }
        return result.substring(0, min);
    }
}

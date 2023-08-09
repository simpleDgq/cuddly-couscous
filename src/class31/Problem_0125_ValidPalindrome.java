package class31;

// 125. 验证回文串
public class Problem_0125_ValidPalindrome {
    /**
     * 如果在将所有大写字符转换为小写字符、并移除所有非字母、数字字符之后，短语正着读和反着读都一样。则可以认为该短语是一个 回文串 。
     * 字母和数字都属于字母数字字符。
     * 给你一个字符串 s，如果它是 回文串 ，返回 true ；否则，返回 false 。
     */
    
    /**
     * 思路:
     * 双指针
     * 
     * 搞两个指针，一个指向数组的头，一个指向结尾，如果指向的位置的字符，都是有效的(只有字母或者数字才是有效的)，
     * 判断两个字符是否相等
     * 
     * 英文字母，大小写相差32。 例如 a 和 A相差32
     */
    public boolean isPalindrome(String s) {
        if(s == null || s.length() == 0) {
            return false;
        }
        char strs[] = s.toCharArray();
        int L = 0;
        int R = strs.length - 1;
        // 没有撞上
        while(L < R) {
            // 如果两个字符都是有效的，则判断是否相等
            if(isValid(strs[L]) && isValid(strs[R])) {
                // 不相等，直接返回false
                if(!isEqual(strs[L], strs[R])) {
                    return false;
                }
                L++;
                R--;
            } else {
                // 如果不是有效的，不是有效的那个位置，往下跳
                L += isValid(strs[L]) ? 0 : 1; 
                R -= isValid(strs[R]) ? 0 : 1; 
            }
        }
        return true;
    }
    
    // 判断字符是否是有效的
    // 只有数字和字母有效
    public boolean isValid(char c) {
        return isNumber(c) || isChar(c);
    }
    // 判断是否是数字
    public boolean isNumber(char c) {
        return (c >= '0' && c <= '9');
    }
    // 判断是否是字符
    public boolean isChar(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }
    // 判断是否相等
    public boolean isEqual(char a, char b) {
        // 如果是数字，直接判断就行
        if (isNumber(a) || isNumber(b)) {
            return a == b;
        }
        // 如果是字符
        // 都是大写或者都是小写，直接比较
        // 一个大，一个小，小写a比大写A，大32
        return a == b || (Math.max(a, b) - Math.min(a, b) == 32);
    }
}

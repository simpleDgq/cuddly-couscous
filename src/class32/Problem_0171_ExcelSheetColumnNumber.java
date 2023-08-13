package class32;

//171. Excel 表列序号
public class Problem_0171_ExcelSheetColumnNumber {
    /**
     * 给你一个字符串 columnTitle ，表示 Excel 表格中的列名称。返回 该列名称对应的列序号 。
     * 例如：
     * A -> 1
     * B -> 2
     * C -> 3
     * ...
     * Z -> 26
     * AA -> 27
     * AB -> 28 
     * ...
     */
    
    /**
     * 思路:
     * 伪26进制
     * 
     * 和二进制类似，每一位表示26的多少次方，比如ABC
     * 
     * C表示的是26的0次方，有C个 = 3 -> 3 * 26 ^ 0 
     * B表示的是26的1次方，有B个 = 2 -> 2 * 26
     * A表示的是26的2次方，有A个 = 1 -> 1 * 26 ^ 2
     * 
     * 表示的数，相加得到 3 * 26 ^ 0 + 2 * 26 + 1 * 26 ^ 2
     */
    public int titleToNumber(String columnTitle) {
        if(columnTitle == null || columnTitle.length() == 0) {
            return 0;
        }
        char chars[] = columnTitle.toCharArray();
        int N = chars.length;
        int ans = 0;
        int temp = 1; // 26的0次方，26的1次方，没求一位，加一个次方
        for(int i = N - 1; i >= 0; i--) {
            ans += (chars[i] - 'A' + 1) * temp;
            temp *= 26;
        }
        return ans;
    }
}

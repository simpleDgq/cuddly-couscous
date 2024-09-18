package class28;

import java.util.ArrayList;
import java.util.List;

// 17. 电话号码的字母组合
public class Problem_0017_LetterCombinationsOfAPhoneNumber {
    /**
     * 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。答案可以按 任意顺序 返回。
     *   给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。
     */
    
    /**
     * 思路:
     * 深度优先遍历
     * 
     * 搞一张表，将按键代表的字符都存放起来
     * 遍历每一个数字，取出它代表的所有的字符，
     * 每个字符都加入到path中一次，然后，去取下一个数字代表的字符，
     * 递归去搞
     * 
     */
    public static char[][] phone = { 
            { 'a', 'b', 'c' }, // 2    0
            { 'd', 'e', 'f' }, // 3    1
            { 'g', 'h', 'i' }, // 4    2
            { 'j', 'k', 'l' }, // 5    3
            { 'm', 'n', 'o' }, // 6    
            { 'p', 'q', 'r', 's' }, // 7 
            { 't', 'u', 'v' },   // 8
            { 'w', 'x', 'y', 'z' }, // 9
    };
    public List<String> letterCombinations(String digits) {
        ArrayList<String> ans = new ArrayList<String>();
        if(digits == null || digits.length() == 0) {
            return ans;
        }
        char str[] = digits.toCharArray();
        char path[] = new char[str.length];
        process(str, 0, path, ans);
        return ans;
    }
    // 当前来到了index位置，str是要处理的数字字符串，
    // 已经走过的路径放在了path里面，你给我把取到的结果放到ans里面去
    public void process(char[] str, int index, char[] path, List<String> ans) {
        if(index == str.length) { // 如果已经遍历到了数字最后，表示没有字符了，搜集答案
           // ans.add(path.toString()); // 不能用这种，这种会全是内存地址
            ans.add(String.valueOf(path));
        } else {
            // 取出index位置对应的字符
            // str[index] - '2' ，比如str[index]是'2'，减去之后就是0，取出的就是phone[0]
            // 也就是2表示的字符
            char chars[] = phone[str[index] - '2'];
            // 每一个字符打头去搞下一个index位置的数字。深度优先遍历
            for(char cur : chars) {
                // 当前字符加到path里面
                path[index] = cur; // path的长度是和ste相等的，选了一个数字，相当于已经选了这个数字代表的一个字母。所以这里是index
                // 去搞下一个位置
                process(str, index + 1, path, ans);
            }
        }
    }
}

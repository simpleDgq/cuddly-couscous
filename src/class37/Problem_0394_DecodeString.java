package class37;

// 394. 字符串解码
// https://leetcode.cn/problems/decode-string/description/
public class Problem_0394_DecodeString {
    /**
     * 给定一个经过编码的字符串，返回它解码后的字符串。
     * 编码规则为: k[encoded_string]，表示其中方括号内部的 encoded_string 正好重复 k 次。注意 k 保证为正整数。
     * 
     * 可以认为输入字符串总是有效的；输入字符串中没有额外的空格，且输入的方括号总是符合格式要求的。
     * 此外，你可以认为原始数据不包含数字，所有的数字只表示重复的次数 k ，例如不会出现像 3a 或 2[4] 的输入。
     * 
     * 示例 1：
     * 输入：s = "3[a]2[bc]"
     * 输出："aaabcbc"
     * 
     * 示例 2：
     * 输入：s = "3[a2[c]]"
     * 输出："accaccacc"
     */
    
    /**
     * 思路:
     * 
     * 递归
     * 定义一个函数f(str, i), 从str的i位置开始往后转换，遇到右括号]或者字符串的结尾就停止
     * 停的时候，从i到停的位置这段返回，并且返回停的位置
     * 
     * 返回值有两个: 
     * 1. 结果字符串
     * 2. 处理到的位置
     * 
     * 例子: 
     * 21[b5[cb]4[t]]
     * 01234567891123
     * 
     * 从0位置开始转换，调f(0),f(0)里面ans记录转换的结果，cur搜集数字
     * 遇到2，记录ans=""，cur=2，遇到1，ans=""，cur=21
     * 遇到左括号，知道是21个什么，我才不管怎么搞，直接调用f(3)去搞，f(3)给我返回结果
     * 然后我就知道了
     * 
     * 同理，f(3)遇到b，搜集ans=b，cur = 0，遇到5，ans=b,cur=5
     * 又遇到左括号了，我知道应该是5个重复的什么，我才不管怎么搞，直接调用f(6)去搞，f(6)给我返回结果
     * 然后我就知道了
     * 
     * 同理，f(6)遇到c，搜集ans=c，cur = 0，遇到b，搜集ans=cb，cur = 0
     * 遇到右括号了，f(6)需要返回，f(6)返回cb给f(3)，并且返回当前处理到到位置是8
     * f(3)知道是5个重复的cb，就会生成好，加到它自己的ans里面去，而且指定当前已经处理到的位置，
     * 就能继续往后处理  --> 返回处理到的位置的原因，能让递归继续往下处理剩余的字符串
     */
    public String decodeString(String s) {
        if(s== null || s.length() == 0) {
            return null;
        }
        char chars[] = s.toCharArray();
        return process(chars, 0).ans;
    }
    
    public class Info {
        String ans;
        int cur;
        public Info(String ans, int cur) {
            this.ans = ans;
            this.cur = cur;
        }
    }
    // 从str的i位置往后开始转换，遇到右括号或者达到字符串的结尾就结束，
    // 返回info
    public Info process(char str[], int i) {
        int cur = 0;
        StringBuilder ans = new StringBuilder();
        while(i < str.length && str[i] != ']') { // 没有到达字符串的结尾且遇到的不是右括号
            // 如果遇到的是数字，则更新cur
            // 如果遇到的是字母，则加入到ans
            // 如果是左括号，调用process(i + 1)去搞
            if(str[i] >= '0' && str[i] <= '9') { // 如果遇到了数字
                cur = cur * 10 + (str[i] - '0');
                i++;
            } else if((str[i] >= 'a' && str[i] <= 'z') || str[i] >= 'A' && str[i] <= 'Z') { // 如果遇到的是字母
                ans.append(str[i]);
                i++;
            } else { // 如果是左括号
               Info info = process(str, i + 1);
               // i+1位置出发的答案返回之后，继续上一次的递归，返回的ans生产好字符串，加入到上一次递归的ans里面去
               ans.append(timesString(cur, info.ans));
               // 然后从已经处理到的位置的下一个位置继续搞
               i = info.cur + 1;
               // 新的位置开始了，以前的cur已经被使用了，恢复cur
               cur = 0;
            }
        }
        // 如果达到了字符串的结尾，或者遇到了右括号，直接返回搜集到的ans，以及当前处理到的位置
        return new Info(ans.toString(), i);  
    }
    
    public String timesString(int count, String ans) {
        StringBuilder res = new StringBuilder();
        for(int i = 0; i <= count - 1; i++) {
            res.append(ans);
        }
        return res.toString();
    }
}

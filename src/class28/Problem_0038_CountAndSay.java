package class28;

// 38. 外观数列
public class Problem_0038_CountAndSay {
    /**
     * 给定一个正整数 n ，输出外观数列的第 n 项。
     * 
     * 外观数列」是一个整数序列，从数字 1 开始，序列中的每一项都是对前一项的描述。
     * 你可以将其视作是由递归公式定义的数字字符串序列：
     *  countAndSay(1) = "1"
     *  countAndSay(n) 是对 countAndSay(n-1) 的描述，然后转换成另一个数字字符串。

        前五项如下：
        
        1.     1
        2.     11
        3.     21
        4.     1211
        5.     111221
        第一项是数字 1 
        描述前一项，这个数是 1 即 “ 一 个 1 ”，记作 "11"
        描述前一项，这个数是 11 即 “ 二 个 1 ” ，记作 "21"
        描述前一项，这个数是 21 即 “ 一 个 2 + 一 个 1 ” ，记作 "1211"
        描述前一项，这个数是 1211 即 “ 一 个 1 + 一 个 2 + 二 个 1 ” ，记作 "111221"
        
        要 描述 一个数字字符串，首先要将字符串分割为 最小 数量的组，每个组都由连续的最多 
        相同字符 组成。然后对于每个组，先描述字符的数量，然后描述字符，形成一个描述组。
        要将描述转换为数字字符串，先将每组中的字符数量用数字替换，再将所有描述组连接起来。
     */
    
    /**
     * 思路: 遍历数组，记录相等的字符出现的次数，统计完，将times和字符都加入到结果集中
     * 继续
     */ 
    public String countAndSay(int n) {
        if(n < 0) {
            return "";
        }
        if(n == 1) {
            return "1";
        }
        StringBuilder ans = new StringBuilder();
        // 要搞定第n项，首先必须搞定第n-1项，递归搞定
        char chars[] = countAndSay(n - 1).toCharArray();
        // 两个指针，一个记录相等的字符出现的次数，一个用来记录遍历到哪了
        int times = 1; // 第0号字符，一定出现一次，直接从第1号字符开始搞
        for(int i = 1; i <= chars.length - 1; i++) {
            // 如果当前指向的字符和前一个字符相等，则times++
            if (chars[i] == chars[i - 1]) {
                times++;
            } else {
                // 不相等的时候，要记录答案
                // 先加次数，再加对应的字符
                ans.append(times);
                ans.append(chars[i - 1]); // i已经指向不相等的字符了，得记录上一次相等的字符
                times = 1; // 重置times，用来统计下一个字符出现的次数
            }
        }
        // 不要忘了将最后数组结尾的字符加进去
        ans.append(times);
        ans.append(chars[chars.length - 1]);
        
        return ans.toString();
    }
}

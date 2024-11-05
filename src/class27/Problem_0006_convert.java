package class27;

// 6. Z 字形变换
public class Problem_0006_convert {
    /**
     * 思路：从上往下一行行去填
     * StringBuilder数组放好每一行，最后将所有的行取出来拼凑成字符串就行
     * 
     * 搞一个变量，来改变方向和下标
     * 如果当前方向是向下（goingDown == true），并且你到达了最后一行（currentRow == numRows -
     * 1），你需要改为向上移动（将 goingDown 设置为 false）。
     * 如果当前方向是向上（goingDown == false），并且你到达了第一行（currentRow == 0），你需要改为向下移动（将
     * goingDown 设置为 true）。
     */
    public String convert(String s, int numRows) {
        if(numRows <= 1 || s == null || s.length() == 0 || numRows >= s.length()) {
            return s; // 行数小于等于1或者大于整个字符串的长度，答案都是原字符串，要么一直向左(numRows <=1)，要么一直向下(numRows >= s.length)
        }
        StringBuilder sbs[] = new StringBuilder[numRows]; // 每一行用一个StringBuilder来存储
        for(int i = 0; i <= numRows - 1; i++) {
            sbs[i] = new StringBuilder(); // 初始化为空
        }

        int curRow = 0; // 当前填到的行
        boolean goingDown = false; // 当前的方向。默认向上

        // s中的每一个字符都去填
        for(char c : s.toCharArray()) {
            sbs[curRow].append(c); // 将字符加入对应的行
            if(curRow == 0 || curRow == numRows - 1) { // 如果到达了第0行或者最后一行，方向要变
                goingDown = !goingDown;
            }
            curRow += goingDown ? 1 : -1; // 如果是向下的，那么行数要加1，向上行数就要减1
        }
        // 每一行取出来，合并最后的结果
        StringBuilder res = new StringBuilder();
        for(StringBuilder sb : sbs) {
            res.append(sb.toString());
        }
        return res.toString();
    }
}

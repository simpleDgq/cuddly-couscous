package class08;

import java.util.LinkedList;

//本题测试链接 : https://leetcode.cn/problems/basic-calculator-iii/
public class Code01_ExpressionCompute {
    /*
     * 计算str表达式结果
     * 
     * 给定一个字符串str，str表示一个公式，公式里可能有整数、加减乘除符号和左右 括号，返回公式的计算结果。
        【举例】
        
        str="48*((70-65)-43)+8*1"，返回-1816。
        str="3+1*4"，返回7。
        str="3+(1*4)"，返回7。
        【说明】
        1.可以认为给定的字符串一定是正确的公式，即不需要对str做公式有效性检查。 2.如果是负数，就需要用括号括起来，比如"4* (-3)"。但如果负数作为公式的开头 或括号部分的开头，则可以没有括号，比如"-3* 4"和"(-3* 4)"都是合法的。
        3.不用考虑计算过程中会发生溢出的情况。
     */
    
    /**
     * 模板，所有的括号嵌套问题都可以这么改
     * 一定要背住
     */
    public static int calculate(String str) {
        return process(str.toCharArray(), 0)[0];
    }
    // 当前来到i位置，
    // 从str[i...]往下算，遇到字符串终止位置或者右括号，就停止
    // 给我返回:
    // 0) 负责的这一段的结果是多少
    // 1) 负责的这一段计算到了哪个位置
    public static int[] process(char str[], int i) {
        int bra[] = new int[2];
        int cur = 0;
        LinkedList<String> que = new LinkedList<String>(); // 用队列和栈都行
        // 既不能遇到遇到右括号，又不能到字符串的结尾
        while(i < str.length && str[i] != ')') {
            if(str[i] >= '0' && str[i] <= '9') { // 遇到的是数字。就收集
                cur = cur * 10 + (str[i++] - '0');
            } else if(str[i] != '(') { // 遇到的是运算符
                // 将当前值加入到队列中，同时会处理*和/
                addNum(que, cur);
                // 将当前遇到的运算符放入队列中
                que.addLast(String.valueOf(str[i++]));
                cur = 0;
            } else { // 遇到的是左括号
                bra = process(str, i + 1); // 扔递归，计算下一个区间，两个值抓住
                cur = bra[0]; // 返回给上一层的值
                i = bra[1] + 1; // 子过程算到了哪儿，下一个位置继续
            }
        }
        // 到字符串结尾了，最后一个收集的数要放入容器
        addNum(que, cur);
        // 最后que中只有+ - 和数组，计算答案，返回
        return new int[] { getNum(que), i};
    }
    
    // 将一个数加入到容器中，同时处理* 和 /
    public static void addNum(LinkedList<String> que, int num) {
        if(!que.isEmpty()) { // 如果栈不为空
            String cur = que.pollLast(); // 弹出栈顶
            if(cur.equals("*") || cur.equals("/") ) { // 栈顶元素是* 或者 /
                int value = Integer.valueOf(que.pollLast()); // 弹出元素和num计算答案
                num = cur.equals("*") ? (value * num) : (value / num); // 更新num
            } else {
              // 加回栈顶元素
              que.addLast(String.valueOf(cur));
            }
        }
        // 将最新的num加入栈顶
        que.addLast(String.valueOf(num));
    }
    
    // 最后栈中只有+ - 和数字，弹出所有元素计算答案
    public static int getNum(LinkedList<String> que) {
        int res = 0;
        String cur = null;
        int num = 0;
        boolean add = true;
        while(!que.isEmpty()) {
            cur = que.pollFirst();
            if(cur.equals("+")) {
                add = true;
            } else if(cur.equals("-")) {
                add = false;
            } else {
                num = Integer.valueOf(cur);
                res += add ? num : -num;
            }
        }
        return res;
    }
    
    public static void main(String[] args) {
        String str = "12+3*(12+3)+10/(12-7)";
        System.out.println(calculate(str));
    }
}

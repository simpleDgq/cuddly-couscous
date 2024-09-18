package class28;

import java.util.Stack;

// 20. 有效的括号
public class Problem_0020_ValidParentheses {
    /**
     * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
     * 有效字符串需满足：
     * 左括号必须用相同类型的右括号闭合.
     * 左括号必须以正确的顺序闭合。
     * 每个右括号都有一个对应的相同类型的左括号。
     */
    
    /**
     * 思路:
     * 1. 搞一个栈
     * 2. 如果遍历到的字符是左括号(小，中，大)，入栈 ==> 入栈的时候，将右括号入栈，方便后面弹出的时候进行比较 。 
     * 压入左括号也是可以的，只不过后面比较的时候，你弹出的是左括号，而当前遍历到的是右括号，不好判断它们是不是匹配。可能得写一个函数来搞。
     * 直接压入的是对应的右括号，就可以直接用==来判断了，非常方便。
     * 3. 如果遍历到的字符是右括号，弹出栈顶元素和当前字符比较，如果不是右括号，说明不是有效字符串
     *    1)如果栈为空了，没有元素弹出了，现在还是遇到了右括号，说明不是有效的字符串。例如:()())
     *    2)遍历完字符串之后，最终栈也要是空，如果不是的话，也不是有效的字符串。例如: ()(
     * 
     * 优化： 用数组代替栈
     * 
     */
    public boolean isValid(String s) {
        if(s == null || s.length() == 0) {
            return false;
        }
        char str[] = s.toCharArray();
        Stack<Character> stack = new Stack<Character>();
        for(int i = 0; i <= str.length - 1; i++) {
            char ch = str[i];
            // 如果是左括号，压入右括号
            if(ch == '(' || ch == '['  || ch == '{' ) {
                stack.add(ch == '(' ? ')' : ch == '[' ? ']' : '}');
            } else {
                // 如果栈已经是空了，不是有效字符串
                if(stack.isEmpty()) {
                   return false; 
                }
                char top = stack.pop();
                if(top != ch) {
                    return false;
                }
            }
        }
        // 如果最终stack不是空，也不是有效字符串，例如()(
        return stack.isEmpty();
    }
    
    // 优化，使用数组代替上面的栈，优化常数时间
    public boolean isValid2(String s) {
        if(s == null || s.length() == 0) {
            return false;
        }
        char str[] = s.toCharArray();
        int N = str.length;
        char stack[] = new char[N];
        int size = 0;
        for(int i = 0; i <= N - 1; i++) {
            char ch = str[i];
            // 如果是左括号，压入右括号
            if(ch == '(' || ch == '['  || ch == '{' ) {
                stack[size++] = ch == '(' ? ')' : ch == '[' ? ']' : '}';
            } else {
                // 如果栈已经是空了，不是有效字符串
                if(size == 0) {
                   return false; 
                }
                char top = stack[--size];
                if(top != ch) {
                    return false;
                }
            }
        }
        // 如果最终stack不是空，也不是有效字符串，例如()(
        return size == 0;
    }
}

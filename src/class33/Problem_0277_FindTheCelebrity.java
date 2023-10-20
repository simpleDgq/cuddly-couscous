package class33;

//277 搜寻名人
public class Problem_0277_FindTheCelebrity {
    /**
     * 假设你是一个专业的狗仔，参加了一个 n 人派对，其中每个人被从 0 到 n - 1 标号。
     * 在这个派对人群当中可能存在一位 “名人”。
     * 所谓 “名人” 的定义是：其他所有 n - 1 个人都认识他/她，而他/她并不认识其他任何人。
     * 
     * 现在你想要确认这个 “名人” 是谁，或者确定这里没有 “名人”。
     * 而你唯一能做的就是问诸如 “A 你好呀，请问你认不认识 B呀？” 的问题，以确定 A 是否认识 B。
     * 你需要在（渐近意义上）尽可能少的问题内来确定这位 “名人” 是谁（或者确定这里没有 “名人”）。
     * 
     * 在本题中，你可以使用辅助函数 bool knows(a, b) 获取到 A 是否认识 B。请你来实现一个函数 int findCelebrity(n)。
     * 派对最多只会有一个 “名人” 参加。
     * 
     * 若 “名人” 存在，请返回他/她的编号；若 “名人” 不存在，请返回 -1。
     */
    
    /**
     * 思路: 主要在于名人的定义 --> 名人所有人都认识它，它不认识所有人
     * 
     * 三个for:
     * 1.确定一个候选
     * 2.判断是不是所有人都认识候选
     * 3.判断是不是候选不认识所有人
     */
    
    // 提交时不要提交这个函数，因为默认系统会给你这个函数
    // x是否认识i
    public static boolean knows(int x, int i) {
        return true;
    }
    
    public int findCelebrity(int n) {
        int candidate = 0; // 假设第0位是名人
        for(int i = 1; i <= n - 1; i++) {
            if(knows(candidate, i)) {
                candidate = i; // 如果当前的candiate认识i，那么当前的候选一定不是名人，因为名人不认识所有人，候选变成下一个
                             //  如果当前的candiate不认识i，直接跳过，候选不变，继续
            }
        }
        // 遍历一遍找到了唯一的候选，题目告诉我们名人只有一个
        // 判断是不是所有人都认识它
//        for(int i = 0; i <= n -1; i++) {
//            if(!knows(i, candidate)) {
//                return -1; // 如果有一个不认识候选，就没有名人，返回-1
//            }
//        }
//        // 判断是不是它不认识所有人
//        for(int i = 0; i <= n -1; i++) {
//            if(knows(candidate, i)) {
//                return -1; // 如果候选认识任何一个人，就没有名人，返回-1
//            }
//        }
        
        /**
         * 后两个for循环可以合并
         */
        for(int i = 0; i <= n -1; i++) {
            if(!knows(i, candidate) || knows(candidate, i)) {
                return -1;
            }
        }
        
        return candidate;
    }
}

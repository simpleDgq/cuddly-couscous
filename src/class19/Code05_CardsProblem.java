package class19;

import java.util.LinkedList;

public class Code05_CardsProblem {
    /**
     * 一张扑克有3个属性，每种属性有3种值（A、B、C）
     * 比如"AAA"，第一个属性值A，第二个属性值A，第三个属性值A
     * 比如"BCA"，第一个属性值B，第二个属性值C，第三个属性值A
     * 给定一个字符串类型的数组cards[]，每一个字符串代表一张扑克
     * 从中挑选三张扑克，一个属性达标的条件是：这个属性在三张扑克中全一样，或全不一样
     * 挑选的三张扑克达标的要求是：每种属性都满足上面的条件
     * 比如："ABC"、"CBC"、"BBC"
     * 第一张第一个属性为"A"、第二张第一个属性为"C"、第三张第一个属性为"B"，全不一样
     * 第一张第二个属性为"B"、第二张第二个属性为"B"、第三张第二个属性为"B"，全一样
     * 第一张第三个属性为"C"、第二张第三个属性为"C"、第三张第三个属性为"C"，全一样
     * 每种属性都满足在三张扑克中全一样，或全不一样，所以这三张扑克达标
     * 返回在cards[]中任意挑选三张扑克，达标的方法数
     * 
     * 题意理解:
     * 扑克牌张数可能要到百万张,每种扑克牌只有三个属性,只会是A, B, C
     *   100万张左右，随意抽3张，达标的可能有多少种。
     */
    
    /**
     * 思路:
     * 100万张牌, 
     *   一张牌有3种属性, 每个属性只有A, B, C三种可能性, 一共有27种牌面
     *   最重要的技巧就是根据数据量猜解法
     *       我一看100万张牌，我就知道用牌的角度来想是不对的，
     *       必须从牌面着手，因为只有27种牌面
     *       
     *       
     *  使用3进制, 将牌面对应成一个数值
     *       ABC对应成一个值
     *       A对应0, B对应1, C对应2
     *       ABC来说, 
     *       第一位3^0上是2
     *       第二位3^1上是1
     *       第三位3^2上是0
     *       2* 3^0 + 1* 3^1 + 0 * 3^2 =  5
     *       
     *  收集AAA有几张
     *   AAB有几张
     *   AAC有几张
     *   比如AAA 100张, 这100张里随意挑3张都达标 排列组合C 100 3
     *   比如AAB 200张, 这200张里随意挑3张都达标 C 200 3
     *   比如AAC 150张, 这150张里随意挑3张都达标 C 150 3
     *   因为3张都是一样的, 符合规定
     *   
     *   27张牌面列一下, 不要张数, 就想牌面
     * 搞出所有可能的牌面, 两种情况：
     *   1)选两张AAA, 第三张必须AAA, 也就是排列组合的情况
     *   2)一共27个牌面, 必须选3张不同牌面的可能性有多少种
     *      例子：如果必选3个牌面, 如果它达标的话, 那这3个排名的组合数有多少? ABC:100张 BAB:50张 CCA:60张
     *      总共100 * 50 * 60中可能
     *   最暴力的就是27个牌面，有多少不同的三个牌面你都枚举一下
     *   最后方案就是几个数相乘就行了
     *     一共27个牌面, 每个牌面要跟不要, 但是一旦超过3种, 就停止, 
     *     到最后正好3个排名, 如果发现每一位都一样或者每一位都不一样
     *     把它们的数量取出来一乘就搞定了
     */
    public static int ways(String cards[]) {
        // 统计每个牌面有多少张牌
        int counts[] = new int[27];// 总共27种牌面
        for(int i = 0; i <= cards.length - 1; i++) {
            String card = cards[i];
            char[] cardChars = card.toCharArray();
            // 定义A是1 B是2, C是3 --> ABC组成的数就是1 * 3^2 + 2 * 3^1 + 3 * 3 ^ 0 = 9 + 6 + 3 = 18
            // 采用下面的方式来统计每个牌面有多少张牌
            counts[9 * (cardChars[0] - 'A') + 3 * (cardChars[1] - 'A') + 1 * (cardChars[2] - 'A')]++;
        }
        // 1) 三张都是一样的牌面的情况，牌面一样的牌，任意取3张，都是达标的，就是排列组合，单独算
        int ways = 0;
        for(int i = 0; i < 27; i++) {
            int count = counts[i];
            if(count > 2) { // 相同的牌面的牌的数量，大于两张，就能任意取3张
                ways += count == 3 ? 1: (count * (count - 1) * (count -2)) / 6;
            }
        }
        // 2) 三张都是不同牌面的牌的情况
        // 27种不同的牌面，取每一种牌面当做第1张，剩下的有递归去搞
        LinkedList<Integer> path = new LinkedList<Integer>();
        for(int i = 0; i < 27; i++) {
            if(counts[i] != 0) { // 如果牌面有牌，才能拿
                path.addLast(i); // 第1个牌面拿了i，后序从i往后拿
                ways += process(counts, i, path); // 递归去搞剩下的第2张，第3张
                path.removeLast(); // 恢复现场
            }
        }
        return ways;
    }
    // 之前的牌面拿了一些放在path中，pre是之前拿的牌面中最大的，
    // 之后的牌面都从pre+1开始往后拿，不拿pre之前的，防止重复，
    // 给我返回形成有效牌面的方法数
    public static int process(int counts[], int pre, LinkedList<Integer> path) {
        if(path.size() == 3) { // 已经有3个不同牌面了，求达标牌面的方法数
            return getWays(counts, path);
        }
        int ways = 0;
        // 没有够3张牌, 从pre+1开始，往后的牌面开始拿，防止重复拿
        for(int i = pre + 1; i < 27; i++) {
            if(counts[i] != 0) { // 如果牌面有牌，才能拿
                path.addLast(i);
                ways += process(counts, i, path); // 求后序有多少种情况
                path.removeLast(); // 删除最后加的元素，方便后面搞新的分支
            }
        }
        return ways;
    }
    // 判断path中的三个不同的牌面是不是达标的，达标的话，返回达标的方法数
    public static int getWays(int counts[], LinkedList<Integer> path) {
        int v1 = path.get(0); // 第1张牌面
        int v2 = path.get(1); // 第2张牌面
        int v3 = path.get(2); // 第3张牌面
        // 取出牌面的每一位，进行对比
        for(int i = 9; i >= 1; i /= 3) {
            int cur1 = v1 / i;// 分别取出第1张排的第1位，第二位，第3位
            int cur2 = v2 / i;// 分别取出第2张排的第1位，第二位，第3位
            int cur3 = v3 / i;// 分别取出第3张排的第1位，第二位，第3位
            // 去掉最高位
            v1 %= i;
            v2 %= i;
            v3 %= i;
            // 判断是否是达标的牌面。对应的位全都不相等，或者全相等，每次比较的是同一位
            if((cur1 != cur2 && cur2 != cur3 && cur1 != cur3) || (cur1 == cur2 && cur2 == cur3)) {
                continue; // 达标，则continue，判断下一位是否也达标
            }
            return 0; // 如果有不达标的位，说明这一组path不是答案，返回0
        }
        // 一直没返回，说明path里的三个牌面是达标的。取出path里面的牌面的张数，相乘，就是答案
        v1 = path.get(0); // 第1张牌面
        v2 = path.get(1); // 第2张牌面
        v3 = path.get(2); // 第3张牌面
        return counts[v1] * counts[v2] * counts[v3];
    }
}

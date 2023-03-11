package class25;

import java.util.HashMap;

//本题测试链接: https://leetcode.cn/problems/max-points-on-a-line/
public class Code03_MaxPointsOnALine {
    /**
     * 149. 直线上最多的点数
     * 给你一个数组 points ，其中 points[i] = [xi, yi] 表示 X-Y 平面上的一个点。求最多有多少个点在同一条直线上。
     */
    /**
     * 
     * 思路:
     * 1. 在一条线上的点，斜率相同
     * 
     * 以每一个点作为开始节点，都去求斜率，放在map中
     * 假设必须以a出发，斜率是1/3的点有2个，斜率1/5的右3个，共点的有3个，共水平线的有4个，共竖线的右8个
     * 那么以a出发的线，在这条线上的点的数量最多就是，共横线的，共竖线的，普通的斜率 三者求最大值，然后加上共点的数量。
     * 
     * 大流程:
     * a,b,c,d,e,f这些点:
     * 大流程, 来到一个点时候没有必要看前面的
     * 所以我们流程就变成了来到a的时候后面的节点搞一下, 来到b的时候后面的节点搞一下，
     *   到c的时候后面的节点搞一下，不用往前重复求了，这就是我们的大流程.
     *
     *
     *怎么表示斜率, 两个点X, Y
        1) 正常的斜率
        2) 共点, 在同样一个位置, 要算两份（因为求的是多少个点在这条直线上）
        3) 共水平线, 斜率是0
        4) 共竖线
         
        * 最终答案是共横线的共竖线的以及表里面所有的值，你给我求个max。再把共点的加上就行了
        * 
        * 数组中没一个点作为出发点，都去求max，就是答案
        * 
        * 怎么表示斜率：
        * 1. 用double，存在精度丢失的问题。 999999.00000000001 和999999.00000000000不相等，但是double由于精度丢失，可能想等。
        * 2. 将分数约分成最简形式 比例10/30 变成1/3 , 然后存储成字符串"1_3"  --> 分子分母分别除以最大公约数得到最简形式
        * 3. 用map， key是分子, value也是map，这个map中
        *                  key是分母
        *                  value是个数
        *                            表示的是以某个数为分子，然后以某个数为分母的时候，分别有多少个
        * 例如{    2，
        * {5 3} 
        * {6, 2}
                    } 表示的是以2位分子的时候，以5为分母，有3个;   是以2位分子的时候，以6为分母，有2个
        * 
     */
    // 最大公约数(背)
    public static int gcd(int a, int b) {
        return b == 0 ? a: gcd(b, a % b);
    }
    public static int maxPoints(int[][] points) {
        if(points == null) {
            return 0;
        }
        if(points.length <= 2) {
            return points.length;
        }
        int N = points.length;
        // hashMap存放分数的最简形式
        // key 分子
        // value 又是map : key是分母，value是个数
        // 例子
        // key = 3
        // value = {7 , 10}  -> 斜率为3/7的点 有10个
        //         {5,  15}  -> 斜率为3/5的点 有15个
        HashMap<Integer, HashMap<Integer, Integer>> map = new HashMap<Integer, HashMap<Integer, Integer>>();
        int result = 0; // 记录最终的结果
        for(int i = 0; i <= N - 1; i++) {
            int samePoints = 1; // 记录以i号点出发，后面与它相同的点的个数(从1开始，出发的点自己本身就是一个点，应该加到答案中，或者这里不加，设为0，在返回的时候result+1)
            int sameX = 0; // 记录以i号点出发，后面与它共横线的点的个数
            int sameY = 0; // 记录以i号点出发，后面与它共竖线的点的个数
            int line = 0; // 记录以i号点出发，后面与它构成普通斜率的点的个数，的最大值
            for(int j = i + 1; j <= N - 1; j++) { // i号点和j号点，斜率关系
                int x = points[j][0] - points[i][0];
                int y = points[j][1] - points[i][1];
                if(x == 0 && y == 0) { //共点
                    samePoints++;
                } else if(y == 0) { //共竖线
                    sameY++;
                } else if(x == 0) {//共横线
                    sameX++;
                } else { //普通斜率
                    // 求最大公约数,将分数化为最简形式
                    // x =-13, y =26, gcd=-13 1 -2
                    // x =-16, y =32, gcd=-16 1,-2
                    int gcd = gcd(x, y);
                    x /= gcd;
                    y /= gcd;
                   
                    if(!map.containsKey(x)) {  // 如果分子不在map里面，创建一个
                        // 分子x，分母y，个数1，new一个新的map放入x位置
                        HashMap<Integer, Integer> cur = new HashMap<Integer, Integer>();
                        cur.put(y, 1);
                        map.put(x, cur);
                    } else if(!map.get(x).containsKey(y)) { // 分子在，分母不在
                        // get出x位置的HashMap，新放入一对(y,1)
                        map.get(x).put(y, 1);
                        // 不能直接map.put(x, cur); 这样会覆盖key=x对应的hashMap
                        // 例如原先就存在{1={1, -2}}，如果直接put cur，假设cur是{2, 2}
                        // put之后map就变成{1={2, 2}}了，原先的子map直接被覆盖掉
                        // 应该是取出key对应的原先的子map，放入一对新的键值对
                    }else { // 分子在，分母也在
                        // 取出原来的加1
                        map.get(x).put(y, map.get(x).get(y) + 1);
                    }
                    // 所有与当前点构成普通斜率的点的个数，的最大值
                    line = Math.max(line, map.get(x).get(y));
                }
            }
            // (共横线，共竖线，普通斜率最大值) 三者取最大值 + 共点数
            result = Math.max(result, Math.max(line, Math.max(sameX, sameY)) + samePoints);
            // 继续以下一个点作为出发节点之前，map要清空
            map.clear();
        }
        return result;
    }
}

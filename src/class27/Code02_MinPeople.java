package class27;

import java.util.Arrays;

public class Code02_MinPeople {
    /**
     * //   题目二：
        //  企鹅厂每年都会发文化衫，文化衫有很多种，厂庆的时候，企鹅们都需要穿文化衫来拍照
        //  一次采访中，记者随机遇到的企鹅，企鹅会告诉记者还有多少企鹅跟他穿一种文化衫
        //  我们将这些回答放在 answers 数组里，返回鹅厂中企鹅的最少数量。
        //  输入: answers = [1]    输出：2
        //  输入: answers = [1, 1, 2]    输出：5
        //  Leetcode题目：https://leetcode.com/problems/rabbits-in-forest/
     */
    
    /**
     * 思路:
     * 
     * 如果穿a文化衫的说还有7个和我相同
     * 穿b文化衫的说还要9个和我相同，那么a和b一定不一样
     * 
     * 先将数组排序，然后自我消化。
     * 
     * 例子[1,1,1,1, 2,2, 3,3,3,3,3,3,3,3, 4,4,4, 5,5,5,5,5,5,5,5,5]
     *  0位置的1说还要一人和我相同，
     *  1位置的1说还要一人和我相同，
     *  2位置的1说还要一人和我相同，
     *  3位置的1说还要一人和我相同，
     *  
     *  0和1位置的1自我消化，2人
     *  2和3位置的1自我消化，2人
     *  总共4人
     *  
     *  4位置的2说还要2人和我相同，
     *  4位置的2说还要2人和我相同，
     *  然后后面没有2了，说明没有搜集全
     *  至少穿着和2位置一样颜色的人有3个，这样其它人说还有2人和我一样，才是正确的
     *  
     *  同理:
     *  3 自我消化，4个一组，每组4人，有2组，2*4 = 8人
     *  4自我消化，5个一组，没有收集全，至少需要5人
     *  5自我消化，6个一组，每组6人，有两组，2*6 = 12
     *  
     *  所以至少有4 + 3 + 8 + 5 + 12
     *  
     *  规律: 如果当前数是x，有c个，有几组? c/(x + 1) 向上取整 
     *  a/b 怎么向上取整？ (a + (b - 1)) / b
     *  代入:  c/(x + 1) 向上取整 = (c + x) / (x + 1) 组
     *  
     *  每组多少人？ x + 1
     *  所以对于一个x来说，总共的人数就是((c + x) / (x + 1)) * (x + 1)
     *  
     *  不能约分母:
     *  3,3,3,3,3,3,3  x = 3 c = 7
     *  两组，每组4人总共8人。如果约掉分母，就是c+x = 7 + 3 = 10，明显不对
     *  
     *  代码思路:
     *  先将数组排序
     *  x从arr[0]开始，c从1开始，
     *  如果后面的数和x相等，则c++，说明是同一个组
     *  如果不相等，说明是新的组，结算上一组，同时将x赋值为新组的值arr[i],c=1
     *  注意退出for的时候，最后一组没有结算，需要结算。
     */
    public int numRabbits(int[] answers) {
        if(answers == null || answers.length == 0) {
            return 0;
        }
        // 先排序
        Arrays.sort(answers);
        int x = answers[0];
        int c = 1;
        int ans = 0;
        for(int i = 1; i <= answers.length - 1; i++) {
            if(x != answers[i]) {
                // 结算上一组的答案
                ans += ((c + x) / (x + 1)) * (x + 1);
                c = 1;
                x = answers[i];
            } else { // 同一组
                c++;
            }
        }
        // 最后一组还没有结算，需要结算
        ans += ((c + x) / (x + 1)) * (x + 1);
        return ans;
    }
}

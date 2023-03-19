package class27;

import java.util.Arrays;

public class Code01_PickBands {
    /**
     * 
    // 每一个项目都有三个数，[a,b,c]表示这个项目a和b乐队参演，花费为c
    // 每一个乐队可能在多个项目里都出现了，但是只能挑一次
    // nums是可以挑选的项目数量，所以一定会有nums*2只乐队被挑选出来
    // 乐队的全部数量一定是nums*2，且标号一定是0 ~ nums*2-1
    // 返回一共挑nums轮(也就意味着一定请到所有的乐队)，最少花费是多少？
     * 
     * nums < 9, programs长度小于500，每组测试乐队的全部数量一定是nums * 2，且标号一定是0 ~ nums * 2-1
     */
    
    /**
     * 思路:
     * 
     * 题意:
     * 乐队数量编号跟numbers*2是严格绑定的
     * nums=5, 乐队数量下标一定是0~9
     * 
     * nums=2, 一定有0,1, 2, 3 四支乐队
     * 一个乐队只能在一个项目里被挑到，它不能同时为两个项目挑到, 把所有乐队都挑全, 返回最低报价。
     * 
     * 题解: 题目给出的nums < 9, programs长度小于500
     * 假设最坏情况，nums = 8
     * 那么乐队数量16只, 任何两个乐队的组合为C_16^2, 共120种组合情况, 现在题目中有500组, 
     * 有大量冗余项目, 只选报价低的, 剩余的删掉，如果某个乐队不在报价中返回-1。 需要清洗数据
     * 
     * 洗数据:
     * 1. 调整乐队编号, 两个乐队最小值放第一位, 最大值放第二位, 最后是花费。 例如:  [3, 1, 200] 调整成[1, 3, 300]
     * 2. 排序: 第一维小的排前面，第一维数据相等的，根据第二维数据小的排前面，前两维数据都相同的，根据报价小的排前面
     *    排序之后: 前两项一样的这一组，我只要第一个(第一个报价最小), 剩下的都删掉
     *    例如: [0, 1, 7] [0, 1, 13] [0, 1, 27]  : [0, 1, 13] [0, 1, 27]  删掉
     *          [0, 2, 3] [0, 2, 5] [0, 7, 13]  : [0, 2, 5] [0, 7, 13]  删掉
     *          
     * 3. 清洗完成之后，用size统计有效数据的数量，同时将有效数据都挪到数组前面
     * 
     * 
     * 可以用一个整数的二进制状态，表示哪个乐队挑了哪个没挑
     * 一共就8组, 16个乐队
     * 最低位就代表0号乐队挑没有挑, 上面如果是1代表挑了, 上面如果是 0 , 代表没挑
     * 
     * 乐队全挑是啥样？
     * 应该是某一个整数，后面 16 位全是1，前面 16 位全是0。这个状态就表示你所有的都挑到了
     */
    public static int clean(int[][] programs) {
        // 调整乐队编号, 两个乐队最小值放第一位, 最大值放第二位, 最后是花费。
        int x = 0;
        int y = 0;
        for(int []p : programs) {
            x = Math.min(p[0], p[1]);
            y = Math.max(p[0], p[1]);
            p[0] = x;
            p[1] = y;
        }
        // 排序: 第一维小的排前面，第一维数据相等的，根据第二维数据小的排前面，前两维数据都相同的，根据报价小的排前面
        // a,b 传入的相当于是一维数组
        Arrays.sort(programs,(a, b) -> (a[0] != b[0]) ? (a[0] - b[0]) : (a[1] != b[1]) ? (a[1] - b[1]) : (a[2] - b[2]));
        // 前两项一样的这一组，我只要第一个(第一个报价最小), 剩下的都删掉
        x = programs[0][0]; // 排序后的第0组，必然会被留下来
        y = programs[0][1];
        for (int i = 1; i < programs.length; i++) {
            if(x == programs[i][0] && y == programs[i][1]) {
                programs[i] = null;
            } else {
                x = programs[i][0];
                y = programs[i][1];
            }
        }
        // 用size统计有效数据条数, 同时将有效数据都挪到前面
        int size = 1; // 第0条数据必然有效
        for (int i = 1; i < programs.length; i++) {
            if(programs[i] != null) {
                programs[size++] = programs[i];
            }
        }
        return size;
    }  
    
    // programs 和 size 固定参数
    // done: 固定参数。乐队全挑完的状态，一个整数。
    // 后面几位全是1. 如果轮数nums=8, 那么done后面 16 位全是1，前面 16 位全是0。这个状态就表示你所有的都挑到了
    // 也就是: (1 << (nums << 1)) - 1
    // pick: 当前挑了哪些乐队
    // index: 挑到了programs的index项，前面的都挑过了
    // rest: 剩余可用的轮数
    // cost: 当前已经做出的决定的最小代价
    // 主函数怎么调用: process(programs, size, (1 << (nums << 1)) - 1, 0, 0, nums, 0)
    // 当前啥也没挑，pick是0； index 从0开始，剩下轮数nums； 最小代价0
    public static int minCost = Integer.MAX_VALUE;
    public static void process(int programs[][], int size, int done, int pick, int index, int rest, int cost) {
        if(rest == 0) { // 如果轮数用完了
            // 如果pick == done，正好所有的乐队都挑完了, pk出最小代价
            if(pick == done) {
                minCost = Math.min(minCost, cost);
            }
            return;
        }
        // 如果轮数没有用完，来到了index位置，从左往右的尝试模型
        // 不要index位置
        process(programs, size, done, pick, index + 1, rest, cost);
        // 要index位置
        // 不能跳重复的乐队，计算出要index位置的项目，挑选的乐队是哪些
        int x = programs[index][0];
        int y = programs[index][1];
        // 当前挑选的两支乐队状态
        int cur = (1 << x) | (1 << y);
        if((pick & cur) == 0) { //这两支乐队，之前没有被挑过
            // index + 1搞下一个 rest-1，当前挑了一轮，所以要减1；当前项目要挑，所以cost加上当前项目的代价，作为下一轮的最小代价，去搞下一轮
            process(programs, size, done, pick | cur, index + 1, rest - 1, cost + programs[index][2]);
        } // != 0说明当前项目和以前的项目打架了
    }
    
    // 优化，可以省掉done参数
    public static void process2(int programs[][], int size, int pick, int index, int rest, int cost) {
        if(rest == 0) { // 如果轮数用完了
            minCost = Math.min(minCost, cost);
            return;
        }
        // 如果轮数没有用完，来到了index位置，从左往右的尝试模型
        // 不要index位置
        process2(programs, size, pick, index + 1, rest, cost);
        // 要index位置
        // 不能跳重复的乐队，计算出要index位置的项目，挑选的乐队是哪些
        int x = programs[index][0];
        int y = programs[index][1];
        // 当前挑选的两支乐队状态
        int cur = (1 << x) | (1 << y);
        // 省掉done参数的原因在这: 因为每一次去搞下一轮的时候，都保证了不会挑重复的，一轮轮往下搞，当rest=0的时候，必然所有的乐队都挑好了
        if((pick & cur) == 0) { //这两支乐队，之前没有被挑过 
            // index + 1搞下一个 rest-1，当前挑了一轮，所以要减1；当前项目要挑，所以cost加上当前项目的代价，作为下一轮的最小代价，去搞下一轮
            process2(programs, size, pick | cur, index + 1, rest - 1, cost + programs[index][2]);
        } // != 0说明当前项目和以前的项目打架了
    }
    
    /***
     * 分治
     * 
     * nums = 8, 16支乐队，总共120个组合
     * 
     * 其实也就是从120个组合中挑选出8组出来，是C_120^8的问题，超过了10^8次方
     * 
     * 需要优化，使用分治
     * 每次搞定C_120^4规模的问题，也就是每次只挑选4组出来，也就是搞定8个乐队，再搞定剩下的4组
     * 
     * 代码思路:
     * 搞两个数组，大小是1 << (nums << 1)
     * 可以表示下，后16位所有可能出现的状态。
     *           16位全是00000000....0000
     *           一直到16位全是11111.....1111
     *           的所有可能的状态
     * 
     * 以nums=8为例子:
     * 如果map中某个下标是0011100111100100，表示的是挑选了2 5 6 7 8 11 12 13这8只球队，
     * map[0011100111100100]表示的是挑选了这8只球队的最小代价，假设是100
     * 怎么求没挑的8只球队的最小代价？
     * 0011100111100100取反得到1100011000011011 -> map[1100011000011011]得到另外的8只球队的最小代价，
     * 
     * 两者一加就是16支球队的最小代价: map[i] + map[~i]
     * 
     * 注意~i，高位全是1，需要用mask与，去掉高位的1. mask = (1 << (nums << 1)) - 1，
     * 最终得到最小代价是map[i] + map[mask & ~i]
     * 
     * 特殊情况，如果nums=7 奇数，先搞定3组，记录在map1中，在搞定4组，记录在map2中。 nums / 2 和 nums- (nums / 2)
     * 例子:14位: map1中，某个下标是01110111000000, 6个1，表示选了6个球队的最小代价
     * map2中，01110111000000取反得到1000100011111111，8个1，表示选剩下的8个球队的最小代价
     * map1[01110111000000] + map2[1000100011111111] 就是选14个乐队的最小代价。
     */
    public static int minCost(int[][] programs, int nums) {
        if (nums == 0 || programs == null || programs.length == 0) {
            return 0;
        }
        // 清洗数据
        int size = clean(programs);
        int[] map1 = init(1 << (nums << 1)); // map全部初始化成系统最大
        int[] map2 = null;
        // 分治
        if((nums & 1) == 0) { // 是偶数
            // 任意8只球队选出来，最小代价是什么，记录在map里
            process3(programs, size, 0, 0, nums >> 1, 0, map1);
            //左右规模一样，任意8只球队选出来，最小代价是什么, map1和map2相等
            map2 = map1; 
        } else {
            // 奇数，例如nums = 7, 先搞3组，再搞定4组
            process3(programs, size, 0, 0, nums >> 1, 0, map1);
            map2 = init(1 << (nums << 1));
            //再搞定4组
            process3(programs, size, 0, 0, nums - (nums >> 1), 0, map2);
        }
        // 整合
        int ans = Integer.MAX_VALUE;
        // 用来去掉高位的1
        // 如果nums=8 那么就有 16 位mask : 0..00 1111.1111(16个1)
        // 如果nums=7 那么就有 14 位mask : 0..00 1111.1111(14个1)
        int mask = (1 << (nums << 1)) - 1; 
        // map中没一个下标，对应的最小代价，都pk一遍
        for(int i = 0; i <= map1.length - 1; i++) {
            // 任意8只球队选择，要有答案，才能累加出最小代价
            if(map1[i] != Integer.MAX_VALUE && map2[mask & ~i] != Integer.MAX_VALUE) {
                ans = Math.min(ans, map1[i] + map2[mask & ~i]);
            }
        }
        return ans;
    }
    
    // 在process2的基础上，加上map，记录任意8只球队选出来，最小代价是什么，记录在map里面
    public static void process3(int programs[][], int size, int pick, int index, int rest, int cost, int map[]) {
        if(rest == 0) { // 如果轮数用完了
            // pick 一定是8个1和8个0混着的状态
            // 任意8只球队选出来，最小代价是什么，记录在map里面
            map[pick] = Math.min(map[pick], cost); 
            return;
        }
        // 如果轮数没有用完，来到了index位置，从左往右的尝试模型
        // 不要index位置
        process3(programs, size, pick, index + 1, rest, cost, map);
        // 要index位置
        // 不能跳重复的乐队，计算出要index位置的项目，挑选的乐队是哪些
        int x = programs[index][0];
        int y = programs[index][1];
        // 当前挑选的两支乐队状态
        int cur = (1 << x) | (1 << y);
        // 省掉done参数的原因在这: 因为每一次去搞下一轮的时候，都保证了不会挑重复的，一轮轮往下搞，当rest=0的时候，必然所有的乐队都挑好了
        if((pick & cur) == 0) { //这两支乐队，之前没有被挑过 
            // index + 1搞下一个 rest-1，当前挑了一轮，所以要减1；当前项目要挑，所以cost加上当前项目的代价，作为下一轮的最小代价，去搞下一轮
            process3(programs, size, pick | cur, index + 1, rest - 1, cost + programs[index][2], map);
        } // != 0说明当前项目和以前的项目打架了
    }
    
    // 将map所有的值设置为最大值
    public static int[] init(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = Integer.MAX_VALUE;
        }
        return arr;
    }
}

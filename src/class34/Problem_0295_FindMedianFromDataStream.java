package class34;

import java.util.PriorityQueue;

// 295. 数据流的中位数

public class Problem_0295_FindMedianFromDataStream {
    /**
     * 中位数是有序整数列表中的中间值。如果列表的大小是偶数，则没有中间值，中位数是两个中间值的平均值。
     * 
     * 例如 arr = [2,3,4] 的中位数是 3 。
     * 例如 arr = [2,3] 的中位数是 (2 + 3) / 2 = 2.5 
     * 
     * 实现 MedianFinder 类:
     *  MedianFinder() 初始化 MedianFinder 对象。
     *  void addNum(int num) 将数据流中的整数 num 添加到数据结构中。
     *  double findMedian() 返回到目前为止所有元素的中位数。与实际答案相差 10-5 以内的答案将被接受。
     *  
     * 示例 1：
     * 输入
     * ["MedianFinder", "addNum", "addNum", "findMedian", "addNum", "findMedian"]
     * [[], [1], [2], [], [3], []]
     * 输出
     * [null, null, null, 1.5, null, 2.0]
     * 
     * 解释
     * MedianFinder medianFinder = new MedianFinder();
     * medianFinder.addNum(1);    // arr = [1]
     * medianFinder.addNum(2);    // arr = [1, 2]
     * medianFinder.findMedian(); // 返回 1.5 ((1 + 2) / 2)
     * medianFinder.addNum(3);    // arr[1, 2, 3]
     * medianFinder.findMedian(); // return 2.0
     */
    
    /**
     * 题目理解: 有一个数据流，吐出数字
     * 做出的结构提供两个方法:
     * 1.将数字加入到结构中
     * 2.返回流吐出的所有数字的中位数
     * 
     * 数字可以有重复值，
     * 如果没有重复值，可以用有序表TreeMap
     * 
     * 思路:
     * 用两个堆，大根堆 + 小根堆  -> 堆弹出和加入数字的事件复杂度都是logN级别
     * 
     * 这题的目标是：数组中较小的一半数全部放在大根堆，较大的一半全部放在小根堆，那么大根堆和小根堆的堆顶一顶能够算出中位数。
     * 流程:
     * 1.第一个数字，直接进入大根堆
     * 
     * 2.接下来的数字
     * 如果当前数<=大根堆堆顶，入大根堆
     * 如果当前数>大根堆堆顶，入小根堆
     * 两个堆的大小相差等于2，将size大的堆顶元素放入另一个堆
     * 
     * 求中位数:
     * 1.如果arr的元素个数是奇数，那么中位数就是size较大的堆的堆顶。
     *  例如大根堆中是3，2，小根堆中是4，5，6，那么小根堆堆顶4就是中位数
     * 2.如果arr的元素个数是偶数，那么中位数两个堆的堆顶相加除2
     *  例如大根堆中是3，2, 1，小根堆中是4，5，6，那么就是两个堆顶元素(3+4) / 2
     * 
     * 
     * ====
     * 例子:arr = [3, 5, 7, 6]
     * 第一个数是3，直接加入大根堆 --> 大根堆是空
     * 
     * 第二个数是5，比大根堆堆顶大，入小根堆
     * 
     * 第三个数是7, 比大根堆堆顶大，入小根堆
     * 
     * 第四个数是6, 比大根堆堆顶大，入小根堆，这时候小根堆中有3个元素，大根堆只有一个元素
     * 
     * 两者size相差2，所以要调整，将小根堆的堆顶元素5，放到大根堆中
     * 
     * 大根堆事5，3 小根堆事6，7 --> 可以发现数组中较小的一半全都在大根堆中，较大的一半全都在小跟堆中
     * 而且大根堆和小根堆的堆顶一顶能够算出中位数
     * 
     */
    class MedianFinder {
        // 大根堆
        PriorityQueue<Integer> maxH;
        // 小根堆
        PriorityQueue<Integer> minH;
        public MedianFinder() {
            // lamada表达式，这里也可以用比较器
            maxH = new PriorityQueue<Integer>((a, b) -> b - a);
            minH = new PriorityQueue<Integer>((a, b) -> a - b);
        }
        
        public void addNum(int num) {
            // 大根堆是空，或者当前元素小于大跟堆堆顶，直接入大根堆
            if(maxH.isEmpty() || num < maxH.peek()) {
                maxH.add(num);
            } else {
                // 入小根堆
                minH.add(num);
            }
            // 两个堆的大小相差超过2，将大的堆顶放到小的堆里面去
            blance();
        }
        
        // 找中位数
        public double findMedian() {
            // 两个堆的大小一样，说明是偶数个数
            if(maxH.size() == minH.size()) {
                // 堆顶元素相加除2
                return ((double)(maxH.peek() + minH.peek())) / 2; // 先转double，再除2
            } else {
                // 奇数个数，取size大的堆顶
                if(maxH.size() > minH.size()) {
                    return maxH.peek();
                } else {
                    return minH.peek();
                }
            }
        }
        
        // 两个堆的大小相差超过2，将size大的堆的堆顶放到小的堆里面去
        public void blance() {
            if(Math.abs(maxH.size() - minH.size()) == 2) {
                if(maxH.size() > minH.size()) {
                    minH.add(maxH.poll());
                } else {
                    maxH.add(minH.poll());
                }
            } 
        }
    }
    
    /**
     * 比较器实现
     * 
     */
//    class myComparator implements Comparator<Integer> {
//        @Override
//        public int compare(Integer arg0, Integer arg1) {
//            return arg0 - arg1; // 小根堆 
//            // return arg0 - arg1; // 大根堆 
//        } 
//    }

}

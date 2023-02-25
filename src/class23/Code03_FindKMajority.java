package class23;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class Code03_FindKMajority {
    /**
     *  扩展1：摩尔投票

        扩展2：给定一个正数K，返回所有出现次数>N/K的数

        1)给定一个数组arr，如果有某个数出现次数超过了数组长度的一半，打印这个数，如果没有不打印
        169.[[多数元素]] [E]
        https://leetcode-cn.com/problems/majority-element/

        2)给定一个数组arr和整数k，arr长度为N，如果有某些数出现次数超过了N/K，打印这些数，如果没有不打印
        229.[[求众数 II]] [M]
        https://leetcode-cn.com/problems/majority-element-ii/ 
     */
    
    /**
     * 思路:
     * 解决1) 给定一个数组arr，如果有某个数出现次数超过了数组长度的一半，打印这个数，如果没有不打印?
     * 
     * 一次删掉两个不同值的数, 如果arr中真的有水王的话, 这个水王数一定会剩下来
        因为水王数是大于一半的次数的。哪怕其它所有的数字都跟水王数为敌
        水王数也会活下来, 更不用说其它数字之间还会有内战的情况.
        
        arr一次删掉两个不同的数, 最后剩下的数一定是水王吗?
        不一定是水王(1,2,3,4,5)。
        如果有水王的话, 一定会剩下来;
        如果没有水王的话, 如果剩下来的数都不是水王，那就没有数是了。
        
        =====
        1) arr 一次删除两个不同值的数, 看最终谁会剩下来
        可能性
        1: 没有数字剩下来: 无水王数
        2: 有数剩下来
         x剩下来, 再遍历看x真实出现的次数跟N/2对比
         
      怎么一次删除两个不同的数?
            时间复杂度O(N)
            空间复杂度O(1)
            
      两个变量
        1) 候选
        2) 血量
        当血量=0, 认为没有候选人
        
      流程:
       一个一个数遍历, 三条规则:
            1) 如果没有候选(当血量等于0的时候无候选), 当前数立为候选, 血量+1
            2) 如果有候选
                  1)当前数跟候选不一样,血量--
                  2)当前数是候选,血量++
       遍历完成后,如果血量=0,表示什么数也没有剩下来,如果血量不等于零，候选就是剩下来的数.
     */
    
    public static void printHalfMajority(int arr[]) {
        if(arr == null || arr.length == 0) {
            return;
        }
        int HP = 0;
        int cand = 0;
        int N = arr.length;
        for(int i = 0; i <= N - 1; i++) {
            if(HP == 0) { //当血量等于0的时候无候选 
                cand = arr[i];
                HP = 1;
            } else if(cand == arr[i]) { // 血量不等于0，有候选，而且和当前数相等
                HP++;
            } else { // 血量不等于0，有候选，而且和当前数不相等
                HP--;
            }
        }
        if(HP == 0) { //当血量等于0的时候无候选 , 无水王数
            System.out.println("No match number");
            return;
        }
        // 有候选
        HP = 0; // 记录候选在数组中出现的次数
        for(int i = 0; i <= N - 1; i++) {
            if(arr[i] == cand) {
                HP++;
            }
        }
        if(HP > N / 2) {
            System.out.println(cand);
        } else {
            System.out.println("No match number");
        }
    }
    
    /**
     * 解决2) 给定一个数组arr和整数k，arr长度为N，如果有某些数出现次数超过了N/K，打印这些数，如果没有不打印
     * 
     * 思路:
     * 每个数出现的次数大于N/K, 那么这些数最多也就K-1个。
     * 因为如果这些数出现了K个，每个数出现的次数大于N/K -> K * (K / N) > N 了
     * 
     * 遍历数组，
     * 搞一个hash表，存放K-1个候选，
     * 没有满的时候，往里加，
     *      如果已经在里面了，血量+1
     *      如果不在里面，直接加
     * 满了的时候，hash表里面所有的候选血量都要-1，而且要删掉血量为0的候选
     * 
     * 遍历完数组之后，所有的候选，计算在数组中出现的次数，如果大于N/K, 就是候选
     * 
     */
    public static List<Integer> printKMajor(int[] arr, int K) {
        if (K < 2) {
            return null;
        }
        HashMap<Integer, Integer> cands = new HashMap<Integer, Integer>();
        int N = arr.length;
        // 最多K - 1个候选，放入map中
        for(int i = 0; i <= N - 1; i++) {
            int curNum = arr[i];
            if(cands.containsKey(curNum)) { // 如果在map中，血量+1
                cands.put(curNum, cands.get(curNum) + 1);
            } else {
                // 不在map中，如果map满了，所有的元素血量-1，而且删掉血量为0的元素
                if(cands.size() == K - 1) {
                    allCandsMinusOne(cands);
                } else { // 不在map中，map没满，直接加入，血量为1
                    cands.put(curNum, 1);
                }
            }
        }
        // k - 1个候选，计算在数组中出现的次数
        HashMap<Integer, Integer> reals = getReals(cands, arr);
        // 出现次数大于N/K的候选，打印
        ArrayList<Integer> ansList = new ArrayList<Integer>(); 
        for(Entry<Integer, Integer> entry : reals.entrySet()) {
            int key = entry.getKey();
            int value = entry.getValue();
            if(value > N / K) {
                System.out.println(value);
                ansList.add(key);
            }
        }
        return ansList;
    }
    
    public static void allCandsMinusOne(HashMap<Integer, Integer> cands) {
        ArrayList<Integer> removeList = new ArrayList<Integer>(); // 要删除的key记录下来
        for(Entry<Integer, Integer> entry: cands.entrySet()) {
            int key = entry.getKey();
            int value = entry.getValue();
            if(value == 1) {
                removeList.add(key);// 要删除的key记录下来，因为遍历map过程中不能删除key，会报ConcurrentModificationException
            }
            // 血量减1
            cands.put(key, value - 1);
        }
        // 删除血量为0的元素
        for(Integer key : removeList) {
            cands.remove(key);
        }
    }
    // 计算候选在数组中真实出现的次数
    public static HashMap<Integer, Integer> getReals(HashMap<Integer, Integer> cands, int arr[]) {
        HashMap<Integer, Integer> reals = new HashMap<Integer, Integer>();
        for(int i = 0; i <= arr.length - 1; i++) {
            int curNum = arr[i];
            if(cands.containsKey(curNum)) { // 如果数组当前的数是候选
                if(reals.containsKey(curNum)) { // 已经计算过。次数直接加1
                    reals.put(curNum, reals.get(curNum) + 1); 
                } else {
                    reals.put(curNum, 1);  // 没计算过，次数就是1
                }
            }
        }
        return reals;
    }
    
    
    public static void main(String[] args) {
        int arr[] = {4,2,1,1};
        printKMajor(arr, 3);
    }
    
}

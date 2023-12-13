package class34;

import java.util.HashMap;

// 380. O(1) 时间插入、删除和获取随机元素

public class Problem_0380_InsertDeleteGetRandom {
    /**
     * 实现RandomizedSet 类：
     * 
     *  RandomizedSet() 初始化 RandomizedSet 对象
     *  
     *  bool insert(int val) 当元素 val 不存在时，向集合中插入该项，并返回 true ；否则，返回 false 
     *  bool remove(int val) 当元素 val 存在时，从集合中移除该项，并返回 true ；否则，返回 false 。
     *  int getRandom() 随机返回现有集合中的一项（测试用例保证调用此方法时集合中至少存在一个元素）。每个元素应该有 相同的概率 被返回。
     *  你必须实现类的所有函数，并满足每个函数的 平均 时间复杂度为 O(1) 。
     */
    
    /**
     * 题意: 三个函数，一个函数添加元素，一个删除元素，一个随机get一个元素
     * 
     * 思路:
     * 两个hash表
     * 
     * 第一个key是值，value是index
     * 第二个keu是index，value是值
     * 
     * size从0开始，来了一个7，size是0
     * 第一个hash表里面放(7,0)
     * 第二个hash表里面放(0,7)
     * size++
     *  
     * 又来了一个8，size是1
     * 第一个hash表里面放(8,1)
     * 第二个hash表里面放(1,8)
     * size++
     * 
     * 又来了一个9，size是2
     * 第一个hash表里面放(9,2)
     * 第二个hash表里面放(2,9)
     * size++  ==> 3
     * 
     * 1.再没有发生remove的情况下，要随机取一个数，可以在0~size随机个数，也就是0~2，
     * 然后用随机出来的下标取一个数
     * 
     * 2.如果发生了remove，比如1位置被删了，size=2，这时候就不存在1小标的数，会有个洞，如果在0~2随机就不行了
     * 1位置是没数的，3位置永远取不到，怎么解决？
     * 
     * 解决: 删除的时候，用最后的位置填洞
     * 
     * 比如删除1位置，值是8，那么可以用2位置的9将8替换，然后删掉2位置那一行
     *
     */
    class RandomizedSet {
        
        private HashMap<Integer, Integer> keyIndexMap;
        private HashMap<Integer, Integer> indexKeyMap;
        int size;
        public RandomizedSet() {
            size = 0;
            keyIndexMap = new HashMap<Integer, Integer>();
            indexKeyMap = new HashMap<Integer, Integer>();
        }
        
        public boolean insert(int val) {
            // map中不存在才插入
            if(!keyIndexMap.containsKey(val)) {
                keyIndexMap.put(val, size); 
                indexKeyMap.put(size, val);
                size++;
                return true;
            }
            return false;
        }
        
        public boolean remove(int val) {
            // 存在才删，删的时候用最后一个元素的值覆盖要删的元素，然后删最后一个元素
            if(keyIndexMap.containsKey(val)) {
                int lastIndex = --size;
                // 要删除的值的index
                int index = keyIndexMap.get(val);
                // 最后一个元素的值
                int lastVal = indexKeyMap.get(lastIndex);
                // 更新indexKeyMap，用最后一个元素的值覆盖要删的元素
                indexKeyMap.put(index, lastVal);
                // 更新keyIndexMap
                keyIndexMap.put(lastVal, index);
                // 删除最后一个元素
                indexKeyMap.remove(lastIndex);
                keyIndexMap.remove(val);
                return true;
            }
            return false;
        }
        
        public int getRandom() {
            if(size == 0) { // 空，返回-1
                return -1;
            }
            int randomIndex =(int) (Math.random() * size);
            return indexKeyMap.get(randomIndex);
        }
    }
}

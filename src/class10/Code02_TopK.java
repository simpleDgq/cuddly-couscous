package class10;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

public class Code02_TopK {
    /*
     * 描述
        在实时数据流中找到最常使用的k个单词.
        实现TopK类中的三个方法:
        TopK(k), 构造方法
        add(word), 增加一个新单词
        topk(), 得到当前最常使用的k个单词.
        
        如果两个单词有相同的使用频率, 按字典序排名.
        
        样例
        text
        样例 1:
            输入：
            TopK(2)
            add("lint")
            add("code")
            add("code")
            topk()
            输出：["code", "lint"]
        解释：
        "code" 出现两次并且 "lint" 出现一次， 它们是出现最频繁的两个单词。
        样例 2:
            输入：
            TopK(1)
            add("aa")
            add("ab")
            topk()
            输出：["aa"]
        解释：(出现次数一样的时候，字典序小的输出)
        "aa" 和 "ab" 出现 , 但是aa的字典序小于ab。
        ```
     */
    
    /**
     * 三个东西:
     * 
     * 词频表、反向索引表、小根堆
     * 
     * 为什么是小根堆？ 堆顶维护一个门槛，如果新来的单词能够的词频大于堆顶，说明要更新堆顶元素，然后做一个heapify
     */
    public class Node {
        private String word;
        private int times;
        public Node(String word, int times) {
            this.word = word;
            this.times = times;
        }
    }
    // 堆中的元素，比较器
    public class NodeComparator implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) { // 词频不一样，按词频从小到大排序；词频一样，按字典序升序排序
            return o1.times != o2.times ? o1.times - o2.times : 
                o2.word.compareTo(o1.word); // 字典序比较，返回正数，说明o2的字典序在o1前面，o2应该排在o1前
        }
    }
    // TreeSet中的元素比较器
    public static class NodeTreeSetComp implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) { // 词频不一样，按词频从大到小排序；词频一样，按字典序降序排序(和上面的正好相反)
            return o1.times != o2.times ? (o2.times - o1.times) : (o1.word.compareTo(o2.word));
        }
    }
    
    private Node heap[];
    private int heapSize; // 堆中元素个数
    private HashMap<String, Node> strNodeMap; // 词频表
    private HashMap<Node, Integer> nodeIndexMap; // 反向索引表
    private TreeSet<Node> treeSet; // 搜集topk的答案
    NodeComparator nodeComparator;
    
    public Code02_TopK(int K) { // 构造函数，传入K
        this.heap = new Node[K];
        heapSize = 0;
        strNodeMap = new HashMap<String, Node>();
        nodeIndexMap = new HashMap<Node, Integer>();
        treeSet = new TreeSet<Node>(new NodeTreeSetComp());
        nodeComparator = new NodeComparator();
    }
   /* 当有一个字符串要加入到这个结构里时:
        1） 先建词频表
        2） 看字符串str在不在堆上
        何一个字符串进来的时候，如果它已经在堆上，更新完词频是变大的, 所以从这个字符串的位置往下heapify
        就是自己的孩子谁次数小谁上来，它交换完了之后再去看自己新的两个孩子，谁小在交换，它一直往底沉
     */
    public void add(String word) {
        if (heap.length == 0) { // top 0问题，别逗，直接return
            return;
        }
        // str   找到的对应节点  curNode
        Node curNode = null;
        // 对应节点  curNode  在堆上的位置
        int preIndex = -1;
        if (!strNodeMap.containsKey(word)) { // 如果词频表上没有，说明是第一次出现
            curNode = new Node(word, 1);
            strNodeMap.put(word, curNode);// 加入词频表
            nodeIndexMap.put(curNode, -1); // 堆上还没有
        } else { // 已经出现过
           curNode = strNodeMap.get(word);
           // 要在time++之前，先在treeSet中删掉
           // 原因是因为一但times++，curNode在treeSet中的排序就失效了
           // 这种失效会导致整棵treeSet出现问题
           if (treeSet.contains(curNode)) {
               treeSet.remove(curNode);
           }
           curNode.times++;
           preIndex = nodeIndexMap.get(curNode); // 看堆上是不是有
        }
        if(preIndex == -1) { // 堆上没有出现过，看是不是要加入堆
            if(heapSize == heap.length) { // 堆满了，需要和堆顶元素pk
                if(nodeComparator.compare(heap[0], curNode) < 0) { // 如果堆顶元素小于当前curNode。则替换，然后向下调整堆
                    treeSet.remove(heap[0]); // treeSet中remove掉
                    treeSet.add(curNode);
                    nodeIndexMap.put(heap[0], -1); // 注意，这里需要把heap[0]的索引设置成-1，因为后面被remove出去了
                    nodeIndexMap.put(curNode, 0); // 更新索引表
                    heap[0] = curNode;
                    heapify(0, heapSize); //向下调整堆
                }
            } else { // 没满，直接加入
                treeSet.add(curNode);
                heap[heapSize] = curNode;
                nodeIndexMap.put(curNode, heapSize); // 更新索引表
                // 向上调整堆
                heapInsert(heapSize++);
            }
        } else { // 堆上出现过，现在times变大了，需要向下调整堆
            treeSet.add(curNode); // times++的时候，remove掉了，需要重新加入，让treeSet能自动重新排序
            heapify(preIndex, heapSize); //向下调整堆
        }
    }
    
    public List<String> topk() {
        ArrayList<String> ans = new ArrayList<>();
        for (Node node : treeSet) {
            ans.add(node.word);
        }
        return ans;
    }
    
    // 向上调整堆
    public void heapInsert(int index) {
        while(index != 0) {
            int partent = (index - 1) / 2;
            if(nodeComparator.compare(heap[index], heap[partent]) < 0) { // 和父节点PK，如果比父节点小，就交换
                swap(partent, index);
                index = partent; // 继续往上
            } else {
                break; // 干不过父节点，直接退出
            }
        }
    }
    
    // 向下调整堆
    public void heapify(int index, int heapSize) {
        int l = 2 * index + 1;
        int r = 2 * index + 2;
        int smallest = index;
        // 左右孩子中选一个最小的交换, 一直往下
        while(l < heapSize) { // 要有左孩子
            if(nodeComparator.compare(heap[l], heap[index]) < 0) { // 左孩子比index小
                smallest = l;
            }
            if(r < heapSize && nodeComparator.compare(heap[r], heap[index]) < 0) { // 右孩子存在，且右孩子比求出来的smallest还小，更新smallest
                smallest = r;
            }
            if(index != smallest) { 
                swap(index, smallest);
            } else {// 如果左右没有比index小的，就不用交换。不用继续往下了，break
                break;
            }
            // 继续往下
            index = smallest;
            l = 2 * index + 1;
            r = 2 * index + 2;
        }
    }
    
    public void swap(int i, int j) {
        // 交换堆中的元素的时候，注意更新反向索引表
        nodeIndexMap.put(heap[i], j);
        nodeIndexMap.put(heap[j], i);
        // 交换堆中元素
        Node temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
    
    public static void main(String[] args) {
        Code02_TopK topK = new Code02_TopK(2);
        topK.add("lint");
        topK.add("code");
        topK.add("code");
        System.out.println(topK.topk());
        
        Code02_TopK topK2 = new Code02_TopK(1);
        topK2.add("aa");
        topK2.add("ab");
        System.out.println(topK2.topk());
    }
}

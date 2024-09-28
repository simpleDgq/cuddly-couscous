package class19;

import java.util.HashMap;

//本题测试链接 : https://leetcode.com/problems/lru-cache/
public class Code01_LRUCache {
    /**
     * 运用你所掌握的数据结构，设计和实现一个 LRU (最近最少使用) 缓存机制 。
     * 实现 LRUCache 类：
     * 
     * LRUCache(int capacity) 以正整数作为容量 capacity 初始化 LRU 缓存
     * int get(int key) 如果关键字 key 存在于缓存中，则返回关键字的值，否则返回 -1 。
     * void put(int key, int value) 如果关键字已经存在，则变更其数据值；如果关键字不存在，则插入该组「关键字-值」。
     * 当缓存容量达到上限时，它应该在写入新数据之前删除最久未使用的数据值，从而为新的数据值留出空间。
     * 
     * 进阶：你是否可以在 O(1) 时间复杂度内完成这两种操作？
     * 
     * 示例：
     * 
     * 输入
     * ["LRUCache", "put", "put", "get", "put", "get", "put", "get", "get", "get"]
     * [[2], [1, 1], [2, 2], [1], [3, 3], [2], [4, 4], [1], [3], [4]]
     * 输出
     * [null, null, null, 1, null, -1, null, -1, 3, 4]
     * 
     * 解释
     * LRUCache lRUCache = new LRUCache(2);
     * lRUCache.put(1, 1); // 缓存是 {1=1}
     * lRUCache.put(2, 2); // 缓存是 {1=1, 2=2}
     * lRUCache.get(1); // 返回 1
     * lRUCache.put(3, 3); // 该操作会使得关键字 2 作废，缓存是 {1=1, 3=3}
     * lRUCache.get(2); // 返回 -1 (未找到)
     * lRUCache.put(4, 4); // 该操作会使得关键字 1 作废，缓存是 {4=4, 3=3}
     * lRUCache.get(1); // 返回 -1 (未找到)
     * lRUCache.get(3); // 返回 3
     * 提示：
     * 
     * 1 <= capacity <= 3000
     * 0 <= key <= 3000
     * 0 <= value <= 10^4
     * 最多调用 3 * 10^4 次 get 和 put
     */

    /**
     * 每一条数据key,value都有一个时间戳，每次get，put都会更新这个时间戳，要淘汰数据的时候，都是淘汰时间戳离现在最远的那个数据。
     * 
     * 思路:
     * hashMap + 双向链表
     * Hash表里: key: A  value: Key对应的节点Node (A, 3), 一个双向链表的节点, 有last, next指针
     * 
     * 用双向链表来表示谁是较近操作的, 谁是较远操作的
     * put元素的时候，如果链表中不存在这个元素，则直接加入双向链表的尾部；如果存在这个元素，则从双向链表中将
     * 这个元素取下来，放在尾部； 通过hashMap判断一个节点是否链表中存在。没来一个元素同时加入hashmap和链表；
     *     容量满了之后，删除链表的头节点，也要从hashmap中删除对应的节点
     * get元素的时候，将对应的元素从链表中取下来放在链表的尾部。
     * 
     * 双向链标记一个头指针，记一个尾指针, 可以很方便的把一个数据直接挂在尾巴上
     * 
     * 例子:
     * 
     * A,3 到来，将(A,Node(A,3))放入hashmap中，同时将Node(A,3)放入双向链表;
     * B,7 到来，将(B,Node(B,7))放入hashmap中，同时将Node(B,7)放入双向链表最后，和Node(A,3)连接;
     * C,17 到来，将(C,Node(C,17))放入hashmap中，同时将Node(C,17)放入双向链表最后，和Node(B,7)连接;
     * B,23 到来，更改B节点的值为23，把B从原始链表上下环境中分离出来, 挂在尾部; --> 通过hashmap获得某个key对应的Node节点
     * get(A), 从哈希表直接找到A, 把A从链表分离出来, 挂到最后;
     * D, 15到来, 假设一共只有3条记录容量, 所以删除头节点C, D挂在链表尾部
     * 
     * 
     * 手动实现双向链表
     * 1) 新加一个Node节点, 挂在双向链表尾部
     * 2) 已知一个Node一定在双向链表上, 把Node前后环境重新连接后把它挂在尾巴上
     * 3) 在双向链表中删除头节点
     */
    public MyCache<Integer, Integer> cache;

    public Code01_LRUCache(int capacity) {
        cache = new MyCache<Integer, Integer>(capacity);
    }

    public int get(int key) {
        Integer ans = cache.get(key);
        return ans == null ? -1 : ans;// 没有找到返回-1
    }

    public void put(int key, int value) {
        cache.put(key, value);
    }

    // Node 节点
    public class Node<K, V> {
        public K key;
        public V value;
        public Node<K, V> last;
        public Node<K, V> next;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    // 双向链表
    public class NodeDoubleLinkedList<K, V> {
        // 双向链表的头尾节点
        public Node<K, V> head;
        public Node<K, V> tail;

        // 新加一个Node节点, 挂在双向链表尾部
        public void addNode(Node<K, V> node) {
            if (node == null) {
                return;
            }
            // 如果一个节点都没有
            if (head == null) {
                head = node;
                tail = head;
            } else { // 有节点存在，直接挂在尾部
                tail.next = node;
                node.last = tail;
                tail = node;
            }
        }

        // node 入参，一定保证！node在双向链表里！
        // node原始的位置，左右重新连好，然后把node分离出来
        // 挂到整个链表的尾巴上
        public void moveNodeToTail(Node<K, V> node) {
            if (node == tail) { // 是尾部节点了，不用移动，直接返回
                return;
            }
            // 如果要移动的节点就是头结点. 为什么要单独讨论？ 因为移动头结点和移动其它的节点有不同
            if (head == node) {
                head = node.next;
                head.last = null;
            } else { // 中间的节点
                node.last.next = node.next;
                node.next.last = node.last;
            }
            // 公共代码
            node.last = tail;
            node.next = null;
            tail.next = node;
            tail = node;
        }

        // 删除链表的头结点
        public Node<K, V> removeHead() {
            if (head == null) { // 没有节点了
                return null;
            }
            Node<K, V> res = head;
            // 如果只有一个节点
            if (head == tail) {
                head = tail = null;
            } else { // 多个节点
                head = head.next;
                head.last = null;
                res.next = null;
            }
            return res;
        }

    }

    public class MyCache<K, V> {
        public HashMap<K, Node<K, V>> map; // 方便通过key找到node
        public NodeDoubleLinkedList<K, V> nodeList; // 双向链表
        public final int capacity; // 容量，固定的

        public MyCache(int capacity) {
            this.capacity = capacity;
            map = new HashMap<K, Node<K, V>>();
            nodeList = new NodeDoubleLinkedList<K, V>();
        }

        // put一个值，如果存在，则直接更新，并且放到双向链表的最后
        // 如果不存在，则直接新建一个节点，挂在链表的最后，并且加入hashMap
        // 如果容量超过了，需要删除链表的头节点
        public void put(K key, V value) {
            if (map.containsKey(key)) { // 如果存在
                Node<K, V> node = map.get(key);
                node.value = value;
                // 移动到链表的最后
                nodeList.moveNodeToTail(node);
            } else {
                Node<K, V> newNode = new Node<K, V>(key, value);
                // 新加节点到双向链表
                nodeList.addNode(newNode);
                // 新加节点到hashMap
                map.put(key, newNode);
                // 如果容量超过了，需要删除链表的头节点,同时要从hashMap中删除该节点
                if (map.size() > capacity) {
                    Node<K, V> node = nodeList.removeHead();
                    map.remove(node.key);
                }
            }
        }

        // get一个值，并且将节点放在链表的最后
        public V get(K key) {
            if (map.containsKey(key)) {
                Node<K, V> node = map.get(key);
                // 放到最后
                nodeList.moveNodeToTail(node);
                return node.value;
            }
            return null;
        }
    }

}

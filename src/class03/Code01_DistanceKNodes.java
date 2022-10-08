package class03;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class Code01_DistanceKNodes {
	/**
	 * 给定三个参数:
	 *二叉树的头节点head,树上某个节点 target,正数K
	 *从 target开始，可以向上走或者向下走
	 *返回与 target的距离是K的所有节点.
	 *
	 * 思路:
	 * 1. 生成每个节点的父节点map, 这棵树里面每一个节点都能找到它的父是谁
   	 *		因为经典二叉树没有往上走的指针, 父map的功能是让一个节点往上走
	 *		当有两个这个父map之后, 距一个节点距离为2这件事就变成了图的宽度优先遍历
	 *	核心点:
	 *	第一，生成父map的技巧，
	 *	第二，你用这样size的方式，一批一批搞, 可以很好的标记同一批的节点的这个技巧
	 */
	public static class Node {
		int value;
		Node left;
		Node right;
		
		public Node(int value) {
			this.value = value;
			this.left = null;
			this.right = null;
		}
	}
	
	// 生成每个节点的父节点map
	public static void getParentsMap(Node head, HashMap<Node, Node> parents) {
		if(head == null) {
			return;
		}
		if(head.left != null) {
			parents.put(head.left, head);
			getParentsMap(head.left, parents);
		}
		if(head.right != null) {
			parents.put(head.right, head);
			getParentsMap(head.right, parents);
		}
	}
	
	public static ArrayList<Node> distanceKNodes(Node head, Node target, int K) {
		if(head == null || target == null) {
			return null;
		}
		HashMap<Node, Node> parents = new HashMap<Node, Node>();
		getParentsMap(head, parents);
		
		// 在层次遍历的过程中，将访问过的节点加入visited中，如果访问过，就不在入队列
		Queue<Node> queue = new LinkedList<Node>();
		HashSet<Node> visited = new HashSet<Node>();
		ArrayList<Node> ans = new ArrayList<Node>();
		queue.add(target);
		visited.add(target);
		
		int curLevel = 0; // 记录当前是第几层
		while(!queue.isEmpty()) {
			int size = queue.size();
			while(size != 0) { // 每次处理一层，第一次处理第0层，只有一个节点。第二次处理所有距离为1的节点,....
				Node cur = queue.poll();
				if(curLevel == K) {
					ans.add(cur);
				}
				if(cur.left != null && !visited.contains(cur.left)) {
					queue.add(cur.left);
					visited.add(cur.left);
				}
				if(cur.right != null && !visited.contains(cur.right)) {
					queue.add(cur.right);
					visited.add(cur.right);
				}
				if(parents.get(cur) != null && !visited.contains(parents.get(cur))) {
					queue.add(parents.get(cur));
					visited.add(parents.get(cur));
				}
				size--;
			}
			curLevel++;
			if (curLevel > K) {
				break;
			}
		}
		return ans;
	}
	
	
	public static void main(String[] args) {
		Node root = new Node(1);
		Node node2 = new Node(2);
		Node node3 = new Node(3);
		
		root.left = node2;
		root.right = node3;
		Node node4 = new Node(4);
		node3.right = node4;
		
//		HashMap<Node, Node> parents = new HashMap<Node, Node>();
//		getParentsMap(node1, parents);
		
//		System.out.println(parents.get(node2).value);
	
//		for(Node key : parents.keySet()) {
//			System.out.println(parents.get(key).value);
//		}

		Node target = node3;
		int K = 2;
		
		ArrayList<Node> ans = distanceKNodes(root, target, K);
		for(int i = 0; i <= ans.size() - 1; i++) {
			System.out.println(ans.get(i).value);
		}
	}

}

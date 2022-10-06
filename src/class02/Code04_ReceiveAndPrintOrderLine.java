package class02;

import java.util.HashMap;

public class Code04_ReceiveAndPrintOrderLine {
	/**
	 *  一种消息接收并打印的结构设计。
	 *  
	 * 已知一个消息流会不断地吐出整数 1~N，但不一定按照顺序吐出。如果上次打印的数为 i， 
	 * 那么当 i+1 出现时，请打印 i+1 及其之后接收过的并且连续的所有数，直到 1~N 全部接收
	 * 并打印完，请设计这种接收并打印的结构
	 * 
	 * 
	 * 思路:
	 * 两个表，头表和尾表，每个消息num来了之后，都创建一个节点放进头表和尾表；
	 * 然后尾表中检查有没有以num-1为尾的节点，如果有，将该节点的next指向num当前节点，同时将num当前节点从头表中删除，
	 * 将num-1从尾表中删除，因为连接之后，num就不是头了，num-1也就不是尾了；
	 * 同理，检查头表中有没有以num+1为头的节点，如果有，将num当前节点的next连接到num+1，同时将num当前节点从尾表中删除，
	 * 将num+1从头表中删除，因为连接之后，num就不是尾了，num+1也就不是头了。
	 */
	public static class Node {
		public String info; // 信息
		public Node next;
		
		public Node(String info) {
			this.info = info;
		}
	}
	
	public static class MessageBox {
		public HashMap<Integer, Node> headMap;
		public HashMap<Integer, Node> tailMap;
		public int waitPoint; // 等待的编号，当它来到的时候，打印所有信息
		
		public MessageBox() {
			headMap = new HashMap<Integer, Node>();
			tailMap = new HashMap<Integer, Node>();
			waitPoint = 1;
		}
		
		public void receive(int num, String info) {
			if(num < 1) {
				return;
			}
			Node node = new Node(info);
			// 放入头表和尾表
			headMap.put(num, node);
			tailMap.put(num, node);
			
			// 检查尾表是否有num-1为尾的节点
			if(tailMap.containsKey(num - 1)) {
				tailMap.get(num - 1).next = node;
				tailMap.remove(num - 1);
				headMap.remove(num);
			}
			
			// 检查头表是否有num+1为头的节点
			if(headMap.containsKey(num + 1)) {
				node.next = headMap.get(num + 1);
				headMap.remove(num + 1);
				tailMap.remove(num);
			}
			
			if (num == waitPoint) {
				print();
			}
			
		}
		
		public void print() {
			Node start = headMap.get(waitPoint);
			headMap.remove(waitPoint); // 从headMap里面删掉waitPoint
			while(start != null) {
				System.out.println(start.info);
				start = start.next;
				waitPoint++;
			}
			tailMap.remove(waitPoint - 1); // 从headMap里面删掉waitPoint
		}
		
		
		public static void main(String[] args) {
			// MessageBox only receive 1~N
			MessageBox box = new MessageBox();
			// 1....
			System.out.println("这是2来到的时候");
			box.receive(2,"B"); // - 2"
			System.out.println("这是1来到的时候");
			box.receive(1,"A"); // 1 2 -> print, trigger is 1
			box.receive(4,"D"); // - 4
			box.receive(5,"E"); // - 4 5
			box.receive(7,"G"); // - 4 5 - 7
			box.receive(8,"H"); // - 4 5 - 7 8
			box.receive(6,"F"); // - 4 5 6 7 8
			System.out.println("这是3来到的时候");
			box.receive(3,"C"); // 3 4 5 6 7 8 -> print, trigger is 3
			box.receive(9,"I"); // 9 -> print, trigger is 9
			box.receive(10,"J"); // 10 -> print, trigger is 10
			box.receive(12,"L"); // - 12
			box.receive(13,"M"); // - 12 13
			box.receive(11,"K"); // 11 12 13 -> print, trigger is 11

		}
	}
	
	
	
	
}

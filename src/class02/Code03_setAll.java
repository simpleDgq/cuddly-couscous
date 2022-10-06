package class02;

import java.util.HashMap;

public class Code03_setAll {
	/**
	 * 数据结构设计之O(1)实现setAll
	 * 
	 * HashMap: 增加一个setAll(value)， 所有key对应的value值变为新的值， 依然保证复杂度(1)
	 *	不能在setAll的时候遍历一遍, 那就不是O(1)了
	 *
	 * 1.给每条数据都打上时间戳, 每次put数据的时候，time都++;
	 * 2.setAll数据的时候，time也++，同时记录setAll数据;
	 * 3.get数据的时候，取出数据里面的time，和当前最新的time比较，如果小于当前最新的time，那么返回
	 * setAll数据的value；否则返回get到的数据.
	 * 
	 */
	
	
	public static class MyValue {
		public Integer value;
		public Integer time;
		
		public MyValue(Integer value, Integer time) {
			this.value = value;
			this.time = time;
		}
	}
	public static class MyHashMap {
		public static HashMap<Integer, MyValue> myHashMap;
		public static int time;
		public static MyValue setAll;
		
		public MyHashMap() {
			time = 0;
			myHashMap = new HashMap<Integer, MyValue>();
			setAll = new MyValue(null, -1);
		}
		public static void setAll(int all) {
			time++;
			setAll = new MyValue(all, time);
		}
		public static void put(int key, int value) {
			time++;
			myHashMap.put(key, new MyValue(value, time));
		}
		public static Integer get(int key) {
			if(!myHashMap.containsKey(key)) {
				return null;
			}
			
			int time = myHashMap.get(key).time;
			if(time < setAll.time) {
				return setAll.value;
			}
			return myHashMap.get(key).value;
		}
	}
	

}

package class02;

import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeMap;

public class Code01_ChooseWork {
	
	/**
	 * 给定数组hard和money，长度都为N, hard[i]表示i号的难度， money[i]表示i号工作的收入给定数组ability，长度都为M，
	 * ability[j]表示j号人的能力, 每一号工作，都可以提供无数的岗位，难度和收入都一样
	 *	但是人的能力必须>=这份工作的难度，才能上班. 返回一个长度为M的数组ans，ans[j]表示j号人能获得的最好收入
	 */
	
	/*
	 * 有序表
	 * 
	 * 1. 先对工作进行排序，难度小的放前面，难度一样的，收益大的放前面
	 * 2. 难度一样，收益小的删掉; 难度上升，但是收益不变或者变小，删掉
	 * 
	 * 这样一来，一定是难度变大，收益变高的工作，都放入HashMap中
	 * 
	 * 有一个人能力是6，要知道他能获得的最大收益，只需要查询难度<=6的且离6最近的工作 ==> 二分
	 * 
	 * 有序表，floorKey自带这种二分功能，且时间复杂度也是logN
	 */
	
	
	public static class Job {
		public int hard;
		public int money;
		
		public Job(int hard, int money) {
			this.hard = hard;
			this.money = money;
		}
	}
	
	public static class JobComparator implements Comparator<Job> {
		@Override
		// 如果返回负数，认为第一个参数应该排在前面
		// 如果返回正数，认为第二个参数应该排在前面
		// 如果返回0，认为谁放前面无所谓
		public int compare(Job o1, Job o2) {
			// 难度不一样，难度小的排在前面；难度一样，money多的在前面
			return o1.hard != o2.hard ? o1.hard - o2.hard : o2.money - o1.money;
		}
	}
	
	public static int[] getMoney(Job jobs[], int ability[]) {
		// 先排序
		Arrays.sort(jobs, new JobComparator());
		
		// key: hard value: money
		TreeMap<Integer, Integer> jobsTreeMap = new TreeMap<Integer, Integer>();
		jobsTreeMap.put(jobs[0].hard,jobs[0].money); // 排好序的第一份工作一定是hard小，money多的
		Job pre = jobs[0];
		// 去掉难度一样，但收益小的；去掉难度不一样，但是收益不变或者下降的
		for(int i = 1; i <= jobs.length - 1; i++) {
			if(jobs[i].hard != pre.hard && jobs[i].money > pre.money) { // hard 上升，而且money上升
				jobsTreeMap.put(jobs[i].hard, jobs[i].money);
					pre = jobs[i];
			}
		}
		
		// 有序表查离某个值最近的value
		int ans[] = new int[ability.length];
		for(int i = 0; i <= ability.length - 1; i++) {
			Integer key = jobsTreeMap.floorKey(ability[i]);
			ans[i] = key == null ? 0 :jobsTreeMap.get(key) ;
		}
		return ans;
	}
		


}

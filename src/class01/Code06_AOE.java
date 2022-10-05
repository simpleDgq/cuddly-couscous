package class01;

import java.util.Arrays;

public class Code06_AOE {
	/**
	 * 清空所有怪兽需要的AOE技能次数
	 * 给定两个非负数组x和hp，长度都是N，再给定一个正数range，x有序，x[i]表示i号怪兽在x轴上的位置；hp[i]表示i号怪兽的血量
		range表示法师如果站在x位置，用AOE技能打到的范围是：
		[x-range,x+range]，被打到的每只怪兽损失1点血量
		返回要把所有怪兽血量清空，至少需要释放多少次AOE技能？
	 */	
	/*
	 * 贪心 O(N)
	 * 1) 总是用技能的最左边缘刮死当前最左侧的没死的怪物
	 * 2) 然后向右找下一个没死的怪物，重复步骤1)
	 */
	public static int minAoe2(int[] x, int[] hp, int range) {
		// 举个例子：
		// 如果怪兽情况如下，
		// 怪兽所在，x数组  : 2 3 5 6 7 9
		// 怪兽血量，hp数组 : 2 4 1 2 3 1
		// 怪兽编号        : 0 1 2 3 4 5
		// 技能直径，range = 2
		int n = x.length;
		int cover[] = new int[n]; // 记录在每个位置放技能，能够cover的最右位置
		int r = 0;
		for(int i = 0; i <= n - 1; i++) {
			while(r < n && x[r] - x[i] <= range) { // cover[i] = j，表示如果技能左边界在i怪兽，那么技能会影响i...j-1号所有的怪兽
				r++;
			}
			cover[i] = r;
		}
		// 假设来到i号怪兽了
		// 如果i号怪兽的血量>0，说明i号怪兽没死
		// 根据我们课上讲的贪心：
		// 我们要让技能的左边界，刮死当前的i号怪兽
		// 这样能够让技能尽可能的往右释放，去尽可能的打掉右侧的怪兽
		// 此时cover[i]，正好的告诉我们，技能影响多大范围。
		// 比如当前来到100号怪兽，血量30
		// 假设cover[100] == 200
		// 说明，技能左边界在100位置，可以影响100号到199号怪兽的血量。
		// 为了打死100号怪兽，我们释放技能30次，
		// 释放的时候，100号到199号怪兽都掉血，30点
		// 然后继续向右寻找没死的怪兽，像课上讲的一样
		int ans = 0;
		for(int i = 0; i <= n - 1; i++) {
			if(hp[i] > 0) {
				int minAoe = hp[i];
				for(int j = i; j <= cover[i] - 1; j++) {
					hp[j] -= minAoe;
				}
				ans += minAoe;
			}
		}
		return ans;
	}
	
	/*
	 * 线段树 -- 范围上减去一个值，正好就是线段树， 时间复杂度O(logN) --> add方法
	 * 查询左边界的血量 --> query方法
	 * 所以这题只涉及到add和query两个方法。
	 * 2) 总是用技能的最左边缘刮死当前最左侧的没死的怪物
		3) 然后向右找下一个没死的怪物，重复步骤2)
	 */
	public static int minAoe3(int[] x, int[] hp, int range) { 
		int N = x.length;
		// 求cover数组
		int cover[] = new int[N];
		int r = 0;
		for(int i = 0; i <= N - 1; i++) {
			while(r < N && x[r] - x[i] <= range) {
				r++;
			}
			cover[i] = r - 1;
		}
		
		SegmentTree segmentTree = new SegmentTree(hp);
		segmentTree.build(1, N, 1);
		
		// 卡住左边界，刮死最边缘的怪物
		int ans = 0;
		for(int i = 1; i <= N; i++) {
			int leftHp = segmentTree.query(i, i, 1, N, 1); // 查询左边界血量
			if(leftHp > 0) {
				segmentTree.add(i, cover[i - 1] + 1, -leftHp, 1, N, 1);
				ans += leftHp;
			}
		}
		return ans;
	}
	
	public static class SegmentTree {
		public int arr[];
		public int lazy[]; // 懒更新信息
		public int sum[]; // 和二叉树
		public int MAXN;
		
		public SegmentTree(int originalArr[]) {
			this.MAXN = originalArr.length + 1;
			this.lazy = new int[MAXN << 2];
			this.sum = new int[MAXN << 2];
			this.arr = new int[this.MAXN]; // 0号位置不用
			for(int i = 1; i <= this.MAXN - 1; i++) { // 将原来的数组拷贝到arr中，以后用这个数组
				this.arr[i] = originalArr[i - 1];
			}
		}
		
		// 左右两个孩子相加，等于父节点的sum
		public void pushUp(int rt) {
			sum[rt] = sum[rt << 1] + sum[(rt << 1 | 1)];
		}
		
		// ln 表示下一层的节点的表示的范围的，元素个数。 eg: 1-100, 元素个数就是100
		public void pushDown(int ln, int rn, int rt) {
			// 这题没有更新，所以不写更新操作
			if(lazy[rt] != 0) { // 如果有懒更新信息，往下发一层
				lazy[rt << 1] += lazy[rt];
				lazy[(rt << 1 | 1)] += lazy[rt];
				sum[rt << 1] += lazy[rt] * ln;
				sum[(rt << 1 | 1)] += lazy[rt] * rn;
				lazy[rt] = 0;
			}
		}
		/*
		 * 给你l-r的范围，以及这个范围对应的二叉树节点，给我建好这个范围上的这棵树返回
		 */
		public void build(int l, int r, int rt) {
			if(l == r) { // l == r 一定是叶子节点，直接设置sum节点的值为arr的值
				sum[rt] = arr[l];
				return;
			}
			// 如果不是叶子节点，需要分左右去建这颗sum树
			int mid = l + ((r - l) >> 1);
			build(l, mid, rt << 1);
			build(mid + 1, r, (rt << 1 | 1));
			// 算出左右节点之后，pushUp算出sum[rt]
			pushUp(rt);
		}
		
		public void add(int L, int R, int C, int l, int r, int rt) {
			// 如果当前任务全包了当前所在的范围，直接更新当前节点的懒更新信息、sum信息
			if(L <= l && R >= r) {
				lazy[rt] += C;
				sum[rt] += (r - l + 1) * C; // 每一个节点都加一个C
				return;
			}
			// 如果没有全包，先下发老的任务，再处理新的任务
			int mid = l + ((r - l) >> 1);
			pushDown(mid - l + 1, r - mid,rt);
			// 分左右，处理新任务
			if(L <= mid) { // 只有需要去左边的时候，才左边 (想1-1000的这个例子，左边是1-500，右边是501-1000)
				add(L, R, C, l, mid, rt << 1);
			}
			if(R > mid) {
				add(L, R, C, mid + 1, r, (rt << 1 | 1));
			}
			// 左右两边算完，计算rt
			pushUp(rt);
		}
		
		public int query(int L, int R, int l, int r, int rt) {
			// 如果当前任务全包了当前所在的范围，直接返回
			if(L <= l && R >= r) {
				return sum[rt];
			}
			// 如果没有全包，先下发老的任务，再处理新的任务
			int mid = l + ((r - l) >> 1);
			pushDown(mid - l + 1, r - mid, rt);
			// 分左右，处理新任务
			int ans = 0;
			if(L <= mid) { // 只有需要去左边的时候，才左边 (想1-1000的这个例子，左边是1-500，右边是501-1000)
				ans += query(L, R, l, mid, rt << 1);
			}
			if(R > mid) {
				ans += query(L, R, mid + 1, r, (rt << 1 | 1));
			}
			return ans;
		}
	}
	
	
	// 为了测试
	public static int[] randomArray(int n, int valueMax) {
		int[] ans = new int[n];
		for (int i = 0; i < n; i++) {
			ans[i] = (int) (Math.random() * valueMax) + 1;
		}
		return ans;
	}

	// 为了测试
	public static int[] copyArray(int[] arr) {
		int N = arr.length;
		int[] ans = new int[N];
		for (int i = 0; i < N; i++) {
			ans[i] = arr[i];
		}
		return ans;
	}

	// 为了测试
	public static void main(String[] args) {
		int N = 50;
		int X = 500;
		int H = 60;
		int R = 10;
		int testTime = 50000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int len = (int) (Math.random() * N) + 1;
			int[] x2 = randomArray(len, X);
			Arrays.sort(x2);
			int[] hp2 = randomArray(len, H);
			int[] x3 = copyArray(x2);
			int[] hp3 = copyArray(hp2);
			int range = (int) (Math.random() * R) + 1;
			int ans2 = minAoe2(x2, hp2, range);
			int ans3 = minAoe3(x3, hp3, range);
			System.out.println(ans2 + "-" + ans3);
			if (ans2 != ans3) {
				System.out.println("出错了！");
			}
		}
		System.out.println("测试结束");

		N = 500000;
		long start;
		long end;
		int[] x2 = randomArray(N, N);
		Arrays.sort(x2);
		int[] hp2 = new int[N];
		for (int i = 0; i < N; i++) {
			hp2[i] = i * 5 + 10;
		}
		int[] x3 = copyArray(x2);
		int[] hp3 = copyArray(hp2);
		int range = 10000;

		start = System.currentTimeMillis();
		System.out.println(minAoe2(x2, hp2, range));
		end = System.currentTimeMillis();
		System.out.println("运行时间 : " + (end - start) + " 毫秒");

		start = System.currentTimeMillis();
		System.out.println(minAoe3(x3, hp3, range));
		end = System.currentTimeMillis();
		System.out.println("运行时间 : " + (end - start) + " 毫秒");
	}

}

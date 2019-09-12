package week1;

import java.util.*;
import java.io.*;

public class SlidingWindowMax {
	class FastScanner {
		StringTokenizer tok = new StringTokenizer("");
		BufferedReader in;

		FastScanner() {
			in = new BufferedReader(new InputStreamReader(System.in));
		}

		String next() throws IOException {
			while (!tok.hasMoreElements())
				tok = new StringTokenizer(in.readLine());
			return tok.nextToken();
		}
		int nextInt() throws IOException {
			return Integer.parseInt(next());
		}
	}
	
	public class SlidingWindow{
		int n;
		int sequence[];
		int m;
		void read() throws IOException {
			FastScanner in = new FastScanner();
			n = in.nextInt();
			sequence = new int[n];
			for (int i = 0; i < n; i++) {
				sequence[i] = in.nextInt();
			}
			m = in.nextInt();
		}
		int[] getMaxSequence(){
			int[] ans = new int[n - m + 1];
			// O(nm) implement
			for(int i = 0; i < n - m + 1; i++) {
				int maxValue = Integer.MIN_VALUE;
				for(int j = i; j < i + m; j++) {
					if(sequence[j] > maxValue) {
						maxValue = sequence[j];
					}
				}
				ans[i] = maxValue;
			}
			return ans;
		}
		
		/**
		 * Based on hint1
		 * @return
		 */
		int[] getMaxSequenceWithStackWithMax() {
			int[] ans = new int[n - m + 1];
			// TODO O(n) solution
			/**
			 * consider the sliding window as a queue
			 * construct the queue with two stackw/max
			 * https://stackoverflow.com/questions/69192/how-to-implement-a-queue-using-two-stacks
			 * the max in that window 
			 */
			// build the very first window
			QueueWithTwoStacks<Integer> window = new QueueWithTwoStacks<>();
			for(int i = 0; i < m; i++) {
				window.add(sequence[i]);
			}
			ans[0] = window.getMax();
			
			// following windows
			for(int i = 1; i + m - 1 < n; i++) {
				window.add(sequence[i+m-1]);
				window.remove();
				ans[i] = window.getMax(); 
			}
			return ans;
		}
		class QueueWithTwoStacks<E>{
			private StackWithMax<E> inbox = new StackWithMax<>();
			private StackWithMax<E> outbox = new StackWithMax<>();
			
			public void add(E e) {
				inbox.push(e);
			}
			public E remove() {
				if(outbox.isEmtpy()) {
					while (!inbox.isEmtpy()) {
						outbox.push(inbox.pop());
					}
				}
				return outbox.pop();
			}
			public int getMax() {
				return Math.max((int)inbox.getMax(), (int)outbox.getMax());
				// just for debugging
//				return Math.max((int)inbox.getMax(), 0);
//				return Math.max(0, (int)outbox.getMax());
			}
			
			/**
			 * 
			 * @author CJ
			 * StackWithMax just like that in the previous assignment
			 * @param <E>
			 */
			class StackWithMax<E>{
				private Stack<E> stackForValues = new Stack<>();
				private Stack<E> stackForMax = new Stack<>();
				public Integer max;
				
				public StackWithMax() {
					// TODO Auto-generated constructor stub
					max = Integer.MIN_VALUE;
				}
				public void push(E item) {
					stackForValues.push(item);
	                if((int) item > max) {
	                	max = (int) item;
	                	stackForMax.push((E) max);
	                }
	                else {
	                	stackForMax.push((E) max);
					}
				}
				public E pop() {
					stackForMax.pop();
					/**
					 * This is very important
					 */
					// important
					// max will store the last max even if the stack is empty now
					if(stackForMax.isEmpty()) {
						max = Integer.MIN_VALUE;
					}
					else {
						max = (int)stackForMax.lastElement();
					}
					return stackForValues.pop();
				}
				public int getMax() {
//					for(int i =0; i<stackForMax.size();i++) {
//						System.out.print(stackForMax.elementAt(i) + " ");
//					}
					if(stackForMax.isEmpty()) {
						return Integer.MIN_VALUE;
					}
					return (int)stackForMax.lastElement();
				}
				public boolean isEmtpy() {
					return stackForValues.isEmpty();
				}
			}
			public void testStackWithMax() {
				/**
				 * test StackWithMax
				 */
				
				StackWithMax stackWithMax = new StackWithMax<Integer>();
				stackWithMax.push(1);
				System.out.println(stackWithMax.getMax());
				stackWithMax.push(2);
				System.out.println(stackWithMax.getMax());
				stackWithMax.push(3);
				System.out.println(stackWithMax.getMax());
				stackWithMax.push(10);
				System.out.println(stackWithMax.getMax());
				stackWithMax.pop();
				stackWithMax.push(4);
				System.out.println(stackWithMax.getMax());
				stackWithMax.pop();
				System.out.println(stackWithMax.getMax());
				stackWithMax.pop();
				System.out.println(stackWithMax.getMax());
				stackWithMax.pop();
				System.out.println(stackWithMax.getMax());
				
			}
		}
		/**
		 * test StackWithMax
		 */
		public void testStackWithMax() {
			QueueWithTwoStacks<Integer> queueWithTwoStacks = new QueueWithTwoStacks<>();
			queueWithTwoStacks.testStackWithMax();
		}
		/**
		 * test QueueWithTwoStacks
		 */
		public void testQueueWithTwoStacks() {
			QueueWithTwoStacks<Integer> queueWithTwoStacks = new QueueWithTwoStacks<>();
			queueWithTwoStacks.add(2);
			queueWithTwoStacks.add(7);
			queueWithTwoStacks.add(3);
			queueWithTwoStacks.add(1);
			System.out.println(queueWithTwoStacks.getMax());
			queueWithTwoStacks.remove();
			queueWithTwoStacks.add(5);
			System.out.println(queueWithTwoStacks.getMax());
			queueWithTwoStacks.remove();
			queueWithTwoStacks.add(2);
			System.out.println(queueWithTwoStacks.getMax());
			queueWithTwoStacks.remove();
			queueWithTwoStacks.add(6);
			System.out.println(queueWithTwoStacks.getMax());
			queueWithTwoStacks.remove();
			queueWithTwoStacks.add(2);
			System.out.println(queueWithTwoStacks.getMax());
		}
		
		/**
		 * Based on hint2
		 * This is hard to understand!
		 * https://stackoverflow.com/questions/56720446/finding-second-largest-element-in-sliding-window?noredirect=1&lq=1
		 * https://www.coursera.org/learn/data-structures/discussions/weeks/1/threads/RxcNj59KEemuvw73k0cMAg/replies/XUklbcCMEempqw45cwE0nA/comments/t4stNMHfEemX3Q5u3dtUcA
		 * a b c d | e f g h
		 * for block [a b c d], it has
		 * [d],[c d], [b c d], [a b c d] as prefixes
		 * [a], [a b], [a b c], [a b c d] as suffixes
		 * for example, for window [b c d e] = [b c d] + [e]
		 * then max = max(max(b c d), max(e))
		 * From head to tail -> max among suffixes
		 * From tail to head -> max among prefixes
		 */
		int[] getMaxSequenceFromPreprocess() {
			int blockNumber = (n % m == 0 ? n / m: n / m + 1);
			int[] fillSequence = new int[blockNumber * m];
			for(int i=0; i < fillSequence.length; i++) {
				fillSequence[i] = (i < sequence.length ? sequence[i]: Integer.MIN_VALUE); 
			}
			int[] blockRightEdges = new int[blockNumber];
			// put right edges of blocks into `blockRightEdges`
			for(int i = 0; i < blockNumber; i++) {
				blockRightEdges[i]= (i + 1) * m - 1; 
			}
			// from head to tail, suffixes
			int i = 0;
			int blockIdx = 0;
			int currMax = Integer.MIN_VALUE;
			HashMap<Integer, Integer> maxInSuffixes = new HashMap<>();
			while(i < n) {
				if(fillSequence[i] > currMax) {
					currMax = fillSequence[i];
				}
				maxInSuffixes.put(i, currMax);
				i++;
				if(i > blockRightEdges[blockIdx]) {
					currMax = Integer.MIN_VALUE;
					blockIdx++;
				}
			}
			// similarly, we can get prefixes from tail to head
			int[] blockLeftEdges = new int[blockNumber];
			// put left edges of blocks into `blockLeftEdges`
			for(i = 0; i < blockNumber; i++) {
				blockLeftEdges[i]= i * m; 
			}
			i = n - 1;
			blockIdx = blockNumber - 1;
			currMax = Integer.MIN_VALUE;
			HashMap<Integer, Integer> maxInPreffixes = new HashMap<>();
			while(i >= 0) {
				if(fillSequence[i] > currMax) {
					currMax = fillSequence[i];
				}
				maxInPreffixes.put(i, currMax);
				i--;
				if(i < blockLeftEdges[blockIdx]) {
					currMax = Integer.MIN_VALUE;
					blockIdx--;
				}
			}
			// Slide the window
			int[] ans = new int[n - m + 1];
			for(i = 0; i < n - m + 1; i++) {
				int start = i;
				int end = i + m - 1;
				ans[i] = Math.max(maxInPreffixes.get(start), maxInSuffixes.get(end)); 
			}
			return ans;
		}
		
		/**
		 * Based on hint3
		 * Dequeue
		 * https://www.geeksforgeeks.org/sliding-window-maximum-maximum-of-all-subarrays-of-size-k/
		 * make a decreasing order deque containing index of those decreasing values
		 * make sure:
		 * deque stores indexes
		 * ans stores values
		 */
		int[] getMaxSequenceWithDeque() {
			int[] ans = new int[n - m + 1];
			Deque<Integer> deque = new LinkedList<Integer>();
			for(int i = 0; i < m; i++) {
				while(!deque.isEmpty() && sequence[i] > sequence[deque.peekLast()]) {
					deque.removeLast();
				}
				deque.add(i);
			}
			ans[0] = sequence[deque.peek()];
			for(int i = m; i < n; i++) {
				// update window starts
				while(!deque.isEmpty() && deque.peek() <= i - m) {
					deque.removeFirst();
				}
				// examine the new element
				while(!deque.isEmpty() && sequence[i] > sequence[deque.peekLast()]){
					deque.removeLast();
				}
				deque.add(i);
				ans[i - m + 1] = sequence[deque.peek()];
			}
			return ans;
		}
	}
	
	public static void main(String[] args) throws IOException {
		new Thread(null, new Runnable() {
            public void run() {
                try {
                    new SlidingWindowMax().run();
                } catch (IOException e) {
                }
            }
        }, "1", 1 << 26).start();
	}
	public void run() throws IOException {
		SlidingWindow sWindow = new SlidingWindow();
		sWindow.read();
		
		long startTime = System.nanoTime();
		
//		int[] ans = sWindow.getMaxSequenceWithStackWithMax();
//		int[] ans = sWindow.getMaxSequenceFromPreprocess();
		int[] ans = sWindow.getMaxSequenceWithDeque();
		
		long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println("Runtime: " + totalTime/(1e6) + " ms");
        Runtime runtime = Runtime.getRuntime();
		long usedMemory = (long) (runtime.totalMemory()-runtime.freeMemory())/(1024*1024);
		if(usedMemory < 2.5) {
			System.out.println("Memory usage < 2.5 MB");
		}
		else {
			System.out.println("Memory usage: " + usedMemory + " MB");
		}
		
		for(int i = 0; i < ans.length; i++) {
			System.out.print(ans[i] + " ");
		}
		
		/**
		 * test StackWithMax, QueueWithTwoStacks
		 */
//		sWindow.testStackWithMax();
//		sWindow.testQueueWithTwoStacks();
//		sWindow.main(null);
		
		
	}
}

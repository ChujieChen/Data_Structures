package week1;

import java.util.*;

import com.sun.accessibility.internal.resources.accessibility;
import com.sun.org.apache.xalan.internal.xsltc.dom.SAXImpl.NamespaceWildcardIterator;

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
			for(int i = 1; i + m < n; i++) {
				window.add(sequence[i+m]);
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
					return stackForValues.pop();
				}
				public int getMax() {
					if(stackForMax.isEmpty()) {
						return Integer.MIN_VALUE;
					}
					return (int)stackForMax.lastElement();
				}
				public boolean isEmtpy() {
					return stackForValues.isEmpty();
				}
			}
		}
		
		
		public void main(String[] args) {
			/**
			 * test StackWithMax
			 */
			/*
			StackWithMax stackWithMax = new StackWithMax<>();
			stackWithMax.push(1);
			System.out.println(stackWithMax.getMax());
			stackWithMax.push(2);
			System.out.println(stackWithMax.getMax());
			stackWithMax.push(3);
			System.out.println(stackWithMax.getMax());
			stackWithMax.push(4);
			System.out.println(stackWithMax.getMax());
			stackWithMax.pop();
			System.out.println(stackWithMax.getMax());
			System.out.println(stackWithMax.isEmtpy());
			stackWithMax.pop();
			System.out.println(stackWithMax.getMax());
			System.out.println(stackWithMax.isEmtpy());
			stackWithMax.pop();
			System.out.println(stackWithMax.getMax());
			System.out.println(stackWithMax.isEmtpy());
			stackWithMax.pop();
//			System.out.println(stackWithMax.getMax());
			System.out.println(stackWithMax.isEmtpy());
//			stackWithMax.pop();
//			System.out.println(stackWithMax.getMax());
			System.out.println(stackWithMax.isEmtpy());
			*/
			/**
			 * test QueueWithTwoStacks
			 */
			/*
			QueueWithTwoStacks<Integer> queueWithTwoStacks = new QueueWithTwoStacks<>();
			queueWithTwoStacks.add(3);
			queueWithTwoStacks.add(2);
			System.out.println(queueWithTwoStacks.getMax());
			queueWithTwoStacks.remove();
			System.out.println(queueWithTwoStacks.getMax());
			queueWithTwoStacks.add(4);
			System.out.println(queueWithTwoStacks.getMax());
			*/
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
		int[] ans = sWindow.getMaxSequence();
		for(int i = 0; i < ans.length; i++) {
			System.out.print(ans[i] + " ");
		}
		
		/**
		 * test StackWithMax, QueueWithTwoStacks
		 */
		/*
		sWindow.main(null);
		*/ 
		
	}
}
package week4;

import java.util.*;

import com.sun.media.jfxmedia.control.VideoDataBuffer;

import java.io.*;

public class tree_orders {
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

	public class TreeOrders {
		int n;
		int[] key, left, right;
		
		void read() throws IOException {
			FastScanner in = new FastScanner();
			n = in.nextInt();
			key = new int[n];
			left = new int[n];
			right = new int[n];
			for (int i = 0; i < n; i++) { 
				key[i] = in.nextInt();
				left[i] = in.nextInt();
				right[i] = in.nextInt();
			}
		}

		List<Integer> inOrder() {
			ArrayList<Integer> result = new ArrayList<Integer>();
			// TODO:
            // Finish the implementation
            // You may need to add a new recursive method to do that
			InOrderTraversal(0, result);
			return result;
		}
		
		void InOrderTraversal(int index, ArrayList<Integer> result) {
			if(index == -1)
				return;
			InOrderTraversal(left[index], result);
			result.add(key[index]);
			InOrderTraversal(right[index], result);
		}
		
		List<Integer> preOrder() {
			ArrayList<Integer> result = new ArrayList<Integer>();
			// TODO:
            // Finish the implementation
            // You may need to add a new recursive method to do that
			PreOrderTraversal(0, result);
			return result;
		}
		void PreOrderTraversal(int index, ArrayList<Integer> result) {
			if(index == -1)
				return;
			result.add(key[index]);
			PreOrderTraversal(left[index], result);
			PreOrderTraversal(right[index], result);
		}
		
		List<Integer> postOrder() {
			ArrayList<Integer> result = new ArrayList<Integer>();
			// TODO:
            // Finish the implementation
            // You may need to add a new recursive method to do that
            PostOrderTraveral(0, result);
			return result;
		}
		void PostOrderTraveral(int index, ArrayList<Integer> result) {
			if(index == -1)
				return;
			PostOrderTraveral(left[index], result);
			PostOrderTraveral(right[index], result);
			result.add(key[index]);
		}
	}

	static public void main(String[] args) throws IOException {
            new Thread(null, new Runnable() {
                    public void run() {
                        try {
                            new tree_orders().run();
                        } catch (IOException e) {
                        }
                    }
                }, "1", 1 << 26).start();
	}

	public void print(List<Integer> x) {
		for (Integer a : x) {
			System.out.print(a + " ");
		}
		System.out.println();
	}

	public void run() throws IOException {
		TreeOrders tree = new TreeOrders();
		tree.read();
		print(tree.inOrder());
		print(tree.preOrder());
		print(tree.postOrder());
	}
}

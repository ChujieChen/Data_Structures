package week1;

import java.util.*;

import com.sun.org.apache.xerces.internal.dom.ParentNode;

import java.io.*;
import java.sql.Time;

public class tree_height {
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

	public class TreeHeight {
		int n;
		int parent[];
		
		void read() throws IOException {
			FastScanner in = new FastScanner();
			n = in.nextInt();
			parent = new int[n];
			for (int i = 0; i < n; i++) {
				parent[i] = in.nextInt();
			}
		}

		int computeHeight() {
                        // Replace this code with a faster implementation
			int maxHeight = 0;
			for (int vertex = 0; vertex < n; vertex++) {
				int height = 0;
				for (int i = vertex; i != -1; i = parent[i])
					height++;
				maxHeight = Math.max(maxHeight, height);
			}
			return maxHeight;
		}
		
		int computeHeightFast() {
			// construct a general tree O(n) then count height from root to leaf O(n)
			// construct a tree: we can use nested arrays, index for node, inner array for children nodes
			ArrayList<Integer>[] nodes = new ArrayList[n];
			// have to initialize inner ArrayList
			for(int i=0; i<nodes.length; i++) {
				nodes[i] = new ArrayList<>(); 
			}
			int rootNode = -1;
			for(int currNode = 0; currNode < n; currNode++) {
				int parentNode = parent[currNode];
				if(parentNode == -1) {
					rootNode = currNode;
				}
				else {
					nodes[parentNode].add(currNode);
				}
			}
			// check if the tree has been well built
//			for(int i=0; i<nodes.length; i++) {
//				System.out.println(nodes[i]);
//			}
			// calculate the Height using heightHelper
			if(rootNode == -1) {
				System.out.println("Something wrong: no root");
				return -1;
			}
			
			return heightHelper(rootNode, nodes);
		}
		
		int heightHelper(int node, ArrayList<Integer>[] nodes) {
			if(node == -1) {
				return 0;
			}
			int maxHeight = 0;
			for(int i = 0; i < nodes[node].size(); i++) {
				// iterate all children
				int childHeight = heightHelper(nodes[node].get(i), nodes);
				if(maxHeight < childHeight) {
					maxHeight = childHeight;
				}
			}
			return 1 + maxHeight;
		}
	}

	static public void main(String[] args) throws IOException {
            new Thread(null, new Runnable() {
                    public void run() {
                        try {
                            new tree_height().run();
                        } catch (IOException e) {
                        }
                    }
                }, "1", 1 << 26).start();
	}
	public void run() throws IOException {
		TreeHeight tree = new TreeHeight();
		tree.read();
//		long startTime = System.nanoTime();
//		System.out.println(tree.computeHeight());
//		long endTime   = System.nanoTime();
//        long totalTime = endTime - startTime;
//        System.out.println("runtime:" + totalTime);
		long startTime = System.nanoTime();
		System.out.println(tree.computeHeightFast());
		
		long endTime   = System.nanoTime();
		long totalTime = endTime - startTime;
        System.out.println("Runtime: " + totalTime + " ns");
        
		Runtime runtime = Runtime.getRuntime();
		long usedMemory = (long) (runtime.totalMemory()-runtime.freeMemory())/(1024*1024);
		if(usedMemory < 2.5) {
			System.out.println("Memory usage < 2.5 MB");
		}
		else {
			System.out.println("Memory usage: " + usedMemory + " MB");
		}
	}
}

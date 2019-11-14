package week4;

import java.util.*;

import com.sun.org.apache.xpath.internal.operations.And;

import java.io.*;

public class is_bst {
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

    public class IsBST {
        class Node {
            int key;
            int left;
            int right;

            Node(int key, int left, int right) {
                this.left = left;
                this.right = right;
                this.key = key;
            }
        }

        int nodes;
        Node[] tree;

        void read() throws IOException {
            FastScanner in = new FastScanner();
            nodes = in.nextInt();
            tree = new Node[nodes];
            for (int i = 0; i < nodes; i++) {
                tree[i] = new Node(in.nextInt(), in.nextInt(), in.nextInt());
            }
        }

        boolean isBinarySearchTree() {
        	// TODO:
        	// Implement correct algorithm here
        	// I think i can use InOrder
        	// and make sure new is always greater than old
        	// but the pdf says it would be too slow
        	// I think the PDF is wrong about this
        	// ref: leetcode 98
        	/**
        	 * InOrder with stack
        	 */
        	if(tree.length <= 1)
        		return true;
        	Stack<Integer> stack = new Stack<>();
        	int currMax = Integer.MIN_VALUE;
        	int index = 0;
        	while(!stack.isEmpty() || index != -1) {
        		while(index != -1) {
        			stack.push(index);
        			index = tree[index].left;
        		}
        		index = stack.pop();
        		if(tree[index].key < currMax)
        			return false;
        		currMax = tree[index].key;
        		// This line below is very important
        		// Wrong: stack.push(tree[index].right);
        		index = tree[index].right;
        	}
        	return true;
        }       
        /**
    	 * This does NOT work; because currMax in the last call
    	 * wouldn't be affected by current call
    	 */
        boolean InOrderTraversalIncorrect(int index, int currMax, boolean valid) {
        	
        	if(index == -1)
        		return true;
        	if(valid == false)
        		return false;
        	InOrderTraversalIncorrect(tree[index].left, currMax, valid);
        	System.out.println(tree[index].key);
        	System.out.println(currMax);
        	if(tree[index].key > currMax) {
        		currMax = tree[index].key;
        	}
        	else{
        		valid = false;
        		System.out.print("gg");
        	}
        	System.out.println(currMax);
        	InOrderTraversalIncorrect(tree[index].right, currMax, valid);
        	return valid;
        }
    }

    static public void main(String[] args) throws IOException {
        new Thread(null, new Runnable() {
            public void run() {
                try {
                    new is_bst().run();
                } catch (IOException e) {
                }
            }
        }, "1", 1 << 26).start();
    }
    public void run() throws IOException {
        IsBST tree = new IsBST();
        tree.read();
        if (tree.isBinarySearchTree()) {
            System.out.println("CORRECT");
        } else {
            System.out.println("INCORRECT");
        }
    }
}

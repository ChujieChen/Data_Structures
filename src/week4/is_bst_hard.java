package week4;

import java.util.*;
import java.io.*;

public class is_bst_hard {
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
        	// Implement correct algorithm here
        	// TODO:
        	// still leetcode 98, but has to use solution 1
        	if(tree.length <= 0) return true;
        	return helper(0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }
        boolean helper(int index, int min, int max) {
        	if(index == -1) return true;
        	// check current node isIn range[min, max)
        	int currVal = tree[index].key;
        	// for sample 6
        	if(currVal != Integer.MAX_VALUE) {
        		if(currVal < min || currVal >= max) return false;
        	}
        	// update range and pass it to subtrees
        	if(! helper(tree[index].left, min, currVal)) return false;
        	if(! helper(tree[index].right, currVal, max)) return false;
        	return true;
        }
    }

    static public void main(String[] args) throws IOException {
        new Thread(null, new Runnable() {
            public void run() {
                try {
                    new is_bst_hard().run();
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

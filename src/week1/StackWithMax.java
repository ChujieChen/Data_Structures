package week1;

import java.util.*;
import java.io.*;

public class StackWithMax {
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

    public void solve() throws IOException {
        FastScanner scanner = new FastScanner();
        int queries = scanner.nextInt();
        Stack<Integer> stack = new Stack<Integer>();

        for (int qi = 0; qi < queries; ++qi) {
            String operation = scanner.next();
            if ("push".equals(operation)) {
                int value = scanner.nextInt();
                stack.push(value);
            } else if ("pop".equals(operation)) {
                stack.pop();
            } else if ("max".equals(operation)) {
                System.out.println(Collections.max(stack));
            }
        }
    }
    // TODO
    public void solveFast() throws IOException{
    	FastScanner scanner = new FastScanner();
        int queries = scanner.nextInt();
        Stack<Integer> stack = new Stack<Integer>();
        // use the second stack to store the max_i of all 0~i elements
        Stack<Integer> maxStack = new Stack<>();
        int maxForAllPrevious = Integer.MIN_VALUE;
        
        for (int qi = 0; qi < queries; ++qi) {
            String operation = scanner.next();
            if ("push".equals(operation)) {
                int value = scanner.nextInt();
                stack.push(value);
                if(value > maxForAllPrevious) {
                	maxForAllPrevious = value;
                	maxStack.push(maxForAllPrevious);
                }
                else {
                	maxStack.push(maxForAllPrevious);
				}
            } else if ("pop".equals(operation)) {
                stack.pop();
                maxStack.pop();
                if(maxStack.isEmpty()) {
                	maxForAllPrevious = Integer.MIN_VALUE;
                }
                else {
                	maxForAllPrevious = maxStack.lastElement();
                }
            } else if ("max".equals(operation)) {
                System.out.println(maxStack.lastElement());
            }
        }
        
    }

    static public void main(String[] args) throws IOException {
    	long startTime = System.nanoTime();
    	
        new StackWithMax().solveFast();
        
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
    }
}

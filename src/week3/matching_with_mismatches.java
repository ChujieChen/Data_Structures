package week3;

import java.util.*;
import java.util.stream.Collectors;
import java.io.*;

public class matching_with_mismatches {
	// TODO: everything
    public List<Integer> solve(int k, String text, String pattern) {
    	ArrayList<Integer> pos = new ArrayList<>();
    	long[] hText = new long[text.length()];
    	for(int i = 0; i < text.length(); i++) {
    		hText[i] = text.substring(0, i).hashCode();
    	}
    	long[] hPattern = new long[pattern.length()];
    	for(int i = 0; i < pattern.length(); i++) {
    		hPattern[i]= pattern.substring(0, i).hashCode(); 
    	}
    	// H(SaSa+1...Sa+l-1) = h[a+l] - x^l * h[a]
    	// Since java is using the polyHash defined in assignment 4 with x=31
    	for(int i = 0; i < text.length() - pattern.length() + 1; i++) {
    		String subS = text.substring(i, i + pattern.length());
    		int numOfMismatch = 
    				numberOfMismatchesNaive(0, pattern.length(), k, subS, pattern);
    		if(numOfMismatch <= k)
    			pos.add(i);
    	}
    	return pos;
    }
    public int numberOfMismatchesNaive(int left, int right, int k, String subS, String p) {
    	int count = 0;
    	// using overload to make count as an `optional` parameter
    	return numberOfMismatchesNaive(left, right, k, count, subS, p);
    }
    public int numberOfMismatchesNaive(int left, int right, int k, int count, String subS, String p) {
    	// binary search
    	if(count > k) return count;
    	if(left >= right - 1) return count;
    	int mid = (left + right) / 2; 
    	String leftS = subS.substring(left, mid);
    	String rightS = subS.substring(mid, right);
    	String leftP = p.substring(left, mid);
    	String rightP = p.substring(mid, right);
    	// update count based on left part
    	if(leftS.hashCode() == leftP.hashCode() && rightS.hashCode() == rightP.hashCode()) {
    		return count;
    	}
    	else if(leftS.hashCode() != leftP.hashCode() && rightS.hashCode() != rightP.hashCode()) {
    		if(count == 0)
    			count = count + 2;
    		else
    			count = count + 1;
    		count = numberOfMismatchesNaive(left, mid, k, count, subS, p);
    		if(count > k) return count;
    		count = numberOfMismatchesNaive(mid, right, k, count, subS, p);
    		if(count > k) return count;
    	}
    	else if(leftS.hashCode() != leftP.hashCode() && rightS.hashCode() == rightP.hashCode()) {
    		if(count == 0)
    			count = 1;
    		count = numberOfMismatchesNaive(left, mid, k, count, subS, p);
    		if(count > k) return count;
    	}
    	else if(leftS.hashCode() == leftP.hashCode() && rightS.hashCode() != rightP.hashCode()) {
    		if(count == 0)
    			count = 1;
//    		System.out.println(mid + "_" + right);
    		count = numberOfMismatchesNaive(mid, right, k, count, subS, p);
    		if(count > k) return count;
    	}
    	return count;
    }

    public void run() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        in.lines().forEach(line -> {
            StringTokenizer tok = new StringTokenizer(line);
            int k = Integer.valueOf(tok.nextToken());
            String s = tok.nextToken();
            String t = tok.nextToken();
            List<Integer> ans = solve(k, s, t);
            out.format("%d ", ans.size());
            out.println(ans.stream()
                    .map(n -> String.valueOf(n))
                    .collect(Collectors.joining(" "))
            );
            // TODO: fix output
            out.flush();
        });
        out.close();
    }

    static public void main(String[] args) {
        new matching_with_mismatches().run();
    }
    
}

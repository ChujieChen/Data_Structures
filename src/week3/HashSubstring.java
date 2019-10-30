package week3;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class HashSubstring {

    private static FastScanner in;
    private static PrintWriter out;
    
    // TODO: set p and x
    private static int prime = 1000007;
    private static int multiplier = 31;	// Java is using x=31 in fact

    public static void main(String[] args) throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        printOccurrences(getOccurrences(readInput()));
        out.close();
    }

    private static Data readInput() throws IOException {
        String pattern = in.next();
        String text = in.next();
        return new Data(pattern, text);
    }

    private static void printOccurrences(List<Integer> ans) throws IOException {
        for (Integer cur : ans) {
            out.print(cur);
            out.print(" ");
        }
    }

    private static List<Integer> getOccurrences(Data input) {
        String s = input.pattern, t = input.text;
        int m = s.length(), n = t.length();
        List<Integer> occurrences = new ArrayList<Integer>();
        /**
    	 * Original naive way
    	 */
        /*
        for (int i = 0; i + m <= n; ++i) {
	    boolean equal = true;
	    for (int j = 0; j < m; ++j) {
		if (s.charAt(j) != t.charAt(i + j)) {
		     equal = false;
 		    break;
		}
	    }
            if (equal)
                occurrences.add(i);
	}	*/
        /**
         * by LCC
         */
        // TODO: fast way -- Rabin-Karp
        int p = prime;
        int x = multiplier;
        int pHash = PolyHash(s, p, x);
        int[] H = PrecomputeHashes(t, m, p, x);
        for(int i = 0; i <= n - m; i++) {
//        	System.out.println(H[i]);
//        	System.out.println(t.substring(i, i + m));
        	if(pHash != H[i])
        		continue;
        	if(t.substring(i, i + m).equals(s))
        		occurrences.add(i);
        }
        return occurrences;
    }
    // TODO: hashFunction
    static int PolyHash(String S, int p, int x) {
    	int hash = 0;
    	for(int i = S.length() - 1; i >= 0; i--) {
//    		hash = (hash * x + S.charAt(i)) % p;
//    		hash = (((hash % p) * (x % p)) % p + S.charAt(i) % p) % p;
    		hash = modp(hash * x + S.charAt(i), p);
    	}
		return hash;
    }
    /**
     * negative numbers mod p
     * @param a
     * @param p
     * @return
     */
    static int modp(int a, int p) {
    	return ((a % p) + p) % p;
    }
    // TODO: the way to improve Rabin-Karp 
    // from O(|T||P|) to O(|T| + |P|)
    /**
     * H[i] = xH[i+1] + (T[i] - T[i+|P|] x^|P|) mod p
     * @param T
     * @param PLength
     * @param p
     * @param x
     * @return H
     */
    static int[] PrecomputeHashes(String T, int PLength, int p, int x) {
    	int[] H = new int[T.length() - PLength + 1];
    	String S = T.substring(T.length() - PLength);
    	H[T.length() - PLength] = PolyHash(S, p, x);
    	// compute x to the power of PLength
    	int y = 1;
    	for(int i = 0; i < PLength; i++)
    		y = modp(y * x, p);
    	for(int i = T.length() - PLength - 1; i >= 0 ; --i)
    		H[i] = modp(x * H[i + 1] + T.charAt(i) - y * T.charAt(i + PLength), p); 
    	return H;
    }
    

    static class Data {
        String pattern;
        String text;
        public Data(String pattern, String text) {
            this.pattern = pattern;
            this.text = text;
        }
    }

    static class FastScanner {
        private BufferedReader reader;
        private StringTokenizer tokenizer;

        public FastScanner() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = null;
        }

        public String next() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}


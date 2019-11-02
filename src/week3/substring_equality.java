package week3;

import java.util.*;
import java.io.*;
import java.math.BigInteger;

public class substring_equality {
	// TODO: set prime, multiplier and h[] array -- by LCC
    private static long prime1 = 1000007;
    private static long prime2 = 1000009;
    private static long multiplier = 31;	// Java is using x=31 in fact
    private static long[] h1;
    private static long[] h2;
    ///////////////////////////////////
	public class Solver {
		private String s;
		public Solver(String s) {
			this.s = s;
			// TODO: get h[] array at the beginning -- by LCC
			h1 = getSmallH(s, multiplier, prime1);
			h2 = getSmallH(s, multiplier, prime2);
		}
		public boolean ask(int a, int b, int l) {
			return s.substring(a, a + l).equals(s.substring(b, b + l));
		}
		// TODO: fast solution -- by LCC
		/**
		 * By LCC
		 * @param a: start index one
		 * @param b: start index two
		 * @param l: length
		 * @return
		 */
		public boolean askFast(int a, int b, int l) {
			boolean isEqual = false;
			BigInteger xBigInteger = new BigInteger(Long.toString(multiplier));
			BigInteger lBigInteger = new BigInteger(Long.toString(l));
			BigInteger p1BigInteger = new BigInteger(Long.toString(prime1));
			long hashForA1 = modP(h1[a + l] - xBigInteger.modPow(lBigInteger, p1BigInteger).intValue() * h1[a], prime1);
			long hashForB1 = modP(h1[b + l] - xBigInteger.modPow(lBigInteger, p1BigInteger).intValue() * h1[b], prime1);
			
			BigInteger p2BigInteger = new BigInteger(Long.toString(prime2));
			long hashForA2 = modP(h2[a + l] - xBigInteger.modPow(lBigInteger, p2BigInteger).intValue() * h2[a], prime2);
			long hashForB2 = modP(h2[b + l] - xBigInteger.modPow(lBigInteger, p2BigInteger).intValue() * h2[b], prime2);
			
			System.out.println(hashForA1 +"\n"+ hashForB1+"\n"+ hashForA2+"\n"+ hashForB2+"\n");
			if(hashForA1 == hashForB1 && hashForA2 == hashForB2) isEqual = true;
			return isEqual;
		}
		// TODO: specific polynomial hash function for this problem -- by LCC
		/**
		 * Note that this polyHash is different from the one in HashSubstring.java
		 * H[t0...t_{m-1}] = \sum_{j=0}^{m-1} t_j x^{m-j-1}
		 * If h[i] = H[s0...s_{i-1}]
		 * We have h[i+1] = h[i]*x + t_i
		 * And H[s_a...s_{a+l-1}] = h[a+l] - x^l * h[a]
		 * @param s
		 * @param x
		 * @return 
		 */
		public long[] getSmallH(String t, long x, long p) {
			int n = t.length();
			long[] h = new long[n + 1];
			h[0] = 0;
			for(int i = 1; i <= n; i++) {
				h[i] = modP(modP(h[i - 1] * x, p) + t.charAt(i - 1), p);
			}
			return h;
		}
		// TODO: advanced mod -- by LCC
		public long modP(long a, long p) {
			return ((a % p) + p) % p;
		}
	}

	public void run() throws IOException {
		FastScanner in = new FastScanner();
		PrintWriter out = new PrintWriter(System.out);
		String s = in.next();
		int q = in.nextInt();
		Solver solver = new Solver(s);
		for (int i = 0; i < q; i++) {
			int a = in.nextInt();
			int b = in.nextInt();
			int l = in.nextInt();
//			out.println(solver.ask(a, b, l) ? "Yes" : "No");
			out.println(solver.askFast(a, b, l) ? "Yes" : "No");
		}
		out.close();
	}

	static public void main(String[] args) throws IOException {
	    new substring_equality().run();
	}

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
}

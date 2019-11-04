package week3;

import java.util.*;

import javafx.scene.shape.Line;
import jdk.nashorn.internal.ir.BreakNode;

import java.io.*;
import java.text.BreakIterator;

public class common_substring {
    public class Answer {
        int i, j, len;
        Answer(int i, int j, int len) {
            this.i = i;
            this.j = j;
            this.len = len;
        }
    }

    public Answer solve(String s, String t) {
        Answer ans = new Answer(0, 0, 0);
        for (int i = 0; i < s.length(); i++)
            for (int j = 0; j < t.length(); j++)
                for (int len = 0; i + len <= s.length() && j + len <= t.length(); len++)
                    if (len > ans.len && s.substring(i, i + len).equals(t.substring(j, j + len)))
                        ans = new Answer(i, j, len);
        return ans;
    }
    public Answer solveFast(String s, String t) {
    	Answer ans = new Answer(0, 0, 0);
    	int left = 0, right = Math.min(s.length(), t.length());
    	while(left <= right) {
//    		System.out.println("l, r = " + left + " " + right);
    		int k = (left + right) / 2;
//    		System.out.println("k = " + k);
    		// map will store {hashValue: index} from s
    		HashMap<Long, Integer> map = new HashMap<>();
    		for(int i = 0; i + k - 1 < s.length(); ++i) {
    			// well. I was supposed to use what I used in substrinng_equality.java
    			// to compute the hashValue, but I think it would be too much work
    			map.put((long) s.substring(i, i + k).hashCode(), i);
    		}
    		boolean found = false;
    		for(int i = 0; i + k - 1 < t.length(); ++i) {
    			long hashFromT = (long) t.substring(i, i + k).hashCode();
    			if(map.containsKey(hashFromT)) {
    				found = true;
    				left = k;
    				ans.i = map.get(hashFromT);
    				ans.j = i;
    				ans.len = k;
    				break;
    			}
    		}
    		if(found) left = k + 1;
    		else right = k - 1;
    	}
    	return ans;
    }

    public void run() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        in.lines().forEach(line -> {
            StringTokenizer tok = new StringTokenizer(line);
            String s = tok.nextToken();
            String t = tok.nextToken();
            Answer ans = solveFast(s, t);
            out.format("%d %d %d\n", ans.i, ans.j, ans.len);
            out.flush();
        });
        // TODO: fix input taker
        out.close();
    }

    static public void main(String[] args) {
        new common_substring().run();
    }
}

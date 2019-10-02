package week2;

import java.io.*;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class JobQueue {
    private int numWorkers;
    private int[] jobs;

    private int[] assignedWorker;
    private long[] startTime;

    private FastScanner in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        new JobQueue().solve();
    }

    private void readData() throws IOException {
        numWorkers = in.nextInt();
        int m = in.nextInt();
        jobs = new int[m];
        for (int i = 0; i < m; ++i) {
            jobs[i] = in.nextInt();
        }
    }

    private void writeResponse() {
        for (int i = 0; i < jobs.length; ++i) {
            out.println(assignedWorker[i] + " " + startTime[i]);
        }
    }

    private void assignJobs() {
        // TODO: replace this code with a faster algorithm.
//        assignedWorker = new int[jobs.length];
//        startTime = new long[jobs.length];
//        long[] nextFreeTime = new long[numWorkers];
//        for (int i = 0; i < jobs.length; i++) {
//            int duration = jobs[i];
//            int bestWorker = 0;
//            for (int j = 0; j < numWorkers; ++j) {
//                if (nextFreeTime[j] < nextFreeTime[bestWorker])
//                    bestWorker = j;
//            }
//            assignedWorker[i] = bestWorker;
//            startTime[i] = nextFreeTime[bestWorker];
//            nextFreeTime[bestWorker] += duration;
//        }
    	
    	// TODO: priority queue of size numWorkers
    	// Default Java Priority Queue is min-heap
    	// we need a Comparator to compare [id, time]'s time
    	// store each job into an array where 0th is thread id, 1st is startTime
    	////////////// Important ////////////
    	assignedWorker = new int[jobs.length];
    	startTime = new long[jobs.length];
    	/////////////////////////////////////
    	PriorityQueue<long[]> pQueue = new PriorityQueue<>(numWorkers, new timeComparator());
    	for(int i = 0; i < numWorkers; i++) {
    		long[] idDefaultTime = {i, 0};
    		pQueue.add(idDefaultTime);
    	}
    	for(int i = 0; i < jobs.length; i++) {
    		long[] idTime = pQueue.poll();
    		assignedWorker[i] = (int) idTime[0];
    		startTime[i] = idTime[1];
    		idTime[1] = idTime[1] + jobs[i];
    		pQueue.offer(idTime);
    	}
    	
    }

    public void solve() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        readData();
        long startTime = System.nanoTime();
        assignJobs();
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
        writeResponse();
        out.close();
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
    
    static class timeComparator implements Comparator<long[]>{

		@Override
		public int compare(long[] o1, long[] o2) {
			// TODO Auto-generated method stub
			// front - back = negative is increasing order
			if(o1[1] != o2[1])
				return (int) (o1[1] - o2[1]);
			else
				return (int) (o1[0] - o2[0]);
		}
    }
    	
}

package week2;

import java.io.*;
import java.util.Arrays;
import java.util.Locale;
import java.util.StringTokenizer;

import com.sun.xml.internal.ws.server.provider.ProviderArgumentsBuilder;

public class MergingTables {
    private final InputReader reader;
    private final OutputWriter writer;

    public MergingTables(InputReader reader, OutputWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public static void main(String[] args) {
        InputReader reader = new InputReader(System.in);
        OutputWriter writer = new OutputWriter(System.out);
        new MergingTables(reader, writer).run();
        writer.writer.flush();
    }

    class Table {
        Table parent;
        int rank;
        int numberOfRows;

        Table(int numberOfRows) {
            this.numberOfRows = numberOfRows;
            rank = 0;
            parent = this;
        }
        Table getParent() {
        	// TODO
            // find super parent and compress path
        	// added by LCC
        	// change all nodes' parent along the tracing path
        	if(this != parent) {
        		// we want to find a parent that both this and this.parent share
        		// it is valid even if this.parent is root
        		parent = parent.getParent();
        	}
        	////////////////
            return parent;
        }
    }

    int maximumNumberOfRows = -1;

    void merge(Table destination, Table source) {
        Table realDestination = destination.getParent();
        Table realSource = source.getParent();
        if (realDestination == realSource) {
            return;
        }
        // TODO
        // merge two components here
        // use rank heuristic
        // update maximumNumberOfRows
        
        // LCC: no matter how they merge, once they are merged, they have same numberOfRows
        if(realDestination.rank > realSource.rank) {
        	realDestination.numberOfRows = realDestination.numberOfRows + realSource.numberOfRows;
        	realSource.numberOfRows = realDestination.numberOfRows;
        	if(realDestination.numberOfRows > maximumNumberOfRows) {
        		maximumNumberOfRows = realDestination.numberOfRows;
        	}
        	realSource.parent = realDestination;
        	
        }
        else {
        	realDestination.numberOfRows = realDestination.numberOfRows + realSource.numberOfRows;
        	realSource.numberOfRows = realDestination.numberOfRows;
        	if(realDestination.numberOfRows > maximumNumberOfRows) {
        		maximumNumberOfRows = realDestination.numberOfRows;
        	}
        	realDestination.parent = realSource;
			// this is important
			if(realDestination.rank == realSource.rank)
				realSource.rank = realSource.rank + 1;
		}
        
    }

    public void run() {
        int n = reader.nextInt();
        int m = reader.nextInt();
        long startTime = System.nanoTime();
        Table[] tables = new Table[n];
        for (int i = 0; i < n; i++) {
            int numberOfRows = reader.nextInt();
            tables[i] = new Table(numberOfRows);
            maximumNumberOfRows = Math.max(maximumNumberOfRows, numberOfRows);
        }
        for (int i = 0; i < m; i++) {
            int destination = reader.nextInt() - 1;
            int source = reader.nextInt() - 1;
            merge(tables[destination], tables[source]);
            writer.printf("%d\n", maximumNumberOfRows);
        }
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


    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }
    }

    static class OutputWriter {
        public PrintWriter writer;

        OutputWriter(OutputStream stream) {
            writer = new PrintWriter(stream);
        }

        public void printf(String format, Object... args) {
            writer.print(String.format(Locale.ENGLISH, format, args));
        }
    }
}

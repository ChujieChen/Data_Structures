package week1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;


class Request {
    public Request(int arrival_time, int process_time) {
        this.arrival_time = arrival_time;
        this.process_time = process_time;
    }

    public int arrival_time;
    public int process_time;
}

class Response {
    public Response(boolean dropped, int start_time) {
        this.dropped = dropped;
        this.start_time = start_time;
    }

    public boolean dropped;
    public int start_time;
}

class Buffer {
	public Buffer(int size) {
        this.size_ = size;
        this.finish_time_ = new ArrayList<Integer>();    // this is the queue provided
    }
    /**
     * 
     * @param request: one single request
     * @return Response(boolean dropped, int start_time)
     */
    public Response Process(Request request) {
    	// TODO
    	// write your code here
    	// check the arrival time of this request
    	// pop out all requests in finish_time_ that are already processed by then
    	int beginTime = -1;
    	int finishTime = -1;
    	while(!finish_time_.isEmpty() && finish_time_.get(0) <= request.arrival_time) {
    		finish_time_.remove(0);
    	}
    	// if still no space
    	if(finish_time_.size() + 1 > size_) {
//    		System.out.println("Arrival time " + request.arrival_time + " cannot get in");
    		return new Response(true, beginTime);
    	}
    	// if there is space, two cases: 
    	// 1. finish_time_ is empty, then the new finish_time is arrival_time + process_time
    	// 2. if there are requests inside, the new finish_time is the last_finish_time + process_time
    	if(finish_time_.isEmpty()) {
    		beginTime = request.arrival_time;
    		finishTime = beginTime + request.process_time;
    		finish_time_.add(finishTime);
    	}
    	else {
    		beginTime = finish_time_.get(finish_time_.size()-1);
    		finishTime = beginTime + request.process_time;
    		finish_time_.add(finishTime);
    	}
        return new Response(false, beginTime);
    }

    private int size_;
    private ArrayList<Integer> finish_time_;
}

class process_packages {
    private static ArrayList<Request> ReadQueries(Scanner scanner) throws IOException {
        int requests_count = scanner.nextInt();
        ArrayList<Request> requests = new ArrayList<Request>();
        for (int i = 0; i < requests_count; ++i) {
            int arrival_time = scanner.nextInt();
            int process_time = scanner.nextInt();
            requests.add(new Request(arrival_time, process_time));
        }
        return requests;
    }

    private static ArrayList<Response> ProcessRequests(ArrayList<Request> requests, Buffer buffer) {
        ArrayList<Response> responses = new ArrayList<Response>();
        for (int i = 0; i < requests.size(); ++i) {
            responses.add(buffer.Process(requests.get(i)));
        }
        return responses;
    }

    private static void PrintResponses(ArrayList<Response> responses) {
        for (int i = 0; i < responses.size(); ++i) {
            Response response = responses.get(i);
            if (response.dropped) {
                System.out.println(-1);
            } else {
                System.out.println(response.start_time);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        int buffer_max_size = scanner.nextInt();
        Buffer buffer = new Buffer(buffer_max_size);

        ArrayList<Request> requests = ReadQueries(scanner);
        
        long startTime = System.nanoTime();
        
        ArrayList<Response> responses = ProcessRequests(requests, buffer);
        PrintResponses(responses);
        
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

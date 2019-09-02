package week1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Stack;

class Bracket {
    Bracket(char type, int position) {
        this.type = type;
        this.position = position;
    }

    boolean Match(char c) {
        if (this.type == '[' && c == ']')
            return true;
        if (this.type == '{' && c == '}')
            return true;
        if (this.type == '(' && c == ')')
            return true;
        return false;
    }

    char type;
    int position;
}

class check_brackets {
    public static void main(String[] args) throws IOException {
        InputStreamReader input_stream = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input_stream);
        String text = reader.readLine();

        Stack<Bracket> opening_brackets_stack = new Stack<Bracket>();
        for (int position = 0; position < text.length(); ++position) {
            char next = text.charAt(position);

            if (next == '(' || next == '[' || next == '{') {
                // Process opening bracket, write your code here
            	Bracket openBracket = new Bracket(next, position + 1);
            	opening_brackets_stack.add(openBracket);
            }

            if (next == ')' || next == ']' || next == '}') {
                // Process closing bracket, write your code here
            	Bracket closeBracket = new Bracket(next, position + 1);
            	if(!opening_brackets_stack.pop().Match(next)) {
            		System.out.print(closeBracket.position);
            		return;
            	}
            }
        }

        // Printing answer, write your code here
        if(!opening_brackets_stack.empty()) {
        	System.out.print(opening_brackets_stack.pop().position);
        	return;
        }
        System.out.println("Success");
    }
}

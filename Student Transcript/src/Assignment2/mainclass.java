package Assignment2;

import java.io.IOException;

public class mainclass {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Transcript in = new Transcript("Input.txt","output.txt");
		in.printTranscript(in.buildStudentArray());
		
	}

}

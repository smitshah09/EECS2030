package Assignment2;

import java.util.ArrayList;

public class testCode {

	public static void main(String[] args) {
		Transcript ts = new Transcript("input.txt", "transcript.txt");
		ts.printTranscript(ts.buildStudentArray());
	}

}

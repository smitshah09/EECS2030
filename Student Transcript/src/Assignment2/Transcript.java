package Assignment2;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

/* PLEASE DO NOT MODIFY A SINGLE STATEMENT IN THE TEXT BELOW.
READ THE FOLLOWING CAREFULLY AND FILL IN THE GAPS

I hereby declare that all the work that was required to 
solve the following problem including designing the algorithms
and writing the code below, is solely my own and that I received
no help in creating this solution and I have not discussed my solution 
with anybody. I affirm that I have read and understood
the Senate Policy on Academic honesty at 
https://secretariat-policies.info.yorku.ca/policies/academic-honesty-senate-policy-on/
and I am well aware of the seriousness of the matter and the penalties that I will face as a 
result of committing pla+giarism in this assignment.

BY FILLING THE GAPS,YOU ARE SIGNING THE ABOVE STATEMENTS.

Full Name: SMIT SHAH
Student Number: 217478710
Course Section: LAB 2 SECTION E
 */

/**
 * This class generates a transcript for each student, whose information is in
 * the text file.
 * 
 *
 */

public class Transcript {
	private ArrayList<Object> grade = new ArrayList<Object>();
	private File inputFile;
	private String outputFile;

	/**
	 * This the the constructor for Transcript class that initializes its instance
	 * variables and call readFie private method to read the file and construct
	 * this.grade.
	 * 
	 * @param inFile  is the name of the input file.
	 * @param outFile is the name of the output file.
	 */
	public Transcript(String inFile, String outFile) {
		inputFile = new File(inFile);
		outputFile = outFile;
		grade = new ArrayList<Object>();
		this.readFile();
	}// end of Transcript constructor

	/**
	 * This method reads a text file and add each line as an entry of grade
	 * ArrayList.
	 * 
	 * @exception It throws FileNotFoundException if the file is not found.
	 */
	private void readFile() {
		Scanner sc = null;
		try {
			sc = new Scanner(inputFile);
			while (sc.hasNextLine()) {
				grade.add(sc.nextLine());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			sc.close();
		}
	} // end of readFile

	/**
	 * @return ArrayList<Student>
	 */
	public ArrayList<Student> buildStudentArray() {
		ArrayList<Student> result = new ArrayList<Student>(); // creates an object result to return the result at the
																// end
		ArrayList<String> id = new ArrayList<String>(); // creates an object id

		// for every object in the grade array
		for (Object i : this.grade) {
			int k = 0;
			String studentID = "", name = "", course = "";
			String s = (String) i; // converting object i into string
			String credit = "";
			for (int j = 0; j < s.length(); j++) // to get the course id
			{
				if (s.charAt(j) == ',') {
					k++;
				}
				if (k == 0 && s.charAt(j) != ',') {
					course = course + s.charAt(j);
				}

				if (k == 2 && s.charAt(j) != ',') {
					studentID = studentID + s.charAt(j);
				}

				if (k == 1 && s.charAt(j) != ',') {
					credit = credit + s.charAt(j);
				}
				if (k > 3) {
					break;
				}
			}

			if (id.contains(studentID))// if its an old student
			{
				Student student = result.get(id.indexOf(studentID));
				student.addCourse(new Course(course, getAssessmentlist(s), Double.parseDouble(credit)));
				student.addGrade(this.getGradeList(s), this.getWeightList(s));
			} else {
				for (int j = s.length() - 1; j >= 0; j--) // getting the name of the new student
				{
					if (s.charAt(j) == ',') {
						break;
					} else {
						name = s.charAt(j) + name;
					}
				}
				ArrayList<Course> c = new ArrayList<Course>(); // adds a new course
				c.add(new Course(course, getAssessmentlist(s), Double.parseDouble(credit)));
				Student student = new Student(studentID, name, c); // creating new student object
				student.addGrade(this.getGradeList(s), this.getWeightList(s)); // adding grades for the new student
																				// object created above
				result.add(student); // adding the student into the result
				id.add(studentID);
			}
		}
		return result;
	}

	//
	/**
	 * A helper method creating the list of the assignments of a specific student
	 * 
	 * @param String s
	 * @return ArrayList<Assessment>
	 */
	private ArrayList<Assessment> getAssessmentlist(String s) {
		ArrayList<Assessment> result = new ArrayList<Assessment>();
		int k = 0, i = 1;
		char t = ' ';
		String weight = "";
		while (s.contains(",") && s.length() > 8) {
			if (s.charAt(i) == ',') {
				k++;
			}
			if (k > 2 && s.charAt(i) != ',') {
				if (s.charAt(i) == 'P' || s.charAt(i) == 'E') {
					t = s.charAt(i);
					if (s.charAt(2) != '(') {
						weight = weight + s.charAt(2);
					}
					if (s.charAt(3) != '(') {
						weight = weight + s.charAt(3);
					}
					if (s.charAt(4) != '(') {
						weight = weight + s.charAt(4);
					}
					result.add(Assessment.getInstance(t, Integer.parseInt(weight)));
					weight = "";
				}
			}
			s = s.substring(i);
		}
		return result;
	}

	/**
	 * A helper method to get the list of grades of the assignments and tests for a
	 * specific student
	 * 
	 * @param String s
	 * @return ArrayList<Double>
	 */
	private ArrayList<Double> getGradeList(String s) {
		ArrayList<Double> result = new ArrayList<Double>();
		int i = 1;
		boolean in = false;
		String grade = "";
		while (s.contains(",") && s.length() > 1) {
			if (s.charAt(i) == '(') {
				in = true;
			}
			if (s.charAt(i) == ')') {
				in = false;
				result.add(Double.parseDouble(grade));
				grade = "";
			}
			if (in && s.charAt(i) != '(') {
				grade = grade + s.charAt(i);
			}
			s = s.substring(i);
		}
		return result;
	}

	/**
	 * A helper method that gets the weight of the assessments
	 * 
	 * @param String s
	 * @return ArrayList<Integer>
	 */
	private ArrayList<Integer> getWeightList(String s) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		int i = 1, k = 0;
		String weight = "";
		while (s.contains(",") && s.length() > 8) {
			if (s.charAt(i) == ',') {
				k++;
			}
			if (k > 2 && s.charAt(i) != ',') {
				if (s.charAt(i) == 'P' || s.charAt(i) == 'E') {
					if (s.charAt(2) != '(') {
						weight = weight + s.charAt(2);
					}
					if (s.charAt(3) != '(') {
						weight = weight + s.charAt(3);
					}
					if (s.charAt(4) != '(') {
						weight = weight + s.charAt(4);
					}
					result.add(Integer.parseInt(weight));
					weight = "";
				}
			}
			s = s.substring(i);
		}
		return result;
	}

	// To print/generate the transcript of the student
	public void printTranscript(ArrayList<Student> s) throws IOException {
		FileWriter myWriter = new FileWriter(this.outputFile);
		for (Student i : s) {
			myWriter.write(i.getName() + "\t" + i.getStudentID() + "\n");
			myWriter.write("--------------------\n");
			for (int j = 0; j < i.getCourseTaken().size(); j++) {
				myWriter.write(i.getCourseTaken().get(j).getCode() + "\t" + i.getFinalGrade().get(j) + "\n");
			}
			myWriter.write("--------------------\n");
			myWriter.write("GPA: " + i.weightedGPA() + "\n");
			myWriter.write("\n");

		}
		myWriter.close();
	}
} // end of Transcript

//===========================================================================================================================================================================================================================

class Student {

	private String studentID;
	private String name;
	private ArrayList<Course> courseTaken;
	private ArrayList<Double> finalGrade;

	public Student() {
		this.courseTaken = new ArrayList<Course>();
		this.finalGrade = new ArrayList<Double>();
	}

	// Student constructor that takes the parameter stID = student id, name =
	// student name, courseTaken = list of courses taken by a student
	public Student(String stID, String name, ArrayList<Course> courseTaken) {
		this.studentID = stID;
		this.name = name;
		this.finalGrade = new ArrayList<Double>();
		this.courseTaken = new ArrayList<Course>();
		this.courseTaken.addAll(courseTaken);
	}

	public void addCourse(Course course) { // when a student adds/takes a new course
		this.courseTaken.add(course);
	}

	public void addGrade(ArrayList<Double> grade, ArrayList<Integer> weight) {
		double gradesum = 0;
		int weightsum = 0;
		double finalgrade = 0;
		for (double i : grade) {
			gradesum += i; // gets the sum of grades
		}
		gradesum = gradesum / 100;

		for (int i : weight) {
			weightsum += i; // gets the sum of weights
		}

		if (weightsum != 100) {
			throw new InvalidTotalException(); // throws exception when the total of the weight is not equal to 100
		} else {
			for (int i = 0; i < weight.size(); i++) {
				finalgrade += weight.get(i) * (grade.get(i) / 100);
				if (finalgrade > 100) {
					throw new InvalidTotalException(); // throws exception when the total of the grades is greater than
														// 100
				}
			}
			finalgrade = Math.round(finalgrade * 10) / 10.0; // to calculate the final grade of the student
			this.finalGrade.add(finalgrade);
		}
	}

	public double weightedGPA() {
		double gradepoint, courseCredit, average = 0, csum = 0;

		for (int i = 0; i < this.courseTaken.size() && i < this.finalGrade.size(); i++) {
			courseCredit = this.courseTaken.get(i).getCredit();
			csum += courseCredit;
			double f = this.finalGrade.get(i);
			if (f >= 90 && f <= 100) { // to get which gradepoint has the student earned (conditions for GPA calculation)
				gradepoint = 9;
			} else if (f >= 80 && f < 90) {
				gradepoint = 8;
			} else if (f >= 75 && f < 80) {
				gradepoint = 7;
			} else if (f >= 70 && f < 75) {
				gradepoint = 6;
			} else if (f >= 65 && f < 70) {
				gradepoint = 5;
			} else if (f >= 60 && f < 65) {
				gradepoint = 4;
			} else if (f >= 55 && f < 60) {
				gradepoint = 3;
			} else if (f >= 50 && f < 55) {
				gradepoint = 2;
			} else if (f >= 47 && f < 50) {
				gradepoint = 1;
			} else {
				gradepoint = 0;
			}
			average += courseCredit * gradepoint; // calculates the average of the student's grades
		}

		return Math.round((average / csum) * 10) / 10.0; // calculate the GPA of the student to one decimal place
	}

	/**
	 * @return the studentID
	 */
	public String getStudentID() {
		return studentID;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the courseTaken
	 */
	public ArrayList<Course> getCourseTaken() {
		return courseTaken;
	}

	/**
	 * @return the finalGrade
	 */
	public ArrayList<Double> getFinalGrade() {
		return finalGrade;
	}
}

//=========================================================================================================================================================================================================================================

class Course {
	private String code;
	private ArrayList<Assessment> assessment;
	private double credit;

	public Course() {
		this.code = "";
		this.assessment = new ArrayList<Assessment>();
		this.credit = 0.0;
	}

	/**
	 * @param String code
	 * @param ArrayList<Assessment> assessment
	 * @param double credit
	 */
	public Course(String code, ArrayList<Assessment> assessment, double credit) {
		this.code = code;
		this.assessment = new ArrayList<Assessment>();
		this.assessment.addAll(assessment);
		this.credit = credit;
	}

	public Course(Course course) {
		this.code = course.getCode();
		this.assessment = new ArrayList<Assessment>();
		this.assessment.addAll(course.getAssessment());
		this.credit = course.getCredit();
	}

	@Override
	// over-riding the equals method to check if this course is equal to other
	// course
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (obj == null) {
			return false;
		}

		if (this.getClass() != obj.getClass()) {
			return false;
		}

		Course other = (Course) obj; // converting object into course
		boolean ass = true;
		for (int i = 0; i < this.assessment.size() && i < other.getAssessment().size(); i++) {
			ass = ass && this.assessment.get(i).equals(other.getAssessment().get(i));
		}

		if (other.getCode() == this.code && this.credit == other.getCredit() && ass) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @return the code
	 */
	protected String getCode() {
		return code;
	}

	/**
	 * @return the assessment
	 */
	protected ArrayList<Assessment> getAssessment() {
		return assessment;
	}

	/**
	 * @return the credit
	 */
	protected double getCredit() {
		return credit;
	}

}

//==================================================================================================================================================================================================================================================================

class Assessment {
	private char type;
	private int weight;

	private Assessment() {
		this.weight = 0;
	}

	/**
	 * @param char type
	 * @param int  weight
	 */
	private Assessment(char type, int weight) {
		this.type = type;
		this.weight = weight;
	}

	/**
	 * @param type
	 * @param weight
	 * @return Assessment
	 */
	public static Assessment getInstance(char type, int weight) {
		return new Assessment(type, weight);
	}

	@Override
	// over-riding the equals method to check if this assessment is equal to other
	// assessment
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (this.getClass() != obj.getClass()) {
			return false;
		}

		Assessment other = (Assessment) obj; // converting object into assessment

		if (other.getType() == this.type && other.getWeight() == this.weight) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @return the type
	 */
	public char getType() {
		return type;
	}

	/**
	 * @return the weight
	 */
	public int getWeight() {
		return weight;
	}
}

//========================================================================================================================================================================================================================================================

// Exception handling
class InvalidTotalException extends IllegalArgumentException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3949147535695170701L;

	public InvalidTotalException() {
		super();
	}

}

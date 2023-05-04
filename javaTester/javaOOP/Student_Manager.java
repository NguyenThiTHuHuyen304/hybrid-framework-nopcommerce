package javaOOP;

public class Student_Manager {

	private int studentID;
	private String studentName;
	private float knowledgePoint;
	private float practicePoint;

	public Student_Manager(int studentID, String studentName, float knowledgePoint, float practicePoint) {
		this.studentID = studentID;
		this.studentName = studentName;
		this.knowledgePoint = knowledgePoint;
		this.practicePoint = practicePoint;
	}

	public Student_Manager() {
	}

	private int getStudentID() {
		return studentID;
	}

	private void setStudentID(int studentID) {
		this.studentID = studentID;
	}

	private String getStudentName() {
		return studentName;
	}

	private void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	private float getKnowledgePoint() {
		return knowledgePoint;
	}

	private void setKnowledgePoint(float knowledgePoint) {
		this.knowledgePoint = knowledgePoint;
	}

	private float getPracticePoint() {
		return practicePoint;
	}

	private void setPracticePoint(float practicePoint) {
		this.practicePoint = practicePoint;
	}

	public float getAveragePoint() {
		return (this.knowledgePoint + (this.practicePoint * 2)) / 3;
	}

	public void showStudentInfor() {
		System.out.println("**************************************");
		System.out.println("Student ID= " + getStudentID());
		System.out.println("Student Name= " + getStudentName());
		System.out.println("Student Knowledge Point= " + getKnowledgePoint());
		System.out.println("Student Practice Point= " + getPracticePoint());
	}

	public static void main(String[] args) {
		Student_Manager firstStudent = new Student_Manager(2005206, "John Terry", 8.0f, 7.8f);
		Student_Manager sencondStudent = new Student_Manager();
		sencondStudent.setStudentID(2005207);
		sencondStudent.setStudentName("Michael Jackson");
		sencondStudent.setKnowledgePoint(9.5f);
		sencondStudent.setPracticePoint(8.5f);

		firstStudent.showStudentInfor();
		sencondStudent.showStudentInfor();
	}
}

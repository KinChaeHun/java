package basic;
public class UpcastingEx{
	public static void main(String[] args) {
		Person p;
		Student s = new Student("홍길동");
		p = s;
	}
}

class Person {
	String name;
	String id;
	public Person(String name) {
		this.name = name;
	}
}
class Student extends Person {
	String grade;
	String department;
	public Student(String name) {
		super(name);
	}
}

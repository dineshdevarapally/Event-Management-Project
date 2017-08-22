package Algo;

public class Person {
	private int index;
	private int score;

	public Person(Person person) {
		this.index = person.index;
		this.score = person.score;
	}

	public Person() {
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Person(int index) {
		this.index = index;
	}

	public Person(int index, int score) {
		this.index = index;
		this.score = score;
	}
}
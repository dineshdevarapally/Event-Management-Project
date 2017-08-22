package Algo;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Table implements Comparable<Table> {

	private int capacity;
	private int score;
	private List<Person> persons = new LinkedList<>();

	public Table(Table table) {
		this.capacity = table.capacity;
		this.score = table.score;
		for (Person p : table.getPersons()) {
			this.persons.add(new Person(p));
		}
	}

	public Table() {
		this.score = 0;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public List<Person> getPersons() {
		return persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

	public int tableStrength() {
		return persons.size();
	}

	public int emptyChairs() {
		return capacity - tableStrength();
	}

	public boolean isPersonPresent(Integer index) {
		return persons.stream().anyMatch(person -> person.getIndex() == index);
	}

	public Person getPerson(int index) {

		if (isPersonPresent(index))
			return persons.stream().filter(person -> person.getIndex() == index).collect(Collectors.toList()).get(0);
		else {
			// System.out.println("Person is not in this table");
			return null;
		}
	}

	public void removePerson(Person p) {
		persons.remove(p);
	}

	public boolean isFull() {
		return persons.size() == capacity;
	}
	
	public boolean isEmpty() {
		return persons.isEmpty();
	}

	@Override
	public int compareTo(Table arg0) {
		return -1 * Integer.compare(this.getScore(), arg0.getScore());
	}
}

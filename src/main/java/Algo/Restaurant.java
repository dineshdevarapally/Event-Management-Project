package Algo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Restaurant implements Comparable<Restaurant> {

	private int score;
	private List<Table> tables = new ArrayList<>();

	public Restaurant(Restaurant restaurant) {
		this.score = restaurant.score;
		for (Table table : restaurant.getTables()) {
			this.tables.add(new Table(table));
		}
	}

	public Restaurant() {
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public List<Table> getTables() {
		return tables;
	}

	public void setTables(List<Table> tables) {
		this.tables = tables;
	}

	public int emptySeats() {
		return tables.stream().mapToInt(Table::emptyChairs).sum();
	}

	public Table getLastTable() {
		return tables.get(tables.size() - 1);
	}

	public void printRestaurant() {
		int i = 0;
		for (Table table : tables) {
			System.out.println(++i);
			System.out.print("Persons: ");
			for (Person person : table.getPersons()) {
				System.out.print(person.getIndex() + " ");
			}
			System.out.println();
			System.out.println(table.getScore());
		}
		System.out.println(this.score);
	}

	public void generateScore(RuleMap ruleMap) {
		this.score = 0;
		for (Table table : tables) {
			table.setScore(0);
			generateTableScore(ruleMap, table);
		}
		for (Table table : tables) {
			this.score += table.getScore();
		}
	}

	public static void generateTableScore(RuleMap ruleMap, Table table) {
		for (Person p : table.getPersons()) {
			int pScore = 0;
			Condition condition = ruleMap.getRules().get(p.getIndex());
			for (Person p2 : table.getPersons()) {
				if (p2 != p) {
					if (condition.getDesiredNeighbours().contains(p2.getIndex())) {
						table.setScore(table.getScore() + 10);
						pScore += 10;
					} else if (condition.getUndesiredNeightbours().contains(p2.getIndex())) {
						table.setScore(table.getScore() - 10);
						pScore -= 10;
					}
				}
			}
			p.setScore(pScore);
		}
	}

	public void removePerson(Person p) {
		for (Table table : tables) {
			if (table.isPersonPresent(p.getIndex())) {
				table.removePerson(getPesonOfIndex(p.getIndex()));
			}
		}
	}

	public Person getPesonOfIndex(int index) {
		for (Table table : tables) {
			for (Person p : table.getPersons()) {
				if (p.getIndex() == index) {
					return p;
				}
			}
		}
		return null;
	}

	public void removeEmptyTables() {
		Iterator<Table> iter = tables.iterator();

		while (iter.hasNext()) {
			Table table = iter.next();

			if (table.isEmpty())
				iter.remove();
		}
	}

	@Override
	public int compareTo(Restaurant o) {
		return -1 * Integer.compare(getScore(), o.getScore());
	}

}
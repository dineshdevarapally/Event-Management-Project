package Algo;

public class Crossover {

	public static Restaurant crossover(Restaurant x, Restaurant y, RuleMap ruleMap) {
		if (y.getScore() > x.getScore()) {
			Restaurant tmp = x;
			x = y;
			y = tmp;
		}
		Restaurant child = new Restaurant(x);
		child.getTables().sort(null);

		int listSize = child.getTables().size();
		for (int i = listSize / 2; i < listSize; i++) {
			child.getTables().remove(child.getTables().size() - 1);
		}

		Restaurant copyY = new Restaurant(y);
		for (Table table : child.getTables()) {
			for (Person p : table.getPersons()) {
				copyY.removePerson(p);
			}
		}
		child.generateScore(ruleMap);
		copyY.removeEmptyTables();
		copyY.generateScore(ruleMap);
		combineTables(copyY, ruleMap);
		for (Table table : copyY.getTables()) {
			if (!table.isEmpty()) {
				child.getTables().add(new Table(table));
			}
		}
		combineTables(child, ruleMap);
		child.generateScore(ruleMap);
		return child;
	}

	public static Restaurant combineTables(Restaurant restaurant, RuleMap ruleMap) {

		for (int i = 0; i < restaurant.getTables().size(); i++) {
			for (int j = i + 1; j < restaurant.getTables().size(); j++) {

				if (restaurant.getTables().get(i).getPersons().size()
						+ restaurant.getTables().get(j).getPersons().size() <= restaurant.getTables().get(i)
								.getCapacity()) {
					Table table = new Table(restaurant.getTables().get(i));

					for (Person p : restaurant.getTables().get(j).getPersons()) {
						table.getPersons().add(new Person(p));
						Restaurant.generateTableScore(ruleMap, table);
						if (table.getScore() >= restaurant.getTables().get(i).getScore()
								|| table.getScore() >= restaurant.getTables().get(j).getScore()) {
							restaurant.getTables().remove(j);
							restaurant.getTables().remove(i);
							restaurant.getTables().add(table);
							i--;
							if(i == -1) {
								i=0;
							}
							break;
						}
					}
				}
			}

		}

		return null;
	}
}

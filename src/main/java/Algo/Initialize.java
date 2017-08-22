package Algo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import Entities.Event;
import Entities.Rule;

public class Initialize {

	List<Restaurant> initialGeneration = new ArrayList<>();
	Restaurant strongRestaurant = new Restaurant(); 
	
	public Restaurant getStrongRestaurant() {
		return strongRestaurant;
	}

	public Restaurant generateNode(RuleMap ruleMap, int key, int capacity, int maxScore) {
		Restaurant restaurant = new Restaurant();
		int numberOfPersons = ruleMap.getRules().keySet().size();
		for (int j = 1; j < numberOfPersons + 1; j++) {
			if (restaurant.getTables().isEmpty()) {
				addPersonToNewTable(restaurant, key, capacity);
			} else {
				if (restaurant.getLastTable().isFull()) {
					addPersonToNewTable(restaurant, key, capacity);
				} else {
					Table lastTable = restaurant.getLastTable();
					boolean enemy = false;
					for (Person person : lastTable.getPersons()) {
						if (ruleMap.getRules().get(person.getIndex()).getUndesiredNeightbours().contains(key)) {
							enemy = true;
							break;
						}
					}
					if (enemy) {
						addPersonToNewTable(restaurant, key, capacity);
					} else {
						lastTable.getPersons().add(new Person(key));
					}

				}

			}
			key = key % numberOfPersons;
			key++;
		}
		restaurant.generateScore(ruleMap);
		return restaurant;
	}

	private void addPersonToNewTable(Restaurant restaurant, int key, int capacity) {
		Table table = new Table();
		table.setCapacity(capacity);
		table.getPersons().add(new Person(key));
		restaurant.getTables().add(table);
	}

	public Initialize(Event event, int nGen) {
		RuleMap ruleMap = new RuleMap();
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("First");
		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		Query query = entitymanager.createNamedQuery("get rules with event id");
		query.setParameter("eventId", event.getEventId());
		@SuppressWarnings("unchecked")
		List<Rule> list = (List<Rule>) query.getResultList();
		int maxScore = 0;
		for (Rule rule : list) {
			if (!ruleMap.getRules().containsKey(rule.getMainGuest().getEventGuestNumber())) {
				Condition condition = new Condition();
				ruleMap.getRules().put(rule.getMainGuest().getEventGuestNumber(), condition);
			}
			if (rule.getScore() == 10) {
				maxScore += 10;
				ruleMap.getRules().get(rule.getMainGuest().getEventGuestNumber()).getDesiredNeighbours()
						.add(rule.getSubGuest().getEventGuestNumber());
			} else {
				ruleMap.getRules().get(rule.getMainGuest().getEventGuestNumber()).getUndesiredNeightbours()
						.add(rule.getSubGuest().getEventGuestNumber());
			}
		}

		for (Integer i : ruleMap.getRules().keySet()) {
			initialGeneration.add(generateNode(ruleMap, i, event.getnSeats() - event.getEmpytSeats(), maxScore));
		}
		List<Restaurant> currentGeneration = initialGeneration;
		int currentMax = -100000;
		for(int i=0;i<nGen;i++) {
			currentGeneration.sort(null);
			if(currentMax < currentGeneration.get(0).getScore()) {
				currentMax = currentGeneration.get(0).getScore();
				strongRestaurant = new Restaurant(currentGeneration.get(0));
			}
			if(currentMax == maxScore) {
				break;
			}
			currentGeneration = createNextGeneration(initialGeneration, ruleMap);
		}
//		strongRestaurant.printRestaurant();
	}

	private List<Restaurant> createNextGeneration(List<Restaurant> parentGeneration, RuleMap ruleMap) {
		parentGeneration.sort(null);
		List<Restaurant> generation = new ArrayList<>();

		for (int i = 0; i < parentGeneration.size(); i++) {
			for (int j = i + 1; j < parentGeneration.size(); j++) {
				if (generation.size() <= parentGeneration.size()) {
					generation.add(Crossover.crossover(parentGeneration.get(i), parentGeneration.get(j), ruleMap));
				}
			}
		}
		return generation;
	}
}
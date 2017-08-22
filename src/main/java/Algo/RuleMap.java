package Algo;

import java.util.HashMap;
import java.util.Map;

public class RuleMap {
	private Map<Integer, Condition> rules = new HashMap<>();
	public Map<Integer, Condition> getRules() {
		return rules;
	}
	public void setRules(Map<Integer, Condition> rules) {
		this.rules = rules;
	}
}
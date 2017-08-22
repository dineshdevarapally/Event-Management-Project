package Algo;

import java.util.LinkedList;
import java.util.List;

public class Condition {

	private List<Integer> desiredNeighbours = new LinkedList<>();
	private List<Integer> undesiredNeightbours = new LinkedList<>();
	public List<Integer> getDesiredNeighbours() {
		return desiredNeighbours;
	}
	public void setDesiredNeighbours(List<Integer> desiredNeighbours) {
		this.desiredNeighbours = desiredNeighbours;
	}
	public List<Integer> getUndesiredNeightbours() {
		return undesiredNeightbours;
	}
	public void setUndesiredNeightbours(List<Integer> undesiredNeightbours) {
		this.undesiredNeightbours = undesiredNeightbours;
	}
}




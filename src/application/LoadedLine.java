package application;

import java.util.ArrayList;

class LoadedLine {
	private int level;
	private String type;
	private String name;
	ArrayList<Integer> calls;
	ArrayList<Integer> callings;
	ArrayList<Integer> accesses;

	public LoadedLine(int level, String type, String name, ArrayList<Integer> calls, ArrayList<Integer> callings, ArrayList<Integer> accesses) {
		this.level = level;
		this.type = type;
		this.name = name;
		this.calls = calls;
		this.callings = callings;
		this.accesses = accesses;
	}
	
	public int getLevel() {
		return this.level;
	}

	public String getType() {
		return this.type;
	}
	
	public String getName() {
		return this.name;
	}
	
	public ArrayList<Integer> getCalls() {
		return this.calls;
	}
	
	public ArrayList<Integer> getCallings() {
		return this.callings;
	}

	public ArrayList<Integer> getAccesses() {
		return this.accesses;
	}
}
package model;

public class Team {
	private String name;
	private int id;
	private static int idGeneratoe = 100;
	private boolean hasWin;
	
	public Team(String name) {
		this.id = idGeneratoe++;
		this.name = new String(name);
		
	}

	public boolean isHasWin() {
		return hasWin;
	}

	public void setHasWin(boolean hasWin) {
		this.hasWin = hasWin;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Team [name=" + name + ", id=" + id + ", hasWin=" + hasWin + "]";
	}
	
}

package survive.elements;

public class Team {
	
	private String name;
	private int id;
	private int score;
	
	public Team(String name, int id) {
		super();
		this.name = name;
		this.id = id;
		setScore(0);
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public void increaseScore() {
		this.score++;
	}
	public String getName() {
		return name;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Team other = (Team) obj;
		if (id != other.id)
			return false;
		return true;
	}

}

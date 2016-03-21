package survive.elements;

public class Cell {
	private float posX, posY;
	private int teamId;
	
	public Cell(int column, int row) {
		this.teamId = -1;
		this.posX = (.5f + column) * Game.CELL_SIZE;
		this.posY = (.5f + row) * Game.CELL_SIZE;
	}

	public float getPosX() {
		return posX;
	}

	public void setPosX(float posX) {
		this.posX = posX;
	}

	public float getPosY() {
		return posY;
	}

	public void setPosY(float posY) {
		this.posY = posY;
	}

	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int team) {
		this.teamId = team;
	}
}

package survive.elements;

public class Unit {
	private float posX, posY, targetX, targetY, speed;
	private int teamId;
	private int id;
	private long lastActivityTime;
	
	public Unit(float posX, float posY, float speed, int teamId, int id) {
		setPosX(posX);
		setPosY(posY);
		setTargetX(posX);
		setTargetY(posY);
		setSpeed(speed);
		setTeamId(teamId);
		this.id = id;
		updateLastActivityTime();		
	}
	public void updateLastActivityTime() {
		lastActivityTime = System.currentTimeMillis();
	}
	public boolean checkInactivity() {
		if(System.currentTimeMillis() - lastActivityTime > Game.MAX_INACTIVITY) {
			return true;
		}
		return false;
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
	public float getTargetX() {
		return targetX;
	}
	public void setTargetX(float targetX) {
		this.targetX = targetX;
	}
	public float getTargetY() {
		return targetY;
	}
	public void setTargetY(float targetY) {
		this.targetY = targetY;
	}
	public float getSpeed() {
		return speed;
	}
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	public int getTeamId() {
		return teamId;
	}
	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}
	public int getId() {
		return this.id;
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
		Unit other = (Unit) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
}

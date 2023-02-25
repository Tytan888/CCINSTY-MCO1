
public class Tile implements Comparable<Tile> {

	public static final int WALL = 0;
	public static final int FREE = 1;
	public static final int START = 2;
	public static final int GOAL = 3;

	public static final int UNEXPLORED = 0;
	public static final int FRONTIER = 1;
	public static final int EXPLORED = 2;

	private int[] coordinate;
	private int type;
	private int status;
	private int heuristic;
	private Tile parent;
	private int priority;
	private int cost;

	public Tile(int type, int status, int row, int col) {
		this.type = type;
		this.coordinate = new int[2];
		this.coordinate[0] = row;
		this.coordinate[1] = col;
		this.heuristic = Integer.MAX_VALUE;
	}

	public void caclculateHeuristic(Tile goalTile) {
		this.heuristic = Math.abs(this.coordinate[0] - goalTile.coordinate[0])
				+ Math.abs(this.coordinate[1] - goalTile.coordinate[1]);
	}

	public boolean isWall() {
		if (this.type == Tile.WALL) {
			return true;
		}
		return false;
	}

	public int getStatus() {
		return status;
	}

	public int getType() {
		return type;
	}

	public int getPriority() {
		return this.priority;
	}

	public int getCost() {
		return this.cost;
	}

	public int getHeuristic() {
		return heuristic;
	}

	public int[] getCoordinate() {
		return this.coordinate;
	}

	public Tile getParent() {
		return this.parent;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public void setParent(Tile parent) {
		this.parent = parent;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public int compareTo(Tile t) {
		if (this.priority > t.getPriority()) {
			return 1;
		}

		else if (this.priority < t.getPriority()) {
			return -1;
		}

		else
			return 0;
	}
}

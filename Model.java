
import java.io.File;
import java.io.FileNotFoundException;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Model {

	private Tile maze[][];
	private int[] goal;
	private int[] start;
	private Scanner scan;
	private int size;
	private boolean first;
	private PriorityQueue<Tile> frontier;
	private int count;

	public Model() throws FileNotFoundException {
		// method call for file accessing get the N first
		// in method call do board = new Tile[N][N]
		// in method call initiatlize the whole board\
		frontier = new PriorityQueue<Tile>();
		start = new int[2];
		first = true;
		goal = new int[2];
		generateMaze();
		this.count = 0;
	}

	public void generateMaze() throws FileNotFoundException {
		File file = new File("maze2.txt");
		int lineCount = 0;

		try {
			scan = new Scanner(file);
		} catch (Exception e) {
			System.out.println("File could not be found!");
		}

		String temp;
		temp = scan.nextLine();
		this.size = Integer.parseInt(temp);
		maze = new Tile[size][size];
		while (scan.hasNextLine()) {
			temp = scan.nextLine();
			for (int i = 0; i < size; i++) {
				maze[lineCount][i] = new Tile(checkCharType(temp.charAt(i)), Tile.UNEXPLORED, lineCount, i);
				if (checkCharType(temp.charAt(i)) == Tile.GOAL) {
					goal[0] = lineCount;
					goal[1] = i;
				}
				if (checkCharType(temp.charAt(i)) == Tile.START) {
					start[0] = lineCount;
					start[1] = i;
				}
			}
			lineCount++;
		}
		scan.close();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				maze[i][j].caclculateHeuristic(maze[goal[0]][goal[1]]);
			}
		}
	}

	public Tile[][] getTile() {
		return maze;
	}

	public int checkCharType(char c) {
		if (c == '#') {
			return Tile.WALL;
		} else if (c == '.') {
			return Tile.FREE;
		} else if (c == 'S') {
			return Tile.START;
		} else if (c == 'G') {
			return Tile.GOAL;
		}
		return -1;

	}

	public void printMaze() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				System.out.print(maze[i][j].getHeuristic() + " ");
			}
			System.out.print("\n");
		}
	}

	public boolean isValidCoordinate(int row, int col) {

		if (row < 0 || row >= size || col >= size || col < 0) {
			return false;
		} else if (this.maze[row][col].getType() == Tile.WALL) {
			return false;
		}

		return true;
	}

	public PriorityQueue<Tile> getFrontier() {
		return this.frontier;
	}

	public Tile Astar() {
		if (first) {
			maze[start[0]][start[1]].setParent(null);
			frontier.add(maze[start[0]][start[1]]);
			maze[start[0]][start[1]].setStatus(Tile.FRONTIER);
			maze[start[0]][start[1]].setCost(0);
			maze[start[0]][start[1]].setPriority(maze[start[0]][start[1]].getHeuristic());
			first = false;
		}

		Tile temp = frontier.poll();
		this.count++;
		int[] tempCoord = new int[2];
		tempCoord[0] = temp.getCoordinate()[0];
		tempCoord[1] = temp.getCoordinate()[1];
		if (tempCoord[0] == goal[0] && tempCoord[1] == goal[1]) {
			return temp;
		}
		temp.setStatus(Tile.EXPLORED);
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if ((i != 0 && j == 0) || (i == 0 && j != 0)) {
					if (isValidCoordinate(tempCoord[0] + i, tempCoord[1] + j)) {
						if (maze[tempCoord[0] + i][tempCoord[1] + j].getStatus() == Tile.UNEXPLORED) {
							maze[tempCoord[0] + i][tempCoord[1] + j].setParent(temp);
							maze[tempCoord[0] + i][tempCoord[1] + j].setStatus(Tile.FRONTIER);
							maze[tempCoord[0] + i][tempCoord[1] + j].setCost(temp.getCost() + 1);
							maze[tempCoord[0] + i][tempCoord[1] + j]
									.setPriority(maze[tempCoord[0] + i][tempCoord[1] + j].getCost()
											+ maze[tempCoord[0] + i][tempCoord[1] + j].getHeuristic());
							frontier.add(maze[tempCoord[0] + i][tempCoord[1] + j]);
						} else if (maze[tempCoord[0] + i][tempCoord[1] + j].getStatus() == Tile.FRONTIER) {
							if (maze[tempCoord[0] + i][tempCoord[1] + j].getCost() > temp.getCost() + 1) {
								frontier.remove(maze[tempCoord[0] + i][tempCoord[1] + j]);
								maze[tempCoord[0] + i][tempCoord[1] + j].setParent(temp);
								maze[tempCoord[0] + i][tempCoord[1] + j].setCost(temp.getCost() + 1);
								maze[tempCoord[0] + i][tempCoord[1] + j]
										.setPriority(maze[tempCoord[0] + i][tempCoord[1] + j].getCost()
												+ maze[tempCoord[0] + i][tempCoord[1] + j].getHeuristic());
								frontier.add(maze[tempCoord[0] + i][tempCoord[1] + j]);
							}
						}
					}
				}
			}
		}

		return null;
	}

	public int getSize() {
		return this.size;
	}

	public int[] getGoals() {
		return goal;
	}

	public int getCount(){
		return this.count;
	}

	/*
	 * public static void main(String args[]) throws FileNotFoundException {
	 * Model model = new Model();
	 * model.printMaze();
	 * Tile goal = null;
	 * do {
	 * goal = model.Astar();
	 * } while (goal == null && !model.getFrontier().isEmpty());
	 * 
	 * while (goal.getParent() != null) {
	 * System.out.println(goal.getCoordinate()[0] + " " + goal.getCoordinate()[1]);
	 * }
	 * }
	 */
}

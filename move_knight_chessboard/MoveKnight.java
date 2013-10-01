import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * This is fun problem where we need to compute shortest path for a knight ( http://en.wikipedia.org/wiki/Knight_(chess) ) 
 * from a given starting point to the bottom right grid of the chess board.
 * Lets assume that this is not an ordinary '8x8' chessboard. Instead the user can specify the value of n for a 'nxn' chessboard.
 * Print the shortest path from given starting point to the bottom right point.
 * There are certain starting points which should be marked as point of no return. Throw an exception to the user for all such points.
 * 
 * @author sushantdewan
 *
 */
public class MoveKnight {

	// Hard code the dimension to 3 for now.
	// TODO - Take 'n' as an input from the user and construct 'nxn' matrix
	private static final int DIMENSION = 3;
	
	// This is sentinel value that refers to infinity. 
	// Distance from a starting (point of no return!) node to destination node == 'infinite'
	private static final int FLAG_VAL = Integer.MAX_VALUE;

	private static int mat[][] = new int[DIMENSION][DIMENSION];

	private static Map<Coordinates, Coordinates> map = new HashMap<>();

	/**
	 * Internal class that represents the chess board. The chess board starts at
	 * (0,0) and bottom-right corner is at (DIMENSION - 1, DIMENSION - 1)
	 * 
	 * @author sushantdewan
	 */
	private static class Coordinates {

		private int x;
		private int y;

		Coordinates(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public boolean equals(Object o) {
			if (!(o instanceof Coordinates)) {
				return false;
			}
			Coordinates other = (Coordinates) o;
			return this.x == other.x && this.y == other.y;
		}

		@Override
		public int hashCode() {
			return 31 * x + 117 * y;
		}
	}

	/**
	 * Helper method that prints the chessboard matrix
	 */
	private static void printMat() {
		for (int i = 0; i < DIMENSION; i++) {
			System.out.println();
			for (int j = 0; j < DIMENSION; j++) {
				System.out.print(" " + mat[i][j] + " ");
			}
		}
		System.out.println();
	}

	/**
	 * Helper method that pretty prints teh coordinates of the chessboard grid
	 * 
	 * @param currX
	 * @param currY
	 * @return
	 */
	private static String position(int currX, int currY) {
		return "(" + currX + " , " + currY + ")";
	}

	/**
	 * Initialize all elements to INT_MAX. A value of INT_MAX implies that if
	 * thats the start position, then there is no way to get to the right corner
	 * of the matrix. Throw an error in that case!
	 */
	private static void initMat() {
		for (int i = 0; i < DIMENSION; i++) {
			for (int j = 0; j < DIMENSION; j++) {
				mat[i][j] = FLAG_VAL;
			}
		}
	}

	/**
	 * Tells whether the given coordinate is within the range of the chessboard
	 * 
	 * @param c
	 * @return
	 */
	private static boolean isValidCoord(Coordinates c) {
		if (c.x < 0 || c.x > DIMENSION - 1 || c.y < 0 || c.y > DIMENSION - 1) {
			return false;
		}

		return true;
	}

	/**
	 * If within range and a better suited node, then add that node to the map 
	 * and also initializes the correct weight for the given input node.
	 * 
	 * @param collection
	 * @param x
	 * @param y
	 * @param inputNode
	 */
	private static void addIfValid(Collection<Coordinates> collection, int x,
			int y, Coordinates inputNode) {
		int currVal = mat[inputNode.x][inputNode.y];
		Coordinates c = new Coordinates(x, y);
		if (isValidCoord(c) && mat[x][y] > currVal + 1) {
			mat[x][y] = currVal + 1;
			collection.add(c);
			map.put(new Coordinates(x, y), inputNode);
		}
	}

	/**
	 * For a given input node, finds all potential immediate nodes that can lead to this input node
	 * 
	 * @param inputNode
	 * @return
	 */
	private static Collection<Coordinates> findPotentialMatchInt(
			Coordinates inputNode) {

		Collection<Coordinates> collection = new HashSet<Coordinates>();
		// group by (x-1)
		addIfValid(collection, inputNode.x - 1, inputNode.y + 2, inputNode);
		addIfValid(collection, inputNode.x - 1, inputNode.y - 2, inputNode);

		// group by (x+1)
		addIfValid(collection, inputNode.x + 1, inputNode.y + 2, inputNode);
		addIfValid(collection, inputNode.x + 1, inputNode.y - 2, inputNode);

		// group by (y+1)
		addIfValid(collection, inputNode.x + 2, inputNode.y + 1, inputNode);
		addIfValid(collection, inputNode.x - 2, inputNode.y + 1, inputNode);

		// group by (y-1)
		addIfValid(collection, inputNode.x + 2, inputNode.y - 1, inputNode);
		addIfValid(collection, inputNode.x - 2, inputNode.y - 1, inputNode);

		return collection;
	}

	/**
	 * Recursive helper method that is used to build the map and construct the weight matrix
	 * 
	 * @param inputCollection
	 */
	private static void findPotentialMatch(
			Collection<Coordinates> inputCollection) {
		if (inputCollection.size() == 0) {
			return;
		}

		Collection<Coordinates> collection = new HashSet<Coordinates>();

		for (Coordinates c : inputCollection) {
			collection.addAll(findPotentialMatchInt(c));
		}

		findPotentialMatch(collection);
	}

	/**
	 * Builds the map and constructs the weight matrix
	 */
	private static void buildMap() {
		// Put the bottom-right corner in the map
		map.put(new Coordinates(DIMENSION - 1, DIMENSION - 1), null);
		mat[DIMENSION - 1][DIMENSION - 1] = 0;
		Collection<Coordinates> collection = new HashSet<Coordinates>();
		collection.add(new Coordinates(DIMENSION - 1, DIMENSION - 1));
		findPotentialMatch(collection);
	}

	/**
	 * Moves the knight from given input node. 
	 * Note that the value of dimension 'n' and 
	 * the starting node which is in the range '0 <= value < n' should be entered by the user
	 * 
	 * @param currX
	 * @param currY
	 */
	private static void moveKnight(int currX, int currY) {
		System.out.println("Knight is at position - " + position(currX, currY));
		if (currX == DIMENSION - 1 && currY == DIMENSION - 1) {
			System.out.println("Reached destination!");
			return;
		}
		if (mat[currX][currY] == FLAG_VAL) {
			throw new IllegalArgumentException("Point of no return!");
		}

		Coordinates c = new Coordinates(currX, currY);
		if (map.containsKey(c)) {
			Coordinates nextCoord = map.get(c);
			moveKnight(nextCoord.x, nextCoord.y);
		} else {
			throw new IllegalArgumentException("Point of no return!");
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		printMat();
		initMat();
		printMat();
		buildMap();
		printMat();
		int inputX = Integer.parseInt(args[0]);
		int inputY = Integer.parseInt(args[1]);
		moveKnight(inputX, inputY);
	}
}

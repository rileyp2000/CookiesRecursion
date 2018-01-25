import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class OptimalPath {
	int[][] grid;

	public OptimalPath() {
		Scanner keyboard = new Scanner(System.in);
		String fileName;

		// Open input file:

		System.out.print("\nEnter input file name: ");
		fileName = keyboard.nextLine().trim();

		Scanner sc = fileReader(fileName);
		grid = createGrid(sc);
		System.out.println("max cookies: " + optimalPathFromTop(0, 0, grid.length-1, grid[0].length-1));
	}

	/**
	 * reads in a file
	 * 
	 * @param s
	 *            file name
	 * @return scanner representing the given file
	 */
	public Scanner fileReader(String s) {
		File f = new File(s);
		Scanner daGrid = null;

		try {
			daGrid = new Scanner(f);
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
			System.exit(0);
		}

		return daGrid;
	}

	/**
	 * This takes in a scanner and then converts into an int[][]
	 * 
	 * @param fileGrid
	 *            the scanner containing the grid
	 * @return a 2D array of ints that represents the maze
	 */
	public int[][] createGrid(Scanner fileGrid) {
		String line;

		ArrayList<int[]> temp = new ArrayList<int[]>();
		while (fileGrid.hasNextLine()) {

			line = fileGrid.nextLine();
			// String[] unparsed = line.split(" ");
			ArrayList<String> unparsed = new ArrayList<String>(Arrays.asList(line.split(" ")));
			for (int i = unparsed.size() - 1; i >= 0; i--) {
				if (unparsed.get(i).equals(""))
					unparsed.remove(i);
			}

			int[] row = new int[unparsed.size()];

			for (int i = 0; i < unparsed.size(); i++) {
				// System.out.println(unparsed.get(i));
				row[i] = Integer.parseInt(unparsed.get(i));
			}

			temp.add(row);
		}
		int[][] ret = new int[temp.size()][temp.get(0).length];
		for (int i = 0; i < temp.size(); i++)
			ret[i] = temp.get(i);
		return ret;
	}

	public int lookUp(int r, int c) {
		try {
			return grid[r - 1][c];
		} catch (ArrayIndexOutOfBoundsException e) {
			return -1;
		}
	}

	public int lookLeft(int r, int c) {
		try {
			return grid[r][c - 1];
		} catch (ArrayIndexOutOfBoundsException e) {
			return -1;
		}
	}
	
	public int lookDown(int r, int c) {
		try {
			return grid[r + 1][c];
		} catch (ArrayIndexOutOfBoundsException e) {
			return -1;
		}
	}

	public int lookRight(int r, int c) {
		try {
			return grid[r][c + 1];
		} catch (ArrayIndexOutOfBoundsException e) {
			return -1;
		}
	}

	public int optimalPath(int r, int c) {
		int up = -1;
		int left = -1;
		if (lookUp(r, c) == -1 && lookLeft(r, c) == -1 && r == 0 && c == 0) {
			return grid[r][c];
		} else {
			if(lookUp(r,c) != -1)
				up = optimalPath(r - 1, c);
			if(lookLeft(r,c) != -1)
				left = optimalPath(r, c - 1);
			return grid[r][c] + Math.max(up, left);
		}
	}
	
	public int optimalPathFromTop(int r, int c, int goalR, int goalC) {
		int down = -1;
		int right = -1;
		if (lookDown(r, c) == -1 && lookRight(r, c) == -1 && r == goalR && c == goalC) {
			return grid[r][c];
		} else {
			if(lookDown(r,c) != -1)
				down = optimalPathFromTop(r + 1, c, goalR, goalC);
			if(lookRight(r,c) != -1)
				right = optimalPathFromTop(r, c + 1, goalR, goalC);
			return grid[r][c] + Math.max(down, right);
		}
	}

}

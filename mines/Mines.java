package mines;

import java.util.ArrayList;
import java.util.Random;

public class Mines {

	private int width,height,numMines;
	private boolean showAll = false;
	private Place[][] gameBoard;

	public Mines(int height, int width, int numMines) {  //constructor
		this.height = height;
		this.width = width;
		this.numMines = numMines;
		this.gameBoard = new Place[height][width];
		
		
		for (int i=0; i<height; i++)
			for (int j=0; j<width; j++) {
				gameBoard[i][j] = new Place(i, j);
			}
		setRandMines(numMines);// set number of mines in random places
	}

	public boolean addMine(int i, int j) {

		if (i>width || i<0 || j>height || j<0)
			return false;
		if (gameBoard[i][j].isMine())
			return false;
		gameBoard[i][j].setMine(true);
		return true;
	}

	// set number of correct mines into random places.
	public void setRandMines(int numberOfMines) {
		Random rand = new Random();
		int coorx, coory;
		for (int i = 0; i < numberOfMines; i++) {
			coorx = rand.nextInt(height);
			coory = rand.nextInt(width);
			if (!addMine(coorx, coory))
				i--;
		}
	}

	public boolean open(int i, int j) {

		if (gameBoard[i][j].isMine()) // if its mine- returning false
			return false;
		if (gameBoard[i][j].isStatus()) // if place is open- returning true.
			return true;
		gameBoard[i][j].setStatus(true); // opening this place.

		// checking the neighbors
		if (countMines(gameBoard[i][j].getNeighbors()) == 0)
			for (Place places : gameBoard[i][j].getNeighbors()) {
				open(places.coorX, places.coorY);
			}

		return true;
	}

	public void toggleFlag(int x, int y) {
		if (gameBoard[x][y].isFlag())
			gameBoard[x][y].setFlag(false);
		else
			gameBoard[x][y].setFlag(true);
	}

	public boolean isDone() {
		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++) {
				if (!gameBoard[i][j].isMine())
					if (!gameBoard[i][j].isStatus())
						return false;
			}
		return true;
	}

	public String get(int i, int j) {

		if (showAll) {//in case need to show all
			if (gameBoard[i][j].isMine())
				return "X";
			else {
				if (countMines(gameBoard[i][j].getNeighbors()) == 0)
					return " ";
				else
					return "" + countMines(gameBoard[i][j].getNeighbors());
			}
		}
		if (gameBoard[i][j].isStatus()) {
			if (gameBoard[i][j].isMine())
				return "X";
			else {
				if (countMines(gameBoard[i][j].getNeighbors()) == 0)
					return " ";
				else
					return "" + countMines(gameBoard[i][j].getNeighbors());
			}
		} else {
			if (gameBoard[i][j].isFlag())
				return "F";
			else
				return ".";
		}
	}

	public String toString() {

		StringBuilder str = new StringBuilder();
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				str.append(get(i, j));
			}
			str.append("\n");
		}
		return str.toString();

	}

	public static int countMines(ArrayList<Place> somePlace) {

		int counter = 0;

		for (Place place : somePlace)
			if (place.isMine())
				counter++;
		return counter;
	}

	public void setShowAll(boolean showAll) {
		this.showAll = showAll;
	}

	// this class describe a place in the game matrix
	public class Place {

		private boolean status;// false for close true for open.
		private boolean mine;
		private boolean flag;
		private int coorX, coorY;

		public Place(int coorX, int coorY) {
			this.coorX = coorX;
			this.coorY = coorY;
		}

		public boolean isStatus() {
			return status;
		}

		public void setStatus(boolean status) {
			this.status = status;
		}

		public boolean isMine() {
			return mine;
		}

		public void setMine(boolean mine) {
			this.mine = mine;
		}

		public boolean isFlag() {
			return flag;
		}

		public void setFlag(boolean flag) {
			this.flag = flag;
		}

		public ArrayList<Place> getNeighbors() {// return all place neighbors
			ArrayList<Place> neighbors = new ArrayList<Place>();

			for (int i = -1; i < 2; i++) {
				for (int j = -1; j < 2; j++) {
					if (coorX+i<height && i+coorX>= 0 && j+coorY<width&& j+coorY >= 0)
						neighbors.add(gameBoard[coorX + i][coorY + j]);
				}
			}
			return neighbors;
		}
	}// closing the place

	public static void main(String[] args) {
		Mines m = new Mines(3, 4, 0);
		m.addMine(0, 1);
		m.addMine(2, 3);
		m.open(2, 0);
		System.out.println(m);
		m.toggleFlag(0, 1);
		System.out.println(m);

	}

}

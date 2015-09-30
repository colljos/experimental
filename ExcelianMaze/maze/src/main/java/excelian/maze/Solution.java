package excelian.maze;

public class Solution {

	private boolean[][] correctPath;
	private boolean[][] wasHere;	
	
	public Solution(boolean[][] correctPath, boolean[][] wasHere) {
			
		this.correctPath = correctPath;
		this.wasHere = wasHere;
	}

	public boolean[][] path() {
		return correctPath;
	}

	public boolean[][] history() {
		return wasHere;
	}

}

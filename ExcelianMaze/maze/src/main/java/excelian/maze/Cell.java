package excelian.maze;

public class Cell {
	
	private int x;
	private int y;
	private char value = '?';

	public Cell(int x, int y, char value) {
		this.x = x;
		this.y = y;
		this.value = value;
	}

	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public char value() {
		return value;
	}

	public int X() {
		return x;
	}

	public int Y() {
		return y;
	}

	public Cell left() {
		return new Cell(x-1, y, value);
	}

	public Cell right() {
		return new Cell(x+1, y, value);
	}

	public Cell down() {
		return new Cell(x, y-1, value);
	}

	public Cell up() {
		return new Cell(x, y+1, value);
	}
	
	@Override
	public String toString() {
		return "Cell [x=" + x + ", y=" + y + ", value=" + value + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + value;
		result = prime * result + x;
		result = prime * result + y;
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
		Cell other = (Cell) obj;
		if (value != other.value)
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
}
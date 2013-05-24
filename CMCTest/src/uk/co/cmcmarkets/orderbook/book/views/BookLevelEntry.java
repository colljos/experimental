package uk.co.cmcmarkets.orderbook.book.views;

public class BookLevelEntry {

	private final int price;
	private int size;
	private int orderCount;

	public BookLevelEntry(int price, int quantity, int count) {
		this.price = price;
		this.size = quantity;
		this.orderCount = count;
	}
	
	public void update(int quantity) {
		this.size += quantity;
		orderCount++;
	}
	
	public void amend(int newQuantity, int oldQuantity) {
		this.size += newQuantity;
		this.size -= oldQuantity;
	}

	public void remove(int quantity) {
		this.size -= quantity;
		orderCount--;
	}

	@Override
	public String toString() {
		return new StringBuilder("BookEntry: ")
		.append("Price: ").append(price)
		.append(", Size: ").append(size)
		.append(", Count: ").append(orderCount)
		.toString();
	}

	public int getCount() {
		return orderCount;
	}


}

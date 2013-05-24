package com.jc.markets.data;

public class Tick {

	private final double price;
	private final long quantity;
	private final String instrument;
	private final String marketName;

	public Tick(String marketName, String instrument, double price, long quantity) {
		this.marketName = marketName;
		this.instrument = instrument;
		this.price = price;
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public long getQuantity() {
		return quantity;
	}

	public String getInstrument() {
		return instrument;
	}

	public String getMarketName() {
		return marketName;
	}

	public String toString() {
		return "[" + marketName + ", " + instrument + ", " + price + ", " + quantity + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((instrument == null) ? 0 : instrument.hashCode());
		result = prime * result
				+ ((marketName == null) ? 0 : marketName.hashCode());
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (int) (quantity ^ (quantity >>> 32));
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
		Tick other = (Tick) obj;
		if (instrument == null) {
			if (other.instrument != null)
				return false;
		} else if (!instrument.equals(other.instrument))
			return false;
		if (marketName == null) {
			if (other.marketName != null)
				return false;
		} else if (!marketName.equals(other.marketName))
			return false;
		if (Double.doubleToLongBits(price) != Double
				.doubleToLongBits(other.price))
			return false;
		if (quantity != other.quantity)
			return false;
		return true;
	}

}

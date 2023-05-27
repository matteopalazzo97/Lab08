package it.polito.tdp.extflightdelays.model;

public class Rotta {
	
	private Airport origin;
	private Airport destination;
	private int totDistance;
	private int nVoli;
	private double avgDistance;
	
	
	public Rotta(Airport origin, Airport destination, int totDistance, int nVoli) {
		super();
		this.origin = origin;
		this.destination = destination;
		this.totDistance = totDistance;
		this.nVoli = nVoli;
		this.avgDistance = totDistance/nVoli;
	}
	
	public Rotta(Airport origin, Airport destination, double avgDistance) {
		super();
		this.origin = origin;
		this.destination = destination;
		
		
		this.avgDistance = avgDistance;
	}

	public Airport getOrigin() {
		return origin;
	}

	public void setOrigin(Airport origin) {
		this.origin = origin;
	}

	public Airport getDestination() {
		return destination;
	}

	public void setDestination(Airport destination) {
		this.destination = destination;
	}

	public int getTotDistance() {
		return totDistance;
	}

	public void setTotDistance(int totDistance) {
		this.totDistance = totDistance;
	}

	public int getnVoli() {
		return nVoli;
	}
	
	public double getAvgDistance() {
		return avgDistance;
	}

	public void setnVoli(int nVoli) {
		this.nVoli = nVoli;
	}
	
	public void aumentaTotDistance(int distanza) {
		this.totDistance += distanza;
	}
	
	public void aumentaNvoli(int nVoli) {
		this.nVoli += nVoli;
	}

}

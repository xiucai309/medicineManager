package com.medicine.model.entity;

import java.util.Date;

public class OverdueRecord {
	private int storageId;
	private String medName = null;
	private Date productDate = null;
	private Date storageDate = null;
	private double price;
	private int remainNumber;
	private int overdue;

	public int getStorageId() {
		return storageId;
	}

	public void setStorageId(int storageId) {
		this.storageId = storageId;
	}

	public String getMedName() {
		return medName;
	}

	public void setMedName(String medName) {
		this.medName = medName;
	}

	public Date getProductDate() {
		return productDate;
	}

	public void setProductDate(Date productDate) {
		this.productDate = productDate;
	}

	public Date getStorageDate() {
		return storageDate;
	}

	public void setStorageDate(Date storageDate) {
		this.storageDate = storageDate;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getRemainNumber() {
		return remainNumber;
	}

	public void setRemainNumber(int remainNumber) {
		this.remainNumber = remainNumber;
	}

	public int getOverdue() {
		return overdue;
	}

	public void setOverdue(int overdue) {
		this.overdue = overdue;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((medName == null) ? 0 : medName.hashCode());
		result = prime * result + overdue;
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((productDate == null) ? 0 : productDate.hashCode());
		result = prime * result + remainNumber;
		result = prime * result
				+ ((storageDate == null) ? 0 : storageDate.hashCode());
		result = prime * result + storageId;
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
		OverdueRecord other = (OverdueRecord) obj;
		if (medName == null) {
			if (other.medName != null)
				return false;
		} else if (!medName.equals(other.medName))
			return false;
		if (overdue != other.overdue)
			return false;
		if (Double.doubleToLongBits(price) != Double
				.doubleToLongBits(other.price))
			return false;
		if (productDate == null) {
			if (other.productDate != null)
				return false;
		} else if (!productDate.equals(other.productDate))
			return false;
		if (remainNumber != other.remainNumber)
			return false;
		if (storageDate == null) {
			if (other.storageDate != null)
				return false;
		} else if (!storageDate.equals(other.storageDate))
			return false;
		if (storageId != other.storageId)
			return false;
		return true;
	}
}

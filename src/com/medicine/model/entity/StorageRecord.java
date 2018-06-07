package com.medicine.model.entity;

import java.util.Date;

public class StorageRecord {
	private String storageId;// 入库编号
	private String medId;// 入库药品Id
	private double price;// 入库药品单价
	private int storageNumber;// 入库药品数量
	private Date productDate = null;// 入库药品生产日期
	private Date storageDate = null;// 入库日期(入库日期必须大于生产日期)
	private String leastNumber = null; // 该批药品剩余量
	private String note = null;

	public String getStorageId() {
		return storageId;
	}

	public void setStorageId(String storageId) {
		this.storageId = storageId;
	}

	public String getMedId() {
		return medId;
	}

	public void setMedId(String medId) {
		this.medId = medId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getStorageNumber() {
		return storageNumber;
	}

	public void setStorageNumber(int storageNumber) {
		this.storageNumber = storageNumber;
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

	public String getLeastNumber() {
		return leastNumber;
	}

	public void setLeastNumber(String leastNumber) {
		this.leastNumber = leastNumber;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((leastNumber == null) ? 0 : leastNumber.hashCode());
		result = prime * result + ((medId == null) ? 0 : medId.hashCode());
		result = prime * result + ((note == null) ? 0 : note.hashCode());
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((productDate == null) ? 0 : productDate.hashCode());
		result = prime * result
				+ ((storageDate == null) ? 0 : storageDate.hashCode());
		result = prime * result
				+ ((storageId == null) ? 0 : storageId.hashCode());
		result = prime * result + storageNumber;
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
		StorageRecord other = (StorageRecord) obj;
		if (leastNumber == null) {
			if (other.leastNumber != null)
				return false;
		} else if (!leastNumber.equals(other.leastNumber))
			return false;
		if (medId == null) {
			if (other.medId != null)
				return false;
		} else if (!medId.equals(other.medId))
			return false;
		if (note == null) {
			if (other.note != null)
				return false;
		} else if (!note.equals(other.note))
			return false;
		if (Double.doubleToLongBits(price) != Double
				.doubleToLongBits(other.price))
			return false;
		if (productDate == null) {
			if (other.productDate != null)
				return false;
		} else if (!productDate.equals(other.productDate))
			return false;
		if (storageDate == null) {
			if (other.storageDate != null)
				return false;
		} else if (!storageDate.equals(other.storageDate))
			return false;
		if (storageId == null) {
			if (other.storageId != null)
				return false;
		} else if (!storageId.equals(other.storageId))
			return false;
		if (storageNumber != other.storageNumber)
			return false;
		return true;
	}

}

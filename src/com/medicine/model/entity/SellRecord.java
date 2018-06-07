package com.medicine.model.entity;

import java.util.Date;

public class SellRecord {
	private String sellId = null; // 销售记录编号
	private String storageId = null;// 所销售的药品入库编号
	private String sellNumber = null; // 销售数量(销售数量必须小于等于该批药品的剩余数量)
	private String sellPrice = null;// 销售单价
	private Date sellDate = null; // 销售日期

	public String getSellId() {
		return sellId;
	}

	public void setSellId(String sellId) {
		this.sellId = sellId;
	}

	public String getStorageId() {
		return storageId;
	}

	public void setStorageId(String storageId) {
		this.storageId = storageId;
	}

	public String getSellNumber() {
		return sellNumber;
	}

	public void setSellNumber(String sellNumber) {
		this.sellNumber = sellNumber;
	}

	public String getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(String sellPrice) {
		this.sellPrice = sellPrice;
	}

	public Date getSellDate() {
		return sellDate;
	}

	public void setSellDate(Date sellDate) {
		this.sellDate = sellDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((sellDate == null) ? 0 : sellDate.hashCode());
		result = prime * result + ((sellId == null) ? 0 : sellId.hashCode());
		result = prime * result
				+ ((sellNumber == null) ? 0 : sellNumber.hashCode());
		result = prime * result
				+ ((sellPrice == null) ? 0 : sellPrice.hashCode());
		result = prime * result
				+ ((storageId == null) ? 0 : storageId.hashCode());
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
		SellRecord other = (SellRecord) obj;
		if (sellDate == null) {
			if (other.sellDate != null)
				return false;
		} else if (!sellDate.equals(other.sellDate))
			return false;
		if (sellId == null) {
			if (other.sellId != null)
				return false;
		} else if (!sellId.equals(other.sellId))
			return false;
		if (sellNumber == null) {
			if (other.sellNumber != null)
				return false;
		} else if (!sellNumber.equals(other.sellNumber))
			return false;
		if (sellPrice == null) {
			if (other.sellPrice != null)
				return false;
		} else if (!sellPrice.equals(other.sellPrice))
			return false;
		if (storageId == null) {
			if (other.storageId != null)
				return false;
		} else if (!storageId.equals(other.storageId))
			return false;
		return true;
	}

}

package com.medicine.model.entity;

public class Medicine {
	private String medId = null;
	private String medName = null;
	private String shelfLife = null;
	private boolean isOTC = true;
	private String price = null;
	private String introduce = null;
	private String stroageWay = null;
	private String approvalNumber = null;

	public String getMedId() {
		return medId;
	}

	public void setMedId(String medId) {
		this.medId = medId;
	}

	public String getMedName() {
		return medName;
	}

	public void setMedName(String medName) {
		this.medName = medName;
	}

	public String getShelfLife() {
		return shelfLife;
	}

	public void setShelfLife(String shelfLife) {
		this.shelfLife = shelfLife;
	}

	public boolean isOTC() {
		return isOTC;
	}

	public void setOTC(boolean isOTC) {
		this.isOTC = isOTC;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getStroageWay() {
		return stroageWay;
	}

	public void setStroageWay(String stroageWay) {
		this.stroageWay = stroageWay;
	}

	public String getApprovalNumber() {
		return approvalNumber;
	}

	public void setApprovalNumber(String approvalNumber) {
		this.approvalNumber = approvalNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((approvalNumber == null) ? 0 : approvalNumber.hashCode());
		result = prime * result
				+ ((introduce == null) ? 0 : introduce.hashCode());
		result = prime * result + (isOTC ? 1231 : 1237);
		result = prime * result + ((medId == null) ? 0 : medId.hashCode());
		result = prime * result + ((medName == null) ? 0 : medName.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result
				+ ((shelfLife == null) ? 0 : shelfLife.hashCode());
		result = prime * result
				+ ((stroageWay == null) ? 0 : stroageWay.hashCode());
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
		Medicine other = (Medicine) obj;
		if (approvalNumber == null) {
			if (other.approvalNumber != null)
				return false;
		} else if (!approvalNumber.equals(other.approvalNumber))
			return false;
		if (introduce == null) {
			if (other.introduce != null)
				return false;
		} else if (!introduce.equals(other.introduce))
			return false;
		if (isOTC != other.isOTC)
			return false;
		if (medId == null) {
			if (other.medId != null)
				return false;
		} else if (!medId.equals(other.medId))
			return false;
		if (medName == null) {
			if (other.medName != null)
				return false;
		} else if (!medName.equals(other.medName))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (shelfLife == null) {
			if (other.shelfLife != null)
				return false;
		} else if (!shelfLife.equals(other.shelfLife))
			return false;
		if (stroageWay == null) {
			if (other.stroageWay != null)
				return false;
		} else if (!stroageWay.equals(other.stroageWay))
			return false;
		return true;
	}

}

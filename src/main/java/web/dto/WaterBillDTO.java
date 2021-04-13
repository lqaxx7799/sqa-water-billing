package web.dto;

import java.util.List;
import java.util.Map;

import web.model.WaterBill;

public class WaterBillDTO {
	private String customerName;
	private String address;
	private String addressType;
	private String date;
	private int previousReading;
	private int currentReading;
	private int calculatedReading;
	private List<DetailedBillDTO> detailedBill;
	private float totalPrice;
	private WaterBill waterBill;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getPreviousReading() {
		return previousReading;
	}

	public void setPreviousReading(int previousReading) {
		this.previousReading = previousReading;
	}

	public int getCurrentReading() {
		return currentReading;
	}

	public void setCurrentReading(int currentReading) {
		this.currentReading = currentReading;
	}

	public int getCalculatedReading() {
		return calculatedReading;
	}

	public void setCalculatedReading(int calculatedReading) {
		this.calculatedReading = calculatedReading;
	}

	public List<DetailedBillDTO> getDetailedBill() {
		return detailedBill;
	}

	public void setDetailedBill(List<DetailedBillDTO> detailedBill) {
		this.detailedBill = detailedBill;
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public WaterBill getWaterBill() {
		return waterBill;
	}

	public void setWaterBill(WaterBill waterBill) {
		this.waterBill = waterBill;
	}

}

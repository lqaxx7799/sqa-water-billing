package web.dto;

import web.model.Address;
import web.model.Customer;
import web.model.WaterMeter;
import web.model.WaterMeterReading;

public class WaterBillCalculateDTO {
	private Address address;
	private Customer customer;
	private WaterMeter waterMeter;
	private WaterMeterReading previousReading;
	private WaterMeterReading currentReading;
	private float cost;

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public WaterMeterReading getPreviousReading() {
		return previousReading;
	}

	public void setPreviousReading(WaterMeterReading previousReading) {
		this.previousReading = previousReading;
	}

	public WaterMeterReading getCurrentReading() {
		return currentReading;
	}

	public void setCurrentReading(WaterMeterReading currentReading) {
		this.currentReading = currentReading;
	}

	public WaterMeter getWaterMeter() {
		return waterMeter;
	}

	public void setWaterMeter(WaterMeter waterMeter) {
		this.waterMeter = waterMeter;
	}

	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

}

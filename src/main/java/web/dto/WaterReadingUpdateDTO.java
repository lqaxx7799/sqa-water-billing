package web.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

public class WaterReadingUpdateDTO {
	private Integer readingValue;
	private Integer waterMeterId;

	public Integer getReadingValue() {
		return readingValue;
	}

	public void setReadingValue(Integer readingValue) {
		this.readingValue = readingValue;
	}

	public Integer getWaterMeterId() {
		return waterMeterId;
	}

	public void setWaterMeterId(Integer waterMeterId) {
		this.waterMeterId = waterMeterId;
	}

}

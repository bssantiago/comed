package com.mhc.exceptions.converter;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonAutoDetect(getterVisibility = Visibility.NONE)
public @Data
class ErrorResponseConverter {

	@JsonProperty("ID")
	private String id;
	@JsonProperty("Description")
	private String description;

	public ErrorResponseConverter() {
	}

	public ErrorResponseConverter(int id, String description) {
		ErrorResponseConverter errorResponse = buildErrorResponse(id, description);
		
		this.id = errorResponse.getId();
		this.description = errorResponse.getDescription();
	}
	
	private ErrorResponseConverter buildErrorResponse(int id, String description){
		ErrorResponseConverter errorResponse = new ErrorResponseConverter();
		errorResponse.setDescription(description);
		errorResponse.setId(String.valueOf(id));
		return errorResponse;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}

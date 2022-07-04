package com.whatsapp.core.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReferencedMessageDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@NotEmpty
    @JsonProperty("referenceId")
	private int referenceId;
	
	@NotEmpty
    @JsonProperty("originName")
	private String orginName;
	
	@NotEmpty
    @JsonProperty("message")
	private String message;
	
}

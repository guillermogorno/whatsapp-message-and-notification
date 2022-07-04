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
public class OutboxMessateDTO implements Serializable  {
	
	private static final long serialVersionUID = 1L;

	@NotEmpty
    @JsonProperty("id")
	private Integer id;
	
	@NotEmpty
    @JsonProperty("destinationId")
	private String destinationName;

	@NotEmpty
    @JsonProperty("originId")
	private String originName;

	@NotEmpty
    @JsonProperty("referencedMessage")
	private ReferencedMessageDTO referencedMessage;

	@NotEmpty
    @JsonProperty("messageContent")
	private String message;

}

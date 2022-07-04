package com.whatsapp.core.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class InboxMessageDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@NotEmpty
    @JsonProperty("destinationId")
	private Integer destinationId;

	@NotEmpty
    @JsonProperty("originId")
	private Integer originId;

    @JsonProperty("messageReferenceId")
	private Integer messageReferenceId;

	@NotEmpty
    @JsonProperty("messageContent")
	private String message;
}

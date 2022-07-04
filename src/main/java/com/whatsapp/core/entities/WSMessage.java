package com.whatsapp.core.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.whatsapp.core.entities.enums.MessageStatus;

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
@Entity(name="MESSAGES")
public class WSMessage implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="MESSAGE_ID")
	private Integer id;
	
	@Column(name="DESTINATIO_ID")
	private Integer destinationId;
	
	@Column(name="ORIGIN_ID")
	private Integer originId;
	
	@Column(name="REFERENCE_ID")
	private Integer messageReferenceId;
	
	@Column(name="STATUS")
	@Enumerated(value = EnumType.STRING)
	private MessageStatus status;
		
	@Column(name="MESSAGE")
	private String message;
	
}
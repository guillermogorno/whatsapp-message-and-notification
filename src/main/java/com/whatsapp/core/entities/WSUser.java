package com.whatsapp.core.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.whatsapp.core.entities.enums.UserType;

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
@Entity(name="USERS")
public class WSUser implements Serializable  {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="USER_ID")
	private Integer id;
	
	@Column(name="USER_NAME")
	private String nick;
	
	@Column(name="USER_TYPE")
	@Enumerated(value = EnumType.STRING)
	private UserType type;
			
}
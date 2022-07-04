package com.whatsapp.core.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="CONTACTS")
public class WSUserContact {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CONTACT_ID")
	private Integer id;
	
	@Column(name="USER_ID")
	private Integer userId;
	
	@Column(name="CONTACT_USER_ID")
	private Integer contactUserId;
	
}

package com.whatsapp.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whatsapp.core.dto.InboxMessageDTO;
import com.whatsapp.core.dto.OutboxMessateDTO;
import com.whatsapp.core.exceptions.WhatsAppException;
import com.whatsapp.core.service.MessageService;

@RestController
@RequestMapping("/message")
public class MessageController {

	@Autowired
	private MessageService service;
	
	@PostMapping("/inbox")
	public ResponseEntity<Void> receiveMessage (@RequestBody InboxMessageDTO message) throws WhatsAppException {
		
		service.receiveMessage(message);
		
		return ResponseEntity.noContent ().build ();
	}
	
	@GetMapping("/outbox/{id}")
	public ResponseEntity<List<OutboxMessateDTO>> checkNewMessages (@PathVariable Integer id) throws WhatsAppException {
				
		return ResponseEntity.ok (service.retrieveNewMessage (id));
	}
	
}

package com.whatsapp.core.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whatsapp.core.dto.InboxMessageDTO;
import com.whatsapp.core.dto.OutboxMessateDTO;
import com.whatsapp.core.dto.ReferencedMessageDTO;
import com.whatsapp.core.entities.WSMessage;
import com.whatsapp.core.entities.WSUser;
import com.whatsapp.core.entities.WSUserContact;
import com.whatsapp.core.entities.enums.MessageStatus;
import com.whatsapp.core.entities.enums.UserType;
import com.whatsapp.core.exceptions.WhatsAppException;
import com.whatsapp.core.repository.WSMessageRepository;
import com.whatsapp.core.repository.WSUserContactRepository;
import com.whatsapp.core.repository.WSUserRepository;
import com.whatsapp.core.validator.WSValidator;

@Service
public class MessageService {

	@Autowired
	private WSUserRepository userRepository;

	@Autowired
	private WSMessageRepository messageRepository;
	
	@Autowired
	private WSUserContactRepository userContactRepository;
	
	@Autowired
	private WSValidator wsValidator;

	/**
	 * @param message
	 * @throws WhatsAppException
	 */
	public void receiveMessage (InboxMessageDTO message) throws WhatsAppException {

		wsValidator.validateMessage (message);
		
		persistMessage (message);

	}
	
	/**
	 * @param id
	 * @return
	 * @throws WhatsAppException 
	 */
	public List<OutboxMessateDTO> retrieveNewMessage (Integer id) throws WhatsAppException {
		
		Optional<WSUser> user = userRepository.findById (id);
		
		wsValidator.outboxUserValidation (user);
		
		Optional<List<WSMessage>> newMsgs = messageRepository.findByDestinationIdAndStatus (id, MessageStatus.NOT_RECEIVED);
			
		return outboxMessateDTOConverter (newMsgs);
		
	}	
	
	/**
	 * @param messages
	 * @return
	 */
	private List<OutboxMessateDTO> outboxMessateDTOConverter (Optional<List<WSMessage>> messages) {

		return messages.isEmpty() ? new ArrayList<OutboxMessateDTO>()
										: messages.get()
										.stream()
										.map(msg -> fromWSMessageToOutboxMessateDTO(msg))
										.collect(Collectors.toList());
		
	}
	
	/**
	 * @param message
	 * @return
	 */
	private OutboxMessateDTO fromWSMessageToOutboxMessateDTO (WSMessage message) {
		
		OutboxMessateDTO o = new OutboxMessateDTO ();
		ReferencedMessageDTO refMsg = getReferencedMessageDTO (message);
		
		WSUser dUsr	= userRepository.findById (message.getDestinationId()).get();
		WSUser oUsr = userRepository.findById (message.getOriginId()).get();
				
		o.setId(message.getId());
		o.setDestinationName (dUsr.getNick ());
		o.setOriginName (oUsr.getNick ());
		o.setReferencedMessage (refMsg);
		o.setMessage (message.getMessage ());
		
		markMessageAsReceived (message);
		
		return o;
		
	}
	
	/**
	 * @param message
	 * @return
	 */
	private ReferencedMessageDTO getReferencedMessageDTO (WSMessage message) {
		
		ReferencedMessageDTO referencedMessage = null;
		
		if (null != message.getMessageReferenceId()) {
			WSMessage rf = messageRepository.findById (message.getMessageReferenceId()).get();
			WSUser referencedUsr  = userRepository.findById (rf.getOriginId()).get();
						
			referencedMessage = new ReferencedMessageDTO ();
			
			referencedMessage.setReferenceId(message.getId());
			referencedMessage.setMessage(rf.getMessage());
			referencedMessage.setOrginName(referencedUsr.getNick());
			
		}
		
		return referencedMessage;
		
	}
	
	/**
	 * @param message
	 */
	private void markMessageAsReceived (WSMessage message) {
		
		message.setStatus(MessageStatus.RECEIVED);
		messageRepository.save(message);
		
	}
	

	/**
	 * @param message
	 */
	private void persistMessage (InboxMessageDTO message) {

		WSUser destinationUser = userRepository.findById (message.getDestinationId ()).get();
		
		if (UserType.GROUP == destinationUser.getType ()) {
			// Si el usuario es del tipo GROUP persisto el mensaje enviado al grupo pero dirigido a cada integrante
			persistGroupMessage (message, destinationUser);
		
		} else {
			// Persisto el mensaje pero dirigido al usuario en particular.
			persistSingleMessage (message, message.getOriginId (), message.getDestinationId ());
		
		}
		
	}
	
	/**
	 * @param message
	 * @param destinationUser
	 */
	private void persistGroupMessage (InboxMessageDTO message, WSUser destinationUser) {
		
		List<WSUserContact> contacts = userContactRepository.findByUserId(destinationUser.getId()).get();
		// Persisto tantos msj como integrantes del grupo.
		contacts.stream ().forEach (contact -> {
			persistSingleMessage (message, message.getDestinationId (), contact.getContactUserId ());
		});
		
	}
	
	/**
	 * @param message
	 * @param origin
	 * @param destinationId
	 */
	private void persistSingleMessage (InboxMessageDTO message, Integer origin, Integer destinationId) {
		
		WSMessage msg = new WSMessage();
		
		msg.setDestinationId(destinationId);
		msg.setOriginId(origin);
		msg.setMessage(message.getMessage ());
		msg.setMessageReferenceId (message.getMessageReferenceId());
		msg.setStatus(MessageStatus.NOT_RECEIVED);
		
		messageRepository.save (msg);
		
	}

}

package com.whatsapp.core.validator;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.whatsapp.core.dto.InboxMessageDTO;
import com.whatsapp.core.entities.WSMessage;
import com.whatsapp.core.entities.WSUser;
import com.whatsapp.core.entities.WSUserContact;
import com.whatsapp.core.entities.enums.UserType;
import com.whatsapp.core.exceptions.WhatsAppException;
import com.whatsapp.core.repository.WSMessageRepository;
import com.whatsapp.core.repository.WSUserContactRepository;
import com.whatsapp.core.repository.WSUserRepository;

@Component
public class WSValidator {
	
	@Autowired
	private WSUserRepository userRepository;

	@Autowired
	private WSMessageRepository messageRepository;
	
	@Autowired
	private WSUserContactRepository userContactRepository;

	/**
	 * @param message
	 * @throws WhatsAppException 
	 */
	public void validateMessage (InboxMessageDTO message) throws WhatsAppException {

		Optional<WSUser> oUsr = userRepository.findById (message.getOriginId());

		Optional<WSUser> dUsr = userRepository.findById (message.getDestinationId());
		
		// Valido si los usuarios son validos.
		inboxUsersValidation (oUsr, dUsr);

		WSUser origin = oUsr.get ();
		
		WSUser destination = dUsr.get ();
		
		// Valido si la referencia de mensaje existe en la db.
		if (null != message.getMessageReferenceId ()) {
			Optional<WSMessage> relatedMessage = messageRepository.findById (message.getMessageReferenceId());
			relatedMessageValidation(relatedMessage);
		}
		
		// Valido en el caso que el usuario que envia un msj al grupo pertenezca a este.
		if (UserType.GROUP == destination.getType ()) {
			validateContact (origin, destination);
		}
		
	}
	
	/**
	 * @param origin
	 * @param destination
	 * @throws WhatsAppException
	 */
	public void validateContact (WSUser origin, WSUser destination) throws WhatsAppException {
		
//		Optional<WSUserContact> contacts = userContactRepository.findByUserIdAndContactUserId (origin.getId (),
//				destination.getId ());
		
		Optional<WSUserContact> contacts = userContactRepository.findByUserIdAndContactUserId(destination.getId (),
				origin.getId ());
		
		emptyObjectValidation (contacts, "El usuario que emite el mensaje no forma parte de los miembros del grupo.");
		
	}

	/**
	 * @param relatedMessage
	 * @throws WhatsAppException
	 */
	public void relatedMessageValidation (Optional<WSMessage> relatedMessage) throws WhatsAppException {
		
		emptyObjectValidation (relatedMessage, "El mensaje al que se hace referencia no existe en la base de datos.");
	
	}

	/**
	 * @param origin
	 * @param destination
	 * @throws WhatsAppException
	 */
	public void inboxUsersValidation (Optional<WSUser> origin, Optional<WSUser> destination) throws WhatsAppException {

		emptyObjectValidation (origin, "El usuario que intenta enviar el mensaje no existe en la base de datos.");
		
		emptyObjectValidation (destination, "El usuario al que se le intenta enviar el mensaje no existe.");
		
	}
	
	/**
	 * @param user
	 * @throws WhatsAppException
	 */
	public void outboxUserValidation (Optional<WSUser> user) throws WhatsAppException {
		
		emptyObjectValidation (user, "El usuario que intenta obtener su outbox no existe.");
		
	}
	
	/**
	 * @param <T>
	 * @param o
	 * @param messageError
	 * @throws WhatsAppException
	 */

	private <T> void emptyObjectValidation (Optional<T> o, String messageError) throws WhatsAppException {
		
		if (o.isEmpty()) {
			throw new WhatsAppException (messageError);
		}
		
	}
	
}

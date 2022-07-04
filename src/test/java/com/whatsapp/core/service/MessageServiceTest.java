package com.whatsapp.core.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.whatsapp.core.dto.InboxMessageDTO;
import com.whatsapp.core.entities.WSUser;
import com.whatsapp.core.entities.WSUserContact;
import com.whatsapp.core.exceptions.WhatsAppException;
import com.whatsapp.core.repository.WSMessageRepository;
import com.whatsapp.core.repository.WSUserContactRepository;
import com.whatsapp.core.repository.WSUserRepository;
import com.whatsapp.core.validator.WSValidator;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageServiceTest {

	@InjectMocks
	private MessageService service;
	
	@Mock
	private WSUserRepository userRepository;

	@Mock
	private WSMessageRepository messageRepository;
	
	@Mock
	private WSUserContactRepository userContactRepository;
	
	@Mock
	private WSValidator wsValidator;
	
	@Test
	public void when_receiveMessage_givenValidRequestFromCommonToGroup_than_ok () throws Exception {
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		// declaro y seteo los objetos con los datos necesarios para que cumplan con lo que plantea el nombre del metodo.
		
		InboxMessageDTO rq = objectMapper.readValue("{ \"destinationId\" : 4, \"originId\" : 1, \"messageContent\" : \"Hola como andan!!!\"}", InboxMessageDTO.class);
		
		
		WSUser origin = objectMapper.readValue("{\"id\" : 1, \"nick\" : \"USER1\", \"type\" : \"COMMON\"}", WSUser.class);
		
		WSUser destination  = objectMapper.readValue("{\"id\" : 4, \"nick\" : \"Grupo del Trabajo\", \"type\" : \"GROUP\"}", WSUser.class);
		
		WSUserContact originContacts = objectMapper.readValue("{\"id\" : 3, \"userId\" : 1, \"contactUserId\" : 4}", WSUserContact.class);
		
		WSUserContact destinationContact1 = objectMapper.readValue("{\"id\" : 7, \"userId\" : 4, \"contactUserId\" : 1}", WSUserContact.class);
		
		WSUserContact destinationContact2 = objectMapper.readValue("{\"id\" : 8, \"userId\" : 4, \"contactUserId\" : 2}", WSUserContact.class);
		
		// mockeo userRepositorio para que cuando consulte por el id 1 devuelva origin.
		when(userRepository.findById(1) ).thenReturn(Optional.ofNullable(origin));
		
		// mockeo userRepositorio para que cuando consulte por el id 4 devuelva destination.
		when(userRepository.findById(4) ).thenReturn(Optional.ofNullable(destination));
		
		// mockeo userContactRepository para que cuando haga la consulta de si pertenece el destination a sus contactos me devuelva 
		when(userContactRepository.findByUserIdAndContactUserId(1,4) ).thenReturn(Optional.ofNullable(originContacts));
		
		// mockeo como contactos los miembros del grupo, en este caso solo a user 1 y user 2.
		when(userContactRepository.findByUserId(4)).thenReturn(Optional.ofNullable(List.of(destinationContact1, destinationContact2)));
		
		// ejecuto el servicio con los datos ya mockeados.
		service.receiveMessage(rq);
		
		// verifico que al ejecutarse la persistencia 2 veces el flujo de ejecucion se ejecuto exitosamente.
		verify(messageRepository, times(2)).save(any());
		
	}
}

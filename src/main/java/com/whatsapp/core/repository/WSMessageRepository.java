package com.whatsapp.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whatsapp.core.entities.WSMessage;
import com.whatsapp.core.entities.enums.MessageStatus;

@Repository
public interface WSMessageRepository extends JpaRepository<WSMessage, Integer> {

	Optional<List<WSMessage>> findByDestinationIdAndStatus (Integer destinationId, MessageStatus status);

	Optional<List<WSMessage>> findByDestinationId (Integer id);
}

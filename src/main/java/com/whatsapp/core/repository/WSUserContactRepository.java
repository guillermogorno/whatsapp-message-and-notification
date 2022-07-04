package com.whatsapp.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whatsapp.core.entities.WSUserContact;

@Repository
public interface WSUserContactRepository extends JpaRepository<WSUserContact, Integer>{

	Optional<WSUserContact> findByUserIdAndContactUserId(Integer origin, Integer destination);
	
	Optional<List<WSUserContact>> findByUserId (Integer userId);

}

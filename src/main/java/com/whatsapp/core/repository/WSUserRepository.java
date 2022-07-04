package com.whatsapp.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whatsapp.core.entities.WSUser;

@Repository
public interface WSUserRepository extends JpaRepository<WSUser, Integer> {

}

package com.carealert.app.repositories;

import com.carealert.app.entities.TrustedContact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrustedContactRepository
        extends JpaRepository<TrustedContact, Long> {

    List<TrustedContact> findByUser_UserId(Long userId);
}

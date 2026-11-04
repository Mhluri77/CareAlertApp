package com.carealert.app.services;

import com.carealert.app.entities.TrustedContact;
import com.carealert.app.repositories.TrustedContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrustedContactService {

    private final TrustedContactRepository trustedContactRepository;

    public TrustedContactService(
            TrustedContactRepository trustedContactRepository
    ) {
        this.trustedContactRepository = trustedContactRepository;
    }

    public List<TrustedContact> getAllContacts() {
        return trustedContactRepository.findAll();
    }

    public List<TrustedContact> getContactsByUser(
            Long userId
    ) {
        return trustedContactRepository
                .findByUser_UserId(userId);
    }

    public TrustedContact saveContact(
            TrustedContact contact
    ) {
        return trustedContactRepository.save(contact);
    }

    public void deleteContact(Long id) {
        trustedContactRepository.deleteById(id);
    }
}

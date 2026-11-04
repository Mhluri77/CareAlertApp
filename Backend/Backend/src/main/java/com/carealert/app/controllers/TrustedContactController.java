package com.carealert.app.controllers;

import com.carealert.app.entities.TrustedContact;
import com.carealert.app.services.TrustedContactService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carealert/contacts")
@CrossOrigin(origins = "*")
public class TrustedContactController {

    private final TrustedContactService trustedContactService;

    public TrustedContactController(
            TrustedContactService trustedContactService
    ) {
        this.trustedContactService =
                trustedContactService;
    }

    @GetMapping
    public List<TrustedContact> getAllContacts() {

        return trustedContactService
                .getAllContacts();
    }

    @GetMapping("/user/{userId}")
    public List<TrustedContact> getContactsByUser(
            @PathVariable Long userId
    ) {

        return trustedContactService
                .getContactsByUser(userId);
    }

    @PostMapping
    public TrustedContact createContact(
            @RequestBody TrustedContact contact
    ) {

        return trustedContactService
                .saveContact(contact);
    }

    @DeleteMapping("/{id}")
    public void deleteContact(
            @PathVariable Long id
    ) {

        trustedContactService
                .deleteContact(id);
    }
}

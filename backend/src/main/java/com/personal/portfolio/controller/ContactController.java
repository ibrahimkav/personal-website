package com.personal.portfolio.controller;

import com.personal.portfolio.dto.contact.ContactRequest;
import com.personal.portfolio.dto.contact.ContactResponse;
import com.personal.portfolio.dto.contact.ContactReplyRequest;
import com.personal.portfolio.service.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @PostMapping
    public ResponseEntity<ContactResponse> createContact(@Valid @RequestBody ContactRequest contactRequest) {
        return ResponseEntity.ok(contactService.createContact(contactRequest));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<ContactResponse>> getAllContacts(Pageable pageable) {
        return ResponseEntity.ok(contactService.getAllContacts(pageable));
    }

    @GetMapping("/unread")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<ContactResponse>> getUnreadContacts(Pageable pageable) {
        return ResponseEntity.ok(contactService.getUnreadContacts(pageable));
    }

    @GetMapping("/unread/count")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Long> getUnreadCount() {
        return ResponseEntity.ok(contactService.getUnreadCount());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ContactResponse> getContactById(@PathVariable Long id) {
        return ResponseEntity.ok(contactService.getContactById(id));
    }

    @PostMapping("/{id}/reply")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ContactResponse> replyToContact(
            @PathVariable Long id,
            @Valid @RequestBody ContactReplyRequest replyRequest) {
        return ResponseEntity.ok(contactService.replyToContact(id, replyRequest));
    }

    @PutMapping("/{id}/read")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> markAsRead(@PathVariable Long id) {
        contactService.markAsRead(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        contactService.deleteContact(id);
        return ResponseEntity.ok().build();
    }
} 
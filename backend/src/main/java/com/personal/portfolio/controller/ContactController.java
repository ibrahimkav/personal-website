package com.personal.portfolio.controller;

import com.personal.portfolio.dto.ApiResponse;
import com.personal.portfolio.dto.contact.ContactRequest;
import com.personal.portfolio.dto.contact.ContactResponse;
import com.personal.portfolio.entity.Contact;
import com.personal.portfolio.service.ContactService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contacts")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class ContactController {

    private final ContactService contactService;

    @PostMapping
    public ResponseEntity<ApiResponse<ContactResponse>> submitContact(
            @Valid @RequestBody ContactRequest request,
            HttpServletRequest servletRequest) {
        Contact contact = new Contact();
        contact.setName(request.getName());
        contact.setEmail(request.getEmail());
        contact.setMessage(request.getMessage());
        contact.setIpAddress(servletRequest.getRemoteAddr());

        Contact savedContact = contactService.save(contact);
        return ResponseEntity.ok(ApiResponse.success("Message sent successfully", mapToResponse(savedContact)));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Page<ContactResponse>>> getAllContacts(
            @PageableDefault(size = 10) Pageable pageable) {
        Page<Contact> contacts = contactService.getAllContacts(pageable);
        Page<ContactResponse> response = contacts.map(this::mapToResponse);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/unread")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Page<ContactResponse>>> getUnreadContacts(
            @PageableDefault(size = 10) Pageable pageable) {
        Page<Contact> contacts = contactService.getUnreadContacts(pageable);
        Page<ContactResponse> response = contacts.map(this::mapToResponse);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/unread/count")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Long>> getUnreadCount() {
        long count = contactService.getUnreadContactCount();
        return ResponseEntity.ok(ApiResponse.success(count));
    }

    @PutMapping("/{id}/read")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ContactResponse>> markAsRead(@PathVariable Long id) {
        Contact contact = contactService.markAsRead(id);
        return ResponseEntity.ok(ApiResponse.success("Message marked as read", mapToResponse(contact)));
    }

    @PutMapping("/{id}/respond")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ContactResponse>> respondToContact(
            @PathVariable Long id,
            @RequestParam String response) {
        Contact contact = contactService.respondToContact(id, response);
        return ResponseEntity.ok(ApiResponse.success("Response sent successfully", mapToResponse(contact)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteContact(@PathVariable Long id) {
        contactService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Message deleted successfully", null));
    }

    private ContactResponse mapToResponse(Contact contact) {
        return ContactResponse.builder()
                .id(contact.getId())
                .name(contact.getName())
                .email(contact.getEmail())
                .message(contact.getMessage())
                .read(contact.getRead())
                .response(contact.getResponse())
                .createdAt(contact.getCreatedAt())
                .ipAddress(contact.getIpAddress())
                .build();
    }
} 
package com.personal.portfolio.controller;

import com.personal.portfolio.dto.about.AboutRequest;
import com.personal.portfolio.dto.about.AboutResponse;
import com.personal.portfolio.service.AboutService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/about")
@RequiredArgsConstructor
public class AboutController {

    private final AboutService aboutService;

    @GetMapping
    public ResponseEntity<AboutResponse> getActiveAbout() {
        return ResponseEntity.ok(aboutService.getActiveAbout());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AboutResponse> createAbout(@Valid @RequestBody AboutRequest aboutRequest) {
        return ResponseEntity.ok(aboutService.createAbout(aboutRequest));
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AboutResponse> updateActiveAbout(@Valid @RequestBody AboutRequest aboutRequest) {
        return ResponseEntity.ok(aboutService.updateActiveAbout(aboutRequest));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAbout(@PathVariable Long id) {
        aboutService.deleteAbout(id);
        return ResponseEntity.ok().build();
    }
} 
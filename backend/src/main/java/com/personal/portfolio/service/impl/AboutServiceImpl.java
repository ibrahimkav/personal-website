package com.personal.portfolio.service.impl;

import com.personal.portfolio.entity.About;
import com.personal.portfolio.repository.AboutRepository;
import com.personal.portfolio.service.AboutService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AboutServiceImpl implements AboutService {

    private final AboutRepository aboutRepository;

    @Override
    @Transactional
    public About save(About entity) {
        return aboutRepository.save(entity);
    }

    @Override
    @Transactional
    public About update(Long id, About entity) {
        if (!aboutRepository.existsById(id)) {
            throw new EntityNotFoundException("About not found with id: " + id);
        }
        entity.setId(id);
        return aboutRepository.save(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        About about = findById(id);
        about.setActive(false);
        aboutRepository.save(about);
    }

    @Override
    public About findById(Long id) {
        return aboutRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("About not found with id: " + id));
    }

    @Override
    public List<About> findAll() {
        return aboutRepository.findAll();
    }

    @Override
    public boolean existsById(Long id) {
        return aboutRepository.existsById(id);
    }

    @Override
    public About getActiveAbout() {
        return aboutRepository.findFirstByActiveTrue()
                .orElseThrow(() -> new EntityNotFoundException("No active about information found"));
    }

    @Override
    @Transactional
    public About updateActiveAbout(About about) {
        About existingAbout = getActiveAbout();
        about.setId(existingAbout.getId());
        return aboutRepository.save(about);
    }
} 
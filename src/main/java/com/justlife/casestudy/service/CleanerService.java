package com.justlife.casestudy.service;

import com.justlife.casestudy.model.Cleaner;
import com.justlife.casestudy.repository.CleanerRepository;
import org.springframework.stereotype.Service;

@Service
public class CleanerService extends BaseService<Cleaner>{

    private CleanerRepository cleanerRepository;

    public CleanerService(CleanerRepository repository) {
        super("Cleaner", repository);
        this.cleanerRepository = repository;
    }
}

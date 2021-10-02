package com.justlife.casestudy.service;

import com.justlife.casestudy.model.BaseEntity;
import com.justlife.casestudy.repository.BaseRepository;

public class BaseService<T extends BaseEntity> extends ModelService<T, Long> {

    private BaseRepository<T> repository;

    public BaseService(String resourceName, BaseRepository<T> repository) {

        super(resourceName, repository);
        this.repository = repository;
    }
}
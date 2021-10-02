package com.justlife.casestudy.repository;

import com.justlife.casestudy.model.BaseEntity;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity> extends ModelRepository<T, Long> {}

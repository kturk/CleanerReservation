package com.justlife.casestudy.repository;

import com.justlife.casestudy.model.Cleaner;
import org.springframework.stereotype.Repository;

@Repository
public interface CleanerRepository extends BaseRepository<Cleaner>{

    //@Query("SELECT where type :i");
    //public int countCleaner(Type);
    //)
}

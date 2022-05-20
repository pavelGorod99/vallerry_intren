package com.vallerry.peopledbweb.dao;

import com.vallerry.peopledbweb.biz.model.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.Set;

@Repository
public interface PersonRepository extends PagingAndSortingRepository<Person, Long> {

    @Query(nativeQuery = true, value = "SELECT PHOTO_FILENAME FROM PERSON WHERE ID IN :IDS")
    Set<String> findFilenameByIds(@Param("IDS") Iterable<Long> IDS);
}

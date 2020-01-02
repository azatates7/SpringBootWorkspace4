package h2_jpa.dao;

import h2_jpa.entity.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PersonDAO extends CrudRepository<Person, Long> {

    public List<Person> findByFullNameLike(String name);

    public List<Person> findByDateOfBirthGreaterThan(Date date);

}
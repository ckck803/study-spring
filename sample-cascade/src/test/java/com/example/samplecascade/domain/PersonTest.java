package com.example.samplecascade.domain;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class PersonTest {

    @PersistenceContext
    private EntityManager entityManager;

//    @Test
    void cascadeAllTest() {
        Phone phone = new Phone();
        phone.setPhoneNumber("010-1234-1234");

        Person person = new Person();
        person.setName("tester");

        person.addPhone(phone);
        entityManager.persist(person);
        entityManager.flush();
    }

//    @Test
    void cascadePersistTest() {
        Phone phone = new Phone();
        phone.setPhoneNumber("010-1234-1234");

        Person person = new Person();
        person.setName("tester");

        person.addPhone(phone);
        entityManager.persist(person);
        entityManager.flush();

        entityManager.remove(phone);
        entityManager.remove(person);
//        assertThatThrownBy(() -> {
//            entityManager.remove(person);
//        })
//                .isInstanceOf(ConstraintViolationException.class);
//        assertThatThrownBy(() -> {entityManager.remove(person);}).isInstanceOf(ConstraintViolationException.class);
    }


    @Test
    void cascadeMergeTest() {
        Phone phone = new Phone();
        phone.setPhoneNumber("010-1234-1234");

        Person person = new Person();
        person.setName("tester");

        person.addPhone(phone);
        entityManager.persist(person);
        entityManager.persist(phone);
        entityManager.flush();

        // 영속성 Context 를 초기화 한다.
        entityManager.clear();

        Person savedPerson = phone.getPerson();

        phone.setPhoneNumber("010-1111-2222");
        savedPerson.setName("tester2");

        // Person 객체를 merge 할 경우 Phone 객체까지 같이 영속성 Context 로 올라오게 된다.
        entityManager.merge(person);
    }

//    @Test
    void cascadeRemoveTest() {
        Phone phone = new Phone();
        phone.setPhoneNumber("010-1234-1234");

        Person person = new Person();
        person.setName("tester");

        person.addPhone(phone);
        entityManager.persist(person);
        entityManager.persist(phone);
        entityManager.flush();
        Long personId = person.getId();
        entityManager.clear();

        Person savedPerson = entityManager.find(Person.class, personId);
        entityManager.remove(savedPerson);
    }

//    @Test
    void cascadeRefreshTest() {
        Phone phone = new Phone();
        phone.setPhoneNumber("010-1234-1234");

        Person person = new Person();
        person.setName("tester");

        person.addPhone(phone);
        entityManager.persist(person);
        entityManager.persist(phone);
        entityManager.flush();

        person.setName("test2");
        phone.setPhoneNumber("010-1111-2222");
        entityManager.refresh(person);

        assertThat(person.getName()).isEqualTo("tester");
        assertThat(phone.getPhoneNumber()).isEqualTo("010-1234-1234");
    }

//    @Test
    void cascadeDetachTest() {
        Phone phone = new Phone();
        phone.setPhoneNumber("010-1234-1234");

        Person person = new Person();
        person.setName("tester");

        person.addPhone(phone);
        entityManager.persist(person);
        entityManager.persist(phone);
        entityManager.flush();

        assertThat(entityManager.contains(person)).isTrue();
        assertThat(entityManager.contains(phone)).isTrue();

        entityManager.detach(person);

        assertThat(entityManager.contains(person)).isFalse();
        assertThat(entityManager.contains(phone)).isFalse();
    }
}
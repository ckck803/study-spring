package com.example.datajpa.repository;

import com.example.datajpa.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class MemberRepositoryImpl implements MemberRepositoryCustom{

    @PersistenceContext
    private final EntityManager em;

    public MemberRepositoryImpl(EntityManager em){
        this.em = em;
    }

    @Override
    public List<Member> findMemberCustom() {
        return em.createQuery("select m from Member m")
                .getResultList();
    }
}

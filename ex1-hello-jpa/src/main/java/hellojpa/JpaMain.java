package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] ars) {
        // persistence.xml을 이용해
        // EntityManagerFactory를 생성한다.
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");
        // EntityManager를 생성한다.
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        // 트랜잭션을 생성한다.
        EntityTransaction transaction = entityManager.getTransaction();

        // JPA에서 데이터를 변경하는 모든 작업은 트랜잭션 내에서 이루어져야 한다.
        transaction.begin();
        try {
            // 데이터 삽
//            Member member = new Member();
//            member.setId(1L);
//            member.setName("HelloA");
//
//            영속성 컨텍스트에 저장한다.
//            entityManager.persist(member);

            // 데이터 수정
//            Member findMember = entityManager.find(Member.class, 1L);
//            findMember.setName("HelloJPA");

            List<Member> result = entityManager.createQuery("select m from Member as m", Member.class)
                    .setFirstResult(5)
                    .setMaxResults(10)
                    .getResultList();

            for (Member member : result) {
                System.out.println("Member.name : " + member.getName());
            }

            // DB에 변경사항을 반영한다.
            transaction.commit();


        } catch (Exception e) {
            transaction.rollback();
        } finally {
            // EntityManager를 종료시킨다.
            entityManager.close();
        }
        // EntityManagerFactory를 종료시킨다.
        entityManagerFactory.close();
    }
}

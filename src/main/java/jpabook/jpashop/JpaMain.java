package jpabook.jpashop;

import jpabook.jpashop.domain.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member1 = new Member();
            member1.setName("관리자1");
            em.persist(member1);

            Member member2 = new Member();
            member2.setName("관리자2");
            em.persist(member2);

            String query = "select function('group_concat', m.name) from Member m";

            List<String> result = em.createQuery(query, String.class)
                    .getResultList();

            for(String s : result) System.out.println(s); // 관리자1,관리자2

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}

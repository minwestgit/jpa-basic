package jpabook.jpashop;

import jpabook.jpashop.domain.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Map;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member();
            member.setName("hello");

            em.persist(member);

            em.flush();
            em.clear();

            //프록시
            //Member findMember = em.find(Member.class, member.getId());
            //System.out.println("findMember.id = " + findMember.getId());
            //System.out.println("findMember.userName = " + findMember.getName());

            Member member1 = new Member();
            member1.setName("member1");
            em.persist(member1);
            Member member2 = new Member();
            member2.setName("member2");
            em.persist(member2);

            Member m1 = em.find(Member.class, member1.getId());
            Member m2 = em.find(Member.class, member2.getId());

            System.out.println("m1 == m2: " + (m1.getClass() == m2.getClass())); // false
            System.out.println("m1 instanceof: " + (m1 instanceof Member)); // true

            Member reference = em.getReference(Member.class, member1.getId());
            System.out.println("reference: " + reference.getClass()); // 이거도 Member 반환

            Member refMember = em.getReference(Member.class, member1.getId()); // 프록시 초기화
            // 프록시 초기화 후 find 호출 시 같은 객체 반환
            Member findMember = em.find(Member.class, member1.getId());

            em.detach(refMember);
            refMember.getName();

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}

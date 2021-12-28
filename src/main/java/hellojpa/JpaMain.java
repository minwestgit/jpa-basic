package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello"); // 하나만 생성해 App 전체가 공유
        EntityManager em = emf.createEntityManager(); // 쓰레드간 공유 절대 금지!

        // 트랜잭션 처리 (JPA의 모든 데이터 변경은 트랜잭션 안에서 진행)
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = em.find(Member.class, 150L);
            member.setName("AAAA");

            em.detach(member); // 영속성 컨텍스트에서 분리
            tx.commit(); // update 쿼리가 실행되지 않음!!
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}

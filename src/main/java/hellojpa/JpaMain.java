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
            // 엔티티 등록
            Member member1 = new Member(150L, "A");
            Member member2 = new Member(160L, "B");

            em.persist(member1);
            em.persist(member2);

            // 엔티티 수정
            Member findMember = em.find(Member.class, 101L);
            member.setName("ZZZ");

            tx.commit(); // 커밋하는 순간에 쿼리 실행
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}

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
            Member member = new Member();
            member.setId(101L);

            em.getTransaction().begin();

            // 객체를 저장한 상태(영속) -> 1차 캐시에 저장
            em.persist(member);

            // 1차 캐시에서 조회(없으면 DB에서 조회)
            Member findMember = em.find(Member.class, 101L);

            // 영속 엔티티의 동일성 보장
            Member findMember1 = em.find(Member.class, 101L);
            Member findMember2 = em.find(Member.class, 101L);

            System.out.println("result = " + (findMember1 == findMember2));
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}

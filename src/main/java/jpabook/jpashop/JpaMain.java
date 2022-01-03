package jpabook.jpashop;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            //저장
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setName("member1");
            // member.setTeamId(team.getId());
            member.setTeam(team);
            em.persist(member);

            Member findMember = em.find(Member.class, member.getId());
            // 연관관계 설정을 안해주면 객체지향스럽지 않음.
            //Long findTeamId = findMember.getTeadId();
            //Team findTeam = em.find(Team.class, findTeamId);
            Team findTeam = findMember.getTeam();

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

}

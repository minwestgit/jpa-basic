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
            Team teamA = new Team();
            teamA.setName("teamA");

            Team teamB = new Team();
            teamB.setName("teamB");

            Member member1 = new Member();
            member1.setName("회원1");
            member1.setTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setName("회원2");
            member2.setTeam(teamA);
            em.persist(member2);

            Member member3 = new Member();
            member3.setName("회원3");
            member3.setTeam(teamB);
            em.persist(member3);

            // 일반 조인
            String query = "select m from Member m";
            List<Member> result = em.createQuery(query, Member.class)
                    .getResultList();
            for (Member member : result) {
                // 회원1, 팀A -> SQL
                // 회원2, 팀A -> 1차캐시
                // 회원3, 팀B -> SQL
                // => N+1 문제 발생 가능성
                System.out.println("member = " + member.getName() + ", " + member.getTeam());
            }

            // 엔티티 페치 조인
            String jpql = "select t from Team t join fetch t.members where t.name = '팀A'";
            List<Team> teams = em.createQuery(jpql, Team.class).getResultList();
            for(Team team : teams) {
                System.out.println("teamname = " + team.getName() + ", team = " + team);
                for (Member member : team.getMembers()) {
                    //페치 조인으로 팀과 회원을 함께 조회해서 지연 로딩 발생 안함
                    System.out.println("-> username = " + member.getName()+ ", member = " + member);
                }
            }

            // 컬렉션 페치 조인
            String jpql2 = "select t from Team t join fetch t.members where t.name = '팀A'";
            List<Team> teams2 = em.createQuery(jpql2, Team.class).getResultList();
            for(Team team : teams2) {
                System.out.println("teamname = " + team.getName() + ", team = " + team);
                for (Member member : team.getMembers()) {
                    //페치 조인으로 팀과 회원을 함께 조회해서 지연 로딩 발생 안함
                    System.out.println("-> username = " + member.getName()+ ", member = " + member);
                }
            }

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}

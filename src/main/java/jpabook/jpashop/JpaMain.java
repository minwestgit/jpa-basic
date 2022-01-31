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
            String query = "select m.username, 'HELLO', TRUE From Member m";
            String query2 = "select m.username, 'HELLO', TRUE From Member m"
                    + "where m.type = jpql.MemberType.ADMIN";
            List<Object[]> result = em.createQuery(query).getResultList();

            Book book = new Book();
            book.setName("JPA");
            book.setAuthor("author");

            em.persist(book);

            // ITEM과 BOOK은 상속관계이므로 type(i)가 Book으로 나오게 됨.
            em.createQuery("select i from Item i where type(i) = Book", Item.class)
                    .getResultList();

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}

package jpabook.jpashop;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository{

    @PersistenceContext // 스프링부트가 entity manager 주입
    private EntityManager em;

    public Long save(Member member){
        em.persist(member);
        return member.getId(); // command와 query를 분리(side effect 방지를 위해 id 값만 반환)
    }

    public Member find(Long id) {
        return em.find(Member.class, id);
    }

}

package com.eunsoly.findex.common.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing // JPA Auditing 활성화 (@CreatedDate, @LastModifiedDate 자동 입력)
public class QueryDslConfig {

    /**
     * QueryDSL 쿼리를 만드는 JPAQueryFactory를 빈으로 등록한다. 주입되는 EntityManager는 스프링이 감싼 프록시라서, 요청(트랜잭션)마다 알맞은 실제 EntityManager로 연결된다. 그래서 싱글톤 빈 하나를 여러 요청이 같이
     * 써도 안전하다.
     *
     * @param entityManager 스프링이 주입하는 EntityManager (JPA 작업의 창구)
     * @return 쿼리 작성에 사용할 JPAQueryFactory
     */
    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager entityManager) {
        return new JPAQueryFactory(entityManager);
    }
}

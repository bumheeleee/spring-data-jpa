package study.datajpa.feature.team.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.datajpa.feature.team.entity.Team;

//@Repository를 생략이 가능
public interface TeamRepository extends JpaRepository<Team, Long> {
}

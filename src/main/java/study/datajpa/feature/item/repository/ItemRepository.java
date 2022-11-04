package study.datajpa.feature.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.datajpa.feature.item.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}

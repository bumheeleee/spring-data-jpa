package study.datajpa.feature.item.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.feature.item.entity.Item;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemRepositoryTest {
    @Autowired
    ItemRepository itemRepository;

    @Test
    public void saveTest(){
        /**
         * 식별자가 객체일 때 null 로 판단
         * 식별자가 자바 기본 타입일 때 0 으로 판단
         * Persistable 인터페이스를 구현해서 판단 로직 변경 가능
         */
        Item item = new Item();
        Item save = itemRepository.save(item);
    }

}
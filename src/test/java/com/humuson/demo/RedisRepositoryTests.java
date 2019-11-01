package com.humuson.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.ZonedDateTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.humuson.demo.redis.model.MessageModel;
import com.humuson.demo.redis.repository.RedisRepository;

@SpringBootTest
class RedisRepositoryTests {

	@Autowired
	private RedisRepository redisRepository;
	
	private MessageModel setupEntity;
	
	@BeforeEach
	public void setUp() {
		setupEntity = new MessageModel();
		setupEntity.setMessage("kimbs");
		setupEntity.setTimestamp(ZonedDateTime.now());
		redisRepository.save(setupEntity);
	}
	
	@AfterEach
	public void tearDown() {
		redisRepository.deleteById(setupEntity.getId());
	}

	@Test
	@DisplayName("redisRepository를 활용한 데이터 삽입 테스트")
	public void testRedisRepository() {
		// arrange
		MessageModel entity = this.setupEntity;		

		// act
		MessageModel find = redisRepository.findById(entity.getId()).orElseGet(() -> this.getTempMessageModel());

		// assert
		Assertions.assertEquals(entity, find);
	}

	@Test
	@DisplayName("redisRepository를 활용한 데이터 수정 테스트")
	public void testModifyRepository() {
		// arrange
		MessageModel entity = this.setupEntity;

		// act
		MessageModel find = redisRepository.findById(entity.getId()).orElseGet(() -> this.getTempMessageModel());
		find.setMessage("Modifyed message");
		redisRepository.save(find);

		// assert
		MessageModel modifyed = redisRepository.findById(find.getId()).orElseGet(() -> this.getTempMessageModel());
		Assertions.assertEquals(find.getId(), modifyed.getId());
		Assertions.assertEquals(find.getMessage(), modifyed.getMessage());
		Assertions.assertEquals(find.getTimestamp(), modifyed.getTimestamp());
	}

	@Test
	@DisplayName("redisRepository를 활용한 데이터 삭제 테스트")
	public void testDeleteRepository() {
		// arrange
		MessageModel entity = this.setupEntity;

		// act
		redisRepository.deleteById(entity.getId());
		
		// assert
		MessageModel find = redisRepository.findById(entity.getId()).orElseGet(() -> this.getTempMessageModel());
		
		Assertions.assertEquals(null, find.getId());
		Assertions.assertEquals(null, find.getMessage());
		Assertions.assertEquals(null, find.getTimestamp());
	}
	
	@Test
	@DisplayName("redisRepository를 활용한 모든 데이터 검색 테스트")
	public void testFindAllRepository() {
		// arrange
		
		// act
		Iterable<MessageModel> messages = redisRepository.findAll();
		
		// assert
		assertThat(messages).hasSize(1);
	}

	private MessageModel getTempMessageModel() {
		return new MessageModel();
	}
}

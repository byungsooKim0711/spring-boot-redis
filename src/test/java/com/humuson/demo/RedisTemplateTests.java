package com.humuson.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import com.humuson.demo.redis.model.MessageModel;

@SpringBootTest
public class RedisTemplateTests {
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	private Map<String, Object> setupMap;
	
	private MessageModel m1, m2, m3;
	
	@BeforeEach
	public void setUp() {
		m1 = new MessageModel();
		m1.setId("A");
		m1.setMessage("AAAAA");
		
		m2 = new MessageModel();
		m2.setId("B");
		m2.setMessage("BBBBB");
		
		m3 = new MessageModel();
		m3.setId("C");
		m3.setMessage("CCCCC");
		
		setupMap = new HashMap<>();
		setupMap.put(m1.getId(), m1);
		setupMap.put(m2.getId(), m2);
		setupMap.put(m3.getId(), m3);
	}
	
	@AfterEach
	public void tearDown() {
		redisTemplate.opsForHash().delete("kbs", m1.getId(), m2.getId(), m3.getId());
	}
	
	@Test
	@DisplayName("redisTemplate을 활용한 삽입 테스트")
	public void testInsertWithRedisTemplate() {
		// arrange
		redisTemplate.opsForHash().putAll("kbs", setupMap);
		
		// act
		List<Object> lists = redisTemplate.opsForHash().multiGet("kbs", Arrays.asList(m1.getId(), m2.getId(), m3.getId()));
		
		// assert
		assertThat(lists).hasSize(3).contains(m1, m2, m3);
	}
	
	@Test
	public void testRedisTemplateEntries() {
		// arrange
		redisTemplate.opsForHash().putAll("kbs", setupMap);
		
		// act
		Map<Object, Object> entries = redisTemplate.opsForHash().entries("kbs");
		
		// assert
		assertThat(entries).isNotEmpty().hasSize(3).containsKeys(m1.getId(), m2.getId(), m3.getId());
	}
	
	@Test
	public void testUpdateWithRedisTemplate() {
		// arrange
		redisTemplate.opsForHash().putAll("kbs", setupMap);
		
		// act
		m3.setMessage("Updated message");
		redisTemplate.opsForHash().put("kbs", m3.getId(), m3);
		
		// assert
		MessageModel updated = (MessageModel) redisTemplate.opsForHash().get("kbs", m3.getId());
		Assertions.assertEquals(m3.getMessage(), updated.getMessage());
	}
	
	@Test
	public void testDeleteWithRedisTemplate() {
		// arrange
		redisTemplate.opsForHash().putAll("kbs", setupMap);
		
		// act
		redisTemplate.opsForHash().delete("kbs", m2.getId());
		
		// assert
		Map<Object, Object> entries = redisTemplate.opsForHash().entries("kbs");
		assertThat(entries).isNotEmpty().hasSize(2).containsKeys(m1.getId(), m3.getId()).doesNotContainKey(m2.getId());
	}
}

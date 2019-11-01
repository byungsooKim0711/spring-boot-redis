package com.humuson.demo.redis.repository;

import org.springframework.data.repository.CrudRepository;

import com.humuson.demo.redis.model.MessageModel;

public interface RedisRepository extends CrudRepository<MessageModel, String>{

}

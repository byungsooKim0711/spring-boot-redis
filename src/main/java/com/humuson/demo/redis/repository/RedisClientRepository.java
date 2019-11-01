package com.humuson.demo.redis.repository;

import org.springframework.data.repository.CrudRepository;

import com.humuson.demo.redis.model.RedisClient;

public interface RedisClientRepository extends CrudRepository<RedisClient, String>{

}

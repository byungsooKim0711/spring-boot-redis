package com.humuson.demo.redis.model;

import java.io.Serializable;
import java.time.ZonedDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.Data;

@RedisHash("kbs-message")
@Data
public class MessageModel implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	private String message;
	
	private ZonedDateTime timestamp;
}

package com.humuson.demo.redis.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.Data;

@Data
@RedisHash("RedisClient")
public class RedisClient {

	@Id
	private String id;
	
	private String password;
	
	private String masterHost;
	
	private int masterMessagePort;
	
	private int masterReportPort;
	
	private int masterSendLineCount;
	
	private int masterReportLineCount;
	
	private String slaveHost;
	
	private int slaveMessageProt;
	
	private int slaveReportPort;
	
	private int slaveSendLineCount;
	
	private int slaveReportLineCount;
	
	private String useYn;
	
	private String encMethod;
	
	private String encKey;
	
	private int reportConsumerCount;
	
	private String userAtBizMsg;
	
	private String userFtBizMsg;
	
	private String useSmsMt;
	
	private String useLmsMt;
	
	private String useMmsMt;
	
	private String userAtResend;
	
	private String usePush;
	
	private String connectIp;
	
	private String userMtNumberCheck;
	
	private int mtSendGroupId;
	
	private int reSendGroupId;
	
	private int smtSendGroupId;
	
	private int lmtSendGroupId;
	
	private int mmtSendGroupId;
	
	private int mmtResendGroupId;
	
	private int userId;
	
	private int reportBrokerId;
}

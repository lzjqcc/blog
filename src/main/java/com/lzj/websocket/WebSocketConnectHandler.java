/*
 * Copyright 2014-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lzj.websocket;

import com.lzj.VO.ResponseVO;
import com.lzj.constant.FriendStatusEnum;
import com.lzj.dao.FriendDao;
import com.lzj.dao.dto.FriendDto;
import com.lzj.domain.Account;
import com.lzj.domain.Friend;
import com.lzj.helper.RedisTemplateHelper;
import com.lzj.security.AccountToken;
import com.lzj.service.impl.FriendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;


import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 为什么 使用原生的websocket不会走 SessionConnectEvent
 * 1,这个使用的Stomp （简单文本协议）
 * @param <S>
 */
@Component
public class WebSocketConnectHandler<S>
		implements ApplicationListener<SessionConnectEvent> {
	@Autowired
	private FriendDao friendDao;
	@Autowired
	private FriendService friendService;
	@Autowired
	private SimpMessageSendingOperations messagingTemplate;
	@Autowired
	private RedisTemplateHelper templateHelper;


	private Logger logger = LoggerFactory.getLogger(WebSocketConnectHandler.class);
	@Override
	public void onApplicationEvent(SessionConnectEvent event) {
		MessageHeaders headers = event.getMessage().getHeaders();
		Principal user = SimpMessageHeaderAccessor.getUser(headers);
		if (user == null) {
			return;
		}
		if (user instanceof AccountToken) {
			Map<String, Object> map = new HashMap<>();
			AccountToken accountToken = (AccountToken) user;
			String id = SimpMessageHeaderAccessor.getSessionId(headers);

			FriendDto dto = new FriendDto();
			dto.setFriendId(accountToken.getAccount().getId());
			dto.setStatus(FriendStatusEnum.AGREE.code);
			Map<Integer, Friend> map1  = friendDao.findFriends(dto).stream().collect(Collectors.toMap(Friend::getCurrentAccountId , t-> t));
			List<Friend> onlineFriendId = friendService.findOnlineFriends(accountToken.getAccount().getId()).getResult();
			// 将自己作为friend的记录推送给已经登录的朋友用户
			// 前端只订阅自己的id, 由后端推送
			for (Friend friend : onlineFriendId) {
				if ( !Objects.isNull(this.templateHelper.get(friend.getFriendId() + ""))) {
					logger.info("推送在线消息，routing key = {}", WebSocketConstans.NOTIFY_FRIEND_SIGN+"/"+ friend.getFriendId());
					this.messagingTemplate.convertAndSend(WebSocketConstans.NOTIFY_FRIEND_SIGN +"/"+friend.getFriendId(), map1.get(friend.getFriendId()));
				}
			}
			templateHelper.put(accountToken.getAccount().getId()+"", id);
		}

	}
}

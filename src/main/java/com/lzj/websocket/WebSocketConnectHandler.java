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

import com.lzj.dao.FriendDao;
import com.lzj.domain.Account;
import com.lzj.domain.Friend;
import com.lzj.helper.RedisTemplateHelper;
import com.lzj.security.AccountToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;


import java.security.Principal;
import java.util.Arrays;
import java.util.Calendar;
@Component
public class WebSocketConnectHandler<S>
		implements ApplicationListener<SessionConnectEvent> {
	@Autowired
	private FriendDao friendDao;
	@Autowired
	private SimpMessageSendingOperations messagingTemplate;
	@Autowired
	private RedisTemplateHelper templateHelper;
	@Override
	public void onApplicationEvent(SessionConnectEvent event) {
		MessageHeaders headers = event.getMessage().getHeaders();
		Principal user = SimpMessageHeaderAccessor.getUser(headers);
		if (user == null) {
			return;
		}
		if (user instanceof AccountToken) {
			AccountToken accountToken = (AccountToken) user;
			String id = SimpMessageHeaderAccessor.getSessionId(headers);
			this.messagingTemplate.convertAndSend("/topic/friends/signin",
					Arrays.asList(user.getName()));
			templateHelper.put(accountToken.getAccount().getId()+"", id);
		}

	}
}

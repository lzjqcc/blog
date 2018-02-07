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

import com.lzj.helper.RedisTemplateHelper;
import com.lzj.security.AccountToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;


import java.security.Principal;
import java.util.Arrays;
@Component
public class WebSocketDisconnectHandler<S>
		implements ApplicationListener<SessionDisconnectEvent> {
	@Autowired
	private SimpMessageSendingOperations messagingTemplate;
	@Autowired
	private RedisTemplateHelper helper;
	@Override
	public void onApplicationEvent(SessionDisconnectEvent event) {
		MessageHeaders headers = event.getMessage().getHeaders();
		Principal user = SimpMessageHeaderAccessor.getUser(headers);
		String id = event.getSessionId();
		if (user == null) {
			return;
		}
		if (user instanceof AccountToken) {
			AccountToken token = (AccountToken) user;

			helper.remove(token.getAccount().getId() + "");
			this.messagingTemplate.convertAndSend("/topic/friend/signout",
					Arrays.asList(token.getAccount().getUserName()));
		}


	}
}

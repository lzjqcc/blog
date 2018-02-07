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

import com.lzj.constant.FriendStatusEnum;
import com.lzj.dao.FriendDao;
import com.lzj.dao.dto.FriendDto;
import com.lzj.domain.Friend;
import com.lzj.helper.RedisTemplateHelper;
import com.lzj.security.AccountToken;
import com.lzj.service.impl.FriendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;


import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class WebSocketDisconnectHandler<S>
		implements ApplicationListener<SessionDisconnectEvent> {
	private Logger logger = LoggerFactory.getLogger(WebSocketDisconnectHandler.class);
	@Autowired
	private SimpMessageSendingOperations messagingTemplate;
	@Autowired
	private RedisTemplateHelper helper;
	@Autowired
	private FriendDao friendDao;
	@Autowired
	private FriendService friendService;
	@Override
	public void onApplicationEvent(SessionDisconnectEvent event) {
		MessageHeaders headers = event.getMessage().getHeaders();
		Principal user = SimpMessageHeaderAccessor.getUser(headers);
		String id = event.getSessionId();
		if (user == null) {
			return;
		}
		if (user instanceof AccountToken) {
			AccountToken accountToken = (AccountToken) user;
			FriendDto dto = new FriendDto();
			dto.setFriendId(accountToken.getAccount().getId());
			dto.setStatus(FriendStatusEnum.AGREE.code);
			Map<Integer, Friend> map1  = friendDao.findFriends(dto).stream().collect(Collectors.toMap(Friend::getCurrentAccountId , t-> t));
			List<Friend> onlineFriendId = friendService.findOnlineFriends(accountToken.getAccount().getId()).getResult();
			for (Friend friend : onlineFriendId) {
				if ( !Objects.isNull(this.helper.get(friend.getFriendId() + ""))) {
					logger.info("推送消息，routing key = {}", WebSocketConstans.NOTIFY_FRIEND_SIGN_OUT+"/"+ friend.getFriendId());
					this.messagingTemplate.convertAndSend(WebSocketConstans.NOTIFY_FRIEND_SIGN_OUT +"/"+friend.getFriendId(), map1.get(friend.getFriendId()));
				}
			}
			helper.remove(accountToken.getAccount().getId() + "");

		}


	}
}

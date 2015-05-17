package com.ahuges.handler;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ahuges.po.MessageBean;
import com.ahuges.po.TextMessage;
import com.ahuges.util.Dict;

public class MessageHandler {
	private static Logger logger = Logger.getLogger(MessageHandler.class);
	public MessageBean getReplayMessageBean(Map<String, String> reqm) {
		logger.info("返回消息bean获取开始-----------------");
		MessageBean repMsgB = null;
		logger.debug("msgType:"+reqm.get(Dict.MsgType));
		
		TextMessage txmsg = new TextMessage();
		txmsg.setFromUserName(reqm.get(Dict.ToUserName));
		txmsg.setToUserName(reqm.get(Dict.FromUserName));
		
		switch (reqm.get(Dict.MsgType)) {
		case Dict.MsgType_TEXT:
			logger.debug("获取的是文字消息");
			txmsg.setContent("你发的消息是："+reqm.get("Content"));
			txmsg.setCreateTime(new Date().getTime());
			txmsg.setMsgType(Dict.MsgType_TEXT);
			repMsgB = txmsg;
			break;
		case Dict.MsgType_EVENT:
			logger.debug("获取的是事件消息");
			switch(reqm.get(Dict.Event)){
			case Dict.MsgType_EVENT_SUBSCRIBE:
				logger.debug("新关注事件");
				txmsg.setContent("欢迎关注！这里是我的小窝~");
				txmsg.setCreateTime(new Date().getTime());
				txmsg.setMsgType(Dict.MsgType_TEXT);
				repMsgB = txmsg;
				break;
			case Dict.MsgType_EVENT_UNSUBSCRIBE:
				logger.debug("取消关注事件");
				break;
			default:
				break;
			}
		default:
			break;
		}
		logger.info("返回消息bean获取结束-----------------");
		return repMsgB;
	}
}

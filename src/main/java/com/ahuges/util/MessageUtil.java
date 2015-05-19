package com.ahuges.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.dom4j.Element;

import com.ahuges.handler.MessageHandler;
import com.ahuges.po.MessageBean;
import com.ahuges.po.TextMessage;
import com.thoughtworks.xstream.XStream;

public class MessageUtil {
	private static Logger logger = Logger.getLogger(MessageUtil.class);
	private static MessageHandler messageHandler = new MessageHandler();
	/**
	 * xml转换为map对象类型
	 * @param req
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> xmlToMap(HttpServletRequest req) throws IOException, DocumentException{
		logger.info("xml-map开始--------：");
		Map <String,String> map = new HashMap<String,String>();
		SAXReader reader = new SAXReader();
		
		InputStream ins = req.getInputStream();
		Document doc = reader.read(ins);
		
		Element root = doc.getRootElement();
		List<Element> list = root.elements();
		
		for(Element e : list){
			map.put(e.getName(), e.getText());
			logger.debug("获取的消息："+e.getName()+"="+e.getText());
		}
		ins.close();
		logger.info("xml-map结束--------：");
		return map;
	}
	/**
	 * 将文本消息转换为xml
	 * @param textmessage
	 * @return
	 */
	public static String textMessageToXML(TextMessage textmessage){
		XStream xstream = new XStream();
		xstream.alias("xml", textmessage.getClass());
		return xstream.toXML(textmessage);
	}
	/**
	 * 获取回复给腾讯的xml报文
	 * @param reqm 腾讯发送过来的map格式报文
	 * @return
	 */
	public static String getReplayXML(Map<String, String> reqm){
		logger.info("返回消息String获取开始-----------------");
		String replayMessage = null;
		MessageBean msgb = messageHandler.getReplayMessageBean(reqm);
		if(null==msgb || "".equals(msgb))
			return "";
		switch (msgb.getMsgType()) {
		case Dict.MsgType_TEXT:
			logger.info("返回的消息类型是文字消息");
			TextMessage txmsg = (TextMessage) msgb;
			replayMessage = textMessageToXML(txmsg);
			logger.info("文字消息返回：\n"+replayMessage);
			break;
		case Dict.MsgType_LINK:
			logger.info("返回的消息类型是链接消息");
			break;
		default:
			break;
		}
		logger.info("返回消息获取结束-----------------");
		return replayMessage;
	}
}

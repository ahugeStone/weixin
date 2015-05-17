package com.ahuges.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;

import com.ahuges.util.CheckUtil;
import com.ahuges.util.MessageUtil;
import com.ahuges.util.SendUtil;

public class WeixinServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(WeixinServlet.class);
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		logger.info("开始握手------------");
		
		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		String echostr = req.getParameter("echostr");
		logger.info("signature:"+signature);
		logger.info("timestamp:"+timestamp);
		logger.info("nonce:"+nonce);
		logger.info("echostr:"+echostr);
		PrintWriter out = resp.getWriter();
		try{
			if(CheckUtil.checkSignature(signature, timestamp, nonce)){
				logger.info("握手成功-----------");
				out.print(echostr);
			}else{
				logger.info("握手失败-----------");
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			out.close();
		}
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		logger.info("******servlet开始******");
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		try {
			 //格式化接受的xml报文
			Map<String, String> reqm = MessageUtil.xmlToMap(req);
			//回复消息
			SendUtil.send(resp, MessageUtil.getReplayXML(reqm));
		} catch (DocumentException e) {
			e.printStackTrace();
		}finally{
			logger.info("******servlet结束******");
		}
	}
}

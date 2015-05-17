package com.ahuges.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ahuges.servlet.WeixinServlet;

public class SendUtil {
	private static Logger logger = Logger.getLogger(WeixinServlet.class);
	/**
	 * 同步回复腾讯消息方法
	 * @param resp
	 * @param msg
	 */
	public static void send(HttpServletResponse resp ,String msg){
		PrintWriter out = null;
		try {
			logger.info("发送数据开始");
			out = resp.getWriter();
			out.print(msg);
			logger.info("发送数据结束");
		} catch (IOException e) {
			e.printStackTrace();
			logger.info("发送数据到腾讯出现异常！");
		}finally{
			out.close();
			logger.info("发送关闭成功");
		}
	}
}

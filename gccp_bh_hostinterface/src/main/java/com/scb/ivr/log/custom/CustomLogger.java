package com.scb.ivr.log.custom;

//import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import com.util.Logging;

/**
 * @author TA
 *
 */

@Component
public class CustomLogger {

	public static Logger getLogger(String sessionID) {
		//Logger sessionLogger = LogManager.getContext().getLogger(GlobalConstants.HostLog_JUNIT);
		//Logger sessionLogger = com.scb.avaya.logging.CustomLogger.getLogger(sessionID);
		Logger sessionLogger = Logging.LoggerConfiguration(sessionID);
//		org.apache.logging.log4j.Logger sessionLogger = org.apache.logging.log4j.LogManager.getContext().getLogger(GlobalConstants.HostLog);

		//Logger sessionLogger = LogManager.getLogger(GlobalConstants.HostSessionLog);

		return sessionLogger;
	}
}

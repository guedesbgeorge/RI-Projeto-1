package crawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;

import org.apache.commons.io.IOUtils;

import crawlercommons.robots.BaseRobotRules;
import crawlercommons.robots.SimpleRobotRulesParser;

public class Robot {
	private static HashMap<String, BaseRobotRules> robotsRules = new HashMap<String, BaseRobotRules>();
	private static String robotName = "RobotIRProject";
	
	public static BaseRobotRules parseRobotsFile(String url) {
		try {
			String hostId = getDomain(url);
			return (new SimpleRobotRulesParser()).parseContent(hostId, IOUtils.toByteArray(new URL(hostId + "/robots.txt").openStream()), "text/plain", robotName);
		} catch (IOException e) {
			e.getMessage();
		}
		return null;
	}
	
	public static boolean isAllowed(String url) {
		try {
			URI uri = new URI(url);
			BaseRobotRules rules = robotsRules.get(uri.getHost());
			if(rules == null) {
				rules = Robot.parseRobotsFile(url);
                robotsRules.put(uri.getHost(), rules);
			}
			
			if(rules == null)
				return true;
			
			return rules.isAllowed(url);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public static String getDomain(String url) {
		URL urlObj = null;
		try {
			urlObj = new URL(url);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String hostId = urlObj.getProtocol() + "://" + urlObj.getAuthority();
		return hostId;
	}


}

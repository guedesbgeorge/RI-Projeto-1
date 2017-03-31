package crawler;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;

import crawlercommons.robots.BaseRobotRules;
import crawlercommons.robots.SimpleRobotRulesParser;

public class Robot {
	private static HashMap<String, BaseRobotRules> robotsRules = new HashMap<String, BaseRobotRules>();

	public static BaseRobotRules parseRobotsFile(String url) {
		try {
			URL urlObj = new URL(url);
			URI uri = new URI(urlObj.getProtocol() + "://" + urlObj.getAuthority() + "/robots.txt");
			String content = uri.toURL().toString();
			
			return (new SimpleRobotRulesParser()).parseContent(uri.toString(), content.getBytes(), "text/plain", "robot" + Integer.toString(robotsRules.size()));
		} catch (MalformedURLException | URISyntaxException e) {
			e.printStackTrace();
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
			
			System.out.println(rules.isAllowed(url));
			return rules.isAllowed(url);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}


}

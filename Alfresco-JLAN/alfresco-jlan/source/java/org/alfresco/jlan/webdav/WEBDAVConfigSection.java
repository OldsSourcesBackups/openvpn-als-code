package org.alfresco.jlan.webdav;

import java.net.InetAddress;

import org.alfresco.jlan.server.config.ConfigSection;
import org.alfresco.jlan.server.config.ServerConfiguration;

public class WEBDAVConfigSection extends ConfigSection{

	// Web DAV configuration section name	  
	public static final String SectionName = "WEBDAV";

	//  Bind Host . A port of -1 indicates do not start FTP server.	  
	private String host;
	private int m_webDavPort = -1;
	private String m_rootDirectory;
	private int maxConnection;
	private int listenOnPort;
	
	//  WEBDAV  debug flags	  
	private int m_webDavDebug;


	public WEBDAVConfigSection(ServerConfiguration config) {
		super(SectionName, config);
	}


	public String getHost() {
		return host;
	}


	public void setHost(String host) {
		this.host = host;
	}


	public int getM_webDavPort() {
		return m_webDavPort;
	}


	public void setM_webDavPort(int davPort) {
		m_webDavPort = davPort;
	}


	public String getM_rootDirectory() {
		return m_rootDirectory;
	}


	public void setM_rootDirectory(String directory) {
		m_rootDirectory = directory;
	}


	public int getM_webDavDebug() {
		return m_webDavDebug;
	}


	public void setM_webDavDebug(int davDebug) {
		m_webDavDebug = davDebug;
	}


	public int getMaxConnection() {
		return maxConnection;
	}


	public void setMaxConnection(int maxConnection) {
		this.maxConnection = maxConnection;
	}


	public int getListenOnPort() {
		return listenOnPort;
	}


	public void setListenOnPort(int listenOnPort) {
		this.listenOnPort = listenOnPort;
	}





}

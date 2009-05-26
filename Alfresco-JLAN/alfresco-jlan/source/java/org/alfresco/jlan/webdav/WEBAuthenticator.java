package org.alfresco.jlan.webdav;

import org.alfresco.config.ConfigElement;
import org.alfresco.jlan.ftp.FTPSrvSession;
import org.alfresco.jlan.server.auth.ClientInfo;
import org.alfresco.jlan.server.config.InvalidConfigurationException;
import org.alfresco.jlan.server.config.ServerConfiguration;
/**
 * @author Kamal hossain
 *
 */
public interface WEBAuthenticator {
	  /**
	   * Initialize the authenticator
	   * 
	   * @param config ServerConfiguration
	   * @param params ConfigElement
	   * @exception InvalidConfigurationException
	   */
	  public void initialize(ServerConfiguration config, ConfigElement params)
	    throws InvalidConfigurationException;
	  
	  /**
	   * Authenticate the user
	   * 
	   * @param cInfo ClientInfo
	   * @param sess FTPSrvSession
	   * @return boolean
	   */
	  public boolean authenticateUser( ClientInfo cInfo, FTPSrvSession sess);	  
}

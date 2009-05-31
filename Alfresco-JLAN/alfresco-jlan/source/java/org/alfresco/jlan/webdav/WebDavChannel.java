package org.alfresco.jlan.webdav;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;

/**
 * @author Kamal Hossain
 *
 */

public class WebDavChannel {	 	
	 final HttpClient client;
	 final String rootPath;
	 HostConfiguration hostConfig;
	
	public WebDavChannel(WEBDAVConfigSection config) {
		rootPath=config.getM_rootDirectory();		
		hostConfig = new HostConfiguration();
		hostConfig.setHost(config.getHost(),config.getM_webDavPort());

		HttpConnectionManagerParams params = new HttpConnectionManagerParams();		
		params.setMaxConnectionsPerHost(hostConfig, config.getMaxConnection());

		HttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
		connectionManager.setParams(params);		
		client = new HttpClient(connectionManager);

	}


	private void setUserCredential(Credentials credential)
	{
		client.getState().setCredentials(AuthScope.ANY, credential);	
	}


	public HttpClient getChannel(Credentials credential) {
		setUserCredential(credential);
		return client;
	}
	
	public HttpClient getChannel() {
		return client;
	}

	public String getRootPath() {
		return rootPath;
	}

}

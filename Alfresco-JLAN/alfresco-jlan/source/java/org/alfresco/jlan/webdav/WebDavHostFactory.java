package org.alfresco.jlan.webdav;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;


/**
 * 
 * @author Kamal Hossain
 *
 */

public class WebDavHostFactory {

	private HttpClient client;
	public static final String PROTOCALL="http://";
	public static WebDavHostFactory webDavhost=null;

	private WebDavHostFactory(){		
	}


	public static WebDavHostFactory newInstence(String host,int port,int max_conn)
	{
		if(webDavhost==null)
		{	
			webDavhost=new WebDavHostFactory();
			init_host(host,port,max_conn);
		}

		return webDavhost;
	}


	public HttpClient connection()
	{
		return client;
	}


	public void closeConnection()
	{

	}

	private static void init_host(String host,int port,int max_conn)
	{
		HostConfiguration hostConfig = new HostConfiguration();
		hostConfig.setHost(host,port);

		HttpConnectionManagerParams params = new HttpConnectionManagerParams();		
		params.setMaxConnectionsPerHost(hostConfig, max_conn);

		HttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
		connectionManager.setParams(params);
		webDavhost.client = new HttpClient(connectionManager);
	}

}

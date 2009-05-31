package org.alfresco.jlan.webdav;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.alfresco.jlan.debug.Debug;
import org.alfresco.jlan.ftp.FTPConfigSection;
import org.alfresco.jlan.server.NetworkServer;
import org.alfresco.jlan.server.ServerListener;
import org.alfresco.jlan.server.config.ConfigurationListener;
import org.alfresco.jlan.server.config.InvalidConfigurationException;
import org.alfresco.jlan.server.config.ServerConfiguration;

/**
 * @author Kamal hossain
 *
 */
public class WebDavProvider extends NetworkServer implements Runnable, ConfigurationListener{

	private Thread m_srvThread;
	//	Session group
	protected static final ThreadGroup WebDavhreadGroup = new ThreadGroup( "WEBDAVSessions");

	//	Listen backlog for the server socket
	protected static final int LISTEN_BACKLOG = 10;

	public static final int defaultConeection=10;	

	//	Server socket
	private ServerSocket m_srvSock;

	//	webdav configureation
	private WEBDAVConfigSection m_configSection;



	public WebDavProvider(ServerConfiguration config) {
		super("WEBDAV", config);
		m_configSection=(WEBDAVConfigSection) config.getConfigSection( WEBDAVConfigSection.SectionName);	
		getConfiguration().addListener(this);
	}



	@Override
	public void run() {
		Debug.println("WebDav provider host "+m_configSection.getHost());
		Debug.println("WebDav provider starting on port "+getPort());

		try 
		{
			m_srvSock = new ServerSocket(getPort(), LISTEN_BACKLOG);

			//  Wait for incoming connection requests
			while ( hasShutdown() == false) {
				Socket sessSock = getSocket().accept();
				sessSock.setTcpNoDelay(true);
				try
				{

					WebDavSession session=new WebDavSession(this,sessSock,new WebDavChannel(m_configSection));

					Thread sessionThread=new Thread(WebDavhreadGroup,session);
					sessionThread.setDaemon(true);
					sessionThread.start();

				}catch(IOException e)
				{
					Debug.println("Unable to estublish session : " + e.toString());
					Debug.println(e);					
				}


			}			

		} catch (IOException e) {
			if (hasShutdown() == false) {
				Debug.println("Socket error : " + e.toString());
				Debug.println(e);
				setException(e);
				fireServerEvent(ServerListener.ServerError);
			}			
		}

	}


	@Override
	public int configurationChanged(int id, ServerConfiguration config,
			Object newVal) throws InvalidConfigurationException {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void shutdownServer(boolean immediate) {
		
		setShutdown(true);
		try {
			if ( getSocket() != null)
				getSocket().close();
		}
		catch (IOException ex) {
		}
		m_configSection.closeConfig();
		
		//	Fire a shutdown notification event    
		fireServerEvent(ServerListener.ServerShutdown);
		
	}


	@Override
	public void startServer() {
		m_srvThread = new Thread(this);
		m_srvThread.setName("WebDav Provider");
		m_srvThread.start();	
	}

	/**
	 * Return the webdav server configuration
	 * 
	 * @return WEBDAVConfigSection
	 */
	protected final WEBDAVConfigSection getFTPConfiguration() {
		return m_configSection;
	}

	/**
	 * Return the server socket
	 * 
	 * @return ServerSocket
	 */
	protected final ServerSocket getSocket() {
		return m_srvSock;
	}

	/**
	 * Return the Lesten to port
	 * 
	 * @return int
	 */
	public final int getPort() {
		return getFTPConfiguration().getListenOnPort();
	}

}

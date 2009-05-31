package org.alfresco.jlan.webdav;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

import org.alfresco.jlan.debug.Debug;
import org.alfresco.jlan.server.SrvSession;
import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
/**
 * @author Kamal hossain
 *
 */
public class WebDavSession extends SrvSession implements Runnable {
	private InputStream m_in;
	private OutputStreamWriter m_out;
	private UsernamePasswordCredentials credintial;
	private final WebDavChannel channel;
	private byte[] m_inbuf;
	private DAVUser sessionUser;

	protected final static String CRLF = "\r\n";

	// Session socket
	private Socket m_sock;


	public WebDavSession(WebDavProvider provider,Socket sock,WebDavChannel channel) throws IOException {
		super(-1,provider,"WEBDAV",null);
		m_sock=sock;
		m_in = m_sock.getInputStream();
		m_out = new OutputStreamWriter(m_sock.getOutputStream());
		this.channel=channel;		
		m_inbuf = new byte[512];
	}


	@Override
	public void run() {
		System.out.println();
		System.out.println("Session estublished");	
		try 
		{
			sendResponse(200, "Ok");			

			MethodExecutor cmd_parser=new MethodExecutor(channel,this);
			int i=1;
			while(m_sock!=null){
				System.out.println("Reading Command:"+i);
				int rdlen=m_in.read(m_inbuf);



				if(rdlen==-1)
				{
					closeSession();
					break;
				}

				if ( rdlen > 0) {
					while (rdlen > 0 && m_inbuf[rdlen - 1] == '\r' || m_inbuf[rdlen - 1] == '\n')
						rdlen--;
				}

				String cmd = new String(m_inbuf,0,rdlen);
				System.out.println(cmd);
				cmd_parser.execute(cmd);

				i++;
			}


		} catch (IOException e) {			
		}

	}

	public InputStream getM_in() {
		return m_in;
	}


	public OutputStreamWriter getM_out() {
		return m_out;
	}


	/**
	 * Close the FTP session, and associated data socket if active
	 */
	public final void closeSession() {
		// Call the base class
		System.out.print("Session Closed");
		super.closeSession();

		if ( m_sock != null) {
			try {
				m_sock.close();
			}
			catch (Exception ex) {
			}
			m_sock = null;
		}

		if ( m_in != null) {
			try {
				m_in.close();
			}
			catch (Exception ex) {
			}
			m_in = null;
		}

		if ( m_out != null) {
			try {
				m_out.close();
			}
			catch (Exception ex) {
			}
			m_out = null;
		}


	}



	public DAVUser getSessionUser() {
		return sessionUser;
	}


	public void setSessionUser(DAVUser sessionUser) {
		this.sessionUser = sessionUser;
	}


	public void setCredintial(UsernamePasswordCredentials credintial) {
		this.credintial = credintial;
	}


	public UsernamePasswordCredentials getCredintial() {
		return credintial;
	}


	public void setCredintial(String user,String pws) {
		this.credintial = new UsernamePasswordCredentials(user,pws);
	}

	public final byte[] receiveFile() throws IOException					  
	{
		
		ByteArrayOutputStream bAOut = new ByteArrayOutputStream();
		int d;
		while((d=m_in.read())!=-1)
			bAOut.write(d);
		
		return bAOut.toByteArray();
	}

	public final void sendResponse(int stsCode, String msg) throws IOException {

		StringBuffer outbuf = new StringBuffer(10 + (msg != null ? msg.length() : 0));
		outbuf.append(stsCode);
		outbuf.append(" ");

		if ( msg != null)
			outbuf.append(msg);

		outbuf.append(CRLF);

		// Output the FTP response

		if ( m_out != null) {
			m_out.write(outbuf.toString());
			m_out.flush();
		}
	}

	public final void sendResponse(String msg)
	throws IOException {

		if ( m_out != null) {
			m_out.write(msg);
			m_out.write(CRLF);
			m_out.flush();
		}

	}

	@Override
	public InetAddress getRemoteAddress() {
		// TODO Auto-generated method stub
		return null;
	}
}

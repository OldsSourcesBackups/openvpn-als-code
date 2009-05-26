package org.alfresco.jlan.webdav;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

import org.alfresco.jlan.server.SrvSession;
/**
 * @author Kamal hossain
 *
 */
public class WebDavSession extends SrvSession implements Runnable {
	private InputStream m_in;
	private OutputStreamWriter m_out;

	// Session socket
	private Socket m_sock;

	
	public WebDavSession(WebDavProvider provider,Socket sock) throws IOException {
		super(-1,provider,"WEBDAV",null);
		m_sock=sock;
		m_in = m_sock.getInputStream();
		m_out = new OutputStreamWriter(m_sock.getOutputStream());
	}


	@Override
	public void run() {
		System.out.print("Session estublished");

	}

	@Override
	public InetAddress getRemoteAddress() {
		// TODO Auto-generated method stub
		return null;
	}
}

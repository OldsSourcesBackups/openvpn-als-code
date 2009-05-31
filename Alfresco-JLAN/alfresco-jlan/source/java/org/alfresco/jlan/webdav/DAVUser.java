package org.alfresco.jlan.webdav;

import org.apache.commons.httpclient.UsernamePasswordCredentials;



/**
 * @author Kamal Hossain
 *
 */

public class DAVUser {
    private String user="";
    private String pws="";
    private UsernamePasswordCredentials creds;
    
	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public String getPws() {
		return pws;
	}
	
	public void setPws(String pws) {
		this.pws = pws;
	}
	
	public UsernamePasswordCredentials getCreds() {
		return creds;
	}
	
	public void setCreds(UsernamePasswordCredentials creds) {
		this.creds = creds;
	}
    
    
    
    

}

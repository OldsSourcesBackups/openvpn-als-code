
				/*
 *  OpenVPNALS
 *
 *  Copyright (C) 2003-2006 3SP LTD. All Rights Reserved
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU General Public License
 *  as published by the Free Software Foundation; either version 2 of
 *  the License, or (at your option) any later version.
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public
 *  License along with this program; if not, write to the Free Software
 *  Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */
			
package net.openvpn.als.applications.types;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jdom.Element;

import com.maverick.multiplex.Request;
import net.openvpn.als.agent.AgentTunnel;
import net.openvpn.als.agent.DefaultAgentManager;
import net.openvpn.als.applications.ApplicationLauncherType;
import net.openvpn.als.applications.ApplicationService;
import net.openvpn.als.applications.ApplicationShortcut;
import net.openvpn.als.extensions.ExtensionDescriptor;
import net.openvpn.als.extensions.ExtensionException;
import net.openvpn.als.policyframework.LaunchSession;
import net.openvpn.als.policyframework.Resource.LaunchRequirement;
import net.openvpn.als.security.SessionInfo;

/**
 * Implementation of an
 * {@link net.openvpn.als.applications.ApplicationLauncherType} that allows
 * launching of native applications installed the client machine using a running
 * Agent.
 */
public class ExecutableType implements ApplicationLauncherType {

	final static Log log = LogFactory.getLog(ExecutableType.class);

	/**
	 * Type name
	 */
	public final static String TYPE = "executable";

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.openvpn.als.extensions.ExtensionType#start(net.openvpn.als.extensions.ExtensionDescriptor,
	 *      org.jdom.Element)
	 */
	public void start(ExtensionDescriptor descriptor, Element element) throws ExtensionException {

		if (element.getName().equals(TYPE)) {
			verifyExecutable(element);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.openvpn.als.extensions.ExtensionType#verifyRequiredElements()
	 */
	public void verifyRequiredElements() throws ExtensionException {

	}

	/* (non-Javadoc)
	 * @see net.openvpn.als.applications.ApplicationLauncherType#launch(java.util.Map, net.openvpn.als.extensions.ExtensionDescriptor, net.openvpn.als.applications.ApplicationShortcut, org.apache.struts.action.ActionMapping, net.openvpn.als.policyframework.LaunchSession, java.lang.String, javax.servlet.http.HttpServletRequest)
	 */
	public ActionForward launch(Map<String, String> parameters, ExtensionDescriptor descriptor, ApplicationShortcut shortcut,
								ActionMapping mapping, LaunchSession launchSession, String returnTo, HttpServletRequest request) throws ExtensionException {
		if (log.isInfoEnabled())
			log.info("Launching client application " + shortcut.getResourceName());

		// SessionInfo session =
		// LogonControllerFactory.getInstance().getSessionInfo(request);
		if (DefaultAgentManager.getInstance().hasActiveAgent(launchSession.getSession())) {
			try {
				Request agentRequest = ((ApplicationService) DefaultAgentManager.getInstance().getService(ApplicationService.class)).launchApplication(launchSession);
				AgentTunnel agent = DefaultAgentManager.getInstance().getAgentBySession(launchSession.getSession());
				if (!agent.sendRequest(agentRequest, true, 60000)) {
					throw new ExtensionException(ExtensionException.AGENT_REFUSED_LAUNCH);
				}
			} catch (ExtensionException ee) {
				throw ee;
			} catch (Exception e) {
				throw new ExtensionException(ExtensionException.INTERNAL_ERROR, e);
			}
		} else {
			throw new ExtensionException(ExtensionException.NO_AGENT);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.openvpn.als.extensions.ExtensionType#isHidden()
	 */
	public boolean isHidden() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.openvpn.als.extensions.ExtensionType#getType()
	 */
	public String getType() {
		return TYPE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.openvpn.als.extensions.ExtensionType#stop()
	 */
	public void stop() throws ExtensionException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.openvpn.als.applications.ApplicationLauncherType#isAgentRequired(net.openvpn.als.applications.ApplicationShortcut,
	 *      net.openvpn.als.extensions.ExtensionDescriptor)
	 */
	public boolean isAgentRequired(ApplicationShortcut shortcut, ExtensionDescriptor descriptor) {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.openvpn.als.extensions.ExtensionType#activate()
	 */
	public void activate() throws ExtensionException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.openvpn.als.extensions.ExtensionType#canStop()
	 */
	public boolean canStop() throws ExtensionException {
		return true;
	}

	private void verifyExecutable(Element element) throws ExtensionException {
		for (Iterator it = element.getChildren().iterator(); it.hasNext();) {
			Element e = (Element) it.next();
			if (e.getName().equalsIgnoreCase("if")) {
				verifyExecutable(e);
			} else if (!e.getName().equalsIgnoreCase("arg") && !e.getName().equalsIgnoreCase("program") && !e.getName().equalsIgnoreCase("jvm")) {
				throw new ExtensionException(ExtensionException.FAILED_TO_PROCESS_DESCRIPTOR, "Unexpected element <" + e.getName()
					+ "> found in <executable>");
			}
		}

	}

	/* (non-Javadoc)
	 * @see net.openvpn.als.extensions.ExtensionType#descriptorCreated(org.jdom.Element)
	 */
	public void descriptorCreated(Element element, SessionInfo session) throws IOException {		
	}

	/* (non-Javadoc)
	 * @see net.openvpn.als.extensions.ExtensionType#getTypeBundle()
	 */
	public String getTypeBundle() {
		return "applications";
	}

	/* (non-Javadoc)
	 * @see net.openvpn.als.applications.ApplicationLauncherType#isServiceSide()
	 */
	public boolean isServerSide() {
		return false;
	}

    /* (non-Javadoc)
     * @see net.openvpn.als.extensions.ExtensionType#getLaunchRequirement()
     */
    public LaunchRequirement getLaunchRequirement() {
        return LaunchRequirement.LAUNCHABLE;
    }
}
<%@ page contentType="text/javascript;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<jsp:directive.page import="com.adito.boot.SystemProperties"/>

/* The server absolutely requires cookies to be able to function correctly
 * This script fragment detects if they are available and directs to an 
 * information page if not.
 */
 
if(!(document.cookie && document.cookie.indexOf("<%= SystemProperties.get("adito.cookie", "JSESSIONID") %>") > -1)) {
	window.location = '/noCookies.do';
}
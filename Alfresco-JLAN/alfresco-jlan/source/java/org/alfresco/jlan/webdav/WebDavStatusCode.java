package org.alfresco.jlan.webdav;

import java.util.Hashtable;

public class WebDavStatusCode {
	private static Hashtable StatusCodes = new Hashtable();

	public static final int CONTINUE = 100;
	public static final int SWITCHING_PROTOCOLS = 101;
	public static final int PROCESSING = 102;

	public static final int OK = 200;
	public static final int CREATED = 201;
	public static final int ACCEPTED = 202;
	public static final int NON_AUTHORITATIVE_INFORMATION = 203;
	public static final int NO_CONTENT = 204;
	public static final int RESET_CONTENT = 205;
	public static final int PARTIAL_CONTENT = 206;
	public static final int MULTI_STATUS = 207;
	
	public static final int ALREADY_REPORTED = 208;

	public static final int MULTIPLE_CHOICES = 300;
	public static final int MOVED_PERMANENTLY = 301;
	public static final int MOVED_TEMPORARILY = 302;
	public static final int SEE_OTHER = 303;
	public static final int NOT_MODIFIED = 304;
	public static final int USE_PROXY = 305;

	public static final int BAD_REQUEST = 400;
	public static final int UNAUTHORIZED = 401;
	public static final int PAYMENT_REQUIRED = 402;
	public static final int FORBIDDEN = 403;
	public static final int NOT_FOUND = 404;
	public static final int METHOD_NOT_ALLOWED = 405;
	public static final int NOT_ACCEPTABLE = 406;
	public static final int PROXY_AUTHENTICATION_REQUIRED = 407;
	public static final int REQUEST_TIMEOUT = 408;
	public static final int CONFLICT = 409;
	public static final int GONE = 410;
	public static final int LENGTH_REQUIRED = 411;
	public static final int PRECONDITION_FAILED = 412;
	public static final int REQUEST_TOO_LONG = 413;
	public static final int UNSUPPORTED_MEDIA_TYPE = 415;
	public static final int REQUESTED_RANGE_NOT_SATISFIABLE = 416;
	public static final int EXPECTATION_FAILED = 417;
	
	public static final int INSUFFICIENT_SPACE_ON_RESOURCE = 419;
	
	public static final int METHOD_FAILURE = 420;
	public static final int UNPROCESSABLE_ENTITY = 422;
	public static final int LOCKED = 423;
	public static final int FAILED_DEPENDENCY = 424;

	public static final int INTERNAL_SERVER_ERROR = 500;
	public static final int NOT_IMPLEMENTED = 501;
	public static final int BAD_GATEWAY = 502;
	public static final int SERVICE_UNAVAILABLE = 503;
	public static final int GATEWAY_TIMEOUT = 504;
	public static final int HTTP_VERSION_NOT_SUPPORTED = 505;
	public static final int LOOP_DETECTED = 506;
	public static final int INSUFFICIENT_STORAGE = 507;

	static{
		addStatusCodeMap(OK, "OK");
		addStatusCodeMap(CREATED, "Created");
		addStatusCodeMap(ACCEPTED, "Accepted");
		addStatusCodeMap(NO_CONTENT, "No Content");
		addStatusCodeMap(MOVED_PERMANENTLY, "Moved Permanently");
		addStatusCodeMap(MOVED_TEMPORARILY, "Moved Temporarily");
		addStatusCodeMap(NOT_MODIFIED, "Not Modified");
		addStatusCodeMap(BAD_REQUEST, "Bad Request");
		addStatusCodeMap(UNAUTHORIZED, "Unauthorized");
		addStatusCodeMap(FORBIDDEN, "Forbidden");
		addStatusCodeMap(NOT_FOUND, "Not Found");
		addStatusCodeMap(INTERNAL_SERVER_ERROR, "Internal Server Error");
		addStatusCodeMap(NOT_IMPLEMENTED, "Not Implemented");
		addStatusCodeMap(BAD_GATEWAY, "Bad Gateway");
		addStatusCodeMap(SERVICE_UNAVAILABLE, "Service Unavailable");

		
		addStatusCodeMap(CONTINUE, "Continue");
		addStatusCodeMap(METHOD_NOT_ALLOWED, "Method Not Allowed");
		addStatusCodeMap(CONFLICT, "Conflict");
		addStatusCodeMap(PRECONDITION_FAILED, "Precondition Failed");
		addStatusCodeMap(REQUEST_TOO_LONG, "Request Too Long");
		addStatusCodeMap(UNSUPPORTED_MEDIA_TYPE, "Unsupported Media Type");

		addStatusCodeMap(SWITCHING_PROTOCOLS, "Switching Protocols");
		addStatusCodeMap(NON_AUTHORITATIVE_INFORMATION,"Non Authoritative Information");
		addStatusCodeMap(RESET_CONTENT, "Reset Content");
		addStatusCodeMap(GATEWAY_TIMEOUT, "Gateway Timeout");
		addStatusCodeMap(HTTP_VERSION_NOT_SUPPORTED,"Http Version Not Supported");

		
		addStatusCodeMap(PROCESSING, "Processing");
		addStatusCodeMap(MULTI_STATUS, "Multi-Status");
		addStatusCodeMap(UNPROCESSABLE_ENTITY, "Unprocessable Entity");
		addStatusCodeMap(INSUFFICIENT_SPACE_ON_RESOURCE,"Insufficient Space On Resource");
		addStatusCodeMap(METHOD_FAILURE, "Method Failure");
		addStatusCodeMap(LOCKED, "Locked");
		addStatusCodeMap(LOOP_DETECTED, "Loop Detected");
		addStatusCodeMap(INSUFFICIENT_STORAGE , "Insufficient Storage");
		addStatusCodeMap(FAILED_DEPENDENCY, "Failed Dependency");

	}

	public static String getStatusText(int nHttpStatusCode) {
		Integer intKey = new Integer(nHttpStatusCode);

		if (!StatusCodes.containsKey(intKey)) {
			return null;
		} else {
			return (String) StatusCodes.get(intKey);
		}
	}



	private static void addStatusCodeMap(int nKey, String strVal) {
		StatusCodes.put(new Integer(nKey), strVal);
	}

}

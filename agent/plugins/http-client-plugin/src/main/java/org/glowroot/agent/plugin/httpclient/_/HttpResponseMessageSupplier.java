package org.glowroot.agent.plugin.httpclient._;

import org.glowroot.agent.plugin.api.Message;
import org.glowroot.agent.plugin.api.MessageSupplier;

import java.util.HashMap;
import java.util.Map;

public class HttpResponseMessageSupplier extends MessageSupplier {
    private String httpMethod;
    private String uri;
    private String host;
    private int statusCode = -1;

    @Override
    public Message get() {
        Map<String, Object> detail = new HashMap<String, Object>();
        if (httpMethod != null) {
            detail.put("Method", httpMethod);
        }
        if (uri != null) {
            detail.put("URI", uri);
        }
        if (host != null) {
            detail.put("Host", host);
        }
        if (statusCode >= 0) {
            detail.put("Result", statusCode);
        }
        return Message.create("http client request: "+httpMethod+" "+ uri, detail);
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}

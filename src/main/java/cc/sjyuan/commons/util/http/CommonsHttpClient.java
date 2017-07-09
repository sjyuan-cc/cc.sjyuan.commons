package cc.sjyuan.commons.util.http;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CommonsHttpClient {

    public static PostMethod post(String url, Map<String, String> params) throws IOException {
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(url);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            postMethod.addParameter(entry.getKey(), entry.getValue());
        }

        httpClient.executeMethod(postMethod);
        return postMethod;
    }

    public static void main(String[] args) throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("uid", "mbesdash");
        params.put("sec", "PZD$T&+6x!%PL3@jrs3b2xNLqj@_Dxj6");
        params.put("destNumber", "258825299873");
        params.put("message", "Test sms gateway fromThoughtWorks team");
        params.put("messageType", "MbesDashboard");
        PostMethod post = CommonsHttpClient.post("http://54.251.189.136/cgi-bin/SMS_gateway/mcel_sms_gateway.cgi", params);
        System.out.println(post.getStatusCode());
        System.out.println(post.getResponseBodyAsString());
    }
}

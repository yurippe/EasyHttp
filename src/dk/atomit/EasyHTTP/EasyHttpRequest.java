package dk.atomit.EasyHTTP;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kristian on 3/24/2016.
 */
public class EasyHttpRequest {

    private String requestMethod;
    private URL requestUrl;
    private Map<String, String> requestHeaders;
    private String requestBody;

    private boolean doInput = true;
    private boolean doOutput = false;

    public EasyHttpRequest(String url) throws MalformedURLException {
        this(url, "GET", new HashMap<>(), "");
    }

    public EasyHttpRequest(URL url) {
        this(url, "GET", new HashMap<>(), "");
    }

    public EasyHttpRequest(String url, String requestMethod) throws MalformedURLException {
        this(url, requestMethod, new HashMap<>(), "");
    }

    public EasyHttpRequest(URL url, String requestMethod) {
        this(url, requestMethod, new HashMap<>(), "");
    }

    public EasyHttpRequest(String url, String requestMethod, Map<String, String> requestHeaders) throws MalformedURLException {
        this(url, requestMethod, requestHeaders, "");
    }

    public EasyHttpRequest(URL url, String requestMethod, Map<String, String> requestHeaders) {
        this(url, requestMethod, requestHeaders, "");
    }

    public EasyHttpRequest(String url, String requestMethod, Map<String, String> requestHeaders, String body) throws MalformedURLException {
        requestMethod = requestMethod.toUpperCase();
        if(validateRequestMethod(requestMethod)) {
            this.requestMethod = requestMethod;
        } else {
            this.requestMethod = "GET";
        }
        this.requestHeaders = requestHeaders;
        this.requestUrl = new URL(url);
        this.requestBody = body;
    }

    public EasyHttpRequest(URL url, String requestMethod, Map<String, String> requestHeaders, String body) {
        requestMethod = requestMethod.toUpperCase();
        if(validateRequestMethod(requestMethod)) {
            this.requestMethod = requestMethod;
        } else {
            this.requestMethod = "GET";
        }
        this.requestHeaders = requestHeaders;
        this.requestUrl = url;
        this.requestBody = body;
    }

    public EasyHttpRequest setHeader(String key, String value){
        this.requestHeaders.put(key, value);
        return this;
    }

    //Returns a copy of the headers map
    public Map<String, String> getHeaders(){
        Map<String, String> copy = new HashMap<>();
        for(String key : this.requestHeaders.keySet()){
            copy.put(key, this.requestHeaders.get(key));
        }
        return copy;
    }

    public EasyHttpRequest setRequestMethod(String method){
        method = method.toUpperCase();
        if(validateRequestMethod(method)){
            this.requestMethod = method;
        }
        return this;
    }

    public String getRequestMethod(){
        return this.requestMethod;
    }


    private boolean validateRequestMethod(String method){
        switch (method){
            case "POST": return true;
            case "GET": return true;
            default: return false;
        }
    }

    public EasyHttpRequest setBody(String body){
        this.requestBody = body;
        return this;
    }

    public String getBody(){
        return this.requestBody;
    }

    public URL getRequestUrl(){
        return this.requestUrl;
    }

    //Perform this request
    public EasyHttpResponse doHTTP(){
        return EasyHttp.doHttp(this);
    }
}

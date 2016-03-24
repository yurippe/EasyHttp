package dk.atomit.EasyHTTP;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

/**
 * Created by Kristian on 3/24/2016.
 */
public class EasyHttp {

    //TODO: find a way to not have to deal with malformed url exception
    public static URL buildURL(String s){
        try {
            return new URL(s);
        } catch (MalformedURLException e) {
            return null;
        }
    }

    public static EasyHttpResponse doHttp(EasyHttpRequest request){
        String reqMethod = request.getRequestMethod();
        EasyHttpResponse response = new EasyHttpResponse();
        if(reqMethod.equals("GET")){
            return getHTTP(request);
        } else if (reqMethod.equals("POST")){
            return postHTTP(request);
        }


        response.setErrorMessage(reqMethod + " is not a valid request method");
        return response;
    }



    private static EasyHttpResponse getHTTP(EasyHttpRequest request){
        return getHTTP(request.getRequestUrl(), request.getHeaders());
    }

    public static EasyHttpResponse getHTTP(URL url, Map<String, String> headers){
        EasyHttpResponse response = new EasyHttpResponse();

        response.setRequestMethod("GET");
        HttpURLConnection connection = null;

        //Get a connection object
        try {
            connection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            connection.disconnect();
            return response.setErrorMessage("Could not establish connection").setFatalException(e);
        }

        //Set headers
        for(String k : headers.keySet()){
            connection.setRequestProperty(k, headers.get(k));
        }

        //Check that status code is 200 OK
        try {
            if(connection.getResponseCode() != 200){
                response.setErrorMessage("Bad status code").setResponseCode(connection.getResponseCode());
            } else {
                response.setResponseCode(200);
            }
        } catch (Exception e){
            connection.disconnect();
            return response.setErrorMessage("Stream unexpectedly closed").setFatalException(e);
        }

        //Read response body
        //TODO: make this more efficient
        try {
            InputStream inp = connection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            while (true) {
                int i = inp.read();         //Get the next character from the server.
                if (i == -1) break;         //If we get a -1, we're finished with reading.
                buffer.append((char) i);    //Append said character.
            }
            response.setResponse(buffer.toString());
            connection.disconnect();
            return response;
        } catch (IOException e) {
            connection.disconnect();
            return response.setErrorMessage("Could not read response").setFatalException(e);
        }
    }

    private static EasyHttpResponse postHTTP(EasyHttpRequest request){
        return postHTTP(request.getRequestUrl(), request.getHeaders(), request.getBody());
    }
    public static EasyHttpResponse postHTTP(URL url, Map<String, String> headers, String body){
        EasyHttpResponse response = new EasyHttpResponse();

        response.setRequestMethod("POST");
        HttpURLConnection connection = null;

        //Get a connection object
        try {
            connection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            connection.disconnect();
            return response.setErrorMessage("Could not establish connection").setFatalException(e);
        }

        //Post requests do both input and output
        connection.setDoInput(true);
        connection.setDoOutput(true);

        //Set request method to POST
        try {
            connection.setRequestMethod("POST");
        } catch (ProtocolException e) {
            //This should litterally NEVER happen, since it is hardcoded
            connection.disconnect();
            return response.setErrorMessage("POST not a valid request method").setFatalException(e);
        }

        //Set headers
        for(String k : headers.keySet()){
            connection.setRequestProperty(k, headers.get(k));
        }

        //Write the body of the post request
        try {
            DataOutputStream writer = new DataOutputStream(connection.getOutputStream());
            writer.write(body.getBytes());
            writer.flush();
            writer.close();
        } catch (Exception e){
            connection.disconnect();
            return response.setErrorMessage("Error writing body to server").setFatalException(e);
        }

        //Check that status code is 200 OK
        try {
            if(connection.getResponseCode() != 200){
                response.setErrorMessage("Bad status code").setResponseCode(connection.getResponseCode());
            } else {
                response.setResponseCode(200);
            }
        } catch (Exception e){
            connection.disconnect();
            return response.setErrorMessage("Stream unexpectedly closed").setFatalException(e);
        }

        //Read response body
        //TODO: make this more efficient
        try {
            InputStream inp = connection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            while (true) {
                int i = inp.read();         //Get the next character from the server.
                if (i == -1) break;         //If we get a -1, we're finished with reading.
                buffer.append((char) i);    //Append said character.
            }
            response.setResponse(buffer.toString());
            connection.disconnect();
            return response;
        } catch (IOException e) {
            connection.disconnect();
            return response.setErrorMessage("Could not read response").setFatalException(e);
        }

    }
}

import dk.atomit.EasyHTTP.EasyHttp;
import dk.atomit.EasyHTTP.EasyHttpRequest;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Kristian on 3/24/2016.
 */
public class EasyHttpTest {

    public static void main(String[] args){
        URL googlecom = EasyHttp.buildURL("http://google.com");
        EasyHttpRequest request = new EasyHttpRequest(googlecom);
        System.out.println(request.doHTTP().getResponse());

    }
}

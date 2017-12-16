import dk.atomit.EasyHTTP.EasyHttp;
import dk.atomit.EasyHTTP.EasyHttpRequest;
import dk.atomit.EasyHTTP.EasyHttpResponse;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;

/**
 * Created by Kristian on 3/24/2016.
 */
public class EasyHttpTest {

    public static void main(String[] args) throws GeneralSecurityException {
        EasyHttp.allowInsecure(true);
        URL googlecom = EasyHttp.buildURL("https://admin.yurippe.net");
        EasyHttpRequest request = new EasyHttpRequest(googlecom);
        EasyHttpResponse resp = request.doHTTP();
        System.out.println(resp.getResponse());
        System.out.println(resp.getFatalException());

    }
}

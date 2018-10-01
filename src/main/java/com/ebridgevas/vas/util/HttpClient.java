package com.ebridgevas.vas.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClient {

    private final static String AIRTIME_TRANSFER_URL =
            "http://192.1.1.57:8080/televas-httpbridge-1.0/billing-platform?" +
            "service-command=airtime-transfer&mobile-number=FROM_MOBILE_NUMNER" +
            "&beneficiary-id=TO_MOBILE_NUMBER" +
            "&amount=AMOUNT&payment-method=AIRTIME&one-time-password=12345";

    private static final CloseableHttpClient HTTP_CLIENT;

    static {
        System.out.println("CREATING A HTTP CLIENT");
        HTTP_CLIENT = HttpClients.createDefault();
    }

    public boolean post(String fromMobileNumber, String toMobileNumber, BigDecimal amount) throws IOException {

        CloseableHttpResponse response = null;
        try {
            String url = url(fromMobileNumber, toMobileNumber, amount);
            response = HTTP_CLIENT.execute(
                            new HttpGet(url));

            System.out.println(response.getStatusLine());
            // HTTP/1.1 200 OK

            String result = IOUtils.toString(response.getEntity().getContent());
            System.out.println(result);

            return processResponse(result);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try { response.close(); } catch (Exception e){}
        }

        return false;
    }

    public String url(String fromMobileNumber, String toMobileNumber, BigDecimal amount) throws IOException {
        String url = AIRTIME_TRANSFER_URL;
        url = url.replaceAll("FROM_MOBILE_NUMNER", fromMobileNumber);
        url = url.replaceAll("TO_MOBILE_NUMBER", toMobileNumber);
        url = url.replaceAll("AMOUNT", amount.toString());

        return url;
    }

    private boolean processResponse(String response) {

        // "$0.01000000000000000020816681711721685132943093776702880859375 transfer to 0736619492 accepted. Your balance is now: $21.01. Reference: 26630AE4F3EF4637A65771D2AC41CEE5"
        return response != null && response.indexOf("accepted") > 0;

    }

    public static void main(String[] args) throws Exception {
//        new HttpClient().post("263735962427", "263736619492", new BigDecimal(0.01));

        String response =  "$0.01000000000000000020816681711721685132943093776702880859375 transfer to 0736619492 accepted. Your balance is now: $21.01. Reference: 26630AE4F3EF4637A65771D2AC41CEE5";
        System.out.println(new HttpClient().processResponse(response));
    }

}
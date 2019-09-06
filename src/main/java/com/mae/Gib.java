package com.mae;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


public class Gib {

public static boolean wCheck2(){
    
        try {
            URL url = new URL("https://merkeztest.efatura.gov.tr/EFaturaMerkez/services/EFatura?wsdl");
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.connect();
            int httpStatusCode = connection.getResponseCode(); //200, 404 etc.
            System.out.println(httpStatusCode);
            if (httpStatusCode==200) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    return false;
}


}
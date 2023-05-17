package AddOn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
// class untuk read perbaris urlnya
public class ReadUrl {
    public String ReadUrl(String url){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String  result = null;
        URL requestURL = null;
        try {
            requestURL = new URL(url);
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream  = urlConnection.getInputStream();
            StringBuilder builder = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line=reader.readLine()) != null){
                builder.append(line + "\n");
            }
            if(builder != null){
                result= builder.toString();
            }else{
                return null;
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}

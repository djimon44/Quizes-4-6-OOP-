package quiz6;

import org.json.JSONArray;
import org.json.JSONObject;
import quiz5.CommunicationManager;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class SpecialCommunicationManager extends CommunicationManager {
    private String commonServiceUrl;
    private String specialServiceUrl;

    public SpecialCommunicationManager(String commonServiceUrl, String specialServiceUrl) {
        super(null);  // Not using the base URL from the parent class
        this.commonServiceUrl = commonServiceUrl;
        this.specialServiceUrl = specialServiceUrl;
    }

    @Override
    public JSONObject sendData(JSONObject data) {
        String targetUrl = commonServiceUrl + "/chat";
        if (shouldUseSpecialService(data)) {
            targetUrl = specialServiceUrl + "/chat";
        }

        try {
            URL url = new URL(targetUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            OutputStream os = conn.getOutputStream();
            os.write(data.toString().getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            StringBuilder response = new StringBuilder();
            while ((output = br.readLine()) != null) {
                response.append(output);
            }

            conn.disconnect();
            return new JSONObject(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean shouldUseSpecialService(JSONObject data) {
        JSONArray chatHistory = data.getJSONArray("chatHistory");
        for (int i = 0; i < chatHistory.length(); i++) {
            if (chatHistory.getString(i).toLowerCase().contains("help")) {
                return true;
            }
        }
        return false;
    }
}

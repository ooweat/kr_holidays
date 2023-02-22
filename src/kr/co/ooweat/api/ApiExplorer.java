package kr.co.ooweat.api;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

import org.json.JSONObject;
import org.json.XML;

//TODO: 인터넷망이 지원될 경우에만 사용하도록 한다.
public class ApiExplorer {
    public static List<Map<String, String>> holidayList = new ArrayList<>();
    public static void call(String year, String month) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B09004" +
                "1/openapi/service/SpcdeInfoService/getRestDeInfo"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") +
                "="); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("solYear", "UTF-8") + "=" + URLEncoder.encode(year, "UTF-8")); /*연*/
        urlBuilder.append("&" + URLEncoder.encode("solMonth", "UTF-8") + "=" + URLEncoder.encode(month, "UTF-8")); /*월*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        JSONObject json = XML.toJSONObject(sb.toString());
        Map<String, Object> response = (Map<String, Object>) json.toMap().get("response");
        Map<String, Object> body = (Map<String, Object>) response.get("body");

        if ((Integer) body.get("totalCount") > 1) {
            Map<String, Object> items = (Map<String, Object>) body.get("items");
            List<Map<String, String>> item = (List<Map<String, String>>) items.get("item");

            for (Map<String, String> i : item) {
                holidayList.add(i);
            }
        } else if ((Integer) body.get("totalCount") == 1) {
            Map items = (Map<String, String>) body.get("items");
            Map item = (Map<String, String>) items.get("item");
            holidayList.add((Map<String, String>) item);
        } else {

        }
    }
}

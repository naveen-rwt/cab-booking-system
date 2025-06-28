package cab_booking;

import java.io.IOException;
import java.net.URLEncoder;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONObject;
public class CityDistanceCalculator {
   
    private static final double EARTH_RADIUS = 6371.0;
    //haversine formula
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);
        
        double dLat = lat2Rad - lat1Rad;
        double dLon = lon2Rad - lon1Rad;
        
        double a = Math.pow(Math.sin(dLat / 2), 2) + Math.cos(lat1Rad) * Math.cos(lat2Rad) * Math.pow(Math.sin(dLon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = EARTH_RADIUS * c;
        
        return distance;
    }
    
    public static double[] getCoordinates(String cityName) throws IOException {
        String url = "https://nominatim.openstreetmap.org/search?format=json&q=" + URLEncoder.encode(cityName, "UTF-8");
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");

        StringBuilder response = new StringBuilder();
        try (Scanner scanner = new Scanner(con.getInputStream())) {
            while (scanner.hasNextLine()) {
                response.append(scanner.nextLine());
            }
        } finally {
            con.disconnect(); 
        }

        // Check if response is empty
        if (response.length() == 0) {
            throw new IOException("Empty response from the server");
        }

        // Check if response is in JSON array format
        if (response.charAt(0) == '[') {
            JSONArray jsonArray = new JSONArray(response.toString());
            if (jsonArray.length() > 0) {
                JSONObject firstResult = jsonArray.getJSONObject(0);
                double lat = Double.parseDouble(firstResult.getString("lat"));
                double lon = Double.parseDouble(firstResult.getString("lon"));
                return new double[]{lat, lon};
            } else {
                throw new IOException("No results found for: " + cityName);
            }
        } else if (response.charAt(0) == '{') {
            JSONObject jsonResponse = new JSONObject(response.toString());

            if (jsonResponse.has("lat") && jsonResponse.has("lon")) {
                double lat = jsonResponse.getDouble("lat");
                double lon = jsonResponse.getDouble("lon");
                return new double[]{lat, lon};
            } else {
                throw new IOException("City coordinates not found for: " + cityName);
            }
        } else {
            throw new IOException("Unexpected response from the server: " + response.toString());
        }

    }

    public static void main(String[] args) {
    	  CityDistanceCalculator distanceCalculator = new CityDistanceCalculator();
    	try {
        	
            String city1 = "delhi";
            String city2 = "noida";
            
            double[] coordinates1 = getCoordinates(city1);
            double[] coordinates2 = getCoordinates(city2);
            
            double distance = calculateDistance(coordinates1[0], coordinates1[1], coordinates2[0], coordinates2[1]);
            
            System.out.println("Distance between " + city1 + " and " + city2 + ": " + distance + " kilometers");
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace(); 
        }
    }
}


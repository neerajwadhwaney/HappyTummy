package com.util;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.pojo.ProductPojo;
import java.awt.Image;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import org.json.JSONArray;
import org.json.JSONObject;

public class SpoonacularAPI {

    public static List<ProductPojo> getAllItemDetailsByName(String foodName) throws Exception {
        System.out.println("hi");
        String apiKey = "b47eac907706408e839adf55ad38bcc2";
        String apiUrl = "https://api.spoonacular.com/food/search?query=" + foodName + "&number=2&apiKey=" + apiKey;
        List<ProductPojo> foodList = new ArrayList<>();
        HttpResponse<JsonNode> response = Unirest.get(apiUrl).header("accept", "application/json").asJson();
//            System.out.println("line 27");
        JSONArray searchResultsArray = response.getBody().getObject().getJSONArray("searchResults");
//            System.out.println("line 29");
        for (int i = 0; i < searchResultsArray.length(); i++) {
            JSONObject searchResults = searchResultsArray.getJSONObject(i);
            JSONArray resultArray = searchResults.getJSONArray("results");

            for (int j = 0; j < resultArray.length(); j++) {
                JSONObject result = resultArray.getJSONObject(j);

                if (!result.has("name") || !result.has("image")) {
                    continue;
                }
                String itemName = result.getString("name");
                String imageUrl = result.getString("image");
                String imageType = imageUrl.substring(imageUrl.lastIndexOf(".") + 1, imageUrl.length());

                if (itemName.isEmpty() || imageUrl.isEmpty()) {
                    continue;
                }

                HttpURLConnection url = (HttpURLConnection) new URL(imageUrl).openConnection();
                url.addRequestProperty("user-agent", "mozilla");

                if (url.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    continue;
                }

                Image image = ImageIO.read(url.getInputStream());

                ProductPojo product = new ProductPojo();
                product.setProductName(itemName);
                product.setProductImage(image);
                product.setProductImageType(imageType);
                foodList.add(product);
                System.out.println(foodList);
            }
        }
        return foodList;
    }
}

package dankminer.dankminer.utils;

import dankminer.dankminer.DankMiner;
import dankminer.dankminer.Post;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MemeLoader {

    private static String SUBREDDIT = "dankmemes";
    private static List<Post> posts = null;
    private static boolean enabled = false;

    /**
     * Enables tne MemeLoader
     * @return true/false if enabling was successful
     */
    public static boolean enable(){

        try{
            posts = new ArrayList<>();
            SUBREDDIT = ConfigFile.getConfigFile().getString("subreddit");
            if(SUBREDDIT == null) SUBREDDIT = "dankmemes";
            fetchJSON();
        }
        catch (Exception e){
            DankMiner.serverLog("§can Exception occurred while enabling the MemeLoader!");
            return false;
        }

        enabled = true;
        return true;

    }

    /**
     * Get a list of the first 40 posts on the subreddit under the "top"-section
     * parses the returning JSON body with the parseJSON method to create a list of {@link Post} elements
     * Uses the {@link HttpClient}, {@link HttpRequest} and {@link HttpResponse}
     */
    private static void fetchJSON(){

        String url = "https://www.reddit.com/r/" + SUBREDDIT + "/top/.json?limit=40";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(s -> parseJSON(s))
                .join();

    }

    /**
     * parses a String representing Data in the JSON format
     * using the org.json.simple JSON-Library
     * @param data the JSON data as a String
     */
    private static void parseJSON(String data){

        JSONObject body = (JSONObject) JSONValue.parse(data);
        JSONArray posts = (JSONArray) ((JSONObject) body.get("data")).get("children");

        //looping through the array of posts
        for(int i = 0; i < posts.size(); i++){

            JSONObject post = (JSONObject) ((JSONObject) posts.get(i)).get("data");
            Post memePost = new Post((String) post.get("url"), (String) post.get("title"), "reddit.com" + (String) post.get("permalink"));
            MemeLoader.posts.add(memePost);

        }

        System.out.println(MemeLoader.posts.toString());

    }

    /**
     * Fetches an image from the web
     * @param urlString the usl to the image
     * @return the Data from the Internet in Form of a Image Object
     */
    public static Image fetchImage(String urlString){

        //checks if the MemeLoader is already properly enabled
        if(!enabled){
            DankMiner.serverLog("§cMemeLoader not yet enabled! §aRun MemeLoader.enable() first");
            return null;
        }

        Image image = null;
        try {

            URL url = new URL(urlString);
            image = ImageIO.read(url);

        } catch (IOException e) {
            DankMiner.serverLog("Failed to load image. (Is the URL correct?)");
        }

        return image;

    }

    /**
     * Fetches an image from the web
     * @param url the url to the image
     * @return the Data from the Internet in Form of a Image Object
     */
    public static Image fetchImage(URL url){

        //checks if the MemeLoader is already properly enabled
        if(!enabled){
            DankMiner.serverLog("§cMemeLoader not yet enabled! §aRun MemeLoader.enable() first");
            return null;
        }

        Image image = null;
        try {

            image = ImageIO.read(url);

        } catch (IOException e) {
            DankMiner.serverLog("Failed to load image. (Is the URL correct?)");
        }

        return image;

    }

    /**
     * selects a random entry in the posts List and returns it
     * @return a random {@link Post}
     */
    public static Post getRandomPost(){

        if(!enabled){
            DankMiner.serverLog("§cMemeLoader not yet enabled! §aRun MemeLoader.enable() first");
            return null;
        }
        short randIndex = (short) new Random().nextInt(posts.size());
        return posts.get(randIndex);

    }

    //GETTER
    public static boolean isEnabled() {
        return enabled;
    }
}

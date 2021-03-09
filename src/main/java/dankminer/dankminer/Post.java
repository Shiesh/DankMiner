package dankminer.dankminer;

/**
 * Class representing a post on Reddit.com
 */
public class Post {

    //URL to the image
    private String memeURL;
    //title as String
    private String title;
    //URL to the post-web-page
    private String postURL;

    public Post(String meme, String title, String postURL) {
        this.memeURL = meme;
        this.title = title;
        this.postURL = postURL;
    }

    //GETTERS
    public String getMemeURL() {
        return memeURL;
    }
    public String getTitle() {
        return title;
    }
    public String getPostURL() {
        return postURL;
    }
}

package it.reply.selly.mobileapp.utility;

/**
 * Created by m.ditucci on 13/05/2017.
 */
public class SellyMessage {
    private String message;
    private boolean isFromServer;
    private boolean isUpsellingImage;
    private String url;
    private String imageUrl;

    public SellyMessage(String message, boolean isFromServer) {
        this.message = message;
        this.isFromServer = isFromServer;
    }

    public SellyMessage(String message, boolean isFromServer, boolean isUpsellingImage, String url, String imageUrl) {
        this.message = message;
        this.isFromServer = isFromServer;
        this.isUpsellingImage = isUpsellingImage;
        this.url = url;
        this.imageUrl = imageUrl;
    }

    public String getMessage() {
        return message;
    }

    public boolean isFromServer() {
        return isFromServer;
    }

    public boolean isUpsellingImage() {
        return isUpsellingImage;
    }

    public String getUrl() {
        return url;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}

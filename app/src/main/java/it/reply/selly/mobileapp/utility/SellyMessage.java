package it.reply.selly.mobileapp.utility;

/**
 * Created by m.ditucci on 13/05/2017.
 */
public class SellyMessage {
    private String message;
    private boolean isFromServer;

    public SellyMessage(String message, boolean isFromServer) {
        this.message = message;
        this.isFromServer = isFromServer;
    }

    public String getMessage() {
        return message;
    }

    public boolean isFromServer() {
        return isFromServer;
    }
}

package it.reply.selly.mobileapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import cz.msebera.android.httpclient.Header;
import it.reply.selly.mobileapp.utility.HttpClient;
import it.reply.selly.mobileapp.utility.MessagesListAdapter;
import it.reply.selly.mobileapp.utility.SellyMessage;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private static final String MESSAGE_POST_PARAM_KEY = "text";
    private static final String RESPONSE_TEXT = "responseText";
    private static final String RESPONSE_IMAGE_LINK = "responseImageLink";
    private static final String RESPONSE_IMAGE_NAME = "responseImageName";

    private EditText inputForm;

    private MessagesListAdapter adapter;
    private List<SellyMessage> messageList = new ArrayList<>();

    private JSONObject lastResponseFromChatbot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getViewReferences();
        initAdapter();
        loadWelcomeMessage();
    }

    private void initAdapter() {
        final ListView listView = (ListView) findViewById(R.id.list_view_messages);
        adapter = new MessagesListAdapter(this, messageList);
        listView.setAdapter(adapter);
    }

    private void loadWelcomeMessage(){
        messageList.add(new SellyMessage(getString(R.string.welcome_message), true));
        adapter.notifyDataSetChanged();
    }

    private void getViewReferences() {
        inputForm = (EditText) findViewById(R.id.inputMsg);
    }

    public void onSendMessageButtonClick(View view) {
        if ("si".equalsIgnoreCase(inputForm.getText().toString()) || "sì".equals(inputForm.getText().toString())){
            showUpSellingProposal();
        } else if ("no".equalsIgnoreCase(inputForm.getText().toString())){
            messageList.add(new SellyMessage(inputForm.getText().toString(), false));
            defaultAnswerForYesInput();
        } else {
            messageList.add(new SellyMessage(inputForm.getText().toString(), false));
            adapter.notifyDataSetChanged();
            sendMessageToServer(inputForm.getText().toString(), false);
        }

        inputForm.setText("");
    }

    private void sendMessageToServer(final String messageToSend, boolean isMock) {
        if (isMock){
            messageList.add(new SellyMessage("Server says bla bla bla", false));
            adapter.notifyDataSetChanged();
            return;
        }

        HttpClient.post("/", new RequestParams(MESSAGE_POST_PARAM_KEY, messageToSend), new JsonHttpResponseHandler() {
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e(TAG, "A problem occurred");
                Toast.makeText(getApplicationContext(), "An error occurred", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i(TAG, "Message received");

                lastResponseFromChatbot = response;
                try {
                    messageList.add(new SellyMessage(response.getString(RESPONSE_TEXT), true));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();

//                /*We are bad people for doing this (and other fancy stuff). We are out of time, shall God forgive us*/
//                try {
//                    messageList.add(new SellyMessage(response.getString(RESPONSE_TEXT), true));
//                    messageList.add(new SellyMessage(response.getString(RESPONSE_TEXT), true, true,
//                            response.getString(RESPONSE_IMAGE_LINK), response.getString(RESPONSE_IMAGE_NAME)));
//                    adapter.notifyDataSetChanged();
//                } catch (JSONException e) {
//                    Log.e(TAG, "An error occurred while transforming JSON received from BE");
//                    adapter.notifyDataSetChanged();
//                }
            }
        });
    }

    private void showUpSellingProposal(){
        if (lastResponseFromChatbot == null){
            defaultAnswerForYesInput();
            return;
        }

        try {
            if (lastResponseFromChatbot.getString(RESPONSE_IMAGE_NAME).equalsIgnoreCase("amazon") ||
                    lastResponseFromChatbot.getString(RESPONSE_IMAGE_NAME).equalsIgnoreCase("travel_insurance")){
                messageList.add(new SellyMessage(lastResponseFromChatbot.getString(RESPONSE_TEXT), true, true,
                        lastResponseFromChatbot.getString(RESPONSE_IMAGE_LINK), lastResponseFromChatbot.getString(RESPONSE_IMAGE_NAME)));
                adapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            defaultAnswerForYesInput();
        }
    }

    private void defaultAnswerForYesInput(){
        messageList.add(new SellyMessage(getString(R.string.ok), true));
        adapter.notifyDataSetChanged();
    }
}

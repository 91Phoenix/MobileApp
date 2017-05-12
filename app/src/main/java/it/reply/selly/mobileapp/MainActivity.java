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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private static final String MESSAGE_POST_PARAM_KEY = "text";
    private static final String RESPONSE_TEXT = "responseText";

    private EditText inputForm;

    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getViewReferences();
        initAdapter();
    }

    private void initAdapter() {
        final ListView listView = (ListView) findViewById(R.id.messagesContainer);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<String>());
        listView.setAdapter(adapter);
    }

    private void getViewReferences() {
        inputForm = (EditText) findViewById(R.id.messageEdit);
    }

    public void onSendMessageButtonClick(View view) {
        adapter.add("Me: " + inputForm.getText().toString());
        sendMessageToServer(inputForm.getText().toString(), false);
        inputForm.setText("");
    }

    private void sendMessageToServer(String messageToSend, boolean isMock) {
        if (isMock){
            adapter.add("Server says bla bla bla");
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
                try {
                    if (response.getString(RESPONSE_TEXT) != null){
                        adapter.add("Chatbot: " + response.getString(RESPONSE_TEXT));
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "An error occurred while transforming JSON received from BE");
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                // Pull out the first event on the public timeline
                String tweetText = "";
                try {
                    JSONObject firstEvent = (JSONObject) timeline.get(0);
                    tweetText = firstEvent.getString("text");
                }
                catch (JSONException e){
                    Log.e("MainActivity", "An exception occurred while transforming the JSON");
                }

                // Do something with the response
                System.out.println(tweetText);
            }
        });


    }
}

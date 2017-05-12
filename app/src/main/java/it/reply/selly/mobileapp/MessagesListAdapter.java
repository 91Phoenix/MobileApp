package it.reply.selly.mobileapp;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import it.reply.selly.mobileapp.utility.SellyMessage;

/**
 * Created by m.ditucci on 12/05/2017.
 */
public class MessagesListAdapter extends BaseAdapter {

    private static final String ME = "Me";
    private static final String SELLY = "Selly";
    private Context context;
    private List<SellyMessage> messagesItems;

    public MessagesListAdapter(Context context, List<SellyMessage> navDrawerItems) {
        this.context = context;
        this.messagesItems = navDrawerItems;
    }

    @Override
    public int getCount() {
        return messagesItems.size();
    }

    @Override
    public Object getItem(int position) {
        return messagesItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        /**
         * The following list not implemented reusable list items as list items
         * are showing incorrect data Add the solution if you have one
         * */

        String message = messagesItems.get(position).getMessage();

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        // Identifying the message owner
        if (! messagesItems.get(position).isFromServer()) {
            // message belongs to you, so load the right aligned layout
            convertView = mInflater.inflate(R.layout.list_item_message_right, null);
        } else {
            // message belongs to other person, load the left aligned layout
            convertView = mInflater.inflate(R.layout.list_item_message_left,
                    null);
        }

        TextView lblFrom = (TextView) convertView.findViewById(R.id.lblMsgFrom);
        TextView txtMsg = (TextView) convertView.findViewById(R.id.txtMsg);

        txtMsg.setText(message);

        if (! messagesItems.get(position).isFromServer()){
            lblFrom.setText(ME);
        } else{
            lblFrom.setText(SELLY);
        }

        return convertView;
    }
}
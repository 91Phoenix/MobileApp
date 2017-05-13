package it.reply.selly.mobileapp.utility;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import it.reply.selly.mobileapp.R;

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
            convertView = setViewForUser(message, mInflater);
        } else {
            // message belongs to server, load the left aligned layout
            convertView = setViewForServer(mInflater, position);
        }

        TextView lblFrom = (TextView) convertView.findViewById(R.id.lblMsgFrom);
        if (! messagesItems.get(position).isFromServer()){
            lblFrom.setText(ME);
        } else{
            lblFrom.setText(SELLY);
        }

        return convertView;
    }

    private View setViewForUser(String message, LayoutInflater mInflater) {
        View convertView;
        convertView = mInflater.inflate(R.layout.list_item_message_right, null);
        TextView txtMsg = (TextView) convertView.findViewById(R.id.txtMsg);
        txtMsg.setText(message);
        return convertView;
    }

    private View setViewForServer(LayoutInflater mInflater, int position) {
        SellyMessage sellyMessage = this.messagesItems.get(position);
        View convertView;

        if (sellyMessage.isUpsellingImage()){
            convertView = mInflater.inflate(R.layout.list_item_image_message_left, null);
            TextView textView = (TextView) convertView.findViewById(R.id.txtMsg);
            textView.setText(sellyMessage.getUrl());
            ImageView imageView = (ImageView) convertView.findViewById(R.id.imageMsg);

            int id = context.getResources().getIdentifier(sellyMessage.getImageUrl(), "drawable", context.getPackageName());
            imageView.setBackgroundResource(id);

            TextView txtMsg = (TextView) convertView.findViewById(R.id.txtMsg);
            txtMsg.setText(messagesItems.get(position).getUrl());
        } else{
            convertView = mInflater.inflate(R.layout.list_item_message_left, null);
            TextView txtMsg = (TextView) convertView.findViewById(R.id.txtMsg);
            txtMsg.setText(messagesItems.get(position).getMessage());
            txtMsg.setMovementMethod(LinkMovementMethod.getInstance());
        }
        return convertView;
    }
}
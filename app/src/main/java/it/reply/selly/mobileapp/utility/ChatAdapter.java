package it.reply.selly.mobileapp.utility;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by m.ditucci on 10/05/2017.
 */
public class ChatAdapter<String> extends ArrayAdapter<String> {
    Context context;
    int layoutResourceId;
    List<String> data;

    public ChatAdapter(Context context, @LayoutRes int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if(row == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
        }

//        if (position % 2 == 0){
//            row.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
//        } else {
//            row.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
//        }

        return row;
    }

}

package com.example.imitateqq.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.imitateqq.R;
import com.example.imitateqq.fragment.MsgFragment;

public class MsgAdapter extends BaseAdapter {
    private Context context;
    MsgFragment data = new MsgFragment();
    public String[] names = data.names;
    public int[] icons = data.icons;
    public String[] times = data.times;
    public String[] message = data.message;

    public MsgAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return names.length;
    }

    @Override
    public Object getItem(int position) {
        return names[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View inflate = View.inflate(context, R.layout.listview_chat_item, null);

        ImageView image = inflate.findViewById(R.id.iv);
        TextView name = inflate.findViewById(R.id.name);
        TextView tTime = inflate.findViewById(R.id.time);
        TextView tMessage = inflate.findViewById(R.id.message);

        image.setImageResource(icons[position]);
        name.setText(names[position]);
        tTime.setText(times[position]);
        tMessage.setText(message[position]);

        return inflate;
    }
}

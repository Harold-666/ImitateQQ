package com.example.imitateqq.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.imitateqq.R;
import com.example.imitateqq.fragment.ContactFragment;

public class ContactAdapter extends BaseAdapter {
    private Context context;
    ContactFragment data = new ContactFragment();
    public String[] names = data.names;
    public String[] status = data.status;
    public int[] icons = data.icons;
    public String[] signature = data.signature;

    public ContactAdapter(Context context){
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
        View inflate = View.inflate(context, R.layout.listview_contact_item, null);

        ImageView image = inflate.findViewById(R.id.iv);
        TextView name = inflate.findViewById(R.id.name);
        TextView tStatus = inflate.findViewById(R.id.status);
        TextView tSignature = inflate.findViewById(R.id.signature);

        image.setImageResource(icons[position]);
        name.setText(names[position]);
        tStatus.setText("[" + status[position] + "]");
        tSignature.setText(signature[position]);

        return inflate;
    }
}

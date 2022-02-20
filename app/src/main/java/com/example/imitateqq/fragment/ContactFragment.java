package com.example.imitateqq.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.imitateqq.R;
import com.example.imitateqq.ShowContactActivity;
import com.example.imitateqq.adapter.ContactAdapter;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ContactFragment extends Fragment {
    private View view;
    private ListView lv;
    public String[] names = {"黄明豪", "王金胜", "邵纪济", "马云", "马化腾", "罗翔老师"};
    public String[] status = {"iphone在线", "WiFi在线", "4G在线", "5G在线", "5G在线", "iphone 13 Pro在线"};
    public int[] icons = {R.drawable.avatar_01, R.drawable.avatar_02, R.drawable.avatar_03, R.drawable.mayun, R.drawable.mahuateng, R.drawable.luoxiang};
    public String[] signature = {"You gotta take chances for the things you care about.",
            "Hello World!",
            "心_",
            "我对钱不感兴趣！",
            "我们不做盗版，只做搬运！",
            "人要接受自己的有限性，人的逻辑、理性、阅读都是有限的，整个人就是在偏见之中。人这一生就是在走出偏见。"
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_contact, container, false);
        lv = view.findViewById(R.id.lv);
        lv.setAdapter(new ContactAdapter(this.getContext()));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ShowContactActivity.class);
                intent.putExtra("name", names[position]);
                intent.putExtra("status", status[position]);
                intent.putExtra("icon", icons[position]);
                intent.putExtra("signature", signature[position]);
                startActivity(intent);
            }
        });
        return view;
    }
}

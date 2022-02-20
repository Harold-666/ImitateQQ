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
import com.example.imitateqq.ShowChatActivity;
import com.example.imitateqq.adapter.MsgAdapter;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class MsgFragment extends Fragment {
    private View view;
    private ListView lv;
    public String[] names = {"黄明豪", "王金胜", "邵纪济", "马云", "马化腾", "罗翔老师"};
    public int[] icons = {R.drawable.avatar_01, R.drawable.avatar_02, R.drawable.avatar_03, R.drawable.mayun, R.drawable.mahuateng, R.drawable.luoxiang};
    public String[] times = {"下午 2:30:36", "下午 6:16:28", "上午 7:30:26", "下午 9:25:55", "上午 9:26:36", "上午 8:30:00"};
    public String[] message = {"今天你学习安卓了吗？",
            "每天一句Hello World!",
            "打篮球🏀吗？",
            "我对钱不感兴趣！！！我没有碰过钱。",
            "你只管抽奖，能抽到算我输！！！",
            "张三定什么罪？"
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_msg, container, false);
        lv = view.findViewById(R.id.lv);
        lv.setAdapter(new MsgAdapter(this.getContext()));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ShowChatActivity.class);
                intent.putExtra("name", names[position]);
                intent.putExtra("icon", icons[position]);
                intent.putExtra("time", times[position]);
                intent.putExtra("message", message[position]);
                startActivity(intent);
            }
        });
        return view;
    }
}
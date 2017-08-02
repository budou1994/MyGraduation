package com.graduation.mygraduation.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.graduation.mygraduation.R;
import com.graduation.mygraduation.activity.NewsDataShowActivity;
import com.graduation.mygraduation.activity.WebShowActivity;
import com.graduation.mygraduation.entity.ChatMessage;

/**
 * 图灵机器人的聊天页面adapter
 */
public class ChatMessageAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<ChatMessage> mDatas;
    private Context context;

    public ChatMessageAdapter(Context context, List<ChatMessage> datas) {
        mInflater = LayoutInflater.from(context);
        mDatas = datas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 接受到消息为1，发送消息为0
     */
    @Override
    public int getItemViewType(int position) {
        ChatMessage msg = mDatas.get(position);
        return msg.getType() == ChatMessage.Type.INPUT ? 1 : 0;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ChatMessage chatMessage = mDatas.get(position);

        ViewHolder viewHolder = null;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            if (chatMessage.getType() == ChatMessage.Type.INPUT) {
                convertView = mInflater.inflate(R.layout.main_chat_from_msg,
                        parent, false);
                viewHolder.createDate = (TextView) convertView
                        .findViewById(R.id.chat_from_createDate);
                viewHolder.content = (TextView) convertView
                        .findViewById(R.id.chat_from_content);
                convertView.setTag(viewHolder);
            } else {
                convertView = mInflater.inflate(R.layout.main_chat_send_msg,
                        null);

                viewHolder.createDate = (TextView) convertView
                        .findViewById(R.id.chat_send_createDate);
                viewHolder.content = (TextView) convertView
                        .findViewById(R.id.chat_send_content);
                convertView.setTag(viewHolder);
            }

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (TextUtils.isEmpty(chatMessage.getUrl())) {
            viewHolder.content.setText(chatMessage.getMsg());
        } else {
            String newStr = chatMessage.getMsg() + "\n\t" +
                   " <font color='#2299EF'><small><i>"+chatMessage.getUrl()+"</i></small></font>";
            viewHolder.content.setText(Html.fromHtml(newStr));
        }
        viewHolder.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chatMessage.getCode()==308000){
                    Log.i("ss", chatMessage.getUrl());
                    Intent intent = new Intent(context, WebShowActivity.class);
                    intent.putExtra("url", chatMessage.getUrl());
                    context.startActivity(intent);
                }else if (chatMessage.getCode()==200000){
                    Intent intent = new Intent(context, WebShowActivity.class);
                    intent.putExtra("url", chatMessage.getUrl());
                    context.startActivity(intent);
                }
            }
        });
        viewHolder.createDate.setText(chatMessage.getDateStr());
        viewHolder.createDate.setTextColor(Color.BLACK);
        return convertView;
    }

    private class ViewHolder {
        public TextView createDate;
        public TextView name;
        public TextView content;
    }

}

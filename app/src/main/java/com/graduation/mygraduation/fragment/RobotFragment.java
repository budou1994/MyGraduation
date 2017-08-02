package com.graduation.mygraduation.fragment;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.graduation.mygraduation.R;
import com.graduation.mygraduation.adapter.ChatMessageAdapter;
import com.graduation.mygraduation.entity.ChatMessage;
import com.graduation.mygraduation.net.NetCallback;
import com.graduation.mygraduation.net.NetClient;
import com.graduation.mygraduation.utils.HttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 图灵
 * 免费初级版 可以讲笑话，搜图片 查航班，聊天等一些简单功能
 *
 * 未实现语音聊天 语音识别
 */

public class RobotFragment extends Fragment {

    View view;

    /**
     * 展示消息的listview
     */
    @Bind(R.id.id_chat_listView)
    ListView mChatView;
    /**
     * 文本域
     */
    @Bind(R.id.id_chat_msg)
    EditText mMsg;
    /**
     * 发送按钮
     */
    @Bind(R.id.id_chat_send)
    Button button;
    /**
     * 存储聊天消息
     */
    private List<ChatMessage> mDatas = new ArrayList<ChatMessage>();
    /**
     * 适配器
     */
    private ChatMessageAdapter mAdapter;


    public static RobotFragment getInstance() {
        RobotFragment fragment = new RobotFragment();
        return fragment;
    }

    public RobotFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_robot, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void initView() {

        mDatas.add(new ChatMessage(ChatMessage.Type.INPUT, "你好，请问你想知道什么呢？"));
        mAdapter = new ChatMessageAdapter(getActivity(), mDatas);
        mChatView.setAdapter(mAdapter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMsg();
            }
        });
    }

    private void sendMsg() {
        final String msg = mMsg.getText().toString();
        if (TextUtils.isEmpty(msg)) {
            Toast.makeText(getActivity(), "您还没有填写信息呢...", Toast.LENGTH_SHORT).show();
            return;
        }
        ChatMessage message = new ChatMessage(ChatMessage.Type.OUTPUT, msg,"",0);
        mDatas.add(message);
        mAdapter.notifyDataSetChanged();
        mChatView.setSelection(mDatas.size() - 1);
        mMsg.setText("");

        // 关闭软键盘
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        // 得到InputMethodManager的实例
        if (imm.isActive()) {
            // 如果开启
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
                    InputMethodManager.HIDE_NOT_ALWAYS);
            // 关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
        }

        NetClient.getInstance()
                .GetRobotMsg(msg, new NetCallback<String>() {
                    @Override
                    public void onSuccess(String response, int id) {
                        if (response != null) {
                            try {
                                JSONObject object = new JSONObject(response);
                                String text = object.getString("text");
                                int code = object.getInt("code");
                                ChatMessage from = null;
                                Log.i("ss", code + "");
                                Log.i("ss", response + "");
                                switch (code) {
                                    case 100000:
                                        from = new ChatMessage(ChatMessage.Type.INPUT, text,"",0);
                                        break;
                                    case 200000:
                                        String url = object.getString("url");
                                        from = new ChatMessage(ChatMessage.Type.INPUT, text, url, 200000);
                                        break;
                                    case 302000:
                                        from = new ChatMessage(ChatMessage.Type.INPUT, "新闻好多啊，我这放不下了，我就不给你看了，你自己去前面自己看吧"
                                        ,"",0);
                                        break;
                                    case 308000:
                                        String url1 = object.getString("url");
                                        from = new ChatMessage(ChatMessage.Type.INPUT, text, url1, 308000);
                                        break;
                                    case 40004:
                                        from = new ChatMessage(ChatMessage.Type.INPUT, "你今日次数使用完毕","",0);
                                        break;
                                }


                                mDatas.add(from);
                                mAdapter.notifyDataSetChanged();
                                mChatView.setSelection(mDatas.size() - 1);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(Exception e, int id) {
                        ChatMessage ms = new ChatMessage(ChatMessage.Type.INPUT, "你点击太快了，让服务器反应一下啦...");
                        mDatas.add(ms);
                        mAdapter.notifyDataSetChanged();
                        mChatView.setSelection(mDatas.size() - 1);
                    }
                });
    }


}

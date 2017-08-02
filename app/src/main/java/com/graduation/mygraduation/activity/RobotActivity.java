package com.graduation.mygraduation.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RobotActivity extends Activity {


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
     * 发送 按钮
     */
    @Bind(R.id.id_chat_send)
     Button btn;
    /**
     * 存储聊天消息
     */
    private List<ChatMessage> mDatas = new ArrayList<ChatMessage>();
    /**
     * 适配器
     */
    private ChatMessageAdapter mAdapter;


    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            ChatMessage from = (ChatMessage) msg.obj;
            mDatas.add(from);
            mAdapter.notifyDataSetChanged();
            mChatView.setSelection(mDatas.size() - 1);
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robot);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mMsg.clearFocus();
        btn.requestFocus();
        mAdapter = new ChatMessageAdapter(this, mDatas);
        mChatView.setAdapter(mAdapter);
        mDatas.add(new ChatMessage(ChatMessage.Type.INPUT, "进来聊两句吧！"));
    }

    public void sendMessage(View view) {
        final String msg = mMsg.getText().toString();
        if (TextUtils.isEmpty(msg)) {
            Toast toast = Toast.makeText(this, "您还没有填写信息呢...", Toast.LENGTH_SHORT);
//            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return;
        }

        ChatMessage to = new ChatMessage(ChatMessage.Type.OUTPUT, msg);
        to.setDate(new Date());
        mDatas.add(to);

        mAdapter.notifyDataSetChanged();
        mChatView.setSelection(mDatas.size() - 1);

        mMsg.setText("");

        // 关闭软键盘
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        // 得到InputMethodManager的实例
        if (imm.isActive()) {
            // 如果开启
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
                    InputMethodManager.HIDE_NOT_ALWAYS);
            // 关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
        }

        new Thread() {
            public void run() {
                ChatMessage from = null;
                try {
                    from = HttpUtils.sendMsg(msg);
                } catch (Exception e) {
                    from = new ChatMessage(ChatMessage.Type.INPUT, "服务器挂了呢...");
                }
                Message message = Message.obtain();
                message.obj = from;
                mHandler.sendMessage(message);
            }
        }.start();

    }
}

package com.graduation.mygraduation.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.graduation.mygraduation.R;
import com.graduation.mygraduation.entity.TodayOfHistoryDetailBean;
import com.graduation.mygraduation.net.NetCallback;
import com.graduation.mygraduation.net.NetClient;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 历史百科详情页
 */
public class TodayDetailActivity extends AppCompatActivity {


    @Bind(R.id.text_title)
    TextView textView;
    @Bind(R.id.img_back)
    ImageView img_back;

    @Bind(R.id.tv_today_content)
    TextView contents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_detail_activityctivity);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        String e_id = getIntent().getStringExtra("e_id");
        NetClient.getInstance().GetTodayOfHistoryDetailData(e_id,
                new NetCallback<TodayOfHistoryDetailBean>() {

                    @Override
                    public void onSuccess(TodayOfHistoryDetailBean response, int id) {
                        if (response.getError_code() != 0) {
                            textView.setText("无结果");
                            contents.setText("无结果");
                            return;
                        }
                        TodayOfHistoryDetailBean.ResultBean resultBean = response.getResult().get(0);
                        String content = resultBean.getContent();
                        String title = resultBean.getTitle();

                        textView.setText(title);
                        contents.setText(content);
                    }

                    @Override
                    public void onError(Exception e, int id) {

                    }
                });

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}

package com.graduation.mygraduation.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.graduation.mygraduation.R;
import com.graduation.mygraduation.activity.ShowVideoActivity;
import com.graduation.mygraduation.adapter.ExpandableAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 视频播放页面
 * <p>
 * 仅仅就集成了一丢丢直播的东西
 */
public class VideoFragment extends Fragment {

    @Bind(R.id.super_video)
    SuperTextView super_video;
    @Bind(R.id.expandable_list)
    ExpandableListView expandable_list;


    private String[] group = new String[]{
            "央视频道"
            , "地方频道"
            , "动漫在线"
            , "不知道"
            , "好难写"
            , "网络影院"
    };


    /**
     * 东方加载较慢 江苏卫视 出现卡顿 很严重  河南卫视不可用 浙江
     *
     * 好几个 不能用 真心不找了 就这样了
     */
    private String[][] children = new String[][]{
            {"CCTV-1 综合", "CCTV-2 财经", "CCTV-3 综艺", "CCTV-4 中文国际", "CCTV—5 体育", "CCTV-6 电影"
                    , "CCTV-7 军事.农业", "CCTV-8 电视剧", "CCTV-9 记录", "CCTV-10 科教", "CCTV-11 戏曲", "CCTV-12 社会与法"}
            , {"北京卫视", "东方卫视", "天津卫视", "安徽卫视", "江苏卫视", "河南卫视", "深圳卫视", "浙江卫视",
            "凤凰中文", "凤凰资讯", "凤凰卫视", "香港HKS", "香港卫视"}
            , {"新科动漫"}
            , {"CBS综艺","奇幻美剧","热门美剧"}
            , {"CCTV-音乐","Rock","Pop Latino"}
            , {"好莱坞","华语电影","惊悚电影","欢乐喜剧人","搞笑鬼片"}
    };
    private String[][] urls = new String[][]{
            //央视频道
            {"http://183.252.176.11//PLTV/88888888/224/3221225922/index.m3u8"
                    , "http://183.252.176.47//PLTV/88888888/224/3221225923/index.m3u8"
                    , "http://183.252.176.20//PLTV/88888888/224/3221225924/index.m3u8"
                    , "http://61.166.153.32:1180/play/playback/90000001000000050000000000000199/live100001.m3u8"
                    , "http://183.252.176.41//PLTV/88888888/224/3221225939/index.m3u8"
                    , "http://61.55.145.199/live/hls/1038.m3u8"
                    , "http://183.252.176.59//PLTV/88888888/224/3221225927/index.m3u8"
                    , "http://183.252.176.65//PLTV/88888888/224/3221225928/index.m3u8"
                    , "http://183.252.176.35//PLTV/88888888/224/3221225929/index.m3u8"
                    , "http://183.252.176.14//PLTV/88888888/224/3221225931/index.m3u8"
                    , "http://183.251.61.204//PLTV/88888888/224/3221225815/index.m3u8"
                    , "http://183.252.176.18//PLTV/88888888/224/3221225932/index.m3u8"}

            //地方频道
            , {"http://61.55.145.199/live/hls/1002.m3u8"
            , "http://112.89.38.22/PLTV/88888894/224/3221225508/1.m3u8"
            , "http://61.55.145.199/live/hls/1027.m3u8"
            , "http://61.55.145.199/live/hls/1001.m3u8"
            , "http://112.89.38.22/PLTV/88888894/224/3221225488/2.m3u8"
            , "http://202.99.114.87/PLTV/88888891/224/3221225517/10000100000000060000000000086761_0.smil"
            , "http://183.252.176.65//PLTV/88888888/224/3221225938/index.m3u8"
            , "http://112.89.38.25/PLTV/88888894/224/3221225487/2.m3u8"
            , "http://zv.3gv.ifeng.com/live/zhongwen800k.m3u8"
            , "http://zv.3gv.ifeng.com/live/zixun800k.m3u8"
            , "rtmp://live.hkstv.hk.lxdns.com:1935/live/hks"
            , "http://fms.cntv.lxdns.com/live/flv/channel84.flv"
            , "rtmp://live.hkstv.hk.lxdns.com/live//hks"}
            //动漫频道
            , {"http://live.g3proxy.lecloud.com/gslb?stream_id=lb_comic_720p&tag=live&ext=m3u8&sign=live_tv&platid=10&splatid=1009&format=letv&expect=1"
    }
            , {
            "http://live.gslb.letv.com/gslb?stream_id=pay_cbs_5000&tag=live&ext=m3u8&sign=live_tv&platid=10&splatid=1009&format=letv&expect=1",
            "http://live.g3proxy.lecloud.com/gslb?stream_id=lb_rmmj_1800&tag=live&ext=m3u8&sign=live_tv&platid=10&splatid=1009&format=letv&expect=1",
            "http://live.g3proxy.lecloud.com/gslb?stream_id=lb_qhmj_1800&tag=live&ext=m3u8&sign=live_tv&platid=10&splatid=1009&format=letv&expect=1"

    }
            , {
            "http://183.251.61.222//PLTV/88888888/224/3221225818/index.m3u8"
            ,"http://edge.music-choice-vc-chaina2.top.comcast.net/VideoChannels/138/chunklist.m3u8"
            ,"http://edge.music-choice-vc-chaina1.top.comcast.net/VideoChannels/123/chunklist.m3u8"

    }
            , {
            "http://live.gslb.letv.com/gslb?stream_id=lb_hlw_1300&tag=live&ext=m3u8&sign=live_tv&platid=10&splatid=1009&format=letv&expect=1",
            "http://live.gslb.letv.com/gslb?stream_id=lb_hydy_1300&tag=live&ext=m3u8&sign=live_tv&platid=10&splatid=1009&format=letv&expect=1",
            "http://live.gslb.letv.com/gslb?stream_id=lb_hk_Thrill_3000&tag=live&ext=m3u8&sign=live_tv&platid=10&splatid=1009&format=letv&expect=1",
            "http://live.g3proxy.lecloud.com/gslb?stream_id=lb_hlxjr_1800&tag=live&ext=m3u8&sign=live_tv&platid=10&splatid=1009&format=letv&expect=1",
            "http://live.gslb.letv.com/gslb?stream_id=lb_hk_hkghost_3000&tag=live&ext=m3u8&sign=live_tv&platid=10&splatid=1009&format=letv&expect=1"

    }
    };

    public VideoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        ButterKnife.bind(this, view);

        init();

        return view;
    }

    private void init() {
        expandable_list.setAdapter(new ExpandableAdapter(group, children, getActivity()));
        expandable_list.setGroupIndicator(null);
        expandable_list.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (parent.isGroupExpanded(groupPosition)) {
                    // 如果展开则关闭
                    parent.collapseGroup(groupPosition);
                } else {
                    // 如果关闭则打开，注意这里是手动打开不要默认滚动否则会有bug
                    parent.expandGroup(groupPosition);
                }
                return true;
            }
        });

        expandable_list.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(getActivity(), ShowVideoActivity.class);
                intent.putExtra("from", "z");
                intent.putExtra("url", urls[groupPosition][childPosition]);
                intent.putExtra("name", children[groupPosition][childPosition]);
                startActivity(intent);
                return true;
            }
        });
    }

}

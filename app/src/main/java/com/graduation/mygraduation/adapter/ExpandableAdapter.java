package com.graduation.mygraduation.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.graduation.mygraduation.R;

/**
 * Created by 杨大少 on 2017/5/6.
 */

public class ExpandableAdapter extends BaseExpandableListAdapter {

    private int[][] icon = new int[][]{
            {
                    R.drawable.cctv_1, R.drawable.cctv_2, R.drawable.cctv_3, R.drawable.cctv_4,
                    R.drawable.cctv_5, R.drawable.cctv_6, R.drawable.cctv_7, R.drawable.cctv_8,
                    R.drawable.cctv_9, R.drawable.cctv_10, R.drawable.cctv_11, R.drawable.cctv_12
            }, {
            R.drawable.cctv_1, R.drawable.cctv_2, R.drawable.cctv_3, R.drawable.cctv_4,
            R.drawable.cctv_5, R.drawable.cctv_6, R.drawable.cctv_7, R.drawable.cctv_8,
            R.drawable.cctv_9, R.drawable.cctv_10, R.drawable.cctv_11, R.drawable.cctv_12, R.drawable.cctv_1
    }, {
            R.drawable.cctv_1
    }, {
            R.drawable.cctv_1, R.drawable.cctv_2, R.drawable.cctv_3
    }, {
            R.drawable.cctv_1, R.drawable.cctv_2, R.drawable.cctv_3
    }, {
            R.drawable.cctv_1, R.drawable.cctv_2, R.drawable.cctv_3, R.drawable.cctv_4,
            R.drawable.cctv_5
    }
    };

    private String[] group;
    private String[][] children;
    private Context context;

    public ExpandableAdapter(String[] group, String[][] children, Context context) {
        this.group = group;
        this.children = children;
        this.context = context;
    }

    public ExpandableAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return group.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        return children[groupPosition].length;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return group[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return children[groupPosition][childPosition];
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder holder = null;
        View view = convertView;
        if (convertView == null) {
            holder = new GroupHolder();
            view = LayoutInflater.from(context).inflate(R.layout.group_layout, parent, false);
            holder.title = (TextView) view.findViewById(R.id.text_name);
            holder.icon = (ImageView) view.findViewById(R.id.img_icon);
            holder.arrow = (ImageView) view.findViewById(R.id.img_arrow);
            view.setTag(holder);
        } else {
            holder = (GroupHolder) view.getTag();
        }

        if (isExpanded) {
            holder.arrow.setImageResource(R.drawable.bottom);
        } else {
            holder.arrow.setImageResource(R.drawable.right);
        }

        holder.title.setText(group[groupPosition]);
        holder.icon.setImageResource(R.drawable.beauty);
//        View view = LayoutInflater.from(context).inflate(R.layout.group_layout, null);
//        SuperTextView groups = (SuperTextView) view.findViewById(R.id.super_group);
//        groups.setLeftString(group[groupPosition]);
//        return view;

//        LinearLayout ll = new LinearLayout(context);
//        ll.setOrientation(LinearLayout.HORIZONTAL);
//        TextView textView = getTextView();
//        textView.setText(getGroup(groupPosition).toString());
//        textView.setPadding(10, 0, 0, 0);
//        ll.addView(textView);
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = convertView;
        ChildHolder holder = null;
        if (view == null) {
            holder = new ChildHolder();
            view = LayoutInflater.from(context).inflate(R.layout.child_layout, parent, false);
            holder.name = (TextView) view.findViewById(R.id.super_child);
            holder.img_icon = (ImageView) view.findViewById(R.id.img_child);
            view.setTag(holder);
        } else {
            holder = (ChildHolder) view.getTag();
        }
        holder.name.setText(children[groupPosition][childPosition]);
        if (groupPosition == 0) {

        }
        holder.img_icon.setImageResource(icon[groupPosition][childPosition]);
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class GroupHolder {
        private TextView title;
        private ImageView icon;
        private ImageView arrow;
    }

    class ChildHolder {
        private TextView name;
        private ImageView img_icon;
    }


    private TextView getTextView() {
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 64);
        TextView textView = new TextView(context);
        textView.setLayoutParams(lp);
        textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        textView.setPadding(36, 0, 0, 0);
        textView.setTextSize(20);
        return textView;
    }

}

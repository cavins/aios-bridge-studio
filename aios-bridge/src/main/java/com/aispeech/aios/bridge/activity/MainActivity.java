package com.aispeech.aios.bridge.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.aispeech.aios.bridge.R;

import service.DaemonService;
import service.MainService;

public class MainActivity extends Activity {
    private TextView textView;

    //接入地图
    //加载自定义命令
    //加载自定义唤醒词

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
//        ListView mListView = (ListView) findViewById(R.id.list_view);
//        String versionText = String.format(getString(R.string.sdk_version), SDKBuild.VERSION.NAME);
//        String versionText = "欢迎使用AIOS for car SDK";
//        textView = (TextView) findViewById(R.id.text_version_info);
//        textView.setText(versionText);

        // 添加ListItem，设置事件响应
//        if (mListView != null) {
//            mListView.setAdapter(new DemoListAdapter());
//            mListView.setOnItemClickListener(new OnItemClickListener() {
//                public void onItemClick(AdapterView<?> arg0, View v, int index, long arg3) {
//                    onListItemClick(index);
//                }
//            });
//        }

        startService(new Intent(this, MainService.class));
        startService(new Intent(this, DaemonService.class));
        MainActivity.this.finish();
    }


    void onListItemClick(int index) {
        Intent intent =  new Intent(MainActivity.this, DEMOS[index].demoClass);
        this.startActivity(intent);
    }

    private static final DemoInfo[] DEMOS = {
            new DemoInfo(R.string.demo_title_phone,
                    R.string.demo_desc_phone, PhoneActivity.class),
            new DemoInfo(R.string.demo_title_music,
                    R.string.demo_desc_music, MusicActivity.class),
            new DemoInfo(R.string.demo_title_customize, R.string.demo_desc_customize,
                    CustomizeActivity.class),
            new DemoInfo(R.string.demo_title_system,
                    R.string.demo_desc_system, SystemActivity.class),
            new DemoInfo(R.string.demo_title_map,
                    R.string.demo_desc_map, MapActivity.class),
            new DemoInfo(R.string.demo_title_setting,
                    R.string.demo_desc_setting, SettingActivity.class),
            new DemoInfo(R.string.demo_title_status,
                    R.string.demo_desc_status, StatusActivity.class),
            new DemoInfo(R.string.demo_title_radio,
                    R.string.demo_desc_radio, RadioActivity.class),
            new DemoInfo(R.string.demo_title_ui,
                    R.string.demo_desc_ui, UIActivity.class)
    };

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, MainService.class));
        stopService(new Intent(this, DaemonService.class));
    }

    private class DemoListAdapter extends BaseAdapter {
        public DemoListAdapter() {
            super();
        }

        @Override
        public View getView(int index, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (null == convertView) {
                convertView = View.inflate(MainActivity.this,
                        R.layout.demo_info_item, null);
                viewHolder = new ViewHolder();
                viewHolder.title = (TextView) convertView.findViewById(R.id.title);
                viewHolder.desc = (TextView) convertView.findViewById(R.id.desc);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.title.setText(DEMOS[index].title);
            viewHolder.desc.setText(DEMOS[index].desc);
            if (index >= 16) {
                viewHolder.title.setTextColor(Color.YELLOW);
            }
            return convertView;
        }

        @Override
        public int getCount() {
            return DEMOS.length;
        }

        @Override
        public Object getItem(int index) {
            return DEMOS[index];
        }

        @Override
        public long getItemId(int id) {
            return id;
        }

        class ViewHolder {
            TextView title;
            TextView desc;
        }
    }

    private static class DemoInfo {
        private final int title;
        private final int desc;
        private final Class<? extends Activity> demoClass;

        public DemoInfo(int title, int desc,
                        Class<? extends Activity> demoClass) {
            this.title = title;
            this.desc = desc;
            this.demoClass = demoClass;
        }
    }
}

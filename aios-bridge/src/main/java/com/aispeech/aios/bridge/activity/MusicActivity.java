package com.aispeech.aios.bridge.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.aispeech.aios.bridge.R;
import com.aispeech.aios.bridge.listener.BridgeMusicListener;
import com.aispeech.aios.sdk.AIOSForCarSDK;
import com.aispeech.aios.sdk.bean.LocalMusic;
import com.aispeech.aios.sdk.manager.AIOSMusicManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 音乐功能Demo，主要是AIOSMusicListener接口类，可以在音乐app中实现
 */
public class MusicActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        AIOSMusicManager.getInstance().registerMusicListener(new BridgeMusicListener());
        AIOSMusicManager.getInstance().setLocalMusicInfo("AIOS音乐", AIOSForCarSDK.getPackageName());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_sync_musics:
                List<LocalMusic> localMusics = new ArrayList<LocalMusic>();

                LocalMusic localMusic = new LocalMusic();
                localMusic.setTitle("南山南");  //歌曲名
                localMusic.setArtist("马頔"); //歌手名
                localMusic.setName("南山南-马頔");  //文件名
                localMusics.add(localMusic);
                localMusics.add(new LocalMusic("皆非", "马頔", "皆非-马頔"));

                AIOSMusicManager.getInstance().syncLocalMusicList(localMusics);
                break;
            case R.id.button_scan_enable:
                //只是Demo作为演示，代码中在初始化时调用一次即可
                AIOSMusicManager.getInstance().setScanLocalMusicEnabled(true);
                break;

            case R.id.button_scan_disable:
                //只是Demo作为演示，代码中在初始化时调用一次即可
                AIOSMusicManager.getInstance().setScanLocalMusicEnabled(false);
                break;

            case R.id.button_display_list_enable:
                //设置纯搜歌名时，显示结果列表
                AIOSMusicManager.getInstance().setDisplayListEnabled(true);
                break;

            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //清除音乐应用信息
        AIOSMusicManager.getInstance().cleanLocalMusicInfo();
        //注销音乐监听器
        AIOSMusicManager.getInstance().unregisterMusicListener();
    }
}

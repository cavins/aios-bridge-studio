package com.aispeech.aios.bridge.listener;

import com.aispeech.ailog.AILog;
import com.aispeech.aios.common.property.MusicProperty.PlayState;
import com.aispeech.aios.common.property.MusicProperty.PlayMode;
import com.aispeech.aios.sdk.bean.Music;
import com.aispeech.aios.sdk.bean.SearchMusic;
import com.aispeech.aios.sdk.listener.AIOSMusicListener;
import com.aispeech.aios.sdk.manager.AIOSMusicManager;
import com.aispeech.aios.sdk.manager.AIOSTTSManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 音乐监听器类
 */
public class BridgeMusicListener implements AIOSMusicListener {
    private static final String TAG = "Bridge - BridgeMusicListener";
    private int playState = PlayState.PAUSE;

    @Override
    public int getPlayState() {
        return playState;
    }

    @Override
    public void onPlay() {
        AIOSTTSManager.speak("播放音乐");
        playState = PlayState.PLAYING;

        //如果状态成功改变，调用接口通知AIOS
        AIOSMusicManager.getInstance().setPlayState(playState);
    }

    @Override
    public void onPause() {
        AIOSTTSManager.speak("播放已暂停");
        playState = PlayState.PAUSE;

        //如果状态成功改变，调用接口通知AIOS
        AIOSMusicManager.getInstance().setPlayState(playState);
    }

    @Override
    public void onResume() {
        AIOSTTSManager.speak("播放将继续");
        playState = PlayState.PLAYING;

        //如果状态成功改变，调用接口通知AIOS
        AIOSMusicManager.getInstance().setPlayState(playState);
    }

    @Override
    public void onStop() {
        AIOSTTSManager.speak("播放已停止");
        playState = PlayState.STOP;

        //如果状态成功改变，调用接口通知AIOS
        AIOSMusicManager.getInstance().setPlayState(playState);
    }

    @Override
    public void onExit() {
        AIOSTTSManager.speak("退出音乐");
        playState = PlayState.STOP;

        //如果状态成功改变，调用接口通知AIOS
        AIOSMusicManager.getInstance().setPlayState(playState);
    }

    @Override
    public void onPrev() {
        AIOSTTSManager.speak("好的");
        playState = PlayState.PLAYING;

        //如果状态成功改变，调用接口通知AIOS
        AIOSMusicManager.getInstance().setPlayState(playState);
    }

    @Override
    public void onNext() {
        AIOSTTSManager.speak("好的");
        playState = PlayState.PLAYING;

        //如果状态成功改变，调用接口通知AIOS
        AIOSMusicManager.getInstance().setPlayState(playState);
    }

    @Override
    public void onPlayList(List<Music> musics) {
        AIOSTTSManager.speak("播放音乐列表");
        AILog.d(TAG, "播放音乐列表：" + musics.toString());
        playState = PlayState.PLAYING;

        //如果状态成功改变，调用接口通知AIOS
        AIOSMusicManager.getInstance().setPlayState(playState);
    }

    @Override
    public void onSearch(List<SearchMusic> searchKeys) {
        SearchMusic searchKey = searchKeys.get(0);
        String artist = searchKey.getArtist();
        String title = searchKey.getTitle();
        String album = searchKey.getAlbum();
        String style = searchKey.getStyle();
        String type = searchKey.getType();
        AILog.d(TAG, "正在为你搜歌：" + artist + title + album + style + type);

        //模拟搜索结果，正式项目中请注意该方法由主线程调用，不要做耗时操作
        List<Music> musics = new ArrayList<>();
        musics.add(new Music("0001", "七里香", "周杰伦"));
        musics.add(new Music("0002", "借口", "周杰伦", "七里香", 111, 56231, "/card/music/02.mp3", "借口-周杰伦"));
        //localName属性只针对本地音乐，如果在线音乐设置该属性，会直接被清空
        musics.add(new Music("0003", "搁浅", "周杰伦", "七里香", 127, 56231, "http://music.163.com/card/music/03.mp3", "无效的localName"));
        musics.add(new Music("0004", "半岛铁盒", "周杰伦", "七里香", 129, 56231, "http://music.163.com/card/music/04.mp3"));
        musics.add(new Music("0005", "暗号", "周杰伦", "七里香", 100, 56231, "/card/music/05.mp3"));
        musics.add(new Music("0006", "回到过去", "周杰伦", "七里香", 201, 56231, "http://music.163.com/card/music/06.mp3"));
        //将模拟的搜索结果发给AIOS；1是使用的搜索关键词组，在AIOS显示搜索结果时该序号会有所体现
        //以下方法序号从1开始，废弃！请使用新方法
        //AIOSMusicManager.getInstance().postMusicSearchResult(musics, 1);
        AIOSMusicManager.getInstance().postMusicSearchResultFromZero(musics, 0);
    }

    @Override
    public void onSetPlayMode(String musicMode) {
        if (musicMode.equals(PlayMode.ORDER)) {
            AIOSTTSManager.speak("顺序播放");

        } else if (musicMode.equals(PlayMode.SINGLE)) {
            AIOSTTSManager.speak("单曲循环");

        } else if (musicMode.equals(PlayMode.RANDOM)) {
            AIOSTTSManager.speak("随机播放");

        } else if (musicMode.equals(PlayMode.CIRCLE)) {
            AIOSTTSManager.speak("列表循环");

        } else {
        }
    }

    @Override
    public String onSyncMusicsFinished(boolean isSuccess) {
        if (isSuccess) {
            return "本地音乐同步完成";
        } else {
            return "本地音乐同步失败";
        }
    }
}

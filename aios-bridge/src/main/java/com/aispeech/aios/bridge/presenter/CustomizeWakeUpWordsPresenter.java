package com.aispeech.aios.bridge.presenter;

/**
 * Created by Administrator on 2016/12/5 0005.
 */

import com.aispeech.aios.common.bean.MajorWakeup;
import com.aispeech.aios.sdk.bean.ShortcutWakeup;
import com.aispeech.aios.sdk.manager.AIOSCustomizeManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 增加自定义的唤醒词
 */
public class CustomizeWakeUpWordsPresenter {
    private static CustomizeWakeUpWordsPresenter customizeWakeUpWordsPresenter;
    private List<MajorWakeup> majorWakeups;
    public static final String WAKE_UP_1 = "征仔你好";
    public static final String WAKE_UP_2 = "你好征仔";

    /**
     * 唤醒词的拼音
     */
    public static final String WAKE_UP_1_PINYIN = "zheng zai ni hao";
    public static final String WAKE_UP_2_PINYIN = "ni hao zheng zai";

    public static synchronized CustomizeWakeUpWordsPresenter getInstance() {

        if (customizeWakeUpWordsPresenter == null) {

            customizeWakeUpWordsPresenter = new CustomizeWakeUpWordsPresenter();
        }
        return customizeWakeUpWordsPresenter;
    }

    public void loadingWakeUpWords() {
        setMajorWakeUpWords();
    }

    private void setMajorWakeUpWords() {
        majorWakeups = new ArrayList<MajorWakeup>();
        majorWakeups.add(new MajorWakeup(WAKE_UP_1, WAKE_UP_1_PINYIN, 0.12f));
        majorWakeups.add(new MajorWakeup(WAKE_UP_2, WAKE_UP_2_PINYIN, 0.12f));
        AIOSCustomizeManager.getInstance().setMajorWakeup(majorWakeups);
    }
}

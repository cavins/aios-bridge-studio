package com.aispeech.aios.bridge.presenter;

/**
 * Created by Administrator on 2016/12/3 0003.
 */


import com.aispeech.aios.sdk.bean.Command;
import com.aispeech.aios.sdk.manager.AIOSCustomizeManager;

import java.util.ArrayList;

/**
 * 增加自定义的命令，以字符串数组的方式添加
 */
public class CustomizeCommandsPresenter {

    /**
     * 语音反馈词
     */
    public static final String OPEN_FM = "/condition/openfm";//打开fm反馈词
    public static final String OPEN_EDOG = "/condition/openedog";//打开电子狗反馈词
    public static final String OPEN_RADAR = "/condition/openradar";//打开雷达反馈词
    public static final String CLOSE_RADAR = "/condition/closeradar";//关闭雷达反馈词
    public static final String OPEN_XIMALAYA = "/condition/openximalaya";//打开喜马拉雅反馈词
    public static final String OPEN_BAIDUMAP = "/condition/openbaidumap";//打开百度地图反馈词
    public static final String OPEN_GAODEMAP = "/condition/opengaodemap";//打开高德地图反馈词
    public static final String SHUTDOWN = "/condition/shutdown";//关机
    public static final String REBOOT = "/condition/reboot";//重启机器

    /**
     * 自定义命令集
     */
    private static final Command FM_COMMAND = new Command(OPEN_FM, new String[] {"开启fm","打开fm","帮我打开fm","开下fm"});//FM命令集
    private static final Command EDOG_COMMAND = new Command(OPEN_EDOG, new String[] {"开启电子狗","打开电子狗","帮我开电子狗","开下电子狗"});//电子狗命令集
    private static final Command RADAR_COMMAND_ON = new Command(OPEN_RADAR, new String[] {"开启雷达","打开雷达","帮我开雷达","开下雷达"});//雷达命令集
    private static final Command RADAR_COMMAND_OFF = new Command(CLOSE_RADAR, new String[] {"关闭雷达","关掉雷达","帮我关雷达"});//雷达命令集
    private static final Command XIMALAYA_COMMAND = new Command(OPEN_XIMALAYA, new String[] {"开启喜马拉雅","打开喜马拉雅","帮我开喜马拉雅","开下喜马拉雅"});//喜马拉雅命令集
    private static final Command BAIDUMAP_COMMAND = new Command(OPEN_BAIDUMAP, new String[] {"开启百度地图","打开百度地图","帮我开百度地图","开下百度地图"});//百度地图命令集
    private static final Command GAODEMAP_COMMAND = new Command(OPEN_GAODEMAP, new String[] {"开启高德地图","打开高德地图","帮我开高德地图","开下高德地图"});//高德地图命令集
    private static final Command SHUTDOWN_COMMAND = new Command(SHUTDOWN, new String[] {"关机", "帮我关机"});//关机命令集
    private static final Command REBOOT_COMMAND = new Command(REBOOT, new String[] {"重启", "重启机器", "帮我重启", "帮我重启机器"});//重启命令集


    private ArrayList<Command> initCommands;
    private static CustomizeCommandsPresenter customizeCommandsPresenter;

    public static synchronized CustomizeCommandsPresenter getInstance() {

        if (customizeCommandsPresenter == null) {

            customizeCommandsPresenter = new CustomizeCommandsPresenter();
        }
        return customizeCommandsPresenter;
    }

    public CustomizeCommandsPresenter() {
        loadingCommands();
    }

    /**
     * 注册自定义命令
     */
    public void regisCommands() {
        AIOSCustomizeManager.getInstance().regCommands(initCommands);
    }

    /**
     * 装填命令集，新加命令记得在这里加上新的命令哦
     */
    private void loadingCommands() {
        initCommands = new ArrayList<Command>();
        initCommands.add(FM_COMMAND);
        initCommands.add(EDOG_COMMAND);
        initCommands.add(RADAR_COMMAND_ON);
        initCommands.add(RADAR_COMMAND_OFF);
        initCommands.add(XIMALAYA_COMMAND);
        initCommands.add(BAIDUMAP_COMMAND);
        initCommands.add(GAODEMAP_COMMAND);
        initCommands.add(SHUTDOWN_COMMAND);
        initCommands.add(REBOOT_COMMAND);
    }

    public ArrayList<Command> getAllCommands() {
        if(initCommands.size() == 0)
            loadingCommands();
        return initCommands;
    }
}

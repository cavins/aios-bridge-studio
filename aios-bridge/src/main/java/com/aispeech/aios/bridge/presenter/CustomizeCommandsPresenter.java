package com.aispeech.aios.bridge.presenter;

/**
 * Created by Administrator on 2016/12/3 0003.
 */


import com.aispeech.aios.sdk.bean.Command;
import com.aispeech.aios.sdk.bean.ShortcutWakeup;
import com.aispeech.aios.sdk.manager.AIOSCustomizeManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 增加自定义的命令，以字符串数组的方式添加
 */
public class CustomizeCommandsPresenter {

    /**
     * 语音反馈词
     */
    public static final String OPEN_FM = "/condition/openfm";//打开fm反馈词
    public static final String CLOSE_FM = "/condition/closefm";//关闭fm反馈
    public static final String OPEN_EDOG = "/condition/openedog";//打开电子狗反馈词
    public static final String CLOSE_EDOG = "/condition/closeedog";//关闭电子狗反馈词
    public static final String OPEN_RADAR = "/condition/openradar";//打开雷达反馈词
    public static final String CLOSE_RADAR = "/condition/closeradar";//关闭雷达反馈词
    public static final String OPEN_XIMALAYA = "/condition/openximalaya";//打开喜马拉雅反馈词
    public static final String OPEN_BAIDUMAP = "/condition/openbaidumap";//打开百度地图反馈词
    public static final String OPEN_GAODEMAP = "/condition/opengaodemap";//打开高德地图反馈词
    public static final String SHUTDOWN = "/condition/shutdown";//关机
    public static final String REBOOT = "/condition/reboot";//重启机器
    public static final String OPEN_BT = "/condition/openBlueToothPhone";//打开蓝牙反馈
    public static final String CLOSE_BT = "/condition/closeBlueToothPhone";//关闭蓝牙
    public static final String SET_VOLUME_1 = "/condition/setvolume1";//音量调到1
    public static final String SET_VOLUME_2 = "/condition/setvolume2";//音量调到2
    public static final String SET_VOLUME_3 = "/condition/setvolume3";//音量调到3
    public static final String SET_VOLUME_4 = "/condition/setvolume4";//音量调到4
    public static final String SET_VOLUME_5 = "/condition/setvolume5";//音量调到5
    public static final String CURRENT_VOLUME = "/condition/currentvolume";//询问当前音量

    public static final String SET_BRIGHTNESS_1 = "/condition/brightness1";//亮度调到1
    public static final String SET_BRIGHTNESS_2 = "/condition/brightness2";//亮度调到2
    public static final String SET_BRIGHTNESS_3 = "/condition/brightness3";//亮度调到3
    public static final String SET_BRIGHTNESS_4 = "/condition/brightness4";//亮度调到4
    public static final String SET_BRIGHTNESS_5 = "/condition/brightness5";//亮度调到5
    public static final String CURRENT_BRIGHTNESS = "/condition/currentbrightness";//询问当前亮度


    public static final String OPEN_HOME_QUICKSET = "da kai kuai jie she zhi";//打开快捷菜单
    public static final String CLOSE_HOME_QUICKSET = "guan bi kuai jie she zhi";//关闭快捷菜单
    public static final String EXIT_AND_FINISH_MAP = "/condition/exitandfinishmap";//退出和关闭导航
	
	    /**
     * 自定义 快捷命令集
     */
    /*加载联系人*/
    public static final String BT_LOAD_CONTACT_1 = "que ding";
    public static final String BT_LOAD_CONTACT_2 = "que ding";
    /*取消加载联系人*/
    public static final String BT_CANCEL_CONTACT_1 = "qv xiao";
    public static final String BT_CANCEL_CONTACT_2 = "qv xiao";
    public static final String BT_CANCEL_CONTACT_3 = "qv xiao";
    public static final String BT_CANCEL_CONTACT_4 = "bu yao";

    public static final String TUI_CHU_DAO_HANG = "tui chu dao hang";
    public static final String GUAN_BI_DAO_HANG = "guan bi dao hang";
    public static final String QU_XIAO_DAO_HANG = "qv xiao dao hang";
    public static final String JIE_SHU_DAO_HANG = "jie shu dao hang";
    public static final String TING_ZHI_DAO_HANG = "ting zhi dao hang";


    /**
     * 自定义命令集
     */
    private static final Command FM_COMMAND_ON = new Command(OPEN_FM, new String[]{"开启fm", "打开fm", "帮我打开fm", "开下fm"});//FM命令集
    private static final Command FM_COMMAND_OFF = new Command(CLOSE_FM, new String[]{"关闭fm", "关掉fm", "断开fm", "关fm"});
    private static final Command OPENEDOG_COMMAND = new Command(OPEN_EDOG, new String[] {"开启电子狗","打开电子狗","帮我开电子狗","开下电子狗"});//打开电子狗命令集
    private static final Command CLOSEEDOG_COMMAND = new Command(CLOSE_EDOG, new String[] {"关闭电子狗","关掉电子狗","帮我关电子狗"});//关闭电子狗命令集
    private static final Command RADAR_COMMAND_ON = new Command(OPEN_RADAR, new String[] {"开启雷达","打开雷达","帮我开雷达","开下雷达"});//雷达命令集
    private static final Command RADAR_COMMAND_OFF = new Command(CLOSE_RADAR, new String[] {"关闭雷达","关掉雷达","帮我关雷达"});//雷达命令集
    private static final Command XIMALAYA_COMMAND = new Command(OPEN_XIMALAYA, new String[] {"开启喜马拉雅","打开喜马拉雅","帮我开喜马拉雅","开下喜马拉雅"});//喜马拉雅命令集
    private static final Command BAIDUMAP_COMMAND = new Command(OPEN_BAIDUMAP, new String[] {"开启百度地图","打开百度地图","帮我开百度地图","开下百度地图"});//百度地图命令集
    private static final Command GAODEMAP_COMMAND = new Command(OPEN_GAODEMAP, new String[] {"开启高德地图","打开高德地图","帮我开高德地图","开下高德地图"});//高德地图命令集
    private static final Command SHUTDOWN_COMMAND = new Command(SHUTDOWN, new String[] {"关机", "帮我关机"});//关机命令集
    private static final Command REBOOT_COMMAND = new Command(REBOOT, new String[] {"重启", "重启机器", "帮我重启", "帮我重启机器"});//重启命令集
    private static final Command BT_COMMAND_ON = new Command(OPEN_BT, new String[]{"打开蓝牙", "打开蓝牙电话", "帮我打开蓝牙", "帮我打开蓝牙电话", "开启蓝牙", "开启蓝牙电话", "启动蓝牙电话", "启动蓝牙"});//打开蓝牙命令集
    private static final Command BT_COMMAND_OFF = new Command(CLOSE_BT, new String[]{"退出蓝牙", "关闭蓝牙电话", "关掉蓝牙", "关掉蓝牙电话"});//关闭蓝牙命令集
    private static final Command SETVOLUMELEVEL1_COMMAND = new Command(SET_VOLUME_1, new String[]{"音量调到一"});//音量调到一命令集
    private static final Command SETVOLUMELEVEL2_COMMAND = new Command(SET_VOLUME_2, new String[]{"音量调到二"});//音量调到二命令集
    private static final Command SETVOLUMELEVEL3_COMMAND = new Command(SET_VOLUME_3, new String[]{"音量调到三"});//音量调到三命令集
    private static final Command SETVOLUMELEVEL4_COMMAND = new Command(SET_VOLUME_4, new String[]{"音量调到四"});//音量调到四命令集
    private static final Command SETVOLUMELEVEL5_COMMAND = new Command(SET_VOLUME_5, new String[]{"音量调到五"});//音量调到五命令集
    private static final Command CURRENT_VOLUME_COMMAND = new Command(CURRENT_VOLUME, new String[]{"当前音量", "现在的音量", "现在音量是多少"});//询问当前音量

    private static final Command SETBRIGHTNESSLEVEL1_COMMAND = new Command(SET_BRIGHTNESS_1, new String[]{"亮度调到一"});//亮度调到一命令集
    private static final Command SETBRIGHTNESSLEVEL2_COMMAND = new Command(SET_BRIGHTNESS_2, new String[]{"亮度调到二"});//亮度调到二命令集
    private static final Command SETBRIGHTNESSLEVEL3_COMMAND = new Command(SET_BRIGHTNESS_3, new String[]{"亮度调到三"});//亮度调到三命令集
    private static final Command SETBRIGHTNESSLEVEL4_COMMAND = new Command(SET_BRIGHTNESS_4, new String[]{"亮度调到四"});//亮度调到四命令集
    private static final Command SETBRIGHTNESSLEVEL5_COMMAND = new Command(SET_BRIGHTNESS_5, new String[]{"亮度调到五"});//亮度调到五命令集
    private static final Command CURRENT_BRIGHTNESS_COMMAND = new Command(CURRENT_BRIGHTNESS, new String[]{"当前亮度", "现在的亮度", "现在亮度是多少"});//亮度调到五命令集
    private static final Command EXIT_AND_FINISH_MAP_COMMAND = new Command(EXIT_AND_FINISH_MAP, new String[] {"退出导航", "关闭导航", "停止导航", "取消导航", "结束导航"});//退出导航

//    private static final Command OPEN_HOME_QUICKSET_COMMAND = new Command(OPEN_HOME_QUICKSET, new String[]{"打开快捷设置", "帮我打开快捷设置", "快捷设置"});//打开桌面快捷菜单
//    private static final Command CLOSE_HOME_QUICKSET_COMMAND = new Command(CLOSE_HOME_QUICKSET, new String[] {"关闭快捷设置", "帮我关闭快捷设置"});//关闭快捷菜单



    private ArrayList<Command> initCommands;
    private List<ShortcutWakeup> shortcutWakeupList = new ArrayList<ShortcutWakeup>();
    private static CustomizeCommandsPresenter customizeCommandsPresenter;

    public static synchronized CustomizeCommandsPresenter getInstance() {

        if (customizeCommandsPresenter == null) {

            customizeCommandsPresenter = new CustomizeCommandsPresenter();
        }
        return customizeCommandsPresenter;
    }

    public CustomizeCommandsPresenter() {
        loadingCommands();
        loadingShortcutCommand();
    }
	
	    public void registQuickWakeUp() {
        //定制快捷唤醒词
        List<ShortcutWakeup> shortcutWakeupList = new ArrayList<ShortcutWakeup>();
        shortcutWakeupList.add(new ShortcutWakeup(BT_LOAD_CONTACT_1, 0.12f));
        shortcutWakeupList.add(new ShortcutWakeup(BT_LOAD_CONTACT_2, 0.12f));
        shortcutWakeupList.add(new ShortcutWakeup(BT_CANCEL_CONTACT_1, 0.12f));
        shortcutWakeupList.add(new ShortcutWakeup(BT_CANCEL_CONTACT_2, 0.12f));
        shortcutWakeupList.add(new ShortcutWakeup(BT_CANCEL_CONTACT_3, 0.12f));
        shortcutWakeupList.add(new ShortcutWakeup(BT_CANCEL_CONTACT_4, 0.12f));
        AIOSCustomizeManager.getInstance().registerShortcutWakeups(shortcutWakeupList);
    }

    /**
     * 注册自定义命令
     */
    public void regisCommands() {
        AIOSCustomizeManager.getInstance().regCommands(initCommands);
    }

    public void regisCustomizeShortcutListener() {
        AIOSCustomizeManager.getInstance().registerShortcutWakeups(shortcutWakeupList);
    }

    /**
     * 装填快速命令集，新加命令记得在这里加上新的命令哦
     */
    private void loadingShortcutCommand() {
        shortcutWakeupList = new ArrayList<ShortcutWakeup>();
        shortcutWakeupList.add(new ShortcutWakeup(OPEN_HOME_QUICKSET, 0.12f));
        shortcutWakeupList.add(new ShortcutWakeup(CLOSE_HOME_QUICKSET, 0.12f));
        shortcutWakeupList.add(new ShortcutWakeup("da kai che men ", 0.12f));
        shortcutWakeupList.add(new ShortcutWakeup("guan bi che men", 0.12f));

//        shortcutWakeupList.add(new ShortcutWakeup(TUI_CHU_DAO_HANG, 0.12f));
//        shortcutWakeupList.add(new ShortcutWakeup(JIE_SHU_DAO_HANG, 0.12f));
//        shortcutWakeupList.add(new ShortcutWakeup(GUAN_BI_DAO_HANG, 0.12f));
//        shortcutWakeupList.add(new ShortcutWakeup(QU_XIAO_DAO_HANG, 0.12f));
//        shortcutWakeupList.add(new ShortcutWakeup(TING_ZHI_DAO_HANG, 0.12f));
    }

    /**
     * 装填命令集，新加命令记得在这里加上新的命令哦
     */
    private void loadingCommands() {
        initCommands = new ArrayList<Command>();
        initCommands.add(FM_COMMAND_ON);
        initCommands.add(FM_COMMAND_OFF);
        initCommands.add(OPENEDOG_COMMAND);
        initCommands.add(CLOSEEDOG_COMMAND);
        initCommands.add(RADAR_COMMAND_ON);
        initCommands.add(RADAR_COMMAND_OFF);
        initCommands.add(XIMALAYA_COMMAND);
        initCommands.add(BAIDUMAP_COMMAND);
        initCommands.add(GAODEMAP_COMMAND);
        initCommands.add(SHUTDOWN_COMMAND);
        initCommands.add(REBOOT_COMMAND);
        initCommands.add(BT_COMMAND_OFF);
        initCommands.add(BT_COMMAND_ON);
        initCommands.add(SETVOLUMELEVEL1_COMMAND);
        initCommands.add(SETVOLUMELEVEL2_COMMAND);
        initCommands.add(SETVOLUMELEVEL3_COMMAND);
        initCommands.add(SETVOLUMELEVEL4_COMMAND);
        initCommands.add(SETVOLUMELEVEL5_COMMAND);
        initCommands.add(SETBRIGHTNESSLEVEL1_COMMAND);
        initCommands.add(SETBRIGHTNESSLEVEL2_COMMAND);
        initCommands.add(SETBRIGHTNESSLEVEL3_COMMAND);
        initCommands.add(SETBRIGHTNESSLEVEL4_COMMAND);
        initCommands.add(SETBRIGHTNESSLEVEL5_COMMAND);
        initCommands.add(CURRENT_VOLUME_COMMAND);
        initCommands.add(CURRENT_BRIGHTNESS_COMMAND);
        initCommands.add(EXIT_AND_FINISH_MAP_COMMAND);
    }

    public ArrayList<Command> getAllCommands() {
        if(initCommands.size() == 0)
            loadingCommands();
        return initCommands;
    }
}

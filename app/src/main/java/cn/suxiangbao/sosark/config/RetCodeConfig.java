package cn.suxiangbao.sosark.config;

/**
 * Created by sxb on 2017/2/19.
 */

public class RetCodeConfig {

    public static final String UID = "SessionUid";

    public static long DEFAUTL_LONG = -1;
    public static int DEFAUTL_INT = -1;
    public static byte DEFAUTL_BYTE = -1;
    public static boolean DEFAUTL_BOOLEAN = false;
    public static String DEFAUTL_EMPTY_STRING = "";

    public static int NOT_AUTH = 401;
    public static int VERSION_TOO_OLD = 505;// 代表这个app版本太旧了（重新打开app时的validate接口）
    public static int NOT_ALLOW = 402;
    public static int NOT_EXIST = 404;// 资源不存在（如Feed不存在）
    public static int SUCCESS = 1;
    public static int FAILED = 0;
    public static int USER_NOT_EXIST = -1;// /app/validate、/user/get接口
    public static int USER_BANED = -8;// 所有接口都会返回，特别是/app/loginReport、/app/validate接口，用户被封禁（返回的msg字段会有封禁的结果，如“您因连续违规被封号24小时”，弹出提示用户）

    public static int THEMEDAY_TOPIC_EXIST = -1;
    public static int COLUMN_TOPIC_EXIST = -2;
    public static int USERNAME_EXISTED = -2;// /user/addOrUpdate和/user/checkUsername接口
    public static int USER_SHOULD_LOGOUT = -3; // /app/validate接口
    public static int SENSITIVE_WORDS = -4;// /user/addOrUpdate接口，用户输入包含敏感词
    public static int USERNAME_NOT_LEGAL = -5;// /user/addOrUpdate接口，用户输入的ID包含非法字符
    public static int LS_STOP = -6;// /liveShow/*接口，直播已结束
    public static int LS_USER_NO_IN = -7;// /liveShow/link接口，用户不在直播间
    public static int EXISTS = -9; // 资源已存在。 /admin/popup/save
    public static int VERIFY_FAILED = -10; // /pay/getAccount 绑定提现账户验证token失败
    public static int PAY_ACCOUNT_NOT_BINDING = -11; // /pay/withdraw 未绑定提现账户
    public static int NOT_WITHDRAW_TIME = -12; // /pay/withdraw 提现日期限制
    public static int WITHDRAW_COUNT_LIMIT = -13; // /pay/withdraw 提现次数限制
    public static int WITHDRAW_LESS = -14; // /pay/withdraw 账户余额不足10元
    public static int BALANCE_NOT_ENOUGH = -15; // /pay/withdraw 账户余额小于提现金额
    public static int USER_SHOULD_KICK = -16; // guestEnter、guestShake接口，需要踢人
    public static int INVALID_ID_CARD = -17; // /pay/saveAccount 身份证号码不合法
    public static int INVALID_PAY_ACCOUNT = -18; // /pay/saveAccount 提现账户不合法
    public static int NOT_ALLOW_HANDS_UP = -19; // /liveShow/handsUp 没有举手资格
    public static int PASSWORD_HAS_CHANGED = -20;// 用户在其他地方已经更改了密码
    public static int LIVE_NOW = -21;// 用户正在直播中
    public static int NOT_ALLOW_LINK = -22; // 没有连麦资格
    public static int MUTE_NOT_ANCHOR = -23; //非主播去禁言
    public static int MIBI_BALANCE_NOT_ENOUGH = -25; // M币余额不足
    public static int DEVICE_BANED = -26;// /app/checkDevice接口返回，设备被封禁（返回的msg字段会有封禁的结果，如“您因连续违规被封设备24小时”，弹出提示用户）
    public static int LS_START_BANED = -27;// /liveShow/add, /liveShow/start接口返回，用户开播权限被封禁
    public static int LS_START_NOT_IN_24_HOUR_SCHEDULE = -28;// /liveShow/start接口返回，用户勾选24小时官方开播，但没在计划时间内
    public static int LS_START_IN_24_HOUR_SCHEDULE = -29;// /liveShow/start接口返回，用户没勾选24小时官方开播，但在计划时间内
    public static int LS_START_24_HOUR_CAN_NOT_START_LIVESHOW_IN_APP = -30;// 24小时官方号不能在app开播
    public static int LS_START_24_HOUR_ANCHOR_IN_GF_SCHEDULE_BUT_GF_NOT_START = -31;// 24小时官方号没有开启直播间，但主播在他的排班中
    public static int LS_MSG_ERROR_CODE = -32;// /liveShow/add ,/liveShow/start,/liveShow/guestEnter的错误码。客户端显示服务器返回的错误消息
    public static int ANCHOR_NOT_AUTH = -33;// 用户开播未实名认证
    public static int ANCHOR_AUTH_REVIEWING = -34;// 用户开播实名认证审核中
    public static int ANCHOR_AUTH_FAIL = -35;// 用户开播实名认证未通过
    public static int ANCHOR_AUTH_PHONE_BIND_EXCEED_LIMIT = -36;// 用户开播实名认证手机绑定账号已达上限
    public static int ANCHOR_AUTH_CAPTCHA_SEND_EXCEED_LIMIT = -37;// 用户开播实名认证发送验证码次数已达上限
    public static int ANCHOR_AUTH_IDCARD_BIND_EXCEED_LIMIT = -38;// 用户开播实名认证身份证绑定次数已达上限
    public static int ANCHOR_AUTH_CAPTCHA_VERIFY_FAIL = -39;// 用户开播实名认证验证码验证失败
    public static int ANCHOR_AUTH_ALREADY_BIND_PHONE = -40;// 用户开播实名认证已绑定手机
    public static int ANCHOR_AUTH_ALREADY_SUBMIT = -41;// 用户开播实名认证已提交
    public static int NOT_YOUR_OWNED_LS = -42;// 不属于主播的直播间
    public static int IN_SHAKE_LINK_STATE = -43;// 路人处于摇一摇状态
    public static int SHAKE_PAIR_NOT_EXIST_STATE = -44;// 没有可用的匹配
    public static int HANDS_UP_NOT_ZERO = -45;// 摇一摇请求下，举手人数不为0

    public static int EXCHANGE_EXCEEED = -23; // 兑换超过上限
    public static int GIFT_COMBO_UNSUPPORTED = -24; // 礼物不支持连送

    public static int IS_NOT_WEEKEND=-46;//抢班接口 时间不是周六周日
    public static int PARAM_ERROR=-47;//抢班接口 参数错误
    public static int PARAM_DAY_STR_FORMAT_ERROR=-48;//抢班日期格式错误
    public static int PARAM_BUCID_ERROR=-49;//抢班接口 buckid错误
    public static int GRABING_EXCEPTION=-50;//抢班接口 抢班异常
    public static int PARAM_NOW_BIGGER_DAYTIME=-51; //改班接口，日期天数小于今天
    public static int LS_FULL=-52; //直播间人数已满
    public static int IS_NOT_ARRANGED=-53; //不是排班主播
    public static int GRABING_HAS_FAILE_PEOPLE_FULL=-54;//抢班失败，人数满了
}

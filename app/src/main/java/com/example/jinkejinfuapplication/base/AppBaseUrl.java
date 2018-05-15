package com.example.jinkejinfuapplication.base;

/**
 * Created by Administrator on 2017/9/27.
 */

public class AppBaseUrl
{
    public static final String BASEURL="http://www.goldcs.net/"; //我的ID 476   http://vip.goldcs.net/
    /*public static final String BASEURL="http://192.168.1.17:8080/Dada/";*/
    public final static String OSSURL="http://shunxe.oss-cn-shenzhen.aliyuncs.com/"; //oss网址
    public static final String ISEXIST_ACCOUNT=BASEURL+"app/finduser"; //判断用户是否存在
    public static final String SENDVERIFYCODE=BASEURL+"mobilephone/sendVerifyCodeSms"; //注册验证码
    public static final String SENDVERIFYCODE_XIUGAI=BASEURL+"mobilephone/sendVerifyCodeSms/password"; //修改信息验证码
    public static final String REGISTER=BASEURL+"app/jinke/register";  //注册接口
    public static final String LOGIN=BASEURL+"app/jinke/login";  //登录
    public static final String MYINFOR=BASEURL+"app/user/update";  //修改个人信息
    public static final String PW_UPDATE=BASEURL+"app/jinke/find_password";  //修改密码
    public static final String SHOUYE=BASEURL+"app/index/new/list";  //首页
    public static final String HAIWAI=BASEURL+"app/game/overseas/ajax";  //海外
    public static final String ZIXUN_XIANGQ=BASEURL+"app/VR/news";  //资讯详情
    public static final String WENZHANG =BASEURL+"static/html/";  //文章前缀
    public static final String MOREZIXUN_ZIXUN =BASEURL+"app/VR/news/more";  //资讯界面的更多资讯
    public static final String YOUXI_XIANGQ =BASEURL+"app/game/new/details";  //各种游戏详情
    public static final String YOUXI_ZIXUN =BASEURL+"app/game/new/details/news";  //游戏相关资讯
    public static final String YOUXI_XIANGSI=BASEURL+"app/game/new/details/game";  //游戏界面相关游戏
    public static final String SHIPING_MORE=BASEURL+"app/game/new/details/video";  //游戏界面相关视频
    public static final String VR=BASEURL+"app/VR/game/new/ajax";  //VR
    public static final String DUJIA_SHOUYOU=BASEURL+"app/game/sole/shouyou/ajax";  //独家手游
    public static final String DUJIA_YEYOU=BASEURL+"app/game/sole/yeyou/ajax";  //独家页游
    public static final String DUJIA_ZHEKOU=BASEURL+"app/game/sole/discount/ajax";  //独家折扣
    public static final String DUJIA_MIANFEI=BASEURL+"app/game/sole/discount/ajax";  //独家免费
    public static final String DUJIA_JINGXUAN=BASEURL+"app/game/chosen";  //独家精选
    public static final String SHIPING_YOUXI=BASEURL+"app/game/video/ajax";  //视频游戏
    public static final String SHIPING_VR=BASEURL+"app/VR/new/video/ajax";  //视频VR
    public static final String SHIPING_VR_TUIJIAN=BASEURL+"app/game/VR/top";  //视频VR推荐
    public static final String SHIPING_YOUXI_TUIJIAN=BASEURL+"app/game/video/top";  //视频游戏推荐
    public static final String SHIPING_XIANGQ=BASEURL+"app/VR/game/video/details";  //视频详情
    public static final String SHIPING_XIANGQ_MORE=BASEURL+"app/VR/game/video/more";  //视频详情更多视频
    public static final String SHIPING_YINGSHI=BASEURL+"app/movie/ajax";  //视频影视
    public static final String YINGSHI_XIANGQ=BASEURL+"app/weixin/movie/details";  //影视详情
    public static final String SHARE_GAME=BASEURL+"game/new/details_share/";  //分享游戏
    public static final String SHARE_SHIPING=BASEURL+"video/";  //分享视频
    public static final String TUIJIAN_GEREN=BASEURL+"app/taojinke/personal/center/game";  //个人主页推荐
    public static final String GEREN=BASEURL+"app/taojinke/personal/center";  //个人主页
    public static final String ZHAOMU_HUIYUAN=BASEURL+"app/taojinke/personal/member/Qrcode";  //招募会员
    public static final String ZHAOMU_HEHUO=BASEURL+"app/taojinke/personal/proxy/Qrcode";  //招募合伙人
    public static final String TAOJINKE_HUIYUAN=BASEURL+"app/taojinke/proxy/member";  //淘金客会员界面
    public static final String TAOJINKE_HEHUOREN=BASEURL+"app/taojinke/proxy";  //淘金客合伙人界面
    public static final String TAOJINKE_HEHUOREN_TUIJIAN=BASEURL+"app/taojinke/proxy/list";  //淘金客合伙人推荐会员
    public static final String TAOJINKE_FENXIANG=BASEURL+"app/taojinke/personal/game/share/ajax";  //淘金客推荐分享
    public static final String SENDVERIFYCODE_HEHUOREN=BASEURL+"app/proxy/mobilephone/sendVerifyCodeSms";  //注册合伙人验证码
    public static final String ZHUCE_HEHUOREN=BASEURL+"app/taojinke/proxy/save";  //注册合伙人
    public static final String PANDUAN_HEHUOREN=BASEURL+"app/taojinke/proxy/state";  //判断是否注册合伙人
    public static final String FUFEI_HEHUOREN=BASEURL+"app/taojinke/proxy/Recharge";  //注册付费信息
    public static final String FENSI_JIANGLI=BASEURL+"app/taojinke/personal/fans";  //粉丝奖励界面
    public static final String FENSI_LEIJI=BASEURL+"app/taojinke/personal/fans/details";  //粉丝累计界面
    public static final String MYVIP_HUIYUAN=BASEURL+"app/taojinke/proxy/member/Recharge/html";  //我的VIP会员
    public static final String MYZHANGHU=BASEURL+"app/taojinke/personal/account";  //个人账户
    public static final String MYMINGXI=BASEURL+"app/taojinke/personal/mingxi";  //个人明细
    public static final String MINGXI_HUIYUAN=BASEURL+"app/taojinke/personal/member/mingxi";  //会员明细
    public static final String MINGXI_HEHUOREN=BASEURL+"app/taojinke/personal/proxy/mingxi";  //合伙人明细
    public static final String WEIXIN_ZHIFU_HUIYUAN=BASEURL+"app/taojinke/weixin/member/pay";  //会员微信支付
    public static final String WEIXIN_ZHIFU_HEHUOREN=BASEURL+"app/taojinke/weixin/proxy/pay";  //合伙人微信支付
    public static final String ZHIFUBAO_ZHIFU_HUIYUAN=BASEURL+"app/taojinke/Alipay/member/pay";  //会员支付宝支付
    public static final String ZHIFUBAO_ZHIFU_HEHUOREN=BASEURL+"app/taojinke/Alipay/proxy/pay";  //合伙人支付宝支付
    public static final String SENDVERIFYCODE_FINDPW=BASEURL+"mobilephone/sendVerifyCodeSms/password";  //忘记密码验证码
    public static final String FINDPW=BASEURL+"app/jinke/find_password";  //忘记密码修改密码
    public static final String MYHEHUOREN=BASEURL+"app/taojinke/personal/proxy/fans";  //我的合伙人
    public static final String SEARCH=BASEURL+"app/taojinke/game/search";  //搜索
    public static final String UPLOAD=BASEURL+"app/android/upload";  //更新app
    public static final String QIANDAO=BASEURL+"app/taojinke/sign";  //签到
    public static final String CHOUJIANG=BASEURL+"app/integral/details";  //抽奖
    public static final String SHOUYE_ZIXUN=BASEURL+"app/index/new/news";  //热点资讯
    public static final String SHOUYE_SHIPING=BASEURL+"app/index/new/video";  //热点视频
    public static final String JIFEN_JIANGLI=BASEURL+"app/taojinke/member/integral/list";  //积分奖励
    public static final String BANNER_SHOUYE=BASEURL+"app/banner/ajax";  //首页banner
    public static final String MORE_ZIXUN=BASEURL+"app/index/news/more";  //首页更多资讯
    public static final String BANNER_YOUXI=BASEURL+"app/game/video/top";  //视频游戏banner
    public static final String BANNER_VR=BASEURL+"app/game/VR/top";  //视频VRbanner
    public static final String MYTIXIAN=BASEURL+"app/taojinke/personal/weixin/withdraw/apply";  //提现界面
    public static final String TIXIAN_WEIXIN=BASEURL+"app/taojinke/personal/weixin/withdraw/apply/save";  //微信提现
    public static final String TIXIAN_ZHIFUBAO=BASEURL+"app/taojinke/personal/apply/withdraw/apply/save";  //支付宝提现
    public static final String TIXIAN_JILU=BASEURL+"app/taojinke/personal/Alipay/withdraw/list";  //提现记录
    public static final String PINGLUN_LIST=BASEURL+"app/comment/list";  //获取评论
    public static final String PINGLUN=BASEURL+"app/comment";  //评论
    public static final String DIANZAN=BASEURL+"app/details/like";  //点赞
    public static final String YINGSHI_TUIJIAN=BASEURL+"app/weixin/movie/more";  //影视推荐
    public static final String TOUXIANG_XIUGAI=BASEURL+"app/personal/user/photo/update";  //修改头像
    public static final String UPDATE_XINXI=BASEURL+"app/user/data";  //更新信息
    public static final String XIAZAI_TONGJI=BASEURL+"app/game/download/ajax";  //下载统计
    public static final String UPDATE_GAME=BASEURL+"app/game/update/list";  //游戏更新
    public static final String ZIXUN_TYPE=BASEURL+"app/news/sort";  //资讯分类
    public static final String SHIPING_TYPE=BASEURL+"app/video/index/type";  //视频分类
    public static final String ZHOUBIAN_SHOUYE=BASEURL+"app/game/commodity";  //首页周边
    public static final String HUATI_ADD=BASEURL+"app/topic/add";  //添加话题
    public static final String HUATI=BASEURL+"app/topic/list";  //话题 分类1为话题、2问答
    public static final String LIUYIN=BASEURL+"app/message/list"; //留言
    public static final String LIUYIN_ADD=BASEURL+"app/message/add"; //添加留言
    public static final String SHOUYE_HUDONG=BASEURL+"app/topic/index/ajax"; //首页互动 sort=message为留言；=topic为话题；=topic1为问答
    public static final String HUDONG_XIANGQ=BASEURL+"app/topic/details"; //话题、问答详情接口
    public static final String LIUYIN_XIANGQ=BASEURL+"app/message/details"; //留言详情
    public static final String HUDONG_PINGLUN=BASEURL+"app/comment"; //评论通用接口
    public static final String HUDONG_HUOQUPINGLUN=BASEURL+"app/comment/list"; //获取评论通用接口 sort=topic为话题 =topic1为问答 =message为留言
    public static final String HUDONG_MYHUIFU=BASEURL+"app/my/reply/topic";  //话题、问答中 我的回复接口
    public static final String HUDONG_MYHUATI=BASEURL+"app/my/topic";  //我的话题、问答接口通用接口
    public static final String HUDONG_MYHUIFU_LIUYIN=BASEURL+"app/my/reply/message";  //我的留言中 我的回复接口
    public static final String HUDONG_MYLIUYIN=BASEURL+"app/my/message";  //我的留言接口

    public static String isHttp(String url){

        if (url.length()>4&&url.substring(0,4).equals("http")){
            return url;
        }else
        {
            if (url.length()>4){
                return BASEURL+url;
            }else
            {
                return url;
            }
        }
    }
}

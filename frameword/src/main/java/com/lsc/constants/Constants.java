package com.lsc.constants;

/**
 * @Classname SystemConstants
 * @Description
 * @Date 2022/12/10 21:09
 * @Created by linmour
 */
public class Constants {
    //文章是草稿
    public static final int ARTICLE_STATUS_DRAFT = 1;
    //文章是发布状态
    public static final int ARTICLE_STATUS_NORMAL = 0;

    //这是分类的状态
    public static final String CATEGORY_STATUS_NORMAL = "0";
    public static final String CATEGORY_STATUS_NOTNORMAL = "1";

    //link审核通过
    public static final String LINK_STATUS_NORMAL = "0";

    //用户redis的key
    public static final String USER_REDIS_KEY = "userlogin:";

    //管理员redis的key
    public static final String ADMIN_REDIS_KEY = "adminlogin:";

    //根评论
    public static final String ROOT_COMMENT = "-1";

    //友链评论
    public static final String LINK_COMMENT = "1";

    //文章评论
    public static final String ARTICLE_COMMENT = "0";

    //redis文章浏览数key

    public static final String ARTICLE_VIEWCOUNT_KEY = "Article:ViewCount";


}

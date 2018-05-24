package com.weqar.weqar.Global_url_weqar;

/**
 * Created by andriod on 20/2/18.
 */

public class Global_URL {
    public static  String Base_url ="http://dev.weqar.co/webapi/";
    public static String Image_URL="http://dev.weqar.co/webAPI/api/";

//    public static  String Base_url ="https://weqar.co/webapi/";
//    public static String Image_URL="https://weqar.co/webAPI/api/";
//

    public static  String User_signup= Base_url + "api/user/register";
    public static  String User_signin= Base_url + "api/user/mobilelogin";
    public static String User_insertbasicinfo=Base_url+"api/user/add";
    public static String User_insertprofessinalinfo=Base_url+"api/user/userprofessional";
    public static String User_uploadprofessionalimage=Base_url+"api/file/upload";
    public static String User_subscriptiondet_get=Base_url+"api/userplan/get";
    public static String user_insert_completedetails=Base_url+"api/usersubscription";
    public static String user_show_discount=Base_url+"api/Discounts/get";
    public static String user_discount_follow=Base_url+"api/Discounts/";
   // public static String user_show_jobs=Base_url+"api/job/viewall";

    public static String user_show_dashboard=Base_url+"api/Dashboard/view";
    public  static  String user_show_news=Base_url+"api/News/get";
    public  static  String user_dashboard_discount_det=Base_url+"api/discounts/getbyid";
    public  static  String user_dashboard_event_det=Base_url+"api/Event/view";
    public  static  String user_dashboard_news_det=Base_url+"api/News/viewNews";
    public  static  String user_event_edit=Base_url+"api/Event/update";
    public  static  String user_getnews_type=Base_url+"api/system/newstypes";
    public  static  String user_addnews=Base_url+"api/News/add";
    public  static  String user_delete_news=Base_url+"api/News/delete";
    public  static  String user_edit_news=Base_url+"api/News/update";



    public static String Vendor_insertprofessionalinfo=Base_url+"api/vendor/professional";
    public static String Vendor_complete_chooseplan=Base_url+"api/vendor/discountplan";
    public static String vendor_insert_completedetails=Base_url+"api/Discounts/add";
    public static String Vendor_showown_discounts=Base_url+"api/discounts/get";


    public  static  String Vendor_select_categ=Base_url+"api/vendor/category";
    public  static  String Vendor_insert_second_Discount=Base_url+"api/discounts/add";
    public  static  String Vendor_getjobtype=Base_url+"api/system/jobtypes";
    public  static  String Vendor_getjobfield=Base_url+"api/system/jobfields";
    public  static  String Vendor_addjobs=Base_url+"Api/job/add";
    public  static  String Vendor_showownjobs=Base_url+"api/job/get";
    public  static  String Vendor_delete_discounts=Base_url+"api/discounts/delete";
    public  static  String Vendor_discount_detail=Base_url+"api/discounts/vendordiscountall";
    public  static  String Vendor_discount_update=Base_url+"api/discounts/update";
    public  static  String Vendor_job_delete=Base_url+"api/job/delete";
    public  static  String Vendor_job_edit=Base_url+"api/job/update";
    public  static  String Vendor_add_event=Base_url+"api/Event/add";
    public  static  String Vendor_show_allevents=Base_url+"api/event/get";
    public  static  String Vendor_delete_events=Base_url+"api/Event/delete";

    public static String getDetails=Base_url+"api/user/get";


        public static String Image_url_load=Image_URL+"file/view?filename=";

        public static String user_event_register=Base_url+"api/Event/register";

        public static String user_job_apply=Base_url+"api/Job/jobapply";

}

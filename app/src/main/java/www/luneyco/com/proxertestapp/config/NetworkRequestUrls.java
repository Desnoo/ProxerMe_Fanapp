package www.luneyco.com.proxertestapp.config;

/**
 * Class where all request Urls are managed.
 * Dynamic parameter are declared like this: {{?page}}
 * Created by TS on 24.08.2015.
 */
public final class NetworkRequestUrls {
    public static final String NotificationRequestUrl = "https://proxer.me/notifications?format=raw&s=count";
    public static final String LoginRequestUrl = "https://proxer.me/login?format=json&action=login";

    public final class NewsRequest {
        public static final String NewsRequestPageNum = "{{?page}}";
        public static final String NewsRequestUrl     = "http://proxer.me/notifications?format=json&s=news&p=" + NewsRequestPageNum;

        // fields used for json parsing!
        public static final String Error         = "error";
        public static final String Notifications = "notifications";


        public final class ImageRequest {
            public static final String NewsIdHolder      = "{{nid}}";
            public static final String NewsImageIdHolder = "{{image_id}}";
            public static final String ImageRequestUrl   = "http://cdn.proxer.me/news/" + NewsIdHolder + "_" + NewsImageIdHolder + ".png";
        }

        public final class NewsDetailRequest {
            public static final String NewsCategoryIdHolder      = "{{catid}}";
            public static final String NewsThreadIdHolder = "{{thread}}";
            public static final String NewsUrl   = "http://proxer.me/forum/" + NewsCategoryIdHolder + "/" + NewsThreadIdHolder;
        }
    }


}

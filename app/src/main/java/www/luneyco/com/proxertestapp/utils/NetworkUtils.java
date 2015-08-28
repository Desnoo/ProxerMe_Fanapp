package www.luneyco.com.proxertestapp.utils;

import www.luneyco.com.proxertestapp.config.NetworkRequestUrls;

/**
 * Utils to abstract some network related stuff.
 * Created by TS on 26.08.2015.
 */
public class NetworkUtils {

    /**
     * Get the corresponding image url for the newsimage.
     * @param _NewsId the news id.
     * @param _ImageId the image id.
     * @return the url of the image.
     */
    public static String getNewsImageUrl(long _NewsId, String _ImageId){
        String newsUrl = NetworkRequestUrls.NewsRequest.ImageRequest.ImageRequestUrl;
        newsUrl = newsUrl.replace(
                NetworkRequestUrls.NewsRequest.ImageRequest.NewsIdHolder,
                String.valueOf(_NewsId)
        );
        newsUrl = newsUrl.replace(
                NetworkRequestUrls.NewsRequest.ImageRequest.NewsImageIdHolder,
                String.valueOf(_ImageId)
        );
        return newsUrl;
    }

    /**
     * Get the news url for detailed news.
     * @param _CategoryId the category id for news.
     * @param _ThreadId the thread id for news.
     * @return the url to the forum topic.
     */
    public static String getNewsUrl(int _CategoryId, long _ThreadId){
        String newsUrl = NetworkRequestUrls.NewsRequest.NewsDetailRequest.NewsUrl;
        newsUrl = newsUrl.replace(
                NetworkRequestUrls.NewsRequest.NewsDetailRequest.NewsCategoryIdHolder,
                String.valueOf(_CategoryId)
        );
        newsUrl = newsUrl.replace(
                NetworkRequestUrls.NewsRequest.NewsDetailRequest.NewsThreadIdHolder,
                String.valueOf(_ThreadId)
        );
        return newsUrl;
    }
}

package www.luneyco.com.proxertestapp.model;

/**
 * News model to hold the news data.
 * Created by tinos_000 on 24.07.2015.
 */
public class News {

    private int mId;

    // Unix Timestamp
    private long mCreationTimeStamp;

    private String mTitle;

    private String mDescription;

    private String mImageId;

    private long mThreadId;

    private int mHits;

    private int mNumberOfPosts;

    private Category mCategory;

    private User mCreator;


    //region Getter and Setter

    public void setmId(int mId) {
        this.mId = mId;
    }

    public int getmId() {
        return mId;
    }

    public long getmCreationTimeStamp() {
        return mCreationTimeStamp;
    }

    public void setmCreationTimeStamp(long mCreationTimeStamp) {
        this.mCreationTimeStamp = mCreationTimeStamp;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmImageId() {
        return mImageId;
    }

    public void setmImageId(String mImageId) {
        this.mImageId = mImageId;
    }

    public long getmThreadId() {
        return mThreadId;
    }

    public void setmThreadId(long mThreadId) {
        this.mThreadId = mThreadId;
    }

    public int getmHits() {
        return mHits;
    }

    public void setmHits(int mHits) {
        this.mHits = mHits;
    }

    public int getmNumberOfPosts() {
        return mNumberOfPosts;
    }

    public void setmNumberOfPosts(int mNumberOfPosts) {
        this.mNumberOfPosts = mNumberOfPosts;
    }

    public Category getmCategory() {
        return mCategory;
    }

    public void setmCategory(Category mCategory) {
        this.mCategory = mCategory;
    }

    public User getmCreator() {
        return mCreator;
    }

    public void setmCreator(User mCreator) {
        this.mCreator = mCreator;
    }
    //endregion

    //region equals and hashCode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof News)) return false;

        News news = (News) o;

        return getmId() == news.getmId();

    }

    @Override
    public int hashCode() {
        return getmId();
    }

    @Override
    public String toString() {
        return "News{" +
                "mId=" + mId +
                ", mCreationTimeStamp=" + mCreationTimeStamp +
                ", mTitle='" + mTitle + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", mImageId=" + mImageId +
                ", mThreadId=" + mThreadId +
                ", mHits=" + mHits +
                ", mNumberOfPosts=" + mNumberOfPosts +
                ", mCategory=" + mCategory +
                ", mCreator=" + mCreator +
                '}';
    }

    //endregion
}

package www.luneyco.com.proxertestapp.model;

/**
 * The Category model to hold category data.
 * Created by tinos_000 on 24.07.2015.
 */
public class Category {

    private int mId;

    private String mCategoryName;

    //region Getter and Setter
    public String getmCategoryName() {
        return mCategoryName;
    }

    public void setmCategoryName(String mCategoryName) {
        this.mCategoryName = mCategoryName;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public int getmId() {
        return mId;
    }
    //endregion

    //region equals and hashCode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;

        Category category = (Category) o;

        return getmId() == category.getmId();

    }

    @Override
    public int hashCode() {
        return getmId();
    }
    //endregion
}

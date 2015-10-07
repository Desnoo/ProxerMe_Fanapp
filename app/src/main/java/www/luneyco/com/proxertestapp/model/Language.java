package www.luneyco.com.proxertestapp.model;

/**
 * Created by tinos_000 on 07.10.2015.
 */
public class Language {
    private String mLanguageTag;

    public Language(){

    }

    public Language(String _LanguageTag){
        mLanguageTag = _LanguageTag;
    }

    public String getmLanguageTag() {
        return mLanguageTag;
    }

    public void setmLanguageTag(String mLanguageTag) {
        this.mLanguageTag = mLanguageTag;
    }
}

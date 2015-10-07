package www.luneyco.com.proxertestapp.model.anime;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import www.luneyco.com.proxertestapp.model.Language;

/**
 * The episodes of an Anime.
 * * Created by tinos_000 on 07.10.2015.
 */
public class Episode implements JsonSerializer<Episode>, JsonDeserializer<Episode> {

    public static final String EPISODE_NR = "no";
    public static final String EPISODE_TYPE = "typ";
    public static final String GER_SUB = "gersub";
    public static final String ENG_SUB = "engsub";
    private int mEpisodeNumber;

    private Language mLanguage;

    private List<StreamingType> mTypes;

    public int getmEpisodeNumber() {
        return mEpisodeNumber;
    }

    public void setmEpisodeNumber(int mEpisodeNumber) {
        this.mEpisodeNumber = mEpisodeNumber;
    }

    public Language getmLanguage() {
        return mLanguage;
    }

    public void setmLanguage(Language mLanguage) {
        this.mLanguage = mLanguage;
    }

    public List<StreamingType> getmTypes() {
        return mTypes;
    }

    public void setmTypes(List<StreamingType> mTypes) {
        this.mTypes = mTypes;
    }

    @Override
    public Episode deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        mTypes = new ArrayList<>();
        JsonObject jsonObject = json.getAsJsonObject();
        mEpisodeNumber = jsonObject.getAsJsonPrimitive(EPISODE_NR).getAsInt();
        mLanguage = new Language(jsonObject.getAsJsonPrimitive(EPISODE_TYPE).getAsString());
        String[] streamingTypes = jsonObject.getAsJsonPrimitive("types").getAsString().split(",");
        String[] streamingTypeImages = jsonObject.getAsJsonPrimitive("typeimg").getAsString().split(",");

        for (int index = 0; index < streamingTypes.length; index++) {
            mTypes.add(new StreamingType(streamingTypes[index], streamingTypeImages[index]));
        }

        return this;
    }

    @Override
    public JsonElement serialize(Episode src, Type typeOfSrc, JsonSerializationContext context) {
        return null;
    }
}

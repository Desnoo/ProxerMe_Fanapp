package www.luneyco.com.proxertestapp.model.anime;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import www.luneyco.com.proxertestapp.model.Language;

/**
 * Created by tinos_000 on 07.10.2015.
 */
public class Anime implements JsonSerializer<Anime>, JsonDeserializer<Anime> {

    private int mEpisodeCount;

    private String mCategory;

    private List<Episode> mEpisodes;

    public int getmEpisodeCount() {
        return mEpisodeCount;
    }

    public void setmEpisodeCount(int mEpisodeCount) {
        this.mEpisodeCount = mEpisodeCount;
    }

    public String getmCategory() {
        return mCategory;
    }

    public void setmCategory(String mCategory) {
        this.mCategory = mCategory;
    }

    public List<Episode> getmEpisodes() {
        return mEpisodes;
    }

    public void setmEpisodes(List<Episode> mEpisodes) {
        this.mEpisodes = mEpisodes;
    }

    @Override
    public Anime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        mEpisodes = new ArrayList<>();
        JsonObject jsonObject = json.getAsJsonObject();
        int end = jsonObject.getAsJsonPrimitive("end").getAsInt();
        mCategory = jsonObject.getAsJsonPrimitive("kat").getAsString();
        mEpisodeCount = end;
        JsonArray episodeArray = jsonObject.getAsJsonArray("data");
        for(int start = 0;start < episodeArray.size(); start ++){
            mEpisodes.add(new Episode().deserialize(episodeArray.get(start), Episode.class, context));
        }
        return this;
    }

    @Override
    public JsonElement serialize(Anime src, Type typeOfSrc, JsonSerializationContext context) {
        return null;
    }
}

package www.luneyco.com.proxertestapp.model.serializer;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import www.luneyco.com.proxertestapp.model.Category;
import www.luneyco.com.proxertestapp.model.News;
import www.luneyco.com.proxertestapp.model.User;

/**
 * Serializer for news.
 * Created by TS on 25.08.2015.
 */
public class NewsSerializer implements JsonSerializer<News>, JsonDeserializer<News> {

    @Override
    public News deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonObject()) {
            Gson gson = RealmGsonHelper.getGsonParser();
            JsonObject jsonObject = json.getAsJsonObject();
            News news = new News();
            /*
            news.setmId(jsonObject.get("id").getAsInt());
            news.setmCreationTimeStamp(jsonObject.get("creationTime").getAsLong());
            news.setmDescription(jsonObject.get("description").getAsString());
            news.setmHits(jsonObject.get("hits").getAsInt());
            news.setmImageId(jsonObject.get("imageId").getAsInt());
            news.setmNumberOfPosts(jsonObject.get("numberOfPosts").getAsInt());
            news.setmThreadId(jsonObject.get("threadId").getAsLong());
            news.setmTitle(jsonObject.get("title").getAsString());
            */
            news.setmId(jsonObject.get("nid").getAsInt());
            news.setmCreationTimeStamp(jsonObject.get("time").getAsLong());
            news.setmDescription(jsonObject.get("description").getAsString());
            news.setmHits(jsonObject.get("hits").getAsInt());
            news.setmImageId(jsonObject.get("image_id").getAsString());
            news.setmNumberOfPosts(jsonObject.get("posts").getAsInt());
            news.setmThreadId(jsonObject.get("thread").getAsLong());
            news.setmTitle(jsonObject.get("subject").getAsString());

            news.setmCategory(gson.fromJson(jsonObject, Category.class));
            news.setmCreator(gson.fromJson(jsonObject, User.class));
            return news;
        }
        return null;
    }

    @Override
    public JsonElement serialize(News src, Type typeOfSrc, JsonSerializationContext context) {
        Gson       gson       = RealmGsonHelper.getGsonParser();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", src.getmId());
        jsonObject.addProperty("creationTime", src.getmCreationTimeStamp());
        jsonObject.addProperty("description", src.getmDescription());
        jsonObject.addProperty("hits", src.getmHits());
        jsonObject.addProperty("imageId", src.getmImageId());
        jsonObject.addProperty("numberOfPosts", src.getmNumberOfPosts());
        jsonObject.addProperty("threadId", src.getmThreadId());
        jsonObject.addProperty("title", src.getmTitle());
        jsonObject.addProperty("category", gson.toJson(src.getmCategory()));
        jsonObject.addProperty("creator", gson.toJson(src.getmCreator()));
        return null;
    }
}

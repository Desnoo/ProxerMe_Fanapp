package www.luneyco.com.proxertestapp.model.serializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import www.luneyco.com.proxertestapp.model.Category;

/**
 * Serializer for category.
 * Created by TS on 25.08.2015.
 */
public class CategorySerializer implements JsonSerializer<Category>, JsonDeserializer<Category> {

    @Override
    public JsonElement serialize(Category src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        /*
        jsonObject.addProperty("id", src.getmId());
        jsonObject.addProperty("name", src.getmCategoryName());
        */
        jsonObject.addProperty("catid", src.getmId());
        jsonObject.addProperty("catname", src.getmCategoryName());
        return jsonObject;
    }

    @Override
    public Category deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        if (json.isJsonObject()) {
            Category category = new Category();
            JsonObject jsonObject = json.getAsJsonObject();
            /*
            category.setmId(jsonObject.get("id").getAsInt());
            category.setmCategoryName(jsonObject.get("name").getAsString());
            */
            category.setmId(jsonObject.get("catid").getAsInt());
            category.setmCategoryName(jsonObject.get("catname").getAsString());
            return category;

        }
        return null;
    }
}

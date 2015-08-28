package www.luneyco.com.proxertestapp.model.serializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import www.luneyco.com.proxertestapp.model.User;

/**
 * Serializer for the user model.
 * Created by TS on 25.08.2015.
 */
public class UserSerializer implements JsonSerializer<User>, JsonDeserializer<User> {

    @Override
    public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        if(json.isJsonObject()){
            JsonObject jsonObject = json.getAsJsonObject();
            User user = new User();
            //user.setmId(jsonObject.get("id").getAsInt());
            //user.setmCreatorName(jsonObject.get("name").getAsString());
            user.setmId(jsonObject.get("uid").getAsInt());
            user.setmCreatorName(jsonObject.get("uname").getAsString());
            return user;
        }

        return null;
    }

    @Override
    public JsonElement serialize(User src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", src.getmId());
        jsonObject.addProperty("name", src.getmCreatorName());
        return jsonObject;
    }
}

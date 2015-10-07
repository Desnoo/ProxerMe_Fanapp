package www.luneyco.com.proxertestapp.model.anime;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * The type of streaming site.
 * Created by tinos_000 on 07.10.2015.
 */
public class StreamingType implements JsonSerializer<StreamingType>, JsonDeserializer<StreamingType> {

    public static final String STREAMING_NAME = "StreamingName";
    public static final String IMAGE_NAME = "imageName";

    private String mStreamingName;
    private String mImageName;

    public StreamingType(){

    }

    public StreamingType(String _StreamingName, String _ImageName){
        mStreamingName = _StreamingName;
        mImageName = _ImageName;
    }

    public String getmStreamingName() {
        return mStreamingName;
    }

    public void setmStreamingName(String mStreamingName) {
        this.mStreamingName = mStreamingName;
    }

    public String getmImageName() {
        return mImageName;
    }

    public void setmImageName(String mImageName) {
        this.mImageName = mImageName;
    }

    @Override
    public StreamingType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        mStreamingName = json.getAsJsonObject().getAsJsonPrimitive(STREAMING_NAME).getAsString();
        mImageName = json.getAsJsonObject().getAsJsonPrimitive(IMAGE_NAME).getAsString();
        return this;
    }

    @Override
    public JsonElement serialize(StreamingType src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(STREAMING_NAME, src.getmStreamingName());
        jsonObject.addProperty(IMAGE_NAME, src.getmImageName());
        return jsonObject;
    }
}

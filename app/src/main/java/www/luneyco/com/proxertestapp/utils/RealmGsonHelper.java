package www.luneyco.com.proxertestapp.utils;

import android.util.Log;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.realm.RealmObject;
import www.luneyco.com.proxertestapp.model.Category;
import www.luneyco.com.proxertestapp.model.Language;
import www.luneyco.com.proxertestapp.model.News;
import www.luneyco.com.proxertestapp.model.User;
import www.luneyco.com.proxertestapp.model.anime.Anime;
import www.luneyco.com.proxertestapp.model.anime.Episode;
import www.luneyco.com.proxertestapp.model.anime.StreamingType;
import www.luneyco.com.proxertestapp.model.serializer.CategorySerializer;
import www.luneyco.com.proxertestapp.model.serializer.NewsSerializer;
import www.luneyco.com.proxertestapp.model.serializer.UserSerializer;

/**
 * The gson helper to prevent any failures that can occure between gson and realm.io.
 * Important! Register new Realm Models to typeAdapter
 * Created by TS on 25.08.2015.
 */
public class RealmGsonHelper {

    public static final String LOG_TAG = RealmGsonHelper.class.toString();

    /**
     * Retrieve the Gson parser for Realm objects!
     *
     * @return a Gson parser that can parse Realm objects.
     */
    public static Gson getGsonParser() {
        GsonBuilder gsonBuilder = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getDeclaringClass().equals(RealmObject.class);
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                });

        // Register Type Adapters for serializing RealmObjects when accessed directly.
        // This is used, for instance, when serializing a RealmQuery.
        gsonBuilder.registerTypeAdapter(Category.class, new CategorySerializer())
                .registerTypeAdapter(News.class, new NewsSerializer())
                .registerTypeAdapter(User.class, new UserSerializer())
                .registerTypeAdapter(Anime.class, new Anime())
                .registerTypeAdapter(Episode.class, new Episode());

        // Register Type Adapters for serializing RealmObjects when accessed indirectly.
        // Realm proxy objects are registered for when you want to serialize an unmanaged RealmList,
        // and ArrayList of RealmObjects or a newly instantiated RealmObject, for example.
        try {
            gsonBuilder.registerTypeAdapter(Class.forName("io.realm.CategoryRealmProxy"), new CategorySerializer())
                    .registerTypeAdapter(Class.forName("io.realm.NewsRealmProxy"), new NewsSerializer())
                    .registerTypeAdapter(Class.forName("io.realm.UserRealmProxy"), new UserSerializer());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "The proxy class of a model was not found!");
        }

        return gsonBuilder.create();
    }
}

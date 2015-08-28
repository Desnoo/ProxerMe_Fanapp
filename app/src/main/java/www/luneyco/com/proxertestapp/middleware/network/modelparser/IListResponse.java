package www.luneyco.com.proxertestapp.middleware.network.modelparser;

import java.util.List;

import www.luneyco.com.proxertestapp.model.Notification;

/**
 * A response of a list of objects.
 * Created by TS on 26.08.2015.
 */
public interface IListResponse<T> {

    /**
     * Called when the response and parsing was successfully done.
     * @param _Ret  the new element of the given type.
     */
    void onResponse(List<T> _Ret);

    /**
     * Called when the response was not successfully done.
     */
    void onErrorResponse();
}

package www.luneyco.com.proxertestapp.middleware.network.modelparser;

import java.util.List;

/**
 * The simple respons.
 * Created by TS on 01.09.2015.
 */
public interface IResponse<T> {

        /**
         * Called when the response and parsing was successfully done.
         * @param _Ret  the new element of the given type.
         */
        void onResponse(T _Ret);

        /**
         * Called when the response was not successfully done.
         */
        void onErrorResponse();

}

package www.luneyco.com.proxertestapp.utils.provider;

import com.squareup.otto.Bus;

/**
 * Provides the square otto bus.
 * Created by tinos_000 on 28.08.2015.
 */
public class BusProvider {

    private static Bus BUS = new Bus();

    public static Bus getInstance(){
        return BUS;
    }

    private BusProvider(){

    }
}

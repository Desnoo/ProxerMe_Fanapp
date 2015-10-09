package www.luneyco.com.proxertestapp.view.adapter;

/**
 * Touchlistener associated with a {@link android.support.v7.widget.RecyclerView} to propagate the item to the parent class.
 * Created by tinos_000 on 09.10.2015.
 */
public interface IOnTouchListener<T> {
    void onItemClicked(T _Item);
}

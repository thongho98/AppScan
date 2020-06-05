package own.thongho.appscan.ui.favorite;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

import own.thongho.appscan.R;
import own.thongho.appscan.adapter.FavoriteAdapter;
import own.thongho.appscan.models.History;
import own.thongho.appscan.servicesDB.HistoryDB;
import own.thongho.appscan.ui.history.HistoryFragmentInfo;

public class FavoriteFragment extends Fragment {

    private HistoryDB historyDB;
    private FavoriteAdapter favoriteAdapter;
    private ArrayList<History> listFavorite;
    ListView listView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_favorite, container, false);
        listView = root.findViewById(R.id.lsv_showLike);
        listFavorite = new ArrayList<History>();
        historyDB = new HistoryDB(getActivity());
        favoriteAdapter = new FavoriteAdapter(getActivity(), R.layout.custom_listview_favorite, listFavorite);
        listView.setAdapter(favoriteAdapter);
        getAllData();
        setEvent();
        return root;
    }

    private void setEvent() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                History history = new History();
                history.setId(listFavorite.get(position).getId());
                history.setCategory(listFavorite.get(position).getCategory());
                history.setImg(listFavorite.get(position).getImg());
                history.setDate(listFavorite.get(position).getDate());
                history.setContent(listFavorite.get(position).getContent());
                history.setLike(listFavorite.get(position).getLike());
                FavoriteFragmentInfo fragment = new FavoriteFragmentInfo();
                Bundle bundle = new Bundle();
                bundle.putSerializable("favorite", (Serializable) history);
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.containerFavorite, fragment);
                transaction.commit();
            }
        });
    }

    public void getAllData() {
        Cursor cursor = historyDB.getAllDataFavorite();
        if (cursor != null) {
            listFavorite.clear();
            while (cursor.moveToNext()) {
                History history = new History();
                history.setId(cursor.getInt(0));
                history.setCategory(cursor.getString(1));
                history.setContent(cursor.getString(2));
                history.setDate(cursor.getString(3));
                history.setImg(cursor.getInt(4));
                history.setLike(cursor.getInt(5));
                listFavorite.add(history);
            }
            favoriteAdapter.notifyDataSetChanged();
        }
    }
}

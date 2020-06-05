package own.thongho.appscan.ui.history;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import own.thongho.appscan.R;
import own.thongho.appscan.adapter.HistoryAdapter;
import own.thongho.appscan.models.History;
import own.thongho.appscan.servicesDB.HistoryDB;

public class HistoryFragment extends Fragment {

    private HistoryDB historyDB;
    private HistoryAdapter historyAdapter;
    private ArrayList<History> listHistory;
    ListView listView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_history, container, false);
        listView = root.findViewById(R.id.lsv_show);
        listHistory = new ArrayList<History>();
        historyDB = new HistoryDB(getActivity());
        historyAdapter = new HistoryAdapter(getActivity(), R.layout.custom_listview, listHistory);
        listView.setAdapter(historyAdapter);
        getAllData();
        setEvent();
        //refreshEvents(listHistory);
        return root;
    }

    private void setEvent() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HistoryFragmentInfo fragment = new HistoryFragmentInfo();
                History history = new History();
                history.setId(listHistory.get(position).getId());
                history.setCategory(listHistory.get(position).getCategory());
                history.setImg(listHistory.get(position).getImg());
                history.setDate(listHistory.get(position).getDate());
                history.setContent(listHistory.get(position).getContent());
                history.setLike(listHistory.get(position).getLike());

                Bundle bundle = new Bundle();
                bundle.putSerializable("history", (Serializable) history);
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.container, fragment);
                transaction.commit();
            }
        });
    }

    public void getAllData() {
        Cursor cursor = historyDB.getAllData();
        if (cursor != null) {
            listHistory.clear();
            while (cursor.moveToNext()) {
                History history = new History();
                history.setId(cursor.getInt(0));
                history.setCategory(cursor.getString(1));
                history.setContent(cursor.getString(2));
                history.setDate(cursor.getString(3));
                history.setImg(cursor.getInt(4));
                history.setLike(cursor.getInt(5));
                listHistory.add(history);
            }
            historyAdapter.notifyDataSetChanged();
        }
    }

    public void refreshEvents(List<History> events) {
        events.clear();
        events.addAll(events);
        historyAdapter.notifyDataSetChanged();
    }
}

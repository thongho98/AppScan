package own.thongho.appscan.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import java.util.ArrayList;
import own.thongho.appscan.R;
import own.thongho.appscan.models.History;
import own.thongho.appscan.servicesDB.HistoryDB;

public class HistoryAdapter extends ArrayAdapter<History> {
    private Context context;
    private ArrayList<History> listHistory = null;

    public HistoryAdapter(@NonNull Context context, int resource, ArrayList<History> arrHistory) {
        super(context, resource);
        this.context = context;
        this.listHistory = arrHistory;
    }

    static class HistoryHolder {
        ImageView images, imgLike, imgDelete;
        TextView myTitle, myContent, date;
    }

    @Override
    public int getCount() {
        return this.listHistory.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        HistoryHolder holder = null;
        if (holder != null) {
            holder = (HistoryHolder) row.getTag();
        } else {
            holder = new HistoryHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.custom_listview, parent, false);
            holder.images = row.findViewById(R.id.image);
            holder.myTitle = row.findViewById(R.id.category);
            holder.myContent = row.findViewById(R.id.content);
            holder.date = row.findViewById(R.id.date);
            holder.imgLike = row.findViewById(R.id.imgLike);
            holder.imgDelete = row.findViewById(R.id.imgDelete);
            row.setTag(holder);
        }

        final History history = listHistory.get(position);
        holder.images.setImageResource(history.getImg());
        holder.myTitle.setText(history.getCategory());
        holder.myContent.setText(history.getContent());
        holder.myContent.setMaxLines(2);
        holder.myContent.setEllipsize(TextUtils.TruncateAt.END);
        holder.date.setText(history.getDate());
        if (history.getLike() == 0) {
            holder.imgLike.setImageResource(R.drawable.icon_heart_1);
        } else {
            holder.imgLike.setImageResource(R.drawable.icon_heart_2);
        }
        HistoryHolder finalHolder = holder;
        holder.imgLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HistoryDB historyDB = new HistoryDB(context);
                if (history.getLike() == 0) {
                    finalHolder.imgLike.setImageResource(R.drawable.icon_heart_2);
                    historyDB.updateHistory(history);
                    Toast.makeText(context, "Like thành công!", Toast.LENGTH_SHORT).show();
                } else {
                    finalHolder.imgLike.setImageResource(R.drawable.icon_heart_1);
                    historyDB.updateHistoryUnlike(history);
                    Toast.makeText(context, "Unlike thành công!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Bạn có muốn xóa lịch sử này?");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                HistoryDB historyDB = new HistoryDB(context);
                                historyDB.deleteHistory(history.getId());
                                Toast.makeText(context, "Xóa lịch sử này thành công!", Toast.LENGTH_SHORT).show();
                                listHistory.remove(position);
                                notifyDataSetChanged();
                            }
                        });
                builder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        return row;
    }
}

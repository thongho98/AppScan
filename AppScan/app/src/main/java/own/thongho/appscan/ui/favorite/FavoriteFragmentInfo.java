package own.thongho.appscan.ui.favorite;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import own.thongho.appscan.R;
import own.thongho.appscan.models.History;

public class FavoriteFragmentInfo extends Fragment {
    ImageView imageView;
    TextView txtCategory, txtDate;
    EditText editContent;
    Button btnHandle, btnThoat;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_info, container, false);
        setControl();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            History history = (History) bundle.getSerializable("favorite");
            setInfo(history);
        }
        setEvent();
        return view;
    }

    public void setInfo(History history) {
        imageView.setImageResource(history.getImg());
        txtCategory.setText(history.getCategory());
        txtDate.setText(history.getDate());
        editContent.setText(history.getContent());
        if(txtCategory.getText().toString().equalsIgnoreCase("URL")){
            btnHandle.setText("Mở bằng trình duyệt");
        }
        else{
            btnHandle.setText("Copy text");
        }
    }

    private void setControl() {
        imageView = view.findViewById(R.id.imageView);
        txtCategory = view.findViewById(R.id.txtCategory);
        txtDate = view.findViewById(R.id.txtDate);
        editContent = view.findViewById(R.id.editContent);
        btnHandle = view.findViewById(R.id.btnHandle);
        btnThoat = view.findViewById(R.id.btnThoat);
    }

    private void setEvent() {
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        btnHandle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = editContent.getText().toString();
                Intent i = new Intent(Intent.ACTION_VIEW);
                if (URLUtil.isValidUrl(url)) {
                    i.setData(Uri.parse(url));
                    startActivity(i);
                } else {
                    ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("text", url);
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(getActivity(), "Copied", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

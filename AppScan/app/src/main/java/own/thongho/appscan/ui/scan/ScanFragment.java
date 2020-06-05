package own.thongho.appscan.ui.scan;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import own.thongho.appscan.R;
import own.thongho.appscan.models.History;
import own.thongho.appscan.servicesDB.HistoryDB;

public class ScanFragment extends Fragment {
    private CodeScanner mCodeScanner;
    private HistoryDB historyDB;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        historyDB = new HistoryDB(getActivity());
        final Activity activity = getActivity();
        View root = inflater.inflate(R.layout.fragment_scan, container, false);
        CodeScannerView scannerView = root.findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(activity, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull com.google.zxing.Result result) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Nội dung của QR code nè");
                        builder.setMessage("Bạn có muốn copy nội dung này không?\n\n" + result.getText());
                        builder.setCancelable(true);
                        builder.setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        saveQRcode(result);
                                        copyClipboard(result);
                                    }
                                });
                        builder.setNegativeButton("No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        saveQRcode(result);
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();

                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.setFlashEnabled(false);
                mCodeScanner.startPreview();
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mCodeScanner.setFlashEnabled(false);
        mCodeScanner.startPreview();
    }

    @Override
    public void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

    public String convertEpochTime(Long updatedTime) {
        Date date = new Date(updatedTime);
        DateFormat format = new SimpleDateFormat("HH:mm:ss, dd-MM-yyyy");
        format.setTimeZone(TimeZone.getTimeZone("GMT+7"));
        String formattedTime = format.format(date);
        return formattedTime;
    }

    private void saveQRcode(Result result) {
        History history = new History();
        String url = result.getText().toString();
        if (URLUtil.isValidUrl(url)) {
            history.setCategory("URL");
            history.setImg(R.drawable.img_url);
        } else {
            history.setCategory("TEXT");
            history.setImg(R.drawable.img_text);
        }
        history.setContent(result.getText());
        String dateNow = convertEpochTime(System.currentTimeMillis());
        history.setDate("" + dateNow);
        history.setLike(0);
        historyDB.addHistory(history);
    }

    private void copyClipboard(Result result) {
        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("text", result.getText());
        clipboard.setPrimaryClip(clip);
        Toast.makeText(getActivity(), "Copied", Toast.LENGTH_SHORT).show();
    }

    /*
    Phân loại URL qua getResult

    * */
}

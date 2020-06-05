package own.thongho.appscan.ui.setting;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import own.thongho.appscan.MainActivity;
import own.thongho.appscan.R;
import static android.content.Context.MODE_PRIVATE;

public class SettingFragment extends Fragment {
    View root;
    Button btnSave;
    RadioGroup rdiGroupLanguage, rdiGroupFont;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_setting, container, false);
        setControl();
        setEvent();
        return root;
    }

    private void onChangeFont(Integer fontRes){
        getActivity().getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .edit()
                .putInt("font", fontRes)
                .commit();

    }

    private void onChangeLanguage(Context context, Locale locale){
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

        Configuration configuration = new Configuration();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            configuration.setLocale(locale);
        }else{
            configuration.locale = locale;
        }

        getResources().updateConfiguration(configuration, displayMetrics);
        context.getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .edit()
                .putString("language", locale.getLanguage())
                .commit();
        context.getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .edit()
                .putString("country", locale.getCountry())
                .commit();
    }

    private void setControl() {
        btnSave = root.findViewById(R.id.btnSave);
        rdiGroupLanguage = root.findViewById(R.id.rdiGroupLanguage);
        rdiGroupFont = root.findViewById(R.id.rdiGroupFont);
    }
    private void setEvent() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = rdiGroupLanguage.getCheckedRadioButtonId();
                RadioButton rdiLanguage = getView().findViewById(id);
                if(rdiLanguage.getText().toString().equalsIgnoreCase("English")){
                    onChangeLanguage(getActivity(), new Locale("en", "US"));
                }
                else{
                    onChangeLanguage(getActivity(), new Locale("vi", "VN"));
                }

                Intent refresh = new Intent(getActivity(), MainActivity.class);
                startActivity(refresh);
            }
        });
    }
}

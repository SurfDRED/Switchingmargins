package com.example.switchingmargins;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Locale;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {
    private Toolbar myToolbar;
    private Spinner mLanguageSpinner;
    private Spinner mColorSpinner;
    private Button mLanguageBtn;
    private SharedPreferences mySettings;
    private static final String MY_SETTINGS = "my_settings";
    private static final String MY_SETTINGS_LANG = "my_lang";
    private static final String MY_SETTINGS_SLANG = "my_sLang";
    private static final String MY_SETTINGS_COLOR = "my_color";
    private String mLang;
    private int sLang;
    private int mColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mySettings = getSharedPreferences(MY_SETTINGS, MODE_PRIVATE);
        //язык
        mLang = mySettings.getString(MY_SETTINGS_LANG, "");
        //index языка для спинера
        sLang = mySettings.getInt(MY_SETTINGS_SLANG, 0);
        //тема и index для спинера
        mColor = mySettings.getInt(MY_SETTINGS_COLOR, 0);
        setConfig(mLang);
        Selection.setsTheme(mColor);
        Selection.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_main);
        myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        initViews();
    }

    private void initViews() {
        mLanguageSpinner = findViewById(R.id.languageSpinner);
        mColorSpinner = findViewById(R.id.colorSpinner);
        mLanguageBtn = findViewById(R.id.btnLang);
        spinnerLanguage();
        spinnerColor();
        mLanguageBtn.setOnClickListener(this);
    }

    private void setConfig(String mLanguage) {
        Locale locale = new Locale(mLanguage);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }

    private void spinnerLanguage() {
        ArrayAdapter<CharSequence> adapterLanguage = ArrayAdapter.createFromResource(this, R.array.language, android.R.layout.simple_spinner_item);
        adapterLanguage.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mLanguageSpinner.setAdapter(adapterLanguage);
        mLanguageSpinner.setSelection(sLang);
        mLanguageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sLang = mLanguageSpinner.getSelectedItemPosition();
                languageSelection(sLang);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void languageSelection(int languages) {
        switch (languages) {
            case 0:
                mLang = "ru";
                break;
            case 1:
                mLang = "en";
                break;
            case 2:
                mLang = "de";
                break;
        }
    }
    private void spinnerColor() {
        ArrayAdapter<CharSequence> adapterColor = ArrayAdapter.createFromResource(this, R.array.color, android.R.layout.simple_spinner_item);
        adapterColor.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mColorSpinner.setAdapter(adapterColor);
        mColorSpinner.setSelection(mColor);
        mColorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int a, long s) {
                mColor = mColorSpinner.getSelectedItemPosition();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        setConfig(mLang);
        SharedPreferences.Editor myEditor = mySettings.edit();
        myEditor.putString(MY_SETTINGS_LANG, mLang);
        myEditor.putInt(MY_SETTINGS_SLANG, sLang);
        myEditor.putInt(MY_SETTINGS_COLOR, mColor);
        myEditor.apply();
        switch (mColor) {
            case 0:
                Selection.changeToTheme(this, Selection.THEME_BLACK);
                break;
            case 1:
                Selection.changeToTheme(this, Selection.THEME_GREEN);
                break;
            case 2:
                Selection.changeToTheme(this, Selection.THEME_BLUE);
                break;
        }
    }
}
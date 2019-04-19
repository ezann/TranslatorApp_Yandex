/*
 * This Project (MyTranslatorApp) Created by Ezra Septian
 * Copyright(c) 2019. All rights reserved.
 * Last modified 19/04/19 18:03
 */

package com.ezrasept.mytranslatorapp.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.ezrasept.mytranslatorapp.Helpers.checkNetwork;
import com.ezrasept.mytranslatorapp.Helpers.httpConnect;
import com.ezrasept.mytranslatorapp.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView translate_result;
    EditText editText;
    SharedPreferences sPref;
    Button from_language, to_language, klikTranslate;

    ArrayList<HashMap<String,String>> list;
    private String from_lang="en";
    private String to_lang="id";
    private ProgressDialog pDialog;
    private static final String translate_url = "https://translate.yandex.net/api/v1.5/tr.json/translate?" +
            "key=trnsl.1.1.20171129T093344Z.12b562dff18c6b46.d3fa3adb732d67093a02159f276d6a3bc9611402" +
            "&lang=" + "{FROM}" + "-" + "{TO}" +
            "&text=" + "{text}";


    public void setTo_lang(String to_lang) {
        this.to_lang = to_lang;
    }

    public void setFrom_lang(String from_lang) {

        this.from_lang = from_lang;
    }

    public String getFrom_lang() {
        return from_lang;
    }

    public String getTo_lang() {

        return to_lang;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView powered = findViewById(R.id.textView6);
        powered.setClickable(true);
        powered.setMovementMethod(LinkMovementMethod.getInstance());
        String text = "<a href='http://translate.yandex.com/'> Powered by Yandex.Translate </a>";
        powered.setText(Html.fromHtml(text));

        translate_result = findViewById(R.id.translate_result);
        klikTranslate = findViewById(R.id.klikTranslate);
        klikTranslate.setOnClickListener(this);
        editText = findViewById(R.id.fill_text);
        from_language = findViewById(R.id.from_language);
        from_language.setOnClickListener(this);
        to_language = findViewById(R.id.to_language);
        to_language.setOnClickListener(this);
        list = new ArrayList<>();


    }

    private String replace_chars(String tranlater) {
        String char_replacer = tranlater;
        char_replacer = char_replacer.replace(']', ' ').replace('[', ' ').replace('"', ' ');
        return char_replacer;
    }

    private String text_from_edit_text() {

        String text;
            text = editText.getText().toString();
        return text;

    }


    private String HttpResult() {
        httpConnect httpConnect = new httpConnect();
        String reqURL = Translate_text(text_from_edit_text());
        return httpConnect.makeHttpCall(reqURL);

    }

    private String Translate_text(String translate_text) {
        sPref = getPreferences(MODE_PRIVATE);
        String from = getFrom_lang();
        String too = getTo_lang();

        String text = null;
        try {
            text = translate_url
                    .replace("{text}", URLEncoder.encode(translate_text, "UTF-8") )
                    .replace("{FROM}",from)
                    .replace("{TO}", too);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return text;
    }


    @SuppressLint("StaticFieldLeak")
    private class GetTask extends AsyncTask<Void, Void, String> {
        //ArrayList<String> texts = new ArrayList<>();
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Tunggu Sebentar");
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(Void... params) {
            return HttpResult();
        }

        protected void onPostExecute(String str) {
            super.onPostExecute(str);
            if (pDialog.isShowing())
                pDialog.dismiss();

            String text;
            if (str != null) {
                JSONObject jsonObj;
                try {
                    jsonObj = new JSONObject(str);
                    JSONArray jsonArray = jsonObj.getJSONArray("text");
                    String translate = jsonArray.toString();
                    text = replace_chars(translate);

                    HashMap<String, String> contact = new HashMap<>();
                    contact.put("translate_text", text);
                    contact.put("from_text", (editText.getText().toString()));
                    contact.put("from-to", getFrom_lang() + "-" + getTo_lang());
                    list.add(contact);

                    translate_result.setText(text.replace("\\n", "\n").replace("\\","\""));
                    translate_result.setMovementMethod(new ScrollingMovementMethod());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.to_language:
                PopupMenu popup = new PopupMenu(MainActivity.this, to_language);
                popup.inflate(R.menu.popup_menu);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch(item.getItemId()) {
                            case R.id.english1:
                                to_language.setText(item.getTitle());
                                setTo_lang("en");

                                return true;
                            case R.id.indonesian1:
                                to_language.setText(item.getTitle());
                                setTo_lang("id");

                                return true;
                        }
                        return true;

                    }
                });
                popup.show();
                break;

            case R.id.from_language:
                PopupMenu  popup2 = new PopupMenu(MainActivity.this, from_language);
                popup2.inflate(R.menu.popup_menu2);
                popup2.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch(item.getItemId()) {
                            case R.id.english2:
                                from_language.setText(item.getTitle());
                                setFrom_lang("en");
                                return true;
                            case R.id.indonesian2:
                                from_language.setText(item.getTitle());
                                setFrom_lang("id");

                                return true;
                        }
                        return true;

                    }
                });
                popup2.show();
                break;
            case R.id.klikTranslate:
                if (editText.getText().toString().trim().length() <= 0){
                    Toast.makeText(MainActivity.this, "Masih Kosong", Toast.LENGTH_SHORT).show();
                    return;
                    }
                    if (editText.getText().toString().trim().length() > 0 && checkNetwork.isInternetAvailable(MainActivity.this)) {
                new GetTask().execute();
                    }
                        else
                        {
                            Toast.makeText(MainActivity.this,"Tidak Ada Sambungan Internet",Toast.LENGTH_SHORT).show();
                        }
        }

    }

}

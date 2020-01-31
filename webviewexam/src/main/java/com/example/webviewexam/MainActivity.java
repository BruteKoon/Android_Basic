package com.example.webviewexam;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText mAddressEdit;
    private WebView myWebView;
    private Button mMoveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myWebView = findViewById(R.id.web_view);

        WebSettings WebSettings = myWebView.getSettings();
        WebSettings.setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new WebViewClient());
        mAddressEdit = findViewById(R.id.address_list);
        mMoveButton = findViewById(R.id.move_button);

        mAddressEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    mMoveButton.callOnClick();

                    //키보드 내리기
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(),0);

                    return true;
                }
                return false;
            }
        });
    }

    public void onClick(View view) {
        String address = mAddressEdit.getText().toString();
        if (address.startsWith("http://") == false){
            address = "http://" + address;
        }
        myWebView.loadUrl(address);
    }

    @Override
    public void onBackPressed() {
        if (myWebView.canGoBack()){
            myWebView.goBack();
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_back:
                if(myWebView.canGoBack()){
                    myWebView.goBack();
                }
                break;
            case R.id.action_forward:
                if(myWebView.canGoForward()){
                    myWebView.goForward();
                }
                break;
            case R.id.action_refresh:
                myWebView.reload();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

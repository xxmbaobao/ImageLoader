package cn.fcar.app.config;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fcar.fcardemo.FcarActivity;
import com.fcar.fcardemo.R;

public class ConfigWindow extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        System.out.println("enter ConfigWindow.onCreate()!");

        super.onCreate(savedInstanceState);

        setContentView(R.layout.config_window);

        mServerIP = (EditText)findViewById(R.id.serverip);
        mServerPort = (EditText)findViewById(R.id.serverport);
        //hemerr
        mServerIP.setText(serverIp);
        mServerPort.setText(serverPort);

        okButton = (Button) findViewById(R.id.ok_button);
        backButton = (Button) findViewById(R.id.back_button);

        loginBtnListener = new View.OnClickListener() {
            public void onClick(View view) {
                if (view == okButton) {
                        serverIp = mServerIP.getText().toString();
                        serverPort = mServerPort.getText().toString();
                        Log.i("info", "IP is: "+serverIp+"/tPort is: "+serverPort);

                        finish();

                } else if(view == backButton) {
                        finish();
                }
            }
        };

        okButton.setOnClickListener(loginBtnListener);
        backButton.setOnClickListener(loginBtnListener);
    }

    private void launchFetion() {
        Intent i = new Intent(this, FcarActivity.class);
        i.putExtra(KEY_LOGIN_NAME, mServerIP.getText().toString());
        i.putExtra(KEY_LOGIN_PASSWD, mServerPort.getText().toString());

        startActivity(i);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent msg) {
//        System.out.println("enter onKeyDown() in LoginWindow!");
//
//        if (null != loginBtnListener) {
//            View aview = getCurrentFocus();
//            loginBtnListener.onClick(aview);
//        }
        return false;
    }

    public static String getServerIp() {
                return serverIp;
        }

        public static String getServerPort() {
                return serverPort;
        }

    private Button okButton, backButton;
    private EditText mServerIP;
    private EditText mServerPort;

    private View.OnClickListener loginBtnListener;

    public static final String KEY_LOGIN_NAME = "login_name";
    public static final String KEY_LOGIN_PASSWD = "login_passwd";
    public static final String KEY_LOGIN_TYPE = "login_type";

    public static String serverIp = "192.168.3.12";  //;
    public static String serverPort = "8081";


}

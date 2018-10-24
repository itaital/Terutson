package itaital100.gmail.com.terutson.Activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.List;

import itaital100.gmail.com.terutson.R;
import itaital100.gmail.com.terutson.Tools.Utils;

import itaital100.gmail.com.terutson.R;

public class Report_Activity extends AppCompatActivity {


    //email:
    Button btn_sendEmail;
    EditText myEditBox;
    //check if we have 20 char in line
    boolean isReached = false;
    AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Utils.disableLandscapeMode(this);
        setContentView(R.layout.activity_report_);
        mAdView = (AdView) findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        Utils.setGraphicsRegionTo("eng",this);
        System.out.println("debug: .xml loaded");
        //init all components:
        init_editBox();
        init_SendButton();


        //check_New_Line();
    }
    @Override public void onBackPressed()
    {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
    //----------------------------------------------------------------------------------------------
    void init_editBox()
    {
        myEditBox = (EditText) findViewById(R.id.txt_edit);
    }
    //----------------------------------------------------------------------------------------------
    void init_SendButton()
    {
        btn_sendEmail = (Button) findViewById(R.id.btn_email);
        btn_sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail();
            }
        });
    }
    //----------------------------------------------------------------------------------------------
    protected void sendEmail() {
        if(myEditBox.length() ==0)
        {
            Utils.openConfirmDialog("נא להכניס בעיה בבקשה","אישור",this);
            return;
        }
        else
        {
            String SmyEditBox = myEditBox.getText().toString();
            String[] email_AddressToSend = {"terutson@gmail.com"};
            String email_subject = "Hey Terutson support, Check out my Problem ! " ;
            String email_content = "הבעיה:" + "\n"  + SmyEditBox;

            shareToGMail(email_AddressToSend,
                    email_subject,
                    email_content
            );
        }

    }
    //----------------------------------------------------------------------------------------------
    public void shareToGMail(String[] email, String subject, String content) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, email);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, content);
        final PackageManager pm = this.getPackageManager();
        final List<ResolveInfo> matches = pm.queryIntentActivities(emailIntent, 0);
        ResolveInfo best = null;
        for(final ResolveInfo info : matches)
            if (info.activityInfo.packageName.endsWith(".gm") || info.activityInfo.name.toLowerCase().contains("gmail"))
                best = info;
        if (best != null)
            emailIntent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
        this.startActivity(emailIntent);
    }
    //----------------------------------------------------------------------------------------------
    protected  void check_New_Line(){
        myEditBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // if edittext has 20chars & this is not called yet, add new line
                if(myEditBox.getText().length() == 20 && !isReached){
                    myEditBox.append("\n");
                    isReached = true;
                }
                // if edittext has less than 20chars & boolean has changed, reset
                if(myEditBox.getText().length() < 20 && isReached) isReached = false;

            }
        });
    }
}

package itaital100.gmail.com.terutson;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener
{
    //                          Categories                          //
    Button categoryButton_meeting;        Button categoryButton_work;
    Button categoryButton_occassion;      Button categoryButton_hw;
    Button categoryButton_ex;             Button categoryButton_date;

    ArrayList<Button> allCategoriesButtons = new ArrayList<Button>();    //An array to contain all categories:

    //                          Menu:                               //
    private DrawerLayout   myMenu_drawer;
    private NavigationView myMenu_navigation;
    private Button         myMenu_btn;




//Start of the program:
//--------------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize:
                init_Ads();
                init_GraphicOriention();
                init_Categories();
                init_Menu();
                ExusesFactory.selectedGender = Utils.getSelectedGender(this);
        if(openingForTheFirstTime())
        {
            openFirstTimeDiaolog();
        }
    }
//--------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------
// -------------------------------------------------------------------------------------------------

    /**
     *
     * @return true if the app hadnt been opend before
     *  or false if the app had been opened atleast once before
     */
    private boolean openingForTheFirstTime()
    {
        String isFirstTime = Utils.getVariable("Opened",this);
        if(isFirstTime.equals("notfound"))
        {
           // Utils.commitVariable("Opened","yes",this);
            return true;
        }
        else return false;
    }
    //----------------------------------------------------------------------------------------------
    void openFirstTimeDiaolog()
    {
        Intent startIntent = new Intent(getApplicationContext(), startup_1.class);
        //start activity://
        startActivity(startIntent);
        /*
        String msg ="שלום לכם! " + "\n" +"אנו מודים לכם שבחרתם להשתמש באפליקציה שלנו ומקווים שהיא תועיל לכם מאד" +
                "\n"+"עם זאת, אין אנו אחראים על כל נזק שעלול להיגרם כתוצאה משימוש באפליקציה שלנו, והאחריות לשימוש בתירוצים היא על המשתמש בלבד";
        String confirmMsg = "אני מסכים";
        Utils.openConfirmDialog(msg,confirmMsg,this);
        //openGenderSelectDialog();
        */
    }
    //----------------------------------------------------------------------------------------------
    private void init_Menu()
    {
        myMenu_drawer = findViewById(R.id.drawer_layout);
        myMenu_btn =  (Button)findViewById(R.id.btn_menu);
        myMenu_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myMenu_drawer.openDrawer(Gravity.LEFT);
                    }});
        //navigation init:
        myMenu_navigation = (NavigationView)findViewById(R.id.nav_view);
        myMenu_navigation.setNavigationItemSelectedListener(this);
        myMenu_navigation.bringToFront();
    }
    private void init_GraphicOriention()
    {
        Utils.setGraphicsRegionTo("en",this);
    }
    //----------------------------------------------------------------------------------------------
    private void init_Ads()
    {
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
    }
    //----------------------------------------------------------------------------------------------
    /*
       This function inits all categories buttons according to their id
       and attach a clickEvenet Listener to each one of them in a uniform way.
     */
    private void init_Categories()
    {
        setCategories();
        addAllButtonsToArray(allCategoriesButtons);

        //Set an action listener for each category button
        for(final Button categoryButton : allCategoriesButtons)
        {
            categoryButton.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent startIntent = new Intent(getApplicationContext(), DetailsActivity.class);

                            //get category name and build a key messege to send the next activity://
                            String fullResourceName = getResources().getResourceName(categoryButton.getId());
                            String categoryName = fullResourceName.substring(fullResourceName.lastIndexOf("_")+1);
                            String prefix = "com.gmail.itaital100.";
                            String finalKey = prefix + categoryName; //--> final key messege

                            //send messege to the next activity://
                            startIntent.putExtra(finalKey,categoryButton.getId());

                            //start activity://
                            startActivity(startIntent);
                        }
                    });

        }
    }
    //----------------------------------------------------------------------------------------------
    private void setCategories()
    {
        categoryButton_hw         =  (Button)findViewById(R.id.btn_hw);
        categoryButton_meeting    =  (Button)findViewById(R.id.btn_meeting);
        categoryButton_ex         =  (Button)findViewById(R.id.btn_ex);
        categoryButton_work       =  (Button)findViewById(R.id.btn_work);
        categoryButton_date       =  (Button)findViewById(R.id.btn_date);;
        categoryButton_occassion  =  (Button)findViewById(R.id.btn_occasion);
    }
    //----------------------------------------------------------------------------------------------
    private void addAllButtonsToArray(ArrayList<Button> arr)
    {
        arr.add(categoryButton_work);
        arr.add(categoryButton_hw);
        arr.add(categoryButton_meeting);
        arr.add(categoryButton_ex);
        arr.add(categoryButton_date);
        arr.add(categoryButton_occassion);
    }
    //----------------------------------------------------------------------------------------------
    //Change back button fucntionality so it will close the menu if its open
    @Override
    public void onBackPressed()
    {
        if (this.myMenu_drawer.isDrawerOpen(GravityCompat.START))
        {
            this.myMenu_drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }
    //----------------------------------------------------------------------------------------------
    /**
     *   This tells the menu how to react when an item is clicked
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.nav_add_excuse:

                myMenu_drawer.closeDrawer(GravityCompat.START);
                Intent i = new Intent(MainActivity.this, Suggestion_Activity.class);
                startActivity(i);
                finish();

                //This crashes the software
                //Intent startIntent = new Intent(getApplicationContext(), Suggestion_Activity.class);
                //startActivity(startIntent);
                break;
            case R.id.change_gender:
                Utils.openGenderSelectDialog(this);
                break;
            case R.id.about:
                Utils.openConfirmDialog("אנחנו אריאל ואיתי בלה בלה בלה","אוקיי",this);
            default:
                return false;
        }
        myMenu_drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    //----------------------------------------------------------------------------------------------
}


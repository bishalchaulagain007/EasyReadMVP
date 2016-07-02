package com.comlu.foodnepal.easyreadmvp;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class UserAreaActivity extends ActionBarActivity implements android.widget.SearchView.OnQueryTextListener,
        android.widget.SearchView.OnCloseListener{

    public static String USER_LOGGED_IN_USER_AREA = "0";

    private Toolbar toolbar;
    private SearchManager searchManager;
    private android.widget.SearchView  searchView;
    private MyExpandableListAdapter listAdapter;
    private ExpandableListView myList;
    private ArrayList<ParentRow> parentList = new ArrayList<ParentRow>();
    private ArrayList<ParentRow> showTheseParentList = new ArrayList<ParentRow>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        //getting intent extras
        USER_LOGGED_IN_USER_AREA= getIntent().getStringExtra(LoginActivity.USER_LOGGED_IN);
        //Toast.makeText(UserAreaActivity.this, "Final Value passed: " + USER_LOGGED_IN_USER_AREA, Toast.LENGTH_LONG).show();

        //for user
        TextView tvWelcomeMsg = (TextView) findViewById(R.id.tvWelcomeMsg);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String username = intent.getStringExtra("username");
        String email = intent.getStringExtra("email");
        int age = intent.getIntExtra("age", -1); //need to give a default value for getting int as extra

        String message = name + ", Welcome to your user area";
        tvWelcomeMsg.setText(message);

        //for user finished

        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        //for the initialisation of view pager and tab host
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        parentList = new ArrayList<ParentRow>();
        showTheseParentList = new ArrayList<ParentRow>();

        searchView = (android.widget.SearchView) findViewById(R.id.search_bar_mine);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
        searchView.requestFocus();


        //app will crash if displayList isn't called here
        displayList();



        //for the icon of the nav drawer...toggle icon
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //create and object of that fragment class

        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

        //to pass the toolbar and other things from our activity to the navigation drawer
        //creating and passing directly inside the parameter

        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        //setup basically complete

    }

    private void displayList() {
        loadData();
        myList = (ExpandableListView) findViewById(R.id.expandableListView_search);
        listAdapter = new MyExpandableListAdapter(UserAreaActivity.this, parentList);
        myList.setAdapter(listAdapter);
    }

    private void expandAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++) {

            myList.expandGroup(i);

        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //for the items in the overflow menu..event handler
        if (id == R.id.navigate) {
            Toast.makeText(this, "Easy Read", Toast.LENGTH_SHORT).show();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(UserAreaActivity.this);
        alertDialogBuilder.setTitle("Log out?");
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                USER_LOGGED_IN_USER_AREA=null;
                                startActivity(new Intent(UserAreaActivity.this,LoginActivity.class));

                            }
                        })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    private void loadData() {

      /*  Toast.makeText(getApplicationContext(), "FACULTY PASSED: " + faculty + " YEAR_PART PASSED: " + year_part,
                Toast.LENGTH_SHORT).show();
*/
        parentList.clear();
        ArrayList<ChildRow> childRows = new ArrayList<ChildRow>();
        ParentRow parentRow = null;

        childRows.add(new ChildRow("Title: Electric Machine (notes)", "Author: Sunny Pathak",
                "Condition: Best", "Market Price: NRs.115", "Buy at: NRs.80"));
        parentRow = new ParentRow("1. Electrical Machine", "NRs.80", childRows);
        parentList.add(parentRow);

        childRows = new ArrayList<ChildRow>();
        childRows.add(new ChildRow("Title: Engineering Mathematics (vol-IV)", "Author: SP Shrestha, HD Chaudhary, PR Pokharel",
                "Condition: Good", "Market Price: NRs.400", "Buy at: NRs.320"));
        parentRow = new ParentRow("2. Engineering Mathematics", "NRs.320", childRows);
        parentList.add(parentRow);

        //other data
        childRows = new ArrayList<ChildRow>();
        childRows.add(new ChildRow("Title: Data Structure through C++", "Author: G.S Baluja",
                "Condition: Bad", "Market Price: NRs.350", "Buy at: NRs.245"));
        parentRow = new ParentRow("3. Data Structure and Algorithm", "NRs.245", childRows);
        parentList.add(parentRow);

        childRows = new ArrayList<ChildRow>();
        childRows.add(new ChildRow("Title: Solutions of C and Fortran(notes)", "Author: Puskal Gautam",
                "Condition: Good", "Market Price: NRs.80", "Buy at: NRs.55"));
        parentRow = new ParentRow("4. C and Fortran", "NRs.55", childRows);
        parentList.add(parentRow);

        childRows = new ArrayList<ChildRow>();
        childRows.add(new ChildRow("Title: Engineering Mathematics (vol-III)", "Author: SP Shrestha, HD Chaudhary, PR Pokharel",
                "Condition: Bad", "Market Price: NRs.400", "Buy at: NRs.280"));
        parentRow = new ParentRow("5. Mathematics", "NRs.280", childRows);
        parentList.add(parentRow);

        //test
        childRows = new ArrayList<ChildRow>();
        childRows.add(new ChildRow("Title: Engineering Mathematics (vol-II)", "Author: Shuvankar Shah",
                "Condition: Best", "Market Price: NRs.380", "Buy at: NRs.265"));
        parentRow = new ParentRow("6. Mathematics", "NRs.265", childRows);
        parentList.add(parentRow);

        childRows = new ArrayList<ChildRow>();
        childRows.add(new ChildRow("Title: Engineering Electromagnetics Solutions ", "Author: By Experienced Teachers",
                "Condition: Good", "Market Price: NRs.300", "Buy at: NRs.210"));
        parentRow = new ParentRow("7. Electromagnetics", "NRs.210", childRows);
        parentList.add(parentRow);

        childRows = new ArrayList<ChildRow>();
        childRows.add(new ChildRow("Title: Electric Circuit Theory", "Author: Manzil Mudbari",
                "Condition: Bad", "Market Price: NRs.270", "Buy at: NRs.215"));
        parentRow = new ParentRow("8. ECT", "NRs.155", childRows);
        parentList.add(parentRow);

        childRows = new ArrayList<ChildRow>();
        childRows.add(new ChildRow("Title: Digital Logic", "Author: Samir Pokharel, KshitizWagle",
                "Condition: Best", "Market Price: NRs.240", "Buy at: NRs.170"));
        parentRow = new ParentRow("9. Digital Logic", "NRs.170", childRows);
        parentList.add(parentRow);

        childRows = new ArrayList<ChildRow>();
        childRows.add(new ChildRow("Title: Basic Electronic Engineering", "Author: Sanjay Kumar Sah",
                "Condition: Best", "Market Price: NRs.175", "Buy at: NRs.95"));
        parentRow = new ParentRow("10. Basic ELectroncs", "NRs.95", childRows);
        parentList.add(parentRow);

        childRows = new ArrayList<ChildRow>();
        childRows.add(new ChildRow("Title:  Fundamental of Thermodynamics and heat transfer", "Author: MK Luitel",
                "Condition: Good", "Market Price: NRs.350", "Buy at: NRs.245"));
        parentRow = new ParentRow("11. Thermodynamics", "NRs.245", childRows);
        parentList.add(parentRow);

        //test
        childRows = new ArrayList<ChildRow>();
        childRows.add(new ChildRow("Title: TOC", "Author: Shuvankar Shah",
                "Condition: Good", "Market Price: NRs.325", "Buy at: NRs.105"));
        parentRow = new ParentRow("12. Theory Of Computation", "NRs.105", childRows);
        parentList.add(parentRow);

        childRows = new ArrayList<ChildRow>();
        childRows.add(new ChildRow("Title: Probability and statistics", "Author:  Tek Bahadur Budhathoki",
                "Condition: Bad", "Market Price: NRs.320", "Buy at: NRs.225"));
        parentRow = new ParentRow("13. Statistics", "NRs.225", childRows);
        parentList.add(parentRow);

        childRows = new ArrayList<ChildRow>();
        childRows.add(new ChildRow("Title: Fluid Mechanics and hydrualics", "Author: PN Modi",
                "Condition: Good", "Market Price: NRs.280", "Buy at: NRs.175"));
        parentRow = new ParentRow("14. Fluid Mechanics", "NRs.175", childRows);
        parentList.add(parentRow);

        //new
        myList = (ExpandableListView) findViewById(R.id.expandableListView_search);
        listAdapter = new MyExpandableListAdapter(UserAreaActivity.this, parentList);
        myList.setAdapter(listAdapter);

        //data finished

    }

    @Override
    public boolean onClose() {
        listAdapter.filterData("");
        expandAll();
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        listAdapter.filterData(query);
        expandAll();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        listAdapter.filterData(newText);
        expandAll();
        return false;
    }


}

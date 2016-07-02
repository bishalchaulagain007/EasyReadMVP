package com.comlu.foodnepal.easyreadmvp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PreUserArea extends AppCompatActivity implements android.widget.SearchView.OnQueryTextListener,
        android.widget.SearchView.OnCloseListener {

    private int FACULTY = 1;
    private int YEAR_PART = 1;

    public static String USER_LOGGED_IN_FINAL1 = null;
    public static String USER_LOGGED_IN_FINAL2 = null;

    Spinner spinner, spinner2;
    TextView tvSellNow;

    String[] faculty = {
            "Civil",
            "Mechanical",
            "Architecture",
            "Computer",
            "Electronics",
            "Electrical"
    };

    String[] year_part = {
            "I | I",
            "I | II",
            "II | I",
            "II | II",
            "III | I",
            "III | II",
            "IV | I",
            "IV | II",
    };

    //spinner finished


    private Toolbar toolbar;

    private SearchManager searchManager;
    private android.widget.SearchView searchView;
    private MyExpandableListAdapter listAdapter;
    private ExpandableListView myList;
    private ArrayList<ParentRow> parentList = new ArrayList<ParentRow>();
    private ArrayList<ParentRow> showTheseParentList = new ArrayList<ParentRow>();
    private MenuItem searchItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_user_area);

        //getting intent extras
        USER_LOGGED_IN_FINAL1 = getIntent().getStringExtra(MainActivity.USER_LOGGED_IN_1);
        USER_LOGGED_IN_FINAL2 = getIntent().getStringExtra(LoginActivity.USER_LOGGED_IN);
        //Toast.makeText(PreUserArea.this, "Final Value passed: " + USER_LOGGED_IN_FINAL, Toast.LENGTH_LONG).show();


        toolbar = (Toolbar) findViewById(R.id.app_bar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Explore");
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //spinner imports
        //referencing the spinner1
        spinner = (Spinner) findViewById(R.id.spinner1);
        //setting the adapter
        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(this, faculty);
        spinner.setAdapter(spinnerAdapter);

        //events
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if (position == 0) FACULTY = 1;
                else if (position == 1) FACULTY = 2;
                else if (position == 2) FACULTY = 3;
                else if (position == 3) FACULTY = 4;
                else if (position == 4) FACULTY = 5;
                else if (position == 5) FACULTY = 6;
               /* Toast.makeText(getApplicationContext(), "FACULTY: " + FACULTY + " Position: " + YEAR_PART,
                        Toast.LENGTH_SHORT).show();

*/
                loadData(FACULTY, YEAR_PART);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //referencing the spinner2
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        //setting the adapter
        SpinnerAdapter spinnerAdapter2 = new SpinnerAdapter(this, year_part);
        spinner2.setAdapter(spinnerAdapter2);

        //events
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) YEAR_PART = 1;
                else if (position == 1) YEAR_PART = 2;
                else if (position == 2) YEAR_PART = 3;
                else if (position == 3) YEAR_PART = 4;
                else if (position == 4) YEAR_PART = 5;
                else if (position == 5) YEAR_PART = 6;
                else if (position == 6) YEAR_PART = 7;
                else if (position == 7) YEAR_PART = 8;

      /*          Toast.makeText(getApplicationContext(), "FACULTY: " + FACULTY + " Position: " + YEAR_PART,
                        Toast.LENGTH_SHORT).show();
*/
                loadData(FACULTY, YEAR_PART);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //spinner imports finished


        //imports..

        searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        parentList = new ArrayList<ParentRow>();
        showTheseParentList = new ArrayList<ParentRow>();

        //app will crash if displayList isn't called here
        displayList();

        //expandAll();
        //finished imports

        //for sell now option
        tvSellNow = (TextView) findViewById(R.id.tv_sell_now);
        tvSellNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ("1".equals(USER_LOGGED_IN_FINAL1) && "1".equals(USER_LOGGED_IN_FINAL2)) {

                    Intent intent = new Intent(PreUserArea.this, DonateActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(PreUserArea.this, "Please Log in to Sell", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PreUserArea.this, LoginActivity.class);
                    startActivity(intent);
                }


            }
        });
    }

    private void displayList() {
        loadData(FACULTY, YEAR_PART);
        myList = (ExpandableListView) findViewById(R.id.expandableListView_search);
        listAdapter = new MyExpandableListAdapter(PreUserArea.this, parentList);
        myList.setAdapter(listAdapter);
    }

    private void expandAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++) {

            myList.expandGroup(i);

        }
    }


    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        searchItem = menu.findItem(R.id.action_search);
        searchView = (android.widget.SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
        searchView.requestFocus();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //for the items in the overflow menu..event handler
        if (id == R.id.action_search) {
            // Toast.makeText(this, "You pressed action_search!", Toast.LENGTH_SHORT).show();

        }

        return super.onOptionsItemSelected(item);
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

    private void loadData(int faculty, int year_part) {

      /*  Toast.makeText(getApplicationContext(), "FACULTY PASSED: " + faculty + " YEAR_PART PASSED: " + year_part,
                Toast.LENGTH_SHORT).show();
*/
        if (faculty == 1 && year_part == 1) {
            parentList.clear();
            ArrayList<ChildRow> childRows = new ArrayList<ChildRow>();
            ParentRow parentRow = null;

            childRows.add(new ChildRow("Title: Basic Mathematics", "Author: SK Gupta",
                    "Condition: Good", "Market Price: NRs.350", "Buy at: NRs.250"));
            parentRow = new ParentRow("1. Mathematics", "NRs.250", childRows);
            parentList.add(parentRow);

            childRows = new ArrayList<ChildRow>();
            childRows.add(new ChildRow("Title: Physics", "Author: NB Chaudary",
                    "Condition: Best", "Market Price: NRs.450", "Buy at: NRs.325"));
            parentRow = new ParentRow("2. Engineering Physics I", "NRs.325", childRows);
            parentList.add(parentRow);

            //test new
            childRows = new ArrayList<ChildRow>();
            childRows.add(new ChildRow("Title:  Fundamental of Thermodynamics and heat transfer", "Author: MK Luitel",
                    "Condition: Good", "Market Price: NRs.350", "Buy at: NRs.245"));
            parentRow = new ParentRow("3. Thermodynamics", "NRs.245", childRows);
            parentList.add(parentRow);

            childRows = new ArrayList<ChildRow>();
            childRows.add(new ChildRow("Title: TOC", "Author: Shuvankar Shah",
                    "Condition: Good", "Market Price: NRs.325", "Buy at: NRs.105"));
            parentRow = new ParentRow("4. Theory Of Computation", "NRs.105", childRows);
            parentList.add(parentRow);

            childRows = new ArrayList<ChildRow>();
            childRows.add(new ChildRow("Title: Probability and statistics", "Author:  Tek Bahadur Budhathoki",
                    "Condition: Bad", "Market Price: NRs.320", "Buy at: NRs.225"));
            parentRow = new ParentRow("5. Statistics", "NRs.225", childRows);
            parentList.add(parentRow);

            childRows = new ArrayList<ChildRow>();
            childRows.add(new ChildRow("Title: Fluid Mechanics and hydrualics", "Author: PN Modi",
                    "Condition: Good", "Market Price: NRs.280", "Buy at: NRs.175"));
            parentRow = new ParentRow("6. Fluid Mechanics", "NRs.175", childRows);
            parentList.add(parentRow);

            childRows = new ArrayList<ChildRow>();
            childRows.add(new ChildRow("Title: Artificial Intelligence", "Author: Sangita Chaulagain",
                    "Condition: Best", "Market Price: NRs.225", "Buy at: NRs.135"));
            parentRow = new ParentRow("7. AI", "NRs.135", childRows);
            parentList.add(parentRow);

            //new
            myList = (ExpandableListView) findViewById(R.id.expandableListView_search);
            listAdapter = new MyExpandableListAdapter(PreUserArea.this, parentList);
            myList.setAdapter(listAdapter);
        }

        //other data
        else if (faculty == 2 && year_part == 3) {
            parentList.clear();

            ArrayList<ChildRow> childRows = new ArrayList<ChildRow>();
            ParentRow parentRow = null;

            childRows = new ArrayList<ChildRow>();
            childRows.add(new ChildRow("Title: Basic Electronics", "Author: SK Gupta",
                    "Condition: Good", "Market Price: NRs.150", "Buy at: NRs.85"));
            parentRow = new ParentRow("1. Electronics I", "NRs.85", childRows);
            parentList.add(parentRow);

            childRows = new ArrayList<ChildRow>();
            childRows.add(new ChildRow("Title: Drawing I", "Author: Lekhak Ji",
                    "Condition: Best", "Market Price: NRs.550", "Buy at: NRs.375"));
            parentRow = new ParentRow("2. Engineering Drawing I", "NRs.375", childRows);
            parentList.add(parentRow);

            childRows = new ArrayList<ChildRow>();
            childRows.add(new ChildRow("Title: Discrete Mathematics", "Author: Shyam Poudel",
                    "Condition: Good", "Market Price: NRs.225", "Buy at: NRs.135"));
            parentRow = new ParentRow("3. Mathematics", "NRs.135", childRows);
            parentList.add(parentRow);

            //test
            childRows = new ArrayList<ChildRow>();
            childRows.add(new ChildRow("Title: TOC", "Author: Shuvankar Shah",
                    "Condition: Bad", "Market Price: NRs.325", "Buy at: NRs.105"));
            parentRow = new ParentRow("4. Theory Of Computation", "NRs.105", childRows);
            parentList.add(parentRow);

            childRows = new ArrayList<ChildRow>();
            childRows.add(new ChildRow("Title: Discrete Mathematics II", "Author: Shyam Poudel",
                    "Condition: Good", "Market Price: NRs.220", "Buy at: NRs.125"));
            parentRow = new ParentRow("5. Mathematics", "NRs.125", childRows);
            parentList.add(parentRow);

            childRows = new ArrayList<ChildRow>();
            childRows.add(new ChildRow("Title: Instrumentation I", "Author: Manzil Mudbari",
                    "Condition: Best", "Market Price: NRs.345", "Buy at: NRs.265"));
            parentRow = new ParentRow("6. Instrumentation", "NRs.265", childRows);
            parentList.add(parentRow);

            childRows = new ArrayList<ChildRow>();
            childRows.add(new ChildRow("Title: Artificial Intelligence", "Author: Sangita Chaulagain",
                    "Condition: Best", "Market Price: NRs.225", "Buy at: NRs.135"));
            parentRow = new ParentRow("7. AI", "NRs.135", childRows);
            parentList.add(parentRow);


            //new
            myList = (ExpandableListView) findViewById(R.id.expandableListView_search);
            listAdapter = new MyExpandableListAdapter(PreUserArea.this, parentList);
            myList.setAdapter(listAdapter);


        } else if (faculty == 2 && year_part == 1) {
            parentList.clear();

            ArrayList<ChildRow> childRows = new ArrayList<ChildRow>();
            ParentRow parentRow = null;

            childRows = new ArrayList<ChildRow>();
            childRows.add(new ChildRow("Title: Data Structure through C++", "Author: G.S Baluja",
                    "Condition: Bad", "Market Price: NRs.350", "Buy at: NRs.245"));
            parentRow = new ParentRow("1. Data Structure and Algorithm", "NRs.245", childRows);
            parentList.add(parentRow);

            childRows = new ArrayList<ChildRow>();
            childRows.add(new ChildRow("Title: Solutions of C and Fortran(notes)", "Author: Puskal Gautam",
                    "Condition: Good", "Market Price: NRs.80", "Buy at: NRs.55"));
            parentRow = new ParentRow("2. C and Fortran", "NRs.55", childRows);
            parentList.add(parentRow);

            childRows = new ArrayList<ChildRow>();
            childRows.add(new ChildRow("Title: Engineering Mathematics (vol-III)", "Author: SP Shrestha, HD Chaudhary, PR Pokharel",
                    "Condition: Bad", "Market Price: NRs.400", "Buy at: NRs.280"));
            parentRow = new ParentRow("3. Mathematics", "NRs.280", childRows);
            parentList.add(parentRow);

            //test
            childRows = new ArrayList<ChildRow>();
            childRows.add(new ChildRow("Title: Engineering Mathematics (vol-II)", "Author: Shuvankar Shah",
                    "Condition: Best", "Market Price: NRs.380", "Buy at: NRs.265"));
            parentRow = new ParentRow("4. Mathematics", "NRs.265", childRows);
            parentList.add(parentRow);

            childRows = new ArrayList<ChildRow>();
            childRows.add(new ChildRow("Title: Engineering Electromagnetics Solutions ", "Author: By Experienced Teachers",
                    "Condition: Good", "Market Price: NRs.300", "Buy at: NRs.210"));
            parentRow = new ParentRow("5. Electromagnetics", "NRs.210", childRows);
            parentList.add(parentRow);

            childRows = new ArrayList<ChildRow>();
            childRows.add(new ChildRow("Title: Electric Circuit Theory", "Author: Manzil Mudbari",
                    "Condition: Bad", "Market Price: NRs.270", "Buy at: NRs.215"));
            parentRow = new ParentRow("6. ECT", "NRs.155", childRows);
            parentList.add(parentRow);

            childRows = new ArrayList<ChildRow>();
            childRows.add(new ChildRow("Title: Artificial Intelligence", "Author: Sangita Chaulagain",
                    "Condition: Best", "Market Price: NRs.225", "Buy at: NRs.135"));
            parentRow = new ParentRow("7. AI", "NRs.135", childRows);
            parentList.add(parentRow);


            //new
            myList = (ExpandableListView) findViewById(R.id.expandableListView_search);
            listAdapter = new MyExpandableListAdapter(PreUserArea.this, parentList);
            myList.setAdapter(listAdapter);


        } else if (faculty == 4 && year_part == 7) {
            parentList.clear();

            ArrayList<ChildRow> childRows = new ArrayList<ChildRow>();
            ParentRow parentRow = null;

            childRows = new ArrayList<ChildRow>();
            childRows.add(new ChildRow("Title: Basic Electronics II", "Author: Some Author",
                    "Condition: Best", "Market Price: NRs.250", "Buy at: NRs.150"));
            parentRow = new ParentRow("1. Electronics II", "NRs.150", childRows);
            parentList.add(parentRow);

            childRows = new ArrayList<ChildRow>();
            childRows.add(new ChildRow("Title: Drawing II", "Author: Lekhak Ji",
                    "Condition: Good", "Market Price: NRs.175", "Buy at: NRs.95"));
            parentRow = new ParentRow("2. Engineering Drawing II", "NRs.95", childRows);
            parentList.add(parentRow);

            childRows = new ArrayList<ChildRow>();
            childRows.add(new ChildRow("Title: Artificial Intelligence", "Author: Sangita Chaulagain",
                    "Condition: Best", "Market Price: NRs.225", "Buy at: NRs.135"));
            parentRow = new ParentRow("3. AI", "NRs.135", childRows);
            parentList.add(parentRow);

            //test new
            childRows = new ArrayList<ChildRow>();
            childRows.add(new ChildRow("Title:  Fundamental of Thermodynamics and heat transfer", "Author: MK Luitel",
                    "Condition: Good", "Market Price: NRs.350", "Buy at: NRs.245"));
            parentRow = new ParentRow("4. Thermodynamics", "NRs.245", childRows);
            parentList.add(parentRow);

            childRows = new ArrayList<ChildRow>();
            childRows.add(new ChildRow("Title: TOC", "Author: Shuvankar Shah",
                    "Condition: Good", "Market Price: NRs.325", "Buy at: NRs.105"));
            parentRow = new ParentRow("5. Theory Of Computation", "NRs.105", childRows);
            parentList.add(parentRow);

            childRows = new ArrayList<ChildRow>();
            childRows.add(new ChildRow("Title: Probability and statistics", "Author:  Tek Bahadur Budhathoki",
                    "Condition: Bad", "Market Price: NRs.320", "Buy at: NRs.225"));
            parentRow = new ParentRow("6. Statistics", "NRs.225", childRows);
            parentList.add(parentRow);

            childRows = new ArrayList<ChildRow>();
            childRows.add(new ChildRow("Title: Fluid Mechanics and hydrualics", "Author: PN Modi",
                    "Condition: Good", "Market Price: NRs.280", "Buy at: NRs.175"));
            parentRow = new ParentRow("7. Fluid Mechanics", "NRs.175", childRows);
            parentList.add(parentRow);


            //new
            myList = (ExpandableListView) findViewById(R.id.expandableListView_search);
            listAdapter = new MyExpandableListAdapter(PreUserArea.this, parentList);
            myList.setAdapter(listAdapter);


        } else if (faculty == 5 && year_part == 2) {
            parentList.clear();

            ArrayList<ChildRow> childRows = new ArrayList<ChildRow>();
            ParentRow parentRow = null;

            //test
            childRows = new ArrayList<ChildRow>();
            childRows.add(new ChildRow("Title: TOC", "Author: Shuvankar Shah",
                    "Condition: Bad", "Market Price: NRs.325", "Buy at: NRs.105"));
            parentRow = new ParentRow("1. Theory Of Computation", "NRs.105", childRows);
            parentList.add(parentRow);

            childRows = new ArrayList<ChildRow>();
            childRows.add(new ChildRow("Title: Discrete Mathematics II", "Author: Shyam Poudel",
                    "Condition: Best", "Market Price: NRs.220", "Buy at: NRs.165"));
            parentRow = new ParentRow("2. Mathematics", "NRs.165", childRows);
            parentList.add(parentRow);

            childRows = new ArrayList<ChildRow>();
            childRows.add(new ChildRow("Title: Instrumentation I", "Author: Manzil Mudbari",
                    "Condition: Best", "Market Price: NRs.345", "Buy at: NRs.275"));
            parentRow = new ParentRow("3. Instrumentation", "NRs.275", childRows);
            parentList.add(parentRow);

            childRows = new ArrayList<ChildRow>();
            childRows.add(new ChildRow("Title: Basic Electronics", "Author: SK Gupta",
                    "Condition: Good", "Market Price: NRs.150", "Buy at: NRs.85"));
            parentRow = new ParentRow("4. Electronics I", "NRs.85", childRows);
            parentList.add(parentRow);

            childRows = new ArrayList<ChildRow>();
            childRows.add(new ChildRow("Title: Drawing I", "Author: Lekhak Ji",
                    "Condition: Best", "Market Price: NRs.550", "Buy at: NRs.375"));
            parentRow = new ParentRow("5. Engineering Drawing I", "NRs.375", childRows);
            parentList.add(parentRow);

            childRows = new ArrayList<ChildRow>();
            childRows.add(new ChildRow("Title: Discrete Mathematics", "Author: Shyam Poudel",
                    "Condition: Good", "Market Price: NRs.225", "Buy at: NRs.135"));
            parentRow = new ParentRow("6. Mathematics", "NRs.135", childRows);
            parentList.add(parentRow);

            childRows = new ArrayList<ChildRow>();
            childRows.add(new ChildRow("Title: Fluid Mechanics and hydrualics", "Author: PN Modi",
                    "Condition: Good", "Market Price: NRs.280", "Buy at: NRs.175"));
            parentRow = new ParentRow("7. Fluid Mechanics", "NRs.175", childRows);
            parentList.add(parentRow);


            //new
            myList = (ExpandableListView) findViewById(R.id.expandableListView_search);
            listAdapter = new MyExpandableListAdapter(PreUserArea.this, parentList);
            myList.setAdapter(listAdapter);


        } else {
            parentList.clear();
            myList = (ExpandableListView) findViewById(R.id.expandableListView_search);
            listAdapter = new MyExpandableListAdapter(PreUserArea.this, parentList);
            myList.setAdapter(listAdapter);

         /*   Toast.makeText(getApplicationContext(), "NO DATA SORRY!",
                    Toast.LENGTH_SHORT).show();
*/

        }
        //if finished

        //data finished

    }


}

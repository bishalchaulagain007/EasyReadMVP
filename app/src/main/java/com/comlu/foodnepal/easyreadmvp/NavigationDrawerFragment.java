package com.comlu.foodnepal.easyreadmvp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NavigationDrawerFragment extends Fragment {

    public static String USER_LOGGED_IN="1";

    //object of the recycler view adapter
    private recyclerViewAdapter adapter;

    //for the recyclerView

    private RecyclerView recyclerView;

    //a constant to represent the name of the file

    public static final String PREF_FILE_NAME = "testPref";

    //a key for the shared preferences

    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";

    //we need a reference of action bar drawer toggle

    private ActionBarDrawerToggle mDrawerToggle; //mDrawerToggle is a popular object used in android..u can name any
    private DrawerLayout mDrawerLayout;

    //for the orientation and creation of nav drawer

    //depending on the values of these two booleans, we should either show or hide the nav drawer
    private boolean mUserLearnedDrawer; //to check if the user is aware of the drawer open or close
    private boolean mFromSavedInstanceState; //to check if the drawer is opening for the first time or it is coming from the screen rotation

    private View containerView;

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }


    //for the booleans default values
    //was the app started by the user before this?
    //for the preferences

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //test

        //test finished

        //if false then it means that the user has never opened the app before
        mUserLearnedDrawer = Boolean.valueOf(readFromPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, "false"));
        if (savedInstanceState != null) {
            mFromSavedInstanceState = true;

        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

        //receiving the username and email
        String username=getActivity().getIntent().getStringExtra("username"); // "username" is the key provided in mainActivity
        String email=getActivity().getIntent().getStringExtra("email");
        //receiving finished

        //setting the fields
        TextView tvNavUsername = (TextView) layout.findViewById(R.id.tvNavUsername);
        TextView tvNavEmail = (TextView) layout.findViewById(R.id.tvNavEmail);
        tvNavUsername.setText("Username: \n" + username);
        tvNavEmail.setText("Email ID: \n" + email);
        //setting finished

        recyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);

        //Adapter
        adapter = new recyclerViewAdapter(getActivity(), getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //touch listener
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {

            @Override
            public void onClick(View view, int position) {

                if (position == 5) {

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                    alertDialogBuilder.setTitle("Log out?");
                    alertDialogBuilder
                            .setMessage("Click yes to exit")
                            .setCancelable(false)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            UserAreaActivity.USER_LOGGED_IN_USER_AREA=null;
                                            startActivity(new Intent(getContext(),LoginActivity.class));
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

                if(position==0)
                {
                    startActivity(new Intent(getContext(),DonateActivity.class));
                }
                if(position==1)
                {
                    Intent intent = new Intent(getContext(),MainActivity.class);
                    intent.putExtra(USER_LOGGED_IN,USER_LOGGED_IN);
                    startActivity(intent);
                }

//                Toast.makeText(getActivity(),"onClick Event " + position,Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onLongClick(View view, int position) {

                //events you like

//                Toast.makeText(getActivity(), "onLongClick Event " + position, Toast.LENGTH_SHORT).show();


            }
        }));

        return layout;


    }

    //the data to be displayed in the nav drawer

    public static List<NavDrawerItems> getData() {

        List<NavDrawerItems> data = new ArrayList<>();

        int[] icons = {
                R.drawable.sell,
                R.drawable.explore,
                R.drawable.syllabus,
                R.drawable.aboutus,
                R.drawable.help,
                R.drawable.exit,
        };

        String[] titles = {
                "Sell",
                "Explore",
                "Syllabus",
                "About us",
                "Help",
                "Log out",



        };

        String[] titlesDesc = {
                "Register books for Selling",
                "Explore the store for the book you want",
                "All of the Semester/Year syllabus",
                "App Developers and Designers",
                "More information about the app",
                "Log out of the app",

        };

        for (int i = 0; i < titles.length && i < icons.length; i++) {

            NavDrawerItems current = new NavDrawerItems();
            current.iconId = icons[i];
            current.title = titles[i];
            current.titleDesc = titlesDesc[i];
            data.add(current);

        }

        return data;

    }

    //setUp is used in MainActivity
    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {


        containerView = getActivity().findViewById(fragmentId);

        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {


            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                if (!mUserLearnedDrawer) {

                    mUserLearnedDrawer = true; //if the user has never seen the drawer before then 'TRUE' means the user saw it now
                    saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer + ""); //saving the above boolean
                }

                //actionBar needs to be redrawn since the nav drawer overlays on top of the main content/actionbar
                getActivity().invalidateOptionsMenu(); //draws the action bar again

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu(); //draws the action bar again

            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                if (slideOffset < 0.6) {
                    toolbar.setAlpha(1 - slideOffset);

                }
            }
        };

        //if the user has never seen the drawer
        //if the fragment is starting for the very first time

        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {

            mDrawerLayout.openDrawer(containerView);

        }

        //important to set this below listener always
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        //for the toggle button icon
        mDrawerLayout.post(new Runnable() {

            @Override
            public void run() {

                mDrawerToggle.syncState();
            }
        });


    }

    //method to save shared preferences
    //to write and read from it...so that we will know about the two booleans declared above
    //i.e abt the states of the nav drawer

    public static void saveToPreferences(Context context, String preferenceName, String preferenceValue) {


        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();

    }

    public static String readFromPreferences(Context context, String preferenceName, String defaultValue) {


        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName, defaultValue);
    }

//for gesture listener

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {


        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        //constructor
        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {

            //reference to the click listener variable
            this.clickListener = clickListener;

            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));


                    }


                }

            });


        }


        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {

                clickListener.onClick(child, rv.getChildPosition(child));


            }

            return false;

        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }


    public static interface ClickListener {

        public void onClick(View view, int position);

        public void onLongClick(View view, int position);

    }

}

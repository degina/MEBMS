package com.example.mebms.mebms;

import android.app.Dialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.itemanimators.AlphaCrossFadeAnimator;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.mikepenz.materialize.util.UIUtils;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class HomeActivity extends AppCompatActivity {
    private static final int PROFILE_SETTING = 1;

    //save our header or result
    private AccountHeader headerResult = null;
    public Drawer result = null;

    public static final String PREFS_NAME = "MEBP";
    public SharedPreferences prefs;

    private CharSequence mTitle;

    private ProgressDialog pDialog;
    public static final int progress_bar_type = 0;

    private int user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        prefs = getSharedPreferences(PREFS_NAME, 0);

        user_id = prefs.getInt("userId", 0);

        // Handle Toolbar
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.title_activity_home);

        // Create a few sample profile
        final IProfile profile = new ProfileDrawerItem().withName(prefs.getString("username", "User")).withIcon(R.drawable.my_profile);

        // Create the AccountHeader
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withCompactStyle(true)
                .withSelectionListEnabledForSingleProfile(false)
                .withHeaderBackground(R.drawable.header_2)
                .addProfiles(
                        profile
                )
                .withSavedInstance(savedInstanceState)
                .build();

        //Create the drawer
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggleAnimated(true)
                .withHasStableIds(true)
                .withItemAnimator(new AlphaCrossFadeAnimator())
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.title_section).withIcon(FontAwesome.Icon.faw_home).withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.title_section8).withIcon(FontAwesome.Icon.faw_hospital_o).withIdentifier(2),
                        new PrimaryDrawerItem().withName(R.string.title_section9).withIcon(FontAwesome.Icon.faw_hospital_o).withIdentifier(3),
                        new PrimaryDrawerItem().withName(R.string.title_section10).withIcon(FontAwesome.Icon.faw_hospital_o).withIdentifier(4),
                        new PrimaryDrawerItem().withName(R.string.title_section11).withIcon(FontAwesome.Icon.faw_hospital_o).withIdentifier(5),
                        new PrimaryDrawerItem().withName(R.string.title_section6).withIcon(FontAwesome.Icon.faw_hospital_o).withIdentifier(7),
                        new PrimaryDrawerItem().withName("Тайлан татах").withIcon(FontAwesome.Icon.faw_paperclip).withIdentifier(8),
                        new SecondaryDrawerItem().withName(R.string.logout).withIcon(FontAwesome.Icon.faw_power_off).withIdentifier(9)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            FragmentManager fragmentManager = getFragmentManager();
                            if (drawerItem.getIdentifier() == 1) {
                                fragmentManager.beginTransaction()
                                        .replace(R.id.frame_container, HomeFragment.newInstance())
                                        .commit();
                            } else if (drawerItem.getIdentifier() == 2) {
                                fragmentManager.beginTransaction()
                                        .replace(R.id.frame_container, ListUvchinFragment.newInstance())
                                        .commit();
                            } else if (drawerItem.getIdentifier() == 3) {
                                fragmentManager.beginTransaction()
                                        .replace(R.id.frame_container, ListSergiileltFragment.newInstance())
                                        .commit();
                            } else if (drawerItem.getIdentifier() == 4) {
                                fragmentManager.beginTransaction()
                                        .replace(R.id.frame_container, ListEmchilgeeFragment.newInstance())
                                        .commit();
                            } else if (drawerItem.getIdentifier() == 5) {
                                fragmentManager.beginTransaction()
                                        .replace(R.id.frame_container, ListShinjilgeeFragment.newInstance())
                                        .commit();
                            } else if (drawerItem.getIdentifier() == 7) {
                                fragmentManager.beginTransaction()
                                        .replace(R.id.frame_container, ListAjiltanFragment.newInstance())
                                        .commit();
                            } else if (drawerItem.getIdentifier() == 8) {
                                String url = "http://10.0.2.2:81/mebp/report.php?user_id=" + String.valueOf(prefs.getInt("userId", 0));
                                new DownloadFileFromURL().execute(url);
                            } else if (drawerItem.getIdentifier() == 9) {
                                finish();
                            } else {
                                fragmentManager.beginTransaction()
                                        .replace(R.id.frame_container, ListUvchinFragment.newInstance())
                                        .commit();
                            }
                        }

                        if (drawerItem instanceof Nameable) {
                            toolbar.setTitle(((Nameable) drawerItem).getName().getText(HomeActivity.this));
                        }

                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();

        // set the selection to the item with the identifier 5
        if (savedInstanceState == null) {
            result.setSelection(1, false);
        }

        //set the back arrow in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                break;
            case 5:
                mTitle = getString(R.string.title_section5);
                break;
            case 6:
                mTitle = getString(R.string.title_section11);
                break;
            case 7:
                mTitle = getString(R.string.title_section6);
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        outState = result.saveInstanceState(outState);
        //add the values which need to be saved from the accountHeader to the bundle
        outState = headerResult.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //handle the click on the back arrow click
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            int count = getFragmentManager().getBackStackEntryCount();

            if (count == 0) {
                super.onBackPressed();
                //additional code
            } else {
                getFragmentManager().popBackStack();
            }

        }
    }

    class ActionBarCallBack implements ActionMode.Callback {

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return false;
        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(UIUtils.getThemeColorFromAttrOrRes(HomeActivity.this, R.attr.colorPrimaryDark, R.color.material_drawer_primary_dark));
            }

            mode.getMenuInflater().inflate(R.menu.cab, menu);
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case progress_bar_type: // we set this to 0
                pDialog = new ProgressDialog(HomeActivity.this);
                pDialog.setMessage("Downloading file. Please wait...");
                pDialog.setIndeterminate(false);
                pDialog.setMax(100);
                pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pDialog.setCancelable(true);
                pDialog.show();
                return pDialog;
            default:
                return null;
        }
    }

    class DownloadFileFromURL extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Bar Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(progress_bar_type);
        }

        /**
         * Downloading file in background thread
         */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();

                // this will be useful so that you can show a tipical 0-100%
                // progress bar
                int lenghtOfFile = conection.getContentLength();

                // download the file
                InputStream input = new BufferedInputStream(url.openStream(),
                        8192);

                // Output stream
                OutputStream output = new FileOutputStream(Environment
                        .getExternalStorageDirectory().toString()
                        + "/Download/report.csv");

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }

        /**
         * Updating progress bar
         */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            pDialog.setProgress(Integer.parseInt(progress[0]));
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after the file was downloaded
            dismissDialog(progress_bar_type);

        }
    }
}

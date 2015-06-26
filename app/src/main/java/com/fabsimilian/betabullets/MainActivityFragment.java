package com.fabsimilian.betabullets;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.fabsimilian.betabullets.adapter.ProjectListAdapter;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {



    ParseUser mCurrentUser;

    @InjectView(R.id.rv_apk_list)
    RecyclerView mProjectsRecycler;

    ProjectListAdapter mAdapter;

    ArrayList<String> mProjectList = new ArrayList<>();

    public MainActivityFragment() {
        mProjectList.add("Foo");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.inject(this, root);
        mProjectsRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new ProjectListAdapter(mProjectList);
        mProjectsRecycler.setAdapter(mAdapter);
        init();
        return root;
    }

    void init() {
        mCurrentUser = ParseUser.getCurrentUser();
        if(mCurrentUser == null) {
            showRegister();
        }
        else {
            loadAppList();
        }
    }

    private void loadAppList() {
        mAdapter = new ProjectListAdapter(mProjectList);
        mProjectsRecycler.setAdapter(mAdapter);
    }

    private void showRegister() {

        View root = getActivity().getLayoutInflater().inflate(R.layout.dialog_register, null);
        TextView mTvDeviceSerial = (TextView) root.findViewById(R.id.tv_device_serial);
        mTvDeviceSerial.setText(Build.SERIAL);
        final EditText mEditDisplayName = (EditText) root.findViewById(R.id.edit_display_name);
        mEditDisplayName.setText(Build.BRAND + " " + Build.MODEL);
        final EditText mEditTANumber = (EditText) root.findViewById(R.id.edit_ta_number);
        final EditText mEditBrand = (EditText) root.findViewById(R.id.edit_brand);
        mEditBrand.setText(Build.BRAND);
        final EditText mEditModel = (EditText) root.findViewById(R.id.edit_model);
        mEditModel.setText(Build.MODEL);

        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setView(root)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final ParseUser user = new ParseUser();
                        user.setUsername(Build.SERIAL);
                        user.setPassword(getString(R.string.default_password));
                        user.signUpInBackground(new SignUpCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    setDeviceModelData(mEditDisplayName.getText().toString(), mEditTANumber.getText().toString(), mEditBrand.getText().toString(), mEditModel.getText().toString());
                                    loadAppList();
                                } else {
                                    //try to login
                                    Toast.makeText(getActivity().getApplicationContext(), "ERROR: " + e.getMessage() + "\ntry to login with user", Toast.LENGTH_LONG).show();
                                    user.logInInBackground(Build.SERIAL, getString(R.string.default_password), new LogInCallback() {
                                        @Override
                                        public void done(ParseUser parseUser, ParseException e) {
                                            if (e == null) {
                                                loadAppList();
                                            } else {
                                                Toast.makeText(getActivity().getApplicationContext(), "ERROR: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }
                })
                .create();
        dialog.show();
    }

    private void setDeviceModelData(final String displayName, final String taNumber, final String brand, final String model) {
        mCurrentUser = ParseUser.getCurrentUser();
        ParseObject parseObject = new ParseObject("device");
        parseObject.put("deviceId", mCurrentUser.getObjectId());
        parseObject.put("displayName", displayName);
        parseObject.put("taNumber", taNumber);
        parseObject.put("brand", brand);
        parseObject.put("model", model);
        parseObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null) {
                    Toast.makeText(getActivity().getApplicationContext(), "Register Device successfully", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(), "ERROR: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setDeviceModelData(displayName, taNumber, brand, model);
                        }
                    }, 2000);
                }
            }
        });

    }
}

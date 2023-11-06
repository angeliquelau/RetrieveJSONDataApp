package com.example.assignment2parta;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class UserDetailFragment extends Fragment {
    private UserData userData;
    private String selectedUser;
    private TextView name, username, email, phone, website, company, address;
    private Button load;

    public UserDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userData = getArguments().getParcelable("userData");
        selectedUser = getArguments().getString("username");
        Log.d("user detail frag: ", "testing: " + userData.getGeoList().get(0).getLatitude());
        Log.d("user detail frag: ", "testing: " + selectedUser);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_detail, container, false);

        name = view.findViewById(R.id.nameTxt);
        username = view.findViewById(R.id.unTxt);
        email = view.findViewById(R.id.emailTxt);
        phone = view.findViewById(R.id.phoneTxt);
        website = view.findViewById(R.id.websiteTxt);
        company = view.findViewById(R.id.companyTxt);
        address = view.findViewById(R.id.addressTxt);
        load = view.findViewById(R.id.loadButton);

        setDetails(name, username, email, phone, website,company, address);

        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoadPostActivity.class);
                intent.putExtra("userId", userData.getIdByUN(selectedUser));
                startActivity(intent);
            }
        });
        Log.d("user detail frag: ", " hello???  .....");

        return view;
    }

    private void setDetails(TextView name, TextView username,TextView email, TextView phone, TextView website, TextView company, TextView address)
    {
        List<String> userInfo = userData.getDetailByUN(selectedUser);
        name.setText(userInfo.get(0));
        username.setText(userInfo.get(1));
        email.setText(userInfo.get(2));
        phone.setText(userInfo.get(3));
        website.setText(userInfo.get(4));
        company.setText("name: " + userInfo.get(5) + "\nCatch phrase: " + userInfo.get(6) + "\nbs: " + userInfo.get(7));
        address.setText("Street:" + userInfo.get(8) + "\nSuite: " + userInfo.get(9) + "\nCity: " +
                userInfo.get(10) + "\nZipcode: " + userInfo.get(11) + "\nCoordinates: " + userInfo.get(12) + ", " + userInfo.get(13));


    }
}
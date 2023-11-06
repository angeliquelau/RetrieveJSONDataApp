package com.example.assignment2parta;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class UserFragment extends Fragment {
    private RecyclerView rv;
    private RecyclerView.Adapter userAdapter;
    private UserData userData;
    private TextView selectUser;

    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userData = getArguments().getParcelable("userData");
        Log.d("user fragment: ", "size: " + userData.getUserList().size());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        rv = (RecyclerView) view.findViewById(R.id.userRV);
        rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        userAdapter = new UserAdapter();
        rv.setAdapter(userAdapter);
        return view;
    }

    public class UViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private User user;

        public UViewHolder(LayoutInflater li, ViewGroup view) {
            super(li.inflate(R.layout.user_list, view, false));
            selectUser = itemView.findViewById(R.id.userTxt);
            Log.d("user fragment: ", "hi hello testing");
            itemView.setOnClickListener(this);
        }

        public void bind(User user)
        {
            this.user = user;
            selectUser.setText(user.getUsername());
        }

        @Override
        public void onClick(View view) {
            Log.d("user fragment: ", "userData: " + userData.getUserList().get(0).getUsername());
            Bundle bundle = new Bundle();
            bundle.putParcelable("userData", userData);
            bundle.putString("username", userData.getUserList().get(getAdapterPosition()).getUsername()); //the selected username
            AppCompatActivity appAct = (AppCompatActivity) itemView.getContext();
            UserDetailFragment udf = new UserDetailFragment();
            appAct.getSupportFragmentManager().beginTransaction().replace(R.id.userSelector, udf).commit();
            udf.setArguments(bundle);

        }
    }

    public class UserAdapter extends RecyclerView.Adapter<UViewHolder> {
        @NonNull
        @Override
        public UViewHolder onCreateViewHolder(@NonNull ViewGroup view, int viewType) {
            LayoutInflater li = LayoutInflater.from(getActivity());
            return new UViewHolder(li, view);
        }

        @Override
        public void onBindViewHolder(@NonNull UViewHolder holder, int position) {
            holder.bind(userData.getUserList().get(position));
        }

        @Override
        public int getItemCount() {
            return userData.getUserList().size();
        }
    }


}
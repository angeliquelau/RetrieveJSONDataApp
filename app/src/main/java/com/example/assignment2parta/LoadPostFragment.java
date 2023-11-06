package com.example.assignment2parta;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class LoadPostFragment extends Fragment {

    private RecyclerView rv;
    private RecyclerView.Adapter postAdapter;
    private PostData postData;
    private TextView userId, title, body;
    private int selectedUserId;

    public LoadPostFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postData = getArguments().getParcelable("posts");
        selectedUserId = getArguments().getInt("selectedUserId");
        Log.d("post fragment: ", "size: " + postData.getPostList().size() + ", selected user id: " + selectedUserId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_load_post, container, false);
        rv = view.findViewById(R.id.postRV);
        rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        postAdapter = new PostAdapter();
        rv.setAdapter(postAdapter);
        return view;
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        private Post post;


        public PostViewHolder(LayoutInflater li, ViewGroup view) {
            super(li.inflate(R.layout.post_list, view, false));
            userId = itemView.findViewById(R.id.userIdTxt);
            title = itemView.findViewById(R.id.titleTxt);
            body = itemView.findViewById(R.id.bodyTxt);

        }

        public void bind(Post post)
        {
            this.post = post;
            Log.d("bind: ", "HIIIIIIi" + post.getBody());

            Log.d("in bind: ", "userId post: " + post.getUserId());
            userId.setText("User ID: " + post.getUserId());
            title.setText(post.getTitle());
            body.setText(post.getBody());

        }
    }

    public class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {
        @NonNull
        @Override
        public LoadPostFragment.PostViewHolder onCreateViewHolder(@NonNull ViewGroup view, int viewType) {
            LayoutInflater li = LayoutInflater.from(getActivity());
            return new PostViewHolder(li, view);
        }

        @Override
        public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {

            holder.bind(postData.getPostList().get(position));
        }

        @Override
        public int getItemCount() {
            return postData.getPostList().size();
        }
    }
}
package com.fabsimilian.betabullets.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fabsimilian.betabullets.R;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by tafabs on 26.06.15.
 */
public class ProjectListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<String> mProjectList;

    public ProjectListAdapter(ArrayList<String> projectList) {
        mProjectList = projectList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_project, parent, false);
        RecyclerView.ViewHolder holder = new ProductViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final String project = mProjectList.get(position);
        ProductViewHolder holder = (ProductViewHolder) viewHolder;
        holder.mProjectName.setText(project);
        holder.mProjectName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkDownloadInstallFile(project, view.getContext());
            }
        });

    }

    private void checkDownloadInstallFile(String project, Context context) {
        Toast.makeText(context, "Install", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return mProjectList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.tv_project_name)
        public TextView mProjectName;

        @InjectView(R.id.rl_project_root)
        public View mProjectRoot;

        public ProductViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}

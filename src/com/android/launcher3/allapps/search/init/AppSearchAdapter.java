package com.android.launcher3.allapps.search.init;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.launcher3.R;

import java.util.ArrayList;

public class AppSearchAdapter extends RecyclerView.Adapter<AppSearchAdapter.ViewHolder> {
    private ArrayList<AppSearchSettings> settingsSearchDataList;


    public AppSearchAdapter(ArrayList<AppSearchSettings> settingsSearchDataList) {
        this.settingsSearchDataList = settingsSearchDataList;
    }


    @NonNull
    @Override
    public AppSearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.viewholder_settings, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AppSearchAdapter.ViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return settingsSearchDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        //public ImageView imgPersonIcon = (ImageView) itemView.findViewById(R.id.img_person_icon);
        public ImageView imgSettingIcon;
        public TextView tvSearchTitle;
        public TextView tvSearchSummery;
        public LinearLayout layoutTittleSetting;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSettingIcon = (ImageView) itemView.findViewById(R.id.img_setting_icon);
            tvSearchTitle = (TextView) itemView.findViewById(R.id.title);
            tvSearchSummery = (TextView) itemView.findViewById(R.id.summary);
            layoutTittleSetting = (LinearLayout) itemView.findViewById(R.id.layout_tittle_setting);
        }
    }
}

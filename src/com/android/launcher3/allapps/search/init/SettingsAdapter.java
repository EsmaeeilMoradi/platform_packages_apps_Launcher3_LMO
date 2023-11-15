package com.android.launcher3.allapps.search.init;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.launcher3.R;

import java.util.ArrayList;
import java.util.List;

public class SettingsAdapter  extends RecyclerView.Adapter<SettingsAdapter.ViewHolder>{
    private List<AppSearchSettings> listdata;

    // RecyclerView recyclerView;
    public SettingsAdapter(List<AppSearchSettings> listdata) {
        this.listdata = listdata;
    }


    @Override
    public SettingsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.item_contact, parent, false);
        SettingsAdapter.ViewHolder viewHolder = new SettingsAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SettingsAdapter.ViewHolder holder, int position) {
        final AppSearchSettings myListData = listdata.get(position);
        if (listdata.get(position).getTitle() != null) {

            holder.textView.setText(listdata.get(position).getTitle());
        }
        if (listdata.get(position).getBreadcrumbs() != null && (listdata.get(position).getBreadcrumbs().size()!=0)) {

            holder.textView2.setText(listdata.get(position).getBreadcrumbs().get(0));
        }
//        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(view.getContext(),"click on item: "+myListData.getName(),Toast.LENGTH_LONG).show();
//            }
//        });
    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public TextView textView2;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(R.id.title);
            this.textView2 = (TextView) itemView.findViewById(R.id.summary);
//            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.relativeLayout);
        }
    }
}
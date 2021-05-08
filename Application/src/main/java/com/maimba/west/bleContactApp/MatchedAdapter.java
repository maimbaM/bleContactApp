package com.maimba.west.bleContactApp;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maimba.west.bleContactApp.DB.MatchedPackets;

import java.util.ArrayList;
import java.util.List;

public class MatchedAdapter extends RecyclerView.Adapter <MatchedAdapter.MatchedHolder>{

    private List<MatchedPackets> matchedPackets = new ArrayList<>();

    @NonNull
    @Override
    public MatchedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.matched_packet_item,parent,false);
        return new MatchedHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchedHolder holder, int position) {

         MatchedPackets currentMatchedPacket = matchedPackets.get(position);
//        holder.textViewPktData.setText(currentMatchedPacket.getPacket());
        holder.textViewTimeseen.setText((CharSequence) currentMatchedPacket.getTimeExposed());
        holder.textViewLocation.setText(currentMatchedPacket.getLocation());
        holder.textViewName.setText(currentMatchedPacket.getName());
        holder.textViewDisease.setText(currentMatchedPacket.getDisease());
        holder.textViewPhone.setText(currentMatchedPacket.getPhone());

        boolean isExpandable = matchedPackets.get(position).isExpandable();
        holder.relativeLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);




    }

    @Override
    public int getItemCount() {
        return matchedPackets.size();
    }

    public void setMatchedPackets(List<MatchedPackets> matchedPackets){
        this.matchedPackets = matchedPackets;
        notifyDataSetChanged();
    }

    class MatchedHolder extends RecyclerView.ViewHolder{
        private TextView textViewPktData;
        private TextView textViewTimeseen;
        private TextView textViewLocation;
        private TextView textViewName;
        private TextView textViewDisease;
        private TextView textViewPhone;
        private LinearLayout linearLayout;
        private RelativeLayout relativeLayout;

        public MatchedHolder(@NonNull View itemView) {
            super(itemView);
            textViewPktData = itemView.findViewById(R.id.tv_pktData);
            textViewTimeseen = itemView.findViewById(R.id.tv_timeseen);
            textViewLocation = itemView.findViewById(R.id.tv_location);
            textViewDisease = itemView.findViewById(R.id.tv_diseaseName);
            textViewName = itemView.findViewById(R.id.tv_name);
            textViewPhone = itemView.findViewById(R.id.tv_phone);

            linearLayout = itemView.findViewById(R.id.linearlayout);
            relativeLayout = itemView.findViewById(R.id.expandable_layout);

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MatchedPackets matchedPacketsObj = matchedPackets.get(getAdapterPosition());
                    matchedPacketsObj.setExpandable(!matchedPacketsObj.isExpandable());
                    notifyItemChanged(getAdapterPosition());
                }

            });
            textViewPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MatchedPackets newPacket = new MatchedPackets();
                    String Phone = newPacket.getPhone();

                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + Phone));
                }
            });

        }
    }

}

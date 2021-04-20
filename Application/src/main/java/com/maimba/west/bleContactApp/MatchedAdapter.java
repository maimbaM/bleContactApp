package com.maimba.west.bleContactApp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
                .inflate(R.layout.packet_item,parent,false);
        return new MatchedHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchedHolder holder, int position) {

        MatchedPackets currentMatchedPacket = matchedPackets.get(position);
        holder.textViewPktData.setText(currentMatchedPacket.getPacket());
//        holder.textViewTimeseen.setText(currentMatchedPacket.getTimeExposed());
//        holder.textViewLocation.setText(currentMatchedPacket.);


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

        public MatchedHolder(@NonNull View itemView) {
            super(itemView);
            textViewPktData = itemView.findViewById(R.id.tv_pktData);
            textViewLocation = itemView.findViewById(R.id.tv_Location);
            textViewTimeseen = itemView.findViewById(R.id.tv_timeseen);
        }
    }

}

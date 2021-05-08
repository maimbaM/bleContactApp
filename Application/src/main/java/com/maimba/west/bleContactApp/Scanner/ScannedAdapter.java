package com.maimba.west.bleContactApp.Scanner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maimba.west.bleContactApp.DB.ScannedPacket;
import com.maimba.west.bleContactApp.R;

import java.lang.invoke.LambdaConversionException;
import java.util.ArrayList;
import java.util.List;

public class ScannedAdapter extends RecyclerView.Adapter<ScannedAdapter.ScannedHolder> {

    private List<ScannedPacket> scannedPackets = new ArrayList<>();
    @NonNull
    @Override
    public ScannedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.packet_item,parent,false);
        return new ScannedHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ScannedHolder holder, int position) {
        ScannedPacket currentScannedPacket = scannedPackets.get(position);
        holder.tv_scanned.setText(currentScannedPacket.getPktData());
        holder.tv_scannedTime.setText(currentScannedPacket.getTimeSeen());
        holder.tv_scannedLocation.setText(currentScannedPacket.getLocation());


    }

    @Override
    public int getItemCount() {
        return scannedPackets.size();
    }

    public void setScannedPackets(List<ScannedPacket> scannedPackets){
        this.scannedPackets = scannedPackets;
        notifyDataSetChanged();

    }

    class ScannedHolder extends RecyclerView.ViewHolder{

        private TextView tv_scanned;
        private TextView tv_scannedTime;
        private TextView tv_scannedLocation;


        public ScannedHolder(@NonNull View itemView) {
            super(itemView);

            tv_scanned = itemView.findViewById(R.id.tv_pktData);
            tv_scannedTime = itemView.findViewById(R.id.tv_timeseen);
            tv_scannedLocation = itemView.findViewById(R.id.tv_location);
        }
    }
}

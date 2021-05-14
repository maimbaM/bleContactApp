package com.maimba.west.bleContactApp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SymptomsAdapter extends RecyclerView.Adapter <SymptomsAdapter.SymptomsHolder>{

    private List<Symptoms> symptomsList = new ArrayList<>();


    @NonNull
    @Override
    public SymptomsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                  View itemView = LayoutInflater.from(parent.getContext())
                          .inflate(R.layout.disease_card,parent,false);
              return new SymptomsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SymptomsHolder holder, int position) {
        Symptoms currentSymptoms = symptomsList.get(position);
        holder.tvDiseaseName.setText(currentSymptoms.getDiseaseName());
        holder.tvIncubationPeriod.setText(currentSymptoms.getIncubationPeriod());
        holder.tvScienceName.setText(currentSymptoms.getScienceName());
        holder.tvCommon.setText(currentSymptoms.getCommon());
        holder.tvSevere.setText(currentSymptoms.getSevere());

        boolean isExpandable = symptomsList.get(position).isExpandable();
        holder.relativeLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);

    }

    @Override
    public int getItemCount() {
        return symptomsList.size();
    }
    public void setSymptomsList(List<Symptoms> symptomsList){
        this.symptomsList = symptomsList;
        notifyDataSetChanged();
    }
    class SymptomsHolder extends RecyclerView.ViewHolder{
        private TextView tvDiseaseName;
        private TextView tvIncubationPeriod;
        private TextView tvScienceName;
        private TextView tvCommon;
        private TextView tvSevere;
        private LinearLayout linearLayout;
        private RelativeLayout relativeLayout;


        public SymptomsHolder(@NonNull View itemView) {
            super(itemView);

            tvDiseaseName = itemView.findViewById(R.id.sm_DiseaseName);
            tvIncubationPeriod = itemView.findViewById(R.id.sm_Incubation);
            tvScienceName = itemView.findViewById(R.id.sm_SccienceName);
            tvCommon = itemView.findViewById(R.id.sm_Common);
            tvSevere = itemView.findViewById(R.id.sm_Severe);

            linearLayout = itemView.findViewById(R.id.sm_linearlayout);
            relativeLayout = itemView.findViewById(R.id.sm_expandable_layout);

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Symptoms symptomsObj = symptomsList.get(getAdapterPosition());
                    symptomsObj.setExpandable(!symptomsObj.isExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });

        }
    }
}

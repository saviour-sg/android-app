package com.socgen.saviour;


import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.media.Image;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.Date;

public class RequestListViewAdapter extends BaseAdapter {
    Context context;
    ArrayList<Request> requests;
    public RequestListViewAdapter(Context context, ArrayList<Request> requests) {
        this.context=context;
        this.requests = requests;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {}

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {}

    @Override
    public int getCount() {
        return requests.size();
    }

    @Override
    public Object getItem(int position) {
        return requests.get(position);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    public View getView(int position, View view, ViewGroup parent) {
        Request request= requests.get(position);
        if(view==null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view=layoutInflater.inflate(R.layout.request, null);
            view.setClickable(false);

            TextView name=view.findViewById(R.id.name);
            TextView address=view.findViewById(R.id.location);
            TextView bloodGroup=view.findViewById(R.id.blood_group);
            TextView timeLabel=view.findViewById(R.id.timelabel);
            TextView requestedBy=view.findViewById(R.id.requestedBy);
            TextView unitsLabel=view.findViewById(R.id.units_label);

            address.setText(request.getHospital()+"\n"+request.getAddress()+"\n"+request.getCity());
            bloodGroup.setText(request.getBloodGroup());
            if(request.getTimestamp()!=null){
                long currentTime = new Date().getTime();
                int hr = (int) ((currentTime - request.getTimestamp())/(3600*1000));
                if(hr<=1) {
                    timeLabel.setText(hr+" hr ago");
                } else{
                    timeLabel.setText(hr+" hrs ago");
                }

            }
            requestedBy.setText(request.getRequesterName());
            Button donate=view.findViewById(R.id.donate);
            ImageView call=view.findViewById(R.id.call);
            LinearLayout ll = view.findViewById(R.id.units);

            int units = Integer.parseInt(request.getUnits());
            int donatedUnits = 0;
            if(request.getDonors()!=null){
                donatedUnits = request.getDonors().size();
            }
            int pendingUnits = units - donatedUnits;


            for(int i=0; i<units-pendingUnits; ++i){
                ImageView redBloodDrop = new ImageView(context);
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(45, 45);
                redBloodDrop.setImageResource(R.drawable.blood_drop_red);
                redBloodDrop.setLayoutParams(layoutParams);
                ll.addView(redBloodDrop);
            }
            for(int i=0; i<pendingUnits; ++i){
                ImageView greyBloodDrop = new ImageView(context);
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(45, 45);
                greyBloodDrop.setImageResource(R.drawable.blood_drop_grey);
                greyBloodDrop.setLayoutParams(layoutParams);
                ll.addView(greyBloodDrop);
            }

            unitsLabel.setText(donatedUnits+"/"+units+" UNITS");



            donate.setOnClickListener(v -> {
                new MaterialAlertDialogBuilder(context)
                        .setTitle("CONFIRMATION")
                        .setMessage("Do you confirm the donation?\n\n\t\tName: "+request.getPatientName()+"\n\t\tBlood Group : "+request.getBloodGroup())
                        .setNegativeButton("NO",null)
                        .setPositiveButton("YES",(dialog, which) -> {
                            Intent myIntent = new Intent(context, DonorAcceptance.class);
                            startActivity(context,myIntent,null);
                        }).show();
//                Toast.makeText(context,"Donate",Toast.LENGTH_LONG).show();
            });
            call.setOnClickListener(v -> {
                Toast.makeText(context,"Call",Toast.LENGTH_LONG).show();
            });
            name.setText(request.getPatientName());
        }
        return view;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return requests.size();
    }

    @Override
    public boolean isEmpty() {
        return requests.size()==0;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    public void update(ArrayList<Request> newRequests) {
        this.requests = new ArrayList<>();
        this.requests.addAll(newRequests);
        notifyDataSetChanged();
    }
}

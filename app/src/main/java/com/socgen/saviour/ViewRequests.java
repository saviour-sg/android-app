package com.socgen.saviour;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ViewRequests extends AppCompatActivity {
    ListView list1,list2;
    FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_requests);

        String phone = "9080093100";

        db = FirebaseDatabase.getInstance();
        DatabaseReference root = db.getReference("");
        list1 = findViewById(R.id.my_requests);
        list2 = findViewById(R.id.all_requests);

        RequestListViewAdapter myRequestsListAdapter = new RequestListViewAdapter(this, new ArrayList<Request>());
        RequestListViewAdapter allRequestsListAdapter = new RequestListViewAdapter(this, new ArrayList<Request>());
        root.child("requests").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                ArrayList<Request> myRequests = new ArrayList<>();
                ArrayList<Request> allRequests = new ArrayList<>();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Request request = postSnapshot.getValue(Request.class);
                    if(request.getRequesterNumber()!=null &&
                            request.getRequesterNumber().equals(phone)){
                        myRequests.add(request);
                    } else{
                        allRequests.add(request);
                    }
                }
                if(myRequests.size()!=0) {
                    myRequestsListAdapter.update(myRequests);
                    list1.setAdapter(myRequestsListAdapter);
                }
                if(allRequests.size()!=0) {
                    allRequestsListAdapter.update(allRequests);
                    list2.setAdapter(allRequestsListAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ViewRequests.this,"Error fetching stats!",Toast.LENGTH_LONG).show();
            }
        });


    }
}
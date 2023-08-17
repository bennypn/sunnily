package com.example.sunnily;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class HistoryAdapter extends FirebaseRecyclerAdapter<History,HistoryAdapter.HistoryViewholder> {

    //private String name;

    public HistoryAdapter(
            @NonNull FirebaseRecyclerOptions<History> options)
    {
        super(options);
    }

    @Override
    protected void
    onBindViewHolder(@NonNull HistoryViewholder holder,
                     int position, @NonNull History model)
    {
        String tanggal = model.getTanggal();
        String hum0 = model.getHumid0();
        String hum1 = model.getHumid1();
        String hum2 = model.getHumid2();
        String uv0 = model.getUv0();


        holder.tanggaltv.setText(tanggal);
        holder.hum0tv.setText(hum0);
        holder.hum1tv.setText(hum1);
        holder.hum2tv.setText(hum2);
        holder.uv0tv.setText(uv0);


    }

    // Function to tell the class about the Card view (here
    // "History.xml")in
    // which the data will be shown
    @NonNull
    @Override
    public HistoryViewholder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType)
    {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.table_item_layout, parent, false);
        return new HistoryAdapter.HistoryViewholder(view);
    }

    // Sub Class to create references of the views in Crad
    // view (here "History.xml")
    class HistoryViewholder
            extends RecyclerView.ViewHolder {
        TextView tanggaltv, hum0tv,hum1tv,hum2tv,uv0tv;
        public HistoryViewholder(@NonNull View itemView)
        {
            super(itemView);

            tanggaltv = itemView.findViewById(R.id.unixTimeTextView);
            hum0tv = itemView.findViewById(R.id.hum0tv);
            hum1tv = itemView.findViewById(R.id.hum1tv);
            hum2tv = itemView.findViewById(R.id.hum2tv);
            uv0tv = itemView.findViewById(R.id.uv0tv);
        }
    }


}


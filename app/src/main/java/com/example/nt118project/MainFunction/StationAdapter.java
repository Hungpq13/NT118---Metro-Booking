package com.example.nt118project.MainFunction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.nt118project.MainFunction.Station;
import com.example.nt118project.R;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StationAdapter extends ArrayAdapter<Station> {
    private final Context mContext;
    private final int mResource;
    private final List<Station> mObjects;

    public StationAdapter(@NonNull Context context, int resource, @NonNull List<Station> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
        this.mObjects = objects;

        // Sắp xếp danh sách các đối tượng Station trước khi hiển thị
        Collections.sort(mObjects, new Comparator<Station>() {
            @Override
            public int compare(Station station1, Station station2) {
                return Integer.compare(Integer.parseInt(station1.getStationID()), Integer.parseInt(station2.getStationID()));
            }
        });
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Station currentItem = mObjects.get(position);

        if (currentItem == null) {
            return convertView;
        }

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
        }

        TextView stationName = convertView.findViewById(R.id.stationName);
        TextView stationID = convertView.findViewById(R.id.stationID);
        TextView trainLogo = convertView.findViewById(R.id.trainLogo);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, NearStation2Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("stationID", currentItem.getStationID());
                intent.putExtra("stationName", currentItem.getStationName());
                intent.putExtra("stationDocumentID", currentItem.getStationDocumentID());
                mContext.startActivity(intent);
            }
        });

        stationName.setText(currentItem.getStationName());
        stationID.setText("Trạm " + currentItem.getStationID());
        trainLogo.setText(currentItem.getStationID());

        return convertView;
    }
}

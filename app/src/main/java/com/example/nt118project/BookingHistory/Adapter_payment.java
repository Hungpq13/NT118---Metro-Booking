package com.example.metro_booking_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class Adapter_payment extends ArrayAdapter<String>
{
    Context context;
    String [] name;
    int[] images;

    public Adapter_payment(@NonNull Context context, String[] name, int[] images) {
        super(context, R.layout.spinner_item_payment);
        this.context = context;
        this.name = name;
        this.images = images;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.spinner_item_payment,null);
        TextView t1 = (TextView) row.findViewById(R.id.txt_momo);
        ImageView i1 = (ImageView) row.findViewById(R.id.momo);

        t1.setText(name[position]);
        i1.setImageResource(images[position]);
        return row;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.spinner_item_payment,null);
        TextView t1 = (TextView) row.findViewById(R.id.txt_momo);
        ImageView i1 = (ImageView) row.findViewById(R.id.momo);


        t1.setText(name[position]);
        i1.setImageResource(images[position]);

        return row;
    }
}
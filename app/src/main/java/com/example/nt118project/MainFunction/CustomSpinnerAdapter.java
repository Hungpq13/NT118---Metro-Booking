package com.example.nt118project.MainFunction;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nt118project.R;

import java.util.List;

public class CustomSpinnerAdapter extends ArrayAdapter<PaymentMethod> {

    public CustomSpinnerAdapter(Context context, List<PaymentMethod> paymentMethods) {
        super(context, 0, paymentMethods);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return createView(position, convertView, parent);
    }

    private View createView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item_with_icon, parent, false);
        }

        PaymentMethod paymentMethod = getItem(position);

        ImageView iconImageView = convertView.findViewById(R.id.spinnerIcon);
        TextView textView = convertView.findViewById(R.id.spinnerText);

        if (paymentMethod != null) {
            iconImageView.setImageResource(paymentMethod.getIconResId());
            textView.setText(paymentMethod.getName());
        }

        return convertView;
    }
}

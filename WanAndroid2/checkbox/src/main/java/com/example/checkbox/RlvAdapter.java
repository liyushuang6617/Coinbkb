package com.example.checkbox;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RlvAdapter extends RecyclerView.Adapter<RlvAdapter.ViewHolder> {

    private Context context;
    public ArrayList<Data> list;
    private ArrayList<Boolean> booleans = new ArrayList<>();
    public SparseBooleanArray sbaState = new SparseBooleanArray();


    public RlvAdapter(Context context, ArrayList<Data> list) {
        this.context = context;
        this.list = list;
    }

    public void setList(ArrayList<Data> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.tv.setText(list.get(i).getName());
//        viewHolder.cb.setChecked(booleans.get(i));


        viewHolder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sbaState.put(i, isChecked);
                    SharedPreferences sp = context.getSharedPreferences("checked", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putInt("ischeck", i);
                    edit.putBoolean("status", true);
                    edit.commit();

                } else {
                    sbaState.delete(i);
                }
            }
        });
        viewHolder.cb.setChecked(sbaState.get(i, false));

        viewHolder.cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (onClick != null) {
                    onClick.onClick(i, list.get(i));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox cb;
        TextView tv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cb = itemView.findViewById(R.id.cb);
            tv = itemView.findViewById(R.id.tv);
        }
    }

    private onClick onClick;

    public void setOnClick(RlvAdapter.onClick onClick) {
        this.onClick = onClick;
    }

    public interface onClick {
        void onClick(int pos, Data data);
    }
}

package com.example.personal_diary_2022551022;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder>{
    public ArrayList<ContactItem> mContactList;


    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

    private OnItemClickListener onItemClickListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }


    public interface OnLongItemClickListener {
        void onLongItemClick(int pos);
    }

    private OnLongItemClickListener onLongItemClickListener = null;

    public void setOnLongItemClickListener(OnLongItemClickListener listener) {
        this.onLongItemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(mContactList.get(position));
    }

    public void setContactList(ArrayList<ContactItem> list){
        this.mContactList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mContactList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView detail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.text_contName_item);
            detail = (TextView) itemView.findViewById(R.id.text_contDetail_item);

            itemView.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(pos);
                    }
                }
            });
            itemView.setOnLongClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    if (onLongItemClickListener != null) {
                        onLongItemClickListener.onLongItemClick(pos);
                    }
                }
                return true;
            });
        }

        void onBind(ContactItem item){
            name.setText(item.getName());
            detail.setText(item.getDetail());
        }
    }
}

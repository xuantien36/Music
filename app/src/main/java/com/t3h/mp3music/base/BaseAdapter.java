package com.t3h.mp3music.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.t3h.mp3music.model.BaseModel;

import java.util.ArrayList;
import java.util.List;

public class BaseAdapter<T extends BaseModel> extends RecyclerView.Adapter<BaseAdapter.BaseHolder> {
    private List<T> data;
    private LayoutInflater inflater;
    private int layoutId;
    private BaseItemListener listener;
    private Context context;

    public BaseAdapter(Context context, @LayoutRes int layoutId) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.layoutId = layoutId;
    }

    public void setListener(BaseItemListener listener) {
        this.listener = listener;
    }

    public void setData(List<T> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public BaseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, layoutId, parent, false);
        return new BaseHolder(binding);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public void onBindViewHolder(@NonNull BaseAdapter.BaseHolder holder, int position) {
        T item = data.get(position);
        holder.binding.setVariable(com.t3h.mp3music.BR.item, item);
//        holder.binding.setVariable(com.t3h.mp3music.BR.listener, listener);
        holder.binding.executePendingBindings();
        holder.itemView.setOnClickListener(view -> {
            listener.onItemClick(position);
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onItemLongClick(position);
                return false;
            }
        });

    }

    public class BaseHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding binding;

        public BaseHolder(@NonNull ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

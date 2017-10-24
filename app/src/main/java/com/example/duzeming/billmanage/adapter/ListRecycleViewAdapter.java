package com.example.duzeming.billmanage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duzeming.billmanage.R;
import com.example.duzeming.billmanage.utility.BillDataBaseMethod;

import java.util.List;

/**
 * Created by DuZeming on 2017/9/13.
 */
public class ListRecycleViewAdapter extends RecyclerView.Adapter<ListViewHolder> {

    private LayoutInflater inflater;
    private Context mContext;
    private List<String> reason;
    private List<String> time;
    private List<Double> money;
    private List<Integer> mode;
    private ListClickListener listener;
    private BillDataBaseMethod method;


    public ListRecycleViewAdapter(Context context, List<String> reason, List<String> time, List<Double> money
            , List<Integer> mode) {
        this.mContext = context;
        this.reason = reason;
        this.time = time;
        this.money = money;
        this.mode = mode;
        inflater = LayoutInflater.from(context);
        method = new BillDataBaseMethod(context);
    }

    public interface ListClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    public void setListClickListener(ListClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_list, parent, false);
        ListViewHolder holder = new ListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ListViewHolder holder, final int position) {
        holder.tvReason.setText(reason.get(position));
        holder.tvTime.setText(time.get(position).toString());
        holder.tvMoney.setText(money.get(position).toString());

        if (mode.get(position) == 0) {
            holder.ivMode.setImageResource(R.drawable.zhifubao);
        } else if (mode.get(position) == 1) {
            holder.ivMode.setImageResource(R.drawable.wechat);
        } else if (mode.get(position) == 2) {
            holder.ivMode.setImageResource(R.drawable.cash);
        } else {
            holder.ivMode.setImageResource(R.drawable.card);
        }
        if (listener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(holder.itemView,position);
                }
            });
//            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    listener.onItemLongClick(holder.itemView,position);
//                    return false;
//                }
//            });
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    method.deleteData(holder.getAdapterPosition());
//                    notifyItemRemoved(holder.getAdapterPosition());
//                    notifyDataSetChanged();
//                }
//            });
        }


    }

    @Override
    public int getItemCount() {
        return reason.size();
    }

    public void addData(int pos) {
        notifyItemInserted(pos);
    }

    public void removeData(int pos) {
        notifyItemRemoved(pos);
    }

}

class ListViewHolder extends RecyclerView.ViewHolder {

    TextView tvReason;
    TextView tvTime;
    TextView tvMoney;
    ImageView ivMode;

    public ListViewHolder(View itemView) {
        super(itemView);
        tvReason = (TextView) itemView.findViewById(R.id.tv_item_reason);
        tvTime = (TextView) itemView.findViewById(R.id.tv_item_time);
        tvMoney = (TextView) itemView.findViewById(R.id.tv_item_money);
        ivMode = (ImageView) itemView.findViewById(R.id.iv_item_mode);
    }
}
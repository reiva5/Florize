package com.findachan.florize.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.findachan.florize.DownloadImageTask;
import com.findachan.florize.R;
import com.findachan.florize.Util;
import com.findachan.florize.activity.DetailActivity;
import com.findachan.florize.models.Bouquet;

import java.util.List;

/**
 * Created by Finda on 21/02/2018.
 */

public class BouquetAdapter extends RecyclerView.Adapter<BouquetAdapter.MyViewHolder> {

    private Context mContext;
    private List<Bouquet> bouquetList;

    public BouquetAdapter(Context mContext, List<Bouquet> bouquetList) {
        this.mContext = mContext;
        this.bouquetList = bouquetList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bouquet_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Util util = new Util();
        Bouquet album = bouquetList.get(position);
        holder.title.setText(album.getName());
        holder.count.setText(util.toPrettyPrice(new Long(album.getPrice())));
        holder.id = album.getId();
        holder.name = album.getName();
        holder.url = album.getUrl();
        holder.price = album.getPrice();

        System.err.println("> AJG: " + holder.id);
        // loading album cover using Glide library
        Glide.with(mContext).load(album.getUrl()).into(holder.thumbnail);
//        holder.overflow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showPopupMenu(holder.overflow);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return bouquetList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title, count;
        private ImageView thumbnail, overflow;
        private String id;
        private String name;
        private String url;
        private Integer price;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
//            overflow = (ImageView) view.findViewById(R.id.overflow);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("Asoe kalian semua " + price);
                    Intent myIntent = new Intent(view.getContext(),DetailActivity.class);
                    myIntent.putExtra("id", id);
                    myIntent.putExtra("name", name);
                    myIntent.putExtra("url", url);
                    myIntent.putExtra("price", String.valueOf(price));
                    view.getContext().startActivity(myIntent);
                }
            });
        }
    }

}

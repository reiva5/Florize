package com.findachan.florize.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.findachan.florize.R;
import com.findachan.florize.models.Type;
import com.findachan.florize.activity.HandBouquetActivity;

import java.util.ArrayList;

/**
 * Created by Finda on 20/02/2018.
 */

public class TypesAdapter extends RecyclerView.Adapter<TypesAdapter.ViewHolder>  {

    private ArrayList<Type> mTypesData;
    private Context mContext;

    public TypesAdapter(Context context, ArrayList<Type> typesData) {
        this.mTypesData = typesData;
        this.mContext = context;
    }

    @Override
    public TypesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_type, parent, false));
    }

    @Override
    public void onBindViewHolder(TypesAdapter.ViewHolder holder, int position) {
        Type currentType = mTypesData.get(position);

        holder.bindTo(currentType);

        Glide.with(mContext).load(currentType.getImageResource()).into(holder.mTypesImage);
    }

    @Override
    public int getItemCount() {
        return mTypesData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitleText;
        private TextView mInfoText;
        private ImageView mTypesImage;

        ViewHolder(final View itemView) {
            super(itemView);

            mTitleText = (TextView)itemView.findViewById(R.id.title);
            mInfoText = (TextView)itemView.findViewById(R.id.subTitle);
            mTypesImage = (ImageView) itemView.findViewById(R.id.typesImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemView.getContext().startActivity(new Intent(itemView.getContext(),HandBouquetActivity.class));
                }
            });
        }

        void bindTo(Type currentSport){
            mTitleText.setText(currentSport.getTitle());
            mInfoText.setText(currentSport.getInfo());
        }
    }
}

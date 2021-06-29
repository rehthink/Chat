package com.example.chat.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.chat.R;
import com.example.chat.Models.SendMessageModel;

import java.util.List;

public class UserProfileDetailsAdapter extends RecyclerView.Adapter<UserProfileDetailsAdapter.MyViewHolder> {
    private Context aCtx;
    private List<SendMessageModel> list;

    public UserProfileDetailsAdapter(Context aCtx, List<SendMessageModel> list)
    {
        this.aCtx=aCtx;
        this.list=list;
    }

    @NonNull
    @Override
    public UserProfileDetailsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(aCtx).inflate(R.layout.card_imageview, parent, false);
        return new UserProfileDetailsAdapter.MyViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull UserProfileDetailsAdapter.MyViewHolder holder, int position) {

        SendMessageModel model = list.get(position);
        if (model.getType().equals("image")) {
            System.out.println(list.size());
            RequestOptions myOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(aCtx)
                    .load(model.getMsg_img())
                    .thumbnail(0.05f)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .apply(myOptions)
                    .into(holder.img);
        }
        else{
            holder.card.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        CardView card;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.msg_img);
            card = itemView.findViewById(R.id.card_image);

        }
    }
}


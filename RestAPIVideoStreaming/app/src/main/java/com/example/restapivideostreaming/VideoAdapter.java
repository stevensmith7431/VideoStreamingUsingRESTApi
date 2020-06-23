package com.example.restapivideostreaming;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private List<Video> allvideo;
    private Context context;

    public VideoAdapter(List<Video> allvideo, Context context) {
        this.allvideo = allvideo;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitems,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.title.setText(allvideo.get(position).getTitle());
        Picasso.get().load(allvideo.get(position).getImageurl()).into(holder.videoImage);

        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle b = new Bundle();
                b.putSerializable("allvideos",allvideo.get(position));
                Intent intent = new Intent(context,Player.class);
                intent.putExtras(b);
                view.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return allvideo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView videoImage;
        TextView title;
        View v;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            videoImage = itemView.findViewById(R.id.imageView);
            title = itemView.findViewById(R.id.textView);
            v = itemView;
        }
    }
}

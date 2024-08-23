package com.example.trans_brat;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private final Context context;
    private final List<Uri> imageUris;

    public ImageAdapter(Context context, List<Uri> imageUris) {
        this.context = context;
        this.imageUris = imageUris;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Uri imageUri = imageUris.get(position);
        Glide.with(context).load(imageUri).into(holder.imageView);

        try {
            holder.buttonRemove.setOnClickListener(v -> {
                imageUris.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, imageUris.size());
            });
        } catch(Exception e) {
            Toast.makeText(context.getApplicationContext(), "Erro: " + e, Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public int getItemCount() {
        return imageUris.size();
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ImageButton buttonRemove;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            buttonRemove = itemView.findViewById(R.id.button_remove);
        }
    }
}


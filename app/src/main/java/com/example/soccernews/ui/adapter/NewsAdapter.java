package com.example.soccernews.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soccernews.R;
import com.example.soccernews.databinding.NewsItemBinding;
import com.example.soccernews.domain.News;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private final List<News> news;
    private final FavoriteListener favoriteListener;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final NewsItemBinding binding;

        public ViewHolder(@NonNull NewsItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public NewsAdapter (List<News> news, FavoriteListener favoriteListener) {
        this.news = news;
        this.favoriteListener = favoriteListener;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        NewsItemBinding binding = NewsItemBinding.inflate(layoutInflater, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Context context = holder.itemView.getContext();

        News news = this.news.get(position);
        Picasso.get().load(news.getImage()).fit().into(holder.binding.ivNewsItem);
        holder.binding.mtvTitle.setText(news.getTitle());
        holder.binding.mtvDescription.setText(news.getDescription());
        Picasso.get().load(news.getImage()).fit().into(holder.binding.ivNewsItem);
        //Implementação da funcionalidade de "Abrir Link":
        holder.binding.mbOpenLink.setOnClickListener(v -> {
            Intent view = new Intent();
            view.setAction(Intent.ACTION_VIEW);
            view.setData(Uri.parse(news.getLink()));
            context.startActivity(view);
        });
        //Implementação da funcionalidade de "Compartilhar":
        holder.binding.ibShare.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_TEXT, news.getLink());
            context.startActivity(Intent.createChooser(i,"Share"));
        });
        //Implementação da funcionalidade de "Favoritar"(O envento será instanciado pelo Fragment)
        holder.binding.ibFavorite.setOnClickListener(v -> {
            news.favorites = !news.favorites;
            this.favoriteListener.onFavorite(news);
            notifyItemChanged(position);
        });
        int favoriteColor = news.favorites ? R.color.favorite_active : R.color.favorite_inactive;
        holder.binding.ibFavorite.setColorFilter(context.getResources().getColor(favoriteColor));
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public interface FavoriteListener {
        void onFavorite(News news);
    }
}

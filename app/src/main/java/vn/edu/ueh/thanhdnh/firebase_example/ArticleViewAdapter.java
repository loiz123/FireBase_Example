package vn.edu.ueh.thanhdnh.firebase_example;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ArticleViewAdapter extends RecyclerView.Adapter<ArticleViewHolder> {
    private LayoutInflater mInflater;
    private List<Article> articleList;
    private final ExecutorService executorService = Executors.newFixedThreadPool(4);
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    public ArticleViewAdapter(Context context, List<Article> articleList) {
        this.mInflater = LayoutInflater.from(context);
        this.articleList = articleList;
    }

    public void update(List<Article> articleList) {
        this.articleList = articleList;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View customView = mInflater.inflate(R.layout.article_list, parent, false);
        return new ArticleViewHolder(customView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        Article currentArticle = articleList.get(position);
        holder.getTxtId().setText(currentArticle.getId());
        holder.getTxtTitle().setText(currentArticle.getTitle());
        holder.getTxtContent().setText(currentArticle.getContent());

        String imageUrl = currentArticle.getImage_url();
        if (imageUrl != null && !imageUrl.trim().isEmpty()) {
            holder.getTxtImageUrl().setText(imageUrl);
            holder.getImgArticle().setVisibility(View.VISIBLE);
            holder.getImgArticle().setImageDrawable(null);
            holder.getImgArticle().setTag(imageUrl);

            executorService.execute(() -> {
                try {
                    URL url = new URL(imageUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(input);
                    mainHandler.post(() -> {
                        if (imageUrl.equals(holder.getImgArticle().getTag())) {
                            holder.getImgArticle().setImageBitmap(bitmap);
                        }
                    });
                } catch (Exception ignored) {
                }
            });
        } else {
            holder.getTxtImageUrl().setText("");
            holder.getImgArticle().setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return articleList != null ? articleList.size() : 0;
    }
}

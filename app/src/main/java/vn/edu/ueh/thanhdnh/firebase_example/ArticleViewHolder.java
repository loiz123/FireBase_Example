package vn.edu.ueh.thanhdnh.firebase_example;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ArticleViewHolder extends RecyclerView.ViewHolder {
    private TextView txtId, txtTitle, txtContent, txtImageUrl;
    private ImageView imgArticle;
    private ArticleViewAdapter adapter;

    public ArticleViewHolder(@NonNull View itemView, ArticleViewAdapter adapter) {
        super(itemView);
        this.adapter = adapter;
        txtId = itemView.findViewById(R.id.txt_id);
        txtTitle = itemView.findViewById(R.id.txt_title);
        txtContent = itemView.findViewById(R.id.txt_content);
        txtImageUrl = itemView.findViewById(R.id.txt_image_url);
        imgArticle = itemView.findViewById(R.id.img_article);
    }

    public TextView getTxtId() {
        return txtId;
    }

    public TextView getTxtTitle() {
        return txtTitle;
    }

    public TextView getTxtContent() {
        return txtContent;
    }

    public TextView getTxtImageUrl() {
        return txtImageUrl;
    }

    public ImageView getImgArticle() {
        return imgArticle;
    }
}

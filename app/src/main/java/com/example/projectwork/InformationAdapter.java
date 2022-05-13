package com.example.projectwork;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class InformationAdapter extends RecyclerView.Adapter<InformationAdapter.ViewHolder> {
    Context context;
    List<InformationModel>  informationModelList;

    public InformationAdapter(Context context, List<InformationModel> informationModelList) {
        this.context = context;
        this.informationModelList = informationModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.design_row_recyclerview,parent,false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        InformationModel informationModel=informationModelList.get(position);
        holder.tvDesc.setText("Description - "+informationModel.getDescription());
        holder.tvName.setText("Name - "+informationModel.getName());



        String imageUri=null;
        imageUri=informationModel.getImage();
        Picasso.get()
                .load(imageUri)
                .into(holder.imageView);

        String finalImageUri = imageUri;
        holder.Download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownloadManager downloadManager=(DownloadManager) context
                        .getSystemService(context.DOWNLOAD_SERVICE);
                Uri uri=Uri.parse(finalImageUri);
                DownloadManager.Request request=new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "name-of-the-file.jpg");
                downloadManager.enqueue(request);

            }
        });

    }

    @Override
    public int getItemCount() {
        return informationModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView Download;

        ImageView imageView;
        TextView tvName,tvDesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Download=itemView.findViewById(R.id.download);
            imageView=itemView.findViewById(R.id.image_view_upload);
            tvName=itemView.findViewById(R.id.text_view_name);
            tvDesc=itemView.findViewById(R.id.text_view_desc);


        }
    }
}

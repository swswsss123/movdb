package space.tarnovsky.moviedb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {


    private Context mContext;
    private List<MovieModeClass>mData;

    public Adapter(Context mContext, List<MovieModeClass> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        v =inflater.inflate(R.layout.movie_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull  Adapter.MyViewHolder holder, int position) {
        holder.id.setText(mData.get(position).getId());
        holder.name.setText(mData.get(position).getName());

        //  Что бы отобразить изображение
        // https://image.tmdb.org/t/p/w500/jTswp6KyDYKtvC52GbHagrZbGvD.jpg
        Glide.with(mContext)
                .load("https://image.tmdb.org/t/p/w500"+mData.get(position).getImg())
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView id;
        TextView name;
        ImageView img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id=itemView.findViewById(R.id.id_txt);
            name=itemView.findViewById(R.id.text_txt);
            img=itemView.findViewById(R.id.imageView);
        }
    }
}

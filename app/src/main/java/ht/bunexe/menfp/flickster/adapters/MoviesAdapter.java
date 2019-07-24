package ht.bunexe.menfp.flickster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ht.bunexe.menfp.flickster.DetailActivity;
import ht.bunexe.menfp.flickster.GlideApp;
import ht.bunexe.menfp.flickster.R;
import ht.bunexe.menfp.flickster.models.Movie;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MoviesAdapter extends  RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    Context context;
    List<Movie> movies;

    public MoviesAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view= LayoutInflater.from(context).inflate(R.layout.item_movie,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      Movie  movie = movies.get(position);
      holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
         // declaration des variables d'Objets
       /* TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;
        RelativeLayout container;
        */

        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvOverview)
        TextView tvOverview;
        @BindView(R.id.ivPoster)
        ImageView ivPoster;
        @BindView(R.id.container)
        RelativeLayout container;



        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            /*tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            container = itemView.findViewById(R.id.container);
            */
        }

        public void bind(final Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String ImagePath=movie.getPoster_path();


            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                ImagePath=movie.getBackdrop_path();
            }
            int radius = 30; // corner radius, higher value = more rounded
            int margin = 1; // crop margin, set to 0 for corners with no crop
            GlideApp.with(context)
                    .load(ImagePath)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.bbnotfound)
                    .transform(new RoundedCornersTransformation(radius, margin))
                    .into(ivPoster);
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("movie", Parcels.wrap(movie));
                    context.startActivity(intent);
                    //Toast.makeText(context,movie.getTitle(),Toast.LENGTH_SHORT).show();
                }
            });
             //.transform(new RoundedCornersTransformation(radius, margin))
        }
    }
}

package org.richit.animal.Adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.richit.animal.Activities.AnimalDetailsActivity;
import org.richit.animal.Models.AnimalModel;
import org.richit.animal.Config;
import org.richit.animal.R;

import java.util.ArrayList;


public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.Holder> {

    Context context;
    ArrayList<AnimalModel> animals = new ArrayList<>();

    public AnimalAdapter(Context context, ArrayList<AnimalModel> animals) {
        this.context = context;
        this.animals = animals;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(context).inflate(R.layout.animal_list_2, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position) {
        holder.textViewName.setText(animals.get(position).getName());
        holder.textViewScientificName.setText(animals.get(position).getScientific());
        holder.textViewType.setText(animals.get(position).getConservation());

        if (animals.get(position).getImage().toLowerCase().contains("wikipedia")) {
            Picasso.get().load(animals.get(position).getImage())
                    .placeholder(R.drawable.loading)
                    .into(holder.imageViewAnimal);
        } else {
            Picasso.get().load(Config.animalImagePath + animals.get(position).getImage())
                    .placeholder(R.drawable.loading)
                    .into(holder.imageViewAnimal);
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AnimalDetailsActivity.class);
                intent.putExtra("animalTypes", animals.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return animals.size();
    }

    public void setAnimals(ArrayList<AnimalModel> animals) {
        this.animals = animals;
        notifyDataSetChanged();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewScientificName, textViewType;
        CardView cardView;
        ImageView imageViewAnimal;

        public Holder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.animalName);
            textViewScientificName = itemView.findViewById(R.id.scientificName);
            textViewType = itemView.findViewById(R.id.animalType);
            imageViewAnimal = itemView.findViewById(R.id.animalImage);
            cardView = itemView.findViewById(R.id.animalLaunch);

        }
    }
}

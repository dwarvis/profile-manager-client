package edu.cis.clientapp.Model;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.cis.clientapp.R;

public class CISBookViewHolder extends RecyclerView.ViewHolder
{
    protected TextView nameText;
    protected TextView statusText;
    protected ImageView image;

    public CISBookViewHolder(@NonNull View itemView) {
        super(itemView);

        nameText = itemView.findViewById(R.id.nameTextView);
        statusText = itemView.findViewById(R.id.statusTextView);
        image = itemView.findViewById(R.id.imageView);
    }
}

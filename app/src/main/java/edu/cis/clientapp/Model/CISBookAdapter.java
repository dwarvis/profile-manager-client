package edu.cis.clientapp.Model;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.cis.clientapp.R;

/**
 * Got code for recycler list adapter at
 * https://www.androidauthority.com/how-to-use-recycler-views-836053/
 * and
 * https://www.youtube.com/watch?v=eJZmt3BTI2k
**/

public class CISBookAdapter extends RecyclerView.Adapter<CISBookViewHolder>
{

    ArrayList<Row> mData;

    public CISBookAdapter(ArrayList data) {
        mData = data;
    }


    @NonNull
    @Override
    public CISBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row,
                                                                        parent,
                                                            false);
        CISBookViewHolder holder = new CISBookViewHolder(myView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CISBookViewHolder holder, int position)
    {
        holder.nameText.setText(mData.get(position).getName());
//        holder.statusText.setText(mData.get(position).getName());
        holder.statusText.setText("bruh");
        holder.image.setImageBitmap(mData.get(position).getImage());
    }

    @Override
    public int getItemCount()
    {
        if (mData != null)
        {
            return mData.size();
        }
        return 0;
    }
}
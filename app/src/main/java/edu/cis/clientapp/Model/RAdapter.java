package edu.cis.clientapp.Model;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import edu.cis.clientapp.R;

/**
 * Got code for recycler list adapter at
 * https://www.androidauthority.com/how-to-use-recycler-views-836053/
**/

public class RAdapter extends RecyclerView.Adapter<RAdapter.ViewHolder>
{

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public ConstraintLayout row;
        public TextView textView;
        public ImageView img;

        public ViewHolder(View itemView)
        {
            super(itemView);

            row = (ConstraintLayout) itemView.findViewById(R.id.a_row);
            textView = (TextView) itemView.findViewById(R.id.profileNameTextView);
            img = (ImageView) itemView.findViewById(R.id.profileImageView);
        }
    }

    ArrayList<String> nmList;

    public RAdapter(Context c, ArrayList<String> nameList)
    {
        nmList = nameList;
    }

    @Override
    public void onBindViewHolder(RAdapter.ViewHolder viewHolder, int i)
    {
        TextView textView = viewHolder.textView;
        textView.setText(nmList.get(i));
    }

    @Override
    public int getItemCount()
    {
        if (nmList != null)
        {
            return nmList.size();
        }
        return 0;
    }


    @Override
    public RAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.row, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public RAdapter.ViewHolder CreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.row, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
}
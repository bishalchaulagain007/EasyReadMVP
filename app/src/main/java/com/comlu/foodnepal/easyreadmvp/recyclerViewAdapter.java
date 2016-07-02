package com.comlu.foodnepal.easyreadmvp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;


//increases performance..better than ListView...once casted it wont have to be casted in Id again\
//The term recycler defines itself

public class recyclerViewAdapter extends RecyclerView.Adapter<recyclerViewAdapter.viewHolder> {


    //this array passes data to the nav drawer item
    List<NavDrawerItems> itemsData = Collections.emptyList();

    private LayoutInflater inflater;
    private Context context;

    public recyclerViewAdapter(Context context, List<NavDrawerItems> itemsData) {

        this.context = context;
        inflater = LayoutInflater.from(context);
        this.itemsData = itemsData;


    }

    //deleting the items in the recycler view

    public void delete(int position) {

        itemsData.remove(position);
        notifyItemRemoved(position);
    }


    //every time a new row is to be displayed this below method will be called

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        //get the layout file...inflate it and give it to the view holder...it avoids using findViewById every time and
        // increases performance
        View view = inflater.inflate(R.layout.recycler_view_layout, parent, false);

        viewHolder Holder = new viewHolder(view);

        return Holder;

    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {

        //fill the data items here

        NavDrawerItems current = itemsData.get(position);

        holder.listText.setText(current.title);
        holder.listDesc.setText(current.titleDesc);
        holder.listIcon.setImageResource(current.iconId);

   }

    @Override
    public int getItemCount() {
        return itemsData.size();
    }

    class viewHolder extends RecyclerView.ViewHolder  {


        TextView listText;
        TextView listDesc;
        ImageView listIcon;
        //constructor

        public viewHolder(View itemView) {

            super(itemView);

            //reference
            listText = (TextView) itemView.findViewById(R.id.listText);
            listDesc = (TextView) itemView.findViewById(R.id.listDesc);
            listIcon = (ImageView) itemView.findViewById(R.id.listIcon);


        }



    }

}

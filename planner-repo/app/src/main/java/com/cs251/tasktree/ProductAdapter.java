package com.cs251.tasktree;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductAdapter extends  RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private static int mExpandedPosition = -1;
    private Context mCtx;
    private List<Product> productList;
//    private onItemClickListener mListner;
//
//    public interface onItemClickListener{
//        void onItemClick(int position);
//    }
//    public void setOnItemClickListner(onItemClickListener listner){
//        mListner=listner;
//    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView task_name_text,task_date_text,task_description_text,task_subtask_text;
        Button edit_button;

        public ProductViewHolder( View itemView) {
            super(itemView);

            task_name_text=itemView.findViewById(R.id.task_name);
            task_date_text=itemView.findViewById(R.id.task_date);
            task_description_text=itemView.findViewById(R.id.task_description);
            task_subtask_text=itemView.findViewById(R.id.task_subtasks);
            edit_button=itemView.findViewById(R.id.task_edit);

//            itemView.setOnClickListener(new View.OnClickListener(){
//                @Override
//                public void onClick(View v){
//                    if(mListner != null){
//                        int position =getAdapterPosition();
//                        if(position != RecyclerView.NO_POSITION){
//                            mListner.onItemClick(position);
//                        }
//                    }
//                }
//            });
        }
    }



    public ProductAdapter(Context mCtx, List<Product> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }


    @Override
    public ProductViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(mCtx);
        View view=inflater.inflate(R.layout.list_layout_main,null);
        ProductViewHolder holder = new ProductViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder( ProductViewHolder holder,final int position) {
        final Product product=productList.get(position);
        holder.task_name_text.setText(product.getTask_name());
        holder.task_date_text.setText(product.getTask_date());
        holder.task_description_text.setText(product.getTask_description());
        holder.task_subtask_text.setText(product.gettask_subtasks());
        holder.edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx,AddTask.class);
                intent.putExtra("isUpdate", true);
                intent.putExtra("id",product.getidoftask());
                mCtx.startActivity(intent);
            }
        });
        final boolean isExpanded = position==mExpandedPosition;
        holder.task_subtask_text.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.task_description_text.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.edit_button.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.itemView.setActivated(isExpanded);
        holder.itemView.setOnTouchListener(new View.OnTouchListener() {
            private GestureDetector gestureDetector = new GestureDetector(mCtx, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    Log.d("TEST", "onDoubleTap");
                    Intent intent = new Intent(mCtx, EditTask.class);
                    Integer idtopass=product.getidoftask();
                    intent.putExtra("id", idtopass);
                    mCtx.startActivity(intent);
                    return super.onDoubleTap(e);
                }

                @Override
                public boolean onDoubleTapEvent(MotionEvent event) {
                    return true;
                }

                @Override
                public boolean onSingleTapConfirmed(MotionEvent e){
                    mExpandedPosition = isExpanded ? -1 : position;
                    notifyDataSetChanged();
                    return super.onSingleTapConfirmed(e);
                }
            });

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


}

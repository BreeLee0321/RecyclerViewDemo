package bree.com.recyclerviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class BaseActivity extends Activity {
    public RecyclerView mRecyclerView;
    private List<String> mDatas;
    public static String type = "";
    public static HomeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        initData();
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
        setRecycler();
        mRecyclerView.setAdapter(adapter = new HomeAdapter());
        changeData();


    }


    public abstract void setRecycler();

    private void initData() {
        mDatas = new ArrayList<String>();
        for (int i = 'A'; i < 'z'; i++) {
            mDatas.add("" + (char) i);
        }
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

        private OnItemClickLitener mOnItemClickLitener;

        public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
            this.mOnItemClickLitener = mOnItemClickLitener;
        }


        @Override
        public HomeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(BaseActivity.this).inflate(R.layout.item, parent, false));
        }

        @Override
        public void onBindViewHolder(final HomeAdapter.MyViewHolder holder, int position) {
            if (type.equals("3")) {
                FrameLayout.LayoutParams linearParams = (FrameLayout.LayoutParams) holder.tv.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20

                linearParams.height = (int) (Math.random() * 300);// 控件的宽强制设成30

                holder.tv.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
            }
            holder.tv.setText(mDatas.get(position));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv;

            public MyViewHolder(View itemView) {
                super(itemView);
                tv = (TextView) itemView.findViewById(R.id.id_num);
            }
        }

        public void addData(int positon) {
            mDatas.add(positon, "add");
            notifyItemInserted(positon);
        }

        public void removeData(int position) {
            mDatas.remove(position);
            notifyItemRemoved(position);
        }

    }

    private void changeData() {
        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.addData(1);
            }
        });
        findViewById(R.id.remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.removeData(1);
            }
        });
    }
    public Toast toast=null;
    public void showToast(String con){

        if (toast==null){
            toast=Toast.makeText(this,con,Toast.LENGTH_SHORT);
        }else {
            toast.setText(con);
        }
        toast.show();
    }
}

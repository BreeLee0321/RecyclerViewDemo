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
        
        //添加头和脚
        setHeaderView(mRecyclerView);
        setFooterView(mRecyclerView);


    }


    public abstract void setRecycler();

    private void initData() {
        mDatas = new ArrayList<String>();
        for (int i = 'A'; i < 'z'; i++) {
            mDatas.add("" + (char) i);
        }
    }

    public void setHeaderView(RecyclerView headerView) {
        View header = LayoutInflater.from(this).inflate(R.layout.header, headerView, false);
        adapter.setHeaderView(header);
    }

    public void setFooterView(RecyclerView footerView) {
        View footer = LayoutInflater.from(this).inflate(R.layout.footer, footerView, false);
        adapter.setFooterView(footer);
    }


    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
        public static final int TYPE_HEADER = 0;  //说明是带有Header的
        public static final int TYPE_FOOTER = 1;  //说明是带有Footer的
        public static final int TYPE_NORMAL = 2;  //说明是不带有header和footer的

        public View getHeaderView() {
            return mHeaderView;
        }

        public void setHeaderView(View mHeaderView) {
            this.mHeaderView = mHeaderView;
            notifyItemInserted(0);
        }

        public View getFooterView() {
            return mFooterView;
        }

        public void setFooterView(View mFooterView) {
            this.mFooterView = mFooterView;
            notifyItemInserted(getItemCount()-1);
        }

        private View mHeaderView;
        private View mFooterView;

        private OnItemClickLitener mOnItemClickLitener;

        public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
            this.mOnItemClickLitener = mOnItemClickLitener;
        }

        @Override
        public int getItemViewType(int position) {
            if (mHeaderView == null && mFooterView == null){
                return TYPE_NORMAL;
            }
            if (position == 0){
                //第一个item应该加载Header
                return TYPE_HEADER;
            }
            if (position == getItemCount()-1){
                //最后一个,应该加载Footer
                return TYPE_FOOTER;
            }
            return TYPE_NORMAL;
        }

        @Override
        public HomeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if(mHeaderView != null && viewType == TYPE_HEADER) {
                return new MyViewHolder(mHeaderView);
            }
            if(mFooterView != null && viewType == TYPE_FOOTER){
                return new MyViewHolder(mFooterView);
            }
            View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
            return new MyViewHolder(layout);
        }

        @Override
        public void onBindViewHolder(final HomeAdapter.MyViewHolder holder, int position) {

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

            if(getItemViewType(position) == TYPE_NORMAL){
                if(holder instanceof MyViewHolder) {
                    //这里加载数据的时候要注意，是从position-1开始，因为position==0已经被header占用了
                    ((MyViewHolder) holder).tv.setText(mDatas.get(position-1));
                    if (type.equals("3")) {
                        FrameLayout.LayoutParams linearParams = (FrameLayout.LayoutParams) holder.tv.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20

                        linearParams.height = (int) (Math.random() * 300);// 控件的宽强制设成30

                        holder.tv.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
                    }
                    return;
                }
                return;
            }else if(getItemViewType(position) == TYPE_HEADER){
                return;
            }else{
                return;
            }

        }

        @Override
        public int getItemCount() {
            if(mHeaderView == null && mFooterView == null){
                return mDatas.size();
            }else if(mHeaderView == null && mFooterView != null){
                return mDatas.size() + 1;
            }else if (mHeaderView != null && mFooterView == null){
                return mDatas.size() + 1;
            }else {
                return mDatas.size() + 2;
            }
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv;

            public MyViewHolder(View itemView) {
                super(itemView);
                if (itemView == mHeaderView){
                    return;
                }
                if (itemView == mFooterView){
                    return;
                }
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

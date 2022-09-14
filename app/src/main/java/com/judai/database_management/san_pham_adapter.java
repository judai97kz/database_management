package com.judai.database_management;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class san_pham_adapter extends BaseAdapter {
    private Context context;
    private List<SanPham> listSP;
    private int layout;
    private SanPham SPs;

    public san_pham_adapter(Context context, List<SanPham> listSP, int layout) {
        this.context = context;
        this.listSP = listSP;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return listSP.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder{
        TextView tvname,tvamout,tvprice,tvsum;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);

            holder = new ViewHolder();
            holder.tvname = view.findViewById(R.id.namesp);
            holder.tvamout = view.findViewById(R.id.amoutSP);
            holder.tvprice = view.findViewById(R.id.priceSP);
            holder.tvsum = view.findViewById(R.id.sumSP);
            view.setTag(holder);
        }
        else{
            holder = (ViewHolder) view.getTag();
        }

        SPs = listSP.get(i);
        holder.tvname.setText("Tên Sản Phẩm : " + SPs.get_nameSP());
        holder.tvprice.setText("Đơn giá : "+SPs.get_priceSP());
        holder.tvamout.setText("Số lượng (còn lại) : "+SPs.get_amoutSP());
        int sum = SPs.get_amoutSP()*SPs.get_priceSP();
        holder.tvsum.setText("Tổng tiền vốn : " + sum);
        return view;
    }
}

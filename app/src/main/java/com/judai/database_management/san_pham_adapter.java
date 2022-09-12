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

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout,null);
        TextView tvname = view.findViewById(R.id.namesp);
        TextView tvamout = view.findViewById(R.id.amoutSP);
        TextView tvprice = view.findViewById(R.id.priceSP);
        TextView tvsum = view.findViewById(R.id.sumSP);

        SPs = listSP.get(i);
        tvname.setText("Tên Sản Phẩm : " + SPs.get_nameSP());
        tvprice.setText("Đơn giá : "+SPs.get_priceSP());
        tvamout.setText("Số lượng (còn lại) : "+SPs.get_amoutSP());
        int sum = SPs.get_amoutSP()*SPs.get_priceSP();
        tvsum.setText("Tổng tiền vốn : " + sum);
        return view;
    }
}

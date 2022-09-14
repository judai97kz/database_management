package com.judai.database_management;

import android.content.Context;
import android.view.ContentInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class custom_item_danh_muc extends BaseAdapter {

    private List<DanhMuc> _listDM;
    private Context _context;
    private int _layout;

    public custom_item_danh_muc(List<DanhMuc> _listDM, Context _context, int _layout) {
        this._listDM = _listDM;
        this._context = _context;
        this._layout = _layout;
    }

    @Override
    public int getCount() {
        return _listDM.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public class ViewHolder{
        TextView name_DM;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null){
            LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(_layout,null);

            holder = new ViewHolder();
            holder.name_DM = view.findViewById(R.id.nameDM);
            view.setTag(holder);
        }
        else{
            holder = (ViewHolder) view.getTag();
        }


        DanhMuc dm = _listDM.get(i);
        holder.name_DM.setText(dm.get_nameDM());
        return view;
    }
}

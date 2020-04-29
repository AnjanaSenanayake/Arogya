package lk.gov.arogya.support;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import lk.gov.arogya.R;

public class HashMapAdapter extends BaseAdapter {

    private final ArrayList mData;

    public HashMapAdapter(Map<Integer, String> map) {
        Map<Integer,String> treeMap = new TreeMap<>(map);
        mData = new ArrayList();
        mData.addAll(treeMap.entrySet());
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Map.Entry<String, String> getItem(int position) {
        return (Map.Entry) mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View result;

        if (convertView == null) {
            result = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drop_down_list, parent, false);
        } else {
            result = convertView;
        }

        Map.Entry<String, String> item = getItem(position);

        // TODO replace findViewById by ViewHolder
        ((TextView) result.findViewById(R.id.tv_item_value)).setText(
                String.format("%s - %s", item.getKey(), item.getValue()));

        return result;
    }
}

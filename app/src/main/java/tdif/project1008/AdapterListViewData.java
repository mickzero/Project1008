package tdif.project1008;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by 58010461 on 11/13/2017.
 */

public class AdapterListViewData extends BaseAdapter {

    private LayoutInflater mInflater;
    private Context context;
    private MainActivity control;
    private ArrayList<StData> listData = new ArrayList<StData>();

    public AdapterListViewData(MainActivity control, ArrayList<StData> listData) {
        this.control = control;
        this.context = control.getBaseContext();
        this.mInflater = LayoutInflater.from(context);
        this.listData = listData;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HolderListAdapter holderListAdapter;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.adapter_listview, null);
            holderListAdapter = new HolderListAdapter();

            holderListAdapter.txtID = (TextView) convertView.findViewById(R.id.txtID);
            holderListAdapter.txtName = (TextView) convertView.findViewById(R.id.txtName);
            holderListAdapter.txtGender = (TextView) convertView.findViewById(R.id.txtGender);
            holderListAdapter.txtEmail = (TextView) convertView.findViewById(R.id.txtEmail);
            holderListAdapter.txtBY = (TextView) convertView.findViewById(R.id.txtBY);
            holderListAdapter.btnEdit = (Button) convertView.findViewById(R.id.btnEdit);
            holderListAdapter.btnDelete = (Button) convertView.findViewById(R.id.btnDelete);
            convertView.setTag(holderListAdapter);
        } else {
            holderListAdapter = (HolderListAdapter) convertView.getTag();
        }

        final String id = listData.get(position).getId();
        final String name = listData.get(position).getName();
        final String gender = listData.get(position).getGender();
        final String email = listData.get(position).getEmail();
        final String by = listData.get(position).getBy();
        holderListAdapter.txtID.setText("ID : "+ id);
        holderListAdapter.txtName.setText("Name : " + name);
        holderListAdapter.txtGender.setText("Gender : " + gender);
        holderListAdapter.txtEmail.setText("Email : " + email);
        holderListAdapter.txtBY.setText("BirthYear : " + by);
        holderListAdapter.btnDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                control.deleteProduct(id);
            }
        });

        holderListAdapter.btnEdit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                control.showEdit(id, name, gender, email, by);
            }
        });
        return convertView;
    }
}

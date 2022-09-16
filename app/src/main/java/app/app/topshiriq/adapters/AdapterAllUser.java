package app.app.topshiriq.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;
import java.util.Objects;

import app.app.topshiriq.R;
import app.app.topshiriq.model.Alldata;

public class AdapterAllUser extends BaseAdapter {
    private List<Alldata> itemsModelsl;
    private List<Alldata> itemsModelListFiltered;
    private Context context;

    public AdapterAllUser(List<Alldata> itemsModelsl, Context context) {
        this.itemsModelsl = itemsModelsl;
        this.itemsModelListFiltered = itemsModelsl;
        this.context = context;
    }

    @Override
    public int getCount() {
        return itemsModelListFiltered.size();
    }

    @Override
    public Object getItem(int position) {
        return itemsModelListFiltered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        }
        Alldata dataModal = (Alldata) getItem(position);
        TextView nameTV = listitemView.findViewById(R.id.txtitemname);
        TextView idtxt = listitemView.findViewById(R.id.txtitemrole);
        nameTV.setText(dataModal.getNumber() + " ---- " + dataModal.getName());
        if (dataModal.getRole().equals("0")) {
            idtxt.setText(dataModal.getRole()+" - Admin");
        }
        if (dataModal.getRole().equals("1")) {
            idtxt.setText(dataModal.getRole()+" - "+context.getString(R.string.admin));
        }
        if (dataModal.getRole().equals("2")) {
            idtxt.setText(dataModal.getRole()+" - "+context.getString(R.string.shop_assistant));
        }
        if (dataModal.getRole().equals("3")) {
            idtxt.setText(dataModal.getRole()+" - "+context.getString(R.string.accountant));
        }
        listitemView.setOnClickListener(v -> {
            final BottomSheetDialog dialog = new BottomSheetDialog(context, R.style.custombottomsheet);
            dialog.setContentView(R.layout.bottomsheet);
            TextView txtname = dialog.findViewById(R.id.textView);
            TextView txtrole = dialog.findViewById(R.id.textView2);

            Objects.requireNonNull(txtname).setText(dataModal.getName());
            if (dataModal.getRole().equals("0")) {
                Objects.requireNonNull(txtrole).setText(dataModal.getRole()+" - Admin");
            }
            if (dataModal.getRole().equals("1")) {
                Objects.requireNonNull(txtrole).setText(dataModal.getRole()+" - "+context.getString(R.string.admin));
            }
            if (dataModal.getRole().equals("2")) {
                Objects.requireNonNull(txtrole).setText(dataModal.getRole()+" - "+context.getString(R.string.shop_assistant));
            }
            if (dataModal.getRole().equals("3")) {
                Objects.requireNonNull(txtrole).setText(dataModal.getRole()+" - "+context.getString(R.string.accountant));
            }

            dialog.show();
        });

        return listitemView;
    }

}

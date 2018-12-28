package resende.alan.app4all.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import resende.alan.app4all.R;
import resende.alan.app4all.model.Comentario;
import resende.alan.app4all.util.RoundedTransformation;

public class customAdapter extends BaseAdapter {

    private ArrayList<Comentario> comm;
    private Context context;

    private static class ViewHolder{
        TextView txtTitulo, txtNome, txtComm;
        ImageView imgComm;
        RatingBar nota;
    }

    public customAdapter(ArrayList<Comentario> data, Context context){
        this.comm = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return comm.size();
    }

    @Override
    public Object getItem(int position) {
        return comm.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View converterView, ViewGroup parent){
        ViewHolder viewHolder;

        Log.i("log position", String.valueOf(position));

        final View result;
        if(converterView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            converterView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.txtNome = converterView.findViewById(R.id.txtNomeComm);
            viewHolder.txtTitulo = converterView.findViewById(R.id.txtTituloComm);
            viewHolder.txtComm = converterView.findViewById(R.id.txtComm);
            viewHolder.imgComm = converterView.findViewById(R.id.imgComm);
            viewHolder.nota = converterView.findViewById(R.id.nota);

            result = converterView;
            converterView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) converterView.getTag();
            result = converterView;
        }

        viewHolder.txtNome.setText(comm.get(position).getNome());
        viewHolder.txtTitulo.setText(comm.get(position).getTitulo());
        viewHolder.txtComm.setText(comm.get(position).getComentario());
        viewHolder.nota.setRating(comm.get(position).getNota());

        Picasso.get()
                .load(comm.get(position).getUrlFoto())
                .transform(new RoundedTransformation(100,0))
                .into(viewHolder.imgComm);

        return converterView;
    }
}
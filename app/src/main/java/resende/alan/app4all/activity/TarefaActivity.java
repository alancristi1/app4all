package resende.alan.app4all.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.squareup.picasso.Picasso;

import resende.alan.app4all.R;
import resende.alan.app4all.model.tarefa;
import resende.alan.app4all.ws.EndPoints;
import resende.alan.app4all.ws.HeaderRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class TarefaActivity extends AppCompatActivity {

    GoogleMap googleMap;
    TextView txtCidade, txtTitulo, txtTexto, txtEndMapa;
    ImageView imgTarefa, imgLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarefa);

        txtCidade = findViewById(R.id.txtCidade);
        txtTitulo = findViewById(R.id.txtTitulo);
        txtTexto = findViewById(R.id.txtTexto);
        txtEndMapa = findViewById(R.id.txtEndMapa);
        imgTarefa = findViewById(R.id.imgTarefa);
        imgLogo = findViewById(R.id.imgLogo);

        String id = getIntent().getExtras().getString("id");

        HeaderRetrofit header = new HeaderRetrofit();
        Retrofit retrofit = header.createInstanceRetrofit();

        EndPoints endPoints = retrofit.create(EndPoints.class);
        Call<tarefa> buscarTarefa = endPoints.getTarefa(id);
        buscarTarefa.enqueue(new Callback<tarefa>() {
            @Override
            public void onResponse(Call<tarefa> call, retrofit2.Response<tarefa> response) {
                int code = response.code();
                if(code == 200){
                    txtCidade.append(response.body().getCidade() + " - " + response.body().getBairro());
                    imgTarefa.setImageURI(Uri.parse(response.body().getUrlLogo()));
                    txtTitulo.setText(response.body().getTitulo());
                    txtTexto.setText(response.body().getTexto());
                    txtEndMapa.setText(response.body().getEndereco());

                    Picasso.get()
                            .load(response.body().getUrlFoto())
                            .into(imgTarefa);

                    Picasso.get()
                            .load(response.body().getUrlLogo())
                            .into(imgLogo);
                }
            }

            @Override
            public void onFailure(Call<tarefa> call, Throwable t) {

            }
        });
    }
}

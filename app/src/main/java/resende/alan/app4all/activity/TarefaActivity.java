package resende.alan.app4all.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

import resende.alan.app4all.R;
import resende.alan.app4all.model.tarefa;
import resende.alan.app4all.ws.EndPoints;
import resende.alan.app4all.ws.HeaderRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class TarefaActivity extends AppCompatActivity implements OnMapReadyCallback {


    GoogleMap maps;
    MapView mapView;
    TextView txtCidade, txtTitulo, txtTexto, txtEndMapa;
    ImageView imgTarefa, imgLogo;
    private static final String MAP_VIEW_BUNDLE_KEY = "AIzaSyC-ZeJshDV_7YJ3Yo4092UrxTVphG0U6Jk";

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
        mapView = findViewById(R.id.mapView);

        String id = getIntent().getExtras().getString("id");

        Bundle mapViewBundle = null;
        if(savedInstanceState != null){
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);

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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        maps = googleMap;
        maps.setMinZoomPreference(12);
        LatLng ny = new LatLng(40.7143528, -74.0059731);
        maps.moveCamera(CameraUpdateFactory.newLatLng(ny));
    }
}

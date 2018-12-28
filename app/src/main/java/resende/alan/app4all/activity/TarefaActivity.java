package resende.alan.app4all.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import resende.alan.app4all.R;
import resende.alan.app4all.adapter.customAdapter;
import resende.alan.app4all.model.tarefa;
import resende.alan.app4all.util.Database;
import resende.alan.app4all.util.shareds;
import resende.alan.app4all.ws.EndPoints;
import resende.alan.app4all.ws.HeaderRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class TarefaActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap maps;
    MapView mapView;
    TextView txtCidade, txtTitulo, txtTexto, txtEndMapa, txtNomeComm, txtTituloComm, txtComm,
        btnLigar, btnServico, btnEndereco, btnComentario;
    ImageView imgTarefa, imgLogo, imgComm, imgVoltar;
    RatingBar ratingnota;
    ListView listComm;
    ScrollView scroll;
    private static customAdapter adapter;
    private static final String MAP_VIEW_BUNDLE_KEY = "AIzaSyC-ZeJshDV_7YJ3Yo4092UrxTVphG0U6Jk";
    Double latitude, longitude;

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
        imgComm = findViewById(R.id.imgComm);
        txtNomeComm = findViewById(R.id.txtNomeComm);
        txtTituloComm = findViewById(R.id.txtTituloComm);
        txtComm = findViewById(R.id.txtComm);
        ratingnota = findViewById(R.id.nota);
        listComm = findViewById(R.id.listComm);
        imgVoltar = findViewById(R.id.imgVoltar);
        btnLigar = findViewById(R.id.btnLigar);
        btnServico = findViewById(R.id.btnServico);
        btnEndereco = findViewById(R.id.btnEndereco);
        btnComentario = findViewById(R.id.btnComentario);
        scroll = findViewById(R.id.scroll);

        btnComentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scroll.scrollTo(0, view.getBottom());
            }
        });

        btnServico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TarefaActivity.this, ServicosActivity.class);
                startActivity(intent);
            }
        });


        imgVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



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
            public void onResponse(Call<tarefa> call, final retrofit2.Response<tarefa> response) {
                int code = response.code();
                if(code == 200){

                    latitude = response.body().getLatitude();
                    longitude = response.body().getLongitude();

                    LatLng latLng = new LatLng(latitude, longitude);
                    Database database = new Database(getBaseContext());
                    database.gravarMarker(latLng);

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

                    btnEndereco.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final AlertDialog.Builder mensagem = new AlertDialog.Builder(TarefaActivity.this);
                            mensagem.setTitle("Endereço");
                            mensagem.setMessage(response.body().getEndereco());
                            mensagem.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }

                            });

                            mensagem.show();
                        }
                    });

                    btnLigar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Uri uri = Uri.parse("tel:"+response.body().getTelefone());
                            Intent intent = new Intent(Intent.ACTION_DIAL,uri);
                            startActivity(intent);
                        }
                    });

                    adapter = new customAdapter(response.body().getComentarios(), getBaseContext());
                    listComm.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<tarefa> call, Throwable t) {
                Log.i("log failure", t.getMessage());
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
        Database database = new Database(getBaseContext());
        LatLng latLng = database.buscarDadosMarker();
        maps = googleMap;
        maps.setMinZoomPreference(15);

        if(latLng == null){
            Log.i("log latlng", String.valueOf(latLng));
        }

        LatLng position = new LatLng(latLng.latitude, latLng.longitude);
        googleMap.addMarker(new MarkerOptions().position(position));
        maps.moveCamera(CameraUpdateFactory.newLatLng(position));
    }
}
package resende.alan.app4all.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import resende.alan.app4all.R;
import resende.alan.app4all.model.lista;
import resende.alan.app4all.ws.EndPoints;
import resende.alan.app4all.ws.HeaderRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView lista = findViewById(R.id.lista);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        HeaderRetrofit header = new HeaderRetrofit();
        Retrofit retrofit = header.createInstanceRetrofit();

        EndPoints endPoints = retrofit.create(EndPoints.class);
        Call<lista> buscarLista = endPoints.getLista();
        buscarLista.enqueue(new Callback<lista>() {
            @Override
            public void onResponse(Call<lista> call, Response<lista> response) {

                if(response.code() == 200){
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this,
                            android.R.layout.simple_list_item_1, response.body().getId());
                    lista.setAdapter(adapter);
                }else{
                    Toast.makeText(MainActivity.this, "Tente novamente!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<lista> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Falha na Requisição", Toast.LENGTH_SHORT).show();
            }
        });

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String id = (String) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(MainActivity.this, TarefaActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
    }
}

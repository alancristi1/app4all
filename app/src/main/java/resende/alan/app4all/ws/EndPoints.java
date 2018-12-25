package resende.alan.app4all.ws;

import resende.alan.app4all.model.lista;
import resende.alan.app4all.model.tarefa;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface EndPoints {

    @GET("tarefa")
    Call<lista> getLista();

    @GET("tarefa/{id}")
    Call<tarefa> getTarefa(@Path("id") String id);
}

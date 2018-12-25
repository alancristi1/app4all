package resende.alan.app4all.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class lista {

    @SerializedName("lista")
    public List<String> id;

    public lista(List<String> id) {
        this.id = id;
    }

    public List<String> getId() {
        return id;
    }

    public void setId(List<String> id) {
        this.id = id;
    }
}

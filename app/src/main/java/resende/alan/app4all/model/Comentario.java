package resende.alan.app4all.model;

public class Comentario {

    private String urlFoto;
    private String nome;
    private int nota;
    private String titulo;
    private String comentario;

    public Comentario(String urlFoto, String nome, int nota, String titulo, String comentario) {
        this.urlFoto = urlFoto;
        this.nome = nome;
        this.nota = nota;
        this.titulo = titulo;
        this.comentario = comentario;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}

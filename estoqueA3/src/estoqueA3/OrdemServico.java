package estoqueA3;

public class OrdemServico {
    private int id;
    private int idCliente;
    private String descricaoServico;
    private String status;

    public OrdemServico(int id, int idCliente, String descricaoServico, String status) {
        this.id = id;
        this.idCliente = idCliente;
        this.descricaoServico = descricaoServico;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public String getDescricaoServico() {
        return descricaoServico;
    }

    public String getStatus() {
        return status;
    }
}

package estoqueA3;

import java.util.Date;

public class PedidoCompra {
    private int id;
    private Date dataPedido;
    private String status;
    private int idPeca;
    private String nomePeca;
    private int quantidade;

    public PedidoCompra(int id, Date dataPedido, String status, int idPeca, String nomePeca, int quantidade) {
        this.id = id;
        this.dataPedido = dataPedido;
        this.status = status;
        this.idPeca = idPeca;
        this.nomePeca = nomePeca;
        this.quantidade = quantidade;
    }

    public int getId() {
        return id;
    }

    public Date getDataPedido() {
        return dataPedido;
    }

    public String getStatus() {
        return status;
    }

    public int getIdPeca() {
        return idPeca;
    }

    public String getNomePeca() {
        return nomePeca;
    }

    public int getQuantidade() {
        return quantidade;
    }
}

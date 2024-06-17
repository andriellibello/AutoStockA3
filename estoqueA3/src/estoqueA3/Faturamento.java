package estoqueA3;

public class Faturamento {
    private int id;
    private String nomeCliente; 
    private double valorServico;
    private double valorPecas;

    public Faturamento(int id, String nomeCliente, double valorServico, double valorPecas) {
        this.id = id;
        this.nomeCliente = nomeCliente;
        this.valorServico = valorServico;
        this.valorPecas = valorPecas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public double getValorServico() {
        return valorServico;
    }

    public void setValorServico(double valorServico) {
        this.valorServico = valorServico;
    }

    public double getValorPecas() {
        return valorPecas;
    }

    public void setValorPecas(double valorPecas) {
        this.valorPecas = valorPecas;
    }
}

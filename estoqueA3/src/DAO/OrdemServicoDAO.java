package DAO;

import estoqueA3.ClasseConexao;
import estoqueA3.OrdemServico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrdemServicoDAO {
    private Connection conexao;

    public OrdemServicoDAO() {
        this.conexao = ClasseConexao.getcon();
    }

    public void abrirOrdemServico(int idCliente, String descricaoServico) throws SQLException {
        String sql = "INSERT INTO ordensservico (idCliente, descricaoServico, status) VALUES (?, ?, ?)";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setInt(1, idCliente);
        stmt.setString(2, descricaoServico);
        stmt.setString(3, "Aberta");
        stmt.executeUpdate();
        stmt.close();
        System.out.println("Ordem de Serviço inserida com sucesso!");
    }

    public List<OrdemServico> listarOrdensServico() throws SQLException {
        List<OrdemServico> ordensServico = new ArrayList<>();
        String sql = "SELECT idCliente, descricaoServico, status FROM ordensservico";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            int idCliente = rs.getInt("idCliente");
            String descricaoServico = rs.getString("descricaoServico");
            String status = rs.getString("status");
            OrdemServico os = new OrdemServico(idCliente, idCliente, descricaoServico, status);
            ordensServico.add(os);
        }
        stmt.close();
        rs.close();
        return ordensServico;
    }

    public void removerOrdemServico(int idCliente, String descricaoServico) throws SQLException {
        String sql = "DELETE FROM ordensservico WHERE idCliente=? AND descricaoServico=?";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setInt(1, idCliente);
        stmt.setString(2, descricaoServico);
        stmt.executeUpdate();
        stmt.close();
    }

    public void marcarOrdemComoConcluida(int idCliente, String descricaoServico) throws SQLException {
        String sql = "UPDATE ordensservico SET status = 'Concluída' WHERE idCliente = ? AND descricaoServico = ?";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setInt(1, idCliente);
        stmt.setString(2, descricaoServico);
        stmt.executeUpdate();
        stmt.close();
    }

    public OrdemServico obterOrdemServico(int idCliente, String descricaoServico) throws SQLException {
        OrdemServico os = null;
        String sql = "SELECT idCliente, descricaoServico, status FROM ordensservico WHERE idCliente=? AND descricaoServico=?";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setInt(1, idCliente);
        stmt.setString(2, descricaoServico);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            String status = rs.getString("status");
            os = new OrdemServico(idCliente, idCliente, descricaoServico, status);
        }
        stmt.close();
        rs.close();
        return os;
    }
}

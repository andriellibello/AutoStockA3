package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import estoqueA3.ClasseConexao;
import estoqueA3.Faturamento;

public class FaturamentoDAO {
    private Connection conexao;

    public FaturamentoDAO() {
        this.conexao = ClasseConexao.getcon();
    }

    public void registrarFaturamento(Faturamento faturamento) {
        String sql = "INSERT INTO faturamento (nomeCliente, valorServico, valorPecas) VALUES (?, ?, ?)";
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, faturamento.getNomeCliente());
            stmt.setDouble(2, faturamento.getValorServico());
            stmt.setDouble(3, faturamento.getValorPecas());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                faturamento.setId(rs.getInt(1));
            }

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Faturamento> listarFaturamento() {
        List<Faturamento> faturamentos = new ArrayList<>();
        String sql = "SELECT id, nomeCliente, valorServico, valorPecas FROM faturamento";

        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Faturamento faturamento = new Faturamento(
                        rs.getInt("id"),
                        rs.getString("nomeCliente"),
                        rs.getDouble("valorServico"),
                        rs.getDouble("valorPecas")
                );
                faturamentos.add(faturamento);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return faturamentos;
    }
}

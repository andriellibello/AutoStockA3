package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import estoqueA3.ClasseConexao;
import estoqueA3.Pecas;

public class PecasDAO {
    private Connection conexao;

    public PecasDAO() {
        this.conexao = ClasseConexao.getcon();
    }

    public void inserirPecas(Pecas peca) {
        String sql = "INSERT INTO pecas (codigo, nome, descricao, quantidade) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, peca.getCodigo());
            stmt.setString(2, peca.getNome());
            stmt.setString(3, peca.getDescricao());
            stmt.setInt(4, peca.getQuantidade());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Pecas obterPecaPorId(int id) {
        Pecas peca = null;
        String sql = "SELECT * FROM pecas WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String codigo = rs.getString("codigo");
                    String nome = rs.getString("nome");
                    String descricao = rs.getString("descricao");
                    int quantidade = rs.getInt("quantidade");
                    peca = new Pecas(id, codigo, nome, descricao, quantidade);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return peca;
    }

    public void atualizarQuantidade(int id, int quantidade) {
        String sql = "UPDATE pecas SET quantidade = ? WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, quantidade);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Pecas> listarPecas() {
        List<Pecas> listaPecas = new ArrayList<>();
        String sql = "SELECT * FROM pecas";
        try (PreparedStatement stmt = conexao.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String codigo = rs.getString("codigo");
                String nome = rs.getString("nome");
                String descricao = rs.getString("descricao");
                int quantidade = rs.getInt("quantidade");
                Pecas peca = new Pecas(id, codigo, nome, descricao, quantidade);
                listaPecas.add(peca);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaPecas;
    }

    public int getIdPecaPorNome(String nomePeca) {
        String sql = "SELECT id FROM pecas WHERE nome = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, nomePeca);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; 
    }

    public int getQuantidadeById(int idPeca) {
        String sql = "SELECT quantidade FROM pecas WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idPeca);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("quantidade");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; 
    }
}

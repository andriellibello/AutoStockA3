package DAO;

import estoqueA3.ClasseConexao;
import estoqueA3.PedidoCompra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PedidoCompraDAO {
    private Connection conexao;

    public PedidoCompraDAO() {
        this.conexao = ClasseConexao.getcon();
    }

    public int adicionarPedidoCompra(PedidoCompra pedido) {
        String sql = "INSERT INTO pedidoscompra (dataPedido, status) VALUES (?, ?)";
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setDate(1, new java.sql.Date(pedido.getDataPedido().getTime()));
            stmt.setString(2, pedido.getStatus());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void adicionarItemPedidoCompra(int idPedido, int idPeca, int quantidade) {
        String sql = "INSERT INTO itenspedidocompra (idPedido, idPeca, quantidade) VALUES (?, ?, ?)";
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idPedido);
            stmt.setInt(2, idPeca);
            stmt.setInt(3, quantidade);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void atualizarEstoque(int idPeca, int quantidade) {
        String sql = "UPDATE pecas SET quantidade = quantidade + ? WHERE id = ?";
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, quantidade);
            stmt.setInt(2, idPeca);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void marcarPedidoComoConcluido(int idPedido) {
        String sql = "UPDATE pedidoscompra SET status = 'Concluído' WHERE id = ?";
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idPedido);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public int getIdPecaPorNome(String nomePeca) {
        int idPeca = -1;
        String sql = "SELECT id FROM pecas WHERE nome = ?";
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, nomePeca);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                idPeca = rs.getInt("id");
            }
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idPeca;
    }

    public List<PedidoCompra> listarPedidosCompraConcluidos() {
        List<PedidoCompra> pedidos = new ArrayList<>();
        String sql = "SELECT pc.id, pc.dataPedido, pc.status, ipc.idPeca, p.nome, ipc.quantidade " +
                     "FROM pedidoscompra pc " +
                     "JOIN itenspedidocompra ipc ON pc.id = ipc.idPedido " +
                     "JOIN pecas p ON ipc.idPeca = p.id " +
                     "WHERE pc.status = 'Concluído'";
        PreparedStatement stmt = null;
        try {
            stmt = conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                Date dataPedido = rs.getDate("dataPedido");
                String status = rs.getString("status");
                int idPeca = rs.getInt("idPeca");
                String nomePeca = rs.getString("nome");
                int quantidade = rs.getInt("quantidade");
                PedidoCompra pedido = new PedidoCompra(id, new java.util.Date(dataPedido.getTime()), status, idPeca, nomePeca, quantidade);
                pedidos.add(pedido);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return pedidos;
    }

    public List<PedidoCompra> listarPedidosCompraNaoConcluidos() {
        List<PedidoCompra> pedidos = new ArrayList<>();
        String sql = "SELECT pc.id, pc.dataPedido, pc.status, ipc.idPeca, p.nome, ipc.quantidade " +
                     "FROM pedidoscompra pc " +
                     "JOIN itenspedidocompra ipc ON pc.id = ipc.idPedido " +
                     "JOIN pecas p ON ipc.idPeca = p.id " +
                     "WHERE pc.status != 'Concluído'";
        PreparedStatement stmt = null;
        try {
            stmt = conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                Date dataPedido = rs.getDate("dataPedido");
                String status = rs.getString("status");
                int idPeca = rs.getInt("idPeca");
                String nomePeca = rs.getString("nome");
                int quantidade = rs.getInt("quantidade");
                PedidoCompra pedido = new PedidoCompra(id, new java.util.Date(dataPedido.getTime()), status, idPeca, nomePeca, quantidade);
                pedidos.add(pedido);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return pedidos;
    }
}

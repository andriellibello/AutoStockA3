package GUI;

import DAO.PedidoCompraDAO;
import DAO.PecasDAO;
import estoqueA3.PedidoCompra;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class ListaPedidoCompraGUI {
    private JFrame frame;
    private JPanel panel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JTable concluidosTable;
    private JTable naoConcluidosTable;
    private DefaultTableModel concluidosModel;
    private DefaultTableModel naoConcluidosModel;
    private PedidoCompraDAO pedidoCompraDAO;
    private PecasDAO pecasDAO;

    public ListaPedidoCompraGUI() {
        frame = new JFrame("Lista de Pedidos de Compra");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 400);

        panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());

        rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());

        concluidosModel = new DefaultTableModel();
        naoConcluidosModel = new DefaultTableModel();

        concluidosModel.addColumn("ID");
        concluidosModel.addColumn("Data");
        concluidosModel.addColumn("Status");
        concluidosModel.addColumn("Nome Peça");
        concluidosModel.addColumn("Quantidade");

        naoConcluidosModel.addColumn("ID");
        naoConcluidosModel.addColumn("Data");
        naoConcluidosModel.addColumn("Status");
        naoConcluidosModel.addColumn("Nome Peça");
        naoConcluidosModel.addColumn("Quantidade");

        concluidosTable = new JTable(concluidosModel);
        JScrollPane concluidosScrollPane = new JScrollPane(concluidosTable);

        naoConcluidosTable = new JTable(naoConcluidosModel);
        JScrollPane naoConcluidosScrollPane = new JScrollPane(naoConcluidosTable);

        pedidoCompraDAO = new PedidoCompraDAO();
        pecasDAO = new PecasDAO();

        List<PedidoCompra> pedidosConcluidos = pedidoCompraDAO.listarPedidosCompraConcluidos();
        List<PedidoCompra> pedidosNaoConcluidos = pedidoCompraDAO.listarPedidosCompraNaoConcluidos();

        adicionarPedidosNaTabela(pedidosConcluidos, concluidosModel);
        adicionarPedidosNaTabela(pedidosNaoConcluidos, naoConcluidosModel);

        leftPanel.add(new JLabel("Pedidos Concluídos"), BorderLayout.NORTH);
        leftPanel.add(concluidosScrollPane, BorderLayout.CENTER);

        rightPanel.add(new JLabel("Pedidos em Aberto"), BorderLayout.NORTH);
        rightPanel.add(naoConcluidosScrollPane, BorderLayout.CENTER);

        JButton btnConcluirPedido = new JButton("Concluir Pedido");
        btnConcluirPedido.addActionListener(e -> concluirPedido());
        rightPanel.add(btnConcluirPedido, BorderLayout.SOUTH);

        panel.add(leftPanel);
        panel.add(rightPanel);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void adicionarPedidosNaTabela(List<PedidoCompra> pedidos, DefaultTableModel model) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (PedidoCompra pedido : pedidos) {
            String dataFormatada = sdf.format(pedido.getDataPedido());
            Object[] row = {pedido.getId(), dataFormatada, pedido.getStatus(), pedido.getNomePeca(), pedido.getQuantidade()};
            model.addRow(row);
        }
    }

    private void concluirPedido() {
        int selectedRow = naoConcluidosTable.getSelectedRow();
        if (selectedRow != -1) {
            int idPedido = (int) naoConcluidosModel.getValueAt(selectedRow, 0);
            
            String nomePeca = (String) naoConcluidosModel.getValueAt(selectedRow, 3);
            int quantidade = (int) naoConcluidosModel.getValueAt(selectedRow, 4);

            int idPeca = pecasDAO.getIdPecaPorNome(nomePeca);

            int quantidadeAtual = pecasDAO.getQuantidadeById(idPeca); 
            int novaQuantidade = quantidadeAtual + quantidade;
            pecasDAO.atualizarQuantidade(idPeca, novaQuantidade);

            pedidoCompraDAO.marcarPedidoComoConcluido(idPedido);

            DefaultTableModel model = (DefaultTableModel) naoConcluidosTable.getModel();
            Object[] rowData = new Object[model.getColumnCount()];
            for (int i = 0; i < model.getColumnCount(); i++) {
                rowData[i] = model.getValueAt(selectedRow, i);
            }
            model.removeRow(selectedRow);
            concluidosModel.addRow(rowData);
        } else {
            JOptionPane.showMessageDialog(frame, "Selecione um pedido em aberto para concluir.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ListaPedidoCompraGUI::new);
    }

    public Object setVisible(boolean b) {
        return null;
    }
}

package GUI;

import DAO.ClienteDAO;
import DAO.OrdemServicoDAO;
import DAO.PecasDAO;
import estoqueA3.Cliente;
import estoqueA3.OrdemServico;
import estoqueA3.Pecas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class OrdemServicoGUI {
    private JFrame frame;
    private JPanel panel;
    private JLabel lblCliente;
    private JComboBox<Cliente> comboBoxClientes;
    private JLabel lblDescricao;
    private JTextField txtDescricao;
    private JButton btnAbrirOS;
    private JButton btnExcluirOS;
    private JButton btnConcluirOS;
    private JTable tableOrdensServico;
    private DefaultTableModel tableModel;
    private ClienteDAO clienteDAO;
    private OrdemServicoDAO ordemServicoDAO;

    private JLabel lblIdPeca;
    private JTextField txtIdPeca;
    private JLabel lblQuantidade;
    private JTextField txtQuantidade;
    private JButton btnBaixarEstoque;
    private PecasDAO pecasDAO;

    public OrdemServicoGUI() throws SQLException {
        frame = new JFrame("Abertura de Ordem de Serviço");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLayout(new BorderLayout());

        panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10)); 

        lblCliente = new JLabel("Cliente:");
        comboBoxClientes = new JComboBox<>();
        clienteDAO = new ClienteDAO();
        ordemServicoDAO = new OrdemServicoDAO();
        List<Cliente> clientes = clienteDAO.listarClientes();
        for (Cliente cliente : clientes) {
            comboBoxClientes.addItem(cliente);
        }

        lblDescricao = new JLabel("Descrição do Serviço:");
        txtDescricao = new JTextField();

        btnAbrirOS = new JButton("Abrir Ordem de Serviço");
        btnAbrirOS.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirOrdemServico();
            }
        });

        panel.add(lblCliente);
        panel.add(comboBoxClientes);
        panel.add(lblDescricao);
        panel.add(txtDescricao);
        panel.add(new JLabel()); 
        panel.add(btnAbrirOS);

        frame.add(panel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID Cliente");
        tableModel.addColumn("Nome Cliente");
        tableModel.addColumn("Descrição Serviço");
        tableModel.addColumn("Status");

        tableOrdensServico = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableOrdensServico);
        scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10)); 
        frame.add(scrollPane, BorderLayout.CENTER);

        btnExcluirOS = new JButton("Excluir Ordem de Serviço");
        btnExcluirOS.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                excluirOrdemServico();
            }
        });

        btnConcluirOS = new JButton("Concluir Ordem de Serviço");
        btnConcluirOS.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                concluirOrdemServico();
            }
        });

        lblIdPeca = new JLabel("ID da Peça:");
        txtIdPeca = new JTextField();
        lblQuantidade = new JLabel("Quantidade:");
        txtQuantidade = new JTextField();
        btnBaixarEstoque = new JButton("Baixar Estoque");
        pecasDAO = new PecasDAO();

        btnBaixarEstoque.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                baixarEstoque();
            }
        });

        JPanel panelSul = new JPanel();
        panelSul.setLayout(new BorderLayout());
        panelSul.setBorder(new EmptyBorder(10, 10, 10, 10)); 

        JPanel panelBotoes = new JPanel();
        panelBotoes.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotoes.add(btnExcluirOS);
        panelBotoes.add(btnConcluirOS);

        JPanel panelBaixaEstoque = new JPanel();
        panelBaixaEstoque.setLayout(new GridLayout(3, 2, 10, 10));
        panelBaixaEstoque.add(lblIdPeca);
        panelBaixaEstoque.add(txtIdPeca);
        panelBaixaEstoque.add(lblQuantidade);
        panelBaixaEstoque.add(txtQuantidade);
        panelBaixaEstoque.add(new JLabel());
        panelBaixaEstoque.add(btnBaixarEstoque);

        panelSul.add(panelBotoes, BorderLayout.NORTH);
        panelSul.add(panelBaixaEstoque, BorderLayout.SOUTH);

        frame.add(panelSul, BorderLayout.SOUTH);

        carregarOrdensServico();

        frame.setVisible(true);
    }

    private void abrirOrdemServico() {
        Cliente cliente = (Cliente) comboBoxClientes.getSelectedItem();
        if (cliente == null) {
            JOptionPane.showMessageDialog(frame, "Selecione um cliente válido.");
            return;
        }

        String descricaoServico = txtDescricao.getText();
        try {
            ordemServicoDAO.abrirOrdemServico(cliente.getId(), descricaoServico);
            carregarOrdensServico();
            JOptionPane.showMessageDialog(frame, "Ordem de Serviço aberta com sucesso!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void carregarOrdensServico() {
        try {
            List<OrdemServico> ordensServico = ordemServicoDAO.listarOrdensServico();
            tableModel.setRowCount(0);
            for (OrdemServico os : ordensServico) {
                Cliente cliente = clienteDAO.buscarClientePorId(os.getIdCliente());
                tableModel.addRow(new Object[]{os.getIdCliente(), cliente.getNome(), os.getDescricaoServico(), os.getStatus()});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void excluirOrdemServico() {
        int row = tableOrdensServico.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(frame, "Selecione uma ordem de serviço para excluir.");
            return;
        }
        int idCliente = (int) tableModel.getValueAt(row, 0);
        String descricaoServico = (String) tableModel.getValueAt(row, 2);

        int confirm = JOptionPane.showConfirmDialog(frame, "Tem certeza que deseja remover esta ordem de serviço?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                ordemServicoDAO.removerOrdemServico(idCliente, descricaoServico);
                carregarOrdensServico();
                JOptionPane.showMessageDialog(frame, "Ordem de Serviço removida com sucesso!");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void concluirOrdemServico() {
        int row = tableOrdensServico.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(frame, "Selecione uma ordem de serviço para concluir.");
            return;
        }
        int idCliente = (int) tableModel.getValueAt(row, 0);
        String descricaoServico = (String) tableModel.getValueAt(row, 2);

        try {
            ordemServicoDAO.marcarOrdemComoConcluida(idCliente, descricaoServico);
            carregarOrdensServico();
            JOptionPane.showMessageDialog(frame, "Ordem de Serviço concluída com sucesso!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void baixarEstoque() {
        try {
            int idPeca = Integer.parseInt(txtIdPeca.getText());
            int quantidade = Integer.parseInt(txtQuantidade.getText());

            Pecas peca = pecasDAO.obterPecaPorId(idPeca);
            if (peca != null && peca.getQuantidade() >= quantidade) {
                pecasDAO.atualizarQuantidade(idPeca, peca.getQuantidade() - quantidade);
                JOptionPane.showMessageDialog(frame, "Baixa de estoque realizada com sucesso!");
            } else {
                JOptionPane.showMessageDialog(frame, "Quantidade insuficiente no estoque ou peça não encontrada.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Por favor, insira valores válidos.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new OrdemServicoGUI();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Object setVisible(boolean b) {
        // TODO Auto-generated method stub
        return null;
    }
}

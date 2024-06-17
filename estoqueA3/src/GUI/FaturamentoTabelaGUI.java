package GUI;

import DAO.FaturamentoDAO;
import estoqueA3.Faturamento;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FaturamentoTabelaGUI {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private FaturamentoDAO faturamentoDAO;

    public FaturamentoTabelaGUI() {
        initialize();
        faturamentoDAO = new FaturamentoDAO();
        carregarDados();
    }

    private void initialize() {
        frame = new JFrame("Tabela de Faturamento");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 400);

        tableModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Nome do Cliente", "Valor Serviço", "Valor Peças"}
        );
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        JButton btnNovoFaturamento = new JButton("Registrar Novo Faturamento");
        btnNovoFaturamento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FaturamentoGUI(0, FaturamentoTabelaGUI.this);
            }
        });

        JPanel topPanel = new JPanel();
        topPanel.add(btnNovoFaturamento);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void carregarDados() {
        List<Faturamento> faturamentos = faturamentoDAO.listarFaturamento();
        for (Faturamento faturamento : faturamentos) {
            Object[] row = {
                    faturamento.getId(),
                    faturamento.getNomeCliente(),
                    faturamento.getValorServico(),
                    faturamento.getValorPecas()
            };
            tableModel.addRow(row);
        }
    }

    public void atualizarTabela() {
        tableModel.setRowCount(0);
        carregarDados();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FaturamentoTabelaGUI::new);
    }

	public Object setVisible(boolean b) {
		// TODO Auto-generated method stub
		return null;
	}
}

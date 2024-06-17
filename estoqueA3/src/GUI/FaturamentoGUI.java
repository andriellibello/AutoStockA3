package GUI;

import DAO.FaturamentoDAO;
import estoqueA3.Faturamento;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FaturamentoGUI {
    private JFrame frame;
    private JPanel panel;
    private JLabel lblNomeCliente;
    private JLabel lblValorServico;
    private JLabel lblValorPecas;
    private JTextField txtNomeCliente;
    private JTextField txtValorServico;
    private JTextField txtValorPecas;
    private JButton btnRegistrarFaturamento;
    private FaturamentoDAO faturamentoDAO;
    private int idCliente;
    private FaturamentoTabelaGUI tabelaGUI;

    public FaturamentoGUI(int idCliente, FaturamentoTabelaGUI tabelaGUI) {
        this.idCliente = idCliente;
        this.tabelaGUI = tabelaGUI;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Registro de Faturamento");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 250);

        panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
        
        lblNomeCliente = new JLabel("Nome do Cliente:");
        lblValorServico = new JLabel("Valor do Serviço:");
        lblValorPecas = new JLabel("Valor das Peças:");
        txtNomeCliente = new JTextField();
        txtValorServico = new JTextField();
        txtValorPecas = new JTextField();
        btnRegistrarFaturamento = new JButton("Registrar Faturamento");
        faturamentoDAO = new FaturamentoDAO();

        panel.add(lblNomeCliente);
        panel.add(txtNomeCliente);
        panel.add(lblValorServico);
        panel.add(txtValorServico);
        panel.add(lblValorPecas);
        panel.add(txtValorPecas);
        panel.add(btnRegistrarFaturamento);

        btnRegistrarFaturamento.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registrarFaturamento();
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    private void registrarFaturamento() {
        String nomeCliente = txtNomeCliente.getText();
        double valorServico = Double.parseDouble(txtValorServico.getText());
        double valorPecas = Double.parseDouble(txtValorPecas.getText());

        Faturamento faturamento = new Faturamento(idCliente, nomeCliente, valorServico, valorPecas);
        faturamentoDAO.registrarFaturamento(faturamento);

        JOptionPane.showMessageDialog(frame, "Faturamento registrado com sucesso!");

        tabelaGUI.atualizarTabela(); 
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FaturamentoGUI(0, null));
    }
}

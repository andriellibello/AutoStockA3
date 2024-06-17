package GUI;

import DAO.PedidoCompraDAO;
import estoqueA3.PedidoCompra;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class PedidoCompraGUI {
    private JFrame frame;
    private JPanel panel;
    private JLabel lblStatus;
    private JLabel lblIdPeca;
    private JLabel lblQuantidade;
    private JTextField txtStatus;
    private JTextField txtIdPeca;
    private JTextField txtQuantidade;
    private JButton btnSalvar;
    private PedidoCompraDAO pedidoCompraDAO;

    public PedidoCompraGUI() {
        frame = new JFrame("Pedido de Compra");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 200);

        panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        lblStatus = new JLabel("Status:");
        lblIdPeca = new JLabel("ID da Peça:");
        lblQuantidade = new JLabel("Quantidade:");
        txtStatus = new JTextField();
        txtIdPeca = new JTextField();
        txtQuantidade = new JTextField();
        btnSalvar = new JButton("Salvar");
        btnSalvar.setPreferredSize(new Dimension(100, 30)); 
        pedidoCompraDAO = new PedidoCompraDAO();

        panel.add(lblStatus);
        panel.add(txtStatus);
        panel.add(lblIdPeca);
        panel.add(txtIdPeca);
        panel.add(lblQuantidade);
        panel.add(txtQuantidade);
        panel.add(btnSalvar);

        btnSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                adicionarPedidoCompra();
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    private void adicionarPedidoCompra() {
        Date data = new Date();
        String status = txtStatus.getText();
        int idPeca = Integer.parseInt(txtIdPeca.getText());
        int quantidade = Integer.parseInt(txtQuantidade.getText());

        PedidoCompra pedido = new PedidoCompra(0, data, status, idPeca, null, quantidade);

        int idPedido = pedidoCompraDAO.adicionarPedidoCompra(pedido);

        pedidoCompraDAO.adicionarItemPedidoCompra(idPedido, idPeca, quantidade);

        JOptionPane.showMessageDialog(frame, "Pedido de compra adicionado com sucesso!");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new PedidoCompraGUI();
            }
        });
    }

    public Object setVisible(boolean b) {
        return null;
    }
}

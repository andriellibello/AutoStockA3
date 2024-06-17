package GUI;

import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.*;

public class MenuPrincipal {

    private JFrame frame;
    private JPanel headerPanel;
    

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MenuPrincipal window = new MenuPrincipal();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public MenuPrincipal() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(150, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.BLUE);
        frame.getContentPane().setLayout(new BorderLayout());

        int margin = 20;

        headerPanel = new JPanel();
        headerPanel.setBackground(Color.BLUE);
        headerPanel.setPreferredSize(new Dimension(frame.getWidth(), 80));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(margin, margin, margin, margin));
        frame.getContentPane().add(headerPanel, BorderLayout.NORTH);

    

        JLabel lblHeader = new JLabel("Sistema AutoStock");
        lblHeader.setForeground(Color.WHITE);
        headerPanel.setPreferredSize(new Dimension(frame.getWidth(), 60));
        lblHeader.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblHeader.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(lblHeader);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(0, 1, 0, 20)); 
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(margin, margin, margin, margin));
        frame.getContentPane().add(contentPanel, BorderLayout.CENTER);


        
        addButton(contentPanel, "Consultar Peças", e -> new ConsultaEstoqueGUI().setVisible(true), new Color(22, 121, 171));
        addButton(contentPanel, "Cadastrar Peças", e -> new PecasGUI().setVisible(true), new Color(22, 121, 171)); 
        addButton(contentPanel, "Baixar Estoque", e -> new BaixaEstoqueGUI().setVisible(true), new Color(22, 121, 171)); 
        addButton(contentPanel, "Abrir Ordem de Serviço", e -> {
			try {
				new OrdemServicoGUI().setVisible(true);
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
		}, new Color(22, 121, 171));
        addButton(contentPanel, "Clientes", e -> new ClienteGUI().setVisible(true), new Color(22, 121, 171)); 
        addButton(contentPanel, "Pedido de Compra", e -> new PedidoCompraGUI().setVisible(true), new Color(22, 121, 171));
        addButton(contentPanel, "Lista de Pedidos de Compra", e -> new ListaPedidoCompraGUI().setVisible(true), new Color(22, 121, 171)); 
        addButton(contentPanel, "Registrar Faturamento", e -> new FaturamentoTabelaGUI().setVisible(true), new Color(22, 121, 171)); 

        
    }

    private void addButton(JPanel panel, String text, ActionListener listener, Color buttonColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Tahoma", Font.BOLD, 14)); 
        button.setForeground(Color.WHITE); 
        button.setBackground(buttonColor); 
        button.setPreferredSize(new Dimension(180, 40)); 
        button.addActionListener(listener);
        panel.add(button);
    }
}

package GUI;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import java.awt.Color;

import DAO.PecasDAO;
import estoqueA3.Pecas;

public class BaixaEstoqueGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField textFieldId;
    private JTextField textFieldQuantidade;
    private PecasDAO pecasDAO; 

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    BaixaEstoqueGUI window = new BaixaEstoqueGUI();
                    window.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public BaixaEstoqueGUI() {
        pecasDAO = new PecasDAO(); 
        initialize();
    }

    private void initialize() {
        setTitle("Baixa de Estoque");
        setBounds(100, 100, 450, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);
        getContentPane().setBackground(new Color(195, 195, 195)); 

        JLabel lblId = new JLabel("ID da Peça:");
        lblId.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblId.setForeground(Color.BLACK); 
        lblId.setBounds(50, 50, 100, 30);
        getContentPane().add(lblId);

        textFieldId = new JTextField();
        textFieldId.setBounds(160, 50, 200, 30);
        getContentPane().add(textFieldId);
        textFieldId.setColumns(10);

        JLabel lblQuantidade = new JLabel("Quantidade:");
        lblQuantidade.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblQuantidade.setForeground(Color.BLACK);
        lblQuantidade.setBounds(50, 100, 100, 30);
        getContentPane().add(lblQuantidade);

        textFieldQuantidade = new JTextField();
        textFieldQuantidade.setBounds(160, 100, 200, 30);
        getContentPane().add(textFieldQuantidade);
        textFieldQuantidade.setColumns(10);

        JButton btnBaixarEstoque = new JButton("Baixar Estoque");
        btnBaixarEstoque.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnBaixarEstoque.setBounds(150, 150, 150, 40);
        getContentPane().add(btnBaixarEstoque);

        btnBaixarEstoque.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(textFieldId.getText());
                    int quantidade = Integer.parseInt(textFieldQuantidade.getText());

                    Pecas peca = pecasDAO.obterPecaPorId(id);
                    if (peca != null && peca.getQuantidade() >= quantidade) {
                        pecasDAO.atualizarQuantidade(id, peca.getQuantidade() - quantidade);
                        JOptionPane.showMessageDialog(BaixaEstoqueGUI.this, "Baixa de estoque realizada com sucesso!");
                    } else {
                        JOptionPane.showMessageDialog(BaixaEstoqueGUI.this, "Quantidade insuficiente no estoque ou peça não encontrada.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(BaixaEstoqueGUI.this, "Por favor, insira valores válidos.");
                }
            }
        });
    }
}

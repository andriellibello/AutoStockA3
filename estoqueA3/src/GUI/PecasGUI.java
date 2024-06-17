package GUI;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTextField;

import DAO.PecasDAO;
import estoqueA3.Pecas;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.BorderFactory;

public class PecasGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField textFieldCodigo;
    private JTextField textFieldNome;
    private JTextField textFieldDescricao;
    private JTextField textFieldQuantidade;
    private PecasDAO pecasDAO; 

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    PecasGUI window = new PecasGUI();
                    window.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
  
    public PecasGUI() {
        pecasDAO = new PecasDAO(); 
        initialize();
    }

    private void initialize() {
        setTitle("Cadastro de Peças");
        setBounds(100, 100, 450, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(195, 195, 195));  
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 

        JLabel lblCodigo = new JLabel("Código");
        lblCodigo.setForeground(Color.BLACK);  
        lblCodigo.setFont(new Font("Tahoma", Font.PLAIN, 14));

        textFieldCodigo = new JTextField();
        textFieldCodigo.setColumns(10);

        JLabel lblNome = new JLabel("Nome");
        lblNome.setForeground(Color.BLACK);  
        lblNome.setFont(new Font("Tahoma", Font.PLAIN, 14));

        textFieldNome = new JTextField();
        textFieldNome.setColumns(10);

        JLabel lblDescricao = new JLabel("Descrição");
        lblDescricao.setForeground(Color.BLACK);  
        lblDescricao.setFont(new Font("Tahoma", Font.PLAIN, 14));

        textFieldDescricao = new JTextField();
        textFieldDescricao.setColumns(10);

        JLabel lblQuantidade = new JLabel("Quantidade");
        lblQuantidade.setForeground(Color.BLACK);   
        lblQuantidade.setFont(new Font("Tahoma", Font.PLAIN, 14));

        textFieldQuantidade = new JTextField();
        textFieldQuantidade.setColumns(10);

        JButton btnCadastrar = new JButton("Cadastrar Peça");
        btnCadastrar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                salvarPeca();
            }
        });

        panel.add(lblCodigo);
        panel.add(textFieldCodigo);
        panel.add(Box.createVerticalStrut(10)); 

        panel.add(lblNome);
        panel.add(textFieldNome);
        panel.add(Box.createVerticalStrut(10)); 

        panel.add(lblDescricao);
        panel.add(textFieldDescricao);
        panel.add(Box.createVerticalStrut(10)); 

        panel.add(lblQuantidade);
        panel.add(textFieldQuantidade);
        panel.add(Box.createVerticalStrut(10)); 

        panel.add(btnCadastrar);

        getContentPane().add(panel);
    }

    private void salvarPeca() {
        try {
            String codigo = textFieldCodigo.getText();
            String nome = textFieldNome.getText();
            String descricao = textFieldDescricao.getText();
            int quantidade = Integer.parseInt(textFieldQuantidade.getText());

            Pecas peca = new Pecas(0, codigo, nome, descricao, quantidade);
            pecasDAO.inserirPecas(peca); 

            textFieldCodigo.setText("");
            textFieldNome.setText("");
            textFieldDescricao.setText("");
            textFieldQuantidade.setText("");

            JOptionPane.showMessageDialog(PecasGUI.this, "Peça salva com sucesso!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(PecasGUI.this, "Por favor, insira uma quantidade válida.");
        }
    }
}

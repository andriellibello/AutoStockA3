package GUI;

import javax.swing.*;
import DAO.ClienteDAO;
import estoqueA3.Cliente;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ClienteGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private ClienteDAO clienteDAO;
    private JTextField txtNome;
    private JTextField txtEndereco;
    private JTextField txtTelefone;
    private DefaultListModel<Cliente> listModelClientes;
    private JList<Cliente> listClientes;

    public ClienteGUI() {
        clienteDAO = new ClienteDAO();
        initialize();
        atualizarListaClientes();
        pack(); 
        setLocationRelativeTo(null); 
    }

    private void initialize() {
        setTitle("Cadastro de Clientes");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout(10, 10));

        JPanel panelFormulario = new JPanel(new GridLayout(3, 2, 5, 5));
        panelFormulario.setBackground(new Color(173, 216, 230));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        getContentPane().add(panelFormulario, BorderLayout.NORTH);

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setForeground(Color.BLACK);
        panelFormulario.add(lblNome);

        txtNome = new JTextField();
        panelFormulario.add(txtNome);

        JLabel lblEndereco = new JLabel("Endereço:");
        lblEndereco.setForeground(Color.BLACK);
        panelFormulario.add(lblEndereco);

        txtEndereco = new JTextField();
        panelFormulario.add(txtEndereco);

        JLabel lblTelefone = new JLabel("Telefone:");
        lblTelefone.setForeground(Color.BLACK);
        panelFormulario.add(lblTelefone);

        txtTelefone = new JTextField();
        panelFormulario.add(txtTelefone);

        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotoes.setBackground(new Color(173, 216, 230));
        panelBotoes.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        getContentPane().add(panelBotoes, BorderLayout.CENTER);

        JButton btnAdicionar = new JButton("Adicionar");
        btnAdicionar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                adicionarCliente();
            }
        });
        panelBotoes.add(btnAdicionar);

        JButton btnAtualizar = new JButton("Atualizar");
        btnAtualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                atualizarCliente();
            }
        });
        panelBotoes.add(btnAtualizar);

        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                excluirCliente();
            }
        });
        panelBotoes.add(btnExcluir);

        listModelClientes = new DefaultListModel<>();
        listClientes = new JList<>(listModelClientes);
        listClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listClientes.addListSelectionListener(e -> preencherFormulario(listClientes.getSelectedValue()));
        JScrollPane scrollPane = new JScrollPane(listClientes);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        getContentPane().add(scrollPane, BorderLayout.SOUTH);
    }

    private void adicionarCliente() {
        String nome = txtNome.getText();
        String endereco = txtEndereco.getText();
        String telefone = txtTelefone.getText();
        Cliente cliente = new Cliente(0, nome, endereco, telefone);
        clienteDAO.adicionarCliente(cliente);
        atualizarListaClientes();
    }

    private void atualizarCliente() {
        String nome = txtNome.getText();
        String endereco = txtEndereco.getText();
        String telefone = txtTelefone.getText();
        Cliente clienteSelecionado = listClientes.getSelectedValue();
        if (clienteSelecionado != null) {
            clienteSelecionado.setNome(nome);
            clienteSelecionado.setEndereco(endereco);
            clienteSelecionado.setTelefone(telefone);
            clienteDAO.atualizarCliente(clienteSelecionado);
            atualizarListaClientes();
        }
    }

    private void excluirCliente() {
        Cliente clienteSelecionado = listClientes.getSelectedValue();
        if (clienteSelecionado != null) {
            clienteDAO.excluirCliente(clienteSelecionado.getId());
            atualizarListaClientes();
        }
    }

    private void atualizarListaClientes() {
        listModelClientes.clear();
        List<Cliente> clientes = clienteDAO.listarClientes();
        for (Cliente cliente : clientes) {
            listModelClientes.addElement(cliente);
        }
    }

    private void preencherFormulario(Cliente cliente) {
        if (cliente != null) {
            txtNome.setText(cliente.getNome());
            txtEndereco.setText(cliente.getEndereco());
            txtTelefone.setText(cliente.getTelefone());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ClienteGUI clienteGUI = new ClienteGUI();
                clienteGUI.setVisible(true);
            }
        });
    }
}

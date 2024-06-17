package GUI;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import DAO.PecasDAO;
import estoqueA3.Pecas;

import java.util.List;

public class ConsultaEstoqueGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTable table;
    private PecasDAO pecasDAO; 

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ConsultaEstoqueGUI window = new ConsultaEstoqueGUI();
                    window.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public ConsultaEstoqueGUI() {
        pecasDAO = new PecasDAO(); 
        initialize();
        loadData();
    }

    private void initialize() {
        setTitle("Consulta de Estoque");
        setBounds(100, 100, 600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 11, 564, 339);
        getContentPane().add(scrollPane);

        table = new JTable();
        table.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {"ID", "Código", "Nome", "Descrição", "Quantidade"}
        ));
        scrollPane.setViewportView(table);
    }

    private void loadData() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        List<Pecas> pecas = pecasDAO.listarPecas(); 
        
        for (Pecas peca : pecas) {
            model.addRow(new Object[]{peca.getId(), peca.getCodigo(), peca.getNome(), peca.getDescricao(), peca.getQuantidade()});
        }
    }
}

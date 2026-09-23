package PI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class JanelaStatus extends JFrame {

    private final DefaultTableModel modelo;
    private final JTable tabela;
    private final JComboBox<String> comboBairro;

    public JanelaStatus() {
        setTitle("Status dos problemas");
        setSize(900, 620);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JLabel titulo = new JLabel("ACOMPANHAMENTO DOS PROBLEMAS", SwingConstants.CENTER);
        titulo.setBounds(170, 15, 540, 35);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setForeground(new Color(30, 80, 130));
        add(titulo);

        JLabel lblBairro = new JLabel("Bairro:");
        lblBairro.setBounds(25, 65, 60, 25);
        add(lblBairro);

        comboBairro = new JComboBox<>();
        comboBairro.setBounds(85, 65, 220, 28);
        add(comboBairro);

        JButton btnAtualizarLista = new JButton("ATUALIZAR LISTA");
        btnAtualizarLista.setBounds(320, 65, 150, 28);
        add(btnAtualizarLista);

        JButton btnBaixarCsv = new JButton("BAIXAR CSV");
        btnBaixarCsv.setBounds(485, 65, 140, 28);
        add(btnBaixarCsv);

        String[] colunas = {"ID", "Rua", "Descrição", "Data do Cadastro", "Classificação", "Status", "Usuário"};
        modelo = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabela = new JTable(modelo);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(20, 110, 845, 340);
        add(scroll);

        JLabel lblNovoStatus = new JLabel("Novo status:");
        lblNovoStatus.setBounds(25, 470, 100, 30);
        add(lblNovoStatus);

        JComboBox<String> comboStatus = new JComboBox<>(new String[]{
                "Em análise", "Enviado para prefeitura", "Em andamento", "Concluído"
        });
        comboStatus.setBounds(125, 470, 210, 30);
        add(comboStatus);

        JButton btnSalvarStatus = new JButton("ALTERAR STATUS");
        btnSalvarStatus.setBounds(350, 470, 150, 30);
        btnSalvarStatus.setEnabled(Usuario_conexao.isAdmin());
        if (!Usuario_conexao.isAdmin()) {
            btnSalvarStatus.setToolTipText("Somente administradores podem alterar o status.");
        }
        add(btnSalvarStatus);

        JButton btnVoltar = new JButton("VOLTAR");
        btnVoltar.setBounds(745, 470, 120, 30);
        add(btnVoltar);

        comboBairro.addActionListener(e -> carregarProblemas());
        btnAtualizarLista.addActionListener(e -> {
            carregarBairros();
            carregarProblemas();
        });

        btnBaixarCsv.addActionListener(e -> exportarCsv());

        btnSalvarStatus.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha < 0) {
                JOptionPane.showMessageDialog(this, "Selecione um problema na tabela.");
                return;
            }

            int idProblema = (Integer) modelo.getValueAt(linha, 0);
            String novoStatus = (String) comboStatus.getSelectedItem();
            atualizarStatus(idProblema, novoStatus);
        });

        btnVoltar.addActionListener(e -> {
            dispose();
            TelaInicial.main(null);
        });

        Acessibilidade.adicionarBotao(this, 520, 470, 140, 30);
        Acessibilidade.registrarTela(this);

        carregarBairros();
        carregarProblemas();
    }

    private void exportarCsv() {
        if (modelo.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this,
                    "Não há dados na tabela para exportar.",
                    "Exportar CSV",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JFileChooser seletor = new JFileChooser();
        seletor.setDialogTitle("Salvar relatório em CSV");
        seletor.setSelectedFile(new File("status_problemas.csv"));

        if (seletor.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File arquivo = seletor.getSelectedFile();
        if (!arquivo.getName().toLowerCase().endsWith(".csv")) {
            arquivo = new File(arquivo.getParentFile(), arquivo.getName() + ".csv");
        }

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(arquivo), StandardCharsets.UTF_8))) {

            writer.write('\uFEFF');

            for (int coluna = 0; coluna < modelo.getColumnCount(); coluna++) {
                if (coluna > 0) {
                    writer.write(';');
                }
                writer.write(escaparCsv(modelo.getColumnName(coluna)));
            }
            writer.newLine();

            for (int linha = 0; linha < modelo.getRowCount(); linha++) {
                for (int coluna = 0; coluna < modelo.getColumnCount(); coluna++) {
                    if (coluna > 0) {
                        writer.write(';');
                    }
                    Object valor = modelo.getValueAt(linha, coluna);
                    String texto = valor == null ? "" : valor.toString();

                    // Mantém a data como texto no Excel para evitar que a coluna apareça como ###.
                    if (coluna == 3 && !texto.isEmpty()) {
                        texto = "\'" + texto;
                    }

                    writer.write(escaparCsv(texto));
                }
                writer.newLine();
            }

            JOptionPane.showMessageDialog(this,
                    "CSV salvo com sucesso em:\n" + arquivo.getAbsolutePath(),
                    "Exportação concluída",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao salvar o CSV:\n" + ex.getMessage(),
                    "Erro de exportação",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private String escaparCsv(String valor) {
        String texto = valor.replace("\"", "\"\"");
        return "\"" + texto + "\"";
    }

    private void carregarBairros() {
        Object selecionado = comboBairro.getSelectedItem();
        comboBairro.removeAllItems();
        comboBairro.addItem("Todos");

        String sql = "SELECT DISTINCT end_bairro FROM Endereco ORDER BY end_bairro";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                comboBairro.addItem(rs.getString("end_bairro"));
            }

            if (selecionado != null) {
                comboBairro.setSelectedItem(selecionado);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar bairros:\n" + ex.getMessage());
        }
    }

    private void carregarProblemas() {
        if (comboBairro.getSelectedItem() == null) {
            return;
        }

        modelo.setRowCount(0);
        String bairro = (String) comboBairro.getSelectedItem();

        StringBuilder sql = new StringBuilder(
                "SELECT Problema.pro_id_pk, Endereco.end_rua, Problema.pro_descricao, Problema.pro_hora_envio, " +
                "Problema.pro_classificacao, Problema.pro_status, Usuario.usu_nome " +
                "FROM Problema  " +
                "JOIN Endereco  ON Endereco.end_id_pk = Problema.end_id_fk " +
                "JOIN Usuario  ON Usuario.usu_id_pk = Problema.usu_id_fk ");

        boolean filtrar = !"Todos".equals(bairro);
        if (filtrar) {
            sql.append("WHERE Endereco.end_bairro = ? ");
        }
        sql.append("ORDER BY Problema.pro_hora_envio DESC");

        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql.toString())) {

            if (filtrar) {
                stmt.setString(1, bairro);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                while (rs.next()) {
                    modelo.addRow(new Object[]{
                            rs.getInt("pro_id_pk"),
                            rs.getString("end_rua"),
                            rs.getString("pro_descricao"),
                            formato.format(rs.getTimestamp("pro_hora_envio")),
                            rs.getString("pro_classificacao"),
                            rs.getString("pro_status"),
                            rs.getString("usu_nome")
                    });
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar problemas:\n" + ex.getMessage());
        }
    }

    private void atualizarStatus(int idProblema, String novoStatus) {
        String sql = "UPDATE Problema SET pro_status = ? WHERE pro_id_pk = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, novoStatus);
            stmt.setInt(2, idProblema);
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Status atualizado no banco de dados.");
            carregarProblemas();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar status:\n" + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JanelaStatus().setVisible(true));
    }
}

package PI;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Formulario extends JFrame {

    private File imagemSelecionada;
    private final JTextField txtAnexo = new JTextField();

    public Formulario() {
        setTitle("Cadastrar problema");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(680, 650);
        setLocationRelativeTo(null);

        Color azul = new Color(0, 102, 204);
        JPanel conteudo = new JPanel(new GridBagLayout());
        conteudo.setBackground(Color.WHITE);
        conteudo.setBorder(new EmptyBorder(15, 25, 20, 25));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JLabel titulo = new JLabel("Cadastro do problema", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(azul);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        conteudo.add(titulo, gbc);

        JLabel iconeEndereco = new JLabel(Icones.endereco());
        iconeEndereco.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 1;
        gbc.insets = new Insets(2, 6, 2, 6);
        conteudo.add(iconeEndereco, gbc);

        JLabel subtitulo = new JLabel("Informe o endereço e os dados do problema na mesma tela.", SwingConstants.CENTER);
        subtitulo.setForeground(Color.GRAY);
        gbc.gridy = 2;
        gbc.insets = new Insets(6, 6, 6, 6);
        conteudo.add(subtitulo, gbc);

        gbc.gridwidth = 1;

        JTextField txtCep = new JTextField();
        limitarCep(txtCep);
        JTextField txtRua = new JTextField();
        JComboBox<String> comboBairro = new JComboBox<>(new String[]{
                "Selecione", "Águas Claras", "Brotas", "Cabula", "Cajazeiras", "Cassange", "Centro",
                "Comércio", "Federação", "Itapuã", "Liberdade", "Mata Escura", "Nazaré", "Paripe",
                "Pernambués", "Pirajá", "Pituba", "Rio Vermelho", "São Caetano", "São Cristóvão",
                "Sussuarana", "Valéria", "Vitória"
        });
        JComboBox<String> comboEstado = new JComboBox<>(new String[]{"BA"});
        JTextArea txtDescricao = new JTextArea(5, 20);
        txtDescricao.setLineWrap(true);
        txtDescricao.setWrapStyleWord(true);
        JScrollPane scrollDescricao = new JScrollPane(txtDescricao);

        JRadioButton rbRiscoSim = new JRadioButton("Sim");
        JRadioButton rbRiscoNao = new JRadioButton("Não", true);
        ButtonGroup grupoRisco = new ButtonGroup();
        grupoRisco.add(rbRiscoSim);
        grupoRisco.add(rbRiscoNao);
        JPanel painelRisco = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        painelRisco.setOpaque(false);
        painelRisco.add(rbRiscoSim);
        painelRisco.add(rbRiscoNao);

        JComboBox<String> comboCategoria = new JComboBox<>(new String[]{
                "Selecione", "Infraestrutura", "Acessibilidade", "Mau uso", "Outros"
        });

        txtAnexo.setEditable(false);
        txtAnexo.setToolTipText("Nenhuma imagem selecionada");
        JButton btnSelecionarImagem = new JButton("SELECIONAR IMAGEM");
        btnSelecionarImagem.setFocusPainted(false);

        JPanel painelAnexo = new JPanel(new BorderLayout(8, 0));
        painelAnexo.setOpaque(false);
        painelAnexo.add(txtAnexo, BorderLayout.CENTER);
        painelAnexo.add(btnSelecionarImagem, BorderLayout.EAST);

        btnSelecionarImagem.addActionListener(e -> selecionarImagem());

        int linha = 3;
        adicionarCampo(conteudo, gbc, linha++, "CEP", txtCep, null);
        adicionarCampo(conteudo, gbc, linha++, "Rua", txtRua, null);
        adicionarCampo(conteudo, gbc, linha++, "Bairro", comboBairro, null);
        adicionarCampo(conteudo, gbc, linha++, "Estado", comboEstado, null);
        adicionarCampo(conteudo, gbc, linha++, "Descrição", scrollDescricao, Icones.descricao());
        adicionarCampo(conteudo, gbc, linha++, "Anexo", painelAnexo, Icones.anexo());
        adicionarCampo(conteudo, gbc, linha++, "Risco de acidente", painelRisco, Icones.risco());
        adicionarCampo(conteudo, gbc, linha++, "Categoria", comboCategoria, null);

        JButton btnSalvar = new JButton("SALVAR PROBLEMA");
        btnSalvar.setBackground(azul);
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFont(new Font("Arial", Font.BOLD, 14));
        btnSalvar.setFocusPainted(false);

        JButton btnVoltar = new JButton("VOLTAR");

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        botoes.setOpaque(false);
        botoes.add(btnSalvar);
        botoes.add(btnVoltar);

        gbc.gridx = 0;
        gbc.gridy = linha++;
        gbc.gridwidth = 2;
        conteudo.add(botoes, gbc);

        JButton btnAcessibilidade = Acessibilidade.criarBotao(this);
        gbc.gridy = linha;
        conteudo.add(btnAcessibilidade, gbc);

        btnSalvar.addActionListener(e -> {
            if (!Usuario_conexao.estaLogado()) {
                JOptionPane.showMessageDialog(this,
                        "Faça login antes de cadastrar um problema.",
                        "Sessão não encontrada",
                        JOptionPane.WARNING_MESSAGE);
                dispose();
                new TelaLogin().setVisible(true);
                return;
            }

            String cep = txtCep.getText().trim();
            String rua = txtRua.getText().trim();
            String bairro = (String) comboBairro.getSelectedItem();
            String estado = (String) comboEstado.getSelectedItem();
            String descricao = txtDescricao.getText().trim();
            String categoria = (String) comboCategoria.getSelectedItem();
            boolean risco = rbRiscoSim.isSelected();

            if (cep.length() != 8 || rua.isEmpty() || "Selecione".equals(bairro)
                    || descricao.isEmpty() || "Selecione".equals(categoria)) {
                JOptionPane.showMessageDialog(this,
                        "Preencha CEP, rua, bairro, descrição e categoria.",
                        "Campos obrigatórios",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            salvarProblema(cep, rua, bairro, estado, descricao, categoria, risco, imagemSelecionada);
        });

        btnVoltar.addActionListener(e -> {
            dispose();
            TelaInicial.main(null);
        });

        JScrollPane scroll = new JScrollPane(conteudo);
        scroll.setBorder(null);
        setContentPane(scroll);
        Acessibilidade.registrarTela(this);
    }

    private void selecionarImagem() {
        JFileChooser seletor = new JFileChooser();
        seletor.setDialogTitle("Selecionar imagem do problema");
        seletor.setAcceptAllFileFilterUsed(false);
        seletor.setFileFilter(new FileNameExtensionFilter(
                "Imagens (*.png, *.jpg, *.jpeg, *.gif, *.bmp, *.webp)",
                "png", "jpg", "jpeg", "gif", "bmp", "webp"));

        if (seletor.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File arquivo = seletor.getSelectedFile();
            try {
                if (ImageIO.read(arquivo) == null) {
                    throw new IOException("O arquivo selecionado não é uma imagem válida.");
                }
                imagemSelecionada = arquivo;
                txtAnexo.setText(arquivo.getName());
                txtAnexo.setToolTipText(arquivo.getAbsolutePath());
            } catch (IOException ex) {
                imagemSelecionada = null;
                txtAnexo.setText("");
                JOptionPane.showMessageDialog(this,
                        "Não foi possível usar a imagem selecionada:\n" + ex.getMessage(),
                        "Imagem inválida",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void salvarProblema(String cep, String rua, String bairro, String estado,
                                String descricao, String categoria, boolean risco, File anexo) {
        String sqlEndereco = "INSERT INTO Endereco (end_bairro, end_rua, end_cep, end_estado) VALUES (?, ?, ?, ?)";
        String sqlProblema = "INSERT INTO Problema " +
                "(pro_descricao, pro_hora_envio, pro_local, pro_classificacao, pro_risco, pro_status, usu_id_fk, end_id_fk) " +
                "VALUES (?, NOW(), ?, ?, ?, 'Em análise', ?, ?)";

        Path anexoSalvo = null;

        try (Connection con = Conexao.conectar()) {
            con.setAutoCommit(false);

            try {
                int idEndereco;

                try (PreparedStatement stmtEndereco = con.prepareStatement(sqlEndereco, Statement.RETURN_GENERATED_KEYS)) {
                    stmtEndereco.setString(1, bairro);
                    stmtEndereco.setString(2, rua);
                    stmtEndereco.setString(3, cep);
                    stmtEndereco.setString(4, estado);
                    stmtEndereco.executeUpdate();

                    try (ResultSet chaves = stmtEndereco.getGeneratedKeys()) {
                        if (!chaves.next()) {
                            throw new SQLException("Não foi possível obter o ID do endereço cadastrado.");
                        }
                        idEndereco = chaves.getInt(1);
                    }
                }

                int idProblema;
                String classificacao = risco ? "Alta" : categoria;

                try (PreparedStatement stmtProblema = con.prepareStatement(sqlProblema, Statement.RETURN_GENERATED_KEYS)) {
                    stmtProblema.setString(1, descricao);
                    stmtProblema.setString(2, rua + " - " + bairro);
                    stmtProblema.setString(3, classificacao);
                    stmtProblema.setBoolean(4, risco);
                    stmtProblema.setInt(5, Usuario_conexao.getIdUsuario());
                    stmtProblema.setInt(6, idEndereco);
                    stmtProblema.executeUpdate();

                    try (ResultSet chaves = stmtProblema.getGeneratedKeys()) {
                        if (!chaves.next()) {
                            throw new SQLException("Não foi possível obter o ID do problema cadastrado.");
                        }
                        idProblema = chaves.getInt(1);
                    }
                }

                if (anexo != null) {
                    anexoSalvo = salvarAnexoLocal(anexo, idProblema);
                }

                con.commit();

                String mensagem = "Problema nº " + idProblema + " cadastrado com sucesso.\n" +
                        "Status inicial: Em análise.\n";
                if (anexoSalvo != null) {
                    mensagem += "\nImagem anexada!";
                }

                JOptionPane.showMessageDialog(this,
                        mensagem,
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
                dispose();
                new JanelaStatus().setVisible(true);

            } catch (SQLException | IOException ex) {
                con.rollback();
                if (anexoSalvo != null) {
                    try {
                        Files.deleteIfExists(anexoSalvo);
                    } catch (IOException ignored) {
                    }
                }
                throw ex;
            } finally {
                con.setAutoCommit(true);
            }

        } catch (SQLException | IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao salvar endereço, problema ou anexo:\n" + ex.getMessage(),
                    "Erro ao salvar",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private Path salvarAnexoLocal(File arquivo, int idProblema) throws IOException {
        Path pasta = Path.of("anexos");
        Files.createDirectories(pasta);

        String nome = arquivo.getName();
        String extensao = "png";
        int ponto = nome.lastIndexOf('.');
        if (ponto >= 0 && ponto < nome.length() - 1) {
            extensao = nome.substring(ponto + 1).replaceAll("[^A-Za-z0-9]", "").toLowerCase();
        }
        if (extensao.isBlank()) {
            extensao = "png";
        }

        Path destino = pasta.resolve("problema_" + idProblema + "." + extensao);
        return Files.copy(arquivo.toPath(), destino, StandardCopyOption.REPLACE_EXISTING);
    }

    private static void adicionarCampo(JPanel painel, GridBagConstraints gbc, int linha,
                                       String texto, Component componente, ImageIcon icone) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.BOLD, 13));
        label.setForeground(new Color(0, 102, 204));

        if (icone != null) {
            label.setIcon(icone);
            label.setHorizontalTextPosition(SwingConstants.LEFT);
            label.setIconTextGap(7);
        }

        gbc.gridx = 0;
        gbc.gridy = linha;
        gbc.gridwidth = 1;
        gbc.weightx = 0.28;
        painel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.72;
        painel.add(componente, gbc);
    }

    private static void limitarCep(JTextField campo) {
        ((AbstractDocument) campo.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                String atual = fb.getDocument().getText(0, fb.getDocument().getLength());
                String novo = atual.substring(0, offset) + (text == null ? "" : text) + atual.substring(offset + length);
                novo = novo.replaceAll("[^0-9]", "");
                if (novo.length() <= 8) {
                    fb.replace(0, fb.getDocument().getLength(), novo, attrs);
                }
            }

            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                    throws BadLocationException {
                replace(fb, offset, 0, string, attr);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Formulario().setVisible(true));
    }
}

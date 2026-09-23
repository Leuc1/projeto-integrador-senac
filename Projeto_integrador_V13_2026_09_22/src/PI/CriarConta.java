package PI;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CriarConta extends JFrame {

    public CriarConta() {
        initialize();
    }

    private void initialize() {
        setTitle("Criar conta");
        setSize(500, 740);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(235, 242, 248));

        JLabel titulo = new JLabel("CRIAR CONTA", SwingConstants.CENTER);
        titulo.setBounds(100, 40, 300, 40);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setForeground(new Color(30, 80, 130));
        add(titulo);

        JPanel painel = new JPanel(null);
        painel.setBounds(50, 105, 400, 580);
        painel.setBackground(Color.WHITE);
        add(painel);

        JLabel lblNome = new JLabel("Nome completo");
        lblNome.setBounds(30, 20, 200, 25);
        painel.add(lblNome);

        JTextField txtNome = new JTextField();
        txtNome.setBounds(30, 45, 340, 35);
        painel.add(txtNome);

        JLabel lblCadastro = new JLabel("Cadastrar com:");
        lblCadastro.setBounds(30, 90, 150, 25);
        painel.add(lblCadastro);

        JRadioButton rbEmail = new JRadioButton("E-mail", true);
        rbEmail.setBounds(30, 115, 100, 30);
        rbEmail.setBackground(Color.WHITE);
        painel.add(rbEmail);

        JRadioButton rbCpf = new JRadioButton("CPF");
        rbCpf.setBounds(140, 115, 100, 30);
        rbCpf.setBackground(Color.WHITE);
        painel.add(rbCpf);

        ButtonGroup grupo = new ButtonGroup();
        grupo.add(rbEmail);
        grupo.add(rbCpf);

        JLabel lblIdentificador = new JLabel("E-mail");
        lblIdentificador.setBounds(30, 150, 200, 25);
        painel.add(lblIdentificador);

        JTextField txtIdentificador = new JTextField();
        txtIdentificador.setBounds(30, 175, 340, 35);
        painel.add(txtIdentificador);

        JLabel lblSenha = new JLabel("Senha");
        lblSenha.setBounds(30, 220, 200, 25);
        painel.add(lblSenha);

        JPasswordField txtSenha = new JPasswordField();
        txtSenha.setBounds(30, 245, 340, 35);
        painel.add(txtSenha);

        JLabel lblConfirmar = new JLabel("Confirmar senha");
        lblConfirmar.setBounds(30, 290, 200, 25);
        painel.add(lblConfirmar);

        JPasswordField txtConfirmar = new JPasswordField();
        txtConfirmar.setBounds(30, 315, 340, 35);
        painel.add(txtConfirmar);

        JLabel lblPalavraChave = new JLabel("Palavra-chave de recuperação");
        lblPalavraChave.setBounds(30, 360, 250, 25);
        painel.add(lblPalavraChave);

        JTextField txtPalavraChave = new JTextField();
        txtPalavraChave.setBounds(30, 385, 340, 35);
        painel.add(txtPalavraChave);

        JButton btnCriar = new JButton("CRIAR CONTA");
        btnCriar.setBounds(30, 445, 160, 40);
        btnCriar.setBackground(new Color(35, 110, 180));
        btnCriar.setForeground(Color.WHITE);
        painel.add(btnCriar);

        JButton btnLimpar = new JButton("LIMPAR");
        btnLimpar.setBounds(210, 445, 160, 40);
        painel.add(btnLimpar);

        rbEmail.addActionListener(e -> {
            lblIdentificador.setText("E-mail");
            txtIdentificador.setText("");
        });

        rbCpf.addActionListener(e -> {
            lblIdentificador.setText("CPF");
            txtIdentificador.setText("");
        });

        btnCriar.addActionListener(e -> {
            String nome = txtNome.getText().trim();
            String identificador = txtIdentificador.getText().trim();
            String senha = new String(txtSenha.getPassword());
            String confirmar = new String(txtConfirmar.getPassword());
            String palavraChave = txtPalavraChave.getText().trim();

            if (nome.isEmpty() || identificador.isEmpty() || senha.isEmpty() || confirmar.isEmpty() || palavraChave.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos.", "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!senha.equals(confirmar)) {
                JOptionPane.showMessageDialog(this, "As senhas não são iguais.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String sql = "INSERT INTO Usuario (usu_nome, usu_email, usu_cpf, usu_senha, usu_palavra_chave, usu_tipo) VALUES (?, ?, ?, ?, ?, 'comum')";

            try (Connection con = Conexao.conectar();
                 PreparedStatement stmt = con.prepareStatement(sql)) {

                stmt.setString(1, nome);
                stmt.setString(2, rbEmail.isSelected() ? identificador : null);
                stmt.setString(3, rbCpf.isSelected() ? identificador : null);
                stmt.setString(4, senha);
                stmt.setString(5, palavraChave);
                stmt.executeUpdate();

                JOptionPane.showMessageDialog(this, "Conta criada com sucesso!.");
                dispose();
                new TelaLogin().setVisible(true);

            } catch (SQLException ex) {
                String mensagem = ex.getErrorCode() == 1062
                        ? "Este e-mail ou CPF já está cadastrado."
                        : "Erro ao cadastrar usuário:\n" + ex.getMessage();
                JOptionPane.showMessageDialog(this, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnLimpar.addActionListener(e -> {
            txtNome.setText("");
            txtIdentificador.setText("");
            txtSenha.setText("");
            txtConfirmar.setText("");
            txtPalavraChave.setText("");
            rbEmail.setSelected(true);
            lblIdentificador.setText("E-mail");
        });

        Acessibilidade.adicionarBotao(this, painel, 105, 510, 190, 30);
        Acessibilidade.registrarTela(this);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CriarConta().setVisible(true));
    }
}

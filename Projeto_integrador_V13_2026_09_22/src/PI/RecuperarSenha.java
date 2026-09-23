package PI;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RecuperarSenha extends JFrame {

    public RecuperarSenha() {
        initialize();
    }

    private void initialize() {
        setTitle("Recuperar senha");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(235, 242, 248));

        JLabel titulo = new JLabel("RECUPERAR SENHA", SwingConstants.CENTER);
        titulo.setBounds(80, 30, 340, 40);
        titulo.setFont(new Font("Arial", Font.BOLD, 25));
        titulo.setForeground(new Color(30, 80, 130));
        add(titulo);

        JPanel painel = new JPanel(null);
        painel.setBounds(50, 90, 400, 420);
        painel.setBackground(Color.WHITE);
        add(painel);

        JLabel lblTipo = new JLabel("Localizar conta por:");
        lblTipo.setBounds(30, 20, 180, 25);
        painel.add(lblTipo);

        JRadioButton rbEmail = new JRadioButton("E-mail", true);
        rbEmail.setBounds(30, 45, 100, 30);
        rbEmail.setBackground(Color.WHITE);
        painel.add(rbEmail);

        JRadioButton rbCpf = new JRadioButton("CPF");
        rbCpf.setBounds(140, 45, 100, 30);
        rbCpf.setBackground(Color.WHITE);
        painel.add(rbCpf);

        ButtonGroup grupo = new ButtonGroup();
        grupo.add(rbEmail);
        grupo.add(rbCpf);

        JLabel lblIdentificador = new JLabel("E-mail");
        lblIdentificador.setBounds(30, 85, 200, 25);
        painel.add(lblIdentificador);

        JTextField txtIdentificador = new JTextField();
        txtIdentificador.setBounds(30, 110, 340, 35);
        painel.add(txtIdentificador);

        JLabel lblPalavraChave = new JLabel("Palavra-chave de recuperação");
        lblPalavraChave.setBounds(30, 160, 250, 25);
        painel.add(lblPalavraChave);

        JTextField txtPalavraChave = new JTextField();
        txtPalavraChave.setBounds(30, 185, 340, 35);
        painel.add(txtPalavraChave);

        JLabel lblNovaSenha = new JLabel("Nova senha");
        lblNovaSenha.setBounds(30, 235, 200, 25);
        painel.add(lblNovaSenha);

        JPasswordField txtNovaSenha = new JPasswordField();
        txtNovaSenha.setBounds(30, 260, 340, 35);
        painel.add(txtNovaSenha);

        JLabel lblConfirmar = new JLabel("Confirmar nova senha");
        lblConfirmar.setBounds(30, 305, 200, 25);
        painel.add(lblConfirmar);

        JPasswordField txtConfirmar = new JPasswordField();
        txtConfirmar.setBounds(30, 330, 340, 35);
        painel.add(txtConfirmar);

        JButton btnRedefinir = new JButton("REDEFINIR SENHA");
        btnRedefinir.setBounds(105, 375, 190, 35);
        btnRedefinir.setBackground(new Color(35, 110, 180));
        btnRedefinir.setForeground(Color.WHITE);
        painel.add(btnRedefinir);

        rbEmail.addActionListener(e -> {
            lblIdentificador.setText("E-mail");
            txtIdentificador.setText("");
        });

        rbCpf.addActionListener(e -> {
            lblIdentificador.setText("CPF");
            txtIdentificador.setText("");
        });

        btnRedefinir.addActionListener(e -> {
            String identificador = txtIdentificador.getText().trim();
            String palavraChave = txtPalavraChave.getText().trim();
            String novaSenha = new String(txtNovaSenha.getPassword());
            String confirmar = new String(txtConfirmar.getPassword());

            if (identificador.isEmpty() || palavraChave.isEmpty() || novaSenha.isEmpty() || confirmar.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos.", "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!novaSenha.equals(confirmar)) {
                JOptionPane.showMessageDialog(this, "As novas senhas não são iguais.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String coluna = rbEmail.isSelected() ? "usu_email" : "usu_cpf";
            String sqlBusca = "SELECT usu_id_pk FROM Usuario WHERE " + coluna + " = ? AND usu_palavra_chave = ?";
            String sqlAtualiza = "UPDATE Usuario SET usu_senha = ? WHERE usu_id_pk = ?";

            try (Connection con = Conexao.conectar();
                 PreparedStatement busca = con.prepareStatement(sqlBusca)) {

                busca.setString(1, identificador);
                busca.setString(2, palavraChave);

                try (ResultSet rs = busca.executeQuery()) {
                    if (!rs.next()) {
                        JOptionPane.showMessageDialog(this,
                                "Conta não encontrada ou palavra-chave incorreta.",
                                "Dados inválidos",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    int idUsuario = rs.getInt("usu_id_pk");
                    try (PreparedStatement atualiza = con.prepareStatement(sqlAtualiza)) {
                        atualiza.setString(1, novaSenha);
                        atualiza.setInt(2, idUsuario);
                        atualiza.executeUpdate();
                    }
                }

                JOptionPane.showMessageDialog(this,
                        "Senha redefinida com sucesso. Você já pode entrar com a nova senha.",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
                dispose();

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this,
                        "Erro ao recuperar a senha:\n" + ex.getMessage(),
                        "Erro de banco de dados",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        Acessibilidade.registrarTela(this);
    }
}

package PI;
/* novo */

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TelaLogin extends JFrame {

    public TelaLogin() {
        initialize();

        // ===== ÍCONE DO USUÁRIO =====
        JPanel iconeUsuario = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g;

                g2.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON
                );

                g2.setColor(new Color(180, 195, 210));

                g2.fillOval(
                        28, 0, 34, 34
                );

                g2.fillRoundRect(
                        15, 32, 60, 25,
                        20, 20
                );

                g2.setColor(new Color(30, 80, 130));

                g2.fillOval(
                        34, 6, 22, 22
                );

                g2.fillRoundRect(
                        23, 35, 44, 20,
                        15, 15
                );
            }
        };

        iconeUsuario.setBounds(
                205, 0, 90, 60
        );

        iconeUsuario.setOpaque(false);

        add(iconeUsuario);

        // ===== TÍTULO ZURB =====
        JLabel tituloZurb = new JLabel("ZURB");

        tituloZurb.setBounds(
                125, 55, 250, 40
        );

        tituloZurb.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        tituloZurb.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28
                )
        );

        tituloZurb.setForeground(
                new Color(30, 80, 130)
        );

        add(tituloZurb);
    }

    private void initialize() {

        setTitle("Fazer Login");
        setSize(500, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        getContentPane().setBackground(new Color(235, 242, 248));

        // ===== PAINEL BRANCO =====
        JPanel painel = new JPanel();
        painel.setBounds(50, 115, 400, 480);
        painel.setBackground(Color.WHITE);
        painel.setLayout(null);
        add(painel);

        // ===== TIPO DE LOGIN =====
        JLabel lblTipo = new JLabel("Entrar com:");

        lblTipo.setBounds(30, 25, 150, 25);
        lblTipo.setFont(new Font("Arial", Font.BOLD, 14));

        painel.add(lblTipo);

        JRadioButton rbEmail = new JRadioButton("E-mail");

        rbEmail.setBounds(30, 50, 100, 30);
        rbEmail.setBackground(Color.WHITE);
        rbEmail.setSelected(true);

        painel.add(rbEmail);

        JRadioButton rbCpf = new JRadioButton("CPF");

        rbCpf.setBounds(140, 50, 100, 30);
        rbCpf.setBackground(Color.WHITE);

        painel.add(rbCpf);

        ButtonGroup grupo = new ButtonGroup();

        grupo.add(rbEmail);
        grupo.add(rbCpf);

        // ===== CAMPO EMAIL =====
        JLabel lblEmail = new JLabel("E-mail");

        lblEmail.setBounds(30, 90, 200, 25);
        lblEmail.setFont(new Font("Arial", Font.BOLD, 14));

        painel.add(lblEmail);

        JTextField txtEmail = new JTextField();

        txtEmail.setBounds(30, 115, 340, 35);
        txtEmail.setFont(new Font("Arial", Font.PLAIN, 14));

        painel.add(txtEmail);

        // ===== CAMPO CPF =====
        JLabel lblCpf = new JLabel("CPF");

        lblCpf.setBounds(30, 90, 200, 25);
        lblCpf.setFont(new Font("Arial", Font.BOLD, 14));

        painel.add(lblCpf);

        JTextField txtCpf = new JTextField();

        txtCpf.setBounds(30, 115, 340, 35);
        txtCpf.setFont(new Font("Arial", Font.PLAIN, 14));

        painel.add(txtCpf);

        lblCpf.setVisible(false);
        txtCpf.setVisible(false);

        // ===== CAMPO SENHA =====
        JLabel lblSenha = new JLabel("Senha");

        lblSenha.setBounds(30, 165, 200, 25);
        lblSenha.setFont(new Font("Arial", Font.BOLD, 14));

        painel.add(lblSenha);

        JPasswordField txtSenha = new JPasswordField();

        txtSenha.setBounds(30, 190, 340, 35);

        painel.add(txtSenha);

        // ===== LEMBRAR / ESQUECI SENHA =====
        JCheckBox chkLembrar = new JCheckBox("Lembrar meu usuário");
        chkLembrar.setBounds(30, 230, 170, 25);
        chkLembrar.setBackground(Color.WHITE);
        chkLembrar.setFont(new Font("Arial", Font.PLAIN, 12));
        painel.add(chkLembrar);

        JLabel lblEsqueciSenha = new JLabel("Esqueceu a senha?");
        lblEsqueciSenha.setBounds(270, 230, 180, 25);
        lblEsqueciSenha.setFont(new Font("Arial", Font.PLAIN, 11));
        lblEsqueciSenha.setForeground(new Color(120, 120, 120));
        lblEsqueciSenha.setCursor(new Cursor(Cursor.HAND_CURSOR));
        painel.add(lblEsqueciSenha);

        // ===== BOTÃO ENTRAR =====
        JButton btnEntrar = new JButton("ENTRAR");

        btnEntrar.setBounds(30, 265, 340, 40);
        btnEntrar.setFont(new Font("Arial", Font.BOLD, 13));
        btnEntrar.setBackground(new Color(35, 110, 180));
        btnEntrar.setForeground(Color.WHITE);
        btnEntrar.setFocusPainted(false);
        btnEntrar.setBorderPainted(false);

        painel.add(btnEntrar);

        // ===== LINK DE CADASTRO =====
        JLabel lblCadastro = new JLabel(
                "<html><u>Não possui uma conta? Cadastre-se</u></html>"
        );

        lblCadastro.setBounds(90, 310, 250, 30);
        lblCadastro.setFont(new Font("Arial", Font.PLAIN, 13));
        lblCadastro.setForeground(new Color(35, 110, 180));
        lblCadastro.setCursor(new Cursor(Cursor.HAND_CURSOR));

        painel.add(lblCadastro);

        // ===== ALTERNÂNCIA EMAIL / CPF =====
        rbEmail.addActionListener(e -> {

            lblEmail.setVisible(true);
            txtEmail.setVisible(true);

            lblCpf.setVisible(false);
            txtCpf.setVisible(false);
        });

        rbCpf.addActionListener(e -> {

            lblCpf.setVisible(true);
            txtCpf.setVisible(true);

            lblEmail.setVisible(false);
            txtEmail.setVisible(false);
        });

        // ===== AÇÃO DO BOTÃO ENTRAR =====
        btnEntrar.addActionListener(e -> {

            String senha = new String(txtSenha.getPassword());
            String identificador;
            String colunaBusca;

            if (rbEmail.isSelected()) {
                identificador = txtEmail.getText();
                colunaBusca = "usu_email";

                if (identificador.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Digite seu e-mail!",
                            "Atenção",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }
            } else {
                identificador = txtCpf.getText();
                colunaBusca = "usu_cpf";

                if (identificador.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Digite seu CPF!",
                            "Atenção",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }
            }

            if (senha.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Digite sua senha!",
                        "Atenção",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            // Consulta que busca as credenciais e o tipo da conta
            String sql = "SELECT usu_id_pk, usu_nome, usu_tipo FROM Usuario WHERE " + colunaBusca + " = ? AND usu_senha = ?";

            try (Connection con = Conexao.conectar();
                 PreparedStatement stmt = con.prepareStatement(sql)) {

                stmt.setString(1, identificador);
                stmt.setString(2, senha);

                try (ResultSet rs = stmt.executeQuery()) {

                    if (rs.next()) {
                        int idUsuario = rs.getInt("usu_id_pk");
                        String nome = rs.getString("usu_nome");
                        String tipo = rs.getString("usu_tipo");

                        Usuario_conexao.iniciar(idUsuario, nome, tipo);

                        // Verifica se o usuário é Administrador
                        if ("admin".equalsIgnoreCase(tipo)) {
                            JOptionPane.showMessageDialog(
                                    this,
                                    "Bem-vindo(a) Administrador(a) !",
                                    "Acesso Administrador",
                                    JOptionPane.INFORMATION_MESSAGE
                            );

                            dispose();
                            // Abre a tela de administrador
                            new Instrucoes_de_uso().setVisible(true);

                        } else {
                            JOptionPane.showMessageDialog(
                                    this,
                                    "Login realizado com sucesso! Bem-vindo(a) " + nome + ".",
                                    "Sucesso",
                                    JOptionPane.INFORMATION_MESSAGE
                            );

                            dispose();
                            // Abre a tela normal do usuário comum
                            new Instrucoes_de_uso().setVisible(true);
                        }

                    } else {
                        JOptionPane.showMessageDialog(
                                this,
                                "Usuário não encontrado ou senha incorreta.\n"
                                        + "Verifique os dados ou faça seu cadastro!",
                                "Erro de Login",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Erro ao consultar o banco de dados:\n" + ex.getMessage(),
                        "Erro de Banco de Dados",
                        JOptionPane.ERROR_MESSAGE
                );
                ex.printStackTrace();
            }
        });

        // ===== LINK ESQUECEU A SENHA =====
        lblEsqueciSenha.addMouseListener(
                new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent e) {
                        new RecuperarSenha().setVisible(true);
                    }
                }
        );

        // ===== LINK DE CADASTRO (CLIQUE) =====
        lblCadastro.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            java.awt.event.MouseEvent e) {

                        dispose();

                        new CriarConta().setVisible(true);
                    }
                }
        );

        Acessibilidade.adicionarBotao(this, painel, 105, 395, 190, 30);
        Acessibilidade.registrarTela(this);
    }

    // ===== MÉTODO PARA CRIAR O ADMIN PADRÃO AUTOMATICAMENTE =====
    public static void verificarECriarAdminPadrao() {
        String sqlVerifica = "SELECT COUNT(*) FROM Usuario WHERE usu_tipo = 'admin'";
        String sqlInsere = "INSERT INTO Usuario (usu_nome, usu_email, usu_cpf, usu_senha, usu_palavra_chave, usu_tipo) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = Conexao.conectar();
             PreparedStatement stmtCheck = con.prepareStatement(sqlVerifica);
             ResultSet rs = stmtCheck.executeQuery()) {

            if (rs.next() && rs.getInt(1) == 0) {
                // Se nenhum admin for encontrado no banco, cria o admin padrão
                try (PreparedStatement stmtInsert = con.prepareStatement(sqlInsere)) {
                    stmtInsert.setString(1, "Administrador Zurb");
                    stmtInsert.setString(2, "admin@zurb.com");
                    stmtInsert.setString(3, "AdminZurb123");
                    stmtInsert.setString(4, "Zurb@123");
                    stmtInsert.setString(5, "ZurbAdminRecuperacao");
                    stmtInsert.setString(6, "admin");

                    stmtInsert.executeUpdate();
                    System.out.println("-> Admin padrão gerado com sucesso!");
                    System.out.println("   Login: admin@zurb.com | Senha: Zurb@123 | Palavra-Chave: ZurbAdminRecuperacao");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao verificar/criar administrador padrão: " + e.getMessage());
        }
    }

    public static void main(String[] args) {

        // Garante que existe pelo menos 1 administrador no banco ao iniciar
        verificarECriarAdminPadrao();

        SwingUtilities.invokeLater(() -> {
            new TelaLogin().setVisible(true);
        });
    }
}
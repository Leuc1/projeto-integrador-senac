package PI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class TelaInicial {

    private JFrame frame;

    // =====================================================
    // MAIN
    // =====================================================

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {

            public void run() {

                try {

                    TelaInicial window = new TelaInicial();

                    window.frame.setVisible(true);

                } catch (Exception e) {

                    e.printStackTrace();

                }

            }

        });

    }

    // =====================================================
    // CONSTRUTOR
    // =====================================================

    public TelaInicial() {

        initialize();

    }

    // =====================================================
    // CRIAÇÃO DA TELA
    // =====================================================

    private void initialize() {

        // =================================================
        // JANELA
        // =================================================

        frame = new JFrame();
        frame.setTitle("Tela Inicial");
        frame.setSize(500, 650);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        // =================================================
        // MENU
        // =================================================

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        JMenu menuAjuda = new JMenu("Ajuda");
        menuBar.add(menuAjuda);
        JMenuItem itemSobre =
                new JMenuItem("Sobre o Sistema");
        menuAjuda.add(itemSobre);

        // Ação do menu Sobre

        itemSobre.addActionListener(e -> {

            abrirTelaSobre();

        });

        // =================================================
        // PAINEL PRINCIPAL (COM IMAGEM DE FUNDO)
        // =================================================

        // Véu branco semitransparente (200 = ~78% opacidade)
        // Aumente o alpha (0–255) se o texto ficar ilegível.
        // Se NÃO quiser véu, passe null no segundo argumento.
        Color veuBranco = new Color(255, 255, 255, 200);

        PainelComFundo panel = new PainelComFundo(
                "/icons/fundo_tela_inicial.png"
        		//,
                //veuBranco
        );

        panel.setLayout(null);
        frame.setContentPane(panel);

        // =================================================
        // TÍTULO
        // =================================================

        JLabel lblTitulo = new JLabel("TELA INICIAL");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(135, 25, 230, 40);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(30, 80, 130));
        panel.add(lblTitulo);

        // =================================================
        // SUBTÍTULO
        // =================================================

        JLabel lblSubtitulo = new JLabel("Escolha uma opção para continuar");
        lblSubtitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblSubtitulo.setBounds(100, 65, 300, 25);
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 13));
        lblSubtitulo.setForeground(new Color(90, 90, 90));
        panel.add(lblSubtitulo);

        // =================================================
        // PAINEL INTERNO (TRANSPARENTE PARA MOSTRAR O FUNDO)
        // =================================================
        //
        // Caminho A — imagem visível atrás dos botões:
        //     painel.setOpaque(false);
        //
        // Caminho B — caixa branca atrás dos botões (atual):
        //     painel.setBackground(Color.WHITE);
        //
        // Estamos usando o Caminho B: mantém a caixa branca
        // para garantir legibilidade dos botões.
        //
        // Se quiser mudar para o Caminho A, substitua as duas
        // linhas abaixo por: painel.setOpaque(false);

        JPanel painel = new JPanel();
        painel.setBounds(50, 105, 400, 480);
        painel.setOpaque(false);
        painel.setLayout(null);
        panel.add(painel);

        // =================================================
        // BOTÃO INSTRUÇÕES DE USO
        // =================================================

        JButton btnInstrucoes = new JButton("Instruções de uso");
        btnInstrucoes.setBounds(30, 30, 340, 45);
        btnInstrucoes.setFont(new Font("Arial", Font.BOLD, 14));
        btnInstrucoes.setBackground(new Color(35, 110, 180));
        btnInstrucoes.setForeground(Color.WHITE);
        btnInstrucoes.setFocusPainted(false);
        btnInstrucoes.setBorderPainted(false);
        painel.add(btnInstrucoes);

        // Ação do botão

        btnInstrucoes.addActionListener(e -> {

            Instrucoes_de_uso instrucoes =
                    new Instrucoes_de_uso();

            instrucoes.setVisible(true);

        });

        // =================================================
        // BOTÃO CADASTRAR PROBLEMA
        // =================================================

        JButton btnCadastrar = new JButton("Cadastrar problema");
        btnCadastrar.setBounds(
                30,
                95,
                340,
                45
        );
        btnCadastrar.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );
        btnCadastrar.setBackground(new Color(35, 110, 180));
        btnCadastrar.setForeground(Color.WHITE);
        btnCadastrar.setFocusPainted(false);
        btnCadastrar.setBorderPainted(false);
        painel.add(btnCadastrar);

        // Ação do botão

        btnCadastrar.addActionListener(e -> {

            Formulario formulario =
                    new Formulario();

            formulario.setVisible(true);

        });

        // =================================================
        // BOTÃO VER RELATÓRIO
        // =================================================

        JButton btnRelatorio =
                new JButton("Ver relatório");

        btnRelatorio.setBounds(
                30,
                160,
                340,
                45
        );

        btnRelatorio.setFont(new Font("Arial", Font.BOLD, 14));
        btnRelatorio.setBackground(new Color(35, 110, 180));
        btnRelatorio.setForeground(Color.WHITE);
        btnRelatorio.setFocusPainted(false);
        btnRelatorio.setBorderPainted(false);
        painel.add(btnRelatorio);

        // Ação do botão

        btnRelatorio.addActionListener(e -> {

            JanelaStatus janelaStatus =
                    new JanelaStatus();

            janelaStatus.setVisible(true);

        });

        // =================================================
        // BOTÃO SOBRE
        // =================================================

        JButton btnSobre = new JButton("Sobre");
        btnSobre.setBounds(30, 225, 340, 45);
        btnSobre.setFont(new Font("Arial", Font.BOLD, 14));
        btnSobre.setBackground(new Color(35, 110, 180));
        btnSobre.setForeground(Color.WHITE);
        btnSobre.setFocusPainted(false);
        btnSobre.setBorderPainted(false);
        painel.add(btnSobre);

        // Ação do botão

        btnSobre.addActionListener(e -> {

            abrirTelaSobre();

        });

        // =================================================
        // BOTÃO ACESSIBILIDADE
        // =================================================

        JButton btnAcessibilidade = Acessibilidade.criarBotao(frame);
        btnAcessibilidade.setBounds(30, 300, 340, 40);
        painel.add(btnAcessibilidade);

        // =================================================
        // APLICAR ACESSIBILIDADE
        // =================================================

        Acessibilidade.aplicar(frame);

        // =================================================
        // CENTRALIZAR
        // =================================================

        frame.setLocationRelativeTo(null);
    }

    // =====================================================
    // TELA SOBRE
    // =====================================================

    private void abrirTelaSobre() {

        TelaSobre telaSobre =
                new TelaSobre();

        telaSobre.setVisible(true);

    }
}
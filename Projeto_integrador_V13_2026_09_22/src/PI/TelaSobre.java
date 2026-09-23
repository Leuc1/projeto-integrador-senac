package PI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JScrollPane;

public class TelaSobre {

    private JFrame frame;

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {

            public void run() {

                try {

                    TelaSobre window = new TelaSobre();

                    window.frame.setVisible(true);

                } catch (Exception e) {

                    e.printStackTrace();

                }
            }
        });
    }

    public TelaSobre() {

        initialize();

    }

    private void initialize() {

        // ==========================================
        // JANELA
        // ==========================================

        frame = new JFrame();

        frame.setTitle("Sobre o Projeto Zurb");

        frame.setBounds(100, 100, 750, 650);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.setLocationRelativeTo(null);

        // ==========================================
        // PAINEL
        // ==========================================

        JPanel panel = new JPanel();

        panel.setBackground(Color.WHITE);

        panel.setLayout(null);

        frame.setContentPane(panel);

        // ==========================================
        // TÍTULO
        // ==========================================

        JLabel lblTitulo = new JLabel("Sobre o Projeto Zurb");

        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        lblTitulo.setForeground(new Color(0, 102, 204));

        lblTitulo.setFont(new Font("Arial", Font.BOLD, 30));

        lblTitulo.setBounds(50, 25, 650, 45);

        panel.add(lblTitulo);

        // ==========================================
        // SUBTÍTULO
        // ==========================================

        JLabel lblSubtitulo = new JLabel(
                "Tecnologia e participação cidadã para uma cidade melhor");

        lblSubtitulo.setHorizontalAlignment(SwingConstants.CENTER);

        lblSubtitulo.setForeground(new Color(80, 80, 80));

        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 15));

        lblSubtitulo.setBounds(50, 70, 650, 30);

        panel.add(lblSubtitulo);

        // ==========================================
        // ÁREA DE INFORMAÇÕES
        // ==========================================

        JTextArea txtInformacoes = new JTextArea();

        txtInformacoes.setText(

                "PROJETO ZURB\n\n"

                + "O Projeto Zurb é um sistema desktop desenvolvido com o "
                + "objetivo de contribuir para a identificação e o "
                + "encaminhamento de problemas relacionados à infraestrutura "
                + "urbana das cidades.\n\n"

                + "O sistema utiliza a participação dos cidadãos como uma "
                + "das principais fontes de informação. Por meio da plataforma, "
                + "o usuário pode informar um problema encontrado em sua "
                + "região, descrevendo a ocorrência e fornecendo informações "
                + "que auxiliem na identificação do local.\n\n"

                + "COMO O SISTEMA FUNCIONA\n\n"

                + "O usuário acessa o sistema, realiza seu cadastro ou login "
                + "e, posteriormente, pode registrar um problema urbano. "
                + "Durante o registro, são informados dados como o tipo do "
                + "problema, sua prioridade, uma descrição da ocorrência, "
                + "uma fotografia e o endereço onde o problema foi identificado. "
                + "Também podem ser utilizadas informações de localização "
                + "geográfica para auxiliar na identificação precisa do local.\n\n"

                + "Após o envio, os dados passam por uma etapa de triagem. "
                + "A equipe responsável analisa as informações fornecidas "
                + "pelo cidadão e verifica qual órgão ou setor público possui "
                + "competência para solucionar aquela ocorrência. "
                + "Posteriormente, o problema pode ser encaminhado ao órgão "
                + "competente para que sejam tomadas as providências necessárias.\n\n"

                + "OBJETIVO DO PROJETO\n\n"

                + "O principal objetivo é utilizar a tecnologia para aproximar "
                + "os cidadãos dos processos de identificação de problemas "
                + "urbanos, contribuindo para uma comunicação mais organizada "
                + "e para o direcionamento das ocorrências aos responsáveis "
                + "pela infraestrutura pública.\n\n"

                + "FUNCIONALIDADES DESENVOLVIDAS\n\n"

                + "- Tela de boas-vindas;\n"
                + "- Cadastro de usuários;\n"
                + "- Login no sistema;\n"
                + "- Instruções de uso;\n"
                + "- Cadastro e descrição de problemas;\n"
                + "- Anexação de fotografias;\n"
                + "- Informações de endereço;\n"
                + "- Informações de localização;\n"
                + "- Registro de prioridade;\n"
                + "- Consulta e visualização de relatórios.\n\n"

                + "TECNOLOGIAS E FERRAMENTAS\n\n"

                + "Para o desenvolvimento do projeto foram utilizadas "
                + "tecnologias e ferramentas como Java, Eclipse e MySQL, "
                + "além de ferramentas de planejamento, documentação, "
                + "prototipação e apoio ao desenvolvimento.\n\n"

                + "Entre as ferramentas utilizadas estão:\n"

                + "- Java;\n"
                + "- Eclipse;\n"
                + "- MySQL;\n"
                + "- Canva;\n"
                + "- Gemini;\n"
                + "- ChatGPT;\n"
                + "- Trello;\n"
                + "- BR Modelo;\n"
                + "- Excel;\n"
                + "- WhatsApp;\n"
                + "- GitHub.\n\n"

                + "INTEGRANTES\n\n"

                + "Adriele\n"
                + "Jeferson\n"
                + "Joilson\n"
                + "Leticia\n"
                + "Luis\n\n"
                + "Hugo\n\n"

                + "DOCENTES\n\n"

                + "André Nascimento — docente principal responsável pelo "
                + "acompanhamento e orientação do projeto.\n"

                + "Ridis — docente que acompanhou os testes de software durante "
                + "o desenvolvimento do projeto.\n\n"

                + "VISÃO DE FUTURO\n\n"

                + "Como evolução do Projeto Zurb, o sistema poderá futuramente "
                + "receber novas funcionalidades, como integração com mapas, "
                + "utilização de geolocalização automática, aplicativo para "
                + "dispositivos móveis, painéis administrativos, gráficos, "
                + "notificações e integração com diferentes órgãos públicos.\n\n"

                + "O Projeto Zurb busca, portanto, demonstrar como a "
                + "tecnologia pode ser utilizada como ferramenta de apoio "
                + "à participação cidadã e à melhoria da infraestrutura "
                + "urbana."
        );

        // ==========================================
        // CONFIGURAÇÕES DO TEXTO
        // ==========================================

        txtInformacoes.setFont(
                new Font("Arial", Font.PLAIN, 15)
        );

        txtInformacoes.setEditable(false);

        txtInformacoes.setBackground(Color.WHITE);

        txtInformacoes.setLineWrap(true);

        txtInformacoes.setWrapStyleWord(true);

        // ==========================================
        // SCROLL
        // ==========================================

        JScrollPane scrollPane = new JScrollPane(txtInformacoes);

        scrollPane.setBounds(50, 110, 650, 430);

        panel.add(scrollPane);

        // ==========================================
        // BOTÃO VOLTAR
        // ==========================================

        JButton btnVoltar = new JButton("Voltar");

        btnVoltar.setBounds(275, 560, 200, 45);

        btnVoltar.setBackground(new Color(0, 102, 204));

        btnVoltar.setForeground(Color.WHITE);

        btnVoltar.setFont(new Font("Arial", Font.BOLD, 15));

        btnVoltar.setFocusPainted(false);

        btnVoltar.setBorderPainted(false);

        panel.add(btnVoltar);

        // ==========================================
        // AÇÃO DO BOTÃO VOLTAR
        // ==========================================

        btnVoltar.addActionListener(
                new java.awt.event.ActionListener() {

                    public void actionPerformed(
                            java.awt.event.ActionEvent e) {

                        frame.dispose();

                    }
                }
        );
    }

    // ==========================================
    // MÉTODO SETVISIBLE
    // ==========================================

    public void setVisible(boolean visible) {

        frame.setVisible(visible);

    }

}

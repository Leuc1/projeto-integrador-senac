package PI;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

public class Instrucoes_de_uso extends JFrame {

	public Instrucoes_de_uso() {
		setTitle("Como usar o sistema ");
		setSize(700, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null); 

		JPanel painelPrincipal = new JPanel();
		painelPrincipal.setLayout(new BorderLayout(0, 0));
		painelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));
		painelPrincipal.setBackground(new Color (232, 242, 248));

		JLabel titulo = new JLabel("Bem-vindo! Veja como registrar seu problema", SwingConstants.CENTER);
		titulo.setFont(new Font("Arial", Font.BOLD, 25));
		titulo.setForeground(new Color(100,30,180));
	
		painelPrincipal.add(titulo, BorderLayout.NORTH);

		JPanel painelEtapas = new JPanel();
		painelEtapas.setLayout(new BoxLayout(painelEtapas, BoxLayout.Y_AXIS));
		painelEtapas.setForeground( new Color(100,30,180));
		painelEtapas.setBackground(new Color (232, 242, 248));
		painelEtapas.setOpaque(true);

		painelEtapas.add(criarBlocoEtapa(
				"1. Escolha a categoria",
				"Selecione a categoria que melhor descreve o problema encontrado: "
						+ "Acessibilidade, Infraestrutura, outros."
				));

		painelEtapas.add(Box.createRigidArea(new Dimension(0, 20)));
		
		painelEtapas.add(criarBlocoEtapa(
				"2. como funcina a classificação de prioridades :",
				"Se um problema pode provocar um acidente, será classificado como alto."
				));

		painelEtapas.add(Box.createRigidArea(new Dimension(0, 20)));

		painelEtapas.add(criarBlocoEtapa(
				"3. Envie fotos, vídeos e descrição",
				"Anexe fotos ou vídeos do local e escreva uma breve descrição explicando "
						+ "o que está acontecendo. Depois, toque em 'Enviar'."
				));

		painelEtapas.add(Box.createRigidArea(new Dimension(0, 20)));

		painelEtapas.add(criarBlocoEtapa(
				"4. Acompanhe o andamento",
				"Sua solicitação passará pelos status:  Em Análise → "
						+ "Encaminhado ao Órgão Público → Resolvido. Você pode consultar "
						+ "esse andamento a qualquer momento na tela de acompanhamento."
				));
		
		painelEtapas.add(Box.createRigidArea(new Dimension(0, 20)));

		JScrollPane scroll = new JScrollPane(painelEtapas);
		scroll.setBorder(null);
		scroll.getVerticalScrollBar().setUnitIncrement(12);
		painelPrincipal.add(scroll, BorderLayout.CENTER);

		JButton btnEntendi = new JButton("Entendi, começar a usar");
		btnEntendi.setBackground(new Color (35,115,180));
		btnEntendi.setForeground(Color.WHITE);
		btnEntendi.setOpaque(false);
		btnEntendi.setBorderPainted(false);
		btnEntendi.setFocusPainted(false);
		
		// Ação modificada para abrir a TelaInicial
		btnEntendi.addActionListener(e -> {
			dispose(); // Fecha a tela de instruções
			TelaInicial.main(new String[0]); // Abre a tela inicial
		});
		
		JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
		JButton btnAcessibilidade = Acessibilidade.criarBotao(this);
		painelBotao.add(btnEntendi);
		painelBotao.add(btnAcessibilidade);
		painelPrincipal.add(painelBotao, BorderLayout.SOUTH);
		painelBotao.setSize(0,10);
		painelBotao.setBackground(new Color (35, 110, 180));
		
		add(painelPrincipal);
		Acessibilidade.registrarTela(this);
	}

	private JPanel criarBlocoEtapa(String tituloEtapa, String descricao) {
		JPanel bloco = new JPanel();
		bloco.setLayout(new BorderLayout(5, 5));
		bloco.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(200, 200, 200)),
				new EmptyBorder(10, 10, 10, 10)
				));
		bloco.setAlignmentX(Component.LEFT_ALIGNMENT);
		bloco.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));

		JLabel labelTitulo = new JLabel(tituloEtapa);
		labelTitulo.setFont(new Font("Arial", Font.BOLD, 14));

		JTextArea textoDescricao = new JTextArea(descricao);
		textoDescricao.setWrapStyleWord(false);
		textoDescricao.setLineWrap(true);
		textoDescricao.setEditable(false);
		textoDescricao.setOpaque(true);
		textoDescricao.setFont(new Font("Arial", Font.PLAIN, 13));
		textoDescricao.setBackground(new Color (232, 242, 248));
		textoDescricao.setHighlighter(null);

		bloco.add(labelTitulo, BorderLayout.NORTH);
		bloco.add(textoDescricao, BorderLayout.CENTER);

		return bloco;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			Instrucoes_de_uso tela = new Instrucoes_de_uso();
			tela.setVisible(true);
		});
	}
}
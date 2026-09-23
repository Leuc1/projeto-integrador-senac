package PI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Window;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Acessibilidade {

	public enum Modo {
		PADRAO,
		BAIXA_VISAO,
		PROTANOPIA,
		DEUTERANOPIA,
		TRITANOPIA
	}

	private static Modo modoAtual = Modo.PADRAO;

	private static final String FONTE_ORIGINAL =
			"acessibilidadeFonteOriginal";

	private static final String BOTAO_ACESSIBILIDADE =
			"botaoAcessibilidade";

	private static final String COR_FUNDO_ORIGINAL =
			"acessibilidadeCorFundoOriginal";

	private static final String COR_TEXTO_ORIGINAL =
			"acessibilidadeCorTextoOriginal";

	private Acessibilidade() {
	}

	public static Modo getModoAtual() {

		return modoAtual;

	}

	public static void setModoAtual(Modo modo) {

		if (modo != null) {

			modoAtual = modo;

		}

	}

	public static Color getFundo() {

		switch (modoAtual) {

		case BAIXA_VISAO:
			return new Color(20, 20, 20);

		case PROTANOPIA:
			return new Color(230, 240, 250);

		case DEUTERANOPIA:
			return new Color(245, 240, 220);

		case TRITANOPIA:
			return new Color(245, 235, 245);

		default:
			return new Color(235, 242, 248);

		}

	}

	public static Color getPainel() {

		switch (modoAtual) {

		case BAIXA_VISAO:
			return new Color(40, 40, 40);

		default:
			return Color.WHITE;

		}

	}

	public static Color getTexto() {

		switch (modoAtual) {

		case BAIXA_VISAO:
			return Color.WHITE;

		default:
			return new Color(40, 40, 40);

		}

	}

	public static Color getBotao() {

		switch (modoAtual) {

		case PROTANOPIA:
			return new Color(0, 90, 160);

		case DEUTERANOPIA:
			return new Color(0, 100, 130);

		case TRITANOPIA:
			return new Color(120, 50, 130);

		default:
			return new Color(35, 110, 180);

		}

	}

	public static Color getTextoBotao() {

		return Color.WHITE;

	}

	public static Font aumentarFonte(Font fonte) {

		if (fonte == null) {

			fonte = new Font("Arial", Font.PLAIN, 14);

		}

		int tamanhoOriginal = fonte.getSize();

		int tamanho = tamanhoOriginal;

		if (modoAtual == Modo.BAIXA_VISAO) {

			tamanho = tamanhoOriginal + 5;

		}

		return fonte.deriveFont((float) tamanho);

	}

	private static void aplicarFonte(Component componente) {

		Font fonteOriginal =
				(Font) ((javax.swing.JComponent) componente)
				.getClientProperty(FONTE_ORIGINAL);

		if (fonteOriginal == null) {

			fonteOriginal = componente.getFont();

			((javax.swing.JComponent) componente)
			.putClientProperty(
					FONTE_ORIGINAL,
					fonteOriginal
					);

		}

		int tamanho = fonteOriginal.getSize();

		if (modoAtual == Modo.BAIXA_VISAO) {

			tamanho += 5;

		}

		componente.setFont(
				fonteOriginal.deriveFont((float) tamanho)
				);

	}

	public static void aplicar(Container container) {

		if (container == null) {

			return;

		}

		if (container instanceof javax.swing.JComponent) {

			javax.swing.JComponent jc =
					(javax.swing.JComponent) container;

			Color original =
					(Color) jc.getClientProperty(
							COR_FUNDO_ORIGINAL
							);

			if (original == null) {

				jc.putClientProperty(
						COR_FUNDO_ORIGINAL,
						container.getBackground()
						);

			}

			if (modoAtual == Modo.PADRAO) {

				container.setBackground(
						(Color) jc.getClientProperty(
								COR_FUNDO_ORIGINAL
								)
						);

			} else {

				container.setBackground(getFundo());

			}

		} else {

			container.setBackground(getFundo());

		}

		for (Component componente :
			container.getComponents()) {

			if (componente instanceof javax.swing.JComponent) {

				aplicarComponente(
						(javax.swing.JComponent) componente
						);

			}

			if (componente instanceof Container) {

				aplicar((Container) componente);

			}

		}

		container.revalidate();

		container.repaint();

	}

	private static Color corOriginalFundo(
			javax.swing.JComponent componente) {

		Color cor =
				(Color) componente.getClientProperty(
						COR_FUNDO_ORIGINAL
						);

		if (cor == null) {

			cor = componente.getBackground();

			componente.putClientProperty(
					COR_FUNDO_ORIGINAL,
					cor
					);

		}

		return cor;

	}

	private static Color corOriginalTexto(
			javax.swing.JComponent componente) {

		Color cor =
				(Color) componente.getClientProperty(
						COR_TEXTO_ORIGINAL
						);

		if (cor == null) {

			cor = componente.getForeground();

			componente.putClientProperty(
					COR_TEXTO_ORIGINAL,
					cor
					);

		}

		return cor;

	}

	private static void aplicarCores(
			javax.swing.JComponent componente,
			boolean botao) {

		Color fundoOriginal =
				corOriginalFundo(componente);

		Color textoOriginal =
				corOriginalTexto(componente);

		if (modoAtual == Modo.PADRAO) {

			componente.setBackground(fundoOriginal);

			componente.setForeground(textoOriginal);

			if (botao &&
					componente instanceof JButton) {

				((JButton) componente)
				.setContentAreaFilled(true);

			}

		} else if (botao) {

			componente.setBackground(getBotao());

			componente.setForeground(getTextoBotao());

			if (componente instanceof JButton) {

				JButton btn =
						(JButton) componente;

				btn.setContentAreaFilled(true);

				btn.setOpaque(true);

				btn.setBorder(
						javax.swing.BorderFactory
						.createLineBorder(
								Color.WHITE,
								1
								)
						);

			}

		} else if (componente instanceof JPanel) {

			componente.setBackground(getPainel());

		} else {

			componente.setBackground(getPainel());

			componente.setForeground(getTexto());

		}

	}

	private static void aplicarComponente(
			javax.swing.JComponent componente) {

		boolean ehBotao =
				componente instanceof JButton;

		aplicarCores(
				componente,
				ehBotao
				);

		if (componente instanceof JLabel
				|| componente instanceof JTextField
				|| componente instanceof JPasswordField
				|| componente instanceof JTextArea
				|| componente instanceof javax.swing.JRadioButton
				|| componente instanceof JComboBox
				|| componente instanceof JButton
				|| componente instanceof JTable) {

			aplicarFonte(componente);

		}

		if (componente instanceof JTextField) {

			((JTextField) componente)
			.setCaretColor(getTexto());

			if (modoAtual == Modo.PADRAO) {

				((JTextField) componente)
				.setCaretColor(
						corOriginalTexto(componente)
						);

			}

		}

		if (componente instanceof JTextArea) {

			((JTextArea) componente)
			.setCaretColor(getTexto());

			if (modoAtual == Modo.PADRAO) {

				((JTextArea) componente)
				.setCaretColor(
						corOriginalTexto(componente)
						);

			}

		}

		if (componente instanceof JTable) {

			JTable tabela =
					(JTable) componente;

			tabela.setBackground(
					modoAtual == Modo.PADRAO
					? corOriginalFundo(tabela)
							: getPainel()
					);

			tabela.setForeground(
					modoAtual == Modo.PADRAO
					? corOriginalTexto(tabela)
							: getTexto()
					);

			tabela.setSelectionBackground(
					getBotao()
					);

			tabela.setSelectionForeground(
					Color.WHITE
					);

			tabela.setGridColor(
					modoAtual == Modo.PADRAO
					? new Color(200, 200, 200)
							: new Color(160, 160, 160)
					);

			tabela.getTableHeader()
			.setBackground(
					modoAtual == Modo.PADRAO
					? tabela.getTableHeader()
							.getBackground()
							: getBotao()
					);

			tabela.getTableHeader()
			.setForeground(
					modoAtual == Modo.PADRAO
					? tabela.getTableHeader()
							.getForeground()
							: getTextoBotao()
					);

		}

	}

	public static JButton criarBotao(JFrame tela) {

		JButton botao =
				new JButton("ACESSIBILIDADE");

		botao.putClientProperty(
				BOTAO_ACESSIBILIDADE,
				Boolean.TRUE
				);

		botao.setFont(
				new Font(
						"Arial",
						Font.BOLD,
						12
						)
				);

		botao.setFocusPainted(false);

		botao.setBorderPainted(true);

		botao.setOpaque(true);

		botao.setCursor(
				new java.awt.Cursor(
						java.awt.Cursor.HAND_CURSOR
						)
				);

		botao.addActionListener(
				e -> mostrarMenu(tela)
				);

		return botao;

	}

	public static void adicionarBotao(
			JFrame tela,
			int x,
			int y,
			int largura,
			int altura) {

		if (tela == null) {

			return;

		}

		Container container =
				tela.getContentPane();

		for (Component componente :
			container.getComponents()) {

			if (componente instanceof JButton
					&& Boolean.TRUE.equals(
							((JButton) componente)
							.getClientProperty(
									BOTAO_ACESSIBILIDADE
									))) {

				return;

			}

		}

		JButton botao =
				criarBotao(tela);

		botao.setBounds(
				x,
				y,
				largura,
				altura
				);

		container.add(botao);

		aplicar(tela.getContentPane());

		tela.revalidate();

		tela.repaint();

	}

	public static void adicionarBotao(
			JFrame tela,
			JPanel painel,
			int x,
			int y,
			int largura,
			int altura) {

		if (tela == null || painel == null) {

			return;

		}

		for (Component componente :
			painel.getComponents()) {

			if (componente instanceof JButton
					&& Boolean.TRUE.equals(
							((JButton) componente)
							.getClientProperty(
									BOTAO_ACESSIBILIDADE
									))) {

				return;

			}

		}

		JButton botao =
				criarBotao(tela);

		botao.setBounds(
				x,
				y,
				largura,
				altura
				);

		painel.add(botao);

		aplicar(painel);

		painel.revalidate();

		painel.repaint();

	}

	public static void mostrarMenu(JFrame tela) {

		String[] opcoes = {

				"Modo padrão",

				"Baixa visão",

				"Daltonismo - Protanopia",

				"Daltonismo - Deuteranopia",

				"Daltonismo - Tritanopia"

		};

		int escolha =
				javax.swing.JOptionPane.showOptionDialog(

						tela,

						"Escolha um modo de acessibilidade:",

						"Acessibilidade",

						javax.swing.JOptionPane.DEFAULT_OPTION,

						javax.swing.JOptionPane.INFORMATION_MESSAGE,

						null,

						opcoes,

						opcoes[0]

						);

		if (escolha < 0) {

			return;

		}

		switch (escolha) {

		case 0:

			setModoAtual(
					Modo.PADRAO
					);

			break;

		case 1:

			setModoAtual(
					Modo.BAIXA_VISAO
					);

			break;

		case 2:

			setModoAtual(
					Modo.PROTANOPIA
					);

			break;

		case 3:

			setModoAtual(
					Modo.DEUTERANOPIA
					);

			break;

		case 4:

			setModoAtual(
					Modo.TRITANOPIA
					);

			break;

		default:

			break;

		}

		aplicarTodasAsTelas();

	}

	public static void aplicarTodasAsTelas() {

		for (Window janela :
			Window.getWindows()) {

			if (janela instanceof JFrame
					&& janela.isDisplayable()) {

				aplicar(
						(JFrame) janela
						);

			}

		}

		for (Window janela :
			Window.getWindows()) {

			if (janela.isDisplayable()) {

				janela.revalidate();

				janela.repaint();

			}

		}

	}

	public static void registrarTela(JFrame tela) {

		if (tela == null) {

			return;

		}

		aplicar(tela);

	}

}
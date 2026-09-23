package PI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * JPanel que pinta uma imagem de fundo, opcionalmente com um véu
 * semitransparente por cima para melhorar a legibilidade do texto.
 */
public class PainelComFundo extends JPanel {

    private Image imagemFundo;
    private Color veu;   // pode ser null (sem véu)

    /**
     * @param caminho ex: "/icons/fundo.png"
     * @param veu cor semitransparente por cima, ou null para não aplicar
     */
    public PainelComFundo(String caminho, Color veu) {
        this.veu = veu;

        URL url = getClass().getResource(caminho);
        if (url == null) {
            System.err.println("Imagem de fundo não encontrada: " + caminho);
        } else {
            this.imagemFundo = new ImageIcon(url).getImage();
        }
    }

    public PainelComFundo(String caminho) {
        this(caminho, null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (imagemFundo != null) {
            // Modo "crop": preenche a tela mantendo a proporção, cortando o excesso
            int w = getWidth();
            int h = getHeight();
            int imgW = imagemFundo.getWidth(this);
            int imgH = imagemFundo.getHeight(this);

            double escala = Math.max((double) w / imgW, (double) h / imgH);
            int novaLargura = (int) (imgW * escala);
            int novaAltura  = (int) (imgH * escala);

            int x = (w - novaLargura) / 2;
            int y = (h - novaAltura) / 2;

            g.drawImage(imagemFundo, x, y, novaLargura, novaAltura, this);
        }

        // Véu semitransparente por cima, se houver
        if (veu != null) {
            g.setColor(veu);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}
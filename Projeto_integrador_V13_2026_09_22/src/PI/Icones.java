package PI;

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 * Classe auxiliar para carregar os ícones da aplicação.
 * Os arquivos devem estar em src/icons/ (ex: src/icons/camera.png).
 */
public class Icones {

    /** Tamanho padrão dos ícones na tela. */
    public static final int TAMANHO_PADRAO = 20;

    
    public static ImageIcon carregar(String caminho, int largura, int altura) {
        URL url = Icones.class.getResource(caminho);
        if (url == null) {
            System.err.println("Ícone não encontrado: " + caminho);
            return null;
        }
        ImageIcon original = new ImageIcon(url);
        Image img = original.getImage().getScaledInstance(
                largura, altura, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    /** Carrega no tamanho padrão (20x20). */
    public static ImageIcon carregar(String caminho) {
        return carregar(caminho, TAMANHO_PADRAO, TAMANHO_PADRAO);
    }

    // ---- Ícones específicos da tela ----

    public static ImageIcon anexo()       { return carregar("/icons/attach.png"); }
    public static ImageIcon texto()       { return carregar("/icons/text.png"); }
    public static ImageIcon descricao()   { return carregar("/icons/description.png"); }
    public static ImageIcon risco()       { return carregar("/icons/alert-triangle.png"); }
    public static ImageIcon endereco()       { return carregar("/icons/adress.png", 52, 52); }
    //public static ImageIcon categorias()  { return carregar("/icons/category.png"); }

    //public static ImageIcon infraestrutura() { return carregar("/icons/pothole.png"); }
    //public static ImageIcon acessibilidade() { return carregar("/icons/accessibility.png"); }
    //public static ImageIcon mauUso()         { return carregar("/icons/trash.png"); }
    //public static ImageIcon outros()         { return carregar("/icons/other.png"); }

    //public static ImageIcon endereco()    { return carregar("/icons/map-pin.png"); }
    //public static ImageIcon voltar()      { return carregar("/icons/arrow-left.png"); }
    //public static ImageIcon app()         { return carregar("/icons/app.png"); }
    
    /** Ícone do título — maior que os demais. */
    public static ImageIcon titulo() {
        return carregar("/icons/description.png", 60, 60);
    }
}
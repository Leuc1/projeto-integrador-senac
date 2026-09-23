package PI;

public final class Usuario_conexao {
    private static Integer idUsuario;
    private static String nomeUsuario;
    private static String tipoUsuario;

    private Usuario_conexao() {}

    public static void iniciar(int id, String nome, String tipo) {
        idUsuario = id;
        nomeUsuario = nome;
        tipoUsuario = tipo;
    }

    public static void encerrar() {
        idUsuario = null;
        nomeUsuario = null;
        tipoUsuario = null;
    }

    public static boolean estaLogado() {
        return idUsuario != null;
    }

    public static Integer getIdUsuario() {
        return idUsuario;
    }

    public static String getNomeUsuario() {
        return nomeUsuario;
    }

    public static String getTipoUsuario() {
        return tipoUsuario;
    }

    public static boolean isAdmin() {
        return "admin".equalsIgnoreCase(tipoUsuario);
    }
}

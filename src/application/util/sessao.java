package application.util;

import java.time.LocalDateTime;

public class sessao {
    private static sessao instancia;
    private String usuario;
    private LocalDateTime dataLogin;
    private LocalDateTime expiraEm;

    private sessao(String usuario, int minutosValidade) {
        this.usuario = usuario;
        this.dataLogin = LocalDateTime.now();
        this.expiraEm = dataLogin.plusMinutes(minutosValidade);
    }

    public static void iniciar(String usuario, int minutosValidade) {
        instancia = new sessao(usuario, minutosValidade);
    }

    public static sessao getInstancia() {
        return instancia;
    }

    public boolean isExpirada() {
        return LocalDateTime.now().isAfter(expiraEm);
    }

    public String getUsuario() {
        return usuario;
    }

    public static void encerrar() {
        instancia = null;
    }
}

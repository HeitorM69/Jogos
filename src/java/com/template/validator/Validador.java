package validation;

public interface Validador<T> {
    boolean validar();
    String getMensagemErro();
    T getValor();
}

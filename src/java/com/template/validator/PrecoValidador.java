package validation;

public class PrecoValidador implements Validador<Double> {
    private Double valor;

    public PrecoValidador(Double valor) {
        this.valor = valor;
    }

    @Override
    public boolean validar() {
        return valor != null && valor >= 0;
    }

    @Override
    public String getMensagemErro() {
        return "O preço deve ser um valor numérico válido (maior ou igual a zero).";
    }

    @Override
    public Double getValor() {
        return valor;
    }
}

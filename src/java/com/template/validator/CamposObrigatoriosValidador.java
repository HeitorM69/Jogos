package validation;

public class CamposObrigatoriosValidador implements Validador<String> {
    private String valor;
    private String nomeCampo;

    public CamposObrigatoriosValidador(String valor, String nomeCampo) {
        this.valor = valor;
        this.nomeCampo = nomeCampo;
    }

    @Override
    public boolean validar() {
        return valor != null && !valor.trim().isEmpty();
    }

    @Override
    public String getMensagemErro() {
        return "O campo " + nomeCampo + " é obrigatório.";
    }

    @Override
    public String getValor() {
        return valor;
    }
}

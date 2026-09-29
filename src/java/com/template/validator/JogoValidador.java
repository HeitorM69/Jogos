package validation;

import java.util.ArrayList;
import java.util.List;

public class JogoValidador implements IJogoValidador {
    private List<Validador<?>> validadores = new ArrayList<>();

    @Override
    public void adicionarValidador(Validador<?> validador) {
        validadores.add(validador);
    }

    @Override
    public List<String> validarTodos() {
        List<String> mensagensErro = new ArrayList<>();
        for (Validador<?> validador : validadores) {
            if (!validador.validar()) {
                mensagensErro.add(validador.getMensagemErro());
            }
        }
        return mensagensErro;
    }

    @Override
    public void limparValidadores() {
        validadores.clear();
    }
}

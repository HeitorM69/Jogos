package validation;

import java.util.List;

public interface IJogoValidador {
    void adicionarValidador(Validador<?> validador);
    List<String> validarTodos();
    void limparValidadores();
}

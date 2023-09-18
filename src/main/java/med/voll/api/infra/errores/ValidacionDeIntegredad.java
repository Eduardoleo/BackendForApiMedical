package med.voll.api.infra.errores;

public class ValidacionDeIntegredad extends RuntimeException {
    public ValidacionDeIntegredad(String s) {
        super(s);
    }
}

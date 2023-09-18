package med.voll.api.domain.medico;

public record DatosListadoMedico(

        long id,
        String nombre,
        String especialidad,
        String documento,

        String emial
) {
    public DatosListadoMedico(Medico medico){

        this(medico.getId(), medico.getNombre(),
                medico.getEspecialidad().toString(),
                medico.getDocumento(),medico.getEmail());
    }
}

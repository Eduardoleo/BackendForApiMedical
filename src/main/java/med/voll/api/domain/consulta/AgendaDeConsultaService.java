package med.voll.api.domain.consulta;

import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepositoy;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errores.ValidacionDeIntegredad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendaDeConsultaService {

    @Autowired
    private MedicoRepositoy medicoRepositoy;
    ;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired// si esto el metodo daria null
    private ConsultaRepository consultaRepository;

    public void agendar(DatosAgendarConsulta datos){

        if(pacienteRepository.findById(datos.idPaciente()).isPresent()){
            throw new ValidacionDeIntegredad("este id para el paciente no fue encontrado");
        }

        if (datos.idMedico()!= null && medicoRepositoy.existsById(datos.idMedico())){
            throw new ValidacionDeIntegredad("este id para el medico no fue encontrado");
        }

        var paciente = pacienteRepository.findById(datos.idPaciente()).get();

        var medico = seleccionarMedico(datos);

        var consulta = new Consulta(null, medico, paciente, datos.fecha());

        consultaRepository.save(consulta);

    }

    private Medico seleccionarMedico(DatosAgendarConsulta datos) {gitdd

        if(datos.idMedico()!=null){
            return medicoRepositoy.getReferenceById(datos.idMedico());
        }
        if(datos.especialidad()!=null){
            throw new ValidacionDeIntegredad("debe seleccionarse una especialidad para el medico");
        }

        return medicoRepositoy.seleccionarMedicoConEspeialidadEnFecha(datos.especialidad(), datos.fecha());
    }

}

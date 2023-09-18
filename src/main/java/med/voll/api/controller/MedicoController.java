package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping( "/medicos")
public class MedicoController {

    @Autowired // no se recomienda usar
    private MedicoRepositoy medicoRepositoy;

    @PostMapping//le dice al metodo que agarre el post que fue enviado desde el cliente
    public ResponseEntity regristarMedico(@RequestBody @Valid DatosDeRegristoMedico datosDeRegristoMedico,
                                          UriComponentsBuilder uriComponentsBuilder) {//al poner el@RequestBody spring puede leer el parametro
        Medico medico = medicoRepositoy.save(new Medico(datosDeRegristoMedico));// creamos un constructor qeu acepte de parametros datosDeRegristoMedico
        DatosRespuestaMedico datosRespuestaMedico =
                new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEmail(),
                medico.getTelefono(),medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento(),medico.getDireccion().getProvincia()
                        ,medico.getDireccion().getUrbanizacion()));

        URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaMedico);
        //return 201 Created;
        //URL donde encontrar el medico
        //GET  htpps://localhost:8080/medicos/xs
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoMedico>> listadoMedico(@PageableDefault(size = 2) Pageable paginacion){//cuando se trabaja con page se tiene que tener de parametro a pagable
        //return medicoRepositoy.findAll(paginacion).map(DatosListadoMedico::new);//usa los datos de lstado medico y crea un nuevo medico qeu traiga de la base de datos
        //findAll() es un metodo sobrecargado que  acepta parametros
        return ResponseEntity.ok(medicoRepositoy.findByActivoTrue(paginacion).map(DatosListadoMedico::new));

    }

    @PutMapping
    @Transactional //jpa mappea el metodo caundo termina y la transaccion se termina y agregra un comit a la base de datos
    public ResponseEntity actualizarmedico( @RequestBody @Valid DatosActualizarMedico datosActualizarMedico){
        Medico medico = medicoRepositoy.getReferenceById(datosActualizarMedico.id());
        medico.actualizarDatos(datosActualizarMedico);
        return ResponseEntity.ok(new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEmail(),
                medico.getTelefono(),medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento(),medico.getDireccion().getProvincia()
                        ,medico.getDireccion().getUrbanizacion())));
    }

    //DELETE LOGICO
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarMedico(@PathVariable Long id){
        Medico medico = medicoRepositoy.getReferenceById(id);
        medico.desactivarMedico();
        return ResponseEntity.noContent().build();//metodo dentro spring para retornar un valor cuando se deletea algo
    }

    @GetMapping ("/{id}")
    public ResponseEntity<DatosRespuestaMedico> retornaDatosMedico(@PathVariable Long id){
        Medico medico = medicoRepositoy.getReferenceById(id);
        var datosMedico = new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEmail(),
                medico.getTelefono(),medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento(),medico.getDireccion().getProvincia()
                        ,medico.getDireccion().getUrbanizacion()));

        return ResponseEntity.ok(datosMedico);
    }

    //DELETE EN BASE DE DATOS
    /*public void eliminarMedico(@PathVariable Long id){
        Medico medico = medicoRepositoy.getReferenceById(id);
        medicoRepositoy.delete(medico);
    }*/
}

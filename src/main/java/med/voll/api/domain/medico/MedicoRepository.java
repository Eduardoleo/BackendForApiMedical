package med.voll.api.domain.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/*
 hace todo lo del crud pero automaticamente
 nesecita dos parametros
 el primero es la identidad osea tipo de objecto, que seria la entidad que voy a salvar o a trabajar con ese repositorio
 el segundo seria el tipo de objeto del id. osea si es int, long y etc
 */
@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Page<Medico> findByActivoTrue(Pageable paginacion);


    @Query("""
            select m from Medico m
            where m.activo= true 
            and
            m.especialidad=:especialidad 
            and
            m.id not in(  
                select c.medico.id from Consulta c
                where
                c.fecha=:fecha
            )
            order by rand()
            limit 1
            """)
    Medico seleccionarMedicoConEspecialidadEnFecha(Especialidad especialidad, LocalDateTime fecha);


    @Query("""
            select m.activo 
            from Medico m
            where m.id=:idMedico
            """)
    Boolean findActivoById(Long idMedico);
}
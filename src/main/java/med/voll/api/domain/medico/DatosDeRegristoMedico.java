package med.voll.api.domain.medico;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.direccion.DatosDireccion;

//DTO usado para recibir lo que viene de la api
public record DatosDeRegristoMedico(

       @NotBlank//sirve para dejar algo vacio o en blanco
        String nombre,
       @NotBlank
       @Email//verifica que el email este formato email
        String email,
       @NotBlank
       String telefono,

       @NotBlank
       @Pattern(regexp = "\\d{4,6}")
        String documento,
        @NotNull
        Especialidad especialidad,
        @NotNull
        @Valid
       DatosDireccion direccion
) {}//la clase record son tipos de objetos inmutables que permite crear campos para rellenar


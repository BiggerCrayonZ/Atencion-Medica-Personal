package reza.raul.rm.ActividadDePacientes.citasPacientes;

/**
 * Created by Android on 10/11/2017.
 */

public class citaAtributos {
    private int fotoCita;
    private int idCita;
    private String idMedico;
    private String idPaciente;
    private String fecha;
    private String hora;
    private String direccion;

    //Constructor


    public citaAtributos() {
    }

    //Getter and Setter
    public int getIdCita() {
        return idCita;
    }

    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    public int getFotoCita() {
        return fotoCita;
    }

    public void setFotoCita(int fotoCita) {
        this.fotoCita = fotoCita;
    }

    public String getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(String idMedico) {
        this.idMedico = idMedico;
    }

    public String getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(String idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}

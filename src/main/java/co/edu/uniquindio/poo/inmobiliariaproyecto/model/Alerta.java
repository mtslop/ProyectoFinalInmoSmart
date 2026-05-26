package co.edu.uniquindio.poo.inmobiliariaproyecto.model;

public class Alerta {
        private String mensaje;
        private TipoAlerta tipoAlerta;
        private Usuario destinatario;

        public Alerta(String mensaje, TipoAlerta tipoAlerta, Usuario destinatario) {
            this.mensaje = mensaje;
            this.tipoAlerta = tipoAlerta;
            this.destinatario = destinatario;
        }

        public void enviar() {
            System.out.println(">>> ALERTA [" + tipoAlerta + "] para " + destinatario.getNombre()
                    + ": " + mensaje);
        }

        public String getMensaje() { return mensaje; }
        public void setMensaje(String mensaje) { this.mensaje = mensaje; }

        public TipoAlerta getTipoAlerta() { return tipoAlerta; }
        public void setTipoAlerta(TipoAlerta tipoAlerta) { this.tipoAlerta = tipoAlerta; }

        public Usuario getDestinatario() { return destinatario; }
        public void setDestinatario(Usuario destinatario) { this.destinatario = destinatario; }
    }



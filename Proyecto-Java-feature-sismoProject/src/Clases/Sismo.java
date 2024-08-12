package Clases;

//Abstracción
//encapcsulamiento - privadas
public class Sismo {
        private String fecha;
        private String hora;
        private String codigo;
        private String magnitud;
        private String profundidad;
        private String comuna;



        //Singleton hace una instancia de la clase
        private static Sismo _sismo;


        public Sismo() {

        }
//constructor para acceder a las propiedades
        public Sismo(String fecha, String hora, String codigo, String magnitud, String profundidad, String comuna) {
            this.fecha = fecha;
            this.hora = hora;
            this.codigo = codigo.toUpperCase();
            this.magnitud = magnitud;
            this.profundidad = profundidad;
            this.comuna = comuna;
        }


        //Singleton
        public static Sismo GetInstance(){
            if (_sismo == null)
            {
                _sismo = new Sismo();
            }
            return _sismo;
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


        public String getCodigo() {
            return codigo;
        }

        public void setCodigo(String codigo) {
            this.codigo = codigo;
        }

        public String getMagnitud() {
            return magnitud;
        }

        public void setMagnitud(String magnitud) {
            this.magnitud = magnitud;
        }

        public String getProfundidad() {
            return profundidad;
        }

        public void setProfundidad(String profundidad) {
            this.profundidad = profundidad;
        }

        public String getComuna() {
            return comuna;
        }

        public void setComuna(String comuna) {
            this.comuna = comuna;
        }
    }


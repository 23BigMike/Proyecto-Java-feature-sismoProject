package Clases;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;

public class Menu {


   //patron decorador cuando se hace la instancia
   // de un menu sismo o de ese objeto - le damos un punto de inicio -
   // con el objetivo de usar lo que tiene la clase menu sismo
    private MenuSismo menuSismo = new MenuSismo();



    private boolean isTrue = true;
    private ArrayList<Usuario> usuarios;
    private ArrayList<String> rolesUsuarios;
    private ArrayList<Sismo> sismos; // Lista para almacenar los datos de los sismos
    private HashSet<String> codigo; // Conjunto para almacenar códigos únicos

    public Menu() {
        usuarios = new ArrayList<>();
        rolesUsuarios = new ArrayList<>();
        sismos = new ArrayList<Sismo>(); // Inicializar la lista
        codigo = new HashSet<>(); // Inicializar el conjunto de códigos

        // Usuario predeterminado
        usuarios.add(new Usuario("Miguel", "1234"));
        usuarios.add(new Usuario("Luisa", "1234"));
        rolesUsuarios.add("Administrador");
        rolesUsuarios.add("Investigador");
    }

    public void inicio() {
        do {
            String[] opciones = {"Iniciar sesión", "Agregar usuario", "Salir"};
            String opcionPrincipal = (String) JOptionPane.showInputDialog(null, "Seleccione una opción", "Menu Principal", JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

            switch (opcionPrincipal) {
                case "Iniciar sesión":
                    iniciarSesion();
                    break;
                case "Agregar usuario":
                    agregarUsuario();
                    break;
                case "Salir":
                    isTrue = false;
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida", "Error", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        } while (isTrue);
    }

    private void iniciarSesion() {
        String pUsuario = JOptionPane.showInputDialog(null, "Ingrese su Usuario");
        String pPassword = JOptionPane.showInputDialog(null, "Ingrese su Password");

        Usuario usuarioValido = validarCredenciales(pUsuario, pPassword);
        if (usuarioValido != null) {
            String rolSeleccionado = obtenerRolUsuario(pUsuario);
            mostrarMenu(rolSeleccionado);
        } else {
            JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void agregarUsuario() {
        String nuevoUsuario = JOptionPane.showInputDialog(null, "Ingrese nuevo nombre de usuario");
        String nuevoPassword = JOptionPane.showInputDialog(null, "Ingrese nueva contraseña");

        if (nuevoUsuario != null && nuevoPassword != null && !nuevoUsuario.isEmpty() && !nuevoPassword.isEmpty()) {
            if (usuarioExiste(nuevoUsuario)) {
                JOptionPane.showMessageDialog(null, "El usuario ya existe. Intente con otro nombre.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                String[] roles = {"Administrador", "Investigador"};
                String rolSeleccionado = (String) JOptionPane.showInputDialog(null, "Seleccione el rol para el nuevo usuario", "Rol", JOptionPane.QUESTION_MESSAGE, null, roles, roles[0]);

                usuarios.add(new Usuario(nuevoUsuario, nuevoPassword));
                rolesUsuarios.add(rolSeleccionado);

                JOptionPane.showMessageDialog(null, "Usuario agregado exitosamente con el rol de " + rolSeleccionado);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe ingresar un nombre de usuario y una contraseña válidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean usuarioExiste(String nombreUsuario) {
        for (Usuario u : usuarios) {
            if (u.getUsuario().equals(nombreUsuario)) {
                return true;
            }
        }
        return false;
    }

    private Usuario validarCredenciales(String usuario, String password) {
        for (Usuario u : usuarios) {
            if (u.getUsuario().equals(usuario) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }

    private String obtenerRolUsuario(String usuario) {
        int index = -1;
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getUsuario().equals(usuario)) {
                index = i;
                break;
            }
        }
        return rolesUsuarios.get(index);
    }

    private void mostrarMenu(String rol) {
        String[] menus;

        if ("Administrador".equals(rol)) {
            menus = new String[]{"Menu Sismo", "Salir"};
        } else {
            // Menú para "Investigador" con acceso solo a "Estado Socio"
            menus = new String[]{"Visualizar Registros de los sismos", "Salir"};
        }

        String opcion;

        do {
            opcion = (String) JOptionPane.showInputDialog(null, "Seleccione la opción", "Menu", JOptionPane.QUESTION_MESSAGE, null, menus, menus[0]);

            switch (opcion) {
                case "Menu Sismo":
                    if ("Administrador".equals(rol)) menuSismo();
                    break;
                case "Visualizar Registros de los sismos":
                    if ("Investigador".equals(rol)) menuInvestigador();
                    break;
                case "Salir":
                    iniciarSesion();
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida", "Error", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        } while (!opcion.equals("Salir"));
    }

    public void menuInvestigador() {
        String[] menus = {"Registros de los sismos"};
        try {
            String opcion = (String) JOptionPane.showInputDialog(null, "Seleccionar la opción", "Menu SGC", JOptionPane.QUESTION_MESSAGE, null, menus, menus[0]);
            switch (opcion) {
                case "Registros de los sismos":
                    estadoDelSismo();
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Valor incorrecto", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void menuSismo() {
        String[] menus = {"Ingresar datos del sismo", "Registros de los sismos", "Editar Sismo", "Eliminar registro"};
        try {
            String opcion = (String) JOptionPane.showInputDialog(null, "Seleccionar la opción", "Menu SGC", JOptionPane.QUESTION_MESSAGE, null, menus, menus[0]);
            switch (opcion) {
                case "Ingresar datos del sismo":
                    registrarSismo();
                    break;
                case "Registros de los sismos":
                    estadoDelSismo();
                    break;
                case "Editar Sismo":
                    editarSismo();
                    break;
                case "Eliminar registro":
                    eliminarSismo();
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Valor incorrecto", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void registrarSismo() {
        JPanel panel = new JPanel(new GridLayout(6, 2));
        panel.add(new JLabel("Fecha:"));
        JTextField fechaField = new JTextField();
        panel.add(fechaField);

        panel.add(new JLabel("Hora:"));
        JTextField horaField = new JTextField();
        panel.add(horaField);

        panel.add(new JLabel("Código:"));
        JTextField codigoField = new JTextField();
        panel.add(codigoField);

        panel.add(new JLabel("Magnitud:"));
        JTextField magnitudField = new JTextField();
        panel.add(magnitudField);

        panel.add(new JLabel("Profundidad:"));
        JTextField profundidadField = new JTextField();
        panel.add(profundidadField);

        panel.add(new JLabel("Comuna:"));
        JTextField comunaField = new JTextField();
        panel.add(comunaField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Ingrese los datos", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String pFecha = fechaField.getText();
            String pHora = horaField.getText();
            String pCodigo = codigoField.getText();
            String pMagnitud = magnitudField.getText();
            String pProfundidad = profundidadField.getText();
            String pComuna = comunaField.getText();

            // Guardar los datos en la lista
            codigo.add(pCodigo.toUpperCase()); // Agregar el codigo a la lista de codigos

            // Llamamos el método para registrar el sismo - el patrondecorador se ve evidenciado ya que utilizamos lo que tiene la clase menu sismo
            menuSismo.registrarSismo(pFecha, pHora, pCodigo, pMagnitud, pProfundidad, pComuna);
        }
    }

    public void estadoDelSismo() {
        // Mostrar un desplegable con los codigos registrados
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay codigos registradas.", "Estado del Sismo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            String CodigoSeleccionada = (String) JOptionPane.showInputDialog(null, "Seleccione el Código", "Selección el Código", JOptionPane.QUESTION_MESSAGE, null, codigo.toArray(), codigo.iterator().next());

            if (CodigoSeleccionada != null) {
                // Mostrar los datos del sismo para el código seleccionado
                StringBuilder sb = new StringBuilder("Datos de sismos en el Código seleccionado:\n");
                ArrayList<Sismo> sismos = menuSismo.todosLosSismos(); // Asegúrate de obtener la lista de sismos correctamente
                boolean haySismos = false;

                for (Sismo sismo : sismos) {
                    if (sismo.getCodigo().equals(CodigoSeleccionada.toUpperCase())) {
                        sb.append("Fecha: ").append(sismo.getFecha()).append("\n")
                                .append("Hora: ").append(sismo.getHora()).append("\n")
                                .append("Código: ").append(sismo.getCodigo()).append("\n")
                                .append("Magnitud: ").append(sismo.getMagnitud()).append("\n")
                                .append("Profundidad: ").append(sismo.getProfundidad()).append("\n")
                                .append("Comuna: ").append(sismo.getComuna()).append("\n\n");
                        haySismos = true;
                    }
                }

                if (!haySismos) {
                    JOptionPane.showMessageDialog(null, "No hay datos de sismos para el Código seleccionado.", "Estado del Sismo", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, sb.toString(), "Estado del Sismo", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }


    public void editarSismo() {
        Sismo sismo = Sismo.GetInstance();
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay codigos registradas.", "Estado Socio", JOptionPane.INFORMATION_MESSAGE);
        } else {
            String codigo = JOptionPane.showInputDialog(null, "Ingrese la codigo");
            String fecha = JOptionPane.showInputDialog(null, "Ingrese la fecha");
            String magnitud = JOptionPane.showInputDialog(null, "Ingrese la magnitud");

            sismo = menuSismo.buscarSismo(codigo.toUpperCase(), fecha, magnitud);

            if (sismo != null) {
                menuEditar(sismo);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró un sismo con los datos proporcionados.", "Error", JOptionPane.ERROR_MESSAGE);
            }


        }
    }

    public void menuEditar(Sismo sismo) {
        //Uso del singleton
        Sismo anterior = Sismo.GetInstance();
        Sismo nuevo = Sismo.GetInstance();

        anterior = sismo;
        // Crear el panel con un GridLayout de 6 filas y 2 columnas
        JPanel panel = new JPanel(new GridLayout(6, 2));

        // Añadir cada campo de información al panel, con los valores actuales del objeto Sismo
        panel.add(new JLabel("Fecha:"));
        JTextField fechaField = new JTextField(sismo.getFecha());
        panel.add(fechaField);

        panel.add(new JLabel("Hora:"));
        JTextField horaField = new JTextField(sismo.getHora());
        panel.add(horaField);

        panel.add(new JLabel("Codigo:"));
        JTextField codigoField = new JTextField(sismo.getCodigo());
        panel.add(codigoField);

        panel.add(new JLabel("Magnitud:"));
        JTextField magnitudField = new JTextField(sismo.getMagnitud());
        panel.add(magnitudField);

        panel.add(new JLabel("Profundidad:"));
        JTextField profundidadField = new JTextField(sismo.getProfundidad());
        panel.add(profundidadField);

        panel.add(new JLabel("Comuna:"));
        JTextField comunaField = new JTextField(sismo.getComuna());
        panel.add(comunaField);

        // Mostrar el panel en un JOptionPane
        int result = JOptionPane.showConfirmDialog(null, panel, "Detalles del Sismo", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

        // Si el usuario presiona OK, actualiza los valores del objeto Sismo
        if (result == JOptionPane.OK_OPTION) {
            if(!(codigo.contains(codigoField.getText()))) {
                codigo.add(codigoField.getText().toUpperCase());
            }


            nuevo = new Sismo(fechaField.getText(),horaField.getText(), codigoField.getText(),magnitudField.getText(),profundidadField.getText(), comunaField.getText());

            menuSismo.editarSismo(nuevo,sismo);
        }
        else
        {
            JOptionPane.showMessageDialog(null, "No se encontró un sismo con los datos proporcionados.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void eliminarSismo(){
        Sismo sismo = Sismo.GetInstance();
        String codigo = JOptionPane.showInputDialog(null, "Ingrese la codigo");
        String fecha = JOptionPane.showInputDialog(null, "Ingrese la fecha");
        String magnitud = JOptionPane.showInputDialog(null, "Ingrese la magnitud");

       sismo = menuSismo.buscarSismo(codigo.toUpperCase(), fecha, magnitud);

       menuSismo.removerSismo(sismo);

        JOptionPane.showMessageDialog(null, "Registro eliminado exitosamente", "Precausion", JOptionPane.WARNING_MESSAGE);
    }
}
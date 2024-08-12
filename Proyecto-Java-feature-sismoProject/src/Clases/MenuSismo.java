package Clases;

import Interfaz.IMenuSismo;

import javax.swing.*;
import java.util.ArrayList;


//Polimorfismo
public class MenuSismo implements IMenuSismo {
    public Sismo nuevoSismo = Sismo.GetInstance();
    private ArrayList<Sismo> sismos;


    public MenuSismo() {
        sismos = new ArrayList<Sismo>();
    }

    @Override
    public void registrarSismo(String pFecha, String pHora, String pCodigo, String pMagnitud, String pProfundidad, String pComuna) {
        // Revisar que no haya ya un socio con la misma cédula
        pCodigo = pCodigo.toUpperCase();

        // Se crea el objeto del nuevo socio (todavía no se ha agregado al SGC)
        nuevoSismo = new Sismo(pHora, pFecha, pCodigo, pMagnitud, pProfundidad, pComuna);

        // Se agrega el nuevo sismo
        sismos.add(nuevoSismo);
        JOptionPane.showMessageDialog(null, "Sismo registrado con exito");
    }

    public  ArrayList<Sismo> todosLosSismos(){
        return sismos;
    }

    @Override
    public Sismo buscarSismo(String codigo, String fecha, String magnitud){
        Sismo sismo = Sismo.GetInstance();

        for(Sismo s : sismos){
            if(s.getCodigo().equals(codigo)
            && s.getFecha().equals(fecha)
            && s.getMagnitud().equals(magnitud)){
                sismo = s;
                return sismo;
            }
        }

        return null;
    }

    public void editarSismo(Sismo nuevoSismo, Sismo anterior){

        //Todo buscar sismo anterior
        removerSismo(anterior);
        //Todo crear nuevo sismo
        sismos.add(nuevoSismo);
    }

    public void removerSismo(Sismo sismoBuscado) {
        System.out.println(sismos);
        for (int i = 0; i < sismos.size(); i++) {
            Sismo s = sismos.get(i);

            // Compara los atributos relevantes para determinar si es el mismo sismo
            if (s.getCodigo().equals(sismoBuscado.getCodigo()) &&
                    s.getFecha().equals(sismoBuscado.getFecha()) &&
                    s.getMagnitud().equals(sismoBuscado.getMagnitud())) {
                // Remueve el objeto de la lista utilizando el índice
                sismos.remove(i);
                System.out.println("Sismo removido de la lista.");
                return;  // Sal del método después de remover
            }
        }

        System.out.println("No se encontró el sismo en la lista.");
    }

}

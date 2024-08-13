package Interfaz;

import Clases.Sismo;

public interface IMenuSismo {
    public void registrarSismo(String pCedula, String pNombre, String pTipo, String pMagnitud, String pProfundidad, String pComuna);
    public Sismo buscarSismo(String codigo, String fecha, String magnitud );

}
//Patron Factory - son los implements ya no debemos usar una cosa por que usamos la interfaz
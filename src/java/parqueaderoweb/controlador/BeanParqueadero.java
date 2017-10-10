package parqueaderoweb.controlador;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import parqueadero.Controlador.ListaSE;
import parqueadero.Modelo.Nodo;
import parqueadero.Modelo.Vehiculo;

/**
 *
 * @author Santiago Betancur Villegas <santiago-betancur at hotmail.com>
 */
@Named(value = "beanParqueadero")
@SessionScoped
public class BeanParqueadero implements Serializable {

    private boolean deshabilitarNuevo = true; //Este atributo es para que se deshabilite los ImputText
    private int tipoVehiculoSeleccionado; //Indica el numero que selecciona en el selectOneMenu, tipo de vehiculo
    private Nodo nodoMostrar = new Nodo(new Vehiculo()); //ayudante que toma los datos de la lista y me los va a mostrar en la paguina
    private ListaSE listaVehiculos = new ListaSE(); // Voy a tener acceso a los metodos de listaSE
    private int posicionQueSeraEliminada;

    private boolean deshabilitarBtnIrAlPrimero = false;///---
    private boolean deshabilitarBtnIrAlSiguiente = false;///---
    private boolean deshabilitarBtnIrAlUltimo = false;///---
    private boolean deshabilitarInvertirLista = false;///---

    public BeanParqueadero() {
    }

    public boolean isDeshabilitarInvertirLista() {
        return deshabilitarInvertirLista;
    }

    public void setDeshabilitarInvertirLista(boolean deshabilitarInvertirLista) {
        this.deshabilitarInvertirLista = deshabilitarInvertirLista;
    }

    public boolean isDeshabilitarBtnIrAlUltimo() {
        return deshabilitarBtnIrAlUltimo;
    }

    public void setDeshabilitarBtnIrAlUltimo(boolean deshabilitarBtnIrAlUltimo) {
        this.deshabilitarBtnIrAlUltimo = deshabilitarBtnIrAlUltimo;
    }

    public boolean isDeshabilitarBtnIrAlPrimero() {///---
        return deshabilitarBtnIrAlPrimero;
    }

    public void setDeshabilitarBtnIrAlPrimero(boolean deshabilitarBtnIrAlPrimero) {///---
        this.deshabilitarBtnIrAlPrimero = deshabilitarBtnIrAlPrimero;
    }

    public boolean isDeshabilitarBtnIrAlSiguiente() {///---
        return deshabilitarBtnIrAlSiguiente;
    }

    public void setDeshabilitarBtnIrAlSiguiente(boolean deshabilitarBtnIrAlSiguiente) {///---
        this.deshabilitarBtnIrAlSiguiente = deshabilitarBtnIrAlSiguiente;
    }

    public int getPosicionQueSeraEliminada() {
        return posicionQueSeraEliminada;
    }

    public void setPosicionQueSeraEliminada(int posicionQueSeraEliminada) {
        this.posicionQueSeraEliminada = posicionQueSeraEliminada;
    }

    public ListaSE getListaVehiculos() {
        return listaVehiculos;
    }

    public void setListaVehiculos(ListaSE listaVehiculos) {
        this.listaVehiculos = listaVehiculos;
    }

    public Nodo getNodoMostrar() {
        return nodoMostrar;
    }

    public void setNodoMostrar(Nodo nodoMostrar) {
        this.nodoMostrar = nodoMostrar;
    }

    public int getTipoVehiculoSeleccionado() {
        return tipoVehiculoSeleccionado;
    }

    public void setTipoVehiculoSeleccionado(int tipoVehiculoSeleccionado) {
        this.tipoVehiculoSeleccionado = tipoVehiculoSeleccionado;
    }

    public boolean isDeshabilitarNuevo() {
        return deshabilitarNuevo;
    }

    public void setDeshabilistarNuevo(boolean deshabilitarNuevo) {
        this.deshabilitarNuevo = deshabilitarNuevo;
    }

    public void habilitarGuardado() {
        deshabilitarNuevo = false;
        nodoMostrar = new Nodo(new Vehiculo());
    }
    //true es: SI false es: NO                                  

    public void verificarDatosVacios() { // Creo metodo que valida si es 0, deshabilite la informacion que requiero
        if (listaVehiculos.contarNodos() == 0) {
            deshabilitarBtnIrAlPrimero = true;
            deshabilitarBtnIrAlSiguiente = true;
            deshabilitarBtnIrAlUltimo = true;
            deshabilitarInvertirLista = true;
        }
    }

    public void verificarDatosConUnDato() {// Creo metodo que valida si es 1, deshabilite la informacion que requiero
        if (listaVehiculos.contarNodos() == 1) {
            deshabilitarBtnIrAlPrimero = true;
            deshabilitarBtnIrAlSiguiente = true;
            deshabilitarBtnIrAlUltimo = true;
            deshabilitarInvertirLista = true;
        }
    }

    public void deshabilitarDatosConElUltimo() {// Creo metodo que valida si es 1, deshabilite la informacion que requiero
        if (nodoMostrar.getSiguiente() == null) {
            deshabilitarBtnIrAlSiguiente = true;
            deshabilitarBtnIrAlUltimo = true;
        }
    }

    public void irAlPrimero() { // Sobre estos metodos coloco un condicional para determinar si es lo que requiero se ejecute
        nodoMostrar = listaVehiculos.getCabeza();
        if (listaVehiculos.getCabeza() != null) {
            deshabilitarBtnIrAlPrimero = true;
            deshabilitarBtnIrAlSiguiente = false;
            deshabilitarBtnIrAlUltimo = false;
            deshabilitarInvertirLista = false;
        } else {
            deshabilitarBtnIrAlPrimero = true;
        }
    }

    public void irAlSguiente() { // Sobre estos metodos coloco un condicional para determinar si es lo que requiero se ejecute
        if (listaVehiculos.getCabeza() != null && nodoMostrar.getSiguiente() != null) {
            nodoMostrar = nodoMostrar.getSiguiente();
            deshabilitarBtnIrAlSiguiente = false;
            deshabilitarBtnIrAlPrimero = false;
        } else {
            deshabilitarBtnIrAlSiguiente = true;
            deshabilitarBtnIrAlUltimo = true;
        }
    }

    public void irAlUltimo() { // Sobre estos metodos coloco un condicional para determinar si es lo que requiero se ejecute
        if (listaVehiculos.getCabeza() != null && nodoMostrar.getSiguiente() != null) {
            nodoMostrar = listaVehiculos.irAlUltimo();
            deshabilitarBtnIrAlUltimo = true;
            deshabilitarBtnIrAlSiguiente = true;
            deshabilitarBtnIrAlPrimero = false;
        } else {
            deshabilitarBtnIrAlSiguiente = true;
            deshabilitarBtnIrAlUltimo = true;
        }
    }

    public void guardarVehiculo() {
        listaVehiculos.adicionarNodoAlFinal(nodoMostrar.getDato());//Adiciono en lista
        deshabilitarNuevo = true;
        irAlPrimero();
    }

    public void guarfarVehiculoAlInicio() {
        listaVehiculos.adicionarNodoAlInicio(nodoMostrar.getDato());
        deshabilitarNuevo = true;
        irAlPrimero();
    }

    public void invertir() {
        listaVehiculos.invertirLista();
        deshabilitarNuevo = true;
        irAlPrimero();
    }

    public void eliminarVehiculo() {
        listaVehiculos.eliminarNodo(nodoMostrar.getDato());
        deshabilitarNuevo = true;
        irAlPrimero();
    }

    public void eliminiarXPosicion() {
        listaVehiculos.eliminarNodoxPosicion(getPosicionQueSeraEliminada());
        deshabilitarNuevo = true;
        irAlPrimero();
    }

    public void metodoMostrarDatosPares() {
        listaVehiculos.verListaConNumeroImpares();
        deshabilitarNuevo = true;
        irAlPrimero();
    }

    public void mostrarUltimoEnELPrimero() {
        listaVehiculos.invertirSoloExtremos();
        deshabilitarNuevo = true;
        irAlPrimero();
    }
    
    public void mostrarNodosInternos() {
        listaVehiculos.invertirSoloNodosInternos();
        deshabilitarNuevo = true;
        irAlPrimero();
    }

    @PostConstruct//para despues que se ins se llame este objeto
    public void llenarVehiculos() {
        listaVehiculos.adicionarNodoAlFinal(new Vehiculo("1NAE033", new Date()));
        listaVehiculos.adicionarNodoAlFinal(new Vehiculo("2TVL03D", new Date()));
        listaVehiculos.adicionarNodoAlFinal(new Vehiculo("3NAC995", new Date()));
        listaVehiculos.adicionarNodoAlFinal(new Vehiculo("4SAN201", new Date()));
//        listaVehiculos.adicionarNodoAlFinal(new Vehiculo("5MAT201", new Date()));
        irAlPrimero();
    }
}

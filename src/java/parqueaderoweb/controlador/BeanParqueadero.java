package parqueaderoweb.controlador;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import org.primefaces.model.diagram.Connection;
import org.primefaces.model.diagram.DefaultDiagramModel;
import org.primefaces.model.diagram.DiagramModel;
import org.primefaces.model.diagram.Element;
import org.primefaces.model.diagram.endpoint.DotEndPoint;
import org.primefaces.model.diagram.endpoint.EndPointAnchor;
import parqueadero.Controlador.ListaSE;
import parqueadero.Modelo.Automovil;
import parqueadero.Modelo.Buseta;
import parqueadero.Modelo.Moto;
import parqueadero.Modelo.Nodo;
import parqueadero.Modelo.Vehiculo;
import parqueadero.Modelo.Volqueta;

/**
 *
 * @author Santiago Betancur Villegas <santiago-betancur at hotmail.com>
 */
@Named(value = "beanParqueadero")
@SessionScoped
public class BeanParqueadero implements Serializable {

    private boolean deshabilitarNuevo = true; //Este atributo es para que se deshabilite los ImputText
    private int tipoVehiculoSeleccionado; //Indica el numero que selecciona en el selectOneMenu, tipo de vehiculo
    private int tipoVehiculoBuscado; //Indica el numero que selecciona en el selectOneMenu, tipo de vehiculo
    private Nodo nodoMostrar = new Nodo(new Vehiculo()); //ayudante que toma los datos de la lista y me los va a mostrar en la paguina
    private ListaSE listaVehiculos = new ListaSE(); // Voy a tener acceso a los metodos de listaSE
    private int posicionQueSeraEliminada; //Variable que toma la posicion que requiero que se elimine
    private DefaultDiagramModel model;//Este es el diagrama que me permite ver la opciones en la web
    private boolean opcionSeleccionado;//Capturo el tipo de opcion seleccionada caso o pasaCintas
    private byte numeroDeAsientos = 5;//Capturo el numero de nuemroAsientos buseta
    private byte numeroDeToneladas = 6;//Capturo el numero de nuemroAsientos buseta
    private int numeroDeMotos;  //Variable que captura el numero de motos totales de LIstaSE
    private int numeroDeAuto; //Variable que captura el numero de autos totales de LIstaSE
    private int numeroDeBusetas; //Variable que captura el numero de busetas totales de LIstaSE
    private int numeroDeVolquetas; //Variable que captura el numero de volquetas totales de LIstaSE
    private int numeroDeNodos; //Variable que captura el numero de Nodos totales de LIstaSE
    //============================================================================
    int numeroDeCiudadesContadas = 0; //Variable que toma el numero total de ciudades contadas
    private String ciudadSeleccionada = ""; //Variable que toma la ciudad digitada por pantalla web
    //----------------------------------------------------------------------------
    private boolean deshabilitarBtnIrAlPrimero = false;//Indica en primera instancia que esta deshabilitado el boton 
    private boolean deshabilitarBtnIrAlSiguiente = false;//Indica en primera instancia que esta deshabilitado el boton 
    private boolean deshabilitarBtnIrAlUltimo = false;//Indica en primera instancia que esta deshabilitado el boton 
    private boolean deshabilitarInvertirLista = false;//Indica en primera instancia que esta deshabilitado el boton
    //===============================================================================

    public BeanParqueadero() {
    }

    public int getNumeroDeVolquetas() {
        return numeroDeVolquetas;
    }

    public void setNumeroDeVolquetas(int numeroDeVolquetas) {
        this.numeroDeVolquetas = numeroDeVolquetas;
    }

    public byte getNumeroDeToneladas() {
        return numeroDeToneladas;
    }

    public void setNumeroDeToneladas(byte numeroDeToneladas) {
        this.numeroDeToneladas = numeroDeToneladas;
    }

    public int getNumeroDeCiudadesContadas() {
        return numeroDeCiudadesContadas;
    }

    public void setNumeroDeCiudadesContadas(int numeroDeCiudadesContadas) {
        this.numeroDeCiudadesContadas = numeroDeCiudadesContadas;
    }

    public String getCiudadSeleccionada() {
        return ciudadSeleccionada;
    }

    public void setCiudadSeleccionada(String ciudadSeleccionada) {
        this.ciudadSeleccionada = ciudadSeleccionada;
    }

    public int getTipoVehiculoBuscado() {
        return tipoVehiculoBuscado;
    }

    public void setTipoVehiculoBuscado(int tipoVehiculoBuscado) {
        this.tipoVehiculoBuscado = tipoVehiculoBuscado;
    }

    public int getNumeroDeNodos() {
        return numeroDeNodos;
    }

    public void setNumeroDeNodos(int numeroDeNodos) {
        this.numeroDeNodos = numeroDeNodos;
    }

    public int getNumeroDeMotos() {
        return numeroDeMotos;
    }

    public void setNumeroDeMotos(int numeroDeMotos) {
        this.numeroDeMotos = numeroDeMotos;
    }

    public int getNumeroDeAuto() {
        return numeroDeAuto;
    }

    public void setNumeroDeAuto(int numeroDeAuto) {
        this.numeroDeAuto = numeroDeAuto;
    }

    public int getNumeroDeBusetas() {
        return numeroDeBusetas;
    }

    public void setNumeroDeBusetas(int numeroDeBusetas) {
        this.numeroDeBusetas = numeroDeBusetas;
    }

    public boolean isOpcionSeleccionado() {
        return opcionSeleccionado;
    }

    public void setOpcionSeleccionado(boolean opcionSeleccionado) {
        this.opcionSeleccionado = opcionSeleccionado;
    }

    public byte getNumeroDeAsientos() {
        return numeroDeAsientos;
    }

    public void setNumeroDeAsientos(byte numeroDeAsientos) {
        this.numeroDeAsientos = numeroDeAsientos;
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

    public void habilitarGuardado() { //Llama al boton nuevo 
        deshabilitarNuevo = false;
        nodoMostrar = new Nodo(new Vehiculo());
        tipoVehiculoSeleccionado = 0;
        opcionSeleccionado = true;
        numeroDeAsientos = 5;
        numeroDeToneladas = 6;
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

    private void seleccionarTipoVehiculo() {
        if (nodoMostrar.getDato() instanceof Moto) {
            tipoVehiculoSeleccionado = 1;
            Moto moto = (Moto) nodoMostrar.getDato();
            opcionSeleccionado = moto.isCasco();
        } else if (nodoMostrar.getDato() instanceof Automovil) {
            tipoVehiculoSeleccionado = 2;
            Automovil auto = (Automovil) nodoMostrar.getDato();
            opcionSeleccionado = auto.isPasaCintas();
        } else if (nodoMostrar.getDato() instanceof Buseta) {
            tipoVehiculoSeleccionado = 3;
            Buseta buseta = (Buseta) nodoMostrar.getDato();
            numeroDeAsientos = buseta.getNuemroAsientos();
        } else if (nodoMostrar.getDato() instanceof Volqueta) {
            tipoVehiculoSeleccionado = 4;
            Volqueta volqueta = (Volqueta) nodoMostrar.getDato();
            numeroDeToneladas = volqueta.getCantidadToneladas();
        }
    }

    public void irAlPrimero() { // Sobre estos metodos coloco un condicional para determinar si es lo que requiero se ejecute
        nodoMostrar = listaVehiculos.getCabeza();
        seleccionarTipoVehiculo();
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
            seleccionarTipoVehiculo();
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
            seleccionarTipoVehiculo();
        } else {
            deshabilitarBtnIrAlSiguiente = true;
            deshabilitarBtnIrAlUltimo = true;
        }
    }

    public void guardarVehiculo() {
        Vehiculo vehiculo = null;
        switch (tipoVehiculoSeleccionado) {
            case 1:
                vehiculo = new Moto(opcionSeleccionado, nodoMostrar.getDato().getPlaca(), nodoMostrar.getDato().getFechaHoraEntrada(), nodoMostrar.getDato().getCiudad());
                break;
            case 2:
                vehiculo = new Automovil(opcionSeleccionado, nodoMostrar.getDato().getPlaca(), nodoMostrar.getDato().getFechaHoraEntrada(), nodoMostrar.getDato().getCiudad());
                break;
            case 3:
                vehiculo = new Buseta(numeroDeAsientos, nodoMostrar.getDato().getPlaca(), nodoMostrar.getDato().getFechaHoraEntrada(), nodoMostrar.getDato().getCiudad());
                break;
            case 4:
                vehiculo = new Volqueta(numeroDeToneladas, nodoMostrar.getDato().getPlaca(), nodoMostrar.getDato().getFechaHoraEntrada(), nodoMostrar.getDato().getCiudad());
                break;
        }
        listaVehiculos.adicionarNodoAlFinal(vehiculo);//Adiciono en lista
        deshabilitarNuevo = true;
        tipoVehiculoSeleccionado = 0;
        irAlPrimero();
    }

    public void guarfarVehiculoAlInicio() {
        listaVehiculos.adicionarNodoAlInicio(nodoMostrar.getDato());
        deshabilitarNuevo = true;
        tipoVehiculoSeleccionado = 0;
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

    public void metodoQueEliminaPosicionesImpares() {
        listaVehiculos.metodoQueEliminaPosicionesImpares();
        deshabilitarNuevo = true;
        irAlPrimero();
    }

    public void metodoQueInvierteUltimoEnELPrimero() {
        listaVehiculos.invertirExtremos();
        deshabilitarNuevo = true;
        irAlPrimero();
    }

    public void mostrarNodosInternos() {
        listaVehiculos.invertirNodosInternos();
        deshabilitarNuevo = true;
        irAlPrimero();
    }

    public void vehiculosEnMemoria() {
        numeroDeNodos = listaVehiculos.contarNodos();
        numeroDeMotos = listaVehiculos.numerosDeMoto();
        numeroDeAuto = listaVehiculos.numerosDeAutomovil();
        numeroDeBusetas = listaVehiculos.numerosDeBuses();
        numeroDeVolquetas = listaVehiculos.numerosDeVolquetas();
    }

    public int busquedaDeAutos() {
        int valorQueIndicaLaSElleccion = 0; //Variable que captura el numero seleccionado en selectOneMenu, buscar vehiculo
        switch (tipoVehiculoBuscado) {
            case 1:
                numeroDeMotos = listaVehiculos.numerosDeMoto();
                valorQueIndicaLaSElleccion = numeroDeMotos;
                break;
            case 2:
                numeroDeAuto = listaVehiculos.numerosDeAutomovil();
                valorQueIndicaLaSElleccion = numeroDeAuto;
                break;
            case 3:
                numeroDeBusetas = listaVehiculos.numerosDeBuses();
                valorQueIndicaLaSElleccion = numeroDeBusetas;
                break;
            case 4:
                numeroDeVolquetas = listaVehiculos.numerosDeVolquetas();
                valorQueIndicaLaSElleccion = numeroDeVolquetas;
                break;
            default:
                tipoVehiculoBuscado = 0;
                valorQueIndicaLaSElleccion = 0;
                break;
        }
        return valorQueIndicaLaSElleccion;
    }

    public void buscarPorCiudad() {
        numeroDeCiudadesContadas = listaVehiculos.buscarCiudadPorCiudad(getCiudadSeleccionada());
    }

    private boolean deshabilitarEliminarPorCabeza = false;//Indica en primera instancia que esta deshabilitado el boton 
    private boolean deshabilitarEliminarPorUltimo = false;//Indica en primera instancia que esta deshabilitado el boton 

    public boolean isDeshabilitarEliminarPorUltimo() {
        return deshabilitarEliminarPorUltimo;
    }

    public void setDeshabilitarEliminarPorUltimo(boolean deshabilitarEliminarPorUltimo) {
        this.deshabilitarEliminarPorUltimo = deshabilitarEliminarPorUltimo;
    }

    public boolean isDeshabilitarEliminarPorCabeza() {
        return deshabilitarEliminarPorCabeza;
    }

    public void setDeshabilitarEliminarPorCabeza(boolean deshabilitarEliminarPorCabeza) {
        this.deshabilitarEliminarPorCabeza = deshabilitarEliminarPorCabeza;
    }

    public void eliminarPorCabe() {
        deshabilitarEliminarPorCabeza = true;
    }

    public void eliminarPorUlti() {
        deshabilitarEliminarPorUltimo = true;
    }

    public void eliminarCabeza() {
        listaVehiculos.eliminarCabeza();
        irAlSguiente();
    }

    public void eliminarUltimoDeLaLista() {
        nodoMostrar = listaVehiculos.eliminarUltimoDelaLista();
        listaVehiculos.eliminarNodo(nodoMostrar.getDato());
    }

    @PostConstruct//para despues que se ins se llame este objeto
    public void llenarVehiculos() {
        listaVehiculos.adicionarNodoAlFinal(new Buseta((byte) 30, "1SAN123", new Date(), "manizales"));
        listaVehiculos.adicionarNodoAlFinal(new Moto(true, "2TVL03D", new Date(), "manizales"));
        listaVehiculos.adicionarNodoAlFinal(new Moto(false, "3NAC995", new Date(), "manizales"));
        listaVehiculos.adicionarNodoAlFinal(new Automovil(true, "4NAE033", new Date(), "manizales"));
        listaVehiculos.adicionarNodoAlFinal(new Volqueta((byte) 9, "5DFG033", new Date(), "manizales"));
        listaVehiculos.adicionarNodoAlFinal(new Volqueta((byte) 18, "6GHJ033", new Date(), "manizales"));
////        listaVehiculos.adicionarNodoAlFinal(new Automovil(true, "5NAE033", new Date()));
        irAlPrimero();
    }
//===================================================Diagrama PrimeFaces=======================================

    public void init() {
        model = new DefaultDiagramModel();
        model.setMaxConnections(-1);

//        Element elementA = new Element("", "5em", "5em");
//        elementA.addEndPoint(new DotEndPoint(EndPointAnchor.BOTTOM));
//        elementA.addEndPoint(new DotEndPoint(EndPointAnchor.BOTTOM));
//
//        model.addElement(elementA);
        int cont = 1;
        Nodo temp = listaVehiculos.getCabeza();
        while (cont <= listaVehiculos.contarNodos()) {
            if (cont == 1) {
                crearPrimerNodoDiagrama(cont, temp);
                temp = temp.getSiguiente();
                cont++;
            } else {
                crearNodoDiagrama(cont, temp);
                temp = temp.getSiguiente();
                cont++;
            }
        }
    }

    public DiagramModel getModel() {
        return model;
    }

    private void crearNodoDiagrama(int pos, Nodo nodo) {
        int cont = pos * 5;
        String horizontal = Integer.toString(cont) + "em";
        String verticar = Integer.toString(cont) + "em";
        Element nuevoVehiculo = new Element(nodo.getDato().getPlaca(), horizontal, verticar);
        nuevoVehiculo.addEndPoint(new DotEndPoint(EndPointAnchor.TOP));
        nuevoVehiculo.addEndPoint(new DotEndPoint(EndPointAnchor.BOTTOM));

        model.addElement(nuevoVehiculo);

        model.connect(new Connection(model.getElements().get(pos - 2).getEndPoints().get(1), nuevoVehiculo.getEndPoints().get(0)));
    }

    private void crearPrimerNodoDiagrama(int pos, Nodo nodo) {
        int cont = 4;
        String horizontal = Integer.toString(cont) + "em";
        String verticar = Integer.toString(cont) + "em";
        Element nuevoVehiculo = new Element(nodo.getDato().getPlaca(), horizontal, verticar);
        nuevoVehiculo.addEndPoint(new DotEndPoint(EndPointAnchor.BOTTOM));
        nuevoVehiculo.addEndPoint(new DotEndPoint(EndPointAnchor.BOTTOM));
        model.addElement(nuevoVehiculo);
    }
}

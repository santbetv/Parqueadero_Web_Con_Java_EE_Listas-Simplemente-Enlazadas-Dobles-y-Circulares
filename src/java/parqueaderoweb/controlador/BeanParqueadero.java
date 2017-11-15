package parqueaderoweb.controlador;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.ChartSeries;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import static org.primefaces.component.organigram.Organigram.PropertyKeys.autoScrollToSelection;
import static org.primefaces.component.organigram.Organigram.PropertyKeys.leafNodeConnectorHeight;
import static org.primefaces.component.organigram.Organigram.PropertyKeys.zoom;
import org.primefaces.component.organigram.OrganigramHelper;
import org.primefaces.event.organigram.OrganigramNodeCollapseEvent;
import org.primefaces.event.organigram.OrganigramNodeDragDropEvent;
import org.primefaces.event.organigram.OrganigramNodeExpandEvent;
import org.primefaces.event.organigram.OrganigramNodeSelectEvent;
import org.primefaces.model.DefaultOrganigramNode;
import org.primefaces.model.OrganigramNode;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.PieChartModel;
import org.primefaces.model.diagram.Connection;
import org.primefaces.model.diagram.DefaultDiagramModel;
import org.primefaces.model.diagram.DiagramModel;
import org.primefaces.model.diagram.Element;
import org.primefaces.model.diagram.endpoint.DotEndPoint;
import org.primefaces.model.diagram.endpoint.EndPointAnchor;
import parqueadero.Controlador.ListaSE;
import parqueadero.Controlador.exepciones.ParqueaderoExepcion;
import parqueadero.Modelo.Automovil;
import parqueadero.Modelo.Buseta;
import parqueadero.Modelo.Moto;
import parqueadero.Modelo.Nodo;
import parqueadero.Modelo.Vehiculo;
import parqueadero.Modelo.Volqueta;
import parqueaderoweb.utilidades.JsfUtil;
import org.primefaces.model.diagram.connector.FlowChartConnector;
import org.primefaces.model.diagram.endpoint.BlankEndPoint;
import org.primefaces.model.diagram.endpoint.EndPoint;
import org.primefaces.model.diagram.overlay.ArrowOverlay;
import org.primefaces.model.diagram.overlay.LabelOverlay;

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
    private int filtroRealizado; //Indica el numero que selecciona en el selectOneMenu, tipo de vehiculo
    private Nodo nodoMostrar = new Nodo(new Vehiculo()); //ayudante que toma los datos de la lista y me los va a mostrar en la paguina
    private ListaSE listaVehiculos = new ListaSE(); // Voy a tener acceso a los metodos de listaSE
    private int posicionQueSeraEliminada; //Variable que toma la posicion que requiero que se elimine
    private DefaultDiagramModel model;//Este es el diagrama que me permite ver la opciones en la web
    private DefaultDiagramModel model2;//Este es el diagrama que me permite ver la opciones en la web
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
    private boolean deshabilitarEliminarPorCabeza = false;//Indica en primera instancia que esta deshabilitado el boton 
    private boolean deshabilitarEliminarPorUltimo = false;//Indica en primera instancia que esta deshabilitado el boton 
    private boolean deshabilitarDiagrama = false;//Indica en primera instancia que esta deshabilitado el boton 
    private boolean deshabilitarPanel = false;//Indica en primera instancia que esta deshabilitado el boton
    //===============================================================================

    public BeanParqueadero() {
    }

    public int getFiltroRealizado() {
        return filtroRealizado;
    }

    public void setFiltroRealizado(int filtroRealizado) {
        this.filtroRealizado = filtroRealizado;
    }

    public boolean isDeshabilitarDiagrama() {
        return deshabilitarDiagrama;
    }

    public void setDeshabilitarDiagrama(boolean deshabilitarDiagrama) {
        this.deshabilitarDiagrama = deshabilitarDiagrama;
    }

    public boolean isDeshabilitarPanel() {
        return deshabilitarPanel;
    }

    public void setDeshabilitarPanel(boolean deshabilitarPanel) {
        this.deshabilitarPanel = deshabilitarPanel;
    }

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

    public void mostrarGraficos() {
        inicializarDiagramaClick();
        inicializarDigrama2();
//        init();
    }

    public void inicializarValidadores() {
        mostrarGraficos();
        verificarDatosVacios();
        verificarDatosConUnDato();
        deshabilitarDatosConElUltimo();
        vehiculosEnMemoria();
        filtroRealizado();
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

    public void deshabilitarDatosConElUltimo() {// Creo metodo que valida si esta en el ultimo, deshabilite la informacion que requiero
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
        try {
            listaVehiculos.adicionarNodoAlFinal(vehiculo);//Adiciono en lista
            deshabilitarNuevo = true;
            tipoVehiculoSeleccionado = 0;
            irAlPrimero();
            JsfUtil.addSuccessMessage("Se ha adicionado con éxito");
        } catch (ParqueaderoExepcion ex) {
            JsfUtil.addErrorMessage(ex.getMessage());
        }
    }

    public void guarfarVehiculoAlInicio() {
        try {
            listaVehiculos.adicionarNodoAlInicio(nodoMostrar.getDato());
            deshabilitarNuevo = true;
            tipoVehiculoSeleccionado = 0;
            irAlPrimero();
            JsfUtil.addSuccessMessage("Se ha adicionado con éxito Al Inicio");
        } catch (ParqueaderoExepcion ex) {
            JsfUtil.addErrorMessage(ex.getMessage());
        }

    }

    public void invertir() {
        try {
            listaVehiculos.invertirLista();
            deshabilitarNuevo = true;
            irAlPrimero();
            JsfUtil.addSuccessMessage("Se ha Invertido con éxito");
        } catch (ParqueaderoExepcion ex) {
            JsfUtil.addErrorMessage(ex.getMessage());
        }
    }

    public void eliminarVehiculo() {
        listaVehiculos.eliminarNodo(nodoMostrar.getDato());
        deshabilitarNuevo = true;
        if (listaVehiculos.getCabeza() == null) {
            nodoMostrar = new Nodo(new Vehiculo());
            tipoVehiculoSeleccionado = 0;
            opcionSeleccionado = true;
            numeroDeAsientos = 5;
            numeroDeToneladas = 6;
        } else {
            irAlPrimero();
        }
        JsfUtil.addSuccessMessage("Se ha Eliminado con éxito");
        deshabilitarNuevo = true;

    }

    public void eliminiarXPosicion() {
        listaVehiculos.eliminarNodoxPosicion(getPosicionQueSeraEliminada());
        deshabilitarNuevo = true;
        irAlPrimero();
        JsfUtil.addSuccessMessage("Se ha Eliminado x Posición con éxito");
    }

    public void metodoQueEliminaPosicionesImpares() {
        try {
            listaVehiculos.metodoQueEliminaPosicionesImpares();
            deshabilitarNuevo = true;
            irAlSguiente();
            JsfUtil.addSuccessMessage("Se ha Eliminado Posiciones Impares éxito");
        } catch (ParqueaderoExepcion ex) {
            JsfUtil.addErrorMessage(ex.getMessage());
        }

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
        JsfUtil.addSuccessMessage("Se ha Buscado la ciudad indicada con éxito");
    }

    public void eliminarPorCabe() {
        deshabilitarEliminarPorCabeza = true;
        JsfUtil.addSuccessMessage("Se ha Iniciado la Eliminacion desde la cabeza con éxito");
    }

    public void eliminarPorUlti() {
        deshabilitarEliminarPorUltimo = true;
        JsfUtil.addSuccessMessage("Se ha Iniciado la Eliminacion desde el ultimo con éxito");
    }

    public void eliminarCabeza() {
        listaVehiculos.eliminarCabeza();
        irAlSguiente();
    }

    public void eliminarUltimoDeLaLista() {
        nodoMostrar = listaVehiculos.eliminarUltimoDelaLista();
        listaVehiculos.eliminarNodo(nodoMostrar.getDato());
    }

    public void filtroRealizado() {//Metodo que me permite activar o desactivar panel o diagrama
        switch (filtroRealizado) {
            case 1:
                deshabilitarDiagrama = true;
                deshabilitarPanel = false;
                break;
            case 2:
                deshabilitarPanel = true;
                deshabilitarDiagrama = false;
                break;
            case 3:
                deshabilitarDiagrama = false;
                deshabilitarPanel = false;
                break;
            default:
                filtroRealizado = 0;
                break;
        }
    }

    @PostConstruct//para despues que se ins se llame este objeto
    public void llenarVehiculos() {
        try {
            listaVehiculos.adicionarNodoAlFinal(new Moto(true, "HAY876", new Date(), "manizales"));
            listaVehiculos.adicionarNodoAlFinal(new Buseta((byte) 30, "UNA123", new Date(), "manizales"));
            listaVehiculos.adicionarNodoAlFinal(new Moto(false, "SOL495", new Date(), "manizales"));
            listaVehiculos.adicionarNodoAlFinal(new Automovil(true, "COS467", new Date(), "manizales"));
            listaVehiculos.adicionarNodoAlFinal(new Volqueta((byte) 9, "ENM198", new Date(), "manizales"));
            listaVehiculos.adicionarNodoAlFinal(new Volqueta((byte) 18, "MIF027", new Date(), "manizales"));
////        listaVehiculos.adicionarNodoAlFinal(new Automovil(true, "5NAE033", new Date()));
            irAlPrimero();
        } catch (ParqueaderoExepcion ex) {
            JsfUtil.addErrorMessage(ex.getMessage());
        }
    }
//===================================================Diagrama PrimeFaces=======================================

    public void init() {
        model = new DefaultDiagramModel();
        model.setMaxConnections(-1);//

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
        nuevoVehiculo.setId(nodo.getDato().getPlaca());

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
        nuevoVehiculo.setId(nodo.getDato().getPlaca());
        model.addElement(nuevoVehiculo);
    }

    public void eliminarPorPlacaPrimerDiagramRealizado() {//-------------Metodo para eliminar posiciones por placa---------------------------------nuevo
        listaVehiculos.eliminarNodoXPlaca(PlacaSeleccionada2);
        irAlSguiente();
    }

    public void enviarAlFinalPrimerDiagramRealizado() {//-------------Metodo para enviar al final por placa---------------------------------nuevo
        listaVehiculos.enviarAlFinal(PlacaSeleccionada2);
        irAlPrimero();
    }

    public void enviarAlInicioPrimerDiagramRealizado() {//-------------Metodo para enviar al inicio por placa---------------------------------nuevo
        listaVehiculos.enviarAlInicio(PlacaSeleccionada2);
        irAlPrimero();
    }

    public void modificarInformacionDiagramaPrimerDiagramRealizado() { //-------------Metodo para modificar la informacion de fecha y valor pagado---------------------------------nuevo
        listaVehiculos.modificarInformacionDiagrama(PlacaSeleccionada2, nodoMostrar.getDato().getNuevofechaHoraEntrada(), nodoMostrar.getDato().getNuevoValorAPagar());
    }
    //===================================================Charts - Pie PrimeFaces=======================================
    private PieChartModel pieModel1;
    private PieChartModel pieModel2;

    public void inicializarGraficoCircular() {
        createPieModel1(numeroDeMotos, numeroDeAuto, numeroDeBusetas, numeroDeVolquetas);
        createPieModel2(numeroDeMotos, numeroDeAuto, numeroDeBusetas, numeroDeVolquetas);
    }

    public PieChartModel getPieModel1() {
        return pieModel1;
    }

    public PieChartModel getPieModel2() {
        return pieModel2;
    }

    private void createPieModel1(int moto, int auto, int buseta, int volqueta) {
        pieModel1 = new PieChartModel();

        pieModel1.set("Motos: " + moto, moto);
        pieModel1.set("Autos: " + auto, auto);
        pieModel1.set("Busetas: " + buseta, buseta);
        pieModel1.set("Volquetas: " + volqueta, volqueta);

        pieModel1.setTitle("Primer Grafica de contorno");
        pieModel1.setLegendPosition("w");

    }

    private void createPieModel2(int moto, int auto, int buseta, int volqueta) {
        pieModel2 = new PieChartModel();

        pieModel2.set("Motos: " + moto, moto);
        pieModel2.set("Autos: " + auto, auto);
        pieModel2.set("Busetas: " + buseta, buseta);
        pieModel2.set("Volquetas: " + volqueta, volqueta);

        pieModel2.setTitle("Segunda Grafica de % del 100%");
        pieModel2.setLegendPosition("e");
        pieModel2.setFill(false);
        pieModel2.setShowDataLabels(true);
        pieModel2.setDiameter(150);
    }

    //===================================================Charts - Bar PrimeFaces=======================================
    private BarChartModel barModel;
    private HorizontalBarChartModel horizontalBarModel;

    public void inicializarBarras() {
        createBarModels();
    }

    public BarChartModel getBarModel() {
        return barModel;
    }

    public HorizontalBarChartModel getHorizontalBarModel() {
        return horizontalBarModel;
    }

    private BarChartModel initBarModel(int moto, int auto, int buseta, int volqueta) {
        BarChartModel model = new BarChartModel();

        ChartSeries Vehiculos = new ChartSeries();
        Vehiculos.setLabel("Vehiculos");
        Vehiculos.set("Motos", moto);
        Vehiculos.set("Automovil", auto);
        Vehiculos.set("Buseta", buseta);
        Vehiculos.set("Volqueta", volqueta);

//        ChartSeries Autos = new ChartSeries(); // Esto me sirve para comparar con otros datos 
//        Autos.setLabel("Automovil");
////        Autos.set("Automovil", 52);
        model.addSeries(Vehiculos);
//        model.addSeries(Autos);
        return model;
    }

    private void createBarModels() {
        createBarModel();
    }

    private void createBarModel() {
        barModel = initBarModel(numeroDeMotos, numeroDeAuto, numeroDeBusetas, numeroDeVolquetas);

        barModel.setTitle("Grafica de Columnas");
        barModel.setLegendPosition("ne");//Posicion de la etiqueta
        barModel.setAnimate(true); // Este es la opcion de dar animacion a las columnas
        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Vehiculo"); //Nombre en eje X

        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Cantidad"); //Nombre en eje Y
        yAxis.setMin(0); //Intervalo desde
        yAxis.setMax(10);//intervalo Hasta
    }

    //=================================================Diagrama Realizado por el profesor=============================
    public void inicializarDigrama2() {

        model2 = new DefaultDiagramModel();
        model2.setMaxConnections(-1);

        //Defino el conector por defecto
        FlowChartConnector connector = new FlowChartConnector();
        connector.setPaintStyle("{strokeStyle:'#C7B097',lineWidth:3}");
        model2.setDefaultConnector(connector);

        if (getListaVehiculos().getCabeza() != null) {

            Nodo temp = getListaVehiculos().getCabeza();
            int x = 10;
            int y = 4;
            while (temp != null) {

                //De esta forma se adiciona los elementos
                Element elemento = new Element(temp.getDato().getPlaca(), x + "em", y + "em");
                model2.addElement(elemento);
                elemento.addEndPoint(new DotEndPoint(EndPointAnchor.LEFT));
                elemento.addEndPoint(new DotEndPoint(EndPointAnchor.BOTTOM));
                elemento.setId(temp.getDato().getPlaca());
                temp = temp.getSiguiente();
                x += 10;
                y += 6;

            }

            for (int i = 0; i < model2.getElements().size() - 1; i++) {
                model2.connect(createConnection(model2.getElements().get(i).getEndPoints().get(1),
                        model2.getElements().get(i + 1).getEndPoints().get(0), "Sig"));

            }

        }
    }

    public DefaultDiagramModel getModel2() {
        return model2;
    }

    //Une dos puntos de finalizacion con el conector que se haya definido 
    private Connection createConnection(EndPoint from, EndPoint to, String label) {
        Connection conn = new Connection(from, to);
        conn.getOverlays().add(new ArrowOverlay(20, 20, 1, 1));

        if (label != null) {
            conn.getOverlays().add(new LabelOverlay(label, "flow-label", 0.5));
        }
        return conn;
    }

    public void eliminarPorPlacaProfesor() {//-------------Metodo para eliminar posiciones por placa---------------------------------nuevo
        listaVehiculos.eliminarNodoXPlaca(PlacaSeleccionada);
        irAlSguiente();
    }

    public void enviarAlFinalProfesor() {//-------------Metodo para enviar al final por placa---------------------------------nuevo
        listaVehiculos.enviarAlFinal(PlacaSeleccionada);
        irAlPrimero();
    }

    public void enviarAlInicioProfesor() {//-------------Metodo para enviar al inicio por placa---------------------------------nuevo
        listaVehiculos.enviarAlInicio(PlacaSeleccionada);
        irAlPrimero();
    }

    public void modificarInformacionDiagramaProfesor() { //-------------Metodo para modificar la informacion de fecha y valor pagado---------------------------------nuevo
        listaVehiculos.modificarInformacionDiagrama(PlacaSeleccionada, nodoMostrar.getDato().getNuevofechaHoraEntrada(), nodoMostrar.getDato().getNuevoValorAPagar());
    }

    //=================================================================Diagrama que tiene lo requerido del click===================================
    //======================================Atributos==================================================================
    private String datoDeplaca = ""; // Creo esta variable para tomar el dato seleccionado por click

    private OrganigramNode rootNode; // Indica el nodo 
    private OrganigramNode selection; //Indica la sellecion realizada

    private boolean zoom = false; //Zoom
    private String style = "width: 500px"; //Tamaño del panel 
    private int leafNodeConnectorHeight = 0; // Espacio entre los nodos
    private boolean autoScrollToSelection = false; //Sroll de movimineto

    //===============================================Metodos Utilizados por el diagrama========================
    public void inicializarDiagramaClick() {

        selection = new DefaultOrganigramNode();

        rootNode = new DefaultOrganigramNode("pintarTitulo", "Placas de Vehiculos", null); //Nodo pricipal titulo
        rootNode.setCollapsible(false);
        rootNode.setDroppable(true);

        if (getListaVehiculos().getCabeza() != null) {
            Nodo temp = getListaVehiculos().getCabeza();
            while (temp != null) {
                //De esta forma se adiciona los elementos
                addDivision(rootNode, temp.getDato().getPlaca());
                setLeafNodeConnectorHeight(30);
                setZoom(true);
                temp = temp.getSiguiente();
            }
        }
    }

    protected OrganigramNode addDivision(OrganigramNode parent, String name) { //Metodo que adiciona las diviciones requeridas
        OrganigramNode divisionNode = new DefaultOrganigramNode("pintarDiagrama", name, parent);
        divisionNode.setDroppable(true);//
        divisionNode.setDraggable(true);//Arrastable
        divisionNode.setSelectable(true);//Seleccionable

        return divisionNode;
    }

    public void eliminarPorPlacaDos() {//-------------Metodo para eliminar posiciones por placa---------------------------------nuevo
        OrganigramNode currentSelection = OrganigramHelper.findTreeNode(rootNode, selection);
        setDatoDeplaca("" + currentSelection.getData());
        listaVehiculos.eliminarNodoXPlaca(getDatoDeplaca());
        listaVehiculos.eliminarNodoXPlaca(PlacaSeleccionada);
        irAlSguiente();
    }

    public void enviarAlFinal() {//-------------Metodo para enviar al final por placa---------------------------------nuevo
        OrganigramNode currentSelection = OrganigramHelper.findTreeNode(rootNode, selection);
        setDatoDeplaca("" + currentSelection.getData());
        listaVehiculos.enviarAlFinal(getDatoDeplaca());
        irAlPrimero();
    }

    public void enviarAlInicio() {//-------------Metodo para enviar al inicio por placa---------------------------------nuevo
        OrganigramNode currentSelection = OrganigramHelper.findTreeNode(rootNode, selection);
        setDatoDeplaca("" + currentSelection.getData());
        listaVehiculos.enviarAlInicio(getDatoDeplaca());
        irAlPrimero();
    }

    public void modificarInformacionDiagrama() { //-------------Metodo para modificar la informacion de fecha y valor pagado---------------------------------nuevo
        OrganigramNode currentSelection = OrganigramHelper.findTreeNode(rootNode, selection);
        setDatoDeplaca("" + currentSelection.getData());
        listaVehiculos.modificarInformacionDiagrama(getDatoDeplaca(), nodoMostrar.getDato().getNuevofechaHoraEntrada(), nodoMostrar.getDato().getNuevoValorAPagar());

    }

    public void nodeDragDropListener(OrganigramNodeDragDropEvent event) { // Puedo mover todo los nodos en las posiciones que yo requiera
        FacesMessage message = new FacesMessage();
        message.setSummary("Node '" + event.getOrganigramNode().getData()
                + "' moved from " + event.getSourceOrganigramNode().getData()
                + " to '" + event.getTargetOrganigramNode().getData() + "'");
        message.setSeverity(FacesMessage.SEVERITY_INFO);

        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void nodeSelectListener(OrganigramNodeSelectEvent event) { // Saca un mesaje el cual indica que dato a tomado del nodo
        FacesMessage message = new FacesMessage();
        message.setSummary("Node '" + event.getOrganigramNode().getData() + "' selected.");
//        setDatoDeplaca("" + event.getOrganigramNode().getData()); // Esta opción me sirve para dar datos por click izquiedo
        message.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void nodeCollapseListener(OrganigramNodeCollapseEvent event) { //Cuando utilice esta opcion se escondera la casilla 
        FacesMessage message = new FacesMessage();
        message.setSummary("Node '" + event.getOrganigramNode().getData() + "' collapsed.");
        message.setSeverity(FacesMessage.SEVERITY_INFO);

        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void nodeExpandListener(OrganigramNodeExpandEvent event) { //Cuando utilice esta opcion se mostrara la casilla 
        FacesMessage message = new FacesMessage();
        message.setSummary("Node '" + event.getOrganigramNode().getData() + "' expanded.");
        message.setSeverity(FacesMessage.SEVERITY_INFO);

        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    //=========================================================== getter and setter del codigo===========
    public String getDatoDeplaca() {
        return datoDeplaca;
    }

    public void setDatoDeplaca(String datoDeplaca) {
        this.datoDeplaca = datoDeplaca;
    }

    public OrganigramNode getRootNode() {
        return rootNode;
    }

    public void setRootNode(OrganigramNode rootNode) {
        this.rootNode = rootNode;
    }

    public OrganigramNode getSelection() {
        return selection;
    }

    public void setSelection(OrganigramNode selection) {
        this.selection = selection;
    }

    public boolean isZoom() {
        return zoom;
    }

    public void setZoom(boolean zoom) {
        this.zoom = zoom;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public int getLeafNodeConnectorHeight() {
        return leafNodeConnectorHeight;
    }

    public void setLeafNodeConnectorHeight(int leafNodeConnectorHeight) {
        this.leafNodeConnectorHeight = leafNodeConnectorHeight;
    }

    public boolean isAutoScrollToSelection() {
        return autoScrollToSelection;
    }

    public void setAutoScrollToSelection(boolean autoScrollToSelection) {
        this.autoScrollToSelection = autoScrollToSelection;
    }

    
    //===================================================Metodo Que permite tomar la placa seleccionada en el click==
    public void onClickRight() {
        String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("elementId");
        PlacaSeleccionada = id.replaceAll("frmVehiculos:diagrama-", "");
        PlacaSeleccionada2 = id.replaceAll("frmVehiculos:primerDiagramRealizado-", "");
        System.out.println("PlacaSeleccionada = " + PlacaSeleccionada); //Solo para realizar pruebas
//        System.out.println("PlacaSeleccionada = " + PlacaSeleccionada2);
    }

    private String PlacaSeleccionada = "";
    private String PlacaSeleccionada2 = "";
}

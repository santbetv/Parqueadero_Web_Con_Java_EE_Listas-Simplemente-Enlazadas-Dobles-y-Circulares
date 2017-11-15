package parqueaderoweb.controlador;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Random;
import java.util.UUID;
import javax.annotation.PostConstruct;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.diagram.Connection;
import org.primefaces.model.diagram.DefaultDiagramModel;
import org.primefaces.model.diagram.Element;
import org.primefaces.model.diagram.connector.FlowChartConnector;
import org.primefaces.model.diagram.endpoint.DotEndPoint;
import org.primefaces.model.diagram.endpoint.EndPoint;
import org.primefaces.model.diagram.endpoint.EndPointAnchor;
import org.primefaces.model.diagram.overlay.ArrowOverlay;
import org.primefaces.model.diagram.overlay.LabelOverlay;
import org.primefaces.model.mindmap.DefaultMindmapNode;
import org.primefaces.model.mindmap.MindmapNode;
import parqueadero.Controlador.ListaDEC;
import parqueadero.Controlador.exepciones.CasinoExepciones;
import parqueadero.Modelo.Casino;
import parqueadero.Modelo.NodoDEC;
import parqueaderoweb.utilidades.JsfUtil;

/**
 *
 * @author Santiago Betancur Villegas <santiago-betancur at hotmail.com>
 */
@Named(value = "beanPaginaListaDECircular")
@SessionScoped
public class BeanPaginaListaDECircular implements Serializable {

    //=====================================Atributos===============================================================
    private NodoDEC nodoMostrar = new NodoDEC(new Casino()); //ayudante que toma los datos de la lista y me los va a mostrar en la paguina
    private ListaDEC listaCasino = new ListaDEC(); // Voy a tener acceso a los metodos de listaSE
    private int numeroDeFichas = 0;
    private String valorBuscado = "";
    private String colorBuscadoXPosicion = "";
    private int numeroColor = 0;
    private String color = "";
    private String adicionarOtroColor = "";
    private int adicionarOtrasFichasNumeroDeFichas = 0;
    private boolean deshabilitarXDerecha = false;
    private boolean deshabilitarXIzquierda = false;
    private boolean pararRuleta = false;
    private boolean pararRuletaIzquierda = false;
    private String eliminarColorRepetido = "";

    //====================================Constructor==============================================================
    public BeanPaginaListaDECircular() {
    }
    //====================================Getter  and Setter=======================================================

    public String getEliminarColorRepetido() {
        return eliminarColorRepetido;
    }

    public void setEliminarColorRepetido(String eliminarColorRepetido) {
        this.eliminarColorRepetido = eliminarColorRepetido;
    }

    public int getAdicionarOtrasFichasNumeroDeFichas() {
        return adicionarOtrasFichasNumeroDeFichas;
    }

    public void setAdicionarOtrasFichasNumeroDeFichas(int adicionarOtrasFichasNumeroDeFichas) {
        this.adicionarOtrasFichasNumeroDeFichas = adicionarOtrasFichasNumeroDeFichas;
    }

    public String getAdicionarOtroColor() {
        return adicionarOtroColor;
    }

    public void setAdicionarOtroColor(String adicionarOtroColor) {
        this.adicionarOtroColor = adicionarOtroColor;
    }

    public boolean isPararRuletaIzquierda() {
        return pararRuletaIzquierda;
    }

    public void setPararRuletaIzquierda(boolean pararRuletaIzquierda) {
        this.pararRuletaIzquierda = pararRuletaIzquierda;
    }

    public boolean isDeshabilitarXIzquierda() {
        return deshabilitarXIzquierda;
    }

    public void setDeshabilitarXIzquierda(boolean deshabilitarXIzquierda) {
        this.deshabilitarXIzquierda = deshabilitarXIzquierda;
    }

    public boolean isPararRuleta() {
        return pararRuleta;
    }

    public void setPararRuleta(boolean pararRuleta) {
        this.pararRuleta = pararRuleta;
    }

    public boolean isDeshabilitarXDerecha() {
        return deshabilitarXDerecha;
    }

    public void setDeshabilitarXDerecha(boolean deshabilitarXDerecha) {
        this.deshabilitarXDerecha = deshabilitarXDerecha;
    }

    public String getColorBuscadoXPosicion() {
        return colorBuscadoXPosicion;
    }

    public void setColorBuscadoXPosicion(String colorBuscadoXPosicion) {
        this.colorBuscadoXPosicion = colorBuscadoXPosicion;
    }

    public String getValorBuscado() {
        return valorBuscado;
    }

    public void setValorBuscado(String valorBuscado) {
        this.valorBuscado = valorBuscado;
    }

    public int getNumeroColor() {
        return numeroColor;
    }

    public void setNumeroColor(int numeroColor) {
        this.numeroColor = numeroColor;
    }

    public int getNumeroDeFichas() {
        return numeroDeFichas;
    }

    public void setNumeroDeFichas(int numeroDeFichas) {
        this.numeroDeFichas = numeroDeFichas;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public NodoDEC getNodoMostrar() {
        return nodoMostrar;
    }

    public void setNodoMostrar(NodoDEC nodoMostrar) {
        this.nodoMostrar = nodoMostrar;
    }

    public ListaDEC getListaCasino() {
        return listaCasino;
    }

    public void setListaCasino(ListaDEC listaCasino) {
        this.listaCasino = listaCasino;
    }

    @PostConstruct//para despues que se ins se llame este objeto
    public void llenarCasino() {
        listaCasino.concatenarNumeroYLetra(4, "NEGRO");
        listaCasino.concatenarNumeroYLetra2(4, "AZUL");
        listaCasino.concatenarNumeroYLetra2(4, "ROJO");
        listaCasino.concatenarNumeroYLetra2(4, "VIOLETA");
    }

    public void valorColor() {
        switch (numeroColor) {
            case 1:
                color = "NEGRO";
                break;
            case 2:
                color = "ROJO";
                break;
            case 3:
                color = "AZUL";
                break;
            case 4:
                color = "VIOLETA";
                break;
        }
    }

    public void nuevoValorRuleta() {
        if (numeroColor == 0) {
            JsfUtil.addErrorMessage("Seleccione el color");
        } else {
            valorColor();
            inicializar();
            metodoQueParaLaRuleta();
            if (listaCasino.getCabeza() != null) {
                JsfUtil.addSuccessMessage("Ficha Adicionada");
                listaCasino.concatenarNumeroYLetra2(numeroDeFichas, color);
            } else {
                JsfUtil.addSuccessMessage("Ficha Adicionada");
                listaCasino.concatenarNumeroYLetra(numeroDeFichas, color);
            }
        }
    }

    public void adionarOtroColor() {
        inicializar();
        if (listaCasino.getCabeza() != null) {
            JsfUtil.addSuccessMessage("Ficha Adicionada");
            listaCasino.concatenarNumeroYLetra2(adicionarOtrasFichasNumeroDeFichas, adicionarOtroColor);
        } else {
            JsfUtil.addSuccessMessage("Ficha Adicionada");
            listaCasino.concatenarNumeroYLetra(adicionarOtrasFichasNumeroDeFichas, adicionarOtroColor);
        }

    }

    int divibleEntreElRatio = 0;

    public int getDivibleEntreElRatio() {
        return divibleEntreElRatio;
    }

    public void setDivibleEntreElRatio(int divibleEntreElRatio) {
        this.divibleEntreElRatio = divibleEntreElRatio;
    }

    public void metodoQueDeshabilitarXDerecha() {
        deshabilitarXDerecha = true;
        pararRuleta = false;
    }

    public void metodoQueDeshabilitarXIzquierda() {
        deshabilitarXIzquierda = true;
        pararRuleta = false;
    }

    public void metodoQueParaLaRuleta() {
        JsfUtil.addSuccessMessage("Ruleta Parada");
        pararRuleta = true;
        deshabilitarXDerecha = false;
        deshabilitarXIzquierda = false;
    }

    public void eliminarColorRepetido() {
        listaCasino.eliminarNodoRepetido(eliminarColorRepetido);
    }
    
    public void jugarPorDerecha() {
        int valorSeleccionado = 0;
        Random rnd = new Random();
        valorSeleccionado = (int) (rnd.nextDouble() * ((listaCasino.contarNodos() + 1) * 5000) + 1);
        divibleEntreElRatio = valorSeleccionado / 5000;
        if (divibleEntreElRatio == 0) {
            try {
                divibleEntreElRatio = (int) (rnd.nextDouble() * listaCasino.contarNodos() + 1);
                valorBuscado = "" + listaCasino.busNodoxPosicion(divibleEntreElRatio).getDato().getColor() + listaCasino.busNodoxPosicion(divibleEntreElRatio).getDato().getNumeroDeFichas();
                colorBuscadoXPosicion = listaCasino.busNodoxPosicion(divibleEntreElRatio).getDato().getColor();
                JsfUtil.addSuccessMessage("Ruleta Iniciada");
            } catch (CasinoExepciones ex) {
                JsfUtil.addErrorMessage(ex.getMessage());
            }
        } else {
            try {
                valorBuscado = "" + listaCasino.busNodoxPosicion(divibleEntreElRatio).getDato().getColor() + listaCasino.busNodoxPosicion(divibleEntreElRatio).getDato().getNumeroDeFichas();
                colorBuscadoXPosicion = listaCasino.busNodoxPosicion(divibleEntreElRatio).getDato().getColor();
                JsfUtil.addSuccessMessage("Ruleta Iniciada");
            } catch (CasinoExepciones ex) {
                JsfUtil.addErrorMessage(ex.getMessage());
            }
        }
        init();
    }

    public void jugarPorIzquierda() {
        int valorSeleccionado = 0;
        Random rnd = new Random();
        valorSeleccionado = (int) (rnd.nextDouble() * ((listaCasino.contarNodos()) * 5000) + 1);
        divibleEntreElRatio = valorSeleccionado / 5000;
        if (divibleEntreElRatio == 0) {
            try {
                divibleEntreElRatio = (int) (rnd.nextDouble() * listaCasino.contarNodos() + 1);
                valorBuscado = "" + listaCasino.busNodoxPosicionIzquierda(divibleEntreElRatio).getDato().getColor() + listaCasino.busNodoxPosicionIzquierda(divibleEntreElRatio).getDato().getNumeroDeFichas();
                colorBuscadoXPosicion = listaCasino.busNodoxPosicionIzquierda(divibleEntreElRatio).getDato().getColor();
                JsfUtil.addSuccessMessage("Ruleta Iniciada");
            } catch (CasinoExepciones ex) {
                JsfUtil.addErrorMessage(ex.getMessage());
            }
        } else {
            try {
                valorBuscado = "" + listaCasino.busNodoxPosicionIzquierda(divibleEntreElRatio).getDato().getColor() + listaCasino.busNodoxPosicionIzquierda(divibleEntreElRatio).getDato().getNumeroDeFichas();
                colorBuscadoXPosicion = listaCasino.busNodoxPosicionIzquierda(divibleEntreElRatio).getDato().getColor();
                JsfUtil.addSuccessMessage("Ruleta Iniciada");
            } catch (CasinoExepciones ex) {
                JsfUtil.addErrorMessage(ex.getMessage());
            }
        }
        init();
    }

    public void reiniciarJuego() {
        JsfUtil.addSuccessMessage("Juego Reiniciado");
        valorBuscado = "";
        colorBuscadoXPosicion = "";
        numeroColor = 0;
        numeroDeFichas = 0;
        listaCasino.setCabeza(null);
        inicializar();
        init();
    }

    //==========================Metodos Creados para la lista doblemente enlazada circular=========================
    public void mostrarGraficos() {
        inicializar();
        init();
//        MindmapView();
    }
    //========================================Diagrama======================================================================
    private DefaultDiagramModel model;
//

    public void inicializar() {
        model = new DefaultDiagramModel();
        model.setMaxConnections(-1);

        //Estilo de las flechas
        FlowChartConnector connector = new FlowChartConnector();
        connector.setPaintStyle("{strokeStyle:'#C7B097',lineWidth:3}");
        model.setDefaultConnector(connector);

        int totalfichas = listaCasino.contarNodos();
        int totalfichas2Cuarto = listaCasino.contarNodos() / 2;
        int totalfichas4Cuarto = listaCasino.contarNodos() / 4;

        if (getListaCasino().getCabeza() != null) {
            NodoDEC temp = getListaCasino().getCabeza();
            int x = 70;
            int y = 30;
            do {
                Element elemento = new Element((temp.getDato().getColor() + temp.getDato().getNumeroDeFichas()), x + "em", y + "em");
                switch (temp.getDato().getColor()) {
                    case "NEGRO":
                        elemento.setStyleClass("ui-diagram-black");
                        break;
                    case "ROJO":
                        elemento.setStyleClass("ui-diagram-red");
                        break;
                    case "AZUL":
                        elemento.setStyleClass("ui-diagram-blue");
                        break;
                    case "VIOLETA":
                        elemento.setStyleClass("ui-diagram-blueviolet");
                        break;
                    case "ROSADO":
                        elemento.setStyleClass("ui-diagram-rosa");
                        break;
                    default:
                        break;
                }
                model.addElement(elemento);
                elemento.addEndPoint(new DotEndPoint(EndPointAnchor.LEFT));//IZQUIERDA
                elemento.addEndPoint(new DotEndPoint(EndPointAnchor.TOP));//partesuperior 
                elemento.addEndPoint(new DotEndPoint(EndPointAnchor.RIGHT));//derecho
                elemento.addEndPoint(new DotEndPoint(EndPointAnchor.BOTTOM));
                temp = temp.getSiguiente();

                //posiciones 
                if (model.getElements().size() <= totalfichas4Cuarto) {
                    x += 9;
                    y += 9;
                } else if (model.getElements().size() > totalfichas4Cuarto && model.getElements().size() < totalfichas2Cuarto) {
                    x -= 9;
                    y += 9;
                } else if (model.getElements().size() <= totalfichas - totalfichas4Cuarto && model.getElements().size() >= totalfichas2Cuarto) {
                    x -= 9;
                    y -= 9;
                } else if (model.getElements().size() < totalfichas && model.getElements().size() > totalfichas2Cuarto + totalfichas4Cuarto) {
                    x += 9;
                    y -= 9;
                }
            } while (temp != listaCasino.getCabeza());
        }

        //Inicio flecha cabeza al ultimo
        NodoDEC temp2 = listaCasino.getCabeza();
        if (listaCasino.getCabeza() != null) {
            do {
                for (int i = totalfichas - 2; i < model.getElements().size() - 1; i++) {
                    model.connect(createConnection(model.getElements().get(0).getEndPoints().get(0), model.getElements().get(i + 1).getEndPoints().get(3), "Ant"));
                }
                for (int i = totalfichas - 2; i < model.getElements().size() - 1; i++) {
                    model.connect(createConnection(model.getElements().get(totalfichas - 1).getEndPoints().get(2), model.getElements().get(i - (totalfichas - 2)).getEndPoints().get(1), "Sig"));
                }
                temp2 = temp2.getAnterior();
            } while (temp2 != listaCasino.getCabeza());
            //Final flecha cabeza al ultimo

            //Inicio de flecha siguiente
            do {
                for (int i = 0; i < model.getElements().size() - 1; i++) {
                    model.connect(createConnection(model.getElements().get(i).getEndPoints().get(2), model.getElements().get(i + 1).getEndPoints().get(1), "Sig"));
                }
                temp2 = temp2.getSiguiente();
            } while (temp2 != listaCasino.getCabeza());
            //Final de flecha anterior

            //Inicio de flecha anterior
            do {
                for (int cont = model.getElements().size() - 1; cont > 0; cont--) {
                    model.connect(createConnection(model.getElements().get(cont).getEndPoints().get(0), model.getElements().get(cont - 1).getEndPoints().get(3), "Ant"));
                }
                temp2 = temp2.getAnterior();
            } while (temp2 != listaCasino.getCabeza());
            //Final de flecha anterior
        }
    }

    public DefaultDiagramModel getModel() {
        return model;
    }

    //Une dos puntos de finalizacion con el conector que se haya definido 
    private Connection createConnection(EndPoint from, EndPoint to, String label) {
        Connection conn = new Connection(from, to);
        conn.getOverlays().add(new ArrowOverlay(40, 20, 1, 1));

        if (label != null) {
            conn.getOverlays().add(new LabelOverlay(label, "flow-label", 0.2));
        }
        return conn;
    }

    //================================================================Diagrama Resutado
    private DefaultDiagramModel model2;

    public void init() {
        model2 = new DefaultDiagramModel();
        model2.setMaxConnections(-1);
        Element elementA = new Element(getValorBuscado(), "2.5em", "1em");
        switch (getColorBuscadoXPosicion()) {
            case "NEGRO":
                elementA.setStyleClass("ui-diagram-black");
                break;
            case "ROJO":
                elementA.setStyleClass("ui-diagram-red");
                break;
            case "AZUL":
                elementA.setStyleClass("ui-diagram-blue");
                break;
            case "VIOLETA":
                elementA.setStyleClass("ui-diagram-blueviolet");
                break;
            default:
                break;
        }
        model2.addElement(elementA);
    }

    public DefaultDiagramModel getModel2() {
        return model2;
    }

    //===============================================================Diagrama Gmap======================
    private MindmapNode root;

    private MindmapNode selectedNode;

    public void MindmapView() {
        root = new DefaultMindmapNode("google.com", "Google WebSite", "FFCC00", false);

        MindmapNode nuevoNodo = new DefaultMindmapNode("nuevoNodo");//Nuevo nodo

        root.addNode(nuevoNodo);//Muestro el nodo
    }

    public MindmapNode getRoot() {
        return root;
    }

    public MindmapNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(MindmapNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public void onNodeSelect(SelectEvent event) {
        MindmapNode node = (MindmapNode) event.getObject();

        //populate if not already loaded
        if (node.getChildren().isEmpty()) {
            Object label = node.getLabel();

            if (label.equals("NS(s)")) {
                for (int i = 0; i < 25; i++) {
                    node.addNode(new DefaultMindmapNode("ns" + i + ".google.com", "Namespace " + i + " of Google", "82c542", false));
                }
            } else if (label.equals("IPs")) {
                for (int i = 0; i < 18; i++) {
                    node.addNode(new DefaultMindmapNode("1.1.1." + i, "IP Number: 1.1.1." + i, "fce24f", false));
                }
            } else if (label.equals("Malware")) {
                for (int i = 0; i < 18; i++) {
                    String random = UUID.randomUUID().toString();
                    node.addNode(new DefaultMindmapNode("Malware-" + random, "Malicious Software: " + random, "3399ff", false));
                }
            } else if (label.equals("nuevoNodo")) {
                NodoDEC temp = listaCasino.getCabeza();
                String datos = "";
                if (listaCasino.getCabeza() != null) {
                    for (int i = 0; i < listaCasino.contarNodos(); i++) {
                        node.addNode(new DefaultMindmapNode(temp.getDato().getColor() + i));
                        do {
                            temp = temp.getSiguiente();
                        } while (temp != listaCasino.getCabeza());
                    }
                }
            }
        }
    }

    public void onNodeDblselect(SelectEvent event) {
        this.selectedNode = (MindmapNode) event.getObject();
    }

//---------------------------------------------------------------------------------------------------------------------
}

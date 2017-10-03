package parqueaderoweb.controlador;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author Santiago Betancur Villegas <santiago-betancur at hotmail.com>
 */
@Named(value = "beanParqueadero")
@SessionScoped
public class BeanParqueadero implements Serializable {
    
    private boolean deshabilitarNuevo=true;
    
    public BeanParqueadero() {
    }

    public boolean isDeshabilitarNuevo() {
        return deshabilitarNuevo;
    }

    public void setDeshabilitarNuevo(boolean deshabilitarNuevo) {
        this.deshabilitarNuevo = deshabilitarNuevo;
    }
    
    public void habilitarGuardado(){
        deshabilitarNuevo=false;
    }
    
}

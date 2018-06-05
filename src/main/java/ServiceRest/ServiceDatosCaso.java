
package ServiceRest;

import DaoGenerico.ConexionException;
import Facade.FacadeDatosCaso;
import Facade.FacadeInforme;
import Facade.FacadeUsuario;
import ModeloDTO.AbogadoDTO;
import ModeloDTO.DatosCasoDTO;
import ModeloDTO.DiligenciaDTO;
import ModeloDTO.InformeDTO;
import ModeloDTO.UsuarioDTO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import modelo.Datoscaso;
import modelo.Diligencia;
import modelo.Informe;
import modelo.Usuario;


@Path("/caso")
public class ServiceDatosCaso {
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public DatosCasoDTO addDatosCaso(DatosCasoDTO datos) throws ConexionException {
        
        FacadeDatosCaso facade = null;
        DatosCasoDTO dto = null;

        facade = new FacadeDatosCaso();
        Datoscaso dJPA = new Datoscaso();
        dJPA.setId(datos.getId());
        dJPA.setTipoCaso(datos.getTipoCaso());
        dJPA.setAbogadoAnterior(datos.getAbogadoAnterior());
        dJPA.setInformacionCaso(datos.getInformacionCaso());
        dJPA.setArchivoAdicional(datos.getArchivoAdicional());
        dJPA.setEstado(datos.getEstado());
                       
        
        System.out.println("##############");
        System.out.println(datos.getIdUsuario().getCc());
        
        FacadeUsuario fu= new FacadeUsuario();
        Usuario us = fu.busacarObj(1031164);
        dJPA.setIdUsuario(us);
        
        
        System.out.println("##############");
        System.out.println("##############");
        System.out.println(us);
        System.out.println("##############");
        System.out.println("##############");
        
        dJPA.setIdAbogado(null);
        
        
        facade.crearObj(dJPA);

        return dto;
    }
    
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<DatosCasoDTO> getDatosCaso_JSON() throws ConexionException {
        
        List<DatosCasoDTO> datosDTO = new ArrayList<DatosCasoDTO>();
        FacadeDatosCaso facade = null;
         
        facade = new FacadeDatosCaso();
        List<Datoscaso> datos = facade.buscarTodoObj();
        for (Datoscaso dato : datos) {
            DatosCasoDTO dto = new DatosCasoDTO(dato);
            dto.list_Diligencia(dato.getDiligenciaList());
            dto.list_Informe(dato.getInformeList());
            datosDTO.add(dto);
        }
        return datosDTO;
    }
    
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public DatosCasoDTO getDatoCaso(@PathParam("id") String id) throws ConexionException {
        
        FacadeDatosCaso facade = new FacadeDatosCaso();
      
        Datoscaso dato = facade.busacarObj(Integer.parseInt(id));
        DatosCasoDTO dto = new DatosCasoDTO(dato);
        dto.list_Diligencia(dato.getDiligenciaList());
        dto.list_Informe(dato.getInformeList());
        
        return dto;
    }
}

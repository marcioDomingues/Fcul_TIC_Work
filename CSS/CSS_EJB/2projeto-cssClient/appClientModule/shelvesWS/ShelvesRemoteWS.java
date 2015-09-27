
package shelvesWS;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.FaultAction;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "ShelvesRemoteWS", targetNamespace = "http://domain/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface ShelvesRemoteWS {


    /**
     * 
     * @param arg0
     * @return
     *     returns boolean
     * @throws Exception_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "addNormalShelf", targetNamespace = "http://domain/", className = "shelvesWS.AddNormalShelf")
    @ResponseWrapper(localName = "addNormalShelfResponse", targetNamespace = "http://domain/", className = "shelvesWS.AddNormalShelfResponse")
    @Action(input = "http://domain/ShelvesRemoteWS/addNormalShelfRequest", output = "http://domain/ShelvesRemoteWS/addNormalShelfResponse", fault = {
        @FaultAction(className = Exception_Exception.class, value = "http://domain/ShelvesRemoteWS/addNormalShelf/Fault/Exception")
    })
    public boolean addNormalShelf(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0)
        throws Exception_Exception
    ;

    /**
     * 
     * @param arg0
     * @return
     *     returns boolean
     * @throws Exception_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "removeShelf", targetNamespace = "http://domain/", className = "shelvesWS.RemoveShelf")
    @ResponseWrapper(localName = "removeShelfResponse", targetNamespace = "http://domain/", className = "shelvesWS.RemoveShelfResponse")
    @Action(input = "http://domain/ShelvesRemoteWS/removeShelfRequest", output = "http://domain/ShelvesRemoteWS/removeShelfResponse", fault = {
        @FaultAction(className = Exception_Exception.class, value = "http://domain/ShelvesRemoteWS/removeShelf/Fault/Exception")
    })
    public boolean removeShelf(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0)
        throws Exception_Exception
    ;

    /**
     * 
     * @return
     *     returns java.util.List<java.lang.String>
     * @throws Exception_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getShelves", targetNamespace = "http://domain/", className = "shelvesWS.GetShelves")
    @ResponseWrapper(localName = "getShelvesResponse", targetNamespace = "http://domain/", className = "shelvesWS.GetShelvesResponse")
    @Action(input = "http://domain/ShelvesRemoteWS/getShelvesRequest", output = "http://domain/ShelvesRemoteWS/getShelvesResponse", fault = {
        @FaultAction(className = Exception_Exception.class, value = "http://domain/ShelvesRemoteWS/getShelves/Fault/Exception")
    })
    public List<String> getShelves()
        throws Exception_Exception
    ;

    /**
     * 
     * @return
     *     returns java.lang.String
     * @throws Exception_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getMainShelf", targetNamespace = "http://domain/", className = "shelvesWS.GetMainShelf")
    @ResponseWrapper(localName = "getMainShelfResponse", targetNamespace = "http://domain/", className = "shelvesWS.GetMainShelfResponse")
    @Action(input = "http://domain/ShelvesRemoteWS/getMainShelfRequest", output = "http://domain/ShelvesRemoteWS/getMainShelfResponse", fault = {
        @FaultAction(className = Exception_Exception.class, value = "http://domain/ShelvesRemoteWS/getMainShelf/Fault/Exception")
    })
    public String getMainShelf()
        throws Exception_Exception
    ;

}

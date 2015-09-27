
package libraryWS;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "LibraryRemoteWSService", targetNamespace = "http://domain/", wsdlLocation = "http://nunos-mac-mini.local:8080/LibraryRemoteWSService/LibraryRemoteWS?WSDL")
public class LibraryRemoteWSService
    extends Service
{

    private final static URL LIBRARYREMOTEWSSERVICE_WSDL_LOCATION;
    private final static WebServiceException LIBRARYREMOTEWSSERVICE_EXCEPTION;
    private final static QName LIBRARYREMOTEWSSERVICE_QNAME = new QName("http://domain/", "LibraryRemoteWSService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://nunos-mac-mini.local:8080/LibraryRemoteWSService/LibraryRemoteWS?WSDL");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        LIBRARYREMOTEWSSERVICE_WSDL_LOCATION = url;
        LIBRARYREMOTEWSSERVICE_EXCEPTION = e;
    }

    public LibraryRemoteWSService() {
        super(__getWsdlLocation(), LIBRARYREMOTEWSSERVICE_QNAME);
    }

    public LibraryRemoteWSService(WebServiceFeature... features) {
        super(__getWsdlLocation(), LIBRARYREMOTEWSSERVICE_QNAME, features);
    }

    public LibraryRemoteWSService(URL wsdlLocation) {
        super(wsdlLocation, LIBRARYREMOTEWSSERVICE_QNAME);
    }

    public LibraryRemoteWSService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, LIBRARYREMOTEWSSERVICE_QNAME, features);
    }

    public LibraryRemoteWSService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public LibraryRemoteWSService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns LibraryRemoteWS
     */
    @WebEndpoint(name = "LibraryRemoteWSPort")
    public LibraryRemoteWS getLibraryRemoteWSPort() {
        return super.getPort(new QName("http://domain/", "LibraryRemoteWSPort"), LibraryRemoteWS.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns LibraryRemoteWS
     */
    @WebEndpoint(name = "LibraryRemoteWSPort")
    public LibraryRemoteWS getLibraryRemoteWSPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://domain/", "LibraryRemoteWSPort"), LibraryRemoteWS.class, features);
    }

    private static URL __getWsdlLocation() {
        if (LIBRARYREMOTEWSSERVICE_EXCEPTION!= null) {
            throw LIBRARYREMOTEWSSERVICE_EXCEPTION;
        }
        return LIBRARYREMOTEWSSERVICE_WSDL_LOCATION;
    }

}
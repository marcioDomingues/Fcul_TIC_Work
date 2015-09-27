
package libraryWS;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the libraryWS package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetEMedia_QNAME = new QName("http://domain/", "getEMedia");
    private final static QName _GetEMediaResponse_QNAME = new QName("http://domain/", "getEMediaResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: libraryWS
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetEMedia }
     * 
     */
    public GetEMedia createGetEMedia() {
        return new GetEMedia();
    }

    /**
     * Create an instance of {@link GetEMediaResponse }
     * 
     */
    public GetEMediaResponse createGetEMediaResponse() {
        return new GetEMediaResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEMedia }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://domain/", name = "getEMedia")
    public JAXBElement<GetEMedia> createGetEMedia(GetEMedia value) {
        return new JAXBElement<GetEMedia>(_GetEMedia_QNAME, GetEMedia.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEMediaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://domain/", name = "getEMediaResponse")
    public JAXBElement<GetEMediaResponse> createGetEMediaResponse(GetEMediaResponse value) {
        return new JAXBElement<GetEMediaResponse>(_GetEMediaResponse_QNAME, GetEMediaResponse.class, null, value);
    }

}

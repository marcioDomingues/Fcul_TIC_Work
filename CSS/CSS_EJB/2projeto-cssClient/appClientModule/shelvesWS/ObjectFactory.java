
package shelvesWS;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the shelvesWS package. 
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

    private final static QName _RemoveShelfResponse_QNAME = new QName("http://domain/", "removeShelfResponse");
    private final static QName _GetShelvesResponse_QNAME = new QName("http://domain/", "getShelvesResponse");
    private final static QName _GetMainShelf_QNAME = new QName("http://domain/", "getMainShelf");
    private final static QName _AddNormalShelf_QNAME = new QName("http://domain/", "addNormalShelf");
    private final static QName _RemoveShelf_QNAME = new QName("http://domain/", "removeShelf");
    private final static QName _AddNormalShelfResponse_QNAME = new QName("http://domain/", "addNormalShelfResponse");
    private final static QName _GetMainShelfResponse_QNAME = new QName("http://domain/", "getMainShelfResponse");
    private final static QName _GetShelves_QNAME = new QName("http://domain/", "getShelves");
    private final static QName _Exception_QNAME = new QName("http://domain/", "Exception");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: shelvesWS
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetShelvesResponse }
     * 
     */
    public GetShelvesResponse createGetShelvesResponse() {
        return new GetShelvesResponse();
    }

    /**
     * Create an instance of {@link RemoveShelfResponse }
     * 
     */
    public RemoveShelfResponse createRemoveShelfResponse() {
        return new RemoveShelfResponse();
    }

    /**
     * Create an instance of {@link GetMainShelf }
     * 
     */
    public GetMainShelf createGetMainShelf() {
        return new GetMainShelf();
    }

    /**
     * Create an instance of {@link AddNormalShelf }
     * 
     */
    public AddNormalShelf createAddNormalShelf() {
        return new AddNormalShelf();
    }

    /**
     * Create an instance of {@link RemoveShelf }
     * 
     */
    public RemoveShelf createRemoveShelf() {
        return new RemoveShelf();
    }

    /**
     * Create an instance of {@link AddNormalShelfResponse }
     * 
     */
    public AddNormalShelfResponse createAddNormalShelfResponse() {
        return new AddNormalShelfResponse();
    }

    /**
     * Create an instance of {@link GetMainShelfResponse }
     * 
     */
    public GetMainShelfResponse createGetMainShelfResponse() {
        return new GetMainShelfResponse();
    }

    /**
     * Create an instance of {@link GetShelves }
     * 
     */
    public GetShelves createGetShelves() {
        return new GetShelves();
    }

    /**
     * Create an instance of {@link Exception }
     * 
     */
    public Exception createException() {
        return new Exception();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveShelfResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://domain/", name = "removeShelfResponse")
    public JAXBElement<RemoveShelfResponse> createRemoveShelfResponse(RemoveShelfResponse value) {
        return new JAXBElement<RemoveShelfResponse>(_RemoveShelfResponse_QNAME, RemoveShelfResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetShelvesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://domain/", name = "getShelvesResponse")
    public JAXBElement<GetShelvesResponse> createGetShelvesResponse(GetShelvesResponse value) {
        return new JAXBElement<GetShelvesResponse>(_GetShelvesResponse_QNAME, GetShelvesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMainShelf }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://domain/", name = "getMainShelf")
    public JAXBElement<GetMainShelf> createGetMainShelf(GetMainShelf value) {
        return new JAXBElement<GetMainShelf>(_GetMainShelf_QNAME, GetMainShelf.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddNormalShelf }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://domain/", name = "addNormalShelf")
    public JAXBElement<AddNormalShelf> createAddNormalShelf(AddNormalShelf value) {
        return new JAXBElement<AddNormalShelf>(_AddNormalShelf_QNAME, AddNormalShelf.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveShelf }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://domain/", name = "removeShelf")
    public JAXBElement<RemoveShelf> createRemoveShelf(RemoveShelf value) {
        return new JAXBElement<RemoveShelf>(_RemoveShelf_QNAME, RemoveShelf.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddNormalShelfResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://domain/", name = "addNormalShelfResponse")
    public JAXBElement<AddNormalShelfResponse> createAddNormalShelfResponse(AddNormalShelfResponse value) {
        return new JAXBElement<AddNormalShelfResponse>(_AddNormalShelfResponse_QNAME, AddNormalShelfResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMainShelfResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://domain/", name = "getMainShelfResponse")
    public JAXBElement<GetMainShelfResponse> createGetMainShelfResponse(GetMainShelfResponse value) {
        return new JAXBElement<GetMainShelfResponse>(_GetMainShelfResponse_QNAME, GetMainShelfResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetShelves }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://domain/", name = "getShelves")
    public JAXBElement<GetShelves> createGetShelves(GetShelves value) {
        return new JAXBElement<GetShelves>(_GetShelves_QNAME, GetShelves.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://domain/", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<Exception>(_Exception_QNAME, Exception.class, null, value);
    }

}

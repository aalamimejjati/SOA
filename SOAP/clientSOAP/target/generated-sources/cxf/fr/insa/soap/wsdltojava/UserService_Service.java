package fr.insa.soap.wsdltojava;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.4.2
 * 2024-01-05T09:26:38.463+01:00
 * Generated source version: 3.4.2
 *
 */
@WebServiceClient(name = "UserService",
                  wsdlLocation = "file:/home/alami-mejjat/eclipse-workspace/clientSOAP/src/main/resources/wsdl/analyzer.wsdl",
                  targetNamespace = "http://soap.insa.fr/")
public class UserService_Service extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://soap.insa.fr/", "UserService");
    public final static QName UserServicePort = new QName("http://soap.insa.fr/", "UserServicePort");
    static {
        URL url = null;
        try {
            url = new URL("file:/home/alami-mejjat/eclipse-workspace/clientSOAP/src/main/resources/wsdl/analyzer.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(UserService_Service.class.getName())
                .log(java.util.logging.Level.INFO,
                     "Can not initialize the default wsdl from {0}", "file:/home/alami-mejjat/eclipse-workspace/clientSOAP/src/main/resources/wsdl/analyzer.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public UserService_Service(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public UserService_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public UserService_Service() {
        super(WSDL_LOCATION, SERVICE);
    }

    public UserService_Service(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public UserService_Service(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public UserService_Service(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }




    /**
     *
     * @return
     *     returns UserService
     */
    @WebEndpoint(name = "UserServicePort")
    public UserService getUserServicePort() {
        return super.getPort(UserServicePort, UserService.class);
    }

    /**
     *
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns UserService
     */
    @WebEndpoint(name = "UserServicePort")
    public UserService getUserServicePort(WebServiceFeature... features) {
        return super.getPort(UserServicePort, UserService.class, features);
    }

}

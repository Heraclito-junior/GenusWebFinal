package ejb;

import java.io.ByteArrayOutputStream;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.swing.text.Element;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import dao.CategoriaDAO;

import dominio.Categoria;
import dominio.Contato;
import dominio.Usuario;

@Stateless
public class SOAPClientSAAJ {
	
	public String iniciar(String cpf, String data){
		
        try {
        	
        	
            // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            // Send SOAP Message to SOAP Server
            String url = "http://www.soawebservices.com.br/webservices/producao/cdc/cdc.asmx";
            SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(cpf,data), url);
            
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            soapResponse.writeTo(stream);
            String message = new String(stream.toByteArray(), "utf-8");
            
            
            if(buscarStatus(message)==0){
            	return "0";
            }
            
            
            
            
            
            
            //printSOAPResponse(soapResponse);

            soapConnection.close();
            
            return buscarNome(message);
            
        }catch (Exception e) {
            System.err.println("Error occurred while sending SOAP Request to Server");
            e.printStackTrace();
            return "2";
        }
        
		

	}

    private static SOAPMessage createSOAPRequest(String cpf, String data) throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        String serverURI = "SOAWebServices/PessoaFisicaSimplificada";
        

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("soap", serverURI);

        /*
        Constructed SOAP Request Message:
        <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:example="http://ws.cdyne.com/">
            <SOAP-ENV:Header/>
            <SOAP-ENV:Body>
                <example:VerifyEmail>
                    <example:email>mutantninja@gmail.com</example:email>
                    <example:LicenseKey>123</example:LicenseKey>
                </example:VerifyEmail>
            </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>
         */

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("PessoaFisicaSimplificada", "","SOAWebServices");
        SOAPElement soapBodyElemC = soapBodyElem.addChildElement("Credenciais");
        SOAPElement soapBodyElem1 = soapBodyElemC.addChildElement("Email");
        soapBodyElem1.addTextNode("wreuel@gmail.com");
        SOAPElement soapBodyElem2 = soapBodyElemC.addChildElement("Senha");
        soapBodyElem2.addTextNode("JZzV5mqk1");
        SOAPElement soapBodyElem3 = soapBodyElem.addChildElement("Documento");
        soapBodyElem3.addTextNode(cpf);
        SOAPElement soapBodyElem4 = soapBodyElem.addChildElement("DataNascimento");
        soapBodyElem4.addTextNode(data);
        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", serverURI );

        soapMessage.saveChanges();

        /* Print the request message */
        System.out.print("Request SOAP Message = ");
        soapMessage.writeTo(System.out);
        System.out.println();

        return soapMessage;
    }
    
    

    /**
     * Method used to print the SOAP Response
     */
    private static void printSOAPResponse(SOAPMessage soapResponse) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        soapResponse.writeTo(stream);
        String message = new String(stream.toByteArray(), "utf-8");
        
        
        Source sourceContent = soapResponse.getSOAPPart().getContent();
        System.out.print("\nResponse SOAP Message = ");
        StreamResult result = new StreamResult(System.out);
        transformer.transform(sourceContent, result);
        
        
        
        
        System.out.println("quadrado");
        
        
        System.out.println(message);
        System.out.println("resultado foi : "+buscarStatus(message));
        
        System.out.println("resultado foi : "+buscarNome(message));
    }
    
    public static String buscarNome(String total){
    	String ajuda="";
    	for(int i=0;i<total.length();i++){
    		
    		
    		if(total.charAt(i)=='<'&&total.charAt(i+1)=='N'&&total.charAt(i+2)=='o'&&total.charAt(i+3)=='m'&&total.charAt(i+4)=='e'&&total.charAt(i+5)=='>'){
    			for(int j=i+6;total.charAt(j)!='<';j++){
    				ajuda+=total.charAt(j);
    				
    			}
    			//System.out.println("achou de verdade");
    			if(total.charAt(i+8)=='f'&&total.charAt(i+9)=='a'){
    			}
    			if(total.charAt(i+8)=='t'&&total.charAt(i+9)=='r'){
    			}
    			
    		}
    		if(total.charAt(i)=='S'&&total.charAt(i+1)=='t'&&total.charAt(i+2)=='a'&&total.charAt(i+3)=='t'&&total.charAt(i+4)=='u'&&total.charAt(i+5)=='s'){
    			//System.out.println("achou 111 caralho");
    		}else{
    			//System.out.println("BUUUUU");
    		}
    	}
    	return ajuda;
    }
    public static int buscarStatus(String total){
    	
    	for(int i=0;i<total.length();i++){
    		
    		
    		if(total.charAt(i)=='<'&&total.charAt(i+1)=='S'&&total.charAt(i+2)=='t'&&total.charAt(i+3)=='a'&&total.charAt(i+4)=='t'&&total.charAt(i+5)=='u'&&total.charAt(i+6)=='s'&&total.charAt(i+7)=='>'){
    			//System.out.println("achou de verdade");
    			if(total.charAt(i+8)=='f'&&total.charAt(i+9)=='a'){
    				return 0;
    			}
    			if(total.charAt(i+8)=='t'&&total.charAt(i+9)=='r'){
    				return 1;
    			}
    			
    		}
    		if(total.charAt(i)=='S'&&total.charAt(i+1)=='t'&&total.charAt(i+2)=='a'&&total.charAt(i+3)=='t'&&total.charAt(i+4)=='u'&&total.charAt(i+5)=='s'){
    			//System.out.println("achou 111 caralho");
    		}else{
    			//System.out.println("BUUUUU");
    		}
    	}
    	return 2;
    }
}
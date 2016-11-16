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
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
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
import dao.ClienteDAO;
import dominio.Categoria;
import dominio.Cliente;
import dominio.Contato;
import dominio.Usuario;

@Stateless
public class clienteService {
	public clienteService() {
		super();
		//cliente=new Cliente();
	}

	@Inject
	private CategoriaDAO categoriaDAO;
	
	@Inject
	private ClienteDAO clienteDAO;
	
	//Cliente cliente;
	
	
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int cadastrarCategoria(Categoria categoria){
		
		Categoria c = categoriaDAO.buscarCategoriaNome(categoria.getNome());
		if (c == null) {
			categoriaDAO.salvar(categoria);
			
			
			return 1;
		} else {
			
			return 0;
		}

	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public String filtrar(String cpf,String data){
		
		Long aux=Long.parseLong(cpf);
		Long tamanho=999999999L;
		
		String[] parts = data.split("-");
		if(parts.length!=3){
			
			return "0";
			
		}
		
		if (parts[0].matches("[0-9]+")&&parts[1].matches("[0-9]+")&&parts[2].matches("[0-9]+")){
			
		}else{
			
			return "1";
		}
		
		
		int ano=Integer.parseInt(parts[2]);
		int mes=Integer.parseInt(parts[1]);
		int dia=Integer.parseInt(parts[0]);
		int quantosDiasTemOMes=0;
		
		if(mes==1||mes==3||mes==5||mes==7||mes==8||mes==10||mes==12){
			quantosDiasTemOMes=31;
		}
		
		if(mes==4||mes==6||mes==9||mes==11){
			quantosDiasTemOMes=30;
		}
		if(mes==2){
			if(ano % 400 == 0){
				quantosDiasTemOMes=29;

	        } else if((ano % 4 == 0) && (ano % 100 != 0)){
				quantosDiasTemOMes=29;

	        } else{
				quantosDiasTemOMes=28;
	        }
		}
		
		if(dia>quantosDiasTemOMes){
			
			return "2";
		}
		
		if(cpf.length()<11){
			
			return "3";			
		}
		
		
		Cliente c = clienteDAO.buscarClienteCPF(aux);
		if (c == null) {
			
			
			Cliente temp=new Cliente();
			temp.setCPF(aux);
			temp.setData(data);
			String test=iniciar(cpf, data);
			if(test.equalsIgnoreCase("0")){
				
				return "4";
			}else{
				temp.setNome(test);
				String nome=test;
				return nome;
			}
			
			
			
			
			
		} else {
			
			return "5";
		}
		

	}
	
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
        
        
        
    }
    
    public static String buscarNome(String total){
    	String ajuda="";
    	for(int i=0;i<total.length();i++){
    		
    		
    		if(total.charAt(i)=='<'&&total.charAt(i+1)=='N'&&total.charAt(i+2)=='o'&&total.charAt(i+3)=='m'&&total.charAt(i+4)=='e'&&total.charAt(i+5)=='>'){
    			for(int j=i+6;total.charAt(j)!='<';j++){
    				ajuda+=total.charAt(j);
    				
    			}
    			if(total.charAt(i+8)=='f'&&total.charAt(i+9)=='a'){
    			}
    			if(total.charAt(i+8)=='t'&&total.charAt(i+9)=='r'){
    			}
    			
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
	
	
	
	public List<Categoria> listarCategoria() {
		
		return categoriaDAO.listar();
	}

	public int confirmar(String cpf, String data, String nome) {
		// TODO Auto-generated method stub
		if(nome.equals("")){
			
			return 0;
		}
		Long aux=Long.parseLong(cpf);
		
		Cliente c = clienteDAO.buscarClienteCPF(aux);
		if (c == null) {
			Cliente temp=new Cliente();
			temp.setCPF(aux);
			temp.setData(data);
			temp.setNome(nome);
			clienteDAO.salvar(temp);
			//Cliente cliente = new Cliente();
			
			return 1;
		
		}
		return 2;
	}

	public List<Cliente> listar() {
		return clienteDAO.listar();
	}
}
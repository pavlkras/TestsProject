package handlers;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import tel_ran.tests.controller.HeaderRequestInterceptor;
import tel_ran.tests.controller.MainController;
import tel_ran.tests.services.fields.Role;
import tel_ran.tests.services.interfaces.ICommonService;
import tel_ran.tests.strings.JSONKeys;
import tel_ran.tests.users.Visitor;
import tel_ran.tests.utils.JsonFabric;


public abstract class AbstractHandler implements IHandler {
	
	@Value("${host.name.${phase}}")
	protected String hostname;
	
	protected String token = ""; //token for getting information from BES. It contains id and role	
	protected Role role;
	protected int roleNumber;
	protected final static String ROLE = "role";
	protected static String PATH_ADDRESS_TO_SERVICE = "";
	
	protected ICommonService service;
	

	public String getPossibleAutoCaterories() {
		String result = "{}";
		
		//1 - query preparing
		RestTemplate restTemplate = new RestTemplate();
		HeaderRequestInterceptor interceptor = new HeaderRequestInterceptor("Authorization", token);		
		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
		interceptors.add(interceptor);			
		restTemplate.setInterceptors(interceptors);
			
		//2 - get info
		try {
			result = restTemplate.getForObject(this.hostname+"/tests/autoList", String.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		return result;
	}
	
	@Override
	public String getCommonCustomCategories() {
		String result = "{}";
		
		//1 - query preparing
		RestTemplate restTemplate = new RestTemplate();
				
		//2 - get info
		try {
			result = restTemplate.getForObject(this.hostname+"/tests/adminCustomList", String.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}


	@Override
	public int getRoleNumber() {		
		return role.ordinal();
	}
	

	public String getToken() {
		return token;
	}

	@Override
	public void setToken(String token) {
		this.token = token;
	}
	
	public String logInPage() {
		return "UserSignIn";	
	}
	
	public Map<String, Object> logInAction(String userEmail, String password) {
//		String outPage = "UserSignIn";
		Map<String, Object> result = new HashMap<String, Object>();
		
		RestTemplate restTemplate = new RestTemplate();		
				
		try {
			String request = JsonFabric.getAutorizationJson(userEmail, password);
			
			String response = restTemplate.postForObject(this.hostname+"/guest/login", request, 
					String.class);
			
			JSONObject jsn = new JSONObject(response);
			if(jsn.has(JSONKeys.ERROR)) {
				System.out.println(jsn.get(JSONKeys.ERROR));
				result.put("error", "connecting error");
				} else {
				
					String token = jsn.getString(JSONKeys.AUTO_TOKEN);
			
					if(token.equals("") || token.length()<1) {
						result.put("error", "wrong password or email");		
					} else {				
						int roleNumber = jsn.getInt(JSONKeys.AUTO_ROLENUMBER);						
						Visitor visitor = new Visitor(roleNumber);						
						visitor.setToken(token);		
						visitor.setEmail(userEmail);
						setVisitorInfo(visitor, jsn);						
						result.put("result", visitor);		
					}
				}				
			
		} catch (JSONException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
					
		return result;	
	}
	
	private void setVisitorInfo(Visitor visitor, JSONObject jsn) throws JSONException, ParseException {
		if(jsn.has(JSONKeys.AUTO_ADDRESS))
			visitor.setAddress(jsn.getString(JSONKeys.AUTO_ADDRESS));
		if(jsn.has(JSONKeys.AUTO_BIRTHDATE)) {
			visitor.setBirthDate(getDateFromJSON(jsn.getString(JSONKeys.AUTO_BIRTHDATE)));
		}	
		if(jsn.has(JSONKeys.AUTO_FIRSTNAME)) {
			visitor.setFirstName(jsn.getString(JSONKeys.AUTO_FIRSTNAME));
		}
		if(jsn.has(JSONKeys.AUTO_LASTNAME)) {
			visitor.setLastName(jsn.getString(JSONKeys.AUTO_LASTNAME));
		}
		if(jsn.has(JSONKeys.AUTO_NICKNAME)) {
			visitor.setNickName(jsn.getString(JSONKeys.AUTO_NICKNAME));
		}
		if(jsn.has(JSONKeys.AUTO_PASSPORTID)) {
			visitor.setPassportNumber(jsn.getString(JSONKeys.AUTO_PASSPORTID));
		}
	}
	
	private void setCompanyInfo(Visitor visitor, JSONObject jsn) throws JSONException, ParseException {
		if(jsn.has(JSONKeys.AUTO_EMAIL))
			visitor.setEmail(jsn.getString(JSONKeys.AUTO_EMAIL));
		if(jsn.has(JSONKeys.AUTO_EMPL_NUMBER)) {
			visitor.setEmployesNumber(jsn.getString(JSONKeys.AUTO_EMPL_NUMBER));
		}	
		if(jsn.has(JSONKeys.AUTO_SPECIALIZATION)) {
			visitor.setSpecialization(jsn.getString(JSONKeys.AUTO_SPECIALIZATION));
		}
		if(jsn.has(JSONKeys.AUTO_WEBSITE)) {
			visitor.setSite(jsn.getString(JSONKeys.AUTO_WEBSITE));
		}
	}
	
	
	private Date getDateFromJSON(String date) throws ParseException {		
		SimpleDateFormat format = new SimpleDateFormat(JSONKeys.DATE_TEMPLATE);
		return format.parse(date);
	}
		
	@Override
	public String signUpAction(String firstname, String lastname,String email, String password, String nickname, Model model) {
		String outPage = "UserSignIn";
		
		try {
		// 1 - JSON preparing
		String request = JsonFabric.userSignUp(firstname, lastname, email, password, nickname);
		
		// 2 - request to server
		RestTemplate restTemplate = new RestTemplate();
		
	
		String result = restTemplate.postForObject(this.hostname+"/guest/signin", request, 
				String.class);
		
		// 3 - if false
		JSONObject jsn = new JSONObject(result);
		
		boolean userExist = jsn.getBoolean(JSONKeys.SIGNUP_USER_EXIST);
		boolean success = jsn.getBoolean(JSONKeys.SIGNUP_SUCCESS);
		
		if(success){			
			return logInPage();
		} else {
			if(userExist) {
				model.addAttribute("logedUser","User with this name has already been created !");
			} else {
				model.addAttribute("logedUser","Registration is Failed !");
			}
			model.addAttribute(MainController.ROLE, roleNumber);
		}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return outPage;
	}
	
	@Override
	public String companyLogInPage() {		 
		return "companyLogin";
	}
	

	@Override
	public Map<String, Object> companyLoginAction(String companyName,
			String password) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		RestTemplate restTemplate = new RestTemplate();
				
		try {
			String request = JsonFabric.getCompanyAutorizationJson(companyName, password);
			
			String response = restTemplate.postForObject(this.hostname+"/guest/company_login", request, 
					String.class);
								
			JSONObject jsn = new JSONObject(response);
			if(jsn.has(JSONKeys.ERROR)) {
				System.out.println(jsn.get(JSONKeys.ERROR));
				result.put("error", "connecting error");
				} else {
				
					String token = jsn.getString(JSONKeys.AUTO_TOKEN);
			
					if(token.equals("") || token.length()<1) {
						result.put("error", "wrong password or email");		
					} else {				
						int roleNumber = jsn.getInt(JSONKeys.AUTO_ROLENUMBER);						
						Visitor visitor = new Visitor(roleNumber);						
						visitor.setToken(token);		
						visitor.setCompanyName(companyName);								
						setCompanyInfo(visitor, jsn);
						result.put("result", visitor);		
					}
				}				
			
		} catch (JSONException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return result;
	}
	
	@Override
	public String companySignUp(String C_Name, String C_Site,
			String C_Specialization, String C_AmountEmployes,
			String C_Password, Model model) {
				
		try {
			// 1 - JSON preparing
			String request = JsonFabric.companySignUp(C_Name, C_Site, C_Specialization, C_AmountEmployes, C_Password);
			
			// 2 - request to server
			RestTemplate restTemplate = new RestTemplate();			
		
			String result = restTemplate.postForObject(this.hostname+"/guest/company_signup", request, 
					String.class);
						
			// 3 - if false
			JSONObject jsn = new JSONObject(result);
			
			boolean userExist = jsn.getBoolean(JSONKeys.SIGNUP_USER_EXIST);
			boolean success = jsn.getBoolean(JSONKeys.SIGNUP_SUCCESS);
			
			if(success){			
				return companyLogInPage();
			} else {
				if(userExist) {
					model.addAttribute("myResult","Company with this name has already been created !");
				} else {
					model.addAttribute("myResult","Registration is Failed !");
				}
				model.addAttribute(MainController.ROLE, roleNumber);
				model.addAttribute("myResult", "<H3>Company Added Success. Please log in</H3>");
			}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return companyLogInPage();
	}

	
	@Override
	public String getAccountInformation(Visitor visitor) {		
		return "{}";
	}
	
	@Override
	public boolean checkCompanyName(String name_company) {
		RestTemplate template = new RestTemplate();
		String url = this.hostname+"/guest/if_company_exist/" + name_company;
		boolean response = template.getForObject(url, Boolean.class);
	
		return response;
	}
	
	@Override
	public String[] findCompaniesByName(String jpaStr) {		
		return null;
	}
	
	//------------END  Use case Ordering Test 3.1.3-------------
	protected boolean sendEmail(String link, String personEmail) {
		boolean result = false;
		final String username = "senderurltest@gmail.com";
		final String password = "sender54321.com";
		String subject = "Email from HR";
		String text = "\nPress for this link :  "+ link + "\n";

		try {

			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "465");

			Session session = Session.getDefaultInstance(props, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});

			Message message = new MimeMessage(session);		            
			message.setFrom(new InternetAddress(username));		            
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(personEmail));		            
			message.setSubject(subject);		            
			message.setText(text);
			session.setDebug(true);

			Transport.send(message);
			result = true;

		}  catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();

		}
		return result;
	}

		
}

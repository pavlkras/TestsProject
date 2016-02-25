package tel_ran.test.access_filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tel_ran.tests.services.fields.Role;


@WebFilter(filterName="mainFilter")
public class AccessFilter implements Filter {
	
	private static AccessRule rules;
	
	
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String uri = req.getRequestURI();
		System.out.println("doFilter uri- "+uri);
		int role;
		HttpSession s = (HttpSession) req.getSession();
		
		try {			
			role = (int) s.getAttribute("role");			
			
		} catch(NullPointerException e) {
			role = 0;
			System.out.println("doFilter role=0");
//			Visitor visitor = new Visitor(role);
//			s.setAttribute("role", role);
//			s.setAttribute("visitor", visitor);
		}
		System.out.println("doFilter role- " +role);
		String redirection = rules.isApply(uri, role);		
		if(redirection==null) {
			chain.doFilter(request, response);
		} else {
			System.out.println("else redirect- "+redirection);
			resp.sendRedirect(req.getContextPath() + redirection);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		rules = new AccessRule();
		InputStreamReader stream = new InputStreamReader(filterConfig.getServletContext()
				.getResourceAsStream("/WEB-INF/access-rules"));
		BufferedReader reader = new BufferedReader(stream);
		
		
		String line;
		try {
			while((line = reader.readLine())!=null) {
				if(line.startsWith(AccessRule.COMMENT_IN_FILE) || line.length()<3) {
					continue;
				} else {
					rules.add(line);
				}			
			}
			reader.close();
			stream.close();
		} catch (IOException e) {			
			e.printStackTrace();
		}
		
		
		
	}
	
	
	
	private class AccessRule {
		
		Map<String, List<Role>> rulesMap = new HashMap<>();
		Map<Role, String> loginPages = new HashMap<>();
		private static final String ANY_ROLE = "*";
		private static final String ACCESS_DELIMETER = ",";
		private static final String DELIMETER = "~~";
		private static final String COMMENT_IN_FILE = "//";
		private static final String REDIRECTION = "[";
		private static final String FES = "/TestsProjectFes";
		
		
		private void add(String line) {
	
			if(line.startsWith(REDIRECTION)) {
				
				int redir = REDIRECTION.length();
				line = line.substring(redir);
				String[] words = line.split(DELIMETER);
				byte roleNumber = Byte.parseByte(words[0]);
				String address = words[1];
				loginPages.put(Role.values()[roleNumber], address);
				
			} else {
				
				String[] words2 = line.split(DELIMETER);
				String uriAdress = FES.concat(words2[0]);
				if(words2[1].contains(ANY_ROLE)) {
					rulesMap.put(uriAdress, null);
				} else {
					String[] roles = words2[1].split(ACCESS_DELIMETER);
					List<Role> allowedRoles = new ArrayList<Role>();
					for(String s : roles) {
						int numberRole = Integer.parseInt(s);
						Role role = Role.values()[numberRole];
						allowedRoles.add(role);
						rulesMap.put(uriAdress, allowedRoles);
					}	
					
				}
				
			}			
		}
		
		public String isApply(String uri, int role) {
			Role r = Role.values()[role];
			System.out.println("isApply role- "+role);
			String result = null;
			List<Role> list = rulesMap.get(uri);
			if(list!=null) {
				if(!list.contains(r))
					result = loginPages.get(list.get(0));
			} 
			return result;
		}
		
		

	}

}

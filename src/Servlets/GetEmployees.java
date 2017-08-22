package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import java.util.List;
import javax.persistence.Query;
import Entities.Employee;

@WebServlet("/GetEmployees")
public class GetEmployees extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public GetEmployees() {
        super();
    }
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(false);
		if (session == null) {
		    out.print("Please Login to Continue");
		} else {
			Employee sessionUser = (Employee)session.getAttribute("employee");
			if(!sessionUser.getRole().equals("manager")) {
				out.print("No access to these requests");
			}
			else {
				JSONObject output = new JSONObject();
				try {
					EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("First");
					EntityManager entitymanager = emfactory.createEntityManager();
					Query query = entitymanager.createNamedQuery("get employees");
					List<Employee> list = (List<Employee>)query.getResultList( );
					int i = 0;
					for(Employee e: list) {
						JSONObject obj = new JSONObject();
						obj.put("id", e.getId());
						obj.put("username", e.getUsername());
						obj.put("password", e.getPassword());
						obj.put("dob", e.getDob());
						obj.put("firstname", e.getFirstname());
						obj.put("gender", e.getGender());
						obj.put("lastname", e.getLastname());
						obj.put("contact", e.getContact());
						obj.put("role", e.getRole());
						output.put(String.valueOf(i),obj);
						i++;
					}
					out.print(output);
					entitymanager.close();
					emfactory.close();
				}
				catch (Exception e) {
					out.println("Username Already Exists");
				}
			}
		}
		out.close();
	}
}
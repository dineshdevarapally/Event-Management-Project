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

import Entities.Employee;

@WebServlet("/GetProfileDetails")
public class GetProfileDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public GetProfileDetails() {
        super();
    }
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(false);
		if (session == null) {
		    out.print("Please Login to Continue");
		} else {
			Employee e = (Employee)session.getAttribute("employee");
			int eid = e.getId();
			EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("First");
			EntityManager entitymanager = emfactory.createEntityManager();
			e = entitymanager.find(Employee.class, eid);
			entitymanager.close();
			emfactory.close();
			session.setAttribute("employee", e);
			JSONObject output = new JSONObject();
			output.put("username", e.getUsername());
			output.put("password", e.getPassword());
			output.put("role", e.getRole());
			output.put("gender", e.getGender());
			output.put("dob", e.getDob());
			output.put("id", String.valueOf(e.getId()));
			output.put("firstname", e.getFirstname());
			output.put("lastname", e.getLastname());
			output.put("contact", e.getContact());
			out.print(output);
		}
		out.close();
	}
}
package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Entities.Employee;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@WebServlet("/AddUser")
public class AddUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public AddUser() {
        super();
    }
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
				String username = request.getParameter("username");
				String password = request.getParameter("password");
				String firstname = request.getParameter("firstname");
				String lastname = request.getParameter("lastname");
				String gender = request.getParameter("gender");
				String dob = request.getParameter("dob");
				String role = request.getParameter("role");
				String contact = request.getParameter("contact");
				if(IsFieldEmpty(username) || IsFieldEmpty(password) || IsFieldEmpty(firstname) || IsFieldEmpty(lastname) || IsFieldEmpty(dob) || IsFieldEmpty(gender) || IsFieldEmpty(role) || !("operator".equals(role) || "manager".equals(role)) || IsFieldEmpty(contact)) {
					out.println("Invalid Entires");
				}
				else {
					Employee employee = new Employee(username, password, firstname, lastname, dob, contact, gender, role);
					try {
						EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("First");
						EntityManager entitymanager = emfactory.createEntityManager();
						entitymanager.getTransaction().begin();
						entitymanager.persist(employee);
						entitymanager.getTransaction().commit();
						entitymanager.close();
						emfactory.close();
						out.println("success");
					}
					catch (Exception e) {
						out.println("Username Already Exists");
					}
				}
			}
		}
		out.close();
	}
	private boolean IsFieldEmpty(String field){
		if(field == null || "".equals(field)) {
			return true;
		}
		return false;
	}
}
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

import Entities.Employee;

@WebServlet("/EditUser")
public class EditUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public EditUser() {
        super();
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(false);
		if (session == null) {
		    out.print("Please Login to Continue");
		} else {
			Employee sessionUser = (Employee)session.getAttribute("employee");
			String username = request.getParameter("username");
			if( !(sessionUser.getRole().equals("manager") || sessionUser.getUsername().equals(username)) ) {
				out.print("No access to these requests");
			}
			else {
				String password = request.getParameter("password");
				String firstname = request.getParameter("firstname");
				String lastname = request.getParameter("lastname");
				String gender = request.getParameter("gender");
				String dob = request.getParameter("dob");
				String contact = request.getParameter("contact");
				String id = request.getParameter("id");
				if(IsFieldEmpty(username) || IsFieldEmpty(password) || IsFieldEmpty(firstname) || IsFieldEmpty(lastname) || IsFieldEmpty(dob) || IsFieldEmpty(gender) || IsFieldEmpty(contact) || IsFieldEmpty(id)) {
					out.println("Invalid Entires");
				}
				else {
					try {
						EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("First");
						EntityManager entitymanager = emfactory.createEntityManager();
						entitymanager.getTransaction().begin();
						Employee employee = entitymanager.find(Employee.class,Integer.valueOf(id));
						employee.setContact(contact);
						employee.setDob(dob);
						employee.setFirstname(firstname);
						employee.setLastname(lastname);
						employee.setGender(gender);
						employee.setPassword(password);
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

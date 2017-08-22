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
import Entities.Client;

@WebServlet("/EditClient")
public class EditClient extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public EditClient() {
        super();
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(false);
		if (session == null) {
		    out.print("Please Login to Continue");
		} else {
			String id = request.getParameter("id");
			String firstname = request.getParameter("firstname");
			String lastname = request.getParameter("lastname");
			String address = request.getParameter("address");
			String phonenumber = request.getParameter("phonenumber");
			if(IsFieldEmpty(firstname) || IsFieldEmpty(lastname) || IsFieldEmpty(address) || IsFieldEmpty(phonenumber) || IsFieldEmpty(id)) {
				out.println("Invalid Entires");
			}
			else {
				try {
					EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("First");
					EntityManager entitymanager = emfactory.createEntityManager();
					entitymanager.getTransaction().begin();
					Client client = entitymanager.find(Client.class,Integer.valueOf(id));
					client.setAddress(address);
					client.setFirstname(firstname);
					client.setLastname(lastname);
					client.setPhonenumber(phonenumber);
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
	private boolean IsFieldEmpty(String field){
		if(field == null || "".equals(field)) {
			return true;
		}
		return false;
	}
}
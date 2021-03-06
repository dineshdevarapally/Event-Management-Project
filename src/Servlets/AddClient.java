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

@WebServlet("/AddClient")
public class AddClient extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public AddClient() {
        super();
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(false);
		if (session == null) {
		    out.print("Please Login to Continue");
		} else {
			String firstname = request.getParameter("firstname");
			String lastname = request.getParameter("lastname");
			String phonenumber = request.getParameter("phonenumber");
			String address = request.getParameter("address");
			if(IsFieldEmpty(firstname) || IsFieldEmpty(lastname) || IsFieldEmpty(phonenumber) || IsFieldEmpty(address)) {
				out.print("Invalid Entries");
			} else {
				Client client = new Client(firstname, lastname, phonenumber, address);
				try {
					EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("First");
					EntityManager entitymanager = emfactory.createEntityManager();
					entitymanager.getTransaction().begin();
					entitymanager.persist(client);
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

// be true, be wise, be happy

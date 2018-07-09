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

@WebServlet("/DeleteClient")
public class DeleteClient extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public DeleteClient() {
        super();
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(false);
		if (session == null) {
		    out.print("Please Login to Continue");
		} else {
			String id = request.getParameter("id");
			if(IsFieldEmpty(id)) {
				out.println("Invalid Entires");
			}
			else {
				try {
					EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("First");
					EntityManager entitymanager = emfactory.createEntityManager();
					entitymanager.getTransaction().begin();
					Client client = entitymanager.find(Client.class,Integer.valueOf(id));
					entitymanager.remove(client);
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

// lies are lies

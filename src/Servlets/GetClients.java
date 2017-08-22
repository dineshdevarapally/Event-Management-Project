package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONObject;
import Entities.Client;

@WebServlet("/GetClients")
public class GetClients extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public GetClients() {
        super();
    }
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(false);
		if (session == null) {
		    out.print("Please Login to Continue");
		} else {
			JSONObject output = new JSONObject();
			try {
				EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("First");
				EntityManager entitymanager = emfactory.createEntityManager();
				Query query = entitymanager.createNamedQuery("get clients");
				List<Client> list = (List<Client>)query.getResultList( );
				int i = 0;
				for(Client c: list) {
					JSONObject obj = new JSONObject();
					obj.put("id", c.getId());
					obj.put("firstname", c.getFirstname());
					obj.put("lastname", c.getLastname());
					obj.put("phonenumber", c.getPhonenumber());
					obj.put("address", c.getAddress());
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
}
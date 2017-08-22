package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.persistence.Query;
import Entities.Employee;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public Login() {
        super();
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		PrintWriter out = response.getWriter();
		if(IsFieldEmpty(username) || IsFieldEmpty(password)) {
			out.println("Invalid Entires");
		} else {
			try {
				EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("First");
				EntityManager entitymanager = emfactory.createEntityManager();
				Query query = entitymanager.createNamedQuery("login query");
				query.setParameter("username", username);
				query.setParameter("password", password);
				@SuppressWarnings("unchecked")
				List<Employee> list = query.getResultList();
				int i = 0;
				for (Employee e : list) {
					HttpSession session=request.getSession();
					session.setAttribute("employee", e);
					i++;
				}
				if(i == 0) {
					out.print("Invalid Credentials");
				}
				else {
					out.print("success");
				}
			}
			catch (Exception e) {
				out.println("Connection Error");
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
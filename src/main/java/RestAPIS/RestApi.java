package RestAPIS;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONObject;

import Algo.Initialize;
import Algo.Person;
import Algo.Restaurant;
import Algo.Table;
import Entities.Client;
import Entities.Employee;
import Entities.Event;
import Entities.Guest;
import Entities.Output;
import Entities.Rule;

@Path("/RestApis")
public class RestApi {
	@POST
	@Path("/LoginServlet")
	@Produces(MediaType.TEXT_PLAIN)
	public Response userLogin(@FormParam("password") String password, @FormParam("username") String username,
			@Context HttpServletRequest request) {
		if (IsFieldEmpty(username) || IsFieldEmpty(password)) {
			return Response.status(200).entity("Invalid Entires").build();
		} else {
			EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("First");
			EntityManager entitymanager = emfactory.createEntityManager();
			try {
				Query query = entitymanager.createNamedQuery("login query");
				query.setParameter("username", username);
				query.setParameter("password", password);
				@SuppressWarnings("unchecked")
				List<Employee> list = query.getResultList();
				int i = 0;
				for (Employee e : list) {
					HttpSession session = request.getSession(true);
					session.setAttribute("employee", e);
					i++;
				}
				if (i == 0) {
					return Response.status(200).entity("Invalid Credentials").build();
				} else {
					return Response.status(200).entity("success").build();
				}
			} catch (Exception e) {
				return Response.status(200).entity("Connection Error").build();
			} finally {
				entitymanager.close();
				emfactory.close();
			}
		}
	}

	@POST
	@Path("/Initialize")
	@Produces(MediaType.TEXT_PLAIN)
	public Response Initialize() {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("First");
		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		Query query = entitymanager.createQuery("SELECT e" + " FROM Employee e WHERE e.role='manager'");
		@SuppressWarnings("unchecked")
		List<Employee> list = (List<Employee>) query.getResultList();
		if (list.size() == 1) {
			return Response.status(200).entity("success").build();
		} else {
			Employee employee = new Employee("manager", "manager", "manager", "manager", "1996-07-02 00:00:00",
					"987654210", "Male", "manager");
			try {
				entitymanager.persist(employee);
				entitymanager.getTransaction().commit();
				return Response.status(200).entity("success").build();
			} catch (Exception e) {
				return Response.status(200).entity("Unable to Initialize the Project").build();
			} finally {
				entitymanager.close();
				emfactory.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	@POST
	@Path("/GetProfileDetails")
	@Produces(MediaType.TEXT_PLAIN)
	public Response GetProfileDetails(@Context HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return Response.status(200).entity("Please Login to Continue").build();
		} else {
			Employee e = (Employee) session.getAttribute("employee");
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
			return Response.status(200).entity(output.toString()).build();
		}
	}

	@POST
	@Path("/AddClient")
	@Produces(MediaType.TEXT_PLAIN)
	public Response AddClient(@FormParam("firstname") String firstname, @FormParam("lastname") String lastname,
			@FormParam("phonenumber") String phonenumber, @FormParam("address") String address,
			@Context HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return Response.status(200).entity("Please Login to Continue").build();
		} else {
			if (IsFieldEmpty(firstname) || IsFieldEmpty(lastname) || IsFieldEmpty(phonenumber)
					|| IsFieldEmpty(address)) {
				return Response.status(200).entity("Invalid Entries").build();
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
					return Response.status(200).entity("success").build();
				} catch (Exception e) {
					return Response.status(200).entity("Unable to add client").build();
				}
			}
		}
	}

	@POST
	@Path("/EditClient")
	@Produces(MediaType.TEXT_PLAIN)
	public Response EditClient(@FormParam("id") String id, @FormParam("firstname") String firstname,
			@FormParam("lastname") String lastname, @FormParam("phonenumber") String phonenumber,
			@FormParam("address") String address, @Context HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return Response.status(200).entity("Please Login to Continue").build();
		} else {
			if (IsFieldEmpty(firstname) || IsFieldEmpty(lastname) || IsFieldEmpty(address) || IsFieldEmpty(phonenumber)
					|| IsFieldEmpty(id)) {
				return Response.status(200).entity("Invalid Entires").build();
			} else {
				try {
					EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("First");
					EntityManager entitymanager = emfactory.createEntityManager();
					entitymanager.getTransaction().begin();
					Client client = entitymanager.find(Client.class, Integer.valueOf(id));
					client.setAddress(address);
					client.setFirstname(firstname);
					client.setLastname(lastname);
					client.setPhonenumber(phonenumber);
					entitymanager.getTransaction().commit();
					entitymanager.close();
					emfactory.close();
					return Response.status(200).entity("success").build();
				} catch (Exception e) {
					return Response.status(200).entity("Unable to edit client").build();
				}
			}
		}
	}

	@POST
	@Path("/DeleteClient")
	@Produces(MediaType.TEXT_PLAIN)
	public Response DeleteClient(@FormParam("id") String id, @Context HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return Response.status(200).entity("Please Login to Continue").build();
		} else {
			if (IsFieldEmpty(id)) {
				return Response.status(200).entity("Invalid Entires").build();
			} else {
				try {
					EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("First");
					EntityManager entitymanager = emfactory.createEntityManager();
					entitymanager.getTransaction().begin();
					Client client = entitymanager.find(Client.class, Integer.valueOf(id));
					entitymanager.remove(client);
					entitymanager.getTransaction().commit();
					entitymanager.close();
					emfactory.close();
					return Response.status(200).entity("success").build();
				} catch (Exception e) {
					return Response.status(200).entity("Unable to delete client").build();
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@POST
	@Path("/GetClients")
	@Produces(MediaType.TEXT_PLAIN)
	public Response GetClients(@Context HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return Response.status(200).entity("Please Login to Continue").build();
		} else {
			JSONObject output = new JSONObject();
			try {
				EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("First");
				EntityManager entitymanager = emfactory.createEntityManager();
				Query query = entitymanager.createNamedQuery("get clients");
				List<Client> list = (List<Client>) query.getResultList();
				int i = 0;
				for (Client c : list) {
					JSONObject obj = new JSONObject();
					obj.put("id", c.getId());
					obj.put("firstname", c.getFirstname());
					obj.put("lastname", c.getLastname());
					obj.put("phonenumber", c.getPhonenumber());
					obj.put("address", c.getAddress());
					output.put(String.valueOf(i), obj);
					i++;
				}
				entitymanager.close();
				emfactory.close();
				return Response.status(200).entity(output.toString()).build();
			} catch (Exception e) {
				return Response.status(200).entity("Unable to get clients").build();
			}
		}
	}

	@POST
	@Path("/AddUser")
	@Produces(MediaType.TEXT_PLAIN)
	public Response AddUser(@FormParam("username") String username, @FormParam("password") String password,
			@FormParam("firstname") String firstname, @FormParam("lastname") String lastname,
			@FormParam("gender") String gender, @FormParam("dob") String dob, @FormParam("role") String role,
			@FormParam("contact") String contact, @Context HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return Response.status(200).entity("Please Login to Continue").build();
		} else {
			Employee sessionUser = (Employee) session.getAttribute("employee");
			if (!sessionUser.getRole().equals("manager")) {
				return Response.status(200).entity("No access to these requests").build();
			} else {
				if (IsFieldEmpty(username) || IsFieldEmpty(password) || IsFieldEmpty(firstname)
						|| IsFieldEmpty(lastname) || IsFieldEmpty(dob) || IsFieldEmpty(gender) || IsFieldEmpty(role)
						|| !("operator".equals(role) || "manager".equals(role)) || IsFieldEmpty(contact)) {
					return Response.status(200).entity("Invalid Entires").build();
				} else {
					Employee employee = new Employee(username, password, firstname, lastname, dob, contact, gender,
							role);
					try {
						EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("First");
						EntityManager entitymanager = emfactory.createEntityManager();
						entitymanager.getTransaction().begin();
						entitymanager.persist(employee);
						entitymanager.getTransaction().commit();
						entitymanager.close();
						emfactory.close();
						return Response.status(200).entity("success").build();
					} catch (Exception e) {
						return Response.status(200).entity("Username Already Exists").build();
					}
				}
			}
		}
	}

	@POST
	@Path("/EditUser")
	@Produces(MediaType.TEXT_PLAIN)
	public Response EditUser(@FormParam("id") String id, @FormParam("username") String username,
			@FormParam("password") String password, @FormParam("firstname") String firstname,
			@FormParam("lastname") String lastname, @FormParam("gender") String gender, @FormParam("dob") String dob,
			@FormParam("role") String role, @FormParam("contact") String contact, @Context HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return Response.status(200).entity("Please Login to Continue").build();
		} else {
			Employee sessionUser = (Employee) session.getAttribute("employee");
			if (!(sessionUser.getRole().equals("manager") || sessionUser.getUsername().equals(username))) {
				return Response.status(200).entity("No access to these requests").build();
			} else {
				if (IsFieldEmpty(username) || IsFieldEmpty(password) || IsFieldEmpty(firstname)
						|| IsFieldEmpty(lastname) || IsFieldEmpty(dob) || IsFieldEmpty(gender) || IsFieldEmpty(contact)
						|| IsFieldEmpty(id)) {
					return Response.status(200).entity("Invalid Entires").build();
				} else {
					try {
						EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("First");
						EntityManager entitymanager = emfactory.createEntityManager();
						entitymanager.getTransaction().begin();
						Employee employee = entitymanager.find(Employee.class, Integer.valueOf(id));
						employee.setContact(contact);
						employee.setDob(dob);
						employee.setFirstname(firstname);
						employee.setLastname(lastname);
						employee.setGender(gender);
						employee.setPassword(password);
						entitymanager.getTransaction().commit();
						entitymanager.close();
						emfactory.close();
						return Response.status(200).entity("success").build();
					} catch (Exception e) {
						return Response.status(200).entity("Unable to edit user").build();
					}
				}
			}
		}
	}

	@POST
	@Path("/DeleteUser")
	@Produces(MediaType.TEXT_PLAIN)
	public Response DeleteUser(@FormParam("id") String id, @Context HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return Response.status(200).entity("Please Login to Continue").build();
		} else {
			Employee sessionUser = (Employee) session.getAttribute("employee");
			if (!sessionUser.getRole().equals("manager")) {
				return Response.status(200).entity("No access to these requests").build();
			} else {
				if (IsFieldEmpty(id)) {
					return Response.status(200).entity("Invalid Entires").build();
				} else {
					try {
						EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("First");
						EntityManager entitymanager = emfactory.createEntityManager();
						entitymanager.getTransaction().begin();
						Employee employee = entitymanager.find(Employee.class, Integer.valueOf(id));
						entitymanager.remove(employee);
						entitymanager.getTransaction().commit();
						entitymanager.close();
						emfactory.close();
						return Response.status(200).entity("success").build();
					} catch (Exception e) {
						return Response.status(200).entity("Unable to delete user").build();
					}
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@POST
	@Path("/GetEmployees")
	@Produces(MediaType.TEXT_PLAIN)
	public Response GetEmployees(@Context HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return Response.status(200).entity("Please Login to Continue").build();
		} else {
			Employee sessionUser = (Employee) session.getAttribute("employee");
			if (!sessionUser.getRole().equals("manager")) {
				return Response.status(200).entity("No access to these requests").build();
			} else {
				JSONObject output = new JSONObject();
				try {
					EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("First");
					EntityManager entitymanager = emfactory.createEntityManager();
					Query query = entitymanager.createNamedQuery("get employees");
					List<Employee> list = (List<Employee>) query.getResultList();
					int i = 0;
					for (Employee e : list) {
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
						output.put(String.valueOf(i), obj);
						i++;
					}
					entitymanager.close();
					emfactory.close();
					return Response.status(200).entity(output.toString()).build();
				} catch (Exception e) {
					return Response.status(200).entity("Unable to get employees").build();
				}
			}
		}
	}

	@POST
	@Path("/Logout")
	@Produces(MediaType.TEXT_PLAIN)
	public Response Logout(@Context HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return Response.status(200).entity("Please Login to Continue").build();
		} else {
			session.invalidate();
			return Response.status(200).entity("You are successfully logged out").build();
		}
	}

	@POST
	@Path("/AddEvent")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.TEXT_PLAIN)
	public Response AddEvent(@FormDataParam("eventname") String eventName,
			@FormDataParam("eventDateTime") String eventDateTime, @FormDataParam("eventVenue") String eventVenue,
			@FormDataParam("seatsInTable") Integer seatsInTable, @FormDataParam("clientname") Integer clientId,
			@FormDataParam("importGuestList") InputStream uploadedInputStream,
			@FormDataParam("importGuestList") FormDataContentDisposition fileDetail,
			@FormDataParam("emptySeatsInTable") Integer emptySeatsInTable, @FormDataParam("nGen") Integer nGen, @Context HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return Response.status(200).entity("Please Login to Continue").build();
		} else {
			if (IsFieldEmpty(eventName) || IsFieldEmpty(eventDateTime) || IsFieldEmpty(eventVenue) || clientId == null
					|| emptySeatsInTable == null) {
				return Response.status(200).entity("Invalid Entires").build();
			} else {
				Event event = new Event();
				EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("First");
				EntityManager entitymanager = emfactory.createEntityManager();
				entitymanager.getTransaction().begin();
				Client client = entitymanager.find(Client.class, Integer.valueOf(clientId));
				entitymanager.getTransaction().commit();
				event.setClient(client);
				event.setEmpytSeats(emptySeatsInTable);
				event.setEventName(eventName);
				event.setnSeats(seatsInTable);
				event.setVenue(eventVenue);
				event.setnGen(nGen);
				DateFormat formatter = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
				Date eventDateTimeParsed = null;
				try {
					eventDateTimeParsed = formatter.parse(eventDateTime);
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				event.setDateTime(eventDateTimeParsed);
				try {
					entitymanager.getTransaction().begin();
					String uploadedFileLocation = "C:/ThunderMedia/" + fileDetail.getFileName();
					File file = new File(uploadedFileLocation);
					int num = 0;
					String[] fileNameSplit = getFileNameSplit(fileDetail.getFileName());
					while (file.exists()) {
						uploadedFileLocation = "C:/ThunderMedia/" + fileNameSplit[0] + (num++) + fileNameSplit[1];
						file = new File(uploadedFileLocation);
					}
					saveFileLocal(uploadedInputStream, uploadedFileLocation);
					event.setFileLocation(uploadedFileLocation);
					String line = "";
					@SuppressWarnings("resource")
					BufferedReader br = new BufferedReader(new FileReader(uploadedFileLocation));
					int i = 0;
					String[] headers = null;
					try {
						while ((line = br.readLine()) != null) {
							if (i == 0) {
								headers = line.split(",");
								if (!(headers[0].equals("Guest #") && headers[1].equals("First Name")
										&& headers[2].equals("Last Name") && headers[3].equals("Same Table"))) {
									entitymanager.close();
									emfactory.close();
									return Response.status(200).entity("Invalid File Input").build();
								}
								i++;
							} else {
								entitymanager.persist(event);
								String[] data = line.split(",");
								Guest guest = new Guest();
								guest.setEvent(event);
								guest.setEventGuestNumber(Integer.valueOf(data[0]));
								guest.setFirstName(data[1]);
								guest.setLastName(data[2]);
								entitymanager.persist(guest);
							}
						}
					} catch (Exception e) {
						entitymanager.close();
						emfactory.close();
						System.out.println(e);
						return Response.status(200).entity("Invalid File Input").build();
					}
					entitymanager.getTransaction().commit();
					entitymanager.getTransaction().begin();
					br.close();
					br = new BufferedReader(new FileReader(uploadedFileLocation));
					i = 0;
					headers = null;
					try {
						while ((line = br.readLine()) != null) {
							if (i == 0) {
								headers = line.split(",");
								if (!(headers[0].equals("Guest #") && headers[1].equals("First Name")
										&& headers[2].equals("Last Name") && headers[3].equals("Same Table"))) {
									entitymanager.close();
									emfactory.close();
									return Response.status(200).entity("Invalid File Input").build();
								}
								i++;
							} else {
								String[] data = line.split(",");
								for (int j = 3; j < data.length; j++) {
									if (headers[j].equals("Same Table") && !isBlank(data[j])) {
										System.out.println(data);
										int score = 10;
										Rule rule = new Rule();
										rule.setScore(score);
										rule.getMainGuest();
										Query query = entitymanager.createNamedQuery("get guest with order");
										query.setParameter("eventId", event.getEventId());
										query.setParameter("eventGuestNumber", Integer.valueOf(data[0]));
										Guest mainGuest = (Guest) query.getResultList().get(0);
										query = entitymanager.createNamedQuery("get guest with order");
										query.setParameter("eventId", event.getEventId());
										query.setParameter("eventGuestNumber", Integer.valueOf(data[j]));
										Guest subGuest = (Guest) query.getResultList().get(0);
										rule.setMainGuest(mainGuest);
										rule.setSubGuest(subGuest);
										rule.setEvent(event);
										entitymanager.persist(rule);
									} else if (headers[j].equals("Not Same Table") && !isBlank(data[j])) {
										System.out.println(data);
										int score = -10;
										Rule rule = new Rule();
										rule.setScore(score);
										rule.getMainGuest();
										Query query = entitymanager.createNamedQuery("get guest with order");
										query.setParameter("eventId", event.getEventId());
										query.setParameter("eventGuestNumber", Integer.valueOf(data[0]));
										Guest mainGuest = (Guest) query.getResultList().get(0);
										query = entitymanager.createNamedQuery("get guest with order");
										query.setParameter("eventId", event.getEventId());
										query.setParameter("eventGuestNumber", Integer.valueOf(data[j]));
										Guest subGuest = (Guest) query.getResultList().get(0);
										rule.setMainGuest(mainGuest);
										rule.setSubGuest(subGuest);
										rule.setEvent(event);
										entitymanager.persist(rule);
									}
								}
								Guest guest = new Guest();
								guest.setEvent(event);
								guest.setEventGuestNumber(Integer.valueOf(data[0]));
								guest.setFirstName(data[1]);
								guest.setLastName(data[2]);
								entitymanager.persist(guest);
							}
						}
						br.close();
					} catch (Exception e) {
						System.out.println(e);
						entitymanager.close();
						emfactory.close();
						return Response.status(200).entity("Invalid File Input").build();
					}
					entitymanager.getTransaction().commit();
					Initialize in = new Initialize(event, event.getnGen());
					Restaurant algoOutput = in.getStrongRestaurant();
					entitymanager.getTransaction().begin();
					int tableIt = 1;
					for (Table table : algoOutput.getTables()) {
						for(Person p : table.getPersons()) {
							Output output = new Output();
							output.setEvent(event);
							Query query = entitymanager.createNamedQuery("get guest with order");
							query.setParameter("eventId", event.getEventId());
							query.setParameter("eventGuestNumber", Integer.valueOf(p.getIndex()));
							Guest guest = (Guest) query.getResultList().get(0);
							output.setGuest(guest);
							output.setTableNumber(tableIt);
							entitymanager.persist(output);
						}
						tableIt++;
					}
					entitymanager.getTransaction().commit();
					entitymanager.getTransaction().begin();	
					event.setScore(algoOutput.getScore());
					entitymanager.persist(event);
					entitymanager.getTransaction().commit();
					entitymanager.close();
					emfactory.close();
					br.close();
					return Response.status(200).entity("success").build();
				} catch (Exception e) {
					System.out.println(e);
					return Response.status(200).entity("Unable to add Event").build();
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@POST
	@Path("/GetEvents")
	@Produces(MediaType.TEXT_PLAIN)
	public Response GetEvents(@Context HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return Response.status(200).entity("Please Login to Continue").build();
		} else {
			try {
				EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("First");
				EntityManager entitymanager = emfactory.createEntityManager();
				entitymanager.getTransaction().begin();
				Query query = entitymanager.createNamedQuery("get all events");
				List<Event> list = (List<Event>) query.getResultList();
				int i = 0;
				JSONObject output = new JSONObject();
				Format formatter = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
				for (Event event : list) {
					JSONObject obj = new JSONObject();
					obj.put("eventId", String.valueOf(event.getEventId()));
					obj.put("clientName", String.valueOf(event.getClient().getFirstname()));
					obj.put("eventName", event.getEventName());
					obj.put("seatsInTable", String.valueOf(event.getnSeats()));
					obj.put("emptySeatsInTable", String.valueOf(event.getEmpytSeats()));
					obj.put("venue", event.getVenue());
					obj.put("fileLocation", event.getFileLocation());
					obj.put("nGen", String.valueOf(event.getnGen()));
					obj.put("score", String.valueOf(event.getScore()));
					if (event.getDateTime() == null) {
						obj.put("eventDateTime", "");
					} else {
						String dateTimeString = formatter.format(event.getDateTime());
						obj.put("eventDateTime", dateTimeString);
					}
					output.put(String.valueOf(i), obj);
					i++;
				}
				entitymanager.close();
				emfactory.close();
				return Response.status(200).entity(output.toString()).build();
			} catch (Exception e) {
				return Response.status(200).entity("Unable to fetch Event Details").build();
			}
		}
	}

	@POST
	@Path("/DeleteEvent")
	@Produces(MediaType.TEXT_PLAIN)
	public Response DeleteEvent(@FormParam("eventId") Integer eventId, @Context HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return Response.status(200).entity("Please Login to Continue").build();
		} else {
			Employee sessionUser = (Employee) session.getAttribute("employee");
			if (!sessionUser.getRole().equals("manager")) {
				return Response.status(200).entity("No access to these requests").build();
			} else {
				try {
					EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("First");
					EntityManager entitymanager = emfactory.createEntityManager();
					entitymanager.getTransaction().begin();
					Event event = entitymanager.find(Event.class, Integer.valueOf(eventId));
					entitymanager.remove(event);
					entitymanager.getTransaction().commit();
					entitymanager.close();
					emfactory.close();
					return Response.status(200).entity("success").build();
				} catch (Exception e) {
					return Response.status(200).entity("Unable to Delete Event").build();
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/GetOutput")
	@Produces(MediaType.TEXT_PLAIN)
	public Response GetOutput(@FormParam("eventId") Integer eventId, @Context HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return Response.status(200).entity("Please Login to Continue").build();
		} else {
			try {
				EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("First");
				EntityManager entitymanager = emfactory.createEntityManager();
				entitymanager.getTransaction().begin();
				Query query = entitymanager.createNamedQuery("get output of event");
				query.setParameter("eventId", eventId);
				List<Output> list = (List<Output>) query.getResultList();
				int i = 0;
				JSONObject output = new JSONObject();
				for (Output result : list) {
					JSONObject obj = new JSONObject();
					obj.put("tableNumber", String.valueOf(result.getTableNumber()));
					obj.put("personName", result.getGuest().getFirstName());
					output.put(String.valueOf(i), obj);
					i++;
				}
				entitymanager.close();
				emfactory.close();
				return Response.status(200).entity(output.toString()).build();
			} catch (Exception e) {
				return Response.status(200).entity("Unable to Get Output Data").build();
			}
		}
	}

	private String[] getFileNameSplit(String fileName) {
		String[] split = new String[2];
		int pos = fileName.lastIndexOf(".");
		if (pos > 0) {
			split[0] = fileName.substring(0, pos);
			split[0] = fileName.substring(pos, fileName.length());
			return split;
		}
		return null;
	}

	private boolean IsFieldEmpty(String field) {
		if (field == null || "".equals(field)) {
			return true;
		}
		return false;
	}

	private void saveFileLocal(InputStream uploadedInputStream, String uploadedFileLocation) {
		try {
			OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
			int read = 0;
			byte[] bytes = new byte[1024];
			out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {

				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean isBlank(String s) {
		return (s == null) || (s.trim().length() == 0);
	}
}
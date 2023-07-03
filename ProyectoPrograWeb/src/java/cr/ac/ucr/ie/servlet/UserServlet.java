package cr.ac.ucr.ie.servlet;

import cr.ac.cr.ie.domain.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

@WebServlet("/users")
public class UserServlet extends HttpServlet {

    private List<User> users = new ArrayList<>();
    private File file;

    @Override
    public void init() throws ServletException {
        super.init();
        String filePath = getServletContext().getRealPath("/users.xml");
        System.err.println(filePath);
        file = new File(filePath);
        loadUsersFromXmlFile();
    }

    private void loadUsersFromXmlFile() {
        try {
            if (file.exists()) {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.parse(file);

                NodeList nodeList = document.getElementsByTagName("user");
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;
                        int id = Integer.parseInt(element.getAttribute("id"));
                        String name = element.getElementsByTagName("name").item(0).getTextContent();
                        String email = element.getElementsByTagName("email").item(0).getTextContent();
                        User user = new User(id, name, email);
                        users.add(user);
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    private void saveUsersToXmlFile() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();

            Element rootElement = document.createElement("users");
            document.appendChild(rootElement);

            for (User user : users) {
                Element userElement = document.createElement("user");
                userElement.setAttribute("id", String.valueOf(user.getId()));

                Element nameElement = document.createElement("name");
                nameElement.setTextContent(user.getName());
                userElement.appendChild(nameElement);

                Element emailElement = document.createElement("email");
                emailElement.setTextContent(user.getEmail());
                userElement.appendChild(emailElement);

                rootElement.appendChild(userElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null && action.equals("edit")) {
            int id = Integer.parseInt(request.getParameter("id"));
            User userToEdit = users.stream()
                    .filter(user -> user.getId() == id)
                    .findFirst()
                    .orElse(null);
            if (userToEdit != null) {
                request.setAttribute("user", userToEdit);
                request.getRequestDispatcher("edit-user.jsp").forward(request, response);
            } else {
                response.sendRedirect("users");
            }
        } else {
            request.setAttribute("users", users);
            request.getRequestDispatcher("user-list.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String method = request.getParameter("_method");
        if ("DELETE".equalsIgnoreCase(method)) {
            int id = Integer.parseInt(request.getParameter("id"));
            users.removeIf(user -> user.getId() == id);
            saveUsersToXmlFile();
            response.sendRedirect("users");
        } else if ("UPDATE".equalsIgnoreCase(method)) {
            int id = Integer.parseInt(request.getParameter("id"));
            User userToUpdate = users.stream()
                    .filter(user -> user.getId() == id)
                    .findFirst()
                    .orElse(null);
            if (userToUpdate != null) {
                String name = request.getParameter("name");
                String email = request.getParameter("email");
                userToUpdate.setName(name);
                userToUpdate.setEmail(email);
                saveUsersToXmlFile();
            }
            response.sendRedirect("users");
        } else {
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            User user = new User(users.size() + 1, name, email);
            users.add(user);
            saveUsersToXmlFile();
            response.sendRedirect("users");
        }
    }
    
}

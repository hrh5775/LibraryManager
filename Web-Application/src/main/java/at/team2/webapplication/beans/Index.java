package at.team2.webapplication.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.annotation.WebServlet;
import java.io.Serializable;

@WebServlet(urlPatterns = {"", "/index.html"})
@ManagedBean
@SessionScoped
public class Index implements Serializable {
}

package backend;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet({"/content"})
public class Content extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String color = System.getenv("COLOR");
        if (color == null || color.isEmpty()) {
            color = "BLUE";
        }
        response.setContentType("text/plain;charset=UTF-8");
        response.getWriter().write(color);
        response.getWriter().flush();
    }
}

package pl.coderslab.users;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import pl.coderslab.entity.UserDao;

import java.io.IOException;

@WebServlet("/user/show")
public class UserShow extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userIdParam = request.getParameter("id");
        if (userIdParam != null) {
            int userId = Integer.parseInt(userIdParam);
            UserDao userDao = new UserDao();

            if (userDao.read(userId) != null) {
                request.setAttribute("user", userDao.read(userId));

                getServletContext().getRequestDispatcher("/users/SingleUser.jsp").forward(request, response);
            } else {
                response.getWriter().append("Użytkownik o podanym ID nie istnieje.");
            }
        } else {
            response.getWriter().append("Brak parametru 'id' w żądaniu");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

package pl.coderslab.users;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import pl.coderslab.entity.User;
import pl.coderslab.entity.UserDao;

import java.io.IOException;

@WebServlet("/user/edit")
public class UserEdit extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userIdParam = request.getParameter("id");
        if (userIdParam != null) {
            try {
                int userId = Integer.parseInt(userIdParam);
                UserDao userDao = new UserDao();
                User user = userDao.read(userId);
                if (user != null) {
                    request.setAttribute("user", user);
                    getServletContext().getRequestDispatcher("/users/editUser.jsp").forward(request, response);
                    return;
                }
            } catch (NumberFormatException e) {
                response.getWriter().append("Nieprawidłowy identyfikator użytkownika.");

            }
        }
        response.getWriter().append("Nieprawidłowy identyfikator użytkownika.");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nameParam = request.getParameter("userName");
        String emailParam = request.getParameter("email");
        String passwordParam = request.getParameter("password");
        String userIdParam = request.getParameter("userId");

        if (nameParam != null && emailParam != null && passwordParam != null && userIdParam != null) {
            try {
                int userId = Integer.parseInt(userIdParam);

                UserDao userDao = new UserDao();
                User user = userDao.read(userId);

                if (user != null) {
                    user.setUserName(nameParam);
                    user.setEmail(emailParam);
                    user.setPassword(passwordParam);

                    userDao.update(user);

                    response.sendRedirect(request.getContextPath() + "/user/list");
                } else {
                    response.getWriter().append("Użytkownik o podanym identyfikatorze nie istnieje.");
                }
            } catch (NumberFormatException e) {
                response.getWriter().append("Nieprawidłowy identyfikator użytkownika.");
            }
        } else {
            response.getWriter().append("Wystąpił błąd podczas zmiany danych użytkownika.");
        }
    }
}

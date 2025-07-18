package pl.coderslab.users;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import pl.coderslab.entity.User;
import pl.coderslab.entity.UserDao;

import java.io.IOException;

@WebServlet("/user/add")
public class UserAdd extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/users/createUser.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("userName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if(userName != null && email != null && password != null){
            UserDao userDao = new UserDao();
            User user = new User(userName,email,password);
            User createdUser = userDao.create(user);
            if (createdUser != null) {
                response.sendRedirect(request.getContextPath() + "/user/list");
                return;
            }

        }else {
            response.getWriter().append("Wystąpił błąd podczas tworzenia uzytkownika.");
        }
    }
}

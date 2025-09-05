package org.example.student_management.app.servlet;

import org.example.student_management.app.dao.IUserDAO;
import org.example.student_management.app.dao.UserDAO;
import org.example.student_management.app.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "registerServlet", value = "/register")
public class RegisterServlet extends HttpServlet {
    private IUserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (userDAO.userExists(username)) {
            req.setAttribute("error", "Username đã tồn tại, vui lòng chọn tên khác!");
            req.getRequestDispatcher("/student_management/register.jsp").forward(req, resp);
            return;
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);

        userDAO.registerUser(newUser);

        req.setAttribute("success", "Đăng ký thành công !");
        req.getRequestDispatcher("/student_management/login.jsp").forward(req, resp);
    }
}

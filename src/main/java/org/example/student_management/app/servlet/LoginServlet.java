package org.example.student_management.app.servlet;

import org.example.student_management.app.dao.IUserDAO;
import org.example.student_management.app.dao.UserDAO;
import org.example.student_management.app.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "loginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    private IUserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userAccount = req.getParameter("userAccount");
        String password = req.getParameter("password");

        if (!userDAO.userExists(userAccount)) {
            req.setAttribute("error", "Tài khoản chưa đăng ký!");
            req.getRequestDispatcher("/student_management/login.jsp").forward(req, resp);
            return;
        }

        try {
            User user = userDAO.login(userAccount, password);

            if (user != null) {
                HttpSession session = req.getSession();
                session.setAttribute("user", user);

                if ("admin".equals(user.getRole())) {
                    resp.sendRedirect("/student_management/admin.jsp");
                } else {
                    resp.sendRedirect("/student_management/home.jsp");
                }
            } else {
                req.setAttribute("error", "Invalid username or password");
                req.getRequestDispatcher("/student_management/login.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}

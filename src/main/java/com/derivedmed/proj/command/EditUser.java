package com.derivedmed.proj.command;

import com.derivedmed.proj.factory.ServiceFactory;
import com.derivedmed.proj.model.Role;
import com.derivedmed.proj.model.User;
import com.derivedmed.proj.services.UserService;
import com.derivedmed.proj.util.FieldChecker;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditUser implements Action {

    private final UserService userService = ServiceFactory.getUserService();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        User user = (User) req.getSession().getAttribute("user");
        if (user.getRole()!=Role.ADMINISTRATOR){
            return "pages/403.jsp";
        }
        int userId = Integer.parseInt(req.getParameter("userId"));
        if ("GET".equals(req.getMethod())){
            req.setAttribute("userId",userId);
            return "pages/editUser.jsp";
        }
        User userToEdit = userService.getUserByID(userId);
        String email = req.getParameter("userEmail");
        String login = req.getParameter("userLogin");
        String password = req.getParameter("userPassword");
        String rating = req.getParameter("userRating");
        String role = req.getParameter("role");

        if (StringUtils.isNoneBlank(email)&& FieldChecker.checkEmail(email)){
            userToEdit.setEmail(email);
        }
        if (StringUtils.isNoneBlank(login)&&FieldChecker.checkField(login)){
            userToEdit.setLogin(login);
        }
        if (StringUtils.isNoneBlank(password)&&FieldChecker.checkField(password)){
            userToEdit.setPassword(password);
        }
        if (StringUtils.isNoneBlank(rating)&&FieldChecker.checkInt(rating)){
            userToEdit.setRating(Integer.parseInt(rating));
        }
        if (StringUtils.isNoneBlank(role)){
            userToEdit.setRole(Role.valueOf(role));
        }
        userService.updateUser(userToEdit);
        return new EditUsers().execute(req,resp);
    }
}

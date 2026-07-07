package controller;

import dao.UserDAO;
import model.User;
import util.PasswordUtil;
import util.SessionManager;

public class AuthController {

    private UserDAO userDAO = new UserDAO();
    private SessionManager session = SessionManager.getInstance();

    public AuthController() {
    }

    public int login(String username, String password) {

        if (username == null || password == null ||
            username.trim().isEmpty() || password.trim().isEmpty()) {
            return -1;
        }

        User user = userDAO.searchByUsername(username.trim());

        if (user == null) {
            return -1;
        }

        if (PasswordUtil.verify(password, user.getPassword())) {
            session.login(user);
            return 1;
        }

        return 0;
    }

    public void logout() {
        session.logout();
    }

    public User getCurrentUser() {
        return session.getCurrentUser();
    }

    public boolean isAdmin() {
        return session.isAdmin();
    }

    public int changePassword(String oldPassword, String newPassword) {

        User current = session.getCurrentUser();

        if (current == null) {
            return -1;
        }

        if (!PasswordUtil.verify(oldPassword, current.getPassword())) {
            return 0;
        }

        String newHashed = PasswordUtil.hash(newPassword);
        int result = userDAO.updatePassword(current.getId(), newHashed);

        if (result == 1) {
            current.setPassword(newHashed);
        }

        return result;
    }
}
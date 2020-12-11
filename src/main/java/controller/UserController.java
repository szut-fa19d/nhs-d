package controller;

import datastorage.DAOFactory;
import datastorage.GroupDAO;
import model.User;

public class UserController {
    private static UserController instance;
    private User user;
    private String group;

    public UserController(){
        user = new User();
    }

    public static synchronized UserController getInstance() {
        if ( instance == null ) {
            UserController.instance = new UserController();
        }
        return UserController.instance;
    }

    public void logout() {
        user = null;
        group = null;
    }

    public boolean isAdmin() {
        return group.equals("ADMIN");
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        GroupDAO groupDAO = DAOFactory.getInstance().createGroupDAO();
        group = groupDAO.getInstanceById(user.getGroup()).getGroupName();
    }
}

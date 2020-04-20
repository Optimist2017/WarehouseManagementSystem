package model;

import business.DBManager;
import controller.loginController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name ="log")

public class Log {
    @Id
    @GeneratedValue

    private Long id;


    private Timestamp time = Timestamp.from(Instant.now());
    private String action ;
    private Employees user ;

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Employees getUser() {
        return user;
    }

    public void setUser(Employees user) {
        this.user = user;
    }

    public static void log(String action) {
        Log log = new Log();
        log.setUser(loginController.getUser());
        log.setAction(action);
        DBManager.save(log);
    }
}

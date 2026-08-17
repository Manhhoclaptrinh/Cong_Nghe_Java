package vn.edu.eaut.lab6.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import vn.edu.eaut.lab6.model.Student;
import vn.edu.eaut.lab6.store.StudentStore;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        if (StudentStore.count() == 0) {
            StudentStore.add(new Student(
                    "SV001", "Nguyen Van An", "DCCNTT12", "an@example.com"
            ));
            StudentStore.add(new Student(
                    "SV002", "Tran Thi Binh", "DCCNTT12", "binh@example.com"
            ));
            StudentStore.add(new Student(
                    "SV003", "Le Van Cuong", "DCCNTT13", "cuong@example.com"
            ));
            StudentStore.add(new Student(
                    "SV004", "Pham Thi Dung", "DCCNTT13", "dung@example.com"
            ));
            StudentStore.add(new Student(
                    "SV005", "Hoang Van Em", "DCCNTT14", "em@example.com"
            ));
        }

        sce.getServletContext().setAttribute(
                "studentCount",
                StudentStore.count()
        );

        System.out.println(
                "Ung dung Lab 6 da khoi dong. So sinh vien: "
                        + StudentStore.count()
        );
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println(
                "Ung dung Lab 6 da dung. So sinh vien: "
                        + StudentStore.count()
        );
    }
}

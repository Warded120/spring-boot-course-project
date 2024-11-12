package com.ivan.course;

import com.ivan.course.constants.CourseState;
import com.ivan.course.dto.CourseDto;
import com.ivan.course.dto.usersDto.StudentDto;
import com.ivan.course.dto.usersDto.SuperUserDto;
import com.ivan.course.dto.usersDto.TeacherDto;
import com.ivan.course.entity.Course;
import com.ivan.course.entity.student.Student;
import com.ivan.course.entity.superuser.SuperUser;
import com.ivan.course.entity.teacher.Teacher;
import com.ivan.course.service.course.CourseService;
import com.ivan.course.service.student.StudentService;
import com.ivan.course.service.superUser.SuperUserService;
import com.ivan.course.service.teacher.TeacherService;
import com.ivan.course.service.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class SpringBootCourseProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootCourseProjectApplication.class, args);
    }

    @Bean
    CommandLineRunner populateDatabase(UserService userService,
                                       SuperUserService superUserService,
                                       StudentService studentService,
                                       TeacherService teacherService,
                                       CourseService courseService) {
        return args -> {
            if(userService.findAll().isEmpty()) {
                SuperUser superUser1 = new SuperUser(new SuperUserDto("admin@gmail.com", "Test123", "адмін", "адмін", "адмін", LocalDate.now().minusYears(20)));
                SuperUser superUser2 = new SuperUser(new SuperUserDto("operator@gmail.com", "Test123", "оператор", "оператор", "оператор", LocalDate.now().minusYears(24)));
                superUserService.saveAll(List.of(superUser1, superUser2));

                Student student1 = new Student(new StudentDto("ivan.koval@gmail.com", "Test123", "Іван", "Коваль", 40, LocalDate.now().minusYears(22)));
                Student student2 = new Student(new StudentDto("john.doe@gmail.com", "Test123", "Джон", "До", 15, LocalDate.now().minusYears(24)));
                Student student3 = new Student(new StudentDto("alex.brown@gmail.com", "Test123", "Олексій", "Браун", 100, LocalDate.now().minusYears(21)));
                Student student4 = new Student(new StudentDto("mary.poppins@gmail.com", "Test123", "Марія", "Поппінс", 34, LocalDate.now().minusYears(23)));
                Student student5 = new Student(new StudentDto("susan.white@gmail.com", "Test123", "Сюзанна", "Вайт", 0, LocalDate.now().minusYears(22)));
                Student student6 = new Student(new StudentDto("jane.smith@gmail.com", "Test123", "Джейн", "Сміт", 200, LocalDate.now().minusYears(25)));
                Student student7 = new Student(new StudentDto("abby.parker@gmail.com", "Test123", "Ебігейл", "Паркер", 68, LocalDate.now().minusYears(20)));
                Student student8 = new Student(new StudentDto("michael.clark@gmail.com", "Test123", "Михайло", "Кларк", 33, LocalDate.now().minusYears(23)));
                Student student9 = new Student(new StudentDto("chris.evans@gmail.com", "Test123", "Кріс", "Еванс", 0, LocalDate.now().minusYears(21)));
                Student student10 = new Student(new StudentDto("sarah.lee@gmail.com", "Test123", "Сара", "Лі", 125, LocalDate.now().minusYears(22)));
                Student student11 = new Student(new StudentDto("emily.baker@gmail.com", "Test123", "Емілі", "Бейкер", 44, LocalDate.now().minusYears(24)));
                Student student12 = new Student(new StudentDto("daniel.wright@gmail.com", "Test123", "Даніель", "Райт", 120, LocalDate.now().minusYears(22)));
                Student student13 = new Student(new StudentDto("laura.green@gmail.com", "Test123", "Леся", "Грін", 25, LocalDate.now().minusYears(20)));
                Student student14 = new Student(new StudentDto("jack.reed@gmail.com", "Test123", "Яків", "Рід", 81, LocalDate.now().minusYears(23)));
                Student student15 = new Student(new StudentDto("anna.young@gmail.com", "Test123", "Анна", "Янг", 81, LocalDate.now().minusYears(21)));
                Student student16 = new Student(new StudentDto("lisa.jones@gmail.com", "Test123", "Ліза", "Джонс", 49, LocalDate.now().minusYears(22)));
                Student student17 = new Student(new StudentDto("kevin.harris@gmail.com", "Test123", "Кевін", "Гарріс", 90, LocalDate.now().minusYears(23)));
                Student student18 = new Student(new StudentDto("nina.scott@gmail.com", "Test123", "Ніна", "Скотт", 73, LocalDate.now().minusYears(21)));
                Student student19 = new Student(new StudentDto("oscar.adams@gmail.com", "Test123", "Оскар", "Адамс", 0, LocalDate.now().minusYears(24)));
                Student student20 = new Student(new StudentDto("victoria.wood@gmail.com", "Test123", "Вікторія", "Вуд", 430, LocalDate.now().minusYears(20)));
                Student student21 = new Student(new StudentDto("jason.carter@gmail.com", "Test123", "Джейсон", "Картер", 32, LocalDate.now().minusYears(22)));
                Student student22 = new Student(new StudentDto("lily.anderson@gmail.com", "Test123", "Лілі", "Андерсон", 97, LocalDate.now().minusYears(23)));
                studentService.saveAll(List.of(
                        student1, student2, student3, student4, student5, student6, student7, student8, student9, student10,
                        student11, student12, student13, student14, student15, student16, student17, student18, student19,
                        student20, student21, student22
                ));

                Teacher teacher1 = new Teacher(new TeacherDto("alex.jones@gmail.com", "Test123", "Олексій", "Джонс", LocalDate.now().minusYears(45)));
                Teacher teacher2 = new Teacher(new TeacherDto("laura.king@gmail.com", "Test123", "Леся", "Кінг", LocalDate.now().minusYears(38)));
                Teacher teacher3 = new Teacher(new TeacherDto("peter.miller@gmail.com", "Test123", "Петро", "Міллер", LocalDate.now().minusYears(40)));
                teacherService.saveAll(List.of(teacher1, teacher2, teacher3));

                Course course1 = new Course(new CourseDto("Англійська мова для початківців", "Базовий курс для початківців", "Англійська", "A1", 30, CourseState.CREATED, teacher1.getTeacherData()));
                Course course2 = new Course(new CourseDto("Французька для подорожей", "Основи французької для туристів", "Французька", "A1", 35, CourseState.CREATED, teacher1.getTeacherData()));
                Course course3 = new Course(new CourseDto("Японська мова для початківців", "Основи японської мови та культури", "Японська", "A1", 40, CourseState.CREATED, teacher1.getTeacherData()));
                Course course4 = new Course(new CourseDto("Німецька для роботи", "Професійна німецька для бізнесу", "Німецька", "B2", 50, CourseState.CREATED, teacher2.getTeacherData()));
                Course course5 = new Course(new CourseDto("Іспанська для початківців", "Введення в іспанську мову", "Іспанська", "A1", 28, CourseState.CREATED, teacher2.getTeacherData()));
                Course course6 = new Course(new CourseDto("Корейська для середнього рівня", "Корейська мова для подорожей та роботи", "Корейська", "B1", 45, CourseState.CREATED, teacher2.getTeacherData()));
                Course course7 = new Course(new CourseDto("Італійська для середнього рівня", "Італійська для подорожей та спілкування", "Італійська", "B1", 45, CourseState.CREATED, teacher3.getTeacherData()));
                Course course8 = new Course(new CourseDto("Португальська для подорожей", "Основи португальської для туристів", "Португальська", "A2", 30, CourseState.CREATED, teacher3.getTeacherData()));
                Course course9 = new Course(new CourseDto("Китайська для бізнесу", "Ділова китайська для професіоналів", "Китайська", "B2", 60, CourseState.CREATED, teacher3.getTeacherData()));

                course1.smartEnroll(student1);  
                course1.smartEnroll(student2);
                course1.smartEnroll(student3);  
                course1.smartEnroll(student4);  
                course1.smartEnroll(student5);
                course1.smartEnroll(student6);  
                course1.smartEnroll(student7);
                course1.smartEnroll(student8);  
                course1.smartEnroll(student9);
                course1.smartEnroll(student10); 
                course1.smartEnroll(student11);
                course1.smartEnroll(student12); 
                course1.smartEnroll(student13);
                course1.smartEnroll(student14); 
                course1.smartEnroll(student15);
                course1.smartEnroll(student16); 
                course1.smartEnroll(student17);
                course1.smartEnroll(student18); 
                course1.smartEnroll(student19);
                course1.smartEnroll(student20); 
                courseService.save(course1);

                course2.smartEnroll(student21); 
                course2.smartEnroll(student22);
                courseService.save(course2);

                course3.smartEnroll(student1);  
                course3.smartEnroll(student2);
                course3.smartEnroll(student3);  
                course3.smartEnroll(student4);
                courseService.save(course3);

                course4.smartEnroll(student5);  
                course4.smartEnroll(student6);
                course4.smartEnroll(student7);  
                course4.smartEnroll(student8);
                course4.smartEnroll(student9);  
                courseService.save(course4);

                course5.smartEnroll(student10); 
                course5.smartEnroll(student11);
                course5.smartEnroll(student12); 
                course5.smartEnroll(student13);
                course5.smartEnroll(student14); 
                courseService.save(course5);

                course6.smartEnroll(student15);
                course6.smartEnroll(student16); 
                course6.smartEnroll(student17); 
                course6.smartEnroll(student18);
                course6.smartEnroll(student19); 
                courseService.save(course6);

                course7.smartEnroll(student20); 
                course7.smartEnroll(student21);
                course7.smartEnroll(student22); 
                courseService.save(course7);

                course8.smartEnroll(student1);
                course8.smartEnroll(student2);  
                course8.smartEnroll(student3);  
                course8.smartEnroll(student4);
                course8.smartEnroll(student5);  
                courseService.save(course8);

                course9.smartEnroll(student6);
                course9.smartEnroll(student7);  
                course9.smartEnroll(student8);  
                course9.smartEnroll(student9);
                course9.smartEnroll(student10); 
                courseService.save(course9);
            }
        };
    }
}


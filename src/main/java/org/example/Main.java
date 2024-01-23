package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class Main {
    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml").build();
        SessionFactory sessionFactory = new MetadataSources(registry)
                .buildMetadata().buildSessionFactory();

        // Создание и добавление объекта
        try(Session session = sessionFactory.openSession()) {
            Course course = new Course("Math", 160);
            session.beginTransaction();
            session.save(course);
            course = new Course("Intro to Java", 241);
            session.save(course);
            session.getTransaction().commit();
        }

        // Чтение объекта
        try(Session session = sessionFactory.openSession()) {
            Course course = session.get(Course.class, 1);
            System.out.println("Прочитано из базы: " + course);
        }

        // Обновление объекта
        try (Session session = sessionFactory.openSession()) {
            Course course = session.get(Course.class, 1);
            System.out.println("Считан объект для изменения: " + course);
            session.beginTransaction();
            course.setTitle("Java for beginners");
            course.setDuration(120);
            session.update(course);
            session.getTransaction().commit();
            System.out.println("Записан измененный объект " + course);
        }

        // Удаление объекта
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Course course = session.get(Course.class, 1);
            session.delete(course);
            session.getTransaction().commit();
            System.out.println("Удален объект " + course);
        }
    }
}

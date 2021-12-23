package com.yarmoiseev.webapp;

import com.yarmoiseev.webapp.model.*;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResumeTestData {
    public static void main(String[] args) {
        Map<ContactType, String> contacts = new HashMap<>();
        Map<SectionType, Section> sections = new HashMap<>();
        List<String> achievements = new ArrayList<>();
        List<String> qualifications = new ArrayList<>();
        List<OrgItem> experience = new ArrayList<>();
        List<OrgItem> education = new ArrayList<>();

        achievements.add("С 2013 года: разработка проектов \"Разработка Web приложения\"," +
                "\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). " +
                "Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация " +
                "онлайн стажировок и ведение проектов. Более 1000 выпускников. ");
        achievements.add("Реализация двухфакторной аутентификации для онлайн платформы управления " +
                "проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk. ");
        achievements.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. " +
                "Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на " +
                "стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP " +
                "модулей, интеграция CIFS/SMB java сервера. ");

        qualifications.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2 ");
        qualifications.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce ");
        qualifications.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, ");
        qualifications.add("MySQL, SQLite, MS SQL, HSQLDB ");

        experience.add(new OrgItem(
                new Link("Java Online Projects", "https://javaops.ru/"),
                LocalDate.of(2013, 10, 1),
                LocalDate.now(),
                "Автор проекта.",
                "Создание, организация и проведение Java онлайн проектов и стажировок." + "\n"));
        experience.add(new OrgItem(
                new Link("Wrike", "https://www.wrike.com/vn/"),
                LocalDate.of(2014, 10, 1),
                LocalDate.of(2016, 1, 1),
                "Старший разработчик (backend)",
                "Проектирование и разработка онлайн платформы управления проектами " +
                        "Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, " +
                        "Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO." + "\n"));
        experience.add(new OrgItem(
                new Link("RIT Center", ""),
                LocalDate.of(2012, 4, 1),
                LocalDate.of(2014, 10, 1),
                "Java архитектор",
                "Организация процесса разработки системы ERP для разных окружений: релизная политика, " +
                        "версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), " +
                        "конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной " +
                        "части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), " +
                        "сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN " +
                        "для online редактирование из браузера документов MS Office. Maven + plugin development, " +
                        "Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, " +
                        "Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python" + "\n"));

        education.add(new OrgItem(
                new Link("Coursera", "https://www.coursera.org/learn/scala-functional-programming"),
                LocalDate.of(2013, 3, 1),
                LocalDate.of(2013, 5, 1),
                "\"Functional Programming Principles in Scala\" by Martin Odersky",
                ""));
        education.add(new OrgItem(
                new Link("Luxoft", "https://www.luxoft-training.ru/kurs/obektno-orientirovannyy_" +
                        "analiz_i_proektirovanie_na_uml.html"),
                LocalDate.of(2011, 3, 1),
                LocalDate.of(2011, 4, 1),
                "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"",
                ""));
        education.add(new OrgItem(
                new Link("Siemens AG", "https://new.siemens.com/ru/ru.html"),
                LocalDate.of(2005, 1, 1),
                LocalDate.of(2005, 4, 1),
                "3 месяца обучения мобильным IN сетям (Берлин)",
                ""));

        Section objSection = new TextSection("Ведущий стажировок и корпоративного обучения " +
                "по Java Web и Enterprise технологиям");
        Section persSection = new TextSection("Аналитический склад ума, сильная логика, креативность, " +
                "инициативность. Пурист кода и архитектуры. ");
        Section achieveSection = new BulletTextSection(achievements);
        Section qualSection = new BulletTextSection(qualifications);
        Section expSection = new OrgTextSection(experience);
        Section eduSection = new OrgTextSection(education);

        contacts.put(ContactType.TEL, "+7(921) 855-0482");
        contacts.put(ContactType.SKYPE, "grigory.kislin");
        contacts.put(ContactType.MAIL, "gkislin@yandex.ru");
        contacts.put(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        contacts.put(ContactType.GITHUB, "https://github.com/gkislin");
        contacts.put(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473/grigory-kislin");
        contacts.put(ContactType.HOMEPAGE, "http://gkislin.ru/");


        sections.put(SectionType.OBJECTIVE, objSection);
        sections.put(SectionType.PERSONAL, persSection);
        sections.put(SectionType.ACHIEVEMENT, achieveSection);
        sections.put(SectionType.QUALIFICATIONS, qualSection);
        sections.put(SectionType.EXPERIENCE, expSection);
        sections.put(SectionType.EDUCATION, eduSection);

        Resume resume = new Resume("uuid10","Григорий Кислин", contacts, sections);

        System.out.println(resume.getFullName() + "\n");

        Map<ContactType, String> contactsToPrint = resume.getContacts();
        Map<SectionType, Section> sectionsToPrint = resume.getSections();

        for (ContactType c : ContactType.values()) {
            System.out.println(c + " : " + contactsToPrint.get(c) + "\n");
        }

        for (SectionType s : SectionType.values()) {
            System.out.println(s + "\n" + sectionsToPrint.get(s).toString() + "\n");
        }
    }
}

package com.yarmoiseev.webapp;

import com.yarmoiseev.webapp.model.*;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class ResumeTestData {
    public static void main(String[] args) {

        Resume resume = createResume("uuid001", "Григорий Кислин");

        System.out.println(resume.getFullName() + "\n");

        for (ContactType c : ContactType.values()) {
            System.out.println(c + " : " + resume.getContactsAsString(c) + "\n");
        }

        for (SectionType s : SectionType.values()) {
            System.out.println(s + "\n" + resume.getSection(s).toString() + "\n");
        }
    }

    public static Resume createResume(String uuid, String fullName) {
        List<String> achievements = new ArrayList<>();

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

        List<String> qualifications = new ArrayList<>();

        qualifications.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2 ");
        qualifications.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce ");
        qualifications.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, ");
        qualifications.add("MySQL, SQLite, MS SQL, HSQLDB ");

        List<OrgItem> experience = new ArrayList<>();

        experience.add(new OrgItem("Java Online Projects", "https://javaops.ru/",
                new OrgItem.OrgPeriod(2013, Month.DECEMBER, "Автор проекта.",
                        "Создание, организация и проведение Java онлайн проектов и стажировок.")));
        experience.add(new OrgItem("Wrike", null,
                new OrgItem.OrgPeriod(2014, Month.DECEMBER, 2016, Month.JANUARY,
                        "Старший разработчик (backend)",
                        null)));
        experience.add(new OrgItem("RIT Center", null,
                new OrgItem.OrgPeriod(2012, Month.APRIL, 2014, Month.DECEMBER, "Java архитектор",
                        "Организация процесса разработки системы ERP для разных окружений: релизная политика, " +
                                "версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), " +
                                "конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной " +
                                "части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), " +
                                "сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN " +
                                "для online редактирование из браузера документов MS Office. Maven + plugin development, " +
                                "Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, " +
                                "Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python")));

        List<OrgItem> education = new ArrayList<>();

        education.add(new OrgItem("Coursera", "https://www.coursera.org/learn/scala-functional-programming",
                new OrgItem.OrgPeriod(2031, Month.MARCH, 2013, Month.MAY,
                        "\"Functional Programming Principles in Scala\" by Martin Odersky",
                        null)));
        education.add(new OrgItem("Luxoft", "https://www.luxoft-training.ru",
                new OrgItem.OrgPeriod(2011, Month.MARCH, 2011, Month.APRIL,
                        "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"",
                        null)));
        education.add(new OrgItem("Siemens AG", "https://new.siemens.com/ru/ru.html",
                new OrgItem.OrgPeriod(2005, Month.JANUARY, 2005, Month.APRIL,
                        "3 месяца обучения мобильным IN сетям (Берлин)",
                        null),
                new OrgItem.OrgPeriod(2005, Month.MAY, 2006, Month.JANUARY,
                        "Тест дополнительного периода",
                        "Тестовый текст")));

        AbstractSection objSection = new TextSection("Ведущий стажировок и корпоративного обучения " +
                "по Java Web и Enterprise технологиям");
        AbstractSection persSection = new TextSection("Аналитический склад ума, сильная логика, креативность, " +
                "инициативность. Пурист кода и архитектуры. ");
        AbstractSection achieveSection = new BulletTextSection(achievements);
        AbstractSection qualSection = new BulletTextSection(qualifications);
        AbstractSection expSection = new OrganizationListSection(experience);
        AbstractSection eduSection = new OrganizationListSection(education);

        Resume resume = new Resume(uuid, fullName);

       resume.contacts.put(ContactType.TEL, "+7(921) 855-0482");
        resume.contacts.put(ContactType.SKYPE, "grigory.kislin");
        resume.contacts.put(ContactType.MAIL, "gkislin@yandex.ru");
        resume.contacts.put(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        resume.contacts.put(ContactType.GITHUB, "https://github.com/gkislin");
        resume.contacts.put(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473/grigory-kislin");
        resume.contacts.put(ContactType.HOMEPAGE, "http://gkislin.ru/");
//
//        resume.sections.put(SectionType.OBJECTIVE, objSection);
//        resume.sections.put(SectionType.PERSONAL, persSection);
//        resume.sections.put(SectionType.ACHIEVEMENT, achieveSection);
//        resume.sections.put(SectionType.QUALIFICATIONS, qualSection);
//        resume.sections.put(SectionType.EXPERIENCE, expSection);
//        resume.sections.put(SectionType.EDUCATION, eduSection);

        return resume;
    }
}

package com.yarmoiseev.webapp.storage.serializestrategy;

import com.yarmoiseev.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.yarmoiseev.webapp.ResumeTestData.createResume;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());

            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            Map<SectionType, AbstractSection> sections = r.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {

                SectionType sType = entry.getKey();
                dos.writeUTF(sType.name());
                switch (sType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        TextSection textSection = (TextSection) entry.getValue();
                        dos.writeUTF(textSection.getText());
                        break;

                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        BulletTextSection bulletTextSection = (BulletTextSection) entry.getValue();
                        List<String> items = bulletTextSection.getItems();
                        dos.writeInt(items.size());
                        for (String item : items) {
                            dos.writeUTF(item);
                        }
                        break;

                    case EXPERIENCE:
                    case EDUCATION:
                        OrganizationListSection orgSection = (OrganizationListSection) entry.getValue();
                        List<OrgItem> orgItems = orgSection.getItems();
                        dos.writeInt(orgItems.size());
                        for (OrgItem item : orgItems) {
                            Link name = item.getName();
                            List<OrgItem.OrgPeriod> periodsList = item.getPeriodsList();
                            dos.writeUTF(name.getName());
                            dos.writeUTF(name.getUrl());
                            dos.writeInt(periodsList.size());

                            for (OrgItem.OrgPeriod period : periodsList) {
                                dos.writeInt(period.getStartDate().getYear());
                                dos.writeInt(period.getStartDate().getMonth().getValue());
                                dos.writeInt(period.getEndDate().getYear());
                                dos.writeInt(period.getEndDate().getMonth().getValue());
                                dos.writeUTF(period.getTitle());
                                dos.writeUTF(period.getDescription());
                            }
                        }
                        break;

                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            int sectionsSize = dis.readInt();
            for (int i = 0; i < sectionsSize; i++) {
                SectionType sType = SectionType.valueOf(dis.readUTF());
                switch (sType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.addSection(sType, new TextSection(dis.readUTF()));
                        break;

                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        int itemsSize = dis.readInt();
                        List<String> items = new ArrayList<>(itemsSize);
                        for (int j = 0; j < itemsSize; j++) {
                            items.add(dis.readUTF());
                        }
                        resume.addSection(sType, new BulletTextSection(items));
                        break;

                    case EXPERIENCE:
                    case EDUCATION:
                        int orgItemsSize = dis.readInt();
                        List<OrgItem> orgItems = new ArrayList<>(orgItemsSize);
                        for (int j = 0; j < orgItemsSize; j++) {
                            Link name = new Link(dis.readUTF(), dis.readUTF());
                            int periodsListSize = dis.readInt();
                            List<OrgItem.OrgPeriod> periodsList = new ArrayList<>(periodsListSize);
                            for (int k = 0; k < periodsListSize; k++) {
                                OrgItem.OrgPeriod orgPeriod = new OrgItem.OrgPeriod(
                                        LocalDate.of(dis.readInt(), dis.readInt(), 1),
                                        LocalDate.of(dis.readInt(), dis.readInt(), 1),
                                        dis.readUTF(), dis.readUTF()
                                );
                                periodsList.add(orgPeriod);
                            }
                            OrgItem orgItem = new OrgItem(name, periodsList);
                            orgItems.add(orgItem);
                        }
                        resume.addSection(sType, new OrganizationListSection(orgItems));
                        break;

                }
            }
            return resume;
        }
    }

    public static void main(String[] args) {
        Resume resume = createResume("uuid001", "Григорий Кислин");
        DataStreamSerializer dss = new DataStreamSerializer();
        try {
            OutputStream os = new BufferedOutputStream(new FileOutputStream("nom2.txt"));
            dss.doWrite(resume, os);
        } catch (IOException e) {
            e.printStackTrace();
        }


        InputStream is;
        Resume inResume = null;
        try {
            is = new BufferedInputStream(new FileInputStream("nom2.txt"));
            inResume = dss.doRead(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(inResume);


    }
}

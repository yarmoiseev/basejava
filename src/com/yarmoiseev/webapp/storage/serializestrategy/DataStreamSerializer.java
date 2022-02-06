package com.yarmoiseev.webapp.storage.serializestrategy;

import com.yarmoiseev.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();

            writeCollection(contacts.entrySet(), dos, entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });

            Map<SectionType, AbstractSection> sections = r.getSections();

            writeCollection(sections.entrySet(), dos, entry -> {
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

                        writeCollection(items, dos, dos::writeUTF);
                        break;

                    case EXPERIENCE:
                    case EDUCATION:
                        OrganizationListSection orgSection = (OrganizationListSection) entry.getValue();
                        List<OrgItem> orgItems = orgSection.getItems();

                        writeCollection(orgItems, dos, item -> {
                            Link name = item.getName();
                            List<OrgItem.OrgPeriod> periodsList = item.getPeriodsList();
                            dos.writeUTF(name.getName());
                            String url = name.getUrl();
                            writeNullSafely(dos, url);

                            writeCollection(periodsList, dos, period -> {
                                writeDate(dos, period.getStartDate());
                                writeDate(dos, period.getEndDate());
                                dos.writeUTF(period.getTitle());
                                String description = period.getDescription();
                                writeNullSafely(dos, description);
                            });
                        });
                        break;
                }
            });
        }
    }

    private void writeDate(DataOutputStream dos, LocalDate localDate) throws IOException {
        dos.writeInt(localDate.getYear());
        dos.writeInt(localDate.getMonth().getValue());
    }

    private void writeNullSafely(DataOutputStream dos, String data) throws IOException {
        dos.writeUTF(data == null ? "" : data);
    }

    private <T> void writeCollection(Collection<T> collection, DataOutputStream dos, CollectionItemWriter<T> cw)
            throws IOException {
        dos.writeInt(collection.size());
        for (T t : collection) {
            cw.write(t);
        }
    }

    @FunctionalInterface
    private interface CollectionItemWriter<T> {
        void write(T t) throws IOException;
    }


    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            iterateCollection(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            iterateCollection(dis, () -> {
                SectionType sType = SectionType.valueOf(dis.readUTF());
                switch (sType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.addSection(sType, new TextSection(dis.readUTF()));
                        break;

                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        resume.addSection(sType, new BulletTextSection(iterateList(dis, dis::readUTF)));
                        break;

                    case EXPERIENCE:
                    case EDUCATION:
                        resume.addSection(sType, new OrganizationListSection(iterateList(dis, () -> {
                            Link name = new Link(dis.readUTF(), dis.readUTF());
                            String url = name.getUrl();
                            if (url.equals("")) {
                                name.setUrl(null);
                            }
                            return new OrgItem(name, iterateList(dis, () -> {
                                OrgItem.OrgPeriod orgPeriod = new OrgItem.OrgPeriod(
                                        readDate(dis),
                                        readDate(dis),
                                        dis.readUTF(), dis.readUTF()
                                );
                                String description = orgPeriod.getDescription();
                                if (description.equals("")) {
                                    orgPeriod.setDescription(null);
                                }
                                return orgPeriod;
                            }));
                        })));
                        break;
                }
            });
            return resume;
        }
    }

    private LocalDate readDate(DataInputStream dis) throws IOException {
        return LocalDate.of(dis.readInt(), dis.readInt(), 1);
    }

    private void iterateCollection(DataInputStream dis, CollectionReader ci)
            throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            ci.read();
        }

    }

    @FunctionalInterface
    private interface CollectionReader {
        void read() throws IOException;
    }

    private <T> List<T> iterateList(DataInputStream dis, ListReader<T> lr) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(lr.read());
        }
        return list;

    }

    @FunctionalInterface
    private interface ListReader<T> {
        T read() throws IOException;
    }
}

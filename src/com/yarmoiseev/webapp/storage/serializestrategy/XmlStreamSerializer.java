package com.yarmoiseev.webapp.storage.serializestrategy;

import com.yarmoiseev.webapp.model.*;
import com.yarmoiseev.webapp.util.XmlParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlStreamSerializer implements StreamSerializer {
    private XmlParser xmlParser;

    public XmlStreamSerializer() {
        xmlParser = new XmlParser(Resume.class, TextSection.class, OrgItem.class,
                OrgItem.OrgPeriod.class, OrganizationListSection.class,
                Link.class, BulletTextSection.class);
    }

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try(Writer w = new OutputStreamWriter(os, StandardCharsets.UTF_8)) {
            xmlParser.marshall(r, w);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try(Reader r = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            return xmlParser.unmarshall(r);
        }
    }
}

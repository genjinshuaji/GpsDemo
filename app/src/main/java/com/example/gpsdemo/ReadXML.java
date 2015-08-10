package com.example.gpsdemo;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sy1 on 2015/8/7.
 */
public class ReadXML {
    public static List<GPXLocation> readXML(InputStream inStream) {

        XmlPullParser parser = Xml.newPullParser();
        List<GPXLocation> persons = null;

        try {
            parser.setInput(inStream, "UTF-8");
            int eventType = parser.getEventType();

            GPXLocation currentPerson = null;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT://�ĵ���ʼ�¼�,���Խ������ݳ�ʼ������
                        persons = new ArrayList<GPXLocation>();
                        break;

                    case XmlPullParser.START_TAG://��ʼԪ���¼�
                        String name = parser.getName();
                        if (name.equalsIgnoreCase("trkpt")) {
                            currentPerson = new GPXLocation();
                            currentPerson.setlat(new String(parser.getAttributeValue(null, "lat")));
                            currentPerson.setlon(new String(parser.getAttributeValue(null, "lon")));
                        } else if (currentPerson != null) {
                            if (name.equalsIgnoreCase("name")) {
                                //currentPerson.setName(parser.nextText());// ���������TextԪ��,����������ֵ
                            } else if (name.equalsIgnoreCase("age")) {
                                //currentPerson.setAge(new Short(parser.nextText()));
                            }
                        }
                        break;

                    case XmlPullParser.END_TAG://����Ԫ���¼�
                        if (parser.getName().equalsIgnoreCase("trkpt") && currentPerson != null) {
                            persons.add(currentPerson);
                            currentPerson = null;
                        }

                        break;
                }

                eventType = parser.next();
            }

            inStream.close();
            return persons;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return persons;
    }
}

package io.vepo.jcrawler.uol;

import java.util.Date;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateAdapter extends XmlAdapter<String, Date> {
      // Mon, 07 Feb 2022 17:49:21 -0300
      private static final String CUSTOM_FORMAT_STRING = "EEE, dd MMM yyyy HH:mm:ss XXX";
      private static final DateFormat DATE_FORMAT = new SimpleDateFormat(CUSTOM_FORMAT_STRING);

      @Override
      public String marshal(Date v) {
          return DATE_FORMAT.format(v);
      }

      @Override
      public Date unmarshal(String v) {
          try {
              return DATE_FORMAT.parse(v);
          } catch (ParseException pe) {
              pe.printStackTrace();
              return null;
          }
      }
}

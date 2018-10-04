package gov.laca.amp.datacapturewccuiapp.model.util;


import java.io.Serializable;

import java.math.BigDecimal;

import java.math.BigInteger;

import java.sql.Timestamp;

import java.text.ParseException;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;

import java.util.TimeZone;

import oracle.adf.share.logging.ADFLogger;

import oracle.stellent.ridc.model.impl.DataObjectEncodingUtils;

public class ContentUtil {
    
    private static ADFLogger _logger = ADFLogger.createADFLogger(ContentUtil.class);
    private static final String timeZone = "GMT";
    
    public ContentUtil() {
        super();
    }
    
    public static Date wccDateToUtilDate(String strDate) {
            _logger.entering(ContentUtil.class.getName(), "wccDateToUtilDate");
            Date date = null;

            if ((strDate != null) && !strDate.trim().isEmpty()) {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss'Z'");
                sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
                try {
                    date = sdf.parse(strDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                    _logger.severe(e);
                }
            }
            _logger.exiting(ContentUtil.class.getName(), "wccDateToUtilDate");
            return date;
        }

        public static String utilDateToWccDate(Date date) {
            _logger.entering(ContentUtil.class.getName(), "utilDateToWccDate");
            String strDate = null;

            if (date != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss'Z'");
                sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
                strDate = sdf.format(date);
            }
            _logger.exiting(ContentUtil.class.getName(), "utilDateToWccDate");
            return strDate;
        }
        
        
    public static <T extends Serializable> T parseRidcString(String value, Class<T> type) {
        _logger.entering(ContentUtil.class.getName(), "parseRidcString");
        assert (type != null);

        T obj = null;
        try {
            if ((value != null) && (value.length() != 0)) {
                if (String.class.equals(type)) {
                    obj = (T) value;
                } else if (Timestamp.class.equals(type)) {
                    Calendar calendar = DataObjectEncodingUtils.decodeDate(value);
                    obj = (T) (new Timestamp(calendar.getTimeInMillis()));
                } else if (Boolean.class.equals(type)) {
                    obj = (T) (("1".equals(value)) || ("true".equalsIgnoreCase(value)) ? Boolean.TRUE : Boolean.FALSE);
                } else if (Integer.class.equals(type)) {
                    obj = (T) (Integer.valueOf(value));
                } else if (Long.class.equals(type)) {
                    obj = (T) (Long.valueOf(value));
                } else if (BigDecimal.class.equals(type)) {
                    obj = (T) (new BigDecimal(value));
                } else if (BigInteger.class.equals(type)) {
                    obj = (T) (new BigInteger(value));
                } else if (Date.class.equals(type)) {
                    obj = (T) (ContentUtil.wccDateToUtilDate(value));
                } else {
                    obj = (T) value;
                }
            }
        } catch (Exception e) {
            _logger.severe(e);
        }
        
        _logger.exiting(ContentUtil.class.getName(), "parseRidcString");
        return obj;
    }
    
    public static String toRidcString(Object value) {
        _logger.entering(ContentUtil.class.getName(), "toRidcString");
        String s = null;
      
            try {
                if (value == null) {
                    s = "";
                } else if ((value instanceof String)) {
                    s = (String) value;
                } else if ((value instanceof Date)) {
                    s = ContentUtil.utilDateToWccDate((Date)value);
                } else if ((value instanceof Boolean)) {
                    s = ((Boolean) value).booleanValue() ? "1" : "0";
                } else if ((value instanceof Integer) || (value instanceof BigInteger)) {
                    s = value.toString();
                } else if ((value instanceof Long)) {
                    s = value.toString();
                } else if ((value instanceof BigDecimal)) {
                    s = value.toString();
                } 
                
            } catch (Exception e) {
                _logger.severe(e);
            }
        
        _logger.exiting(ContentUtil.class.getName(), "toRidcString");
        return s;
    }
}

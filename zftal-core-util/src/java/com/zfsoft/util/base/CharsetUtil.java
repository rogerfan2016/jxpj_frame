package com.zfsoft.util.base;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * 
 * <p>
 * Title: CharsetUtil
 * </p>
 * <p>
 * Description: 字符编码工具类
 * </p>
 * <p>
 * Company: XXXX
 * </p>
 * 
 * @author rogerfan
 * @date 2016-2-23
 */
public class CharsetUtil {

	/**
	 * 
	 * <p>
	 * Title: UnicodeToGBK
	 * </p>
	 * <p>
	 * Description: Unicode转GBK
	 * </p>
	 * 
	 * @param original
	 * @return
	 */
	public static String UnicodeToGBK(String original) {
		if (original != null) {
			try {
				String str = new String(original.getBytes("ISO8859_1"), "GBK");
				return str;
			} catch (Exception e) {
				// e.printStackTrace();

				return "";
			}
		} else {
			return "";
		}
	}

	/**
	 * 
	 * <p>
	 * Title: GBKToUnicode
	 * </p>
	 * <p>
	 * Description: GBK转Unicode
	 * </p>
	 * 
	 * @param original
	 * @return
	 */
	public static String GBKToUnicode(String original) {
		if (original != null) {
			try {
				return new String(original.getBytes("GBK"), "ISO8859_1");
			} catch (Exception e) {
				// e.printStackTrace();

				return "";
			}
		} else {
			return "";
		}
	}

	/**
	 * 
	 * <p>
	 * Title: GBK2UTF8
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param text
	 * @return
	 */
	public static String GBK2UTF8(String text) {
		String result = "";
		try {
			result = new String(text.getBytes("GBK"), "UTF-8");
		} catch (UnsupportedEncodingException ex) {
			// ex.printStackTrace();
		}
		return result;
	}

	/**
	 * 
	 * <p>
	 * Title: UTF82GBK
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param text
	 * @return
	 */
	public static String UTF82GBK(String text) {
		String result = "";
		try {
			result = new String(text.getBytes("UTF-8"), "GBK");
		} catch (UnsupportedEncodingException ex) {
			// ex.printStackTrace();
		}
		return result;
	}

	/**
	 * 转换编码 ISO-8859-1到GB2312
	 * 
	 * @param text
	 * @return
	 */
	public String ISO2GB(String text) {
		String result = "";
		try {
			result = new String(text.getBytes("ISO-8859-1"), "GB2312");
		} catch (UnsupportedEncodingException ex) {
			result = ex.toString();
		}
		return result;
	}

	/**
	 * 转换编码 GB2312到ISO-8859-1
	 * 
	 * @param text
	 * @return
	 */
	public String GB2ISO(String text) {
		String result = "";
		try {
			result = new String(text.getBytes("GB2312"), "ISO-8859-1");
		} catch (UnsupportedEncodingException ex) {
			ex.printStackTrace();
		}
		return result;
	}

	/**
	 * Utf8URL编码
	 * 
	 * @param s
	 * @return
	 */
	public String Utf8URLencode(String text) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (c >= 0 && c <= 255) {
				result.append(c);
			} else {
				byte[] b = new byte[0];
				try {
					b = Character.toString(c).getBytes("UTF-8");
				} catch (Exception ex) {
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					result.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return result.toString();
	}

	/**
	 * Utf8URL解码
	 * 
	 * @param text
	 * @return
	 */
	public String Utf8URLdecode(String text) {
		String result = "";
		int p = 0;
		if (text != null && text.length() > 0) {
			text = text.toLowerCase();
			p = text.indexOf("%e");
			if (p == -1)
				return text;
			while (p != -1) {
				result += text.substring(0, p);
				text = text.substring(p, text.length());
				if (text == "" || text.length() < 9)
					return result;
				result += CodeToWord(text.substring(0, 9));
				text = text.substring(9, text.length());
				p = text.indexOf("%e");
			}
		}
		return result + text;
	}

	/**
	 * utf8URL编码转字符
	 * 
	 * @param text
	 * @return
	 */
	private String CodeToWord(String text) {
		String result;
		if (Utf8codeCheck(text)) {
			byte[] code = new byte[3];
			code[0] = (byte) (Integer.parseInt(text.substring(1, 3), 16) - 256);
			code[1] = (byte) (Integer.parseInt(text.substring(4, 6), 16) - 256);
			code[2] = (byte) (Integer.parseInt(text.substring(7, 9), 16) - 256);
			try {
				result = new String(code, "UTF-8");
			} catch (UnsupportedEncodingException ex) {
				result = null;
			}
		} else {
			result = text;
		}
		return result;
	}

	/**
	 * 编码是否有效
	 * 
	 * @param text
	 * @return
	 */
	private boolean Utf8codeCheck(String text) {
		String sign = "";
		if (text.startsWith("%e"))
			for (int i = 0, p = 0; p != -1; i++) {
				p = text.indexOf("%", p);
				if (p != -1)
					p++;
				sign += p;
			}
		return sign.equals("147-1");
	}

	/**
	 * 是否Utf8Url编码
	 * 
	 * @param text
	 * @return
	 */
	public boolean isUtf8Url(String text) {
		text = text.toLowerCase();
		int p = text.indexOf("%");
		if (p != -1 && text.length() - p > 9) {
			text = text.substring(p, p + 9);
		}
		return Utf8codeCheck(text);
	}
	
	/**
	 * 
	* <p>Title: toUtf8String</p>
	* <p>Description: 转换为%E4%BD%A0形式</p>
	* @param s
	* @return
	 */
    public static String toUtf8String(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 0 && c <= 255) {
                sb.append(c);
            } else {
                byte[] b;
                try {
                    b = String.valueOf(c).getBytes("utf-8");
                } catch (Exception ex) {
                    System.out.println(ex);
                    b = new byte[0];
                }
                for (int j = 0; j < b.length; j++) {
                    int k = b[j];
                    if (k < 0)
                        k += 256;
                    sb.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return sb.toString();
    }
   
    /**
     * 
    * <p>Title: unescape</p>
    * <p>Description: 将%E4%BD%A0转换为汉字 </p>
    * @param s
    * @return
     */
    public static String unescape(String s) {
        StringBuffer sbuf = new StringBuffer();
        int l = s.length();
        int ch = -1;
        int b, sumb = 0;
        for (int i = 0, more = -1; i < l; i++) {
            /* Get next byte b from URL segment s */
            switch (ch = s.charAt(i)) {
            case '%':
                ch = s.charAt(++i);
                int hb = (Character.isDigit((char) ch) ? ch - '0'
                        : 10 + Character.toLowerCase((char) ch) - 'a') & 0xF;
                ch = s.charAt(++i);
                int lb = (Character.isDigit((char) ch) ? ch - '0'
                        : 10 + Character.toLowerCase((char) ch) - 'a') & 0xF;
                b = (hb << 4) | lb;
                break;
            case '+':
                b = ' ';
                break;
            default:
                b = ch;
            }
            /* Decode byte b as UTF-8, sumb collects incomplete chars */
            if ((b & 0xc0) == 0x80) { // 10xxxxxx (continuation byte)   
                sumb = (sumb << 6) | (b & 0x3f); // Add 6 bits to sumb   
                if (--more == 0)
                    sbuf.append((char) sumb); // Add char to sbuf   
            } else if ((b & 0x80) == 0x00) { // 0xxxxxxx (yields 7 bits)   
                sbuf.append((char) b); // Store in sbuf   
            } else if ((b & 0xe0) == 0xc0) { // 110xxxxx (yields 5 bits)   
                sumb = b & 0x1f;
                more = 1; // Expect 1 more byte   
            } else if ((b & 0xf0) == 0xe0) { // 1110xxxx (yields 4 bits)   
                sumb = b & 0x0f;
                more = 2; // Expect 2 more bytes   
            } else if ((b & 0xf8) == 0xf0) { // 11110xxx (yields 3 bits)   
                sumb = b & 0x07;
                more = 3; // Expect 3 more bytes   
            } else if ((b & 0xfc) == 0xf8) { // 111110xx (yields 2 bits)   
                sumb = b & 0x03;
                more = 4; // Expect 4 more bytes   
            } else /*if ((b & 0xfe) == 0xfc)*/{ // 1111110x (yields 1 bit)   
                sumb = b & 0x01;
                more = 5; // Expect 5 more bytes   
            }
            /* We don't test if the UTF-8 encoding is well-formed */
        }
        return sbuf.toString();
    }

	/**
	 * 测试
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		CharsetUtil charTools = new CharsetUtil();
		String url;
		url = toUtf8String("2016年2月20日的曾涨停");
		
		if (charTools.isUtf8Url(url)) {
			System.out.println(charTools.Utf8URLencode(url));
		} else {
			System.out.println(URLDecoder.decode(url));
		}
	}

}

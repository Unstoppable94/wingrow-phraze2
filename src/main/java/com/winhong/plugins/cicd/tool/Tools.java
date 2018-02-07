package com.winhong.plugins.cicd.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.winhong.plugins.cicd.view.ProjectView;

public class Tools {
	private static final Logger log = LoggerFactory.getLogger(Tools.class);

	public Tools() {
	}

	public static final String IN_PROGRESS = "IN_PROGRESS";
	public static final String FAILED = "FAILED";
	public static final String SUCCESS = "SUCCESS";
	public static final String NOTBUILD = "NOTBUILD";

	public static String colorToStatus(String color) {
		if (color.endsWith("anime"))
			return IN_PROGRESS;
		else if (color.startsWith("red"))
			return FAILED;
		else if (color.startsWith("blue"))
			return SUCCESS;

		return color;

	}

	public static String getJson(Object o) {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().serializeNulls().create();
		return gson.toJson(o);
	}

	/**
	 * By default ,all file save with encrypting
	 * 
	 * @param str
	 * @param filename
	 * @throws IOException
	 */
	public static void saveStringToFile(String str, String filename) throws IOException {

		saveStringToFile(str, filename, true);
	}
	
	/**
	 * if not encrypting, use this method
	 * @param str
	 * @param filename
	 * @throws IOException
	 */
	public static void saveStringToFile_decrypt(String str, String filename) throws IOException {

		saveStringToFile(str, filename, false);
	}
	public static void saveStringToFile(String str, String filename, boolean encrypt) throws IOException {
		File file = new File(filename);
		if (!file.exists())
			file.createNewFile();
		FileWriter fileWrite = null;
		try {
			fileWrite = new FileWriter(file);
			if(str != null){
				if (encrypt)
					fileWrite.write(Encryptor.encrypt(str));
				else
					fileWrite.write(str);
			}

		} finally {
			if (fileWrite != null)
					fileWrite.close();
		}

	}

	/**
	 * 读取Resource
	 * 
	 * @param resourcename
	 *            resource name
	 * @return String
	 * @throws IOException
	 *             exception
	 */
	public static String readResource(String resourcename, boolean isEncrypt) throws IOException {
		ClassLoader classLoader = Tools.class.getClassLoader();

		InputStream fis = classLoader.getResourceAsStream(resourcename);
		if (isEncrypt)
			return Encryptor.decrypt(IOUtils.toString(fis));
		else
			return (IOUtils.toString(fis));
	}

	/**
	 * 从json格式的资源文件中生成对象
	 * 
	 * @param resourcename
	 *            文件
	 * @param cla
	 *            类名称
	 * @return 对象
	 * @throws IOException
	 */
	public static Object objectFromJsonResource(String resourcename, Class<?> cla) throws IOException {

		return objectFromJsonResource(resourcename, cla, true);

	}

	public static Object objectFromJsonResource(String resourcename, Class<?> cla, boolean isEncrypt)
			throws IOException {

		String input = readResource(resourcename, isEncrypt);

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().serializeNulls().create();

		return gson.fromJson(input, cla);
	}

	/**
	 * 从json格式的资源文件中生成对象
	 * 
	 * @param filename
	 *            文件
	 * @param cla
	 *            类名称
	 * @return 对象
	 * @throws IOException
	 */
	public static Object objectFromJsonFile(String filename, Class cla) throws IOException {

		String str = readFile(new File(filename)).toString();

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().serializeNulls().create();

		return gson.fromJson(str, cla);
	}

	/**
	 * 从json格式的URL生成集合，如ArrayList 对象
	 * 
	 * @param url
	 *            URL
	 * @param type
	 *            gson 定义的类型，如ArrayList<String>
	 * @return 对象
	 * @throws MalformedURLException
	 *             URL异常
	 * @throws IOException
	 *             IO //
	 */

	public static Object objectFromJsonString(String content, Type type) throws MalformedURLException, IOException {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().serializeNulls().create();

		return gson.fromJson(content, type);
	}

	public static Object objectFromJsonString(String json, Class cla) throws FileNotFoundException {

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().serializeNulls().create();

		return gson.fromJson(json, cla);
	}

	/**
	 * 从json格式的URL生成 对象
	 * 
	 * @param url
	 *            URL
	 * @param cla
	 *            class
	 * @return 对象
	 * @throws MalformedURLException
	 *             异常
	 * @throws IOException
	 *             异常
	 */
	@SuppressWarnings("unchecked")
	// public static Object objectFromJsonUrl(String url, Class cla) throws
	// MalformedURLException, IOException {
	// String json = IOUtils.toString(new URL(url));
	//
	// Gson gson = new
	// GsonBuilder().excludeFieldsWithoutExposeAnnotation().serializeNulls().create();
	//
	// return gson.fromJson(json, cla);
	// }

	// Gson gson = new
	// GsonBuilder().excludeFieldsWithoutExposeAnnotation().serializeNulls().create();

	// return gson.fromJson(jsonReader, cla);

	/**
	 * 将STR中HTML特殊字符进行转码，如空格变为&nbsp;
	 * 
	 * @param s
	 *            要转换的字符串
	 * @return String 转换后的字符串
	 */
	public static final String escapeHTML(String s) {

		StringBuffer sb = new StringBuffer();
		int n = s.length();
		for (int i = 0; i < n; i++) {
			char c = s.charAt(i);
			switch (c) {
			case '<':
				sb.append("&lt;");
				break;
			case '>':
				sb.append("&gt;");
				break;
			case '&':
				sb.append("&amp;");
				break;
			case '"':
				sb.append("&quot;");
				break;
			case 'à':
				sb.append("&agrave;");
				break;
			case 'é':
				sb.append("&eacute;");
				break;
			case 'è':
				sb.append("&egrave;");
				break;
			case 'ê':
				sb.append("&ecirc;");
				break;
			case 'ù':
				sb.append("&ugrave;");
				break;
			case 'ü':
				sb.append("&uuml;");
				break;
			case ' ':
				sb.append("&nbsp;");
				break;

			default:
				sb.append(c);
				break;
			}
		}
		return sb.toString();
	}

	/**
	 * 将STR中转码的内容换成HTML，如&nbsp变为空格;
	 * 
	 * @param s
	 *            要转换的字符串
	 * @return String 转换后的字符串
	 * @see escapeHTML
	 */
	public static final String restoreHTML(String s) {

		String temp = strRep(s, "&lt;", "<");
		temp = strRep(temp, "&gt;", ">");
		temp = strRep(temp, "&amp;", "&");
		temp = strRep(temp, "&quot;", "\"");
		temp = strRep(temp, "&agrave;", "à");
		temp = strRep(temp, "&egrave;", "è");
		temp = strRep(temp, "&ecirc;", "ê");
		temp = strRep(temp, "&eacute;", "é");
		temp = strRep(temp, "&ugrave;", "ù");
		temp = strRep(temp, "&uuml;", "ü");
		temp = strRep(temp, "&nbsp;", " ");
		return temp;
	}

	/**
	 * 对指定的字符串进行替换.
	 * 
	 * @param str
	 *            进行替换操作的字符串
	 * @param source
	 *            要被替换的子字符串
	 * @param target
	 *            替换source的字符串
	 * @return String 用target替换了所有source的str
	 */
	// *********************************************************************
	static public String strRep(String str, String source, String target) {

		int sourceLength;
		int fromIndex = 0;
		int findPos = 0;
		if (target == null)
			target = " ";
		sourceLength = source.length();
		findPos = str.indexOf(source, fromIndex);
		while (findPos >= 0) {
			str = str.substring(0, findPos) + target + str.substring(findPos + sourceLength);

			fromIndex = findPos + target.length();
			findPos = str.indexOf(source, fromIndex);
		}
		// System.out.println(findPos); }
		return str;

	}

	/**
	 * 返回root目录对应的filename的相对路径，即去掉filename以root开头的部门
	 * 
	 * @param root
	 * @param filename
	 * @return
	 */
	static public String getAbsPath(String root, String filename) {
		if (filename.startsWith(root)) {
			return filename.substring(root.length());
		}
		return filename;

	}

	/**
	 * 返回root目录对应的file的相对路径，即去掉file以root开头的部门
	 * 如果获取ROOT或file的getCanonicalPath失败，返回null
	 * 
	 * @param root
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	static public String getAbsPath(File root, File file) throws IOException {
		String rootname;
		String filename;

		rootname = root.getCanonicalPath();
		filename = file.getCanonicalPath();

		if (filename.startsWith(rootname)) {
			return filename.substring(rootname.length());
		}
		return filename;

	}

	final static String contextpath = "<%=request.getcontextpath()%>";

	public static String SpecialfileName(String name) {
		return removeJspTag(name);

	}

	final static String jspStartTag = "<%";
	final static String jspEndTag = "%>";

	public static String removeJspTag(String text) {
		return removeContent(text, jspStartTag, jspEndTag);
	}

	public static String removeContent(String text, String startTag, String endTag) {
		int startPos = text.indexOf(startTag);
		int endPos = 0 - endTag.length();
		StringBuffer ret = new StringBuffer("");
		while (startPos >= 0) {
			ret.append(text.substring(endPos + endTag.length(), startPos));
			endPos = text.indexOf(endTag, startPos);
			startPos = text.indexOf(startTag, endPos + endTag.length());

		}
		if (endPos < 0)
			return text;
		ret.append(text.substring(endPos + endTag.length()));
		return ret.toString();
	}

	/**
	 * by defaul ,all file will save with encrypting
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static StringBuffer readFile(File file) throws IOException {
		// String buffer = new
		// String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));

		return readFileWithDecrypt(file);
	}

	public static StringBuffer readFile(File file, boolean isEncrypted) throws IOException {
		// String buffer = new
		// String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
		if (isEncrypted)
			return readFileWithDecrypt(file);
		else {
			String buffer = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
			return new StringBuffer(buffer);
		}

	}

	public static StringBuffer readFileWithDecrypt(File file) throws IOException {

		String buffer = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));

		return new StringBuffer(Encryptor.decrypt(buffer));
	}

	public static StringBuffer readFileAndRemoveEmptyLine(File file) throws IOException {
		// FileReader input = new FileReader(file);
		// BufferedReader in = new BufferedReader(input);
		StringBuffer buffer = new StringBuffer();
		String line = "";
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		try {
			while ((line = in.readLine()) != null) {
				if (line.trim().length() > 0)
					buffer.append(line + "\n");
			}
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				in.close();
			} catch (Exception e) {

			}

		}
		// buffer=new StringBuffer(UTF8ToGBK(buffer.toString()));
		return buffer;
	}

	/**
	 * 将ISO8859_1编码的字符集合转为GBK.
	 * 
	 * @param InStr
	 *            要转换的字符串
	 * @return String 转换后的字符串
	 */
	public static String UTF8ToGBK(String InStr) {
		String returnStr = null;
		try {
			returnStr = new String(InStr.getBytes("UTF-8"), "GBK");
		} catch (UnsupportedEncodingException e) {
			System.out.println("Eorror");
		}
		return returnStr;

	}

	public static String ToUTF8(String InStr) {
		String returnStr = null;
		try {
			returnStr = new String(InStr.getBytes(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			System.out.println("Eorror");
		}
		return returnStr;

	}

	public static String ToGBK(String InStr) {
		String returnStr = null;
		try {
			returnStr = new String(InStr.getBytes(), "GBK");
		} catch (UnsupportedEncodingException e) {
			System.out.println("Eorror");
		}
		return returnStr;

	}

	public static void setEnv(Map<String, String> newenv) {
		try {
			Class<?> processEnvironmentClass = Class.forName("java.lang.ProcessEnvironment");
			Field theEnvironmentField = processEnvironmentClass.getDeclaredField("theEnvironment");
			theEnvironmentField.setAccessible(true);
			Map<String, String> env = (Map<String, String>) theEnvironmentField.get(null);
			env.putAll(newenv);
			Field theCaseInsensitiveEnvironmentField = processEnvironmentClass
					.getDeclaredField("theCaseInsensitiveEnvironment");
			theCaseInsensitiveEnvironmentField.setAccessible(true);
			Map<String, String> cienv = (Map<String, String>) theCaseInsensitiveEnvironmentField.get(null);
			cienv.putAll(newenv);
		} catch (NoSuchFieldException e) {
			try {
				Class[] classes = Collections.class.getDeclaredClasses();
				Map<String, String> env = System.getenv();
				for (Class cl : classes) {
					if ("java.util.Collections$UnmodifiableMap".equals(cl.getName())) {
						Field field = cl.getDeclaredField("m");
						field.setAccessible(true);
						Object obj = field.get(env);
						Map<String, String> map = (Map<String, String>) obj;
						map.clear();
						map.putAll(newenv);
					}
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public static String getHttpResult(HttpURLConnection connection) throws IOException {
		int code = connection.getResponseCode();

		InputStream serverOut = null;
		BufferedReader in = null;
		try {
			serverOut = connection.getInputStream();
			in = new BufferedReader(new InputStreamReader(serverOut));
			String line = "";
			String out = "";
			while ((line = in.readLine()) != null) {
				out += line;
			}

			if (code >= 200 && code < 300) {
				return out;
			} else {

				log.debug(connection.getResponseMessage());
				throw new IOException("Server out:" + out);
			}
		} finally {
			try {
				in.close();
			} catch (Exception e) {

			}

			try {
				serverOut.close();
			} catch (Exception e) {

			}
		}
	}
}

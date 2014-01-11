package de.dakror.anna.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Dakror
 */
public class Assistant
{
	public static void copyInputStream(InputStream in, OutputStream out) throws IOException
	{
		byte[] buffer = new byte[1024];
		int len = in.read(buffer);
		while (len >= 0)
		{
			out.write(buffer, 0, len);
			len = in.read(buffer);
		}
		in.close();
		out.close();
	}
	
	public static String getDefaultBrowser()
	{
		try
		{
			Process process = Runtime.getRuntime().exec("REG QUERY HKEY_CLASSES_ROOT\\http\\shell\\open\\command");
			Scanner kb = new Scanner(process.getInputStream());
			while (kb.hasNextLine())
			{
				String registry = (kb.nextLine()).replaceAll("\\\\", "/").trim();
				
				Matcher matcher = Pattern.compile("/(?=[^/]*$)(.+?)[.]").matcher(registry);
				if (matcher.find())
				{
					kb.close();
					String defaultBrowser = matcher.group(1);
					defaultBrowser = defaultBrowser.substring(0, 1).toUpperCase() + defaultBrowser.substring(1, defaultBrowser.length());
					return defaultBrowser;
				}
			}
			kb.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return "Unable to get default browser";
	}
}

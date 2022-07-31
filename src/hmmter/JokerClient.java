package hmmter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class JokerClient
{
	boolean m_session = false;

	HashMap<String, String> cookieMap = new HashMap<String, String>();
	String m_cookies = "";
	String lastRedirectUrl = "";

	// HTTP 요청 함수
	public String sendUsingHttp(StringBuilder postData, URL url, Map<String, String> headers, boolean httpCheck, boolean redirect, boolean proxyYn) throws IOException, InterruptedException
	{
		String page = "";

		if( httpCheck )
		{
			HttpURLConnection conn;

			if( proxyYn )
			{
				Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("218.157.154.239", 34001));
				conn = (HttpURLConnection) url.openConnection(proxy);
			}
			else
			{
				conn = (HttpURLConnection) url.openConnection();
			}

			conn.setInstanceFollowRedirects(redirect);

			for( String key : headers.keySet() )
			{
				conn.setRequestProperty(key, headers.get(key));
			}

			// HTTP Request Message에 쿠키 포함
			if( m_session )
			{
				m_cookies = "NFS=2;";

				for( String key : cookieMap.keySet() )
				{
					String cook = key + "=" + cookieMap.get(key) + ";";
					m_cookies = m_cookies + cook;
				}

				System.out.println("COOKIES : " + m_cookies);
				conn.setRequestProperty("Cookie", m_cookies);
			}

			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(true);
			conn.setDefaultUseCaches(true);

			if( postData != null )
			{
				byte[] postDataBytes = postData.toString().getBytes("UTF-8");

				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
				conn.getOutputStream().write(postDataBytes);
			}
			else
			{
				conn.setRequestMethod("GET");
			}

			int code = 0;

			try
			{
				code = conn.getResponseCode();
			}
			catch( Exception eeee )
			{
				eeee.printStackTrace();
				Thread.sleep(3000);
				return sendUsingHttp(null, url, headers, httpCheck, redirect, proxyYn);
			}

			// HTTP Response Message에서 쿠키 획득
			Map<String, List<String>> header = conn.getHeaderFields();

			if( header.containsKey("Set-Cookie") )
			{
				List<String> cookie = header.get("Set-Cookie");

				for( String s : cookie )
				{
					String cookSet = s.substring(0, s.indexOf(";"));
					String cook[] = cookSet.split("=");

					cookieMap.put(cook[0], cook[1]);
				}

				m_session = true;
			}
			else
			{
			}

			if( code != HttpURLConnection.HTTP_OK )
			{ //이때 요청이 보내짐.
				return null;
			}

			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

			String line;

			while( (line = reader.readLine()) != null )
			{
				page += line;
			}
		}
		else
		{
			HttpURLConnection conn;

			if( proxyYn )
			{
				Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("218.157.154.239", 34001));
				conn = (HttpsURLConnection) url.openConnection(proxy);
			}
			else
			{
				conn = (HttpsURLConnection) url.openConnection();
			}

			conn.setInstanceFollowRedirects(redirect);

			for( String key : headers.keySet() )
			{
				conn.setRequestProperty(key, headers.get(key));
			}

			// HTTP Request Message에 쿠키 포함
			if( m_session )
			{
				m_cookies = "NFS=2;";

				for( String key : cookieMap.keySet() )
				{
					String cook = key + "=" + cookieMap.get(key) + ";";
					m_cookies = m_cookies + cook;
				}

				System.out.println("COOKIES : " + m_cookies);
				conn.setRequestProperty("Cookie", m_cookies);
			}
			else
			{
			}

			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(true);
			conn.setDefaultUseCaches(true);

			if( postData != null )
			{
				byte[] postDataBytes = postData.toString().getBytes("UTF-8");

				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
				conn.getOutputStream().write(postDataBytes);
			}
			else
			{
				conn.setRequestMethod("GET");
			}

			int code = 0;

			try
			{
				code = conn.getResponseCode();
			}
			catch( Exception eeee )
			{
				eeee.printStackTrace();
				Thread.sleep(3000);
				return sendUsingHttp(null, url, headers, httpCheck, redirect, proxyYn);
			}

			System.out.println(code);
			// HTTP Response Message에서 쿠키 획득
			Map<String, List<String>> header = conn.getHeaderFields();

			if( header.containsKey("nnbcookie") )
			{
				List<String> nnbcookie = header.get("nnbcookie");

				for( String nnb : nnbcookie )
				{
					System.out.println("nnbcookie_" + nnb);
				}
			}
			else
			{
			}

			if( header.containsKey("requestid") )
			{
				List<String> nnbcookie = header.get("requestid");

				for( String nnb : nnbcookie )
				{
					System.out.println("requestid_" + nnb);
				}
			}
			else
			{
			}

			if( header.containsKey("Set-Cookie") )
			{
				List<String> cookie = header.get("Set-Cookie");

				for( String s : cookie )
				{
					String cookSet = s.substring(0, s.indexOf(";"));

					String cook[] = cookSet.split("=");

					cookieMap.put(cook[0], cook[1]);
				}

				m_session = true;
			}
			else
			{
			}

			if( header.containsKey("set-cookie") )
			{
				List<String> cookie = header.get("set-cookie");

				for( String s : cookie )
				{
					String cookSet = s.substring(0, s.indexOf(";"));
					System.out.println("cookSet2_" + cookSet);

					String cook[] = cookSet.split("=");

					cookieMap.put(cook[0], cook[1]);
				}

				m_session = true;
			}
			else
			{
			}

			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

			String line;

			while( (line = reader.readLine()) != null )
			{
				page += line;
			}

			if( code == HttpURLConnection.HTTP_SEE_OTHER ||
				code == HttpURLConnection.HTTP_MOVED_PERM ||
				code == HttpURLConnection.HTTP_MOVED_TEMP ||
				code == 307 )
			{
				String Location = conn.getHeaderField("Location");

				if( Location.startsWith("/") )
				{
					Location = url.getProtocol() + "://" + url.getHost() + Location;
				}
				System.out.println("@@@@@@@@@@@@@@LOCATION_" + Location);

				lastRedirectUrl = Location;

				if( !redirect )
				{
					return sendUsingHttp(null, new URL(Location), headers, httpCheck, false, true);
				}
				else
				{
				}
			}
		}

		//System.out.println(page);
		return page;
	}
}
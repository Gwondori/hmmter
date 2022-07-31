package hmmter;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


// UA에 따라서 NNB 달라지는지

public class MainTest
{
	public static JokerClient d;

	public static String[] userAgents = new String[]{
			"Mozilla/5.0 (Linux; Android 12; SAMSUNG SM-G998N) AppleWebKit/537.36 (KHTML, like Gecko) SamsungBrowser/16.0 Chrome/92.0.4515.166 Mobile Safari/537.36",
			"Mozilla/5.0 (Linux; Android 11; SM-G781N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.92 Mobile Safari/537.36",
			"Mozilla/5.0 (Linux; Android 11; SM-G991N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.104 Mobile Safari/537.36",
			"Mozilla/5.0 (Linux; Android 10; SAMSUNG SM-G980N) AppleWebKit/537.36 (KHTML, like Gecko) SamsungBrowser/11.0 Chrome/75.0.3770.143 Mobile Safari/537.36",
			"Mozilla/5.0 (Linux; Android 11; SAMSUNG SM-G977N) AppleWebKit/537.36 (KHTML, like Gecko) SamsungBrowser/16.0 Chrome/92.0.4515.166 Mobile Safari/537.36",
			"Mozilla/5.0 (iPhone; CPU iPhone OS 15_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) CriOS/96.0.4664.101 Mobile/15E148 Safari/604.1",
			"Mozilla/5.0 (iPhone; CPU iPhone OS 15_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.1 Mobile/15E148 Safari/604.1",
			"Mozilla/5.0 (iPhone; CPU iPhone OS 15_1 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) CriOS/96.0.4664.101 Mobile/15E148 Safari/604.1",
			"Mozilla/5.0 (iPhone; CPU iPhone OS 14_8 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) CriOS/96.0.4664.101 Mobile/15E148 Safari/604.1",
			"Mozilla/5.0 (iPhone; CPU iPhone OS 14_7 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) CriOS/96.0.4664.101 Mobile/15E148 Safari/604.1",
			"Mozilla/5.0 (iPhone; CPU iPhone OS 14_8 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/14.8 Mobile/15E148 Safari/604.1",
			"Mozilla/5.0 (Linux; Android 11; SM-G977N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.92 Mobile Safari/537.36",
	};

	public static String[] viewPorts = new String[]{
			"384||854",
			"412||914",
			"360||800",
			"360||800",
			"360||760",
			"428||926",
			"428||926",
			"390||844",
			"414||896",
			"375||812",
			"375||812",
			"360||760",
	};

	public static String[] oSs = new String[]{
			"Android",
			"Android",
			"Android",
			"Android",
			"Android",
			"iOS",
			"iOS",
			"iOS",
			"iOS",
			"iOS",
			"iOS",
			"Android",
	};

	public static String traf = "뜨거워도 괜찮아 주방집게||82609863991,가히 엑스틴C밤 아쿠아밤||83189513644,건영 마스크 k94마스크 국산 대형||82925667812,두루마리휴지케이스추천||83616331192,화장실 브러시 및 홀더 세트||83213003395,가성비 3단 등산스틱||83339383666,가성비 볼펜 5종세트||82993935041,엘홀더 a4사이즈||82450969229,사각티슈케이스 디자인||83538831906,저렴한 리퍼제품||27416562148,특이한나무 볼펜추천||82811790674,재밌는 어른 놀래키는 장난감||83163926080,연예인추천샴푸 지성용||83011683455,임산부용 편한 레깅스||12109240031,강아지 유해물질 없는 치약||82252837662,손톱 영양제 가성비 추천||83018917104,30대여자영양제추천||82984165894,40대 홍삼 진액 고급 선물세트||82568788719,반려묘 집들이 선물추천||83254997011,40대남자영양제추천||83433775403,무지 흰색 발목 남자 양말 추천||82504734070,여자빅사이즈티셔츠추천||83121861257,고양이털청소기||83361719872,쿨링팬 노트북 거치대 가성비 추천||83172968455,카페 인테리어 컵 소품 추천||82264062668,우정 팔찌 diy 만들기||83264744018,어린이용 diy 문구 세트||83299368001,책상 다용도 라탄 연필꽂이 추천||82456584774,하늘색남자무지반팔티빅사이즈||83178037554,남자 밴딩 슬렉스 디트로이드 진||82899669329,식물 비타민 영양제 종합||82880991951,필수 아미노산 30대 영양제 추천||82640818894,50대 등산가방 가성비 추천||83455866059,대용량 보조배터리 카카오 프렌즈||83376493285,대용량 고급형 보온 보냉 텀블러 추천||82701492150,온도표시 수압상승 스테인리스 샤워기||83023820678,온도유지 분유 보온병||82659172291,가성비 좋은 원터치 텐트 추천||83195664161,자동차 가죽 키 케이스 클리너||82329163323,차키 브라운 가죽 케이스||83519751704,카카오프렌즈 그립톡 어피치||81409889987,카카오프렌즈 그립톡 라이언||83258796506,유아용 젓가락 실리콘 교정기||83113757828,천연 대리석 도마세트||82482478324,칼갈이 연마석 다이아몬드 숫돌||83136651212,저자극 천연 왁싱||82893458090,카카오 프렌즈 실리콘 휴대폰케이스||83314723386,나이키 손목아대 세트||83215357239,배구 팔꿈치 보호대||82646842301,이마아대추천||83389436960,유치원생 교구 세트||83083106616,자동차 깔깔이||8455408685,매장입구 깔판||83009757922,현관코일깔판||82582336608,20대남성카드케이스추천||82188307074,20대여성카드케이스추천||12991899271,가죽연필통||82322396584,가죽휴대폰케이스||81643048771,2인분 떡볶이 밀키트||83640327499,고급물티슈케이스||83661218222,스테인레스 휴지통||82494264730,철제행거||13430397679,이동식 행거||13430397679,조립식 책상||12023331353,무타공선반||82750222937,무타공벽걸이||83440477426,원룸 빔프로젝터||82835387942,원룸 컴퓨터책상||82568725183,1인가구 청소기||83685575784,천연비누추천||8062152204,물질작살||82860267147,가성비루어||12237077548,가성비 낚시추||82215140900,원투낚시 거치대||82046098138,바다뜰채||81033281841,카본낚시대||80551107477,입문용 텐트||83130148754,입문용 오리발||82251284654,미세먼지 차단 식물||83374550594,실내공기정화식물세트||82115441676,전자파차단 스티커||13295418141,종이받침대||81929370272,책받침대||82864744310,브라이덜샤워용품||80529169324,파티풍선세트||82459108784,쓸데없는선물추천||82856557052,사무실의자방석||80312601076,다도세트추천||82836727848,장판수리키트||83229039886,키보드깔판||82436170060";

	public static List<String> trafSequences = new ArrayList<>();

	public static void main(String[] args)
	{
		int cc = 2;
		int ta1 = 0;
		int ta2 = 0;
		int ta3 = 0;
		int ta4 = 0;

		while( true )
		{
			try
			{
				d = new JokerClient();

				Thread.sleep(1000);

				cc++;

				if( cc == 3 )
				{
					cc = 0;
				}

				Map<String, String> header = new HashMap<>();

				URL url = new URL("http://218.157.154.239/getDevices");
				String response = d.sendUsingHttp(null, url, header, true, false, false);

				System.out.println(response);

				if( response != null && !response.trim().equals("") )
				{
					String[] ports = response.split("\\|\\|");

					System.out.println(ports);

					for( String port : ports )
					{
						/*TODO: Why this code exist?*/
						if( !port.contains("340") )
						{
							continue;
						}

						if( port.contains("340") )
						{
							url = new URL("http://218.157.154.239/ipChange?port=" + port);
							response = d.sendUsingHttp(null, url, header, true, false, false);

							System.out.println("[아이피 변경 시작]");
							while( true )
							{
								Thread.sleep(5000);

								url = new URL("http://218.157.154.239/getDevices");
								response = d.sendUsingHttp(null, url, header, true, false, false);

								if( response.contains(port) )
								{
									break;
								}
								else
								{
									System.out.println("아이피 변경 중...");
								}
							}

							Thread.sleep(5000);
							System.out.println("[아이피 변경 완료]");

							trafSequences = new ArrayList<>();

							int myTurn = new Random().nextInt(12);

							if( cc == 0 )
							{
								for( int j = 0; j < 12; j++ )
								{
									if( j == myTurn )
									{
										trafSequences.add("여성 린넨 크롭 투피스||83276407459");
										System.out.println(j + 1 + "번째_" + "여성 린넨 크롭 투피스||83276407459");
										//trafSequences.add("앙리마티스나무액자세트 81종||83406399808");
										//System.out.println(j+1 + "번째_" + "앙리마티스나무액자세트 81종||83406399808");
									}
									else
									{
										trafSequences.add(traf.split("\\,")[new Random().nextInt(traf.split("\\,").length)]);
									}
								}

								findProduct(true, true, true);
								ta1++;
							}
							else if( cc == 1 )
							{
								for( int j = 0; j < 12; j++ )
								{
									if( j == myTurn )
									{
										trafSequences.add("머라이어 캐리 베스트||83695577073");
										System.out.println(j + 1 + "번째_" + "머라이어 캐리 lp||83695577073");
										//trafSequences.add("업소용가정용함마톤 편수냄비||83247805122");
										//System.out.println(j+1 + "번째_" + "업소용가정용함마톤 편수냄비||83247805122");
									}
									else
									{
										trafSequences.add(traf.split("\\,")[new Random().nextInt(traf.split("\\,").length)]);
									}
								}

								findProduct(true, true, true);
								ta2++;
							}
							else if( cc == 2 )
							{
								for( int j = 0; j < 12; j++ )
								{
									if( j == myTurn )
									{
										trafSequences.add("빌리 아일리시 WHEN WE ALL FALL ASLEEP||83678212935");
										System.out.println(j + 1 + "번째_" + "머라이어 캐리 lp||8281868369557707302779");
									}
									else
									{
										trafSequences.add(traf.split("\\,")[new Random().nextInt(traf.split("\\,").length)]);
									}
								}

								findProduct(true, true, true);
								ta3++;
							}
							else if( cc == 3 )
							{
								for( int j = 0; j < 12; j++ )
								{
									if( j == myTurn )
									{
										trafSequences.add("빌리 아일리시 WHEN WE ALL FALL ASLEEP||83678212935");
										System.out.println(j + 1 + "번째_" + "빌리 아일리시 lp||83678212935");
									}
									else
									{
										trafSequences.add(traf.split("\\,")[new Random().nextInt(traf.split("\\,").length)]);
									}
								}

								findProduct(true, true, true);
								ta4++;
							}

							System.out.println("여성 린넨 크롭 투피스 = " + ta1 + ", 업소용 = " + ta2 + ", 머라이어캐리 = " + ta3 + ", 빌 " + ta4);

							break;
						}
					}
				}
			}
			catch( Exception e )
			{
				e.printStackTrace();
			}
		}
	}

	private static void findProduct(boolean typeO, boolean typeC, boolean typeA) throws InterruptedException
	{
		boolean getNnm = true;
		Map<String, String> header = new HashMap<String, String>();

		int idx = new Random().nextInt(userAgents.length);
		String userAgent = userAgents[idx];

		int viewPortX = Integer.parseInt(viewPorts[idx].split("\\|\\|")[0]) - 50;

		System.out.println("UA_" + userAgent);

		for( int cnt = 0; cnt < 12; cnt++ )
		{
			try
			{
				String traf = trafSequences.get(cnt);

				System.out.println(traf);

				String query = traf.split("\\|\\|")[0];
				String mid = traf.split("\\|\\|")[1];

				header.put("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
				header.put("accept-language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7");
				//header.put("sec-ch-ua", "\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"96\", \"Google Chrome\";v=\"96\"");
				//header.put("sec-ch-ua-mobile", "?0");
				//header.put("sec-fetch-dest", "document");
				//header.put("sec-fetch-mode", "navigate");
				//header.put("sec-fetch-site", "none");
				//header.put("sec-fetch-user", "?1");
				//header.put("upgrade-insecure-requests", "1");
				header.put("user-agent", userAgent);

				URL url = new URL("https://m.naver.com");
				//URL url = new URL("https://www.whatismybrowser.com/detect/what-http-headers-is-my-browser-sending");

				String responses = d.sendUsingHttp(null, url, header, false, false, true);

				Thread.sleep(10000);

				header.put("Accept", "image/avif,image/webp,image/apng,image/svg+xml,image/*,*/*;q=0.8");
				header.put("Cache-control", "no-cache");
				header.put("Connection", "keep-alive");
				header.put("Host", "lcs.naver.com");
				header.put("Pragma", "no-cache");
				header.put("Referer", "https://m.naver.com/");
				header.put("User-Agent", userAgent);
				//header.put("sec-fetch-dest", "image");
				//header.put("sec-fetch-mode", "no-cors");
				//header.put("sec-fetch-site", "same-site");

				if( getNnm )
				{
					long eventTime = System.currentTimeMillis();
					getNnm = false;

					url = new URL("https://lcs.naver.com/m?u=https://m.naver.com/&e=&os=" + oSs[idx] + "&ln=ko-KR&sr=" + viewPorts[idx].split("\\|\\|")[0] + "x" + viewPorts[idx].split("\\|\\|")[1] + "&pr=1&bw=" + viewPorts[idx].split("\\|\\|")[0] + "&bh=" + viewPorts[idx].split("\\|\\|")[1]
							+ "&c=24&j=N&k=Y&i=&ls=&ect=4g&navigationStart=" + eventTime
							+ "&unloadEventStart=" + (eventTime + new Random().nextInt(100))
							+ "&unloadEventEnd=" + (eventTime + new Random().nextInt(100) + 100)
							+ "&fetchStart=" + (eventTime + new Random().nextInt(100))
							+ "&domainLookupStart=" + (eventTime + new Random().nextInt(100))
							+ "&domainLookupEnd=" + (eventTime + new Random().nextInt(100) + 100)
							+ "&connectStart=" + (eventTime + new Random().nextInt(100))
							+ "&connectEnd=" + (eventTime + new Random().nextInt(100) + 100)
							+ "&requestStart=" + (eventTime + new Random().nextInt(100))
							+ "&responseStart=" + (eventTime + new Random().nextInt(100))
							+ "&responseEnd=" + (eventTime + new Random().nextInt(100) + 100)
							+ "&domLoading=" + (eventTime + new Random().nextInt(100))
							+ "&domInteractive=" + (eventTime + new Random().nextInt(100))
							+ "&domContentLoadedEventStart=" + (eventTime + new Random().nextInt(100))
							+ "&domContentLoadedEventEnd=" + (eventTime + new Random().nextInt(100) + 100)
							+ "&domComplete=" + (eventTime + new Random().nextInt(100))
							+ "&loadEventStart=" + (eventTime + new Random().nextInt(100))
							+ "&loadEventEnd=" + (eventTime + new Random().nextInt(100) + 100)
							+ "&first-paint=&first-contentful-paint="
							+ "&ngt=1"
							+ "&sti=m_main_home"
							+ "&pid="
							+ "&ugr=newmain"
							+ "&pmd=home"
							+ "&ts=" + (eventTime + new Random().nextInt(100) + 100)
							+ "&EOU="
					);

					d.sendUsingHttp(null, url, header, false, false, true);
				}

				header.remove("Connection");
				header.remove("Host");
				//header.put("sec-fetch-dest", "document");
				//header.put("sec-fetch-mode", "navigate");
				header.put("cache-control", "no-cache");
				header.put("referer", "https://m.naver.com/");
				header.put("user-agent", userAgent);
				header.put("pragma", "no-cache");
				header.put("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
				url = new URL("https://m.search.naver.com/search.naver?sm=mtp_hty.top&where=m&query=" + URLEncoder.encode(query, "UTF-8"));
				d.sendUsingHttp(null, url, header, false, false, true);
				Thread.sleep(10000);

				header.put("referer", "https://m.search.naver.com/search.naver?sm=mtp_hty.top&where=m&query=" + URLEncoder.encode(query, "UTF-8"));
				url = new URL("https://msearch.shopping.naver.com/search/all?query=" + URLEncoder.encode(query, "UTF-8") + "&bt=-1&frm=MOSCPRO");
				d.sendUsingHttp(null, url, header, false, false, true);
				Thread.sleep(10000);

				boolean finded = false;
				String referer = "https://msearch.shopping.naver.com/search/all?query=" + URLEncoder.encode(query, "UTF-8") + "&bt=-1&frm=MOSCPRO";

				for( int i = 0; i < 7; i++ )
				{
					//header.put("sec-fetch-site", "same-origin");
					header.put("accept", "application/json, text/plain, */*");
					header.put("logic", "PART");
					header.put("referer", referer);

					String searchUrl = makeSearchUrl(query, i);

					System.out.println(searchUrl);
					referer = searchUrl;

					url = new URL(searchUrl);

					String response = d.sendUsingHttp(null, url, header, false, false, true);
					Thread.sleep(1150);

					JsonParser parser = new JsonParser();

					JsonObject json = parser.parse(response).getAsJsonObject();

					JsonObject shoppingResult = json.get("shoppingResult").getAsJsonObject();
					JsonArray products = shoppingResult.get("products").getAsJsonArray();

					String zzimParam = "";

					for( int j = 0; j < products.size(); j++ )
					{
						JsonObject jobj = products.get(j).getAsJsonObject();
						String mallPid = jobj.get("mallProductId").getAsString();

						if( j == 0 )
						{
							zzimParam = mallPid;
						}
						else
						{
							zzimParam = zzimParam + "," + mallPid;
						}
					}

					zzimParam = zzimParam + "&chpid=,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,";

					for( int j = 0; j < products.size(); j++ )
					{
						JsonObject jobj = products.get(j).getAsJsonObject();

						String id = jobj.get("id").getAsString();

						if( id.equals(mid) )
						{
							finded = true;

							String imageUrl = jobj.get("imageUrl").getAsString();
							String mallPid = jobj.get("mallProductId").getAsString();
							String mallSeq = jobj.get("mallNo").getAsString();
							String crUrl = jobj.get("crUrl").getAsString();

							header.put("referer", searchUrl);
							referer = searchUrl;

							String recentViewUrl = "https://msearch.shopping.naver.com/api/recent-viewed?nvMid=" + mid + "&tr=slsl&mallPid=" + mallPid + "&mallSeq=" + mallSeq;

							Map<String, String> zzimHeader = new HashMap<String, String>();

							zzimHeader.put("accept", "application/json, text/plain, */*");
							zzimHeader.put("accept-language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7");
							zzimHeader.put("cache-control", "no-cache");
							zzimHeader.put("pragma", "no-cache");
							zzimHeader.put("referer", recentViewUrl);
							//zzimHeader.put("sec-ch-ua-mobile", "?0");
							//zzimHeader.put("sec-fetch-dest", "empty");
							//zzimHeader.put("sec-fetch-mode", "cors");
							//zzimHeader.put("sec-fetch-site", "same-origin");
							zzimHeader.put("user-agent", userAgent);

							String zzimUrl = "https://msearch.shopping.naver.com/api/product-zzim/products?nvMid=" + zzimParam;

							url = new URL(zzimUrl);
							response = d.sendUsingHttp(null, url, zzimHeader, false, false, true);

							//System.out.println("zzim_" + response);

							Map<String, String> picHeader = new HashMap<String, String>();

							picHeader.put("accept", "image/avif,image/webp,image/apng,image/svg+xml,image/*,*/*;q=0.8");
							picHeader.put("accept-language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7");
							picHeader.put("cache-control", "no-cache");
							picHeader.put("pragma", "no-cache");
							picHeader.put("referer", recentViewUrl);
							//picHeader.put("sec-ch-ua-mobile", "?0");
							//picHeader.put("sec-fetch-dest", "image");
							//picHeader.put("sec-fetch-mode", "no-cors");
							//picHeader.put("sec-fetch-site", "cross-site");
							picHeader.put("user-agent", userAgent);

							String picUrl = imageUrl + "?type=f200";

							url = new URL(picUrl);
							System.out.println("picUrl_" + picUrl);

							Thread.sleep(7200);

							response = d.sendUsingHttp(null, url, picHeader, false, false, true);

							header.remove("logic");
							//header.put("sec-fetch-dest", "empty");
							//header.put("sec-fetch-mode", "cors");
							//header.put("sec-fetch-site", "same-origin");

							Thread.sleep(4500);
							url = new URL(recentViewUrl);

							if( typeO )
							{
								System.out.println("@@@@@@@recentView_ " + recentViewUrl);
								response = d.sendUsingHttp(null, url, header, false, false, true);
								//System.out.println(response);
							}

							Map<String, String> ccHeader = new HashMap<String, String>();

							ccHeader.put("Accept", "image/avif,image/webp,image/apng,image/svg+xml,image/*,*/*;q=0.8");
							ccHeader.put("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7");
							ccHeader.put("Cache-Control", "no-cache");
							ccHeader.put("Connection", "keep-alive");
							ccHeader.put("Host", "cc.naver.com");
							ccHeader.put("Pragma", "no-cache");
							ccHeader.put("Referer", searchUrl);
							//ccHeader.put("sec-ch-ua", "\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"96\", \"Google Chrome\";v=\"96\"");
							//ccHeader.put("sec-ch-ua-mobile", "?0");
							//ccHeader.put("Sec-Fetch-Dest", "image");
							//ccHeader.put("Sec-Fetch-Mode", "no-cors");
							//ccHeader.put("Sec-Fetch-Site", "same-site");
							ccHeader.put("User-Agent", userAgent);

							if( typeC )
							{
								int r = ((i * 10) + j) + 1;
								int py = 300 + new Random().nextInt(150) + (j * 100);
								int px = (new Random().nextInt(viewPortX) + 50);

								long time = System.currentTimeMillis();
								int sy = 100 + new Random().nextInt(py);
								String ccUrl = "https://cc.naver.com/cc?a=lst*N.item&i=" + mid + "&g=undefined&r=" + r + "&bw=" + viewPortX + "&px=" + px + "&py=" + py + "&sx=" + px + "&sy=" + sy + "&m=0&u=" + crUrl + "&time=" + time + "&ssc=m.shopping.all&q=" + URLEncoder.encode(query, "UTF-8") + "&p=" + "hP/" + generatedString(122, 16) + "-" + generatedString(64, 6) + "&s=" + generatedString(122, 24);

								url = new URL(ccUrl);
								System.out.println("@@@@@@@CC_" + url);
								response = d.sendUsingHttp(null, url, ccHeader, false, false, true);

								System.out.println(response);

								String clickUrl = "https://cc.naver.com/cc?a=lst*N.item&i=" + mid + "&g=undefined&r=" + r + "&ts=" + time + "&ssc=m.shopping.all" + "&location=" + searchUrl;

								header.put("accept", "image/avif,image/webp,image/apng,image/svg+xml,image/*,*/*;q=0.8");
								//header.put("sec-fetch-dest", "empty");
								//header.put("sec-fetch-mode", "cors");
								//header.put("sec-fetch-site", "same-origin");

								url = new URL(clickUrl);
								System.out.println("@@@@@@@CLICK_" + url);
								response = d.sendUsingHttp(null, url, header, false, false, true);

								System.out.println(response);
							}

							header.put("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
							//header.put("sec-fetch-dest", "document");
							//	header.put("sec-fetch-mode", "navigate");
							//header.put("sec-fetch-site", "same-origin");

							if( typeA )
							{
								url = new URL(crUrl);
								System.out.println("@@@@@@@CR_" + url);
								response = d.sendUsingHttp(null, url, header, false, false, true);

								Pattern p = Pattern.compile("(window.__PRELOADED_STATE__=)(.*?)(</script>)");
								Matcher m = p.matcher(response);

								if( m.find() )
								{
									//https://m.smartstore.naver.com/i/v1/product-logs/5230906940
									String shopJson = m.group(2);

									JsonParser parser2 = new JsonParser();
									JsonElement tradeElement = parser2.parse(shopJson);

									JsonObject obj = tradeElement.getAsJsonObject();
									JsonObject product = obj.get("product").getAsJsonObject();
									JsonObject a = product.get("A").getAsJsonObject();
									String productNo = a.get("productNo").getAsString();
									String product_id = a.get("id").getAsString();
									String channelServiceType = a.get("channelServiceType").getAsString();
									String planNo = "";
									String tr = "slsl";

									try
									{

										planNo = a.get("planNo").getAsString();

									}
									catch( Exception e )
									{

									}

									JsonObject category = a.get("category").getAsJsonObject();
									JsonObject channel = a.get("channel").getAsJsonObject();
									JsonObject product_logs_obj = new JsonObject();

									product_logs_obj.add("category", category);
									product_logs_obj.add("channel", channel);

									product_logs_obj.addProperty("channelServiceType", channelServiceType);
									product_logs_obj.addProperty("id", product_id);
									product_logs_obj.addProperty("planNo", planNo);
									product_logs_obj.addProperty("referer", referer);
									product_logs_obj.addProperty("tr", tr);

									Map<String, String> plogHeader = new HashMap<String, String>();

									plogHeader.put("accept", "application/json, text/plain, */*, application/json");
									plogHeader.put("accept-language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7");
									plogHeader.put("cache-control", "no-cache");
									plogHeader.put("content-type", "application/json");
									plogHeader.put("origin", "https://m.smartstore.naver.com");
									plogHeader.put("pragma", "no-cache");
									plogHeader.put("referer", d.lastRedirectUrl);
									//plogHeader.put("sec-ch-ua-mobile", "?0");
									//plogHeader.put("sec-fetch-dest", "empty");
									//plogHeader.put("sec-fetch-mode", "cors");
									//plogHeader.put("sec-fetch-site", "same-origin");
									plogHeader.put("user-agent", userAgent);
									System.out.println(product_logs_obj.toString());
									System.out.println("autoRedirectUrl_" + d.lastRedirectUrl);

									url = new URL("https://m.smartstore.naver.com/i/v1/product-logs/" + product_id);

									StringBuilder postData = new StringBuilder();

									postData.append(product_logs_obj.toString());

									response = d.sendUsingHttp(postData, url, plogHeader, false, false, true);

									//System.out.println("@@LOG_RESULT=" + response);

									url = new URL("https://m.smartstore.naver.com/i/v1/products/" + product_id + "/contents/" + productNo + "/MOBILE");

									plogHeader.put("accept", "application/json, text/plain, */*");
									plogHeader.remove("origin");
									response = d.sendUsingHttp(null, url, plogHeader, false, false, true);
									//System.out.println("@@product contents_RESULT=" + response);
								}
								else
								{
									System.out.println("not found");
								}
							}

							break;
						}
					}

					if( finded )
					{
						break;
					}
				}

				if( !finded )
				{
					System.out.println("못찾");
				}
			}
			catch( Exception e )
			{
				e.printStackTrace();
			}

			Thread.sleep(10000);
		}
	}

	private static String generatedString(int rightLimit, int targetStringLength)
	{
		int leftLimit = 48; // numeral '0'
		Random random = new Random();

		String generatedString = random.ints(leftLimit, rightLimit + 1)
				.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
				.limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
				.toString();

		return generatedString;
	}

	private static String makeSearchUrl(String query, int pagingIndex) throws UnsupportedEncodingException
	{
		return "https://msearch.shopping.naver.com/api/search/all?frm=NVSHPAG&origQuery=" + URLEncoder.encode(query, "UTF-8") + "&pagingIndex=" + pagingIndex + "&pagingSize=40&productSet=total&query=" + URLEncoder.encode(query, "UTF-8") + "&sort=rel&viewType=lst";
	}
}

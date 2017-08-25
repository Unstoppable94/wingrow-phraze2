package com.winhong.plugins.cicd.view;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.winhong.plugins.cicd.system.Config;
import com.winhong.plugins.cicd.system.InnerConfig;
import com.winhong.plugins.cicd.view.innerData.RssBuild;

public class JenkinsRss {

	
	/**
	 * 所有build
	 */
	public final static String allRss="/rssAll";
	
	/**
	 * 失败build
	 */
	public final static String failRss="/rssFailed";
	
	/**
	 * 每个JOB的最后一次build
	 */
	public final static String rssLatest="/rssLatest";
	
	
	public JenkinsRss() {
		// TODO Auto-generated constructor stub
	}

	
	public  static List<SyndEntry> getRss(String url) throws IllegalArgumentException, MalformedURLException, FeedException, IOException{
		
		SyndFeedInput input = new SyndFeedInput();
		//TODO  add user and password
 		SyndFeed feed = input.build(new XmlReader(new URL(url)));
		
		
		return feed.getEntries();

//		private static Document loadTestDocument(String url) throws Exception {
//	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//	        factory.setNamespaceAware(true);
//	        return factory.newDocumentBuilder().parse(new URL(url).openStream());
//	    }
//
//	    public static void main(String[] args) throws Exception {
//	        Document doc = loadTestDocument("http://www.enetpulse.com/wp-content/uploads/sample_xml_feed_enetpulse_soccer.xml");
//	        System.out.println(doc);
//	        doc = loadTestDocument("http://localhost/array.xml");
//	        System.out.println(doc);
//	    }
	    
	}
	
	
	public static ArrayList<RssBuild>getInfo(String url) throws IllegalArgumentException, MalformedURLException, FeedException, IOException, InstantiationException, IllegalAccessException{
		
 		
		String rssurl=Config.getJenkinsConfig().getUrl()+url;
				
		 List<SyndEntry> list=getRss(rssurl);
		 ArrayList<RssBuild> builds=new ArrayList<RssBuild>();
		 for(int i=0;i<list.size();i++){
			 SyndEntry entry = list.get(i);
			 String title = entry.getTitle();
			 Date publish = entry.getPublishedDate();
			 String s[]=title.substring(0,title.indexOf('(')).split("#");
			 
 			 RssBuild rss = new RssBuild(s[0].trim(),Integer.parseInt(s[1].trim()),
 					 entry.getLink(),entry.getPublishedDate(),entry.getUpdatedDate());
			 
 			builds.add(rss);
			 
		 }
		return builds;
	}
	
	
}

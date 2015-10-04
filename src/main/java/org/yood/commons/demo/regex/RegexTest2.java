package org.yood.commons.demo.regex;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest2 {

	public static void main(String[] args) throws IOException {

		List<String> list = getMailsByWeb();
		for(String mail : list){
			System.out.println(mail);
		}
	}
	
	public static List<String> getMailsByWeb() throws IOException {
		URL url = new URL("http://192.168.1.100:8080/myweb/mail.html");
		BufferedReader bufIn = new BufferedReader(new InputStreamReader(url.openStream()));
		String mail_regex = "\\w+@\\w+(\\.\\w+)+";
		List<String> list = new ArrayList<>();
		Pattern p = Pattern.compile(mail_regex);
		String line;
		while((line=bufIn.readLine())!=null){
			Matcher m = p.matcher(line);
			while(m.find()){
				list.add(m.group());
			}
		}
		return list;
	}

	public static List<String>  getMails() throws IOException{
		BufferedReader bufr = new BufferedReader(new FileReader("c:\\mail.html"));
		String mail_regex = "\\w+@\\w+(\\.\\w+)+";
		List<String> list = new ArrayList<>();
		Pattern p = Pattern.compile(mail_regex);
		String line;
		while((line=bufr.readLine())!=null){
			Matcher m = p.matcher(line);
			while(m.find()){
				list.add(m.group());
			}
		}
		return list;
	}
}

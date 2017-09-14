package com.winhong.jetty;

import com.winhong.plugins.cicd.tool.Encryptor;
import com.winhong.plugins.cicd.tool.RandomString;

public class EncrytMain {

	public static void main(String[] args) {
		if (args.length!=1) {
			System.out.println("encrypt input ,so please only input plain text，只需要输入明文");
			System.out.println("java -cp cicd-jetty.jar com.winhong.jetty.EncrypMain plainText");

			return;
		}
		RandomString.init(16);
		System.out.println(Encryptor.encrypt(args[0]));
	}

}

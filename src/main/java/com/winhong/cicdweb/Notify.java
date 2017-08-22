package com.winhong.cicdweb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winhong.plugins.cicd.action.NotifyAction;

import sun.misc.Signal;
import sun.misc.SignalHandler;

public class Notify {
	private static final Logger log = LoggerFactory.getLogger(NotifyAction.class);

	private static boolean running = true;
	public static void init() {
//		Runtime.getRuntime().addShutdownHook(new Thread() {
//			public void run() {
//			//	System.out.println("reached point of no return ...");
//			}
//		});

		SignalHandler handler = new SignalHandler() {
			public void handle(Signal sig) {
				if (running) {
					running = false;
					System.out.println("Signal " + sig);
					System.out.println("Shutting down ...");
				} else {
					// only on the second attempt do we exit
					System.out.println("shutdown interrupted!");
					System.exit(0);
				}
			}
		};
		Signal.handle(new Signal("INT"), handler);
		Signal.handle(new Signal("TERM"), handler);
	}

	public static void main(String[] args) {

		init();

		int sleepminutes = 5;
		if (args.length > 0) {
			sleepminutes = Integer.parseInt(args[0]);

		}

		while (true) {
			try {
				NotifyAction.checkProjectStatus();
				for (int i = -0; i < sleepminutes; i++) {
					if (running == false)
						System.exit(0);
					Thread.sleep(60 * 1000);
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}

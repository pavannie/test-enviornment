package com.misys.listener.daemon;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class RunDaemon  extends Thread implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) 
	{

		int count = 0;
		while (!false)
		{
			// move the seconds to miliseconds
			try
			{

				synchronized (this)
				{
					this.wait(3000);
				}

			}
			catch (InterruptedException e)
			{
				// this is a normal situation.
				// the DaemonFactory may want to stop this thread form
				// sleeping and call interrupt() on this thread.
			}
			MessageSender msgSender = new MessageSender();
			msgSender.run();
			
		}
	}

}

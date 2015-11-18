/**
 * -----------------------------------------------------------------------
 *     Copyright  2010 ShepHertz Technologies Pvt Ltd. All rights reserved.
 * -----------------------------------------------------------------------
 */

package chatapp;

import com.shephertz.app42.server.AppWarpServer;

/**
 * The Class Main.
 */
public class Main {
    
    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
		String appconfigPath = System.getProperty("user.dir")+System.getProperty("file.separator")+"AppConfig.json";
		boolean started = AppWarpServer.start(new ChatServerAdaptor(), appconfigPath);
		System.out.println("Server Started Successfully");
    }
}

/**
 * -----------------------------------------------------------------------
 *     Copyright  2010 ShepHertz Technologies Pvt Ltd. All rights reserved.
 * -----------------------------------------------------------------------
 */

package chatapp;

import com.shephertz.app42.server.idomain.BaseServerAdaptor;
import com.shephertz.app42.server.idomain.IZone;

/**
 *  * @author Vishnu Garg
 * The Class ChatServerAdaptor.
 */
public class ChatServerAdaptor extends BaseServerAdaptor{
    
    /* (non-Javadoc)
     * @see com.shephertz.app42.server.idomain.BaseServerAdaptor#onZoneCreated(com.shephertz.app42.server.idomain.IZone)
     */
    @Override
    public void onZoneCreated(IZone zone)
    {   
        System.out.println("Zone Created " + zone.getName() + " with key " + zone.getAppKey());
        zone.setAdaptor(new ChatZoneAdaptor(zone));
    }
}

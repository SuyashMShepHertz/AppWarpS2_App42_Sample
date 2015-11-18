/**
 * -----------------------------------------------------------------------
 *     Copyright  2010 ShepHertz Technologies Pvt Ltd. All rights reserved.
 * -----------------------------------------------------------------------
 */

package chatapp;

import com.shephertz.app42.paas.sdk.java.App42API;
import com.shephertz.app42.paas.sdk.java.App42CallBack;
import com.shephertz.app42.paas.sdk.java.user.UserService;
import com.shephertz.app42.server.idomain.BaseZoneAdaptor;
import com.shephertz.app42.server.idomain.HandlingResult;
import com.shephertz.app42.server.idomain.IRoom;
import com.shephertz.app42.server.idomain.IUser;
import com.shephertz.app42.server.idomain.IZone;
import com.shephertz.app42.server.message.WarpResponseResultCode;

/**
 *  * @author Vishnu Garg
 * The Class ChatZoneAdaptor.
 */
public class ChatZoneAdaptor extends BaseZoneAdaptor {
	
	/** The App42 api key. */
	private String App42APIKey = "Your App42 API Key";
	
	/** The App42 secret key. */
	private String App42SecretKey = "Your App42 Secret Key";

	/** The user service. */
	private UserService userService = null;
	
	/** The zone. */
	private IZone zone = null;
	
	/**
	 * Instantiates a new chat zone adaptor.
	 *
	 * @param _zone the _zone
	 */
	public ChatZoneAdaptor(IZone _zone){
		super();
		App42API.initialize(App42APIKey,App42SecretKey);  
		userService = App42API.buildUserService();   
		zone = _zone;
	}

    /* (non-Javadoc)
     * @see com.shephertz.app42.server.idomain.BaseZoneAdaptor#onAdminRoomAdded(com.shephertz.app42.server.idomain.IRoom)
     */
    @Override
    public void onAdminRoomAdded(IRoom room)
    {
        System.out.println("Room Created " + room.getName() + " with ID " + room.getId() );
        room.setAdaptor(new ChatRoomAdaptor());
    }  
	
	/* (non-Javadoc)
	 * @see com.shephertz.app42.server.idomain.BaseZoneAdaptor#handleAddUserRequest(com.shephertz.app42.server.idomain.IUser, java.lang.String, com.shephertz.app42.server.idomain.HandlingResult)
	 */
	@Override
	public void handleAddUserRequest(final IUser user, String authData, HandlingResult result)
	{
		result.code = WarpResponseResultCode.AUTH_PENDING;
		//System.out.println("AuthData : "+authData);
		System.out.println("Going to call authenticate");
		userService.authenticate(user.getName(), authData, new App42CallBack() {
			@Override
			public void onSuccess(Object response) {
				System.out.println("Successfully Authenticated");
				zone.sendAddUserResponse(user, WarpResponseResultCode.SUCCESS, "Auth success on server");  
			}
					
			@Override
			public void onException(Exception ex) {
				System.out.println("Failure in Authenticating");	
				zone.sendAddUserResponse(user, WarpResponseResultCode.AUTH_ERROR, "Auth failed on server");  				
			}
		});
		System.out.println("authenticate called");
	}
}

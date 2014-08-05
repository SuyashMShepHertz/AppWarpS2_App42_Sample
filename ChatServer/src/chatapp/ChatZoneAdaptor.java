/**
 *
 * @author Suyash Mohan
 */
package chatapp;

import com.shephertz.app42.server.idomain.BaseZoneAdaptor;
import com.shephertz.app42.server.idomain.HandlingResult;
import com.shephertz.app42.server.idomain.IUser;
import com.shephertz.app42.server.idomain.IRoom;
import com.shephertz.app42.server.idomain.IZone;
import com.shephertz.app42.server.message.WarpResponseResultCode;

import com.shephertz.app42.paas.sdk.java.App42API;  
import com.shephertz.app42.paas.sdk.java.user.User;   
import com.shephertz.app42.paas.sdk.java.user.UserService; 
import com.shephertz.app42.paas.sdk.java.App42CallBack;

public class ChatZoneAdaptor extends BaseZoneAdaptor {
	
	private String App42APIKey = "b29f4030aba3b2bc7002c4eae6815a4130c862c386e43ae2a0a092b27de1c5af";
	private String App42SecretKey = "bf45f27e826039754f8dda659166d59ffb7b9dce830ac51d6e6b576ae4b26f7e";

	private UserService userService = null;
	private IZone zone = null;
	
	public ChatZoneAdaptor(IZone _zone){
		super();
		App42API.initialize(App42APIKey,App42SecretKey);  
		userService = App42API.buildUserService();   
		zone = _zone;
	}

    @Override
    public void onAdminRoomAdded(IRoom room)
    {
        System.out.println("Room Created " + room.getName() + " with ID " + room.getId() );
        room.setAdaptor(new ChatRoomAdaptor());
    }  
	
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

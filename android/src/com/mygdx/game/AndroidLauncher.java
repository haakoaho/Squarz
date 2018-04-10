package com.mygdx.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesActivityResultCodes;
import com.google.android.gms.games.GamesCallbackStatusCodes;
import com.google.android.gms.games.GamesClient;
import com.google.android.gms.games.InvitationsClient;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayersClient;
import com.google.android.gms.games.RealTimeMultiplayerClient;
import com.google.android.gms.games.multiplayer.Invitation;
import com.google.android.gms.games.multiplayer.Multiplayer;
import com.google.android.gms.games.multiplayer.Participant;
import com.google.android.gms.games.multiplayer.realtime.OnRealTimeMessageReceivedListener;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.realtime.RoomStatusUpdateCallback;
import com.google.android.gms.games.multiplayer.realtime.RoomUpdateCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.mygdx.game.model.MultiplayerInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;


public class AndroidLauncher extends AndroidApplication implements MultiplayerInterface {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mGoogleSignInClient = GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);


		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useWakelock = true;

		Squarz game = new Squarz(this);
		initialize(game, config);
	}

	    /*
     * API INTEGRATION SECTION. This section contains the code that integrates
     * the game with the Google Play game services API.
     */

	final static String TAG = "Squarz";
	private Activity MainActivity = this;
	View gameView;

	// Request codes for the UIs that we show with startActivityForResult:
	final static int RC_SELECT_PLAYERS = 10000;
	final static int RC_INVITATION_INBOX = 10001;
	final static int RC_WAITING_ROOM = 10002;

	// Request code used to invoke sign in user interactions.
	private static final int RC_SIGN_IN = 9001;

	// Client used to sign in with Google APIs
	private GoogleSignInClient mGoogleSignInClient = null;

	// Client used to interact with the real time multiplayer system.
	private RealTimeMultiplayerClient mRealTimeMultiplayerClient = null;

	// Client used to interact with the Invitation system.
	private InvitationsClient mInvitationsClient = null;

	// Room ID where the currently active game is taking place; null if we're
	// not playing.
	String mRoomId = null;

	// Holds the configuration of the current room.
	RoomConfig mRoomConfig;

	// Are we playing in multiplayer mode?
	boolean mMultiplayer = false;

	// The participants in the currently active game
	ArrayList<Participant> mParticipants = null;

	// My participant ID in the currently active game
	String mMyId = null;

	// If non-null, this is the id of the invitation we received via the
	// invitation listener
	String mIncomingInvitationId = null;

	//opponent's moves
	private Queue<Byte> moves = new LinkedList<Byte>() ;







	// Check the sample to ensure all placeholder ids are are updated with real-world values.
	// This is strictly for the purpose of the samples; you don't need this in a production
	// application.
	private void checkPlaceholderIds() {
		StringBuilder problems = new StringBuilder();

		if (getPackageName().startsWith("com.google.")) {
			problems.append("- Package name start with com.google.*\n");
		}

		for (Integer id : new Integer[]{R.string.app_id}) {

			String value = getString(id);

			if (value.startsWith("YOUR_")) {
				// needs replacing
				problems.append("- Placeholders(YOUR_*) in ids.xml need updating\n");
				break;
			}
		}

		if (problems.length() > 0) {
			problems.insert(0, "The following problems were found:\n\n");

			problems.append("\nThese problems may prevent the app from working properly.");
			problems.append("\n\nSee the TODO window in Android Studio for more information");
			(new AlertDialog.Builder(this)).setMessage(problems.toString())
					.setNeutralButton(android.R.string.ok, null).create().show();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG, "onResume()");

		// Since the state of the signed in user can change when the activity is not active
		// it is recommended to try and sign in silently from when the app resumes.
		signInSilently();
	}


	public Queue<Byte> popMoves(){
		Queue<Byte> tempQueue = moves;
		moves.clear();
		return tempQueue;
	}


	@Override


	public void startQuickGame() {
		// quick-start a game with 1 randomly selected opponent
		final int MIN_OPPONENTS = 1, MAX_OPPONENTS = 1;
		Bundle autoMatchCriteria = RoomConfig.createAutoMatchCriteria(MIN_OPPONENTS,
				MAX_OPPONENTS, 0);

		mRoomConfig = RoomConfig.builder(mRoomUpdateCallback)
				.setOnMessageReceivedListener(mOnRealTimeMessageReceivedListener)
				.setRoomStatusUpdateCallback(mRoomStatusUpdateCallback)
				.setAutoMatchCriteria(autoMatchCriteria)
				.build();
		mRealTimeMultiplayerClient.create(mRoomConfig);
	}



	/**
	 * Start a sign in activity.  To properly handle the result, call tryHandleSignInResult from
	 * your Activity's onActivityResult function
	 */
	public void startSignInIntent() {
		startActivityForResult(mGoogleSignInClient.getSignInIntent(), RC_SIGN_IN);
	}

	/**
	 * Try to sign in without displaying dialogs to the user.
	 * <p>
	 * If the user has already signed in previously, it will not show dialog.
	 */
	public void signInSilently() {
		Log.d(TAG, "signInSilently()");

		mGoogleSignInClient.silentSignIn().addOnCompleteListener(this,
				new OnCompleteListener<GoogleSignInAccount>() {
					@Override
					public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
						if (task.isSuccessful()) {
							Log.d(TAG, "signInSilently(): success");
							onConnected(task.getResult());
						} else {
							Log.d(TAG, "signInSilently(): failure", task.getException());
							onDisconnected();
						}
					}
				});
	}

	public void signOut() {
		Log.d(TAG, "signOut()");

		mGoogleSignInClient.signOut().addOnCompleteListener(this,
				new OnCompleteListener<Void>() {
					@Override
					public void onComplete(@NonNull Task<Void> task) {

						if (task.isSuccessful()) {
							Log.d(TAG, "signOut(): success");
						} else {
							handleException(task.getException(), "signOut() failed!");
						}

						onDisconnected();
					}
				});
	}

	/**
	 * Since a lot of the operations use tasks, we can use a common handler for whenever one fails.
	 *
	 * @param exception The exception to evaluate.  Will try to display a more descriptive reason for the exception.
	 * @param details   Will display alongside the exception if you wish to provide more details for why the exception
	 *                  happened
	 */
	private void handleException(Exception exception, String details) {
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {

		if (requestCode == RC_SIGN_IN) {

			Task<GoogleSignInAccount> task =
					GoogleSignIn.getSignedInAccountFromIntent(intent);

			try {
				GoogleSignInAccount account = task.getResult(ApiException.class);
				onConnected(account);
			} catch (ApiException apiException) {
				String message = apiException.getMessage();
				if (message == null || message.isEmpty()) {
					//error
				}

				onDisconnected();

				new AlertDialog.Builder(this)
						.setMessage(message)
						.setNeutralButton(android.R.string.ok, null)
						.show();
			}
		} else if (requestCode == RC_SELECT_PLAYERS) {
			// we got the result from the "select players" UI -- ready to create the room
			handleSelectPlayersResult(resultCode, intent);

		} else if (requestCode == RC_INVITATION_INBOX) {
			// we got the result from the "select invitation" UI (invitation inbox). We're
			// ready to accept the selected invitation:

			//handleInvitationInboxResult(resultCode, intent);

		} else if (requestCode == RC_WAITING_ROOM) {
			// we got the result from the "waiting room" UI.
			if (resultCode == Activity.RESULT_OK) {
				// ready to start playing
				Log.d(TAG, "Starting game (waiting room returned OK).");
				//startGame(true);
			} else if (resultCode == GamesActivityResultCodes.RESULT_LEFT_ROOM) {
				// player indicated that they want to leave the room
				leaveRoom();
			} else if (resultCode == Activity.RESULT_CANCELED) {
				// Dialog was cancelled (user pressed back key, for instance). In our game,
				// this means leaving the room too. In more elaborate games, this could mean
				// something else (like minimizing the waiting room UI).
				leaveRoom();
			}
		}
		super.onActivityResult(requestCode, resultCode, intent);
	}

	// Handle the result of the "Select players UI" we launched when the user clicked the
	// "Invite friends" button. We react by creating a room with those players.

	private void handleSelectPlayersResult(int response, Intent data) {
		if (response != Activity.RESULT_OK) {
			Log.w(TAG, "*** select players UI cancelled, " + response);
			return;
		}

		Log.d(TAG, "Select players UI succeeded.");

		// get the invitee list
		final ArrayList<String> invitees = data.getStringArrayListExtra(Games.EXTRA_PLAYER_IDS);
		Log.d(TAG, "Invitee count: " + invitees.size());

		// get the automatch criteria
		Bundle autoMatchCriteria = null;
		int minAutoMatchPlayers = 1;
		int maxAutoMatchPlayers = 1;
		if (minAutoMatchPlayers > 0 || maxAutoMatchPlayers > 0) {
			autoMatchCriteria = RoomConfig.createAutoMatchCriteria(
					minAutoMatchPlayers, maxAutoMatchPlayers, 0);
			Log.d(TAG, "Automatch criteria: " + autoMatchCriteria);
		}

		// create the room
		Log.d(TAG, "Creating room...");

		mRoomConfig = RoomConfig.builder(mRoomUpdateCallback)
				.addPlayersToInvite(invitees)
				.setOnMessageReceivedListener(mOnRealTimeMessageReceivedListener)
				.setRoomStatusUpdateCallback(mRoomStatusUpdateCallback)
				.setAutoMatchCriteria(autoMatchCriteria).build();
		mRealTimeMultiplayerClient.create(mRoomConfig);
		Log.d(TAG, "Room created, waiting for it to be ready...");
	}



	// Activity is going to the background. We have to leave the current room.
	@Override
	public void onStop() {
		Log.d(TAG, "**** got onStop");

		// if we're in a room, leave it.
		leaveRoom();

		// stop trying to keep the screen on
		super.onStop();
	}


	// Leave the room.
	void leaveRoom() {
		Log.d(TAG, "Leaving room.");
		if (mRoomId != null) {
			mRealTimeMultiplayerClient.leave(mRoomConfig, mRoomId)
					.addOnCompleteListener(new OnCompleteListener<Void>() {
						@Override
						public void onComplete(@NonNull Task<Void> task) {
							mRoomId = null;
							mRoomConfig = null;
						}
					});
		} else {
		}
	}

	// Show the waiting room UI to track the progress of other players as they enter the
	// room and get connected.
	void showWaitingRoom(Room room) {
		// minimum number of players required for our game
		// For simplicity, we require everyone to join the game before we start it
		// (this is signaled by Integer.MAX_VALUE).
		final int MIN_PLAYERS = Integer.MAX_VALUE;
		mRealTimeMultiplayerClient.getWaitingRoomIntent(room, MIN_PLAYERS)
				.addOnSuccessListener(new OnSuccessListener<Intent>() {
					@Override
					public void onSuccess(Intent intent) {
						// show waiting room UI
						startActivityForResult(intent, RC_WAITING_ROOM);
					}
				})
				.addOnFailureListener(createFailureListener("There was a problem getting the waiting room!"));
	}



    /*
     * CALLBACKS SECTION. This section shows how we implement the several games
     * API callbacks.
     */

	private String mPlayerId;

	// The currently signed in account, used to check the account has changed outside of this activity when resuming.
	GoogleSignInAccount mSignedInAccount = null;

	private void onConnected(GoogleSignInAccount googleSignInAccount) {
		Log.d(TAG, "onConnected(): connected to Google APIs");
		if (mSignedInAccount != googleSignInAccount) {

			mSignedInAccount = googleSignInAccount;

			// update the clients
			mRealTimeMultiplayerClient = Games.getRealTimeMultiplayerClient(this, googleSignInAccount);
			mInvitationsClient = Games.getInvitationsClient(this, googleSignInAccount);

			// get the playerId from the PlayersClient
			PlayersClient playersClient = Games.getPlayersClient(this, googleSignInAccount);
			playersClient.getCurrentPlayer()
					.addOnSuccessListener(new OnSuccessListener<Player>() {
						@Override
						public void onSuccess(Player player) {
							mPlayerId = player.getPlayerId();

						}
					})
					.addOnFailureListener(createFailureListener("There was a problem getting the player id!"));
		}


		// get the invitation from the connection hint
		// Retrieve the TurnBasedMatch from the connectionHint
		GamesClient gamesClient = Games.getGamesClient(this, googleSignInAccount);
		gamesClient.getActivationHint()
				.addOnSuccessListener(new OnSuccessListener<Bundle>() {
					@Override
					public void onSuccess(Bundle hint) {
						if (hint != null) {
							Invitation invitation =
									hint.getParcelable(Multiplayer.EXTRA_INVITATION);

							if (invitation != null && invitation.getInvitationId() != null) {
								// retrieve and cache the invitation ID
								Log.d(TAG, "onConnected: connection hint has a room invite!");
								//acceptInviteToRoom(invitation.getInvitationId());
							}
						}
					}
				})
				.addOnFailureListener(createFailureListener("There was a problem getting the activation hint!"));
	}

	private OnFailureListener createFailureListener(final String string) {
		return new OnFailureListener() {
			@Override
			public void onFailure(@NonNull Exception e) {
				handleException(e, string);
			}
		};
	}

	public void onDisconnected() {
		Log.d(TAG, "onDisconnected()");

		mRealTimeMultiplayerClient = null;
		mInvitationsClient = null;

	}

	private RoomStatusUpdateCallback mRoomStatusUpdateCallback = new RoomStatusUpdateCallback() {
		// Called when we are connected to the room. We're not ready to play yet! (maybe not everybody
		// is connected yet).
		@Override
		public void onConnectedToRoom(Room room) {
			Log.d(TAG, "onConnectedToRoom.");

			//get participants and my ID:
			mParticipants = room.getParticipants();
			mMyId = room.getParticipantId(mPlayerId);

			// save room ID if its not initialized in onRoomCreated() so we can leave cleanly before the game starts.
			if (mRoomId == null) {
				mRoomId = room.getRoomId();
			}

			// print out the list of participants (for debug purposes)
			Log.d(TAG, "Room ID: " + mRoomId);
			Log.d(TAG, "My ID " + mMyId);
			Log.d(TAG, "<< CONNECTED TO ROOM>>");
		}

		// Called when we get disconnected from the room. We return to the main screen.
		@Override
		public void onDisconnectedFromRoom(Room room) {
			mRoomId = null;
			mRoomConfig = null;
			showGameError();
		}


		// We treat most of the room update callbacks in the same way: we update our list of
		// participants and update the display. In a real game we would also have to check if that
		// change requires some action like removing the corresponding player avatar from the screen,
		// etc.
		@Override
		public void onPeerDeclined(Room room, @NonNull List<String> arg1) {
			updateRoom(room);
		}

		@Override
		public void onPeerInvitedToRoom(Room room, @NonNull List<String> arg1) {
			updateRoom(room);
		}

		@Override
		public void onP2PDisconnected(@NonNull String participant) {
		}

		@Override
		public void onP2PConnected(@NonNull String participant) {
		}

		@Override
		public void onPeerJoined(Room room, @NonNull List<String> arg1) {
			updateRoom(room);
		}

		@Override
		public void onPeerLeft(Room room, @NonNull List<String> peersWhoLeft) {
			updateRoom(room);
		}

		@Override
		public void onRoomAutoMatching(Room room) {
			updateRoom(room);
		}

		@Override
		public void onRoomConnecting(Room room) {
			updateRoom(room);
		}

		@Override
		public void onPeersConnected(Room room, @NonNull List<String> peers) {
			updateRoom(room);
		}

		@Override
		public void onPeersDisconnected(Room room, @NonNull List<String> peers) {
			updateRoom(room);
		}
	};

	// Show error message about game being cancelled and return to main screen.
	void showGameError() {
		new AlertDialog.Builder(this)
				.setNeutralButton(android.R.string.ok, null).create();

	}

	private RoomUpdateCallback mRoomUpdateCallback = new RoomUpdateCallback() {

		// Called when room has been created
		@Override
		public void onRoomCreated(int statusCode, Room room) {
			Log.d(TAG, "onRoomCreated(" + statusCode + ", " + room + ")");
			if (statusCode != GamesCallbackStatusCodes.OK) {
				Log.e(TAG, "*** Error: onRoomCreated, status " + statusCode);
				showGameError();
				return;
			}

			// save room ID so we can leave cleanly before the game starts.
			mRoomId = room.getRoomId();

			// show the waiting room UI
			showWaitingRoom(room);
		}

		// Called when room is fully connected.
		@Override
		public void onRoomConnected(int statusCode, Room room) {
			Log.d(TAG, "onRoomConnected(" + statusCode + ", " + room + ")");
			if (statusCode != GamesCallbackStatusCodes.OK) {
				Log.e(TAG, "*** Error: onRoomConnected, status " + statusCode);
				showGameError();
				return;
			}
			updateRoom(room);
		}

		@Override
		public void onJoinedRoom(int statusCode, Room room) {
			Log.d(TAG, "onJoinedRoom(" + statusCode + ", " + room + ")");
			if (statusCode != GamesCallbackStatusCodes.OK) {
				Log.e(TAG, "*** Error: onRoomConnected, status " + statusCode);
				showGameError();
				return;
			}

			// show the waiting room UI
			showWaitingRoom(room);
		}

		// Called when we've successfully left the room (this happens a result of voluntarily leaving
		// via a call to leaveRoom(). If we get disconnected, we get onDisconnectedFromRoom()).
		@Override
		public void onLeftRoom(int statusCode, @NonNull String roomId) {
			// we have left the room; return to main screen.
			Log.d(TAG, "onLeftRoom, code " + statusCode);
		}
	};

	void updateRoom(Room room) {
		if (room != null) {
			mParticipants = room.getParticipants();
		}
		if (mParticipants != null) {
		}
	}

    /*
     * COMMUNICATIONS SECTION. Methods that implement the game's network
     * protocol.
     */

	// Score of other participants. We update this as we receive their scores
	// from the network.
	Map<String, Integer> mParticipantScore = new HashMap<>();

	// Participants who sent us their final score.
	Set<String> mFinishedParticipants = new HashSet<>();

	// Called when we receive a real-time message from the network.
	OnRealTimeMessageReceivedListener mOnRealTimeMessageReceivedListener = new OnRealTimeMessageReceivedListener() {
		@Override
		public void onRealTimeMessageReceived(@NonNull RealTimeMessage realTimeMessage) {
			byte[] buf = realTimeMessage.getMessageData();
			moves.add(buf[0]);

		}
	};

	// Send squares to opponent
	public void sendIncrement(Byte b){
		byte[] msg = new byte[1];
		msg[0] = b;
		
		for (Participant p : mParticipants) {
                //sends unreliable messages to increase performance
				mRealTimeMultiplayerClient.sendUnreliableMessage(msg, mRoomId,
						p.getParticipantId());
		}
	}


}


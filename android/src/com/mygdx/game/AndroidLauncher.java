package com.mygdx.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

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
import com.google.android.gms.games.InvitationsClient;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayersClient;
import com.google.android.gms.games.RealTimeMultiplayerClient;
import com.google.android.gms.games.multiplayer.Invitation;
import com.google.android.gms.games.multiplayer.InvitationCallback;
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
	protected void onCreate(Bundle savedInstanceState) {
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
	private Queue<Byte> moves = new LinkedList<Byte>();

	//game ready to start
	private boolean gameReady;

	private void selectPlayers(int resultCode, Intent data){
		if (resultCode != Activity.RESULT_OK) {
			// Canceled or some other error.
			return;
		}

		// get the invitee list
		final ArrayList<String> invitees = data.getStringArrayListExtra(Games.EXTRA_PLAYER_IDS);
		Log.d(TAG, "Invitee count: " + invitees.size());


		// Get Automatch criteria.

		// Create the room configuration.
		RoomConfig.Builder roomBuilder = RoomConfig.builder(mRoomUpdateCallback)
				.addPlayersToInvite(invitees)
				.setOnMessageReceivedListener(mOnRealTimeMessageReceivedListener)
				.setRoomStatusUpdateCallback(mRoomStatusUpdateCallback);
		roomBuilder.setAutoMatchCriteria(
				RoomConfig.createAutoMatchCriteria(1, 1, 0));

		// Save the roomConfig so we can use it if we call leave().
		mRoomConfig = roomBuilder.build();
		Games.getRealTimeMultiplayerClient(this, GoogleSignIn.getLastSignedInAccount(this))
				.create(mRoomConfig);
	}

	private void waitingRoom(int resultCode, Intent data){


		if (resultCode == Activity.RESULT_OK) {
			// Start the game!
			Log.d(TAG, "ready to start the game");
			gameReady = true;
		} else if (resultCode == Activity.RESULT_CANCELED) {
			leaveRoom();
		} else if (resultCode == GamesActivityResultCodes.RESULT_LEFT_ROOM) {
			leaveRoom();

		}
	}

	private void handleInvitationInboxResult(int response, Intent data) {
		if (response != Activity.RESULT_OK) {
			Log.w(TAG, "*** invitation inbox UI cancelled, " + response);
			return;
		}

		Log.d(TAG, "Invitation inbox UI succeeded.");
		Invitation invitation = data.getExtras().getParcelable(Multiplayer.EXTRA_INVITATION);

		// accept invitation
		if (invitation != null) {
			acceptInviteToRoom(invitation.getInvitationId());
		}
	}

	// Accept the given invitation.
	void acceptInviteToRoom(String invitationId) {
		// accept the invitation
		Log.d(TAG, "Accepting invitation: " + invitationId);

		mRoomConfig = RoomConfig.builder(mRoomUpdateCallback)
				.setInvitationIdToAccept(invitationId)
				.setOnMessageReceivedListener(mOnRealTimeMessageReceivedListener)
				.setRoomStatusUpdateCallback(mRoomStatusUpdateCallback)
				.build();


		mRealTimeMultiplayerClient.join(mRoomConfig)
				.addOnSuccessListener(new OnSuccessListener<Void>() {
					@Override
					public void onSuccess(Void aVoid) {
						Log.d(TAG, "Room Joined Successfully!");
					}
				});
	}




	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == RC_SIGN_IN) {
			Task<GoogleSignInAccount> task =
					GoogleSignIn.getSignedInAccountFromIntent(data);

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
		}

		else if (requestCode == RC_SELECT_PLAYERS) {
			selectPlayers(resultCode, data);
		}
		else if (requestCode == RC_WAITING_ROOM) {
			waitingRoom(resultCode, data);
		}
		else if (requestCode == RC_INVITATION_INBOX) {
			// we got the result from the "select invitation" UI (invitation inbox). We're
			// ready to accept the selected invitation:
			handleInvitationInboxResult(resultCode, data);

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


	public Queue<Byte> popMoves() {
		Queue<Byte> tempQueue = moves;
		moves.clear();
		return tempQueue;
	}







	// called when invitation is received
	private InvitationCallback mInvitationCallback = new InvitationCallback() {
		@Override
		public void onInvitationReceived(@NonNull Invitation invitation) {
			// We got an invitation to play a game! So, store it in
			// mIncomingInvitationId
			mIncomingInvitationId = invitation.getInvitationId();

		}

		@Override
		public void onInvitationRemoved(@NonNull String invitationId) {

			if (mIncomingInvitationId.equals(invitationId) && mIncomingInvitationId != null) {
				mIncomingInvitationId = null;
			}
		}
	};

	public void checkForInvitation() {
		// show list of pending invitations
		mInvitationsClient.getInvitationInboxIntent().addOnSuccessListener(
				new OnSuccessListener<Intent>() {
					@Override
					public void onSuccess(Intent intent) {
						startActivityForResult(intent, RC_INVITATION_INBOX);
					}
				}
		).addOnFailureListener(createFailureListener("There was a problem getting the inbox."));

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

	@Override
	public void startQuickGame() {
		// auto-match criteria to invite one random automatch opponent.
		// You can also specify more opponents (up to 3).
		Bundle autoMatchCriteria = RoomConfig.createAutoMatchCriteria(1, 1, 0);

		// build the room config:
		RoomConfig roomConfig =
				RoomConfig.builder(mRoomUpdateCallback)
						.setOnMessageReceivedListener(mOnRealTimeMessageReceivedListener)
						.setRoomStatusUpdateCallback(mRoomStatusUpdateCallback)
						.setAutoMatchCriteria(autoMatchCriteria)
						.build();



		// Save the roomConfig so we can use it if we call leave().
		mRoomConfig = roomConfig;

		// create room:
		Games.getRealTimeMultiplayerClient(this, GoogleSignIn.getLastSignedInAccount(this))
				.create(roomConfig);
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



	// Handle the result of the "Select players UI" we launched when the user clicked the
	// "Invite friends" button. We react by creating a room with those players.


	private void showWaitingRoom(Room room) {
		Games.getRealTimeMultiplayerClient(this, GoogleSignIn.getLastSignedInAccount(this))
				.getWaitingRoomIntent(room, 1)
				.addOnSuccessListener(new OnSuccessListener<Intent>() {
					@Override
					public void onSuccess(Intent intent) {
						// show waiting room UI
						startActivityForResult(intent, RC_WAITING_ROOM);
					}
				})
				.addOnFailureListener(createFailureListener("There was a problem getting the waiting room!"));
	}



	public void invite() {

		Games.getRealTimeMultiplayerClient(this, GoogleSignIn.getLastSignedInAccount(this))
				.getSelectOpponentsIntent(1, 1, true)
				.addOnSuccessListener(new OnSuccessListener<Intent>() {
					@Override
					public void onSuccess(Intent intent) {
						startActivityForResult(intent, RC_SELECT_PLAYERS);
					}
				});
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
	public void leaveRoom() {
		Log.d(TAG, "Leaving room.");
		gameReady = false;
		if (mRoomId != null) {
			Games.getRealTimeMultiplayerClient(this,
					GoogleSignIn.getLastSignedInAccount(this))
					.leave(mRoomConfig, mRoomId);
			mRoomId = null;
		}
	}

	@Override
	public void writeToLog(String s) {
		Log.d(TAG, s);
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
			leaveRoom();
			mRoomConfig = null;
			showGameError();
		}


		// We treat most of the room update callbacks in the same way: we update our list of
		// participants and update the display. In a real game we would also have to check if that
		// change requires some action like removing the corresponding player avatar from the screen,
		// etc.
		@Override
		public void onPeerDeclined(Room room, @NonNull List<String> arg1) {
		}

		@Override
		public void onPeerInvitedToRoom(Room room, @NonNull List<String> arg1) {
		}

		@Override
		public void onP2PDisconnected(@NonNull String participant) {
		}

		@Override
		public void onP2PConnected(@NonNull String participant) {
		}

		@Override
		public void onPeerJoined(Room room, @NonNull List<String> arg1) {
		}

		@Override
		public void onPeerLeft(Room room, @NonNull List<String> peersWhoLeft) {
		}

		@Override
		public void onRoomAutoMatching(Room room) {
		}

		@Override
		public void onRoomConnecting(Room room) {
		}

		@Override
		public void onPeersConnected(Room room, @NonNull List<String> peers) {
		}

		@Override
		public void onPeersDisconnected(Room room, @NonNull List<String> peers) {
		}
	};

	// Show error message about game being cancelled and return to main screen.
	void showGameError() {
		new AlertDialog.Builder(this)
				.setNeutralButton(android.R.string.ok, null).create();

	}

	private RoomUpdateCallback mRoomUpdateCallback = new RoomUpdateCallback() {
		@Override
		public void onRoomCreated(int code, @Nullable Room room) {
			// Update UI and internal state based on room updates.
			if (code == GamesCallbackStatusCodes.OK && room != null) {
				Log.d(TAG, "Room " + room.getRoomId() + " created.");
				mRoomId = room.getRoomId();
				showWaitingRoom(room);
			} else {
				Log.w(TAG, "Error creating room: " + code);
				return;

			}

		}

		@Override
		public void onJoinedRoom(int code, @Nullable Room room) {
			// Update UI and internal state based on room updates.
			if (code == GamesCallbackStatusCodes.OK && room != null) {
				Log.d(TAG, "Room " + room.getRoomId() + " joined.");
				showWaitingRoom(room);
			} else {
				Log.w(TAG, "Error joining room: " + code);

			}
		}

		@Override
		public void onLeftRoom(int code, @NonNull String roomId) {
			Log.d(TAG, "Left room" + roomId);
			gameReady = false;
		}

		@Override
		public void onRoomConnected(int code, @Nullable Room room) {
			if (code == GamesCallbackStatusCodes.OK && room != null) {
				Log.d(TAG, "Room " + room.getRoomId() + " connected.");
			} else {
				Log.w(TAG, "Error connecting to room: " + code);
				// let screen go to sleep

			}
		}
	};

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

	public boolean isGameReady(){
		return gameReady; //TODO make this return true when game is ready
	}


}


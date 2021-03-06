package com.chatty.android.chatty.utilities;

/**
 * Created by Alejandro on 28/04/2016.
 */
public class Callbacks {

    public interface ChatCallback<T> {
        public void onSuccess(T response);

        public void onFailure(String error);
    }

    public interface CallbackChannel {
        void onChannelPressed(String channelTitle);
    }

    public interface CallbackFavorite {
        void onUserPressed(String user);
        void onUserAlreadyAdded();
    }
}

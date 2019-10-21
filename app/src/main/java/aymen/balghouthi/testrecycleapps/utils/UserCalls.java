package aymen.balghouthi.testrecycleapps.utils;

import androidx.annotation.Nullable;

import java.lang.ref.WeakReference;

import aymen.balghouthi.testrecycleapps.Models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserCalls {
    // 1 - Creating a callback
    public interface Callbacks {
        void onResponse(@Nullable User users);
        void onFailure();
    }

    // 2 - Public method to start fetching users following by Jake Wharton
    public static void fetchUserFollowing(Callbacks callbacks){

        // 2.1 - Create a weak reference to callback (avoid memory leaks)
        final WeakReference<Callbacks> callbacksWeakReference = new WeakReference<Callbacks>(callbacks);

        // 2.2 - Get a Retrofit instance and the related endpoints
        UserService userService = UserService.retrofit.create(UserService.class);

        // 2.3 - Create the call on Github API
        Call<User> call = userService.getFollowing();
        // 2.4 - Start the call
        call.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                // 2.5 - Call the proper callback used in controller (MainFragment)
                if (callbacksWeakReference.get() != null) callbacksWeakReference.get().onResponse(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // 2.5 - Call the proper callback used in controller (MainFragment)
                if (callbacksWeakReference.get() != null) callbacksWeakReference.get().onFailure();
            }
        });
    }
}

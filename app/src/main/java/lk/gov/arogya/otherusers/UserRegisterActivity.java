package lk.gov.arogya.otherusers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import lk.gov.arogya.R;
import lk.gov.arogya.askuserinformation.AskUserInformationActivity;
import lk.gov.arogya.models.User;
import lk.gov.arogya.support.ContentHolder;
import lk.gov.arogya.api.RestAPI;

public class UserRegisterActivity extends AppCompatActivity {

    private ArrayList<User> otherUsers;
    private RecyclerView recyclerUsers;
    private TextView tvNoRegisteredUser;
    private UserRegisterAdapter adapter;
    private RelativeLayout layoutRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        recyclerUsers = findViewById(R.id.recycler_view_schedules);
        tvNoRegisteredUser = findViewById(R.id.tv_no_schedules_list_msg);
        layoutRoot = findViewById(R.id.layout_register_user);
        FloatingActionButton fabRegisterUser = findViewById(R.id.fab_add_schedule);

        recyclerUsers.setLayoutManager(new LinearLayoutManager(this));
        loadRegisteredUsers();

        fabRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContentHolder.getUser().isVerified())
                    registerNewUser();
                else {
                    showNotVerifiedUserDialog();
                }
            }
        });
    }

    private void loadRegisteredUsers() {
        SkeletonScreen skeletonScreen = Skeleton.bind(layoutRoot)
                .load(R.layout.layout_skeleton_register_user)
                .duration(1800)
                .angle(15)
                .color(R.color.overlayBackground)
                .show();
        RestAPI.getUserByUID(ContentHolder.getUID(), new RestAPI.OnSuccessListener<User, Throwable>() {
            @Override
            public void onSuccess(User response) {
                ContentHolder.setUser(response);
                RestAPI.getAllChildUsers(ContentHolder.getUser().getUid(), new RestAPI.OnSuccessListener<ArrayList<User>, Throwable>() {
                    @Override
                    public void onSuccess(ArrayList<User> response) {
                        if (!response.isEmpty()) {

                            tvNoRegisteredUser.setVisibility(View.GONE);
                            otherUsers = response;
                            adapter = new UserRegisterAdapter(UserRegisterActivity.this, otherUsers);
                            recyclerUsers.setAdapter(adapter);
                            skeletonScreen.hide();
                        } else {
                            tvNoRegisteredUser.setVisibility(View.VISIBLE);
                            skeletonScreen.hide();
                        }
                    }

                    @Override
                    public void onFailure(Throwable err) {
                        tvNoRegisteredUser.setVisibility(View.VISIBLE);
                        skeletonScreen.hide();
                    }
                });
            }

            @Override
            public void onFailure(Throwable err) {
                tvNoRegisteredUser.setVisibility(View.VISIBLE);
                skeletonScreen.hide();
            }
        });
    }

    private void registerNewUser() {
        Intent intent = new Intent(UserRegisterActivity.this, AskUserInformationActivity.class);
        intent.putExtra("IS_NEW_USER", true);
        startActivity(intent);
    }

    private void showNotVerifiedUserDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Not Verified")
                .setMessage("You need to be a verified user to add other users. Please wait until you are being verified by local authorities")
                .setPositiveButton("Ok", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}

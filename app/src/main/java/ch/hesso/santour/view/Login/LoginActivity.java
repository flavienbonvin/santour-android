package ch.hesso.santour.view.Login;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

import ch.hesso.santour.R;
import ch.hesso.santour.view.Main.MainActivity;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        try{
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }catch (Exception e){
            e.printStackTrace();
        }

        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.login_activity);
        setTitle("Login");
        super.onCreate(savedInstanceState);

        if (auth.getCurrentUser() != null) {

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }


        loadLastLanguage();

        Button buttonReset = findViewById(R.id.btResetPassword);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(MainActivity.URL_RESET_PASS));
                startActivity(browserIntent);
            }
        });

        //button login
        Button btnLogin = findViewById(R.id.login_button_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                EditText emailView = findViewById(R.id.login_textView_username);
                EditText passView = findViewById(R.id.login_textView_password);

                if (!emailView.getText().toString().equals("") && !passView.getText().toString().equals("")) {

                    auth.signInWithEmailAndPassword(emailView.getText().toString(), passView.getText().toString())
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    //progressBar.setVisibility(View.GONE);
                                    if (!task.isSuccessful()) {
                                        // there was an error
                                        Toast.makeText(LoginActivity.this, "Wrong email/password", Toast.LENGTH_LONG).show();

                                    } else {
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                }else {
                    Toast.makeText(LoginActivity.this, "Fill all the fileds!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadLastLanguage(){
        String language = PreferenceManager.getDefaultSharedPreferences(this).getString("lang", "fr");
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }
}
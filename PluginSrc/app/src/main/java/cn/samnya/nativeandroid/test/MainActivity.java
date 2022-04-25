package cn.samnya.nativeandroid.test;

import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.provider.DocumentsContract;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import cn.samnya.nativeandroid.io.AndroidStorage;
import cn.samnya.nativeandroid.test.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        String childName = "test.txt";
//        String uriString = "content://com.android.externalstorage.documents/tree/primary%3AMusic";
        String uriString = "content://com.android.externalstorage.documents/tree/primary%3ATechmania%2FTracks/document/primary%3ATechmania%2FTracks%2FZero%20Fill%20Love";
        Uri uri = Uri.parse(uriString);
        boolean isDocumentUri = DocumentsContract.isDocumentUri(this, uri);
        boolean isTreeUri = DocumentsContract.isTreeUri( uri);
        String treeDocId = DocumentsContract.getTreeDocumentId(uri);
        Uri resultUri;
        if (isTreeUri) {
            if (!isDocumentUri) {
                String docId = treeDocId + "/" + childName;
                resultUri = DocumentsContract.buildDocumentUriUsingTree(uri, docId);
            } else {
                String docId = DocumentsContract.getDocumentId(uri);
                String resultDocId = docId + "/" + childName;
                resultUri = DocumentsContract.buildDocumentUriUsingTree(uri, resultDocId);
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
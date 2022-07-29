package dk.itu.garbage;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.res.Configuration;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    //Shopping V8 using SQLite database and Navigation (in portrait mode)

    // Model: Database of items
    private final static ItemsViewModel itemsDB= new ItemsViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shoppingmain);
        // Context is needed to set up SQLite
        itemsDB.initialize(this);

        //To handle landscape mode - no navigation needed
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            FragmentManager fm;
            Fragment fragmentUI, fragmentList;
            fm= getSupportFragmentManager();
            fragmentUI= fm.findFragmentById(R.id.container_ui_landscape);
            fragmentList= fm.findFragmentById(R.id.container_list);
            if ((fragmentUI == null) && (fragmentList == null)) {
                fragmentUI= new UIFragment();
                fragmentList= new ListFragment();
                fm.beginTransaction()
                    .add(R.id.container_ui_landscape, fragmentUI)
                    .add(R.id.container_list, fragmentList)
                    .commit();
            }
        }
    }
}
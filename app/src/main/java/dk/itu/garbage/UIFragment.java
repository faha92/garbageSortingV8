package dk.itu.garbage;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

public class UIFragment extends Fragment {
  //GUI variables
  private TextView newWhat, newWhere;
  Button addItem, listItems;

  //Model: Database of items
  private ItemsViewModel itemsDB;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    super.onCreateView(inflater, container, savedInstanceState);
    final View v= inflater.inflate(R.layout.fragment_ui, container, false);

    //Text Fields
    newWhat=  v.findViewById(R.id.what_text);
    newWhere= v.findViewById(R.id.where_text);
    listItems = v.findViewById(R.id.list);
    addItem= v.findViewById(R.id.add_button);

    // Shared data
    itemsDB= new ViewModelProvider(requireActivity()).get(ItemsViewModel.class);

    //Only show List button in Portraitmode
    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
      listItems = v.findViewById(R.id.list);
      listItems.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.action_UIFragment_to_listFragment));
    }

    // adding a new thing
    addItem.setOnClickListener(view -> {
      String whatS= newWhat.getText().toString().trim();
      String whereS= newWhere.getText().toString().trim();
      if ((whatS.length() > 0) && (whereS.length() > 0)) {
        itemsDB.addItem(whatS, whereS);
        newWhat.setText("");
        newWhere.setText("");
      } else Toast.makeText(getActivity(), R.string.empty_toast, Toast.LENGTH_LONG).show();
    });
    return v;
  }
}

package ch.hesso.santour.view.Tracking.Fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ch.hesso.santour.R;
import ch.hesso.santour.adapter.PODListAdapter;
import ch.hesso.santour.db.CategoryPODDB;
import ch.hesso.santour.db.DBCallback;
import ch.hesso.santour.model.CategoryPOD;
import ch.hesso.santour.model.POD;
import ch.hesso.santour.view.Main.MainActivity;
import ch.hesso.santour.view.Main.MainFullScreenPictureActivity;
import ch.hesso.santour.view.Tracking.Activity.TrackActivity;

public class FragmentListPOD extends Fragment {

    private View rootView;
    private PODListAdapter adapter;

    public FragmentListPOD() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.tracking_fragment_list_pod, container, false);

        final ListView list = rootView.findViewById(R.id.list_view_pod);
        list.setAdapter(adapter = new PODListAdapter(FragmentListPOD.this.getContext(), MainActivity.track.getPods()));

        //Search inside the list of POD
        search(list);

        //Dialog with the details of the POD
        onClickDialog(list);

        return rootView;
    }

    /**
     * Dialog that pop once a POD is clicked, shows details and picture
     * @param list
     */
    private void onClickDialog(ListView list) {
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final int pos = position;
                CategoryPODDB.getAll(new DBCallback() {
                    @Override
                    public void resolve(Object o) {
                        List<CategoryPOD> categoryPODList = (List<CategoryPOD>) o;

                        final POD pod = adapter.getListData().get(pos);

                        StringBuilder message = new StringBuilder(getString(R.string.name_) + pod.getName() + "\n" + getString(R.string.name_) + pod.getDescription() + "\n\n" + getString(R.string.danger_rating_) + "\n");

                        for (int i = 0; i < pod.getCategoriesID().size(); i++){
                            if(pod.getCategoriesID().get(i).getPodCatID().equals(categoryPODList.get(i).getId())){
                                message.append("\t").append(categoryPODList.get(i).getName()).append(": ").append(pod.getCategoriesID().get(i).getRate()).append("\n");
                            }
                        }

                        AlertDialog.Builder builder = new AlertDialog.Builder(FragmentListPOD.this.getActivity());
                        builder.setTitle(R.string.detail_pod)
                                .setMessage(message.toString())
                                //Close the dialog
                                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                })
                                //Show the picture
                                .setNeutralButton(getString(R.string.show_pic), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(FragmentListPOD.this.getActivity(), MainFullScreenPictureActivity.class);
                                        intent.putExtra("image", pod.getPicture());
                                        FragmentListPOD.this.startActivity(intent);
                                    }
                                })
                                //Delete the POD
                                .setNegativeButton(getString(R.string.delete), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        MainActivity.track.getPods().remove(pod);
                                        TrackActivity.fragmentListPOD.updateList();
                                    }
                                })
                                .show();
                    }
                });
            }
        });
    }

    /**
     * Search inside the listview
     * @param list
     */
    private void search(final ListView list) {
        EditText editText = rootView.findViewById(R.id.input_search_pod);
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<POD> pods = new ArrayList<>();
                ArrayList<POD> podsAdapter = adapter.getListData();

                for(POD pod : podsAdapter){
                    if(pod.getName().contains(s.toString())){
                        pods.add(pod);
                    }
                }
                list.setAdapter(new PODListAdapter(FragmentListPOD.this.getContext(), pods));
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }


    /**
     * Update the list with the new POD (called once a POD is created)
     */
    public void updateList(){
        ListView list = rootView.findViewById(R.id.list_view_pod);
        list.setAdapter(new PODListAdapter(this.getContext(), MainActivity.track.getPods()));
    }
}

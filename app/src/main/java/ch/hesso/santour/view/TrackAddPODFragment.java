package ch.hesso.santour.view;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import ch.hesso.santour.R;
import ch.hesso.santour.business.LocationManagement;
import ch.hesso.santour.business.PictureManagement;
import ch.hesso.santour.db.DBCallback;
import ch.hesso.santour.model.Position;


public class TrackAddPODFragment extends Fragment {

    private Button nextButton;
    private String imageEncoded;

    private FragmentManager fragmentManager;
    private Fragment fragment;
    private View rootView;

    public TrackAddPODFragment() {
        // Required empty public constructor
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_bar_close:
                getActivity().getFragmentManager().popBackStack();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.track_navigation_bar_actions_cancel, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_track_add_pod, container, false);
        setHasOptionsMenu(true);

        nextButton = rootView.findViewById(R.id.track_add_pod_next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextName = (EditText) getActivity().findViewById(R.id.edit_text_pod_name);
                EditText editTextDesc = (EditText) getActivity().findViewById(R.id.edit_text_pod_desc);
                String poiName = editTextName.getText().toString();
                String poiDesc = editTextDesc.getText().toString();

                if(!poiName.equals("") && !poiDesc.equals("")  && !imageEncoded.equals("")){
                    String[] parameters = new String[]{
                            poiName,
                            poiDesc,
                            imageEncoded
                    };
                    Bundle bundle = new Bundle();
                    bundle.putStringArray("parameters", parameters);
                    fragmentManager  = getFragmentManager();
                    fragment  = new TrackPODDetailsFragment();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.addToBackStack(null);
                    transaction.replace(R.id.main_content, fragment).commit();
                }
            }
        });

        ImageButton pictureButton = (ImageButton)rootView.findViewById(R.id.track_add_pod_add_picture);
        pictureButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intentGetMessage = new Intent(TrackAddPODFragment.this.getActivity(), PictureManagement.class);
                startActivityForResult(intentGetMessage, PictureManagement.REQUEST_IMAGE_CAPTURE);
            }
        });

        LocationManagement.getCurrentPosition(getActivity(), new DBCallback() {
            @Override
            public void resolve(Object o) {
                Position position = (Position) o;

                TextView textViewLat = (TextView) getActivity().findViewById(R.id.tv_lat_add_pod);
                TextView textViewLng = (TextView) getActivity().findViewById(R.id.tv_lng_add_pod);

                textViewLat.setText("Lat: " + Math.floor(position.latitude*100)/100);
                textViewLng.setText("Lng: " + Math.floor(position.longitude*100)/100);
            }
        });

        return rootView;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PictureManagement.REQUEST_IMAGE_CAPTURE) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("imageBitmap");
            imageEncoded = extras.getString("imageString");
            ((ImageView) rootView.findViewById(R.id.track_add_pod_picture_view)).setImageBitmap(imageBitmap);


        }
    }
}
